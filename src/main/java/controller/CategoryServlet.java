
package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CategoryGroup;
import model.Product;
import service.CategoryService;
 

@WebServlet(name = "CategoryServlet", urlPatterns = {"/cates"})
public class CategoryServlet extends HttpServlet {
    
    private CategoryService categoryService;
    private final int SIZE_PAGE = 10;
    private final String ORDER_PRODUCTS =  "ORDER BY (0.7 * COALESCE(SIZE(p.orderDetailCollection), 0) + 0.3 * COALESCE(SIZE(p.productViewCollection), 0)) DESC";


    public void init() {
        categoryService = new CategoryService();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String action = request.getParameter("action");
        if(action == null){
            action = "";
        }
        
        try {
            listProducts(request, response, action);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    
    
    private void listProducts(HttpServletRequest request, HttpServletResponse response,String action)
            throws SQLException, IOException, ServletException {
        
        int ID = 1;
        String id =request.getParameter("ID");
        if(id != null){
           ID = Integer.parseInt(id); 
        }
        
        int page = 1;
        String pageNumber = request.getParameter("pageNumber");
        if(pageNumber != null){
            page = Integer.parseInt(pageNumber);
        }
        
        String search = request.getParameter("searchbar");
        if(search == null){
            search = "";
        }
        
        List<Product> listPro = null;
        int totalProducts = 0;
        String typeOfProducts = "";
        
        switch(action){
            case "cateGroup":
                listPro = categoryService.getProductsByCategoryGroupId(ID, SIZE_PAGE, page,ORDER_PRODUCTS);
                totalProducts = categoryService.countProductsByCategoryGroupId(ID);
                typeOfProducts = categoryService.getCategoryGroupByID(ID).getGroupName();
                break;
                
            case "search":
                listPro = categoryService.searchProductsByName(search, SIZE_PAGE, page,ORDER_PRODUCTS);
                totalProducts = categoryService.countProductsBySearch(search);
                typeOfProducts = search;
                
                request.setAttribute("search", search);
                break;
                        
            default :
                listPro = categoryService.getProductsByCategoryId(ID, SIZE_PAGE, page,ORDER_PRODUCTS);
                totalProducts = categoryService.countProductsByCategoryId(ID);
                typeOfProducts = categoryService.getCategoryByID(ID).getCategoryName();
                break;
            
        }
        
        
        List<CategoryGroup> listCategoryGroup = categoryService.getAllCategoryGroup();
        int endPage = totalProducts / SIZE_PAGE;
        if(totalProducts % SIZE_PAGE != 0 ){
            endPage ++;
        }
        
        request.setAttribute("listPro", listPro);
        request.setAttribute("listCategoryGroup", listCategoryGroup);
        request.setAttribute("endPage", endPage);
        request.setAttribute("page", page);
        request.setAttribute("action", action);
        request.setAttribute("ID", ID);
        request.setAttribute("totalProducts", totalProducts);
        request.setAttribute("typeOfProducts", typeOfProducts);
        
        
//        PrintWriter out = response.getWriter();
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        
//        out.print(totalPage);


                        
        request.getRequestDispatcher("/views/search.jsp").forward(request, response);
    
    }
    


}
