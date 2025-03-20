
package utils;

import java.text.Normalizer;

public class NameNormalizer {
    public static String nameNormalized(String name) {
        if (name == null) {
            return "";
        }
        
        // Phân tách ký tự có dấu thành ký tự gốc và dấu (NFD)
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD);
        
        // Loại bỏ các ký tự dấu (Mn - Mark, Nonspacing)
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < normalized.length(); i++) {
            char c = normalized.charAt(i);
            // Kiểm tra nếu ký tự không phải là dấu (category 'Mn')
            if (Character.getType(c) != Character.NON_SPACING_MARK) {
                result.append(c);
            }
        }
        
        // Loại bỏ khoảng trắng và chuyển thành chữ thường
        return result.toString().replace(" ", "").toLowerCase().replace("-", "");
    }

    // Hàm main để kiểm tra
    public static void main(String[] args) {
        String[] testNames = {
            "3 trái dừa xiêm gọt vỏ (300-400g/trái)",
            "Rau Muống",
            "Hà Nội",
            "Không có tên",
            "Gạo Tẻ Séng Cù - Đặc Sản Tây Bắc"
        };

        for (String name : testNames) {
            String normalized = nameNormalized(name);
            System.out.println("Original: " + name + " -> Normalized: " + normalized);
        }
    }
}
