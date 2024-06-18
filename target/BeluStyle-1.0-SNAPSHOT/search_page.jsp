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
                <img src="images/banner.jpg" width="1057px" height="200px" />
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
                <div id="left"><jsp:include page="search_menu.jsp"></jsp:include></div>
                    <div id="right">
                    <%
                        ProductDAOImpl productDAO = new ProductDAOImpl();
                        List<Product> list = new ArrayList<Product>();
                        list = productDAO.getList();
                        String productName = "";
                        String categoryName = "";
                        String brandName = "";
                        if (request.getParameter("productName") != null && request.getParameter("categoryName") != null && request.getParameter("brandName") != null) {
                            categoryName = request.getParameter("categoryName");
                            brandName = request.getParameter("brandName");
                            productName = request.getParameter("productName");
                        }
                        NumberFormat nf = NumberFormat.getInstance();
                        nf.setMinimumFractionDigits(0);
                    %>
                    <% String err = "";
                                    if (request.getAttribute("err") != null) {
                                        err = (String) request.getAttribute("err");%>
                    <h3><%=err%></h3>
                    <% } %>

                    <%if (productDAO.searchList(productName, categoryName, brandName).size() == 0 && err == "") {%>
                    <h3>Không tìm thấy sản phẩm nào phù hợp</h3>
                    <%} %>
                    <div id="site-wrapper" style="float: left">
                        <ul class="products homepage">

                            <%
                                if (productName != null || categoryName != null || brandName != null) {
                                    for (Product p : productDAO.searchList(productName, categoryName, brandName)) {
                            %>

                            <li class="preorder"><span class="tagimg "> </span> <a
                                    href="detail.jsp?productID=<%=p.getProductID()%>"> <img
                                        src="sanpham/<%=p.getImage()%>" width=" 250px" height="250px" />
                                    <h3><%=p.getName()%></h3>
                                    <h4>
                                        Giá:
                                        <%=nf.format(p.getPrice())%>
                                        VNĐ
                                    <!--</h4> <span class="textkm">Khuyến mãi trị giá đến <strong>500.000₫</strong>-->
                                    </span>
                                    <p class="info">
                                        <span>Hãng sx: <%=BrandDAO.getBrand(p.getBrandID()).getName()%>
                                        </span> <span>Giá: <%=nf.format(p.getPrice())%> VNĐ
                                        </span> <span>Thông tin: <%=p.getDescription()%>
                                    </p>
                                </a></li>

                            <%
                                                    }
                                                } else {%>
                            <h3> Nhập thông tin tìm kiếm </h3>
                            <%}
                            %>
                        </ul>
                    </div>
                </div>
            </div>
            <div id="footer"><jsp:include page="footer.jsp"></jsp:include></div>
        </div>

    </body>
</html>