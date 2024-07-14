package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Cart;
import dao.UserDAO;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAO();
    private List<Cart> cart = new ArrayList<Cart>();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String err = "";
        if (username.equals("") || password.equals("")) {
            err += "Phải nhập đầy đủ thông tin!";
        } else {
            if (userDAO.login(username, password) == false) {
                err += "Tên đăng nhập hoặc mật khẩu không chính xác!";
            }
        }

        if (err.length() > 0) {
            session.setAttribute("err", err);
        }

        try {
            if (err.length() == 0) {

                session.setAttribute("username", username);
                session.setAttribute("cart", cart);
                double wallet = userDAO.getWallet(username);
                System.out.println(wallet);
                session.setAttribute("Wallet", wallet);
                String userRole = userDAO.getUserRole(username); // Implement getUserRole in UserDAO
                session.setAttribute("role", userRole);
                userDAO.login(username, password);
                Cookie loginCookie = new Cookie("username", username);
                //setting cookie to expiry in 30 mins
                loginCookie.setMaxAge(30 * 60);
                response.addCookie(loginCookie);
                System.out.println(userRole);
                if (!userRole.isEmpty()) {
                    response.sendRedirect("index");
                }else{
                    response.sendRedirect("login");
                }
            } else {
                response.sendRedirect("login");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            response.sendRedirect("login");
        }
    }

}
