/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import dao.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Duong Nhat Anh CE181079
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 50 // 50 MB
)
public class AddProduct extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddProduct</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddProduct at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Input Validation
        List<String> errors = new ArrayList<>();

        int categoryID;
        try {
            categoryID = Integer.parseInt(request.getParameter("category"));
        } catch (NumberFormatException e) {
            categoryID = -1; // Invalid value
            errors.add("Invalid category selection.");
        }

        int brandID;
        try {
            brandID = Integer.parseInt(request.getParameter("brand"));
        } catch (NumberFormatException e) {
            brandID = -1; // Invalid value
            errors.add("Invalid brand selection.");
        }

        String productName = request.getParameter("productname");
        if (productName == null || productName.trim().isEmpty() || productName.length() < 3) {
            errors.add("Product name must be at least 3 characters.");
        }

        float price;
        try {
            price = Float.parseFloat(request.getParameter("price"));
            if (price <= 0) {
                errors.add("Price must be a positive number.");
            }
        } catch (NumberFormatException e) {
            price = -1; // Invalid value
            errors.add("Invalid price format.");
        }

        int quantity;
        try {
            quantity = Integer.parseInt(request.getParameter("quantity"));
            if (quantity <= 0) {
                errors.add("Quantity must be a positive number.");
            }
        } catch (NumberFormatException e) {
            quantity = -1; // Invalid value
            errors.add("Invalid quantity format.");
        }

        String description = request.getParameter("description");
        if (description != null && description.length() > 255) {
            errors.add("Description cannot exceed 255 characters.");
        }

        Part filePart = request.getPart("fileimage");
        if (filePart == null || filePart.getSize() == 0) {
            errors.add("Please upload an image.");
        }

        // If there are no validation errors, proceed to add product
        if (errors.isEmpty()) {
            String fileName = getFileName(filePart);
            String uploadDir = getServletContext().getRealPath("/assets");
            Files.createDirectories(Paths.get(uploadDir));

            try ( InputStream fileContent = filePart.getInputStream()) {
                Files.copy(fileContent, Paths.get(uploadDir, fileName), StandardCopyOption.REPLACE_EXISTING);
            }

            ProductDAO proDAO = new ProductDAO();
            boolean success = proDAO.addProduct(categoryID, brandID, productName, fileName, price, quantity, description);

            if (success) {
                response.sendRedirect("AdminPanel.jsp#products");
            } else {
                response.sendRedirect("AdminPanel.jsp?error=addProductFailed#products");
            }
        } else { // Validation failed
            request.setAttribute("errors", errors); // Store errors in request scope
            request.getRequestDispatcher("AdminPanel.jsp#products").forward(request, response);
        }
    }

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf('=') + 2, token.length() - 1);
            }
        }
        return "";
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
