<%-- 
    Document   : editDialog
    Created on : Jun 18, 2024, 11:59:56 AM
    Author     : Duong Nhat Anh CE181079
--%>

<%@page import="dao.ProductDAO"%>
<%@page import="model.Category"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Brand"%>
<%@page import="model.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript">
            function previewImage(event) {
                var reader = new FileReader();
                reader.onload = function () {
                    var output = document.getElementById('previewImg');
                    output.src = reader.result;
                    output.style.display = 'block';

                    // Hide the current image only if a new file is selected
                    if (event.target.files.length > 0) {
                        document.getElementById('currentImg').style.display = 'none';
                    }
                }
                reader.readAsDataURL(event.target.files[0]);
            }
        </script>
    </head>
    <body>
        <%
            // Fetch the product details
            int productId = Integer.parseInt(request.getParameter("productId"));
            Product product = ProductDAO.getProductById(productId);

            // Fetch the categories and brands
            ArrayList<Category> listCate = ProductDAO.getAllCategory();
            ArrayList<Brand> listBrand = ProductDAO.getAllBrand();
        %>
        <!-- Edit-Dialog -->
        <div class="dialog overlay" id="EditProduct">
            <a href="#" class="overlay-close"></a>
            <div class="dialog-body">
                <form action="EditProduct" method="post" enctype="multipart/form-data">
                    <a class="dialog-close" href="AdminPanel.jsp">&times;</a>
                    <h1>Edit product</h1>
                    <!-- Hidden field to pass the product ID -->
                    ProductID:
                    <br>
                    <input type="text" name="ProductId" value="<%= product.getProductID()%>" readonly="">
                    <br>
                    <label for="category">Category:</label><br>
                    <select name="category" class="drop-lists" id="category">
                        <%
                            for (Category cate : listCate) {
                        %>
                        <option value="<%= cate.getCategoryID()%>" <%= cate.getCategoryID() == product.getCategoryID() ? "selected" : ""%>>
                            <%= cate.getName()%>
                        </option>
                        <%
                            }
                        %>
                    </select>
                    <br>
                    <label for="brand">Brand:</label><br>
                    <select name="brand" class="drop-lists" id="brand">
                        <%
                            for (Brand brand : listBrand) {
                        %>
                        <option value="<%= brand.getBrandID()%>" <%= brand.getBrandID() == product.getBrandID() ? "selected" : ""%>>
                            <%= brand.getName()%>
                        </option>
                        <%
                            }
                        %>
                    </select>
                    <br>
                    Product Name: <br>
                    <div class="input-container">
                        <input type="text" name="productname" id="productname" value="<%= product.getName()%>">
                    </div>
                    <br>
                    Quantity:
                    <br>
                    <div class="input-container">
                        <input type="text" name="quantity" id="quantity" value="<%= product.getQuantity()%>">
                    </div>
                    <br>
                    <br>Image:<br>
                    <input onchange="previewImage(event)" type="file" id="fileInput" name="fileimage">
                    <br>
                    <!-- Current image display -->
                    <img id="currentImg" style="width: 300px" src="./images/product/<%= product.getImage()%>" alt="<%= product.getImage()%>"/>
                    <!-- Preview new image -->
                    <img id="previewImg" style="width: 300px; display: none;" alt="Image Preview"/>
                    <a id="fileName" name="fileName" style="display: none"></a>
                    <br>
                    Price:
                    <br>
                    <div class="input-container">
                        <input type="text" name="price" id="price" value="<%= product.getPrice()%>">
                    </div>
                    Description:
                    <br>
                    <div class="input-container">
                        <textarea name="description" id="description" cols="40" rows="10"><%= product.getDescription()%></textarea>
                    </div>
                    <input class="btn-submit" type="submit" value="Update">
                </form>
            </div>
        </div>
    </body>
</html>
