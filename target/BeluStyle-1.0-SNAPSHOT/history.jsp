<%@page import="java.text.SimpleDateFormat"%>
<%@page import="model.Order"%>
<%@page import="dao.OrderDetailDAO"%>
<%@page import="dao.OrderDAO"%>
<%@page import="model.OrderDetail"%>
<%@page import="model.Cart"%>
<%@page import="java.util.List"%>
<%@page import="dao.ProductDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.NumberFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@page import="dao.UserDAO"%>
<%@page import="model.User"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History</title>

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
        
         <style>
        

         

            .order-container {
                background-color: #fff;
                border: 1px solid #ddd;
                margin-bottom: 20px;
                padding: 15px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1); /* Subtle shadow */
            }

            .order-header {
                border-bottom: 1px solid #ddd;
                padding-bottom: 10px;
                margin-bottom: 10px;
            }

            .order-header strong {
                font-weight: bold;
            }

            .order-details ul {
                list-style-type: none;
                padding: 0;
            }

            .order-details ul li {
                margin-bottom: 10px;
            }

            .order-details ul li img {
                max-width: 100px;
                max-height: 100px;
                border: 1px solid #ddd;
                margin-right: 15px;
                float: left;
            }

            .order-details ul li .product-details {
                margin-left: 115px; /* Adjust based on image size */
            }

            .order-details ul li .product-price {
                color: #dc3545; /* Red color for price */
            }
        </style>
    </head>
    <body>

        <div id="main">
            <div id="head">
                <%@include file="banner.jsp" %>
            </div>

            <%@include file="navbar.jsp" %>
            <div class="container mt-5">
                <h1>Order History</h1>
                <%
                    OrderDAO orderDAO = new OrderDAO();
                    OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
                    ProductDAO productDAO = new ProductDAO();
                    List<Order> orders = orderDAO.getOrdersByUsername(username);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy 'at' hh:mm:ss a");
                    NumberFormat nf = NumberFormat.getInstance();
                    nf.setMinimumFractionDigits(0);

                    for (Order order : orders) {
                        List<OrderDetail> orderDetails = orderDetailDAO.getOrderDetailsListByOrderID(order.getOrderID());
                %>
                <div class="order-container">
                    <div class="order-header">
                        <strong>Order ID:</strong> <%=order.getOrderID()%>
                        <br>
                        <strong>Order Date:</strong> <%=sdf.format(order.getOrderDate())%>
                    </div>
                    <div class="order-details">
                        <ul>
                            <%
                                for (OrderDetail detail : orderDetails) {
                                    Product product = productDAO.getProductbyId(detail.getProductID());
                            %>
                            <li>
                                <img src="./images/product/<%=product.getImage()%>" alt="Product's image"/>
                            </li>
                            <li>
                                <%=product.getName()%> - Quantity: <%=detail.getQuantity()%> - Price: <%=nf.format(detail.getPrice())%> VNĐ
                            </li>
                            <%
                                }
                            %>
                        </ul>
                    </div>
                </div>
                <%
                    }
                %>
            </div>
            
            <div id="footer"><jsp:include page="footer.jsp"></jsp:include></div>
        </div>
    </body>
</html>





