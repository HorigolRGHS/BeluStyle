<%-- 
    Document   : dialog
    Created on : Jun 17, 2024, 3:54:46 PM
    Author     : Duong Nhat Anh CE181079
--%>

<%@page import="dao.CategoryDAO"%>
<%@page import="model.Product"%>
<%@page import="model.Brand"%>
<%@page import="model.Category"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="./css/dialog.css"/>
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
        <div id="myModal" class="modal">
            <div class="modal-content">
                <div class="modal-header">
                    <span class="close-button">&times;</span>
                    <h2>Add Product</h2>
                </div>
                <div class="modal-body">
                    <form action="AddProduct" method="post" enctype="multipart/form-data">
                        <label for="category">Category:</label><br>
                        <select name="category" class="drop-lists" id="category">
                            <%
                                CategoryDAO categoryDAO = new CategoryDAO();  // Create the DAO instance
                                request.setAttribute("categories", categoryDAO.getList()); // Store categories in request scope 
                            %>

                            <c:forEach items="${categories}" var="cate">
                                <option value="${cate.categoryID}">${cate.name}</option>
                            </c:forEach>
                        </select>
                        <br>

                        <label for="brand">Brand:</label><br>
                        <select name="brand" class="drop-lists" id="brand">
                            <c:forEach items="${ProductDAO.allBrand}" var="brand">
                                <option value="${brand.brandID}">${brand.name}</option>
                            </c:forEach>
                        </select>
                        <br>

                        Product Name: <br>
                        <div class="input-container">
                            <input type="text" name="productname" id="productname">
                        </div>
                        <br>

                        Quantity: <br>
                        <div class="input-container">
                            <input type="text" name="quantity" id="quantity">
                        </div>
                        <br><br>

                        Image:<br>
                        <input onchange="previewImage(event)" type="file" id="fileInput" name="fileimage">
                        <br>
                        <img id="previewImg" style="width: 100px; display: none;" alt="Image Preview"/>
                        <a id="fileName" name="fileName" style="display: none"></a>
                        <br>

                        Price: <br>
                        <div class="input-container">
                            <input type="text" name="price" id="price">
                        </div>
                        <br>

                        Description: <br>
                        <div class="input-container">
                            <textarea name="description" id="description" cols="40" rows="10"></textarea>
                        </div>
                </div>
                <div class="modal-footer">
                    <button class="btn-dialog btn-dialogSubmit" type="submit" id="closeModal">Add</button>
                    <button class="btn-dialog btn-dialogCancel" type="reset" id="closeModal">Cancel</button>
                    </form> 
                </div>
                <script src="./js/popup.js"></script>
            </div>
        </div>
    </body>
</html>
