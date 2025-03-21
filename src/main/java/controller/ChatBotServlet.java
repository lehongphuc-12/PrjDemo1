package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import model.Product;
import org.json.JSONObject;
import org.json.JSONArray;
import service.ProductService;

@WebServlet(name = "ChatBotServlet", urlPatterns = {"/chatbot"})
public class ChatBotServlet extends HttpServlet {
    private ProductService productService;
    private static final String OPENROUTER_API_KEY = "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"; // Thay bằng API key của bạn
    private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions";

    @Override
    public void init() throws ServletException {
        super.init();
        this.productService = new ProductService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String userMessage = request.getParameter("message");
        int userId = request.getSession().getAttribute("userId") != null 
            ? (int) request.getSession().getAttribute("userId") : 1;

        String botResponse = processUserMessageWithDeepSeek(userMessage, userId);

        try (PrintWriter out = response.getWriter()) {
            out.println(botResponse);
        }
    }

    private String processUserMessageWithDeepSeek(String message, int userId) {
        String messageLower = message.toLowerCase(); // Chuyển tin nhắn người dùng về chữ thường

        
        if (messageLower.contains("hello") || messageLower.contains("hi") || messageLower.contains("xin chào")) {
            return "<p>Xin chào! Tôi là chatbot hỗ trợ mua sắm. Bạn có thể hỏi tôi về sản phẩm, giảm giá, hoặc gợi ý mua hàng.</p>";
        }

        String deepSeekResponse = callDeepSeek(message);

        // Nhận diện ý định "mua" và danh mục sản phẩm
        if (messageLower.contains("mua")) {
            if (messageLower.contains("rau xanh")) {
                return suggestProductsByCategory("Rau xanh");
            } else if (messageLower.contains("củ quả") || messageLower.contains("cu qua")) {
                return suggestProductsByCategory("Củ quả");
            } else if (messageLower.contains("nấm") || messageLower.contains("nam")) {
                return suggestProductsByCategory("Nấm");
            } else if (messageLower.contains("trái cây nội địa") || messageLower.contains("trai cay noi dia")) {
                return suggestProductsByCategory("Trái cây nội địa");
            } else if (messageLower.contains("trái cây nhập khẩu") || messageLower.contains("trai cay nhap khau")) {
                return suggestProductsByCategory("Trái cây nhập khẩu");
            } else if (messageLower.contains("hạt") || messageLower.contains("hat")) {
                return suggestProductsByCategory("Các loại hạt");
            } else if (messageLower.contains("trái cây sấy") || messageLower.contains("trai cay say")) {
                return suggestProductsByCategory("Trái cây sấy");
            } else if (messageLower.contains("nước ép") || messageLower.contains("nuoc ep")) {
                return suggestProductsByCategory("Nước ép trái cây");
            } else if (messageLower.contains("trà") || messageLower.contains("tra")) {
                return suggestProductsByCategory("Trà");
            } else if (messageLower.contains("cà phê") || messageLower.contains("ca phe")) {
                return suggestProductsByCategory("Cà phê");
            } else if (messageLower.contains("gạo") || messageLower.contains("gao")) {
                return suggestProductsByCategory("Gạo");
            } else if (messageLower.contains("đặc sản") || messageLower.contains("dac san")) {
                return suggestProductsByCategory("Đặc sản mọi miền");
            } else if (messageLower.contains("ngũ cốc") || messageLower.contains("ngu coc")) {
                return suggestProductsByCategory("Ngũ cốc, yến mạch");
            } else if (messageLower.contains("thịt heo") || messageLower.contains("thit heo")) {
                return suggestProductsByCategory("Thịt heo");
            } else if (messageLower.contains("thịt bò") || messageLower.contains("thit bo")) {
                return suggestProductsByCategory("Thịt bò");
            } else if (messageLower.contains("hải sản") || messageLower.contains("hai san")) {
                return suggestProductsByCategory("Hải sản tươi sống");
            } else {
                // Nếu không khớp danh mục, thử tìm theo tên sản phẩm
                String productName = extractProductName(messageLower);
                if (productName != null && !productName.isEmpty()) {
                    return suggestProductsByName(productName);
                }
                return "<p>Xin lỗi, tôi chưa hiểu bạn muốn mua gì. Bạn có thể nói rõ hơn không? Ví dụ: 'mua gạo', 'mua rau xanh', 'mua trái cây nội địa', v.v.</p>";
            }
        } 
        else if (deepSeekResponse.contains("giảm giá") || deepSeekResponse.contains("giam gia")) {
            return suggestDiscountedProducts();
        } else if (deepSeekResponse.contains("bán chạy") || deepSeekResponse.contains("ban chay")) {
            return suggestBestSellingProducts();
        } else if (deepSeekResponse.contains("gợi ý") || deepSeekResponse.contains("goi y")) {
            return suggestSmartProducts(userId);
        } else {
            return "<p>" + deepSeekResponse + "</p>";
        }
    }

