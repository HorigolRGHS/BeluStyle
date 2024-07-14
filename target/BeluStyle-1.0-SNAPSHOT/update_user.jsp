
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@page import="dao.UserDAO"%>
<%@page import="model.User"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/login.css" rel='stylesheet' type='text/css' />
        <title>Sign Up</title>
        <!-- For favicon png -->
        <link
            rel="shortcut icon"
            type="image/icon"
            href="Banner/Beluicon.png"
            />
    </head>
    <body class="align">
        <%
            String err = "";
            if (request.getAttribute("err") != null) {
                err = (String) request.getAttribute("err");
            }
            String username = request.getParameter("username");
            UserDAO userDAO = new UserDAO();
            User u = userDAO.getUser(username);
        %>

        <!--/start-login-two-->
        <div class="grid align__item">

            <div class="register">

                <img src="Banner/Beluicon.png" width="150px" height="150px" />

                <h1>Update Profile</h1>
                <form action="update-user" method="post">
                    <div style="color: red"><%=err%></div>

                    <!-- User Name -->
                    <div class="form__field">
                        <input type="text" placeholder="<%=u.getUsername()%>" readonly name="username"style="width: 100%; outline: 0; padding: .5rem 1rem;">
                    </div>
                    <!-- password -->
                    <div class="form__field">
                        <input type="password" placeholder="••••••••••••" readonly name="password"style="width: 100%; outline: 0; padding: .5rem 1rem;">
                    </div>
                    <!-- Full name -->
                    <div class="form__field">
                        <input type="text" placeholder="<%=u.getFullName()%>" name="fullName"style="width: 100%; outline: 0; padding: .5rem 1rem;">
                    </div>
                    <!-- Birthday -->
                    <div class="form__field">
                        <input type="date" placeholder="<%=u.getDob()%>" name="dob"
                               onfocus="this.value = '';"
                               onblur="if (this.value == '') {
                                           this.value = '<%=u.getDob()%>';
                                       }"style="width: 100%; outline: 0; padding: .5rem 1rem;">
                    </div>
                    <!-- sex -->
                    <div class="form__field">
                        <input type="text"  placeholder="<%=u.getSex()%>" list="exampleList"
                               onfocus="this.value = '';"
                               onblur="if (this.value == '') {
                                           this.value = '<%=u.getSex()%>';
                                       }"
                               name="sex"style="width: 100%; outline: 0; padding: .5rem 1rem;"> 
                        <datalist id="exampleList">
                            <option value="M">
                            <option value="F">
                        </datalist>
                    </div>
                    <!-- Email -->
                    <div class="form__field">
                        <input type="email" placeholder="<%= u.getEmail()%>" 
                               onfocus="this.value = '';"
                               onblur="if (this.value == '') {
                                           this.value = '<%=u.getEmail()%>';
                                       }" name="email"style="width: 100%; outline: 0; padding: .5rem 1rem;">
                    </div>
                    <!-- Phone Number -->
                    <div class="form__field">
                        <input type="text" placeholder="<%=u.getPhoneNumber()%>"  
                               onfocus="this.value = '';"
                               onblur="if (this.value == '') {
                                           this.value = '<%=u.getPhoneNumber()%>';
                                       }" name="phoneNumber"style="width: 100%; outline: 0; padding: .5rem 1rem;">
                    </div>
                    <!-- Address -->
                    <div class="form__field">
                        <input type="text" placeholder="<%=u.getAddress()%>"  
                               onfocus="this.value = '';"
                               onblur="if (this.value == '') {
                                           this.value = '<%=u.getAddress()%>';
                                       }"
                               name="address"style="width: 100%; outline: 0; padding: .5rem 1rem;">
                    </div>

                    <div class="form__field">
                        <input type="submit" value="CẬP NHẬT" style="width: 100%; outline: 0; padding: .5rem 1rem;">
                    </div>
                    <h5>
                        <a href="index">Trở về</a>
                    </h5>
                </form>
            </div>
        </div>
    </body>
</html>