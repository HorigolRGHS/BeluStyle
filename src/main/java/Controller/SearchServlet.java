package Controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class SearchServlet
 */
public class SearchServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("search_page.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String productName = request.getParameter("productName");
        String categoryName = request.getParameter("categoryName");
        String brandName = request.getParameter("categoryName");
        String err = "";

        if (productName.equals("") && categoryName.equals("") && brandName.equals("")) {
            err += "Phải nhập ít nhất 1 thông tin tìm kiếm";
        }

        if (err.length() > 0) {
            request.setAttribute("err", err);
        }

        String url = "/search_page.jsp";
        try {

            RequestDispatcher rd = getServletContext()
                    .getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/login.jsp");
        }
    }

}
