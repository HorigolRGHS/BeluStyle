<%@page import="dao.ProductDAO"%>
<%@page import="model.Brand"%>
<%@page import="model.Category"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Product"%>
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
                <a href="#add-product" class="btn" onsubmit="saveCurrentSection()" >Add Product</a>
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
                        ArrayList<Product> prolist = ProductDAO.getAllProduct();
                        if (prolist != null) {
                            for (Product pro : prolist) {
                    %>
                    <tr>
                <form action="DeleteProduct" method="post">
                    <td><%= pro.getProductID() %></td>
                    <td><%= ProductDAO.getCategoryNameById(pro.getCategoryID()) %></td>
                    <td><%= ProductDAO.getBrandNameById(pro.getBrandID()) %></td>
                    <td><%= pro.getName() %></td>
                    <td><%= pro.getQuantity() %></td>
                    <td><img src="./images/product/<%= pro.getImage() %>" alt="<%= pro.getImage() %>" width="50"></td>
                    <td><%= pro.getPrice() %></td>
                    <td><%= pro.getDescription() %></td>
                    <td>
                        <a href="EditProduct?productId=<%= pro.getProductID()%>" class="btn btn-edit" onsubmit="saveCurrentSection()" >Edit</a>
                        <input type="hidden" name="productId" value="<%= pro.getProductID()%>">
                        <button type="submit" class="btn btn-delete" onsubmit="saveCurrentSection()" >Delete</button>
                </form>
                </td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="8">No products available.</td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
    </body>
</html>
