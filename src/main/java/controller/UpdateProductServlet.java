/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;
import model.User;
import service.ProductManagerService;

/**
 *
 * @author ASUS
 */@MultipartConfig
@WebServlet(name = "UpdateProductServlet", urlPatterns = {"/updateProduct"})
public class UpdateProductServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateProductServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateProductServlet at " + request.getContextPath() + "</h1>");
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
        
        User user = (User) request.getSession().getAttribute("user");
        ProductManagerService productService = new ProductManagerService();
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String idParam = request.getParameter("id");
            if (idParam == null) {
                throw new IllegalArgumentException("ID không hợp lệ");
            }
            int id = Integer.parseInt(idParam);
            String name = request.getParameter("name");
            double price = Double.parseDouble(request.getParameter("price"));
            double quantity = Double.parseDouble(request.getParameter("quantity"));
            int cityId = Integer.parseInt(request.getParameter("city"));
            int categoryId = Integer.parseInt(request.getParameter("category"));
            String description = request.getParameter("description");

            // Tạo danh sách fileParts với thứ tự cố định
            List<Part> fileParts = new ArrayList<>(4); // 4 vị trí cố định
            fileParts.add(request.getPart("mainImage")); // Vị trí 0
            fileParts.add(request.getPart("subImage1")); // Vị trí 1
            fileParts.add(request.getPart("subImage2")); // Vị trí 2
            fileParts.add(request.getPart("subImage3")); // Vị trí 3

            String uploadPath = "/Users/nguyenanh/NetBeansProjects/PrjDemo1/src/main/webapp/assets/images/productImages";
            System.out.println("Upload Path: " + uploadPath);

            productService.updateProduct(id, name, price, quantity, cityId, categoryId, description, fileParts, uploadPath);
            out.write("success");
        } catch (Exception e) {
            e.printStackTrace();
            out.write("error: " + e.getMessage());
        } finally {
            out.close();
        }
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
