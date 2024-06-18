package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Cart;
import model.User;
import dao.UserDAOImpl;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAOImpl userDAO = new UserDAOImpl();
	private List<Cart> cart = new ArrayList<Cart>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		java.sql.Date dob= null;
		
		try {
			dob = new java.sql.Date((new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dob"))).getTime());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
                String fullName = request.getParameter("fullName");
		String sex = request.getParameter("sex");
		String email = request.getParameter("email");
		String phoneNumber = request.getParameter("phoneNumber");
		String address = request.getParameter("address");
		System.out.println(username);

		String err = "";
		String url = "/register.jsp";

		if (username.equals("") || password.equals("") || email.equals("") || address.equals("") || phoneNumber.equals("")) {
			err += "Phải nhập đầy đủ thông tin!";
		} else {
			if (userDAO.checkUser(username) == true) {
				err += "Tài khoản đã tồn tại!";
			} else {
				Pattern pattenObj = Pattern
						.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
								+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
				Matcher matcherObj = pattenObj.matcher(email);
				if (!matcherObj.matches()) {
					err += "Email sai định dạng!";
				}
			}
		}

		if (err.length() > 0) {
			request.setAttribute("err", err);
		}

		try {
			if (err.length() == 0) {
				HttpSession session = request.getSession();
				session.setAttribute("cart", cart);
                     
				userDAO.addUser(new User(username, password, fullName, dob, sex, email, phoneNumber, address, "User", 1000));
				userDAO.login(username, password);
				Cookie loginCookie = new Cookie("username",username);
	            //setting cookie to expiry in 30 mins
	            loginCookie.setMaxAge(30*60);
	            response.addCookie(loginCookie);
	            response.sendRedirect("index.jsp");
				
			} else {
				url = "/register.jsp";
				RequestDispatcher rd = getServletContext()
						.getRequestDispatcher(url);
				rd.forward(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("/register.jsp");
		}

	}

}
