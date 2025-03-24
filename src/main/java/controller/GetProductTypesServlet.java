package controller;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import model.Category;
import model.CategoryDTO; // Import lớp DTO
import service.CategoryService;

@WebServlet(name = "GetProductTypesServlet", urlPatterns = {"/getProductTypes"})
public class GetProductTypesServlet extends HttpServlet {

    private CategoryService cateService;

    @Override
    public void init() throws ServletException {
        cateService = new CategoryService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String groupId = request.getParameter("groupId");
            System.out.println("Received groupId: " + groupId); // Debug

            // Kiểm tra groupId có hợp lệ không
            if (groupId == null || groupId.trim().isEmpty()) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.print(new Gson().toJson(Collections.emptyList()));
                out.flush();
                return;
            }

            // Parse groupId và lấy danh sách loại sản phẩm
            int parsedGroupId = Integer.parseInt(groupId);
            List<Category> types = cateService.findAllByCateGroupID(parsedGroupId);
            System.out.println("Types retrieved: " + types); // Debug

            // Chuyển sang DTO
            List<CategoryDTO> typeDTOs = types != null 
                ? types.stream().map(CategoryDTO::new).collect(Collectors.toList()) 
                : Collections.emptyList();

            // Trả về JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            Gson gson = new Gson();
            out.print(gson.toJson(typeDTOs));
//            out.print(gson.toJson(types));
            out.flush();
            System.out.println("success");
        } catch (NumberFormatException e) {
            System.out.println("Invalid groupId: " + e.getMessage());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(Collections.emptyList()));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console server
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Servlet to get product types by group ID";
    }
}