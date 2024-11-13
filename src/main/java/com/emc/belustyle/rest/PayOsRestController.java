package com.emc.belustyle.rest;

import com.emc.belustyle.dto.PayOsLinkRequestBodyDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;
import vn.payos.type.PaymentLinkData;


@RestController
@RequestMapping("/api/payment/payos")
public class PayOsRestController {
    private final PayOS payOS;

    @Autowired
    public PayOsRestController(PayOS payOS) {
        super();
        this.payOS = payOS;
    }

    @PostMapping(path = "/create")
    public ObjectNode createPaymentLink(@RequestBody PayOsLinkRequestBodyDTO RequestBody, HttpServletRequest httpServletRequest) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            final String baseUrl = getBaseUrl(httpServletRequest);
            final String description = "Thanh Toan BeluStyle";
            final String returnUrl = "http://localhost:3000/orders/success";
//            final String returnUrl = baseUrl + "/order/success";
//            final String cancelUrl = baseUrl + "/order/cancel";
            final String cancelUrl = "http://localhost:3000/orders/error";
            final int price = RequestBody.getPrice();

            String currentTimeString = String.valueOf(new Date().getTime());
            long orderCode = Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));

            List<ItemData> itemDataList = RequestBody.getItems().stream()
                    .map(itemDTO -> ItemData.builder()
                            .name(itemDTO.getName())
                            .price(itemDTO.getPrice())
                            .quantity(itemDTO.getQuantity())
                            .build())
                    .toList();

            PaymentData paymentData = PaymentData.builder()
                    .orderCode(orderCode)
                    .description(description)
                    .amount(price)
                    .items(itemDataList)
                    .returnUrl(returnUrl)
                    .cancelUrl(cancelUrl)
                    .build();

            CheckoutResponseData data = payOS.createPaymentLink(paymentData);

            response.put("error", 0);
            response.put("message", "success");
            response.set("data", objectMapper.valueToTree(data));
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", -1);
            response.put("message", "fail");
            response.set("data", null);
            return response;
        }
    }

    @GetMapping(path = "/{orderId}")
    public ObjectNode getOrderById(@PathVariable("orderId") long orderId) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();

        try {
            PaymentLinkData order = payOS.getPaymentLinkInformation(orderId);

            response.set("data", objectMapper.valueToTree(order));
            response.put("error", 0);
            response.put("message", "ok");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", -1);
            response.put("message", e.getMessage());
            response.set("data", null);
            return response;
        }

    }

    @PutMapping(path = "/{orderId}")
    public ObjectNode cancelOrder(@PathVariable("orderId") long orderId) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            PaymentLinkData order = payOS.cancelPaymentLink(orderId, null);
            response.set("data", objectMapper.valueToTree(order));
            response.put("error", 0);
            response.put("message", "ok");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", -1);
            response.put("message", e.getMessage());
            response.set("data", null);
            return response;
        }
    }

    private String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();

        String url = scheme + "://" + serverName;
        if ((scheme.equals("http") && serverPort != 80) || (scheme.equals("https") && serverPort != 443)) {
            url += ":" + serverPort;
        }
        url += contextPath;
        return url;
    }
}
