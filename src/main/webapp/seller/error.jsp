<%-- 
    Document   : error
    Created on : Mar 19, 2025, 6:17:21 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Error Page</title>
    <style>
        body {
/*            font-family: Arial, sans-serif;
            background-color: #f0f7f0;  Nền xanh lá nhạt 
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }*/
        .error-container {
            text-align: center;
            background-color: #fff;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border: 2px solid #4CAF50; /* Viền xanh lá */
        }
        h1 {
            color: #4CAF50; /* Xanh lá đậm cho tiêu đề */
            font-size: 36px;
            margin-bottom: 20px;
        }
        p {
            font-size: 18px;
            color: #333;
            margin-bottom: 20px;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            background-color: #4CAF50; /* Nút xanh lá */
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
            font-size: 16px;
        }
        .btn:hover {
            background-color: #45a049; /* Xanh lá đậm hơn khi hover */
        }
    </style>
</head>
<body>
    <div class="error-container">
        <h1>Oops! Có Lỗi Xảy Ra</h1>
        <p>
            <% 
                if (exception != null) {
                    out.println("Lỗi: " + exception.getMessage());
                } else {
                    out.println("Đã xảy ra lỗi không xác định. Vui lòng thử lại sau.");
                }
            %>
        </p>
        <a href="" class="btn">Quay Lại Trang Chủ</a>
    </div>
</body>
</html>