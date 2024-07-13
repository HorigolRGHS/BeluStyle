<%@page import="dao.BrandDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Product"%>
<%@page import="java.util.List"%>
<%@page import="dao.ProductDAO"%>
<%@page import="java.text.NumberFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>BeluStyle</title>
        <!-- For favicon png -->
        <link
            rel="shortcut icon"
            type="image/icon"
            href="Banner/Beluicon.png"
            />
        <link rel="stylesheet" href="css/style.css" />
        <link rel="stylesheet" href="css/menu.css" />
        <link rel="stylesheet" href="css/product.css" />
    </head>
    <body>

        <div id="main">
            <div id="head">
                <%@include file="banner.jsp" %>
            </div>

                      
            <%@include file="navbar.jsp" %>
            
            <div id="content">
                <div id="left"><jsp:include page="search_menu.jsp"></jsp:include></div>
                    <div id="right">
                    <%
                        ProductDAO productDAO = new ProductDAO();
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
                                    href="detail?productID=<%=p.getProductID()%>"> <img
                                        src="images/product/<%=p.getImage()%>" width=" 250px" height="250px" />
                                    <h3><%=p.getName()%></h3>
                                    <h4>
                                        Giá:
                                        <%=nf.format(p.getPrice())%>
                                        VNĐ
                                        <!--</h4> <span class="textkm">Khuyến mãi trị giá đến <strong>500.000₫</strong>-->
                                        </span>
                                        <p class="info">
                                            <span>Manufacturer: <%=BrandDAO.getBrand(p.getBrandID()).getName()%>
                                            </span> <span>Price: <%=nf.format(p.getPrice())%> VNĐ
                                            </span> <span>Information: <%=p.getDescription()%>
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