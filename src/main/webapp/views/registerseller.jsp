<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký bán hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/registerseller.css">
</head>
<body>
    <div class="form-container">
        <h2>Đăng ký trở thành người bán</h2>
        <form action="${pageContext.request.contextPath}/SellerRegistrationServlet" method="post">
            <div class="form-group">
                <label for="fullName">Họ và tên</label>
                <input type="text" id="fullName" name="fullName" value="${user.fullName}" readonly required>
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input type="text" id="email" name="email" value="${user.email}" readonly required>
            </div>
            <div class="form-group">
                <label for="phone">Số điện thoại</label>
                <input type="text" id="phone" name="phone" value="${user.phoneNumber}" readonly required>
            </div>
            <div class="form-group">
                <label for="address">Địa chỉ</label>
                <input type="text" id="address" name="address" value="${user.address}" readonly required>
            </div>
            <div class="form-group">
                <label for="shopName">Tên cửa hàng</label>
                <input type="text" id="shopName" name="shopName" placeholder="Nhập tên cửa hàng" required>
            </div>
            <input type="hidden" name="userId" value="${user.userID}">
            <button type="submit">Gửi yêu cầu</button>
        </form>
        <div class="message">
            <% 
                String message = (String) request.getAttribute("message");
                String error = (String) request.getSession().getAttribute("error");
                if (message != null) {
                    out.print("<p class='success'>" + message + "</p>");
                }
                if (error != null) {
                    out.print("<p class='error'>" + error + "</p>");
                    request.getSession().removeAttribute("error");
                }
            %>
        </div>
    </div>
</body>
</html>