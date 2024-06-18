<%@page import="dao.BrandDAO"%>
<%@page import="model.Brand"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Category"%>
<%@page import="java.util.List"%>
<%@page import="dao.CategoryDAOImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menu dọc</title>
        <link rel="stylesheet" href="css/category.css" />
    </head>
    <body>
        <%
            CategoryDAOImpl categoryDAO = new CategoryDAOImpl();
            BrandDAO brandDAO = new BrandDAO();
            List<Category> categoryList = new ArrayList<Category>();
            List<Brand> brandList = new ArrayList<Brand>();
            categoryList = categoryDAO.getList();
            brandList = brandDAO.getList();
            String err = "";
        %>
        <div class="container">
            <nav>
                <ul class="mcd-menu">
                    <li>
                        <form accept-charset="utf-8" method="post" action="SearchServlet" name="SearchServlet">
                            <p>
                                <label for="tensp">Tên sản phẩm</label>
                                <br>
                                <input accept-charset="utf-8" type="text"  name="productName" style="width:97%">
                            </p>
                            <p>
                                <label for="theloai">Thể loại</label>
                                <br>
                                <select accept-charset="utf-8" name="categoryName" style="width:100%">
                                    <option value="" selected="selected">--Chọn thể loại--</option>
                                    <%
                                        for (Category c : categoryList) {

                                    %>
                                    <option accept-charset="utf-8"  value="<%=c.getName()%>"><%=c.getName()%></option>
                                    <%}%>
                                </select>
                            </p>
                            <p>
                                <label for="theloai">Brand</label>
                                <br>
                                <select accept-charset="utf-8" name="brandName" style="width:100%">
                                    <option value="" selected="selected">--Choose the brand--</option>
                                    <%
                                        for (Brand b : brandList) {

                                    %>
                                    <option accept-charset="utf-8"  value="<%=b.getName()%>"><%=b.getName()%></option>
                                    <%}%>
                                </select>
                            </p>
                            <input type="submit" value="Tìm kiếm" name="timKiem">
                        </form>
                    </li>
                    <li style="color: red"><%=err%></li>
                </ul>
            </nav>
        </div>
    </body>
</html>