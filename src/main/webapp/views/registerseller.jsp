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
    <div class="profile-container">
        <h2>Đăng ký trở thành người bán</h2>
        <form action="${pageContext.request.contextPath}/SellerRegistrationServlet" method="post">
            <table>
                <tr>
                    <th>Thuộc Tính</th>
                    <th>Giá Trị</th>
                </tr>
                <tr>
                    <td>Họ và tên</td>
                    <td><input type="text" name="fullName" value="${user.fullName}" readonly required></td>
                </tr>
                <tr>
                    <td>Email</td>
                    <td><input type="text" name="email" value="${user.email}" readonly required></td>
                </tr>
                <tr>
                    <td>Số điện thoại</td>
                    <td><input type="text" name="phone" value="${user.phoneNumber}" required></td>
                </tr>
                <tr>
                    <td>Địa chỉ</td>
                    <td><input type="text" name="address" value="${user.address}" required></td>
                </tr>
                <tr>
                    <td>Tên cửa hàng</td>
                    <td><input type="text" name="shopName" placeholder="Nhập tên cửa hàng" required></td>
                </tr>
            </table>
            <input type="hidden" name="userId" value="${user.userID}">
            <div class="btn-container">
                <button type="submit" class="btn btn-info">Gửi yêu cầu</button>
                <button type="button" onclick="history.back()" class="btn btn-primary">Quay lại</button>
            </div>
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