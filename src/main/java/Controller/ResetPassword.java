package controller;

import java.io.IOException;
import java.util.Properties;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;









import model.User;
import dao.UserDAOImpl;

/**
 * Servlet implementation class ResetPassword
 */
public class ResetPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAOImpl userDAO = new UserDAOImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResetPassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		
		String err = "";
		if (username.equals("")) {
			err += "Phải nhập đầy đủ thông tin!";
		} else {
			if (userDAO.checkUser(username) == false) {
				err += "Tên đăng nhập không tồn tại!";
			}
		}

		if (err.length() > 0) {
			request.setAttribute("err", err);
		}
		
		String url = "/resetpassword.jsp";
		try {
			if (err.length() == 0) {

				User u = userDAO.getUser(username);
				User new_user = new User(username, "passwordreset", u.getFullName(), u.getDob(), u.getSex(), u.getEmail(), u.getPhoneNumber(), u.getAddress(), u.getRole(), u.getWallet());
				userDAO.updateUser(new_user);
				url = "/login.jsp";
				String mess = "Kiểm tra email để nhận mật khẩu mới!";
				request.setAttribute("mess", mess);
				
				//gửi mật khẩu mới qua email.
				   final String username_mail = "webbanhang1212050@gmail.com";
				   final String password = "matkhauwebbanhang";
				   String to = u.getEmail();
				   String subject = "Reset Password";
				   String text ="<i>Reset Password</i><br/>";
						   text+="<p>User: <strong>"; text+=username; text+="</strong></p>";
						   text+="<p>New password: <strong>passwordreset</strong></p>";
				   Properties props = new Properties();
				   props.put("mail.smtp.auth", "true");
					props.put("mail.smtp.starttls.enable", "true");
					props.put("mail.smtp.host", "smtp.gmail.com");
					props.put("mail.smtp.port", "587");
				   Session session_mail = Session.getInstance(props,
				   new jakarta.mail.Authenticator() {
				      protected PasswordAuthentication getPasswordAuthentication() {
				        return new PasswordAuthentication(username_mail, password);
				      }
				   });
				   try {
				     Message message = new MimeMessage(session_mail);
				     message.setHeader("Content-Type", "text/plain; charset=UTF-8");
				     message.setFrom(new InternetAddress(username_mail));
				     message.setRecipients(Message.RecipientType.TO,
				     InternetAddress.parse(to));
				     message.setSubject(subject);
				     message.setContent(text, "text/html; charset=utf-8");
				     Transport.send(message);
				   } catch (MessagingException e) {
				     throw new RuntimeException(e);
				   }
				
			} else {
				url = "/resetpassword.jsp";
				
			}
			RequestDispatcher rd = getServletContext()
					.getRequestDispatcher(url);
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("/resetpassword.jsp");
		}
	}

}
