<%@page import="dao.ProductDAO"%>
<%@page import="model.Product"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
    <body>
        <script>
            function saveCurrentSection() {
                localStorage.setItem('currentSection', 'products');
            }
        </script>

        <div class="productBox">
            <div class="cardHeader">
                <h2>All Products</h2>
                <button id="openModal" class="btn" onsubmit="saveCurrentSection()">Add Product</button>
            </div>

            <table class="styled-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Category</th>
                        <th>Brand</th>
                        <th>Product Name</th>
                        <th>Quantity</th>
                        <th>Image</th>
                        <th>Price</th>
                        <th>Description</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="prolist" value="${ProductDAO.getAllProduct()}"/>
                    <c:choose>
                        <c:when test="${not empty prolist}">
                            <c:forEach items="${prolist}" var="pro">
                                <tr>
                            <form action="DeleteProduct" method="post">
                                <td>${pro.productID}</td>
                                <td>${ProductDAO.getCategoryNameById(pro.categoryID)}</td>
                                <td>${ProductDAO.getBrandNameById(pro.brandID)}</td>
                                <td>${pro.name}</td>
                                <td>${pro.quantity}</td>
                                <td><img src="./images/product/${pro.image}" alt="${pro.image}" width="50"></td>
                                <td><fmt:formatNumber value="${pro.price}" type="number" maxFractionDigits="0"/></td>
                                <td>${pro.description}</td>
                                <td>
                                    <a href="EditProduct?productId=${pro.productID}" class="btn btn-edit" onsubmit="saveCurrentSection()"><ion-icon name="create-outline"></ion-icon></a>
                                    <input type="hidden" name="productId" value="${pro.productID}">
                                    <a type="submit" class="btn btn-delete" onsubmit="saveCurrentSection()"><ion-icon name="trash-outline"></ion-icon></a>
                            </form>
                            </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="8">No products available.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
    </body>
</html>
