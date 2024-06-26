
<%@page import="model.Cart"%>
<%@page import="java.util.List"%>
<%@page import="dao.ProductDAOImpl"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.NumberFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@page import="dao.UserDAOImpl"%>
<%@page import="model.User"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Giỏ hàng</title>
        <link rel="stylesheet" href="css/cart.css" />
        <link rel="stylesheet" href="css/menu.css" />
        <link rel="stylesheet" href="css/main.css" />
        <link rel="stylesheet" href="css/bootstrap.min.css" />
    </head>
    <body>
        <%
            String username = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("username")) {
                        username = cookie.getValue();
                    }
                }
            }
        %>
        <div id="main">
            <div id="head">
                <%@include file="banner.jsp" %>
            </div>
            <div id="head-link">
                <div id='menungang'>
                    <ul>
                        <li class='last'><a href="index.jsp"><span>Trang
                                    chủ</span></a></li>
                        <li class='last'><a href="product.jsp"><span>Sản phẩm</span></a></li>
                        <li class='last'><a href="cart.jsp"><span>Giỏ hàng</span></a></li>
                        <li class='last'><a href="search_page.jsp"><span>Tìm kiếm</span></a></li>
                        <li class='last' style="float: right;"><a href="login.jsp"><span>Đăng
                                    xuất</span></a></li>
                        <li class='last' style="float: right;"><a href="update_user.jsp?username=<%=username%>"><span><%= username%></span></a></li>			
                    </ul>
                </div>
            </div>
            <div class="container mt-5">
                <h1>Order History</h1>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Order ID</th>
                            <th>Order Date</th>
                            <th>Product ID</th>
                            <th>Product Name</th>
                            <th>Quantity</th>
                            <th>Price</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="order" items="${orderHistoryList}">
                        <tr>
                            <td>${order.orderId}</td>
                            <td>${order.orderDate}</td>
                            <td>${order.productId}</td>
                            <td>${order.productName}</td>
                            <td>${order.quantity}</td>
                            <td>${order.price}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div id="footer"><jsp:include page="footer.jsp"></jsp:include></div>
        </div>
    </body>
</html>