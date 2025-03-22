package service;

import VNPay.VNPayConfig;
import jakarta.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class VNPayService {
    private static final Logger LOGGER = Logger.getLogger(VNPayService.class.getName());

    private final String vnp_TmnCode = VNPayConfig.VNP_TMN_CODE;
    private final String vnp_HashSecret = VNPayConfig.VNP_SECRET_KEY;
    private final String vnp_Url = VNPayConfig.VNP_PAY_URL;
    private final String vnp_ReturnUrl = VNPayConfig.VNP_RETURN_URL;

    // Tạo URL thanh toán VNPay (giữ nguyên)
    public String createPaymentUrl(HttpServletRequest request, int orderId, String total) throws UnsupportedEncodingException {
        String vnp_Version = VNPayConfig.VNP_VERSION;
        String vnp_Command = VNPayConfig.VNP_COMMAND;
        String vnp_OrderInfo = "Thanh toan don hang #" + orderId;
        String vnp_OrderType = VNPayConfig.VNP_ORDER_TYPE;
        String vnp_Amount = String.valueOf((long) (Double.parseDouble(total) * 100));
        String vnp_IpAddr = VNPayConfig.getIpAddress(request);
        String vnp_TxnRef = String.valueOf(orderId);
        String vnp_CreateDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String vnp_Locale = VNPayConfig.VNP_LOCALE;
        String vnp_CurrCode = VNPayConfig.VNP_CURRENCY_CODE;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", vnp_Amount);
        vnp_Params.put("vnp_CurrCode", vnp_CurrCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", vnp_OrderType);
        vnp_Params.put("vnp_Locale", vnp_Locale);
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                String encodedValue = URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString());
                hashData.append(fieldName).append("=").append(encodedValue);
                query.append(fieldName).append("=").append(encodedValue);
                if (fieldNames.indexOf(fieldName) < fieldNames.size() - 1) {
                    hashData.append("&");
                    query.append("&");
                }
            }
        }

        LOGGER.info("Hash data for payment URL: " + hashData.toString());
        String vnp_SecureHash = hmacSHA512(vnp_HashSecret, hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);
        String paymentUrl = vnp_Url + "?" + query.toString();
        LOGGER.info("Generated payment URL: " + paymentUrl);

        return paymentUrl;
    }

    // Xác thực phản hồi từ VNPay
    public boolean validateResponse(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String> vnp_Params = new HashMap<>();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
            String paramName = params.nextElement();
            String paramValue = request.getParameter(paramName);
            vnp_Params.put(paramName, paramValue);
        }

        // Log tất cả tham số trả về để debug
        LOGGER.info("All parameters received from VNPay: " + vnp_Params.toString());

        String vnp_SecureHash = vnp_Params.remove("vnp_SecureHash");
        if (vnp_SecureHash == null || vnp_SecureHash.isEmpty()) {
            LOGGER.warning("vnp_SecureHash is missing in the response.");
            return false;
        }

        String signValue = hashAllFields(vnp_Params);
        LOGGER.info("Calculated signValue: " + signValue);
        LOGGER.info("Received vnp_SecureHash: " + vnp_SecureHash);

        boolean isValid = signValue.equals(vnp_SecureHash);
        if (!isValid) {
            LOGGER.warning("Hash validation failed. Calculated hash does not match received hash.");
        } else {
            LOGGER.info("Hash validation successful.");
        }
        return isValid;
    }

    // Tính toán hash cho các tham số
    private String hashAllFields(Map<String, String> fields) throws UnsupportedEncodingException {
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();

        // Sử dụng tất cả tham số trả về (trừ vnp_SecureHash)
        for (String fieldName : fieldNames) {
            String fieldValue = fields.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                String encodedValue = URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString());
                hashData.append(fieldName).append("=").append(encodedValue);
                if (fieldNames.indexOf(fieldName) < fieldNames.size() - 1) {
                    hashData.append("&");
                }
            }
        }

        LOGGER.info("Data to hash for validation: " + hashData.toString());
        return hmacSHA512(vnp_HashSecret, hashData.toString());
    }

    // Tính toán HMAC-SHA512 (giữ nguyên)
    private String hmacSHA512(String key, String data) {
        try {
            if (key == null || key.isEmpty()) {
                LOGGER.severe("HMAC-SHA512 key is null or empty.");
                throw new IllegalArgumentException("HMAC-SHA512 key cannot be null or empty.");
            }
            if (data == null) {
                LOGGER.severe("HMAC-SHA512 data is null.");
                throw new IllegalArgumentException("HMAC-SHA512 data cannot be null.");
            }

            Mac hmacSHA512 = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmacSHA512.init(secretKey);
            byte[] hash = hmacSHA512.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            String result = sb.toString();
            LOGGER.info("HMAC-SHA512 hash generated: " + result);
            return result;
        } catch (Exception e) {
            LOGGER.severe("Error generating HMAC-SHA512: " + e.getMessage());
            throw new RuntimeException("Failed to generate HMAC-SHA512 hash: " + e.getMessage(), e);
        }
    }
    
    

}