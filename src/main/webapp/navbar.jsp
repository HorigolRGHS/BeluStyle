<%-- 
    Document   : nav-bar
    Created on : Jun 27, 2024, 10:19:00 PM
    Author     : Gura
--%>

<%@page import="model.Product"%>
<%@page import="dao.UserDAO"%>
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
                <li class='last'><a href="home.jsp"><span>Home</span></a></li>
                <li class='last'><a href="product"><span>Product</span></a></li>
                <li class='last'><a href="search"><span>Search</span></a></li>
                    <% if (username != null) {
                    %>
                <li class='last'><a href="cart"><span>Cart</span></a></li>
                <li class='last'><a href="walletHome.jsp"><span>Wallet</span></a></li>
                    <%
                        UserDAO userDAO = new UserDAO();
                        if (userDAO.getUser(username).getRole().equalsIgnoreCase("admin")) {
                    %>
                <li class='last' style="float: right;"><a href="AdminPanel"><span>Admin Panel</span></a></li>
                            <%
                                }
                            %>
                <li class='last' style="float: right;"><a href="update-user?username=<%=username%>"><span><%=username%></span></a></li>
                <li class='last' style="float: right;"><a href="LogoutServlet"><span>Log out</span></a></li>
                    <% } else { %>
                <li class='last' style="float: right;"><a href="login"><span>Sign in</span></a></li>
                <li class='last' style="float: right;"><a href="register"><span>Sign up</span></a></li>
                    <% }%>
            </ul>
        </div>
    </div>

</html>
