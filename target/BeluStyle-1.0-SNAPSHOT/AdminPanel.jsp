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
        <div class="container">
            <jsp:include page="mainmenu.jsp" />
            <div class="main">
                <div class="topbar">
                    <div class="toggle">
                        <ion-icon name="menu-outline"></ion-icon>
                    </div>
                    <div class="search">
                        <label>
                            <input type="text" placeholder="Search here">
                            <ion-icon name="search-outline"></ion-icon>
                        </label>
                    </div>
                    <div class="user">
                        <img src="assets/imgs/customer01.jpg" alt="">
                    </div>
                </div>
                <div id="dashboard" class="section">
                    <%@ include file="dashboardSection.jsp" %> 
                </div>
                <div id="products" class="section" style="display: none;">
                    <%@ include file="productSection.jsp" %> 
                </div>
                <div id="orders" class="section" style="display: none;">
                </div>
                <div id="wallet" class="section" style="display: none;">
                    <%@ include file="walletSection.jsp" %>
                </div>
            </div>
        </div>
        <jsp:include page="dialog.jsp" />
        <script src="./js/main.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
        <script>
            // Function to switch between sections
            function showSection(sectionId) {
                // Save current section to localStorage
                localStorage.setItem('currentSection', sectionId);

                document.querySelectorAll('.section').forEach(section => {
                    section.style.display = 'none';
                });
                document.getElementById(sectionId).style.display = 'block';
            }

            let list = document.querySelectorAll(".navigation li");

            function activeLink() {
                list.forEach((item) => {
                    item.classList.remove("hovered");
                });
                this.classList.add("hovered");

                // Save active link index to localStorage
                const index = Array.from(list).indexOf(this);
                localStorage.setItem('activeLinkIndex', index);
            }

            // Add event listener for each list item
            list.forEach((item) => item.addEventListener("mouseover", activeLink));

            const menuToggle = document.querySelector('.toggle');
            const navigation = document.querySelector('.navigation');
            const main = document.querySelector('.main');

            menuToggle.addEventListener('click', () => {
                navigation.classList.toggle('active');
                main.classList.toggle('active');

                // Save navigation and main state to localStorage
                localStorage.setItem('navigationActive', navigation.classList.contains('active'));
                localStorage.setItem('mainActive', main.classList.contains('active'));
            });

            // Restore the state from localStorage on page load
            document.addEventListener('DOMContentLoaded', () => {
                // Restore the current section
                const currentSection = localStorage.getItem('currentSection');
                if (currentSection) {
                    showSection(currentSection);
                } else {
                    showSection('dashboard'); // Default section
                }

                // Restore active link
                const activeLinkIndex = localStorage.getItem('activeLinkIndex');
                if (activeLinkIndex !== null) {
                    list[activeLinkIndex].classList.add('hovered');
                }

                // Restore navigation and main state
                const navigationActive = localStorage.getItem('navigationActive') === 'true';
                const mainActive = localStorage.getItem('mainActive') === 'true';

                // Apply classes with transitions after initial setup
                setTimeout(() => {
                    if (navigationActive) {
                        navigation.classList.add('active');
                    }
                    if (mainActive) {
                        main.classList.add('active');
                    }
                    navigation.style.display = ''; // Restore default display property
                }, 100); // Delay to allow initial load without triggering animations
            });

            document.getElementById('fileInput').addEventListener('change', function () {
                var fileName = this.files[0].name;
                document.getElementById('fileName').textContent = fileName;
            });
            // Remove hash on page load
            removeHash();

            // Remove hash on hash change
            window.addEventListener('hashchange', removeHash, false);

            function removeHash() {
                if (window.location.hash) {
                    let cleanURL = window.location.href.split('#')[0];
                    history.replaceState(null, null, cleanURL);
                }
            }
        </script>
    </body>
</html>
