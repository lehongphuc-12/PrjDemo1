<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Lỗi</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container my-4">
        <div class="alert alert-danger" role="alert">
            <h4 class="alert-heading">Đã xảy ra lỗi!</h4>
            <p>${errorMessage}</p>
            <hr>
            <p class="mb-0">Vui lòng thử lại hoặc liên hệ hỗ trợ nếu vấn đề vẫn tiếp diễn.</p>
        </div>
        <div class="text-center">
            <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">Quay lại cửa hàng</a>
        </div>
    </div>
</body>
</html>