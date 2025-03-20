<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Sửa thông tin người dùng</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/edit_user.css">

    </head>
    <body>   
        <div class="form-container">
            <h2>Edit User</h2>
            <form action="admin" method="POST" onsubmit="return confirm('Are you sure you want to update this user?');">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="id" value="${user.userID}">
                <div class="left-column">
                    <div class="form-group">
                        <label for="fullName">Name</label>
                        <input type="text" id="fullName" name="fullName" value="${user.fullName}" maxlength="100" required>
                    </div>
                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="text" id="email" name="email" value="${user.email}" maxlength="100" required>
                    </div>
                    <div class="form-group">
                        <label for="password">Password (leave blank to keep current)</label>
                        <input type="password" id="password" name="password" maxlength="100">
                    </div>
                    <div class="form-group">
                        <label for="phoneNumber">Phone</label>
                        <input type="text" id="phoneNumber" name="phoneNumber" value="${user.phoneNumber}" maxlength="100" required>
                    </div>
                </div>
                <div class="right-column">
                    <div class="form-group">
                        <label for="roleID">Role</label>
                        <select name="roleID">
                            <option value="1" ${user.roleID.roleID == 1 ? 'selected' : ''}>Admin</option>
                            <option value="2" ${user.roleID.roleID == 2 ? 'selected' : ''}>Seller</option>
                            <option value="3" ${user.roleID.roleID == 3 ? 'selected' : ''}>User</option>
                            <!-- Thêm các vai trò khác nếu cần -->
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="address">Address</label>
                        <input type="text" id="address" name="address" value="${user.address}" maxlength="100" required>
                    </div>      
                </div>
                <input type="submit" value="Cập nhật" class="submit-btn">
            </form>
        </div>
    </body>
</html>