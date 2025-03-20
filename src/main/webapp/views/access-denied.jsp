<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Truy cập bị từ chối</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            padding: 50px;
            background-color: #f4f4f4;
        }
        h1 {
            color: #dc3545;
        }
        a {
            color: #007BFF;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <h1>Truy cập bị từ chối</h1>
    <p>Bạn không có quyền truy cập vào trang này.</p>
    <p><a href="${pageContext.request.contextPath}/views/home.jsp">Quay lại trang chủ</a></p>
</body>
</html>