<%@page import="model.Brand"%>
<%@page import="model.Category"%>
<%@page import="dao.ProductDAO"%>
<%@page import="dao.CategoryDAO"%>
<%@page import="dao.BrandDAO"%>
<%@page import="java.util.ArrayList"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="./css/editModal.css"/>
<script type="text/javascript">
    function previewImage(event) {
        var reader = new FileReader();
        reader.onload = function () {
            var output = document.getElementById('previewImg');
            output.src = reader.result;
            output.style.display = 'block';
            document.getElementById('currentImg').style.display = 'none';
        }
        reader.readAsDataURL(event.target.files[0]);
    }
</script>
<div id="myModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <a href="AdminPanel"><span class="close-button" onclick="closeModal()">&times;</span></a>
            <h2>Update Product</h2>
        </div>
        <div class="modal-body">
            <c:if test="${not empty pro}">  
                <form action="EditProduct" method="post" enctype="multipart/form-data">
                    <%
                        CategoryDAO categoryDAO = new CategoryDAO();
                        BrandDAO brandDAO = new BrandDAO();
                    %>
                    <input type="hidden" name="productId" value="${pro.productID}">

                    <label for="category">Category:</label><br>
                    <select name="category" class="drop-lists" id="category">
                        <c:forEach items="<%= categoryDAO.getList()%>" var="cate">
                            <option value="${cate.categoryID}" ${cate.categoryID == pro.categoryID ? 'selected' : ''}>${cate.name}</option>
                        </c:forEach>
                    </select><br>

                    <label for="brand">Brand:</label><br>
                    <select name="brand" class="drop-lists" id="brand">
                        <c:forEach items="<%= brandDAO.getList()%>" var="brand">
                            <option value="${brand.brandID}" ${brand.brandID == pro.brandID ? 'selected' : ''}>${brand.name}</option>
                        </c:forEach>
                    </select><br>

                    Product Name:<br>
                    <input type="text" name="productname" id="productname" value="${pro.name}" required=""><br><br>

                    Quantity:<br>
                    <input type="number" name="quantity" id="quantity" min="0" value="${pro.quantity}" required=""><br><br>

                    Image:<br>
                    <input onchange="previewImage(event)" type="file" id="fileInput" name="fileimage" required=""><br>
                    <img id="currentImg" style="width: 300px" src="./images/product/${pro.image}" alt="${pro.image}" />
                    <img id="previewImg" style="width: 300px; display: none;" alt="Image Preview" /><br><br>

                    Price:<br>
                    <input type="number" name="price" id="price" min="0" step="0.01" value="${pro.price}" required=""><br><br>

                    Description:<br>
                    <textarea name="description" id="description" cols="40" rows="10" required="">${pro.description}</textarea><br><br>

                    <div class="modal-footer">
                        <button class="btn-dialog btn-dialog-submit" type="submit">Save</button>
                        <a class="btn-dialog btn-dialog-cancel" type="button" href="AdminPanel" onclick="closeModal()">Cancel</a>
                    </div>
                </form>
            </c:if>

            <c:if test="${empty pro}">
                <p>Product not found.</p>
            </c:if>
        </div>
    </div>
</div>
