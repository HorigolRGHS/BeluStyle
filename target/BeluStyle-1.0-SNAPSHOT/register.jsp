<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/login.css" rel='stylesheet' type='text/css' />
<title>Đăng kí tài khoản</title>
</head>
<body>
	<%
		String err = "";
		if (request.getAttribute("err") != null) {
			err = (String) request.getAttribute("err");
		}
	%>
	<!--/start-login-two-->
	<div class="login-02">
		<div class="two-login  hvr-float-shadow">
			<div class="two-login-head">
				<img src="images/top-note.png" alt="" />
				<h2>Đăng ký</h2>
				<lable></lable>
			</div>
			<form action="register" method="post">
				<li style="color: red"><%=err%></li>
				Username
				<li><input type="text" class="text" value=""
					name="username"><a href="#" class=" icon2 user2"></a></li>
				Password	
				<li><input type="password" value=""
					name="password"><a href="#" class=" icon2 lock2"></a></li>
                                Full name:
				<li><input type="text" class="text" value=""
					name="fullName"><a href="#" class=" icon2 user2"></a></li>
				Birthday
				<li><input type="date" value=""
					name="dob"><a href="#" class=" icon2 lock2"></a></li>
				Sex
				<li><input type="text" value="" list="exampleList"
					name="sex"> 
					<datalist id="exampleList">
					<option value="M">
					<option value="F">
					</datalist><a href="#" class=" icon2 lock2"></a></li>
				Email
				<li><input type="text" value=""
					name="email"><a href="#" class=" icon2 lock2"></a></li>
				Phone Number
				<li><input type="text" value=""				
					name="phoneNumber"><a href="#" class=" icon2 lock2"></a></li>
				Address
				<li><input type="text" value=""
					name="address"><a href="#" class=" icon2 lock2"></a></li>
					
				<div class="p-container">
					<label class="checkbox two"><input type="checkbox"
						name="checkbox" checked><i></i>Tôi đồng ý <a href="#">Điều khoản dịch vụ</a></label>
				</div>
				<div class="submit two">
					<input type="submit" value="ĐĂNG KÝ">
				</div>
				<h5>
					<a href="login.jsp">Đăng nhập</a>
				</h5>
			</form>
		</div>
	</div>
</body>
</html>