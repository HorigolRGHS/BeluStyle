<%@page import="model.User"%>
<%@page import="model.Cart"%>
<%@page import="java.util.List"%>
<%@page import="dao.ProductDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.NumberFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart</title>
        <!-- title of site -->
        <title> BeluStyle</title>

        <!-- For favicon png -->
        <link
            rel="shortcut icon"
            type="image/icon"
            href="Banner/Beluicon.png"
            />
        <link rel="stylesheet" href="css/cart.css" />
        <link rel="stylesheet" href="css/menu.css" />
        <link rel="stylesheet" href="css/main.css" />
        <link rel="stylesheet" href="css/bootstrap.min.css" />
        <style type="text/css">
            #main {
                width: 1060px;
                padding: 0;
                margin-left: auto;
                margin-right: auto;
            }

            #head {
                height: 200px;
                background-color: #F5F5F5;
                border: 1px solid #CDCDCD;
                margin-bottom: 5px;
                margin-top: 5px;
            }

            #head-link {
                height: 50px;
                line-height: 30px;
                border: 1px solid #CDCDCD;
                background-color: #F5F5F5;
                margin-bottom: 5px;
                clear: both;
            }

            #content {
                width: 1060px;
                min-height: 430px;
                border: 1px solid #CDCDCD;
                float: left;
                margin-bottom: 5px;
                clear: both;
            }

            #footer {
                height: 50px;
                clear: both;
                border: 1px solid #CDCDCD;
                background-color: #F8F8FF;
                margin-bottom: 5px;
            }
        </style>
    </head>
    <body>

        <div id="main">
            <div id="head">
                <%@include file="banner.jsp" %>
            </div>

            <%@include file="navbar.jsp" %>

            <div id="content">


                <div class="shopping-cart">

                    <div class="column-labels">
                        <label class="product-image">Hình ảnh</label> <label
                            class="product-details">Product</label> <label
                            class="product-price">Giá bán</label> <label
                            class="product-quantity">Số lượng</label><label
                            class="product-line-price">Tổng tiền</label>
                    </div>
                    <%
                        ProductDAO productDAO = new ProductDAO();

                        NumberFormat nf = NumberFormat.getInstance();
                        nf.setMinimumIntegerDigits(0);
                        double total = 0;
                        ArrayList<Cart> cart = null;
                        if (session.getAttribute("cart") != null) {
                            cart = (ArrayList<Cart>) session
                                    .getAttribute("cart");
                        }

                        UserDAO userDAO = new UserDAO();
                        User user = userDAO.getUser(username);
                        double walletBalance = user.getWallet();
                    %>
                    <%
                        if (cart != null && username != null) {
                            for (Cart c : cart) {
                                if (c.getUsername().equals(username)) {
                                    total = total
                                            + (c.getQuantity() * productDAO.getProductbyId(
                                            c.getP().getProductID()).getPrice());
                    %>
                    <div class="product">
                        <div class="product-image">
                            <img
                                src="images/product/<%=productDAO.getProductbyId(c.getP().getProductID())
                                        .getImage()%>">
                        </div>
                        <div class="product-details">
                            <div class="product-title">
                                <%=productDAO.getProductbyId(c.getP().getProductID()).getName()%>
                            </div>
                            <p class="product-description"></p>
                        </div>
                        <div class="product-price"><%=nf.format(productDAO.getProductbyId(
                                c.getP().getProductID()).getPrice())%>
                            VNĐ
                        </div>
                        <div class="product-quantity cart_quantity_button">
                            <a class="cart_quantity_up" href="cart?command=deleteCart&productId=<%=c.getP().getProductID()%>"> - </a>
                            <input class="cart_quantity_input" type="number" value="<%=c.getQuantity()%>" disabled="disabled">
                            <a class="cart_quantity_up" href="cart?command=addCart&productId=<%=c.getP().getProductID()%>"> + </a>
                        </div>
                        <div class="product-line-price" style="text-align: right"><%=nf.format(productDAO.getProductbyId(
                                c.getP().getProductID()).getPrice()
                                * c.getQuantity())%>
                            VNĐ

                            <a
                                href="cart?command=removeCart&productId=<%=c.getP().getProductID()%>"><img style="margin-left: 10px"
                                                                                                       src="images/delete.png"></a>
                        </div>

                    </div>
                    <%
                                }
                            }
                        }
                    %>
                    <div class="totals">
                        <div class="totals-item">
                            <label>Tổng hóa đơn</label>
                            <div class="totals-value" id="cart-subtotal"><%=nf.format(total)%>
                                VNĐ
                            </div>
                        </div>
                    </div>
                    <%if (cart != null && cart.size() > 0) {%>
                    <a class="checkout" href="history" style="text-decoration: none;">Lịch sử</a>
                    <%
                        if (total > walletBalance) {
                    %>
                    <p class="checkout" style="color:red;" disabled>Your wallet is not enough</p>

                    <%
                    } else {
                    %>
                    <a class="checkout" href="ConfirmServlet?username=<%=username%>" style="text-decoration: none;">Thanh toán</a>
                    <%
                        }
                    %>
                    <% } else { %>
                    <a class="checkout" href="history" style="text-decoration: none;">Lịch sử</a>
                    <a class="checkout" href="product" style="text-decoration: none;">Thanh toán</a>
                    <% }%>
                </div>

            </div>
            <div id="footer"><jsp:include page="footer.jsp"></jsp:include></div>
        </div>
    </body>
</html>