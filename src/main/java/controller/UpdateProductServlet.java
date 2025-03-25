
package controller;

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
import service.ProductManagerService;


@MultipartConfig
@WebServlet(name = "UpdateProductServlet", urlPatterns = {"/updateProduct"})
public class UpdateProductServlet extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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
            int quantity = Integer.parseInt(request.getParameter("quantity"));
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
        } catch (ServletException | IOException | IllegalArgumentException e) {
            out.write("error: " + e.getMessage());
        } finally {
            out.close();
        }
    }


}
