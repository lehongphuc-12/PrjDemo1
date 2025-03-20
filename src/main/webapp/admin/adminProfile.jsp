<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hồ Sơ Quản Trị Viên ${sessionScope.user.fullName}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin_profile.css">

</head>
<body>
    <div class="profile-container">
        <h2>Hồ Sơ Admin</h2>
        <table>
            <tr>
                <th>Thuộc Tính</th>
                <th>Giá Trị</th>
            </tr>
            <tr>
                <td>Tên</td>
                <td>${sessionScope.user.fullName}</td>
            </tr>
            <tr>
                <td>Email</td>
                <td>${sessionScope.user.email}</td>
            </tr>
            <tr>
                <td>Mật Khẩu</td>
                <td>********</td>
            </tr>
            <tr>
                <td>Số Điện Thoại</td>
                <td>${sessionScope.user.phoneNumber}</td>
            </tr>
            <tr>
                <td>Vai Trò</td>
                <td>${sessionScope.user.roleID.roleName}</td> <!-- Giả sử Role có getRoleName() -->
            </tr>
            <tr>
                <td>Địa Chỉ</td>
                <td>${sessionScope.user.address}</td>
            </tr>
            <tr>
                <td>Ngày Tạo</td>
                <td>${sessionScope.user.address}</td>
            </tr>
        </table>
        <div class="btn-container">
            <a href="${pageContext.request.contextPath}/admin" class="btn">Quay Về Trang Chủ</a>
        </div>
    </div>
</body>
</html>