<%-- 
    Document   : home
    Created on : Jul 1, 2024, 9:45:06 AM
    Author     : Guraa
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

        <link rel="stylesheet" href="./homeCss/bootstrap.min.css" />

        <link rel="stylesheet" href="./homeCss/style.css" />
    </head>
    <body>
        <%
            String username = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("username")) {
                        username = cookie.getValue();
                        break;
                    }
                }
            }
        %>

        <section id="home" class="welcome-hero" style="background: url('Banner/BeluStyleBan.png') no-repeat; background-position: center; background-size: cover;">
            <!-- top-area Start -->
            <div class="top-area">
                <div class="header-area">
                    <!-- Start Navigation -->
                    <nav class="navbar navbar-default bootsnav navbar-sticky navbar-scrollspy" data-minus-value-desktop="70" data-minus-value-mobile="55" data-speed="1000">
                        <div class="container">
                            <!-- Start Header Navigation -->
                            <div class="navbar-header">
                                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-menu">
                                    <i class="fa fa-bars"></i>
                                </button>
                                <a class="navbar-brand" href="home.jsp">BeluStyle<span></span></a>
                            </div>
                            <!-- End Header Navigation -->

                            <!-- Collect the nav links, forms, and other content for toggling -->
                            <div class="collapse navbar-collapse menu-ui-design" id="navbar-menu">
                                <ul class="nav navbar-nav navbar-right" data-in="fadeInDown" data-out="fadeOutUp">
                                    <li class='last'><a href="index"><span>Home</span></a></li>
                                    <li class='last'><a href="product"><span>Product</span></a></li>
                                    <li class='last'><a href="search"><span>Search</span></a></li>

                                    <% if (username != null) {%>
                                    <li class='last'><a href="wallet"><span>Wallet</span></a></li>
                                    <li class='last'><a href="cart"><span>Cart</span></a></li>
                                    <li class='last' style="float: right;"><a href="LogoutServlet"><span>Log out</span></a></li>
                                    <li class='last' style="float: right;"><a href="update?username=<%=username%>"><span><%=username%></span></a></li>
                                                <% } else { %>
                                    <li class='last' style="float: right;"><a href="register"><span>Sign up</span></a></li>
                                    <li class='last' style="float: right;"><a href="login"><span>Sign in</span></a></li>
                                        <% }%>
                                </ul>
                            </div>
                        </div>
                    </nav>
                    <!-- End Navigation -->
                </div>
                <!--/.header-area-->
                <div class="clearfix"></div>
            </div>
            <div class="container_footer">
                <div class="welcome-hero-txt">
                    <h2>Discover your style at BeluStyle.</h2>
                    <p>
                        Transform your wardrobe with the latest trends from BeluStyle, where
                        fashion meets affordability and style is just a click away.
                    </p>
                    <button class="welcome-btn" onclick="window.location.href = 'index'">
                        Shop now
                    </button>
                </div>
            </div>
        </section>
        <div id="footer"><jsp:include page="footer.jsp"></jsp:include></div>
    </body>
</html>
