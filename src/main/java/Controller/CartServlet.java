package Controller;

import dao.ProductDAO;
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
 * Servlet implementation class CartServlet
 */
public class CartServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private List<Cart> cart = new ArrayList<Cart>();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = request.getParameter("command");
        String productId = request.getParameter("productId");
        ProductDAO proDAO = new ProductDAO();

        if (command != null) {
            if (command.equals("addCart")) {
                Product p = proDAO.getProductbyId(Integer.parseInt(productId));
                addToCart(p);
            } else if (command.equals("deleteCart")) {
                Product p = proDAO.getProductbyId(Integer.parseInt(productId));
                deleteFromCart(p);
            } else if (command.equals("removeCart")) {
                Product p = proDAO.getProductbyId(Integer.parseInt(productId));
                removeFromCart(p);
            } else if (command.equals("setCart")) {
                Product p = proDAO.getProductbyId(Integer.parseInt(productId));
                setCart(p, Integer.parseInt(request.getParameter("quantity")));
            }
        }

        HttpSession session = request.getSession();
        session.setAttribute("cart", cart);
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    private void removeFromCart(Product p) {
    Cart itemToRemove = null;
    for (Cart item : cart) {
        if (item.getP().getProductID() == p.getProductID()) {
            itemToRemove = item;
            break;
        }
    }
    if (itemToRemove != null) {
        cart.remove(itemToRemove);
    }
}


    private void setCart(Product p, int quantity) {
        for (Cart item : cart) {
            if (item.getP().getProductID() == p.getProductID()) {
                item.setQuantity(quantity);
                return;
            }
        }
        Cart c = new Cart();
        c.setP(p);
        c.setQuantity(quantity);
        cart.add(c);
    }

    private void addToCart(Product p) {
        for (Cart item : cart) {
            if (item.getP().getProductID() == p.getProductID()) {
                if (item.getQuantity() < p.getQuantity()) {
                    item.setQuantity(item.getQuantity() + 1);
                } else {
                    System.out.println("The quantity in the cart cannot exceed the available product quantity.");
                }
                return;
            }
        }
        if (p.getQuantity() > 0) {
            Cart c = new Cart();
            c.setP(p);
            c.setQuantity(1);
            cart.add(c);
        } else {
            System.out.println("The product is out of stock.");
        }
    }

    private void deleteFromCart(Product p) {
        for (Cart item : cart) {
            if (item.getP().getProductID() == p.getProductID() && item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                return;
            }
        }
    }
}
