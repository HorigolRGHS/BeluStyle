package controller;

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
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Cart;
import model.History;
import model.User;
import dao.HistoryDAOImpl;
import dao.ProductDAOImpl;
import dao.UserDAO;
import dao.UserDAOImpl;

/**
 * Servlet implementation class ConfirmServlet
 */
public class ConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAOImpl userDAO = new UserDAOImpl();
    private ProductDAOImpl productDAO = new ProductDAOImpl();  
    private HistoryDAOImpl historyDAO = new HistoryDAOImpl();
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
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
        final String username_mail = "your-email@gmail.com";
        final String appPassword = "your-app-password"; // Use the app password generated from Google
        String to = u.getEmail();
        String subject = "Confirm Cart";
        StringBuilder text = new StringBuilder("<strong>Đơn Hàng - " + username + " - </strong><i> " + ft.format(dNow) + "</i> <ul>");
        
        if (cart != null) {
            for (Cart c : cart) {
                total += (c.getQuantity() * productDAO.getProduct(c.getP().getMa_san_pham()).getGia_ban());
                text.append("<li>").append(productDAO.getProduct(c.getP().getMa_san_pham()).getTen_san_pham())
                    .append(": ").append(nf.format(productDAO.getProduct(c.getP().getMa_san_pham()).getGia_ban()))
                    .append(" VNĐ </li>");
                
                History h = new History(0, u.getUser_id(), c.getP().getMa_san_pham(), tdate, c.getQuantity(), 
                        (c.getQuantity() * productDAO.getProduct(c.getP().getMa_san_pham()).getGia_ban()));
                historyDAO.addHistory(h);
            }
        }
        text.append("Tổng thanh toán: <strong>").append(nf.format(total)).append(" VNĐ </strong>");
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); 
        props.put("mail.smtp.host", "smtp.gmail.com");
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
        response.sendRedirect("index.jsp");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Your doPost implementation
    }
}
