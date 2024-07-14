<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/login.css" rel='stylesheet' type='text/css' />
        <title>Sign up</title>
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
        <!--/start-login-two-->

        <div class="grid align__item">

            <div class="register">

                <img src="Banner/Beluicon.png" width="150px" height="150px" />

                <h1>Sign Up</h1>

                <form action="" method="post" class="form">
                    <!-- User Name -->
                    <div class="form__field">
                        <input type="text" placeholder="UserName" name="username">
                    </div>
                    <!-- Email -->
                    <div class="form__field">
                        <input type="email" placeholder="Info@mailaddress.com" name="email">
                    </div>
                    <!-- Full name -->
                    <div class="form__field">
                        <input type="text" placeholder="FullName" name="fullName">
                    </div>
                    <!-- Birthday -->
                    <div class="form__field">
                        <input type="date" placeholder="Birthday" name="dob">
                    </div>
                    <!-- sex -->
                    <div class="form__field">
                        <select id="sex" name="sex">
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                        </select>
                    </div>
                    <!-- password -->
                    <div class="form__field">
                        <input type="password" placeholder="Password" name="password">
                    </div>
                    <!-- Phone Number -->
                    <div class="form__field">
                        <input type="text" placeholder="PhoneNumber" name="phoneNumber">
                    </div>
                    <!-- Address -->
                    <div class="form__field">
                        <input type="text" placeholder="Address" name="address">
                    </div>


                    <div class="form__field">
                        <input type="submit" value="Sign Up" style="width: 100%; outline: 0; padding: .5rem 1rem;">
                    </div>

                </form>

                <p>Already have an accout? <a href="login">Log in</a></p>

            </div>

        </div>
    </body>
</html>
