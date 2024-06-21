<%@page import="dao.BrandDAO"%>
<%@page import="dao.ProductDAOImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@page import="java.text.NumberFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chi tiết sản phẩm</title>
        <link rel="stylesheet" href="css/detail.css" />
        <link rel="stylesheet" href="css/menu.css" />
        <style type="text/css">
            #main {
                width: 1060px;
                padding: 0;
                margin-left: auto;
                margin-right: auto;
            }

            #head {
                height: 200px;
                background-color: #F5F5F5;
                border: 1px solid #CDCDCD;
                margin-bottom: 5px;
                margin-top: 5px;
            }

            #head-link {
                height: 50px;
                line-height: 30px;
                border: 1px solid #CDCDCD;
                background-color: #F5F5F5;
                margin-bottom: 5px;
                clear: both;
            }

            #content {
                width: 1060px;
                min-height: 430px;
                border: 1px solid #CDCDCD;
                float: left;
                margin-bottom: 5px;
                clear: both;
            }

            #footer {
                height: 50px;
                clear: both;
                border: 1px solid #CDCDCD;
                background-color: #F8F8FF;
                margin-bottom: 5px;
            }
        </style>
    </head>
    <body>

        <%
            // ham nay de lay ma san pham truyen qua tren thanh dia chj
            String productID = "";
            if (request.getParameter("productID") != null) {
                productID = request.getParameter("productID");
            }

            ProductDAOImpl productDAO = new ProductDAOImpl();
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMinimumFractionDigits(0);
        %>
        <div id="main">
            <div id="head">
                <img src="images/banner.jpg" width="1057px" height="200px" />
            </div>
            <%
                String username = null;
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("username")) {
                            username = cookie.getValue();
                        }
                    }
                }

                if (username != null) {
            %>
            <div id="head-link">
                <div id='menungang'>
                    <ul>
                        <li class='last'><a href="index.jsp"><span>Trang
                                    chủ</span></a></li>
                        <li class='last'><a href="#"><span>Sản phẩm</span></a></li>
                        <li class='last'><a href="cart.jsp"><span>Giỏ hàng</span></a></li>
                        <li class='last'><a href="search_page.jsp"><span>Tìm kiếm</span></a></li>
                        <li class='last' style="float: right;"><a href="LogoutServlet"><span>Đăng xuất</span></a></li>
                        <li class='last' style="float: right;"><a href="update_user.jsp?username=<%=username%>"><span><%=username%></span></a></li>

                    </ul>
                </div>
            </div>
            <%
            } else {
            %>
            <div id="head-link">
                <div id='menungang'>
                    <ul>
                        <li class='last'><a href="index.jsp"><span>Trang
                                    chủ</span></a></li>
                        <li class='last'><a href="#"><span>Sản phẩm</span></a></li>
                        <li class='last'><a href="search_page.jsp"><span>Tìm kiếm</span></a></li>
                        <li class='last' style="float: right;"><a href="register.jsp"><span>Đăng
                                    ký</span></a></li>
                        <li class='last' style="float: right;"><a href="login.jsp"><span>Đăng
                                    nhập</span></a></li>
                    </ul>
                </div>
            </div>
            <%
                }
            %>
            <div id="content">

                <div class="left-1">
                    <img
                        src="images/product/<%=productDAO.getProductbyId(Integer.parseInt(productID))
                                                .getImage()%>"
                        width="200px" height="200px" />
                </div>
                <div class="left-2">
                    <table>
                        <tr class="row1">
                            <td class="col2" colspan="2"
                                style="padding: 10px; color: blue; font-size: 15px; text-transform: uppercase; text-align: center; font-weight: bold"><%=productDAO.getProductbyId(Integer.parseInt(productID))
                                                                .getName()%></td>
                        </tr>
                        <tr class="row2">
                            <td class="col1">Hãng sản xuất:</td>
                            <td class="col2"><%= BrandDAO.getBrand(Integer.parseInt(productID)).getName()%></td>
                        </tr>

                        <tr class="row2">
                            <td class="col1">Giá bán:</td>
                                <td class="col2"><%=nf.format(productDAO.getProductbyId(Integer.parseInt(productID))
                                                        .getPrice())%> VNĐ</td>
                        </tr>
                             <tr class="row2">
                            <td class="col1">Stock Quantity:</td>
                                <td class="col2"><%=productDAO.getProductbyId(Integer.parseInt(productID))
                                                        .getQuantity()%></td>
                        </tr>

                    </table>
                </div>
                <% if (username != null) {%>
                <div
                    style="margin-left: auto; margin-right: auto; text-align: center; margin-top: 10px; padding: 10px; clear: both;">
                    <a
                        href="#"><img
                            src="images/giohang.png" /></a>
                    <form action="GioHangServlet" method="post">
                        <input type="number" min="1" max="<%=productDAO.getProductbyId(Integer.parseInt(productID))
                                                .getQuantity()%>" value="1" name="soluong"/>
                        <input type="hidden" value="setCart" name="command"/>
                        <input type="hidden" value="<%=productID%>" name="productId"/>
                        <input type="submit" value="Thêm vào giỏ hàng">
                    </form>	
                </div>
                <%} else { %>
                <div
                    style="margin-left: auto; margin-right: auto; text-align: center; margin-top: 10px; padding: 10px; clear: both;">
                    <a
                        href="login.jsp"><img
                            src="images/giohang.png" /></a>
                </div>
                <%}%>
                <div class="left-3">
                    <article> <input type="checkbox" id="read_more"
                                     role="button"> <label for="read_more" onclick=""
                                     style="width: 770px; margin-left: 150px; margin-right: auto;"><span>Xem
                                thông tin chi tiết</span> <span>Đóng</span></label> <section>
                            <table>

                                <!-- thong tin chung -->
                                <tr rowspan="2">
                                    <td class="detail-1">Thông chi tiết</td>
                                    <td class="detail-2">Đang cập nhật</td>
                                </tr>


                            </table>
                        </section> </article>
                </div>

            </div>
            <div id="footer"><jsp:include page="footer.jsp"></jsp:include></div>
        </div>
    </body>
</html>
