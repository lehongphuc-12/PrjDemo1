<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sửa Hồ Sơ</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/edit_profile.css">

</head>
<body>
    <div class="form-container">
        <h2>Sửa Hồ Sơ</h2>
        <form action="${pageContext.request.contextPath}/admin" method="POST" onsubmit="return confirm('Bạn có chắc muốn cập nhật hồ sơ không?');">
            <input type="hidden" name="action" value="updateProfile">
            <div class="left-column">
                <div class="form-group">
                    <label for="fullName">Họ và Tên</label>
                    <input type="text" id="fullName" name="fullName" value="${user.fullName}" maxlength="100" required>
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="text" id="email" name="email" value="${user.email}" maxlength="100" required>
                </div>
                <div class="form-group">
                    <label for="password">Mật khẩu (để trống nếu không đổi)</label>
                    <input type="password" id="password" name="password" maxlength="100">
                </div>
            </div>
            <div class="right-column">
                <div class="form-group">
                    <label for="phoneNumber">Số điện thoại</label>
                    <input type="text" id="phoneNumber" name="phoneNumber" value="${user.phoneNumber}" maxlength="100" required>
                </div>
                <div class="form-group">
                    <label for="address">Địa chỉ</label>
                    <input type="text" id="address" name="address" value="${user.address}" maxlength="100" required>
                </div>
            </div>
            <input type="submit" value="Cập nhật" class="submit-btn">
        </form>
    </div>
</body>
</html>