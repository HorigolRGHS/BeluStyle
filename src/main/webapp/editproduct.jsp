<%@page import="model.Brand"%>
<%@page import="model.Category"%>
<%@page import="dao.ProductDAO"%>
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
      <span href="AdminPanel" class="close-button" onclick="closeModal()">&times;</span>
      <h2>Update Product</h2>
    </div>
    <div class="modal-body">
        <c:set var="productId" value="${param.productId}"/> <%-- Lấy productId từ tham số request --%>
        <c:if test="${productId != null}"> 
            <c:set var="product" value="${ProductDAO.getProductById(productId)}"/> <%-- Truyền productId vào ProductDAO.getProductById() --%>
            <c:if test="${product != null}">  
                <form action="EditProduct" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="productId" value="${product.productID}">
                    <label for="category">Category:</label><br>
                    <select name="category" class="drop-lists" id="category">
                        <c:set var="listCate" value="${ProductDAO.getAllCategory()}"/>
                        <c:forEach items="${listCate}" var="cate">
                            <option value="${cate.categoryID}" ${cate.categoryID == product.categoryID ? 'selected' : ''}>${cate.name}</option>
                        </c:forEach>
                    </select><br>
                    <label for="brand">Brand:</label><br>
                    <c:set var="listBrand" value="${ProductDAO.getAllBrand()}"/>
                    <select name="brand" class="drop-lists" id="brand">
                        <c:forEach items="${listBrand}" var="brand">
                            <option value="${brand.brandID}" ${brand.brandID == product.brandID ? 'selected' : ''}>${brand.name}</option>
                        </c:forEach>
                    </select><br>
                    Product Name: <br>
                    <input type="text" name="productname" id="productname" value="${product.name}"><br><br>
                    Quantity:<br>
                    <input type="number" name="quantity" id="quantity" min="0" value="${product.quantity}"><br><br>
                    Image:<br>
                    <input onchange="previewImage(event)" type="file" id="fileInput" name="fileimage"><br>
                    <img id="currentImg" style="width: 300px" src="./images/product/${product.image}" alt="${product.image}" />
                    <img id="previewImg" style="width: 300px; display: none;" alt="Image Preview" /><br><br>
                    Price:<br>
                    <input type="number" name="price" id="price" min="0" step="0.01" value="${product.price}"><br><br>
                    Description:<br>
                    <textarea name="description" id="description" cols="40" rows="10">${product.description}</textarea><br><br>

                    <div class="modal-footer">
                        <button class="btn-dialog btn-dialog-submit" type="submit">Save</button>
                        <a class="btn-dialog btn-dialog-cancel" type="button" href="AdminPanel" onclick="closeModal()">Cancel</a>
                    </div>
                </form>
            </c:if>
            <c:if test="${product == null}">
                <p>Product not found.</p>
            </c:if>
        </c:if>
    </div>
</div>
