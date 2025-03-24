<%-- 
    Document   : changePassword
    Created on : Mar 13, 2025, 6:16:33 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<form id="change-password-form">
    <div class="profile-header">
        <h2>Đổi Mật Khẩu</h2>
        <p>Cập nhật mật khẩu để bảo vệ tài khoản của bạn</p>
    </div>
    <div class="form-group">
        <label for="current-password">Mật khẩu hiện tại</label>
        <div class="input-wrapper">
            <input type="password" class="form-control" id="current-password" name="current-password"
                   placeholder="Nhập mật khẩu hiện tại" required>
        </div>
    </div>
    <button type="button" class="save-btn" onclick="verifyCurrentPassword()">Next</button>
</form>

<div id="new-password-section" class="section" style="display: none;">
    <div class="profile-header">
        <h2>Đổi Mật Khẩu</h2>
        <p>Cập nhật mật khẩu để bảo vệ tài khoản của bạn</p>
    </div>
    <form id="update-password-form">
        <div class="form-group">
            <label for="new-password">Mật khẩu mới</label>
            <div class="input-wrapper">
                <input type="password" class="form-control" id="new-password" name="newPassword"
                       placeholder="Nhập mật khẩu mới" required>
            </div>
        </div>
        <div class="form-group">
            <label for="confirm-password">Xác nhận mật khẩu</label>
            <div class="input-wrapper">
                <input type="password" class="form-control" id="confirm-password" name="confirmPassword"
                       placeholder="Xác nhận mật khẩu mới" required>
            </div>
        </div>
        <button type="button" class="save-btn" onclick="updatePassword()">Lưu</button>
    </form>
</div>

