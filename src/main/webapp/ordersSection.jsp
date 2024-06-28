<%@page import="model.Order"%>
<%@page import="dao.OrderDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>

        <div class="productBox">
            <div class="cardHeader">
                <h2>All Orders</h2>
            </div>

            <table class="styled-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Full Name</th>
                        <th>Order Date</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <jsp:useBean id="orderDAO" class="dao.OrderDAO" />
                    <c:set var="ordersList" value="${orderDAO.getAllOrders()}" />

                    <c:choose>
                        <c:when test="${not empty ordersList}">
                            <c:forEach items="${ordersList}" var="order">
                                <tr>
                                    <td>${order.orderID}</td>
                                    <td>${orderDAO.getFullnameOrder(order.username)}</td>
                                    <td>${order.orderDate}</td>
                                    <td>
                                        <a href="InvoiceOrder?orderId=${order.orderID}" class="btn btn-edit">
                                            <ion-icon name="arrow-forward-circle-outline"></ion-icon>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="4">No orders available.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </body>
</html>