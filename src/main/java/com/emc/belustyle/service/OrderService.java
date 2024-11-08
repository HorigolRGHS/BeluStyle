package com.emc.belustyle.service;

import com.emc.belustyle.dto.OrderDTO;
import com.emc.belustyle.dto.OrderDetailDTO;
import com.emc.belustyle.dto.PayOsLinkRequestBodyDTO;
import com.emc.belustyle.dto.ItemDataDTO;
import com.emc.belustyle.dto.mapper.OrderMapper;
import com.emc.belustyle.dto.mapper.OrderDetailMapper;
import com.emc.belustyle.entity.Order;
import com.emc.belustyle.entity.OrderDetail;

import com.emc.belustyle.entity.ProductVariation;
import com.emc.belustyle.dto.mapper.ProductVariationMapper;
import com.emc.belustyle.entity.User;
import com.emc.belustyle.repo.OrderRepository;
import com.emc.belustyle.repo.ProductVariationRepository;
import com.emc.belustyle.rest.PayOsRestController;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private boolean isCancellingOrder = false;
    private final OrderMapper orderMapper;
    private final OrderDetailMapper orderDetailMapper;
    private final VNPayService vnPayService;
    private final PayOsRestController payOsRestController;
    private final EmailService emailService;
    private final OrderDetailService orderDetailService;
    private final ProductVariationService productVariationService;
    private final ProductVariationRepository productVariationRepository;
    private final ProductVariationMapper productVariationMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        OrderMapper orderMapper,
                        OrderDetailMapper orderDetailMapper,
                        VNPayService vnPayService,
                        PayOsRestController payOsRestController,
                        EmailService emailService,
                        ProductVariationService productVariationService,
                        ProductVariationRepository productVariationRepository,
                        ProductVariationMapper productVariationMapper,
                        OrderDetailService orderDetailService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderDetailMapper = orderDetailMapper;
        this.vnPayService = vnPayService;
        this.payOsRestController = payOsRestController;
        this.emailService = emailService;
        this.orderDetailService = orderDetailService;
        this.productVariationService = productVariationService;
        this.productVariationRepository = productVariationRepository;
        this.productVariationMapper = productVariationMapper;

    }

    @Transactional
    public Page<OrderDTO> getOrders(String status, String userId, Pageable pageable) {
        Page<Order> orders = orderRepository.findAllByStatusAndUserId(status, userId, pageable);
        return orders.map(orderMapper::toDTO);
    }

    @Transactional
    public Optional<OrderDTO> getOrderById(String orderId) {
        return orderRepository.findById(orderId).map(orderMapper::toDTO);
    }
    @Transactional
    public Page<OrderDTO> getOrdersByUserId(String userId, Pageable pageable) {
        Page<Order> orders = orderRepository.findByUserId(userId, pageable);
        return orders.map(orderMapper::toDTO);
    }

    @Transactional
    public List<OrderDetailDTO> getOrderDetails(String orderId) {
        return orderDetailService.getOrderDetailsByOrderId(orderId);
    }

    @Transactional
    public JSONObject createOrder(OrderDTO orderDTO, HttpServletRequest request) {
        // Log thông tin OrderDTO nhận được
        System.out.println("Received OrderDTO: " + orderDTO); // Kiểm tra lại tại đây

        // 1. Generate OrderId trước khi lưu
        if (orderDTO.getOrderId() == null || orderDTO.getOrderId().isEmpty()) {
            orderDTO.setOrderId(generateOrderId());
        }

        // 2. Khởi tạo Order từ DTO và lưu vào database
        Order order = initializeOrder(orderDTO);
        order = orderRepository.saveAndFlush(order);

        // 3. Gán OrderId vào OrderDetail và lưu OrderDetail vào database
        List<OrderDetail> orderDetails = saveOrderDetails(order, orderDTO);

        // 4. Xử lý thanh toán
        return handlePayment(order, orderDTO, orderDetails, request);
    }



    private Order initializeOrder(OrderDTO orderDTO) {
        Order order = new Order();

        // Gán OrderId nếu chưa có
        if (orderDTO.getOrderId() == null || orderDTO.getOrderId().isEmpty()) {
            orderDTO.setOrderId(generateOrderId()); // Phương thức generateOrderId() đảm bảo định dạng B00001, B00002, ...
        }
        order.setOrderId(orderDTO.getOrderId());

        // Gán thông tin từ DTO sang Order
        setUser(order, orderDTO.getUserId()); // Thiết lập user từ userId
        order.setOrderDate(new Date()); // Sử dụng java.util.Date
        order.setOrderStatus("PENDING"); // Trạng thái mặc định
        order.setTotalAmount(orderDTO.getTotalAmount());

        order.setUserAddress(orderDTO.getUserAddress());
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        order.setShippingMethod(orderDTO.getShippingMethod());
        order.setTrackingNumber(orderDTO.getTrackingNumber());
        order.setNotes(orderDTO.getNotes());
        order.setDiscountCode(orderDTO.getDiscountCode());
        order.setBillingAddress(orderDTO.getBillingAddress());

        // Tạo expectedDeliveryDate là 10 ngày sau
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 10);
        order.setExpectedDeliveryDate(calendar.getTime()); // Sử dụng java.util.Date

        // Gán transaction reference hoặc thiết lập giá trị mặc định
        order.setTransactionReference(orderDTO.getTransactionReference() != null ? orderDTO.getTransactionReference() : generateDefaultTransactionReference());

        return order;
    }

    // Thay vì setUserId, ta sẽ tạo User và gán vào order
    private void setUser(Order order, String userId) {
        if (userId != null) {
            User user = new User();
            user.setUserId(userId);
            order.setUser(user);
        }
    }

    // Tạo phương thức generateDefaultTransactionReference()
    private String generateDefaultTransactionReference() {
        return "Default_Transaction_Ref_" + System.currentTimeMillis(); // Ví dụ đơn giản để tạo reference mặc định
    }




    private List<OrderDetail> saveOrderDetails(Order order, OrderDTO orderDTO) {
        if (orderDTO.getOrderDetails() == null || orderDTO.getOrderDetails().isEmpty()) {
            throw new IllegalArgumentException("Order details cannot be null");
        }

        System.out.println("Order details count: " + orderDTO.getOrderDetails().size());

        List<OrderDetail> orderDetails = orderDTO.getOrderDetails().stream()
                .map(detailDTO -> {
                    OrderDetail orderDetail = orderDetailMapper.toEntity(detailDTO);
                    orderDetail.setOrder(order);
                    // Log để kiểm tra giá trị của orderDetail
                    System.out.println("Mapping OrderDetail: " + orderDetail);
                    return orderDetail;
                })
                .collect(Collectors.toList());

        // Log để kiểm tra danh sách orderDetails sau khi ánh xạ
        System.out.println("Saved OrderDetails count: " + orderDetails.size());

        // Lưu danh sách orderDetails
        orderDetailService.saveAll(orderDetails);
        System.out.println("After save OrderDetails count: " + orderDetails.size());

        return orderDetails;
    }

    private JSONObject handlePayment(Order order, OrderDTO orderDTO, List<OrderDetail> orderDetails, HttpServletRequest request) {
        if (orderDetails == null || orderDetails.isEmpty()) {
            throw new IllegalArgumentException("Order details cannot be null or empty for payment processing");
        }
//        List<ItemDataDTO> itemDataList = createItemDataList(orderDTO);
        switch (orderDTO.getPaymentMethod().toUpperCase()) {
            case "PAYOS":
                return null;
//                return handlePayOsPayment(order, orderDTO, orderDetails, itemDataList, request);
            case "VNPAY":
                return handleVNPayPayment(order, orderDTO, orderDetails,request);
            default:
                throw new IllegalArgumentException("Invalid payment method: " + orderDTO.getPaymentMethod());
        }
    }

