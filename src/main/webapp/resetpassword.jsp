<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/login.css" rel='stylesheet' type='text/css' />
        <title>Khôi phục mật khẩu</title>
        <!-- For favicon png -->
        <link
            rel="shortcut icon"
            type="image/icon"
            href="Banner/Beluicon.png"
            />
    </head>
    <body>
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
                <h1>Password recovery</h1>
                <span></span>
                <div class="form__field">
                    <form action="ResetPassword" method="post">
                        <div style="color: red"><%= err%></div>
                        <input type="text" class="text" value="Username"
                               onfocus="this.value = '';"
                               onblur="if (this.value == '') {
                                           this.value = 'Username';
                                       }"
                               name="username"style="width: 100%; outline: 0; padding: .5rem 1rem; color: #7e8ba3;">
                        <input type="text" class="text" value="Your account's email"
                               onfocus="this.value = '';"
                               onblur="if (this.value == '') {
                                           this.value = 'Email';
                                       }"
                               name="email"style="width: 100%; outline: 0; padding: .5rem 1rem; color: #7e8ba3;">
                        <input type="text" class="text" value="New Password"
                               onfocus="this.value = '';"
                               onblur="if (this.value == '') {
                                           this.value = 'newpass';
                                       }"
                               name="newpass"style="width: 100%; outline: 0; padding: .5rem 1rem; color: #7e8ba3;">


                        <div class="p-container">

                            <div class="clear"></div>
                        </div>
                        <h1></h1>
                        <div class="submit">
                            <input type="submit" value="RESTORE" style="width: 100%; outline: 0; padding: .5rem 1rem;">
                        </div>

                    </form>
                </div>
            </div>

    </body>
</html>