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
             
            <%@include file="navbar.jsp" %>
            
            <div id="content">
                <div id="left"><jsp:include page="category.jsp"></jsp:include></div>
                    <div id="right">
                    <%
                        ProductDAO productDAO = new ProductDAO();
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
                                    href="detail?productID=<%=p.getProductID()%>"> <img
                                        src="images/product/<%=p.getImage()%>" width=" 250px" />
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
                                    href="detail?productID=<%=p.getProductID()%>"> <img
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