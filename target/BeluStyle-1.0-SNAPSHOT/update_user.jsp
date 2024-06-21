
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="dao.UserDAOImpl"%>
<%@page import="model.User"%>

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
		String username= request.getParameter("username");
		UserDAOImpl userDAO = new UserDAOImpl();
		User u= userDAO.getUser(username);
	%>

	<!--/start-login-two-->
	<div class="login-02">
		<div class="two-login  hvr-float-shadow">
			<div class="two-login-head">
				<img src="images/top-note.png" alt="" />
				<h2>Cập nhật thông tin</h2>
				<lable></lable>
			</div>
			<form action="UpdateUser" method="post">
				<li style="color: red"><%=err%></li>
				Username
				<li><input type="text" class="text"
					value="<%=u.getUsername()%>" readonly name="username"><a
					href="#" class=" icon2 user2"></a></li>
				Password
				<li><input type="password" value="***" readonly
					name="password"><a href="#" class=" icon2 lock2"></a></li>
                                Full Name
				<li><input type="text" class="text"
					value="<%=u.getFullName()%>" name="fullName"><a
					href="#" class=" icon2 user2"></a></li>
				Birthday
				<li><input type="date" value="<%=u.getDob()%>"
					onfocus="this.value = '';"
					onblur="if (this.value == '') {this.value = '<%=u.getDob()%>';}"
					name="dob"><a href="#" class=" icon2 lock2"></a></li>
				Sex
				<li><input type="text" value="<%=u.getSex()%>"
					list="exampleList" onfocus="this.value = '';"
					onblur="if (this.value == '') {this.value = '<%=u.getSex()%>';}"
					name="sex"> <datalist id="exampleList">
					<option value="Nam">
					<option value="Nữ">
					</datalist><a href="#" class=" icon2 lock2"></a></li>
				Email
				<li><input type="text" value="<%= u.getEmail() %>" onfocus="this.value = '';"
					onblur="if (this.value == '') {this.value = '<%=u.getEmail() %>';}" name="email"><a
					href="#" class=" icon2 lock2"></a></li>
				Phone Number
				<li><input type="text" value="<%=u.getPhoneNumber()%>"
					onfocus="this.value = '';"
					onblur="if (this.value == '') {this.value = '<%=u.getPhoneNumber()%>';}" name="phoneNumber"><a
					href="#" class=" icon2 lock2"></a></li>
				Address
				<li><input type="text" value="<%=u.getAddress()%>"
					onfocus="this.value = '';"
					onblur="if (this.value == '') {this.value = '<%=u.getAddress()%>';}"
					name="address"><a href="#" class=" icon2 lock2"></a></li>

				
				<div class="submit two">
					<input type="submit" value="CẬP NHẬT">
					
				</div>
				<h5>
					<a href="index.jsp">Trở về</a>
				</h5>
			</form>
		</div>
	</div>
</body>
</html>