//    private List<ItemDataDTO> createItemDataList(OrderDTO orderDTO) {
//        return orderDTO.getOrderDetails().stream()
//                .map(orderDetail -> new ItemDataDTO(
//                        null, // Hoặc lấy tên từ một nguồn khác nếu có
//                        orderDetail.getVariationId(),
//                        orderDetail.getUnitPrice().intValue(),
//                        orderDetail.getOrderQuantity()
//                ))
//                .collect(Collectors.toList());
//    }

//    private JSONObject handlePayOsPayment(Order order, OrderDTO orderDTO, List<OrderDetail> orderDetails, List<ItemDataDTO> itemDataList, HttpServletRequest request) {
//        PayOsLinkRequestBodyDTO payOsRequest = new PayOsLinkRequestBodyDTO(
//                "Thanh Toan Đơn Hàng",
//                "Chi tiết đơn hàng",
//                (int) (orderDTO.getTotalAmount() * 100),
//                itemDataList
//        );
//        ObjectNode paymentLinkResponse = payOsRestController.createPaymentLink(payOsRequest, request);
//        return handlePaymentResponse(order, paymentLinkResponse,orderDetails);
//    }

    private JSONObject handleVNPayPayment(Order order, OrderDTO orderDTO, List<OrderDetail> orderDetails,  HttpServletRequest request) {
        int totalAmountInCents = (int) (orderDTO.getTotalAmount() * 100);
        String paymentLink = vnPayService.createOrder(
                totalAmountInCents,
                "Order " + order.getOrderId(),
                "https://yourdomain.com/order/success",
                request
        );
        order.setTransactionReference(paymentLink);
        orderRepository.save(order);
        sendOrderConfirmationEmail(order,orderDetails);
        return redirectPayment(paymentLink);
    }


    private JSONObject handlePaymentResponse(Order order, ObjectNode paymentLinkResponse,List<OrderDetail> orderDetails) {
        if (paymentLinkResponse.get("error").asInt() == 0) {
            String paymentLink = paymentLinkResponse.get("data").get("paymentLink").asText();
            order.setTransactionReference(paymentLink);
            orderRepository.save(order);
            sendOrderConfirmationEmail(order, orderDetails);
            return redirectPayment(paymentLink);
        }
        return null;
    }
    private JSONObject redirectPayment(String paymentLink) {
        JSONObject response = new JSONObject();
        response.put("error", 0);
        response.put("message", "success");
        response.put("redirectUrl", paymentLink);
        return response;
    }

    public void handlePaymentCallback(String orderId, boolean isSuccess) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setOrderStatus(isSuccess ? "COMPLETED" : "FAILED");
            orderRepository.save(order);
            sendPaymentCallbackEmail(order, isSuccess);

        }
    }


    private String generateOrderId() {
        long orderCount = orderRepository.count();
        String formattedOrderNumber = new DecimalFormat("00000").format(orderCount + 1);
        return "B" + formattedOrderNumber;
    }

    @Transactional
    public void cancelOrder(String orderId) {
        // Đặt flag để báo hiệu bắt đầu quá trình hủy đơn hàng
        isCancellingOrder = true;

        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid order ID: " + orderId));

            if (!order.getOrderStatus().equals("PENDING")) {
                throw new IllegalStateException("Cannot cancel an order that is not pending");
            }

            // Cập nhật trạng thái đơn hàng thành "CANCELLED" và lưu vào database
            order.setOrderStatus("CANCELLED");
            orderRepository.save(order);

            // Kiểm tra lại trạng thái
            if (!order.getOrderStatus().equals("CANCELLED")) {
                throw new IllegalStateException("Order status was not updated to CANCELLED");
            }

            // Gửi email hủy đơn hàng
            sendOrderCancellationEmail(order);

        } finally {
            // Reset lại flag sau khi quá trình hủy đơn hàng hoàn tất
            isCancellingOrder = false;
        }
    }

    private void sendOrderConfirmationEmail(Order order, List<OrderDetail> orderDetails) {
        // Kiểm tra orderDetails có null không
        if (orderDetails == null) {
            System.out.println("orderDetails is null in sendOrderConfirmationEmail");
        } else {
            System.out.println("orderDetails in sendOrderConfirmationEmail: " + orderDetails);
        }

        String subject = "Xác nhận đơn hàng #" + order.getOrderId();
        StringBuilder body = new StringBuilder();

        // Xây dựng nội dung email
        body.append("<h2>Cảm ơn bạn đã đặt hàng tại BeluStyle!</h2>")
                .append("<p>Đơn hàng của bạn đã được tạo thành công với mã số: <strong>")
                .append(order.getOrderId())
                .append("</strong></p>")
                .append("<h3>Thông tin đơn hàng:</h3>")
                .append("<ul>")
                .append("<li><strong>Ngày đặt hàng:</strong> ").append(order.getOrderDate()).append("</li>")
                .append("<li><strong>Phương thức thanh toán:</strong> ").append(order.getPaymentMethod()).append("</li>")
                .append("<li><strong>Phương thức giao hàng:</strong> ").append(order.getShippingMethod()).append("</li>")
                .append("<li><strong>Tổng số tiền:</strong> ").append(order.getTotalAmount()).append(" VND</li>")
                .append("</ul>")
                .append("<h3>Chi tiết sản phẩm:</h3><ul>");

        // Chuyển đổi OrderDetails thành danh sách ProductVariations
        List<ProductVariation> productVariations = convertOrderDetailsToProductVariations(orderDetails);

        body.append("<table style='width: 100%; border-collapse: collapse;'>")
                .append("<thead>")
                .append("<tr>")
                .append("<th style='border: 1px solid #ddd; padding: 8px;'>Tên sản phẩm</th>")
                .append("<th style='border: 1px solid #ddd; padding: 8px;'>Kích thước</th>")
                .append("<th style='border: 1px solid #ddd; padding: 8px;'>Màu sắc</th>")
                .append("<th style='border: 1px solid #ddd; padding: 8px;'>Số lượng</th>")
                .append("<th style='border: 1px solid #ddd; padding: 8px;'>Đơn giá</th>")
                .append("</tr>")
                .append("</thead>")
                .append("<tbody>");
        // Kiểm tra nếu danh sách ProductVariations không null và không rỗng
        if (productVariations != null && !productVariations.isEmpty()) {
            for (ProductVariation productVariation : productVariations) {
                // Kiểm tra orderDetails trước khi gọi getOrderQuantityForVariation
                if (orderDetails != null) {
                    int orderQuantity = getOrderQuantityForVariation(orderDetails, String.valueOf(productVariation.getVariationId()));

                    // Xây dựng nội dung chi tiết sản phẩm
                    body.append("<tr>")
                            .append("<td style='border: 1px solid #ddd; padding: 8px;'>").append(productVariation.getProduct().getProductName()).append("</td>")
                            .append("<td style='border: 1px solid #ddd; padding: 8px;'>").append(productVariation.getSize().getSizeName()).append("</td>")
                            .append("<td style='border: 1px solid #ddd; padding: 8px;'>").append(productVariation.getColor().getColorName()).append("</td>")
                            .append("<td style='border: 1px solid #ddd; padding: 8px;'>").append(orderQuantity).append("</td>")
                            .append("<td style='border: 1px solid #ddd; padding: 8px;'>").append(productVariation.getProductPrice()).append(" VND</td>")
                            .append("</tr>");
                } else {
                    System.out.println("orderDetails is null while iterating ProductVariations.");
                }
            }
        } else {
            body.append("<li>Không có sản phẩm nào trong đơn hàng.</li>");
        }

        body.append("</tbody>")
                .append("</table>");

        // Gửi email
        emailService.sendHtmlMessage(order.getUser().getEmail(), subject, body.toString());
    }


    private void sendPaymentCallbackEmail(Order order, boolean isSuccess) {
        String subject = "Xác nhận đơn hàng #" + order.getOrderId();
        StringBuilder body = new StringBuilder();

        // Xây dựng nội dung email
        body.append("<h2>Xác nhận đơn hàng #" + order.getOrderId() + "</h2>")
                .append("<p>Đơn hàng của bạn " + (isSuccess ? "đã được thanh toán thành công" : "thanh toán thất bại") + ".</p>")
                .append("<p>Cảm ơn bạn đã mua sắm tại BeluStyle!</p>");

        // Gửi email
        emailService.sendHtmlMessage(order.getUser().getEmail(), subject, body.toString());
    }

    private void sendOrderCancellationEmail(Order order) {
        String subject = "Hủy đơn hàng #" + order.getOrderId();
        String body = "Đơn hàng của bạn với mã số: " + order.getOrderId() + " đã bị hủy thành công.";
        emailService.sendHtmlMessage(order.getUser().getEmail(), subject, body);
    }

    // Phương thức chuyển đổi OrderDetails thành danh sách ProductVariations
    private List<ProductVariation> convertOrderDetailsToProductVariations(List<OrderDetail> orderDetails) {
        if (orderDetails == null) {
            System.out.println("orderDetails is null in convertOrderDetailsToProductVariations");
        } else {
            System.out.println("orderDetails in convertOrderDetailsToProductVariations: " + orderDetails);
        }

        List<ProductVariation> productVariations = new ArrayList<>();
        for (OrderDetail detail : orderDetails) {
            productVariationRepository.findById(detail.getVariationId())
                    .ifPresent(productVariations::add);
        }
        return productVariations;
    }

    // Phương thức lấy số lượng đơn hàng tương ứng cho một variation
    private int getOrderQuantityForVariation(List<OrderDetail> orderDetails, String variationId) {
        if (orderDetails == null) {
            System.out.println("orderDetails is null in getOrderQuantityForVariation");
        } else {
            System.out.println("orderDetails in getOrderQuantityForVariation: " + orderDetails);
        }
        for (OrderDetail detail : orderDetails) {
//            if (detail.getVariationId().equals(variationId)) {
            return detail.getOrderQuantity();
//        }
        }
        return 0; // Trả về 0 nếu không tìm thấy
    }
}

