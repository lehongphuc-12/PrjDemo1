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
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import model.User;
import service.ProductManagerService;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "AddProductServlet", urlPatterns = {"/addProduct"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 5 * 1024 * 1024, // 5MB
        maxRequestSize = 10 * 1024 * 1024) // 10MB
public class AddProductServlet extends HttpServlet {




    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User user = (User) request.getSession().getAttribute("user");
        ProductManagerService productService = new ProductManagerService();
        try {
            String name = request.getParameter("name");
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            int cityId = Integer.parseInt(request.getParameter("city"));
            int categoryId = Integer.parseInt(request.getParameter("category"));
            String description = request.getParameter("description");

            List<Part> fileParts = new ArrayList<>();

            Part mainImage = request.getPart("mainImage");
            Part subImage1 = request.getPart("subImage1");
            Part subImage2 = request.getPart("subImage2");
            Part subImage3 = request.getPart("subImage3");

            if (mainImage != null) {
                fileParts.add(mainImage);
            }
            if (subImage1 != null) {
                fileParts.add(subImage1);
            }
            if (subImage2 != null) {
                fileParts.add(subImage2);
            }
            if (subImage3 != null) {
                fileParts.add(subImage3);
            }

//            String uploadPath = "C:\\Users\\ASUS\\Desktop\\SPRING25\\PRJ201\\projectDemo\\FePrjProject\\src\\main\\webapp\\img";
                String uploadPath = "/Users/nguyenanh/NetBeansProjects/PrjDemo1/src/main/webapp/assets/images/productImages";
            System.out.println("Upload Path: " + uploadPath);

            productService.addProduct(user, name, price, quantity, cityId, categoryId, description, fileParts, uploadPath);

            response.getWriter().write("success");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("error");
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
