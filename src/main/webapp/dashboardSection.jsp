<%-- 
    Document   : dashboardSection
    Created on : Jun 20, 2024, 6:27:06 PM
    Author     : Duong Nhat Anh CE181079
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="dao.OrderDetailDAO"%>
<%@page import="model.OrderDetail"%>
<%@page import="model.Order"%>
<%@page import="java.util.List"%>
<%@page import="dao.OrderDAO"%>
<%@page import="dao.UserDAO"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                    <%
                        double balance = 0.0;

                        Cookie[] arr = request.getCookies();

                        if (arr != null) {
                            for (Cookie o : arr) {
                                if (o.getName().equals("username")) {
                                    String username = o.getValue();
                                    balance = userDAO.getWallet(username);
                                }
                            }
                        }
                    %>
                    <div class="numbers"><%= String.format("%,.2f", balance)%>  VND</div>
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
                    <a href="#" class="btn" onclick="showSection('orders')" >View All</a>
                </div>
                <table>
                    <thead>
                        <tr>
                            <td>OrderID</td>
                            <td>Total</td>
                            <td>Order Date</td>
                            <td>Status</td>
                        </tr>
                    </thead>
                    <%
                        OrderDAO orDAO = new OrderDAO();
                        OrderDetailDAO orDDao = new OrderDetailDAO();
                        List<Order> recentOrders = orDAO.getRecentOrders(8);
                        System.out.println("RecentOrder:" + recentOrders.size());
                    %>
                    <tbody>
                            <% for (Order order : recentOrders) {%>
                        <tr>
                            <td><%= order.getOrderID()%></td>
                            <td><fmt:formatNumber value="<%= orDDao.calTotalOrder(order.getOrderID())%>" type="number" maxFractionDigits="0"/></td>
                            <%
                                SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
                                String formattedDate = fmt.format(order.getOrderDate());
                            %>
                            <td><%= formattedDate %></td>
                            <td><span class="status <%= orDAO.getOrderStatus(order.getOrderID())%>"><%= orDAO.getOrderStatusText(order.getOrderID())%></span></td>  
                        </tr>
                        <% }%> 
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
