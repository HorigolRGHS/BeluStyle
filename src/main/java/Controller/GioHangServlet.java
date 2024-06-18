package controller;

import dao.ProductDAOImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Cart;
import model.Product;

/**
 * Servlet implementation class GioHangServlet
 */
public class GioHangServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private List<Cart> cart = new ArrayList<Cart>();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GioHangServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String command = request.getParameter("command");
        String productId = request.getParameter("productId");
        if (command.equals("addCart")) {
            Product p = ProductDAOImpl.getProductbyId(Integer.parseInt(productId));

            addToCart(p);
            // sau khi them vao gio hang ta se chuyen toi trang gio hang
            // can tao session de luu tru gia tri
            HttpSession session = request.getSession();

            // ta test xem gio hang co them duoc ko?
            System.out.println(cart.size());
            session.setAttribute("cart", cart);
            response.sendRedirect("cart.jsp");
        } else {
            if (command.equals("deleteCart")) {
                Product p = ProductDAOImpl.getProductbyId(Integer.parseInt(productId));
                deleteFromCart(p);
                HttpSession session = request.getSession();

                // ta test xem gio hang co them duoc ko?
                System.out.println(cart.size());
                session.setAttribute("cart", cart);
                response.sendRedirect("cart.jsp");
            } else {
                if (command.equals("removeCart")) {
                    Product p = ProductDAOImpl.getProductbyId(Integer.parseInt(productId));
                    removeFromCart(p);
                    HttpSession session = request.getSession();

                    //lưu vào session
                    session.setAttribute("cart", cart);
                    response.sendRedirect("cart.jsp");
                } else {
                    if (command.equals("setCart")) {
                        Product p = ProductDAOImpl.getProductbyId(Integer.parseInt(productId));
                        setCart(p, Integer.parseInt((String) request.getParameter("soluong")));
                        HttpSession session = request.getSession();

                        session.setAttribute("cart", cart);
                        response.sendRedirect("cart.jsp");
                    }
                }
            }
        }
    }

    private String removeFromCart(Product p) {
        for (Cart item : cart) {
            if (item.getP().getProductID() == p.getProductID()) {
                cart.remove(item);
                return "cart";
            }
        }
        return "cart";
    }

    private String setCart(Product p, int s) {
        for (Cart item : cart) {
            if (item.getP().getProductID() == p.getProductID()) {
                item.setQuantity(s);
                return "cart";
            }
        }
        Cart c = new Cart();
        c.setP(p);
        c.setQuantity(s);
        cart.add(c);
        return "cart";
    }

// Phương thức thêm sản phẩm mới vào trong giỏ hàng
    public String addToCart(Product p) {
        for (Cart item : cart) {
            if (item.getP().getProductID() == p.getProductID()) {
                if (item.getQuantity() < p.getQuantity()) {
                    item.setQuantity(item.getQuantity() + 1);
                } else {
                    // You can add a message here to notify that the product quantity is not sufficient
                    System.out.println("The quantity in the cart cannot exceed the available product quantity.");
                }
                return "cart";
            }
        }
        if (p.getQuantity() > 0) {
            Cart c = new Cart();
            c.setP(p);
            c.setQuantity(1);
            cart.add(c);
        } else {
            // You can add a message here to notify that the product is out of stock
            System.out.println("The product is out of stock.");
        }
        return "cart";
    }

    // phuongw thuc giam bot 1 san pham khoi trong gio hang
    public String deleteFromCart(Product p) {
        for (Cart item : cart) {
            if (item.getP().getProductID() == p.getProductID() && item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                return "cart";
            }
        }
        return "cart";
    }

}
