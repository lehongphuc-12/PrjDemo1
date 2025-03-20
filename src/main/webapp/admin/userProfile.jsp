<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String referer = request.getHeader("Referer");
    if (referer == null || referer.isEmpty()) {
        referer = request.getContextPath() + "/admin"; // Mặc định về trang chủ nếu không có referer
    }
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hồ Sơ Người Dùng ${profileUser.fullName}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/user_profile.css">

</head>
<body>
    <div class="profile-container">
        <h2>Hồ Sơ Người Dùng</h2>
        <table>
            <tr>
                <th>Thuộc Tính</th>
                <th>Giá Trị</th>
            </tr>
            <tr>
                <td>ID</td>
                <td>${profileUser.userID}</td>
            </tr>
            <tr>
                <td>Tên</td>
                <td>${profileUser.fullName}</td>
            </tr>
            <tr>
                <td>Email</td>
                <td>${profileUser.email}</td>
            </tr>
            <tr>
                <td>Mật Khẩu</td>
                <td>********</td>
            </tr>
            <tr>
                <td>Số Điện Thoại</td>
                <td>${profileUser.phoneNumber}</td>
            </tr>
            <tr>
                <td>Vai Trò</td>
                <td>${profileUser.roleID.roleName}</td>
            </tr>
            <tr>
                <td>Địa Chỉ</td>
                <td>${profileUser.address}</td>
            </tr>
            <tr>
                <td>Ngày Tạo</td>
                <td>${profileUser.createdAt}</td>
            </tr>
        </table>
        <div class="btn-container">
            <a href="<%= referer %>" class="btn">Quay Lại</a>
        </div>
    </div>
</body>
</html>