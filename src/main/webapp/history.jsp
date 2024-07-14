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
        <link rel="stylesheet" href="css/history.css" />
        <link rel="stylesheet" href="css/bootstrap.min.css" />


    </head>
    <body>

        <div id="main">
            <div id="head">
                <%@include file="banner.jsp" %>
            </div>

            <%@include file="navbar.jsp" %>
            <div class="Order-History-container">
                <div class="order-history-container">
                    <h1>Order History</h1>
                </div>
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
                            <div class="product-item">
                                <div class="product-image">
                                    <img src="./images/product/<%=product.getImage()%>" alt="Product's image"/>
                                </div>
                                <div class="product-info">
                                    <div class="row d-flex">
                                        <p><%=product.getName()%></p>
                                    </div>
                                    <div class="row d-flex">
                                        <p class="text-muted">Quantity: <%=detail.getQuantity()%></p>
                                    </div>
                                    <div class="col-3 d-flex justify-content-end">
                                        <p>Price: <%=nf.format(detail.getPrice())%> VNĐ</p>
                                    </div>
                                </div>
                            </div>
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

            <div id="footer" style="width: 1060px;"><jsp:include page="footer.jsp"></jsp:include></div>
        </div>
    </body>
</html>





