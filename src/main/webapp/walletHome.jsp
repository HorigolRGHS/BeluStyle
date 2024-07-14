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
    </head>
    <body>

        <div id="main">
            <div id="head">
                <%@include file="banner.jsp" %>
            </div>
            <%@include file="navbar.jsp" %>

            <div id="content">
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
                <p>Wallet: <%= String.format("%,.2f", balance)%>  VND</p>
            </div>
            <div id="footer"><jsp:include page="footer.jsp"></jsp:include></div>
        </div>
    </body>
</html>
