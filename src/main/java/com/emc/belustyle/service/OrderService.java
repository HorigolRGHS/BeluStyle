package com.emc.belustyle.service;

import com.emc.belustyle.dto.OrderDTO;
import com.emc.belustyle.dto.OrderDetailDTO;
import com.emc.belustyle.dto.PayOsLinkRequestBodyDTO;
import com.emc.belustyle.dto.ItemDataDTO;
import com.emc.belustyle.dto.mapper.OrderMapper;
import com.emc.belustyle.dto.mapper.OrderDetailMapper;
import com.emc.belustyle.entity.*;

import com.emc.belustyle.dto.mapper.ProductVariationMapper;
import com.emc.belustyle.repo.OrderRepository;
import com.emc.belustyle.repo.ProductRepository;
import com.emc.belustyle.repo.ProductVariationRepository;
import com.emc.belustyle.repo.UserRepository;
import com.emc.belustyle.rest.PayOsRestController;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private final ProductRepository productRepository;
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
    private final UserRepository userRepository;

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
                        OrderDetailService orderDetailService,
                        UserRepository userRepository, ProductRepository productRepository) {
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
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Page<OrderDTO> getOrders(String status, String userId, Pageable pageable) {
        Page<Order> orders = orderRepository.findAllByStatusAndUserId(status, userId, pageable);
        return orders.map(orderMapper::toDTO);
    }

    @Transactional
    public Optional<Map<String, Object>> getOrderById(String orderId) {
        return orderRepository.findById(orderId).map(this::buildOrderResponseJson);
    }

    @Transactional
    public Optional<Map<String, Object>> getMyOrderById(String orderId, String username) {
        Optional<Order> order = orderRepository.findOrderByUserIdAndOrderId(username, orderId);
        return orderRepository.findOrderByUserIdAndOrderId(username, orderId)
                .map(this::buildOrderResponseJson);
    }

    @Transactional
    public Map<String, Object> getOrdersByUserId(String userId, Pageable pageable) {
        Page<Order> ordersPage = orderRepository.findByUserId(userId, pageable);

        List<Map<String, Object>> ordersList = ordersPage.stream().map(order -> {
            Map<String, Object> orderJson = new HashMap<>();
            orderJson.put("orderId", order.getOrderId());
            orderJson.put("orderStatus", order.getOrderStatus().toString());
            orderJson.put("totalAmount", order.getTotalAmount());

            // Chi tiết đơn hàng
            List<Map<String, Object>> orderDetailsList = order.getOrderDetails().stream().map(detail -> {
                Map<String, Object> detailJson = new HashMap<>();
                ProductVariation variation = productVariationRepository.findById(detail.getVariationId()).orElse(null);

                if (variation != null) {
                    detailJson.put("productName", variation.getProduct().getProductName());
                    detailJson.put("color", variation.getColor().getColorName());
                    detailJson.put("size", variation.getSize().getSizeName());
                    detailJson.put("orderQuantity", detail.getOrderQuantity());
                    detailJson.put("unitPrice", detail.getUnitPrice());
                    detailJson.put("discountAmount", detail.getDiscountAmount());
                    detailJson.put("productImage", variation.getProductVariationImage());
                }
                return detailJson;
            }).collect(Collectors.toList());

            orderJson.put("orderDetails", orderDetailsList);
            return orderJson;
        }).collect(Collectors.toList());

        // Tạo đối tượng JSON phản hồi bao gồm dữ liệu trang
        Map<String, Object> response = new HashMap<>();
        response.put("orders", ordersList);
        response.put("currentPage", ordersPage.getNumber());
        response.put("totalItems", ordersPage.getTotalElements());
        response.put("totalPages", ordersPage.getTotalPages());

        return response;
    }




    @Transactional
    public List<OrderDetailDTO> getOrderDetails(String orderId) {
        return orderDetailService.getOrderDetailsByOrderId(orderId);
    }

    @Transactional
    public Map<String, Object> createOrder(OrderDTO orderDTO, HttpServletRequest request, HttpServletResponse response) {
        // Log thông tin OrderDTO nhận được
        System.out.println("Received OrderDTO: " + orderDTO); // Kiểm tra lại tại đây

        // 1. Generate OrderId trước khi lưu
        if (orderDTO.getOrderId() == null || orderDTO.getOrderId().isEmpty()) {
            orderDTO.setOrderId(generateOrderId());
        }

        // 2. Khởi tạo Order từ DTO và lưu vào database
        Order order = initializeOrder(orderDTO);
        order.setOrderStatus(Order.OrderStatus.PENDING);
        order = orderRepository.saveAndFlush(order);

        // 3. Gán OrderId vào OrderDetail và lưu OrderDetail vào database
        List<OrderDetail> orderDetails = saveOrderDetails(order, orderDTO);
//        sendOrderCreatedEmail(order, orderDetails);

        // 4. Xử lý thanh toán
        JSONObject paymentResponse = handlePayment(order, orderDTO, orderDetails, request);

        // 5. Xây dựng JSON phản hồi, bao gồm cả redirectUrl
        Map<String, Object> jsonResponse = buildOrderResponseJson(order, orderDetails);

        // Thêm redirectUrl vào phản hồi
        if (paymentResponse != null && paymentResponse.has("redirectUrl")) {
            jsonResponse.put("redirectUrl", paymentResponse.get("redirectUrl"));
        }

        return jsonResponse;
    }


    private Map<String, Object> buildOrderResponseJson(Order order, List<OrderDetail> orderDetails) {
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("orderId", order.getOrderId());
        jsonResponse.put("orderDate", order.getOrderDate());
        jsonResponse.put("totalAmount", order.getTotalAmount());
        jsonResponse.put("paymentMethod", order.getPaymentMethod());
        jsonResponse.put("shippingMethod", order.getShippingMethod());
        jsonResponse.put("orderStatus", order.getOrderStatus().toString());
        jsonResponse.put("transactionReference", order.getTransactionReference());

        List<Map<String, Object>> productDetails = new ArrayList<>();
        for (OrderDetail detail : orderDetails) {
            Map<String, Object> productJson = new HashMap<>();
            ProductVariation variation = productVariationRepository.findById(detail.getVariationId()).orElse(null);
            if (variation != null) {
                productJson.put("productName", variation.getProduct().getProductName());
                productJson.put("size", variation.getSize().getSizeName());
                productJson.put("color", variation.getColor().getColorName());
                productJson.put("quantity", detail.getOrderQuantity());
                productJson.put("unitPrice", detail.getUnitPrice());
            }
            productDetails.add(productJson);
        }
        jsonResponse.put("productDetails", productDetails);

        return jsonResponse;
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
        order.setOrderStatus(Order.OrderStatus.PENDING);// Trạng thái mặc định
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
        // Giới hạn độ dài để phù hợp với độ dài tối đa của cột `transaction_reference`
        return String.valueOf(System.currentTimeMillis()).substring(6); // Lấy 6 ký tự cuối cùng
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

        switch (orderDTO.getPaymentMethod()) {
            case PAYOS:
                return handlePayOsPayment(order, orderDTO, orderDetails, request);
            case VNPAY:
                return handleVNPayPayment(order, orderDTO, orderDetails, request);
            case COD:
                return handleCODPayment(order, orderDetails);
            case TRANSFER:
                return handleTransferPayment(order, orderDetails);
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

    private JSONObject handlePayOsPayment(Order order, OrderDTO orderDTO, List<OrderDetail> orderDetails, HttpServletRequest request) {
        List<ItemDataDTO> itemDataList = orderDTO.getOrderDetails().stream()
                .map(detail -> {
                    ItemDataDTO item = new ItemDataDTO();
                    String productName = productRepository.getProductNameById(detail.getVariationId());
                    item.setName(productName);
                    item.setPrice(detail.getUnitPrice().intValue());
                    item.setQuantity(detail.getOrderQuantity());
                    return item;
                })
                .collect(Collectors.toList());

        PayOsLinkRequestBodyDTO payOsRequest = new PayOsLinkRequestBodyDTO(
                "Thanh Toan Đơn Hàng",
                null,
                (int) (orderDTO.getTotalAmount() * 1),
                null,
                itemDataList
        );

        ObjectNode paymentLinkResponse = payOsRestController.createPaymentLink(payOsRequest, request);
        System.out.println("Payment link response: " + paymentLinkResponse);

        return handlePaymentResponse(order, paymentLinkResponse, orderDetails);
    }


    private JSONObject handleCODPayment(Order order, List<OrderDetail> orderDetails) {
        order.setOrderStatus(Order.OrderStatus.PENDING);
        orderRepository.save(order);
        sendOrderConfirmationEmail(order, orderDetails);

        JSONObject response = new JSONObject();
        response.put("error", 0);
        response.put("message", "Order created with COD payment");

        return response;
    }

    private JSONObject handleTransferPayment(Order order, List<OrderDetail> orderDetails) {
        order.setOrderStatus(Order.OrderStatus.PENDING);
        orderRepository.save(order);
        sendOrderConfirmationEmail(order, orderDetails);

        JSONObject response = new JSONObject();
        response.put("error", 0);
        response.put("message", "Order created with bank transfer payment");
        return response;
    }


    private JSONObject handleVNPayPayment(Order order, OrderDTO orderDTO, List<OrderDetail> orderDetails, HttpServletRequest request) {
        int totalAmountInCents = (int) (orderDTO.getTotalAmount() * 1);
        String paymentLink = vnPayService.createOrder(
                totalAmountInCents,
                "Order " + order.getOrderId(),
                "https://yourdomain.com/order/success",
                request
        );

        String transactionReference = extractTransactionCode(paymentLink);
        order.setTransactionReference(transactionReference);
        orderRepository.save(order);
        sendOrderConfirmationEmail(order, orderDetails);
        return redirectPayment(paymentLink);
    }


    // Phương thức để lấy mã giao dịch từ `paymentLink`
    private String extractTransactionCode(String paymentLink) {
        // Giả định mã giao dịch là phần cuối của `paymentLink` và có độ dài 6 ký tự
        if (paymentLink != null && paymentLink.length() >= 6) {
            return paymentLink.substring(paymentLink.length() - 6);
        }
        return "000000"; // Mã mặc định nếu không tìm thấy mã giao dịch hợp lệ
    }

    private JSONObject handlePaymentResponse(Order order, ObjectNode paymentLinkResponse, List<OrderDetail> orderDetails) {
        if (paymentLinkResponse.get("error").asInt() == 0) {
            String paymentLink = paymentLinkResponse.get("data").get("checkoutUrl").asText(); // Sử dụng checkoutUrl
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
            order.setOrderStatus(isSuccess ? Order.OrderStatus.PAID : Order.OrderStatus.CANCELLED);
            orderRepository.save(order);

            if (isSuccess) {
                sendPaymentSuccessEmail(order);
            } else {
                sendOrderCancellationEmail(order);
            }
        }
    }

    private String generateOrderId() {
        long orderCount = orderRepository.count();
        String formattedOrderNumber = String.format("ORD%03d", orderCount + 1); // Thay đổi định dạng để có ORD001, ORD002,...
        return formattedOrderNumber;
    }


    @Transactional
    public void cancelOrder(String orderId) {
        // Đặt flag để báo hiệu bắt đầu quá trình hủy đơn hàng
        isCancellingOrder = true;

        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid order ID: " + orderId));

            if (!order.getOrderStatus().equals(Order.OrderStatus.PENDING)) {
                throw new IllegalStateException("Cannot cancel an order that is not pending");
            }

            // Cập nhật trạng thái đơn hàng thành "CANCELLED" và lưu vào database
            order.setOrderStatus(Order.OrderStatus.CANCELLED);
            orderRepository.save(order);

            // Kiểm tra lại trạng thái
            if (!order.getOrderStatus().equals(Order.OrderStatus.CANCELLED)) {
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
        String subject = "Order Confirmation #" + order.getOrderId();
        String body = buildOrderEmailBody(order, orderDetails, "Your order has been successfully created! Thank you for shopping at BeluStyle!");
        emailService.sendHtmlMessage(order.getUser().getEmail(), subject, body);
    }

    private void sendPaymentCallbackEmail(Order order, boolean isSuccess) {
        String subject = "Order Confirmation #" + order.getOrderId();
        String message = isSuccess ? "Your order has been successfully paid!" : "Payment failed.";
        String body = buildOrderEmailBody(order, order.getOrderDetails(), message + " Thank you for shopping at BeluStyle!");
        emailService.sendHtmlMessage(order.getUser().getEmail(), subject, body);
    }

    private void sendOrderCancellationEmail(Order order) {
        String subject = "Order Cancellation #" + order.getOrderId();
        String body = buildOrderEmailBody(order, order.getOrderDetails(), "Your order has been successfully canceled.");
        emailService.sendHtmlMessage(order.getUser().getEmail(), subject, body);
    }

    private void sendOrderCreatedEmail(Order order, List<OrderDetail> orderDetails) {
        String subject = "Order Created #" + order.getOrderId();
        String body = buildOrderEmailBody(order, orderDetails, "Your order has been created! Please complete the payment so we can process your order.");
        emailService.sendHtmlMessage(order.getUser().getEmail(), subject, body);
    }

    private void sendPaymentSuccessEmail(Order order) {
        String subject = "Payment Successful for Order #" + order.getOrderId();
        String body = buildOrderEmailBody(order, order.getOrderDetails(), "Your order has been successfully paid! Your order is being processed.");
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

    @Transactional
    public void reviewOrderByStaff(String orderId, String staffName, boolean isApproved) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order ID"));

        // Lấy thông tin của staff dựa trên tên
        User staff = userRepository.findByUsername(staffName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid staff name"));

        if (order.getOrderStatus() == Order.OrderStatus.PAID) {
            order.setOrderStatus(isApproved ? Order.OrderStatus.PROCESSING : Order.OrderStatus.CANCELLED);
            order.setStaff(staff); // Gán staff vào order
            orderRepository.save(order);
        } else {
            throw new IllegalStateException("Order must be in PAID status to be reviewed by staff.");
        }
    }


    public void confirmOrderReceived(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Invalid order ID"));
        if (order.getOrderStatus() == Order.OrderStatus.PROCESSING) {
            order.setOrderStatus(Order.OrderStatus.COMPLETED);
            orderRepository.save(order);
        } else {
            throw new IllegalStateException("Order must be in PROCESSING status to confirm receipt.");
        }
    }
    // Phương thức chung để xây dựng nội dung email
    private String buildOrderEmailBody(Order order, List<OrderDetail> orderDetails, String headerMessage) {
        String logoUrl = "https://i.imgur.com/DjTbAx2.png"; // Shop logo URL
        String primaryColor = "#62C0EE"; // Primary color of the email
        String accentColor = "#555"; // Secondary text color
        String facebookUrl = "https://www.facebook.com/belustyle.2024/"; // URL to your Facebook page

        StringBuilder body = new StringBuilder();
        body.append("<div style='font-family: Arial, sans-serif; max-width: 600px; margin: auto; color: ")
                .append(accentColor).append("; background-color: #f9f9f9; border-radius: 8px;'>")

                // Logo and Shop Name
                .append("<div style='text-align: center; padding: 20px; background-color: ").append(primaryColor)
                .append("; color: #ffffff;'>")
                .append("<img src='").append(logoUrl).append("' alt='BeluStyle Logo' style='height: 45px; vertical-align: middle; margin-right: 10px;' />")
                .append("<span style='font-size: 24px; vertical-align: middle; font-weight: bold;'>BeluStyle</span>")
                .append("</div>")

                // Order Content
                .append("<div style='padding: 20px; background-color: #ffffff; border: 1px solid ").append(primaryColor).append("; '>")
                .append("<h2 style='color: ").append(primaryColor).append("; font-size: 20px;'>").append(headerMessage).append("</h2>")
                .append("<p style='font-size: 16px; color: ").append(accentColor).append(";'>Thank you for shopping at BeluStyle! Below are the details of your order.</p>")

                .append("<h3 style='color: ").append(primaryColor).append("; font-size: 18px;'>Order Information:</h3>")
                .append("<ul style='list-style-type: none; padding: 0; color: ").append(accentColor).append(";'>")
                .append("<li><strong>Order ID:</strong> ").append(order.getOrderId()).append("</li>")
                .append("<li><strong>Order Date:</strong> ").append(order.getOrderDate()).append("</li>")
                .append("<li><strong>Payment Method:</strong> ").append(order.getPaymentMethod()).append("</li>")
                .append("<li><strong>Shipping Method:</strong> ").append(order.getShippingMethod()).append("</li>")
                .append("<li><strong>Total Amount:</strong> ").append(order.getTotalAmount()).append(" VND</li>")
                .append("</ul>")

                .append("<h3 style='color: ").append(primaryColor).append("; font-size: 18px;'>Product Details:</h3>")
                .append("<table style='width: 100%; border-collapse: collapse; margin-top: 10px;'>")
                .append("<thead>")
                .append("<tr style='background-color: ").append(primaryColor).append("; color: #ffffff;'>")
                .append("<th style='padding: 8px; border: 1px solid ").append(primaryColor).append(";'>Product Name</th>")
                .append("<th style='padding: 8px; border: 1px solid ").append(primaryColor).append(";'>Size</th>")
                .append("<th style='padding: 8px; border: 1px solid ").append(primaryColor).append(";'>Color</th>")
                .append("<th style='padding: 8px; border: 1px solid ").append(primaryColor).append(";'>Quantity</th>")
                .append("<th style='padding: 8px; border: 1px solid ").append(primaryColor).append(";'>Unit Price</th>")
                .append("</tr>")
                .append("</thead>")
                .append("<tbody>");

        List<ProductVariation> productVariations = convertOrderDetailsToProductVariations(orderDetails);
        if (productVariations != null && !productVariations.isEmpty()) {
            for (ProductVariation productVariation : productVariations) {
                int orderQuantity = getOrderQuantityForVariation(orderDetails, String.valueOf(productVariation.getVariationId()));

                body.append("<tr>")
                        .append("<td style='padding: 8px; border: 1px solid #ddd; color: ").append(accentColor).append(";'>").append(productVariation.getProduct().getProductName()).append("</td>")
                        .append("<td style='padding: 8px; border: 1px solid #ddd; color: ").append(accentColor).append(";'>").append(productVariation.getSize().getSizeName()).append("</td>")
                        .append("<td style='padding: 8px; border: 1px solid #ddd; color: ").append(accentColor).append(";'>").append(productVariation.getColor().getColorName()).append("</td>")
                        .append("<td style='padding: 8px; border: 1px solid #ddd; color: ").append(accentColor).append(";'>").append(orderQuantity).append("</td>")
                        .append("<td style='padding: 8px; border: 1px solid #ddd; color: ").append(accentColor).append(";'>").append(productVariation.getProductPrice()).append(" VND</td>")
                        .append("</tr>");
            }
        } else {
            body.append("<tr><td colspan='5' style='padding: 8px; border: 1px solid #ddd; text-align: center; color: ").append(accentColor).append(";'>No products in this order.</td></tr>");
        }

        body.append("</tbody>")
                .append("</table>")

                // Contact Us
                .append("<div style='text-align: center; margin-top: 20px;'>")
                .append("<p style='font-size: 16px; color: ").append(accentColor).append(";'>If you have any questions, please contact us:</p>")
                .append("<a href='").append(facebookUrl).append("' style='color: ").append(primaryColor).append("; font-size: 16px; text-decoration: none;'>Contact Us</a>")
                .append("</div>")

                .append("</div>")

                // Footer
                .append("<footer style='text-align: center; padding: 10px; background-color: ").append(primaryColor).append("; color: #ffffff; border-bottom-left-radius: 8px; border-bottom-right-radius: 8px;'>")
                .append("<p style='margin: 0; font-size: 14px;'>© 2024 BeluStyle</p>")
                .append("</footer>")
                .append("</div>");

        return body.toString();
    }



    private Map<String, Object> buildOrderResponseJson(Order order) {
        Map<String, Object> orderJson = new HashMap<>();
        orderJson.put("orderId", order.getOrderId());
        orderJson.put("orderStatus", order.getOrderStatus().toString());
        orderJson.put("totalAmount", order.getTotalAmount());

        List<Map<String, Object>> orderDetailsList = order.getOrderDetails().stream()
                .map(this::buildOrderDetailJson)
                .collect(Collectors.toList());

        orderJson.put("orderDetails", orderDetailsList);
        return orderJson;
    }

    private Map<String, Object> buildOrderDetailJson(OrderDetail detail) {
        Map<String, Object> detailJson = new HashMap<>();
        ProductVariation variation = productVariationRepository.findById(detail.getVariationId()).orElse(null);

        if (variation != null) {
            detailJson.put("productName", variation.getProduct().getProductName());
            detailJson.put("color", variation.getColor().getColorName());
            detailJson.put("size", variation.getSize().getSizeName());
            detailJson.put("orderQuantity", detail.getOrderQuantity());
            detailJson.put("unitPrice", detail.getUnitPrice());
            detailJson.put("discountAmount", detail.getDiscountAmount());
            detailJson.put("productImage", variation.getProductVariationImage()); // Lấy ảnh từ ProductVariation
        }
        return detailJson;
    }

}

