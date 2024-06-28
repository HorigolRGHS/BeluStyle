<%-- 
    Document   : mainmenu
    Created on : Jun 20, 2024, 6:31:12 PM
    Author     : Duong Nhat Anh CE181079
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="./css/mainmenu.css"/>
    </head>
    <body>
        <div class="navigation">
            <ul>
                <li>
                    <a href="#">
                        <span class="icon"><img style="width: 50px;  height: 50px" src="images/BeluIcon.png" alt="Logo"/></span>
                        <span class="title">BeluStyle</span>
                    </a>
                </li>

                <li>
                    <a href="#" onclick="showSection('dashboard')">
                        <span class="icon"><ion-icon name="home-outline"></ion-icon></span>
                        <span class="title">Dashboard</span>
                    </a>
                </li>

                <li>
                    <a href="#" onclick="showSection('products')">
                        <span class="icon"><ion-icon name="file-tray-stacked-outline"></ion-icon></span>
                        <span class="title">Products</span>
                    </a>
                </li>

                <li>
                    <a href="#" onclick="showSection('orders')">
                        <span class="icon"><ion-icon name="file-tray-full-outline"></ion-icon></span>
                        <span class="title">Orders</span>
                    </a>
                </li>

                <li>
                    <a href="#" onclick="showSection('wallet')"> 
                        <span class="icon"><ion-icon name="wallet-outline"></ion-icon></span>
                        <span class="title">Wallet</span>
                    </a>
                </li>

                <li>
                    <a href="LogoutServlet">
                        <span class="icon"><ion-icon name="log-out-outline"></ion-icon></span>
                        <span class="title">Sign Out</span>
                    </a>
                </li>
            </ul>
        </div>
    </body>
</html>
