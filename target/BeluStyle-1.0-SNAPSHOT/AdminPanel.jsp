<%-- 
    Document   : AdminPanel
    Created on : Jun 11, 2024, 10:17:41 AM
    Author     : Duong Nhat Anh CE181079
--%>

<%@page import="model.Brand"%>
<%@page import="model.Category"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>BeluStyle - Admin</title>
        <link rel="stylesheet" href="./css/dashboard.css">
        <link rel="stylesheet" href="./css/dialog.css"/>
        <link rel="stylesheet" href="./css/DashboardContext.css" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    </head>
    <body>
        <div id="preloader">
            <div class="spinner"></div>
        </div>
        <div class="container">
            <jsp:include page="mainmenu.jsp" />
            <div class="main">
                <div class="topbar">
                    <div class="toggle">
                        <ion-icon name="menu-outline"></ion-icon>
                    </div>
                    <div class="search">
                        <label>
                            <input type="text" placeholder="Comming soon">
                            <ion-icon name="search-outline"></ion-icon>
                        </label>
                    </div>
                    <div class="user">
                        <a href="/BeluStyle"><img src="./images/profile.jpg" alt="Profile Img"/></a>
                    </div>
                </div>
                <div id="dashboard" class="section">
                    <%@ include file="dashboardSection.jsp" %> 
                </div>
                <div id="products" class="section" style="display: none;">
                    <%@ include file="productSection.jsp" %> 
                </div>
                <div id="orders" class="section" style="display: none;">
                    <%@include file="ordersSection.jsp" %>
                </div>
                <div id="wallet" class="section" style="display: none;">
                    <%@ include file="walletSection.jsp" %>
                </div>
            </div>
        </div>
        <jsp:include page="dialog.jsp" />
        <script src="./js/main.js"></script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const preloader = document.getElementById('preloader');
                preloader.style.display = 'none';
            });
        </script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
    </body>
</html>
