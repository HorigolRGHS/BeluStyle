<%-- 
    Document   : walletHome
    Created on : Jul 14, 2024, 4:03:50 PM
    Author     : Duong Nhat Anh CE181079
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- title of site -->
        <title>BeluStyle</title>

        <!-- For favicon png -->
        <link
            rel="shortcut icon"
            type="image/icon"
            href="Banner/Beluicon.png"
            />
        <link rel="stylesheet" href="css/style.css" />
        <link rel="stylesheet" href="css/menu.css" />
        <link rel="stylesheet" href="css/product.css" />
        <link rel="stylesheet" href="./css/wallet2.css"/>
    </head>
    <body>

        <div id="main">
            <div id="head">
                <%@include file="banner.jsp" %>
            </div>
            <%@include file="navbar.jsp" %>

            <div class="image-container">
                <%
                    double balance = 0.0;
                    UserDAO uDAO = new UserDAO();

                    Cookie[] arr = request.getCookies();

                    if (arr != null) {
                        for (Cookie o : arr) {
                            if (o.getName().equals("username")) {
                                String usern = o.getValue();
                                balance = uDAO.getWallet(username);
                            }
                        }
                    }
                %>
                <h2>Wallet Balance</h2>
                <h1><span class="balance"><%= String.format("%,.2f", balance)%></span> <span class="currency">VND</span></h1>
                <img src="./images/wallHome.jpg" alt="">
            </div>
            <div id="footer"><jsp:include page="footer.jsp"></jsp:include></div>
        </div>
    </body>
</html>
