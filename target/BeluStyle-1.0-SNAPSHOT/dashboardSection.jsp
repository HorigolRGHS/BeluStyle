<%-- 
    Document   : dashboardSection
    Created on : Jun 20, 2024, 6:27:06 PM
    Author     : Duong Nhat Anh CE181079
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <jsp:useBean id="userDAO" class="dao.UserDAO" /> 
        <jsp:useBean id="ProductDAO" class="dao.ProductDAO" /> 
        <jsp:useBean id="OrderDAO" class="dao.OrderDAO" /> 
        <div class="cardBox">
            <div class="card">
                <div>
                    <c:set var="NOO" value="${OrderDAO.getCountOrder()}" />
                    <div class="numbers">${NOO}</div>
                    <div class="cardName">Orders</div>
                </div>
                <div class="iconBx">
                    <ion-icon name="eye-outline"></ion-icon>
                </div>
            </div>
            <div class="card">
                <div>
                    <c:set var="NOP" value="${ProductDAO.getCountProduct()}" />
                    <div class="numbers">${NOP}</div>
                    <div class="cardName">Products</div>
                </div>
                <div class="iconBx">
                    <ion-icon name="cube-outline"></ion-icon>
                </div>
            </div>
            <div class="card">
                <div>
                    <div class="numbers">Infinity VND</div>
                    <div class="cardName">Wallet</div>
                </div>
                <div class="iconBx">
                    <ion-icon name="wallet-outline"></ion-icon>
                </div>
            </div>
        </div>
        <div class="details">
            <div class="recentOrders">
                <div class="cardHeader">
                    <h2>Recent Orders</h2>
                    <a href="#" class="btn">View All</a>
                </div>
                <table>
                    <thead>
                        <tr>
                            <td>Name</td>
                            <td>Price</td>
                            <td>Payment</td>
                            <td>Status</td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Star Refrigerator</td>
                            <td>$1200</td>
                            <td>Paid</td>
                            <td><span class="status delivered">Delivered</span></td>
                        </tr>
                        <tr>
                            <td>Dell Laptop</td>
                            <td>$110</td>
                            <td>Due</td>
                            <td><span class="status pending">Pending</span></td>
                        </tr>
                        <tr>
                            <td>Apple Watch</td>
                            <td>$1200</td>
                            <td>Paid</td>
                            <td><span class="status return">Return</span></td>
                        </tr>
                        <tr>
                            <td>Addidas Shoes</td>
                            <td>$620</td>
                            <td>Due</td>
                            <td><span class="status inProgress">In Progress</span></td>
                        </tr>
                        <tr>
                            <td>Star Refrigerator</td>
                            <td>$1200</td>
                            <td>Paid</td>
                            <td><span class="status delivered">Delivered</span></td>
                        </tr>
                        <tr>
                            <td>Dell Laptop</td>
                            <td>$110</td>
                            <td>Due</td>
                            <td><span class="status pending">Pending</span></td>
                        </tr>
                        <tr>
                            <td>Apple Watch</td>
                            <td>$1200</td>
                            <td>Paid</td>
                            <td><span class="status return">Return</span></td>
                        </tr>
                        <tr>
                            <td>Addidas Shoes</td>
                            <td>$620</td>
                            <td>Due</td>
                            <td><span class="status inProgress">In Progress</span></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="recentCustomers">
                <div class="cardHeader">
                    <h2>Recent Customers</h2>
                </div>
                <table>
                    <c:forEach items="${userDAO.allFullnameUser}" var="user" varStatus="loop">
                        <c:if test="${loop.index < 7}">
                            <tr>
                                <td width="60px">
                                    <div class="imgBx">
                                        <img src="./images/profile.jpg" alt="Profile Image">
                                    </div>
                                </td>
                                <td>
                                    <h4>${user}</h4> 
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </table>
            </div>
        </div>
</body>
</html>
