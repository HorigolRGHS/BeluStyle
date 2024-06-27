<%-- 
    Document   : dialog
    Created on : Jun 17, 2024, 3:54:46 PM
    Author     : Duong Nhat Anh CE181079
--%>

<%@page import="dao.ProductDAO"%>
<%@page import="model.Product"%>
<%@page import="model.Brand"%>
<%@page import="model.Category"%>
<%@page import="java.util.ArrayList"%>
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
                    output.style.display = 'block'; // Ensure the preview image is displayed
                    document.getElementById('currentImg').style.display = 'none';
                }
                reader.readAsDataURL(event.target.files[0]);
            }
        </script>
    </head>
    <body>
        <!--Add Product Dialog-->
        <div class="dialog overlay" id="add-product" >
            <a href="#" class="overlay-close" id="overlay-close"></a>
            <div class="dialog-body">
                <form action="AddProduct" method="post" enctype="multipart/form-data" >
                    <a class="dialog-close" href="#">&times;</a>
                    <h1>Add product</h1>
                    <label for="category">Category:</label><br>
                    <select name="category" class="drop-lists" id="category">
                        <%
                            ArrayList<Category> listCate = ProductDAO.getAllCategory();
                            for (Category cate : listCate) {
                        %>
                        <option value="<%= cate.getCategoryID()%>"><%= cate.getName() %></option>
                        <%
                            }
                        %>
                    </select>
                    <br>
                    <label for="brand">Brand:</label><br>
                    <select name="brand" class="drop-lists" id="brand">
                        <%
                            ArrayList<Brand> listBrand = ProductDAO.getAllBrand();
                            for (Brand brand : listBrand) {
                        %>
                        <option value="<%= brand.getBrandID()%>"><%= brand.getName() %></option>
                        <%
                            }
                        %>
                    </select>
                    <br>
                    Product Name: <br>
                    <div class="input-container">
                        <input type="text" name="productname" id="productname">
                    </div>
                    <br>
                    Quantity:
                    <br>
                    <div class="input-container">
                        <input type="text" name="quantity" id="quantity">
                    </div>
                    <br>
                    <br>Image:<br>
                    <input onchange="previewImage(event)" type="file" id="fileInput" name="fileimage">
                    <br>
                    <!-- Preview new image -->
                    <img id="previewImg" style="width: 100px; display: none;" alt="Image Preview"/>
                    <a id="fileName" name="fileName" style="display: none"></a>
                    <br>
                    Price:
                    <br>
                    <div class="input-container">
                        <input type="text" name="price" id="price">
                    </div>
                    <br>
                    Description:
                    <br>
                    <div class="input-container">
                        <textarea name="description" id="description" cols="40" rows="10"></textarea>
                    </div>
                    <input class="btn-submit" type="submit" value="Add">
                </form>
            </div>
        </div>

    </body>
</html>
