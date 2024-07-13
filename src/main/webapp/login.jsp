<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/login.css" rel='stylesheet' type='text/css' />
        <title>System login</title>
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
        %>
        <!--/start-login-one-->
        <div class="grid align__item">

            <div class="register">

                <img src="Banner/Beluicon.png" width="150px" height="150px" />

                <h1>LOGIN</h1>

                <form action="login" method="post">
                    <% String mess = "";
                        if (request.getAttribute("mess") != null) {
                            mess = (String) request.getAttribute("mess");%>
                    <div style="color: red"><%= err%></div>
                    <% }%>
                    <div style="color: red"><%= err%></div>
                    <!-- User Name -->
                    <div class="form__field">
                        <input type="text" placeholder="UserName" name="username"style="width: 100%; outline: 0; padding: .5rem 1rem;">
                    </div>
                    <!-- password -->
                    <div class="form__field">
                        <input type="password" placeholder="••••••••••••" name="password"style="width: 100%; outline: 0; padding: .5rem 1rem;">
                    </div>
                    <div class="p-container">
                        <label class="checkbox"><input type="checkbox"
                                                       name="checkbox" checked><i></i>Remember password</label>
                        <h6>
                            <a href="ResetPassword">Password recovery</a>
                        </h6>
                        <div class="clear"></div>
                    </div>
                    <div class="submit">
                        <input type="submit" value="SIGN IN" style="width: 100%; outline: 0; padding: .5rem 1rem;">
                    </div>
                </form>
                <h5>
                    Want to register an account?<a href="register"> Sign up </a>
                </h5>
            </div>
        </div>

    </body>
</html>