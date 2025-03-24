<%-- 
    Document   : changePassword
    Created on : Mar 13, 2025, 6:16:33 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<style>
    .input-wrapper {
        position: relative;
        display: flex;
        align-items: center;
        width: 100%;
    }

    .input-wrapper input {
        width: 100%;
        padding-right: 40px; /* Chừa khoảng trống cho icon */
    }

    .input-wrapper button {
        position: absolute;
        right: 10px;
        background: none;
        border: none;
        cursor: pointer;
        font-size: 18px;
        color: #666;
        top: 7px;
    }

    .input-wrapper button:hover {
        color: #333;
    }
</style>
<form id="change-password-form" style="margin: 0 auto; width: 50%">
    <div class="profile-header">
        <h2>Đổi Mật Khẩu</h2>
        <p>Cập nhật mật khẩu để bảo vệ tài khoản của bạn</p>
    </div>
    <div class="form-group">
        <label for="current-password">Mật khẩu hiện tại</label>
        <div class="input-wrapper">
            <input type="password" class="form-control password" id="current-password"  name="current-password"
                   placeholder="Nhập mật khẩu hiện tại" required>
            <button type="button" onclick="togglePassword(this)">👁</button>

        </div>
    </div>
    <button type="button" class="save-btn" onclick="verifyCurrentPassword()">Next</button>
</form>

<div id="new-password-section" class="section" style="display: none; margin: 0 auto; width: 50%">
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
                <button type="button" onclick="togglePassword(this)">👁</button>

            </div>
        </div>
        <div class="form-group">
            <label for="confirm-password">Xác nhận mật khẩu</label>
            <div class="input-wrapper">
                <input type="password" class="form-control" id="confirm-password" name="confirmPassword"
                       placeholder="Xác nhận mật khẩu mới" required>
                <button type="button" onclick="togglePassword(this)">👁</button>

            </div>
        </div>
        <button type="button" class="save-btn" onclick="updatePassword()">Lưu</button>
    </form>
</div>