<%@page import="dao.BrandDAO"%>
<%@page import="dao.CategoryDAO"%>
<%@page import="java.util.List"%>
<%@page import="dao.ProductDAO"%>
<%@page import="model.Product"%>
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
                    <%
                        ProductDAO productDAO = new ProductDAO();
                        CategoryDAO categoryDAO = new CategoryDAO();
                        BrandDAO brandDAO = new BrandDAO();
                        List<Product> prolist = productDAO.getList();

                        for (Product pro : prolist) {
                    %>
                    <tr>
                        <td><%= pro.getProductID()%></td>
                        <td><%= categoryDAO.getCategoryNameById(pro.getCategoryID())%></td>
                        <td><%= brandDAO.getBrandNameById(pro.getBrandID())%></td>
                        <td><%= pro.getName()%></td>
                        <td><%= pro.getQuantity()%></td>
                        <td><img src="./images/product/<%= pro.getImage()%>" alt="<%= pro.getImage()%>" width="50"></td>
                        <td><fmt:formatNumber value="<%= pro.getPrice()%>" type="number" maxFractionDigits="0"/></td>
                        <td><%= pro.getDescription()%></td>
                        <td>
                            <a href="EditProduct?productId=<%= pro.getProductID()%>" class="btn btn-edit" onsubmit="saveCurrentSection()"><ion-icon name="create-outline"></ion-icon></a>
                            <input type="hidden" name="productId" value="<%= pro.getProductID()%>">
                            <a href="#" onclick="doDelete('<%= pro.getProductID()%>')" class="btn btn-delete" onsubmit="saveCurrentSection()"><ion-icon name="trash-outline"></ion-icon></a>
                        </td>
                    </tr>
                    <% } %>

                    <% if (prolist.isEmpty()) { %>
                    <tr>
                        <td colspan="8">No products available.</td>
                    </tr>
                    <% }%>
                </tbody>
            </table>
        </div>
        <script>
            function doDelete(id) {
                if (confirm("Are you sure to delete product with id = " + id + "?")) {
                    window.location = "DeleteProduct?productId=" + id;
                }
            }
        </script> 
    </body>
</html>
