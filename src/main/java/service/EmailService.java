package service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import model.Order1;
import model.OrderDetail;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class EmailService {
    private static final Logger LOGGER = Logger.getLogger(EmailService.class.getName());
    private static final String FROM_EMAIL = "vanbi12092004@gmail.com";
    private static final String APP_PASSWORD = "lhbt bqhr qjvw wnjl";

    // Thêm instance của ProductService
    private static final ProductService productService = new ProductService();

    // Phương thức tiện ích để định dạng giá tiền
    private static String formatPrice(BigDecimal price) {
        if (price.scale() <= 0 || price.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0) {
            return new java.text.DecimalFormat("#,###").format(price.intValue());
        }
        return new java.text.DecimalFormat("#,###.##").format(price);
    }

    public static void sendEmail(String toEmail, String subject, String content, boolean isHtml) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);

            if (isHtml) {
                // Nội dung văn bản thuần túy
                String plainText = "Chào " + toEmail + ",\n\nCảm ơn bạn đã đặt hàng tại Hội Chợ Nông Sản!\n" +
                        "Chúng tôi đã nhận được đơn hàng của bạn và sẽ xử lý trong thời gian sớm nhất.\n" +
                        "Trân trọng,\nHội Chợ Nông Sản";
                Multipart multipart = new MimeMultipart("alternative");
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setText(plainText, "UTF-8");
                MimeBodyPart htmlPart = new MimeBodyPart();
                htmlPart.setContent(content, "text/html; charset=UTF-8");
                multipart.addBodyPart(textPart);
                multipart.addBodyPart(htmlPart);
                message.setContent(multipart);
            } else {
                message.setText(content);
            }

            Transport.send(message);
            LOGGER.info("✅ Email sent successfully to: " + toEmail);
        } catch (MessagingException e) {
            LOGGER.severe("Failed to send email to " + toEmail + ": " + e.getMessage());
            throw e;
        }
    }

    // Phương thức gửi email xác nhận đặt hàng với thông tin chi tiết
    public static void sendOrderConfirmationEmail(String toEmail, String username, Order1 order) throws MessagingException {
        String subject = "Xác nhận đặt hàng thành công";

        // Lấy thông tin đơn hàng
        int orderId = order.getOrderID();
        String totalPrice = formatPrice(order.getTotalAmount()); // Định dạng tổng tiền
        String orderDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(order.getOrderDate());
        String shippingAddress = order.getShippingAddress() != null ? order.getShippingAddress() : "Không có thông tin";
        String paymentMethod = "Thanh toán qua VNPay";
        String paymentStatus = "Đã thanh toán"; // Thêm trạng thái thanh toán

        // Xử lý địa chỉ giao hàng để xuống dòng (thay dấu phẩy bằng <br>)
        String formattedShippingAddress = shippingAddress.replace(", ", "<br>");

        // Tạo danh sách sản phẩm dưới dạng HTML
        StringBuilder orderItemsHtml = new StringBuilder();
        List<OrderDetail> orderDetails = (List<OrderDetail>) order.getOrderDetailCollection();
        if (orderDetails != null && !orderDetails.isEmpty()) {
            for (OrderDetail detail : orderDetails) {
                String productName = detail.getProductID().getProductName();
                int quantity = detail.getQuantity();
                String unitPrice = formatPrice(detail.getPrice()); // Định dạng đơn giá
                String subtotal = formatPrice(detail.getPrice().multiply(new BigDecimal(quantity))); // Định dạng thành tiền
                orderItemsHtml.append("<tr>")
                        .append("<td>").append(productName).append("</td>")
                        .append("<td>").append(quantity).append("</td>")
                        .append("<td>").append(unitPrice).append(" VNĐ</td>")
                        .append("<td>").append(subtotal).append(" VNĐ</td>")
                        .append("</tr>");
            }
        } else {
            orderItemsHtml.append("<tr><td colspan='4'>Không có sản phẩm trong đơn hàng.</td></tr>");
        }

        // Tạo nội dung email
        String content = "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; color: #333; line-height: 1.6; margin: 0; padding: 0; background-color: #f4f4f4; }" +
                ".container { width: 80%; max-width: 600px; margin: 20px auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); }" +
                ".header { text-align: center; padding: 20px 0; background-color: #28a745; color: #ffffff; border-radius: 8px 8px 0 0; }" +
                ".header h2 { margin: 0; font-size: 24px; }" +
                ".content { padding: 20px; }" +
                ".content p { margin: 10px 0; }" +
                ".order-details { margin: 20px 0; }" +
                ".order-details table { width: 100%; border-collapse: collapse; margin-top: 10px; }" +
                ".order-details th, .order-details td { padding: 10px; text-align: left; border-bottom: 1px solid #ddd; }" +
                ".order-details th { background-color: #f8f8f8; font-weight: bold; }" +
                ".total { font-size: 18px; font-weight: bold; margin-top: 10px; text-align: right; }" +
                ".footer { text-align: center; padding: 10px 0; color: #777; font-size: 14px; border-top: 1px solid #ddd; margin-top: 20px; }" +
                ".footer a { color: #28a745; text-decoration: none; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<p style='color: #777; font-size: 12px;'>Đây không phải thư rác. Vui lòng thêm " + FROM_EMAIL + " vào danh bạ để nhận email trong hộp thư đến.</p>" +
                "<div class='header'>" +
                "<h2>Xác nhận đặt hàng thành công</h2>" +
                "</div>" +
                "<div class='content'>" +
                "<p>Chào <strong>" + username + "</strong>,</p>" +
                "<p>Cảm ơn bạn đã đặt hàng tại <strong>Hội Chợ Nông Sản</strong>!</p>" +
                "<p>Chúng tôi đã nhận được đơn hàng của bạn và sẽ xử lý trong thời gian sớm nhất.</p>" +
                "<div class='order-details'>" +
                "<p><strong>Mã đơn hàng:</strong> #" + orderId + "</p>" +
                "<p><strong>Ngày đặt hàng:</strong> " + orderDate + "</p>" +
                "<p><strong>Thông tin giao hàng:</strong><br>" + formattedShippingAddress + "</p>" +
                "<p><strong>Phương thức thanh toán:</strong> " + paymentMethod + "</p>" +
                "<p><strong>Trạng thái thanh toán:</strong> " + paymentStatus + "</p>" + // Thêm dòng trạng thái
                "<h3>Chi tiết đơn hàng</h3>" +
                "<table>" +
                "<thead>" +
                "<tr>" +
                "<th>Sản phẩm</th>" +
                "<th>Số lượng</th>" +
                "<th>Đơn giá</th>" +
                "<th>Thành tiền</th>" +
                "</tr>" +
                "</thead>" +
                "<tbody>" +
                orderItemsHtml.toString() +
                "</tbody>" +
                "</table>" +
                "<div class='total'>" +
                "Tổng tiền: " + totalPrice + " VNĐ" +
                "</div>" +
                "</div>" +
                "<p>Nếu bạn có bất kỳ câu hỏi nào, vui lòng liên hệ với chúng tôi qua email <a href='mailto:support@hoichonongsan.com'>support@hoichonongsan.com</a> hoặc số điện thoại <strong>0123 456 789</strong>.</p>" +
                "<p>Trân trọng,</p>" +
                "<p><strong>Hội Chợ Nông Sản</strong></p>" +
                "</div>" +
                "<div class='footer'>" +
                "<p>© 2025 Hội Chợ Nông Sản. All rights reserved.</p>" +
                "<p><a href='https://hoichonongsan.com'>Truy cập website của chúng tôi</a></p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";

        sendEmail(toEmail, subject, content, true);
    }
}