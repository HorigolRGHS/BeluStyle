package Controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Cart;
import model.Order;
import model.User;
import dao.OrderDAO;
import dao.OrderDetailDAO;
import dao.ProductDAO;
import dao.UserDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.OrderDetail;

/**
 * Servlet implementation class ConfirmServlet
 */
public class ConfirmServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAO();
    private ProductDAO productDAO = new ProductDAO();
    private OrderDAO orderDAO = new OrderDAO();
    private OrderDetailDAO orderDetailDAO = new OrderDetailDAO();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        if (username == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("username")) {
                        username = cookie.getValue();
                        break;
                    }
                }
            }
        }
        

        User u = userDAO.getUser(username);

        // Get the current timestamp for database storage
        Calendar calendar = Calendar.getInstance();
        java.sql.Timestamp tdate = new java.sql.Timestamp(calendar.getTime().getTime());

        double total = 0;
        ArrayList<Cart> cart = (ArrayList<Cart>) request.getSession().getAttribute("cart");
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(0);

        // Get the current date and time for email
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy 'at' hh:mm:ss a");

        // Confirm order via email
        final String username_mail = "Belucom204@outlook.com";
        final String appPassword = "pcegtyokgvrqcmoc"; // Use the app password generated from Google
        String to = u.getEmail();
        String subject = "Thank you so much for shopping with BeluStyle";
        StringBuilder text = new StringBuilder("<strong>The Orders - " + username + " - </strong><i> " + ft.format(dNow) + "</i> <ul>");

        int orderId = orderDAO.getCountOrder() + 1;
        Order o = new Order(orderId, username, tdate);
        orderDAO.addOrder(o);
        if (cart != null) {
            for (Cart c : cart) {
                if (c.getUsername().equals(username)) {
                    total += (c.getQuantity() * productDAO.getProductbyId(c.getP().getProductID()).getPrice());
                    text.append("<li>").append(productDAO.getProductbyId(c.getP().getProductID()).getName())
                            .append(": ").append(nf.format(productDAO.getProductbyId(c.getP().getProductID()).getPrice()))
                            .append(" VNĐ </li>");
                    OrderDetail od = new OrderDetail(orderId, c.getP().getProductID(), c.getQuantity(), c.getQuantity() * productDAO.getProductById(c.getP().getProductID()).getPrice());
                    orderDetailDAO.addOrderDetail(od);
                    productDAO.subtractQuantity(productDAO.getProductbyId(c.getP().getProductID()), c.getQuantity());
                }
            }
        }
        
        text.append("Total Amount: <strong>").append(nf.format(total)).append(" VNĐ  </strong>");
        text.append("<br><i>HOPE YOU HAVE A WONDERFUL DAY</i>");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp-mail.outlook.com");
        props.put("mail.smtp.port", "587");

        Session session_mail = Session.getInstance(props,
                new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username_mail, appPassword);
            }
        });

        try {
            Message message = new MimeMessage(session_mail);
            message.setHeader("Content-Type", "text/plain; charset=UTF-8");
            message.setFrom(new InternetAddress(username_mail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(text.toString(), "text/html; charset=utf-8");
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        cart.clear();
        request.getSession().setAttribute("cart", cart);
        response.sendRedirect("index");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Your doPost implementation
    }
}
