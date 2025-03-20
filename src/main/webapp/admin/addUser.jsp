<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Thêm Người Dùng</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/add_user.css">

    </head>
    <body>   
        <div class="form-container">
            <h2>Thêm Người Dùng Mới</h2>
            <form action="admin" method="POST">
                <input type="hidden" name="action" value="insert">
                <div class="left-column">
                    <div class="form-group">
                        <label for="fullName">Tên</label>
                        <input type="text" id="fullName" name="fullName" maxlength="100" required>
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="text" id="email" name="email" maxlength="100" required>
                    </div>
                    <div class="form-group">
                        <label for="password">Mật Khẩu</label>
                        <input type="text" id="password" name="password" maxlength="100" required>
                    </div>
                    <div class="form-group">
                        <label for="phoneNumber">Số Điện Thoại</label>
                        <input type="text" id="phoneNumber" name="phoneNumber" maxlength="100" required>
                    </div>
                </div>
                <div class="right-column">
                    <div class="form-group">
                        <label for="roleID">Vai Trò</label>
                        <select id="roleID" name="roleID" required>
                            <option value="3">Customer</option>
                            <option value="2">Seller</option>
                            <option value="1">Admin</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="address">Địa Chỉ</label>
                        <input type="text" id="address" name="address" maxlength="100" required>
                    </div>      
                </div>
                <input type="submit" value="Thêm" class="submit-btn">
            </form>
        </div>
    </body>
</html>