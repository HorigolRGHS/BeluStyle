<%@ page import="java.util.List"%>
<%@ page import="model.Product"%>
<%@ page import="dao.ProductDAO"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sản phẩm</title>
        <link
            rel="shortcut icon"
            type="image/icon"
            href="Banner/Beluicon.png"
            />
        <link rel="stylesheet" href="css/product.css" />
        <link rel="stylesheet" href="css/style.css" />
        <link rel="stylesheet" href="css/menu.css" />
        <style>
            /* Add custom styles to ensure 5 products per row and center them */
            .products {
                display: flex;
                flex-wrap: wrap;
                justify-content: center;
                list-style: none;
                padding: 0;
                margin: 0;
            }
            .products li {
                flex: 0 0 20%; /* 100% / 5 products per row */
                box-sizing: border-box;
                text-align: center;
            }

        </style>
    </head>
    <body>
        <div id="main">
            <div id="head">
                <%@include file="banner.jsp" %>
            </div>

            <%@include file="navbar.jsp" %>

            <div id="content_center">
                <%
                    ProductDAO productDAO = new ProductDAO();
                    List<Product> products = productDAO.getList();

                    NumberFormat nf = NumberFormat.getInstance();
                    nf.setMinimumFractionDigits(0);

                    // Set pageSize to 20
                    int pageSize = 20;
                    int currentPage = request.getParameter("currentPage") != null ? Integer.parseInt(request.getParameter("currentPage")) : 1;
                    int start = (currentPage - 1) * pageSize;
                    int end = Math.min(start + pageSize, products.size());
                    List<Product> paginatedProducts = products.subList(start, end);

                    // Calculate the total number of pages
                    int totalPages = (int) Math.ceil((double) products.size() / pageSize);

                    // Set attributes for JSTL
                    request.setAttribute("paginatedProducts", paginatedProducts);
                    request.setAttribute("totalPages", totalPages);
                    request.setAttribute("currentPage", currentPage);
                %>

                <div id="site-wrapper-p" style="margin: 0 auto; width: 90%;">
                    <ul class="products homepage">
                        <c:forEach var="product" items="${paginatedProducts}">
                            <li class="preorder">
                                <span class="tagimg"></span>
                                <a href="detail?productID=${product.getProductID()}">
                                    <img src="images/product/${product.getImage()}" width="250px" height="250px" />
                                    <h3>${product.getName()}</h3>
                                    <h4>
                                        Price: <fmt:formatNumber value="${product.getPrice()}" type="number" minFractionDigits="0" /> VNĐ
                                    </h4>
                                    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

                                    <span >
                                        Stock quantity: 
                                        <strong>
                                            <c:choose>
                                                <c:when test="${product.quantity == 0}">
                                                    Sold out
                                                </c:when>
                                                <c:otherwise>
                                                    ${product.quantity}
                                                </c:otherwise>
                                            </c:choose>
                                        </strong>
                                    </span>

                                    </span>
                                    <p class="info">
                                        <span>Manufacturer: ${BrandDAO.getBrand(product.getBrandID()).getName()}</span>
                                        <span>Price: <fmt:formatNumber value="${product.getPrice()}" type="number" minFractionDigits="0" /> VNĐ</span>
                                        <span>Information: ${product.getDescription()}</span>
                                    </p>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>

                <div id="footer" align="center">
                    <c:forEach var="i" begin="1" end="${totalPages}" step="1">
                        <a href="product.jsp?currentPage=${i}">${i}</a>
                    </c:forEach>
                </div>

            </div>
        </div>
    </body>
</html>
