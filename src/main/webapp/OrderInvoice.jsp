<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="./css/orderInvoice.css"/>
    </head>
    <body>
        <c:set var="orderIdParam" value="${param.orderId}" />
        <c:choose>
            <c:when test="${not empty orderIdParam}">

                <%-- Create an instance of OrderDAO --%>
                <jsp:useBean id="orderDAO" class="dao.OrderDAO" />

                <c:set var="order" value="${orderDAO.getOrderById(orderIdParam)}" />
                <c:if test="${not empty order}">
                    <div class="container">
                        <div class="logo"> <img src="./images/BeluIcon.png" alt="Logo"></div>
                        <h1><strong>BeluStyle</strong></h1>
                        <h2 style="display: inline-block;">Order Invoice</h2>
                        <p><strong>Order ID:</strong> ${order.orderID}</p>
                        <p><strong>Order Date:</strong> ${order.orderDate}</p>
                        <p><strong>Customer:</strong> ${orderDAO.getFullnameOrder(order.username)}</p> <table border="1"> 
                            <thead>
                                <tr>
                                    <th>Product</th>
                                    <th>Quantity</th>
                                    <th>Price</th>
                                    <th>Total</th>
                                </tr>
                            </thead>
                            <tbody>
                                <jsp:useBean id="OrderDetailDAO" class="dao.OrderDetailDAO" />
                                <c:set var="grandTotal" value="0" /> 
                                <c:forEach var="orderDetail" items="${OrderDetailDAO.getOrderDetailsByOrderId(orderIdParam)}">
                                    <tr>
                                        <jsp:useBean id="ProductDAO" class="dao.ProductDAO" />
                                        <td>${ProductDAO.getProductNameById(orderDetail.productID)}</td>
                                        <td>${orderDetail.quantity}</td>
                                        <td>${orderDetail.price} VND</td>
                                        <td>${orderDetail.quantity * orderDetail.price} VND</td> 
                                        <c:set var="grandTotal" value="${grandTotal + orderDetail.quantity * orderDetail.price}" />
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                        <div class="total">
                            <strong>Grand Total:</strong> ${grandTotal} VND
                        </div>
                    </div>
                </c:if>
            </c:when>
        </c:choose>
    </body>
</html>
