<%-- 
    Document   : Hoso
    Created on : Mar 9, 2025, 9:22:57 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <div id="profile-section" class="section active">
        <div class="profile-header">
            <h2>Hồ Sơ Của Tôi</h2>
            <p>Quản lý thông tin hồ sơ để bảo mật tài khoản</p>
        </div>
        <form>
            <div class="form-group">
                <label for="username">Tên đăng nhập</label>
                <div class="input-wrapper">
                    <input type="text" class="form-control" id="username" value="${user.fullName}" readonly>
                    <small class="text-muted">Tên Đăng nhập chỉ có thể thay đổi khi gọi hotline.</small>
                </div>
            </div>
            <div class="form-group">
                <label for="shopName">Tên shop</label>
                <div class="input-wrapper">
                    <input type="text" class="form-control" id="shopName" value="${user.shopName}" >
                    
                </div>
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <div class="input-wrapper">
                    <input type="email" class="form-control" id="email" value="${user.email}" >
                    <small><a href="#">Thay Đổi</a></small>
                </div>
            </div>
            <div class="form-group">
                <label for="phone">Số điện thoại</label>
                <div class="input-wrapper">
                    <input type="tel" class="form-control" id="phone" value="${user.phoneNumber}">
                </div>
            </div>
            <div class="form-group">
                <label for="email">Địa chỉ</label>
                <div class="input-wrapper">
                    <input type="text" class="form-control" id="address" value="${user.address}" >
                    <small><a href="#">Thay Đổi</a></small>
                </div>
            </div>
            <button type="button" class="save-btn" onclick="updateProfile()">Lưu</button>
        </form>
    </div>
</html>
