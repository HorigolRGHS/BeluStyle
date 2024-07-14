<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <%-- Import fmt taglib --%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="./css/orderInvoice.css"/>
    </head>
    <body>
        <c:set var="orderIdParam" value="${param.orderId}" />
        <c:choose>
            <c:when test="${not empty orderIdParam}">
                <jsp:useBean id="orderDAO" class="dao.OrderDAO" />
                <jsp:useBean id="OrderDetailDAO" class="dao.OrderDetailDAO" /> <%-- Move this outside the loop --%>
                <jsp:useBean id="ProductDAO" class="dao.ProductDAO" /> <%-- Move this outside the loop --%>

                <c:set var="order" value="${orderDAO.getOrderById(orderIdParam)}" />
                <c:if test="${not empty order}">
                    <div class="container">
                        <div class="logo"> <img src="./images/BeluIcon.png" alt="Logo"></div>
                        <h1><strong>BeluStyle</strong></h1>
                        <h2>Order Invoice</h2>
                        <p><strong>Order ID:</strong> ${order.orderID}</p>
                        <p><strong>Order Date:</strong> <fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm" /></p>
                        <p><strong>Customer:</strong> ${orderDAO.getFullnameOrder(order.username)}</p> 

                        <table border="1">
                            <thead>
                                <tr>
                                    <th>Product</th>
                                    <th>Quantity</th>
                                    <th>Price</th>
                                    <th>Total</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="grandTotal" value="0" /> 
                                <c:forEach var="orderDetail" items="${OrderDetailDAO.getOrderDetailsByOrderId(orderIdParam)}">
                                    <tr>
                                        <td>${ProductDAO.getProductNameById(orderDetail.productID)}</td>
                                        <td>${orderDetail.quantity}</td>
                                        <td><fmt:formatNumber value="${orderDetail.price}" type="currency" currencySymbol="VND " /></td> <%-- Format price --%>
                                        <td><fmt:formatNumber value="${orderDetail.quantity * orderDetail.price}" type="currency" currencySymbol="VND " /></td> <%-- Format total --%>
                                        <c:set var="grandTotal" value="${grandTotal + orderDetail.quantity * orderDetail.price}" />
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                        <div class="total">
                            <strong>Grand Total:</strong> <fmt:formatNumber value="${grandTotal}" type="currency" currencySymbol="VND " /> <%-- Format grand total --%>
                        </div>
                    </div>
                </c:if>
            </c:when>
        </c:choose>
    </body>
</html>