    private String callDeepSeek(String message) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + OPENROUTER_API_KEY);
            conn.setDoOutput(true);

            String jsonInput = "{\"model\": \"deepseek/deepseek-chat:free\", \"messages\": [{\"role\": \"system\", \"content\": \"Bạn là chatbot hỗ trợ mua sắm, giúp người dùng tìm sản phẩm như rau xanh, củ quả, nấm, trái cây nội địa, trái cây nhập khẩu, các loại hạt, trái cây sấy, nước ép trái cây, trà, cà phê, gạo, đặc sản mọi miền, ngũ cốc - yến mạch, thịt heo, thịt bò, hải sản tươi sống, giảm giá, bán chạy, hoặc gợi ý.\"}, {\"role\": \"user\", \"content\": \"" + message + "\"}]}";
            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonInput.getBytes("UTF-8"));
                os.flush();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = br.lines().collect(Collectors.joining());
            conn.disconnect();

            JSONObject json = new JSONObject(response);
            JSONArray choices = json.getJSONArray("choices");
            JSONObject choice = choices.getJSONObject(0);
            JSONObject messageObj = choice.getJSONObject("message");
            return messageObj.getString("content");
        } catch (Exception e) {
            e.printStackTrace();
            return "Xin lỗi, có lỗi xảy ra khi xử lý yêu cầu của bạn.";
        }
    }

    private String suggestProductsByCategory(String category) {
        List<Product> products = productService.getProductsByCategory(category, 1, 5);
        return buildProductResponse(products, "Danh sách " + category + ":");
    }

    private String suggestDiscountedProducts() {
        List<Product> products = productService.getDiscountedProducts(1, 5);
        return buildProductResponse(products, "Sản phẩm đang giảm giá:");
    }

    private String suggestBestSellingProducts() {
        List<Product> products = productService.getPopularProducts(1, 5);
        return buildProductResponse(products, "Sản phẩm bán chạy:");
    }

    private String suggestSmartProducts(int userId) {
        List<Product> historyProducts = productService.getProductsByUserHistory(userId, 1, 3);
        if (historyProducts != null && !historyProducts.isEmpty()) {
            return buildProductResponse(historyProducts, "Dựa trên lịch sử mua hàng của bạn:");
        }
        List<Product> topRatedProducts = productService.getTopRatedProducts(1, 3);
        if (topRatedProducts != null && !topRatedProducts.isEmpty()) {
            return buildProductResponse(topRatedProducts, "Sản phẩm được đánh giá cao:");
        }
        return suggestBestSellingProducts();
    }

    private String buildProductResponse(List<Product> products, String title) {
        StringBuilder response = new StringBuilder("<div><h3>" + title + "</h3>");
        if (products != null && !products.isEmpty()) {
            response.append("<ul style='list-style: none; padding: 0;'>");
            for (Product p : products) {
                String imageUrl = p.getProductImageCollection().isEmpty() 
                    ? "/demo1/assets/images/productImages/default_product.png" 
                    : "/demo1/assets/images/productImages/"+p.getProductImageCollection().iterator().next().getImageURL();
                response.append("<li style='margin-bottom: 10px;'>")
                        .append("<img src='").append(imageUrl).append("' alt='").append(p.getProductName())
                        .append("' style='width: 50px; height: 50px; margin-right: 10px; vertical-align: middle;'>")
                        .append(p.getProductName()).append(": ").append(p.getPrice()).append("đ")
                        .append("</li>");
            }
            response.append("</ul>");
        } else {
            response.append("<p>Không tìm thấy sản phẩm phù hợp.</p>");
        }
        response.append("</div>");
        return response.toString();
    }

    // Hàm trích xuất tên sản phẩm từ tin nhắn
    private String extractProductName(String message) {
        String[] words = message.split("\\s+");
        for (String word : words) {
            if (!word.equals("mua") && !word.equals("tôi") && !word.equals("muốn")) {
                return word; // Lấy từ đầu tiên không phải từ khóa chung
            }
        }
        return null;
    }

    // Hàm gợi ý sản phẩm theo tên
    private String suggestProductsByName(String productName) {
        List<Product> products = productService.getProductsByName(productName, 1, 5);
        return buildProductResponse(products, "Danh sách sản phẩm liên quan đến '" + productName + "':");
    }
}