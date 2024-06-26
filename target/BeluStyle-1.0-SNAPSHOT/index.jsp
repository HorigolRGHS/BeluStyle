<%@page import="dao.BrandDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Product"%>
<%@page import="java.util.List"%>
<%@page import="dao.ProductDAOImpl"%>
<%@page import="java.text.NumberFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trang chủ</title>
        <link rel="stylesheet" href="css/style.css" />
        <link rel="stylesheet" href="css/menu.css" />
        <link rel="stylesheet" href="css/product.css" />
    </head>
    <body>

        <div id="main">
            <div id="head">
                <%@include file="banner.jsp" %>
            </div>

            <%
                String username = null;
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("username")) {
                            username = cookie.getValue();
                        }
                    }
                }

                if (username != null) {
            %>
            <div id="head-link">
                <div id='menungang'>
                    <ul>
                        <li class='last'><a href="index.jsp"><span>Trang
                                    chủ</span></a></li>
                        <li class='last'><a href="product.jsp"><span>Sản phẩm</span></a></li>
                        <li class='last'><a href="cart.jsp"><span>Giỏ hàng</span></a></li>
                        <li class='last'><a href="search_page.jsp"><span>Tìm kiếm</span></a></li>
                        <li class='last' style="float: right;"><a href="LogoutServlet"><span>Đăng
                                    xuất</span></a></li>
                        <li class='last' style="float: right;"><a href="update_user.jsp?username=<%=username%>"><span><%=username%></span></a></li>
                    </ul>
                </div>
            </div>
            <%
            } else {
            %>
            <div id="head-link">
                <div id='menungang'>
                    <ul>
                        <li class='last'><a href="index.jsp"><span>Trang
                                    chủ</span></a></li>
                        <li class='last'><a href="product.jsp"><span>Sản phẩm</span></a></li>
                        <li class='last'><a href="search_page.jsp"><span>Tìm kiếm</span></a></li>
                        <li class='last' style="float: right;"><a href="register.jsp"><span>Đăng
                                    ký</span></a></li>
                        <li class='last' style="float: right;"><a href="login.jsp"><span>Đăng
                                    nhập</span></a></li>
                    </ul>
                </div>
            </div>
            <%
                }
            %>
            <div id="content">
                <div id="left"><jsp:include page="category.jsp"></jsp:include></div>
                    <div id="right">
                    <%
                        ProductDAOImpl productDAO = new ProductDAOImpl();
                        List<Product> list = new ArrayList<Product>();
                        list = productDAO.getList();
                        String categoryID = null;
                        if (request.getParameter("categoryID") != null) {
                            categoryID = request.getParameter("categoryID");
                        }
                        NumberFormat nf = NumberFormat.getInstance();
                        nf.setMinimumFractionDigits(0);
                    %>
                    <div id="site-wrapper" style="float: left">
                        <ul class="products homepage">
                            <%
                                if (categoryID != null) {
                                    for (Product p : productDAO.getListByCategory(Integer
                                            .parseInt(categoryID))) {
                            %>

                            <li class="preorder"><span class="tagimg "> </span> <a
                                    href="detail.jsp?productID=<%=p.getProductID()%>"> <img
                                        src="images/product/<%=p.getImage()%>" width=" 250px" height="250px" />
                                    <h3><%=p.getName()%></h3>
                                    <h4>
                                        Giá:
                                        <%=nf.format(p.getPrice())%>
                                        VNĐ
                                    </h4> <span class="textkm">Stock quantity: <strong> <%= (p.getQuantity() == 0) ? "Sold out" : p.getQuantity() %> </strong>
                                    </span>
                                    <p class="info">
                                        <span>Hãng sx: <%= BrandDAO.getBrand(p.getBrandID()).getName()%>
                                        </span> <span>Giá: <%=nf.format(p.getPrice())%> VNĐ
                                        </span> <span>Thông tin: <%=p.getDescription()%>
                                    </p>
                                </a></li>

                            <%
                                }
                            } else {
                                for (Product p : productDAO.getList()) {
                            %>

                            <li class="preorder"><span class="tagimg "></span> <a
                                    href="detail.jsp?productID=<%=p.getProductID()%>"> <img
                                        src="images/product/<%=p.getImage()%>" width=" 250px" height="250px" />
                                    <h3><%=p.getName()%></h3>
                                    <h4>
                                        Giá:
                                        <%=nf.format(p.getPrice())%>
                                        VNĐ
                                    </h4> <span class="textkm">Stock quantity: <strong> <%= (p.getQuantity() == 0) ? "Sold out" : p.getQuantity() %> </strong>
                                    </span>
                                    <p class="info">
                                        <span>Hãng sx: <%= BrandDAO.getBrand(p.getBrandID()).getName()%></span> <span>Giá:
                                            <%=nf.format(p.getPrice())%> VNĐ</span> <span>Thông tin: <%=p.getDescription()%>
                                    </p>
                                </a></li>
                                <%
                                        }
                                    }
                                %>
                        </ul>
                    </div>
                </div>
            </div>
            <div id="footer"><jsp:include page="footer.jsp"></jsp:include></div>
        </div>

    </body>
</html>