<%-- 
    Document   : nav-bar
    Created on : Jun 27, 2024, 10:19:00 PM
    Author     : Le Khac Huy - CE180311
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Navigation bar</title>
    </head>
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

    <div id="head-link">
        <div id='menungang'>
            <ul>
                <li class='last'><a href="index"><span>Trang chủ</span></a></li>
                <li class='last'><a href="product"><span>Sản phẩm</span></a></li>
                <li class='last'><a href="search"><span>Tìm kiếm</span></a></li>
                    <%
                        if (username != null) {
                    %>
                <li class='last'><a href="cart"><span>Giỏ hàng</span></a></li>
                <li class='last' style="float: right;"><a href="LogoutServlet"><span>Đăng xuất</span></a></li>
                <li class='last' style="float: right;"><a href="update-user?username=<%=username%>"><span><%=username%></span></a></li>
                            <%
                            } else {
                            %>
                <li class='last' style="float: right;"><a href="register"><span>Đăng ký</span></a></li>
                <li class='last' style="float: right;"><a href="login"><span>Đăng nhập</span></a></li>
                    <%
                        }
                    %>
            </ul>
        </div>
    </div>

</html>
