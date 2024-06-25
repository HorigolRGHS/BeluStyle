<%-- 
    Document   : walletSection
    Created on : Jun 20, 2024, 6:30:15 PM
    Author     : Duong Nhat Anh CE181079
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="./css/wallet.css"/>
    </head>
    <body>
        <div class="image-container">
            <h2>Wallet Balance</h2>
            <%
                Object walletObj = session.getAttribute("Wallet");
                double balance = 0.0; // Default value
                if (walletObj != null && walletObj instanceof Double) {
                    balance = (Double) walletObj;
                }
            %>
            <h1>$<%= balance %></h1> <!-- Thay đổi từ <h2> thành <h1> -->
            <img src="./images/bw.jpg" alt="">
        </div>
    </body>
</html>
