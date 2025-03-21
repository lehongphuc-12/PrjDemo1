<%@ page import="model.User" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>VALIDATION FORM</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login.css">
</head>
<body>
    <div class="background_login"></div>
    <div class="main">
       
        <c:set var="gooleUser" value="sessionScope.googleUser"></c:set>
        <form action="${pageContext.request.contextPath}/logins" method="POST" class="form ${googleUser == null ?"active":""}" id="form-1">
            
            <div class="logo_login">
                 <img src="${pageContext.request.contextPath}/assets/images/logo.png" alt="">
            </div>
            
            <h3 class="heading">LOGIN</h3>
            <div class="spacer"></div>

            <div class="form-group">
                <label for="email-login" class="form-label">Email</label>
                <input type="text" id="email-login" name="email-login" placeholder="VD: email@domain.com" class="form-control">
                <span class="form-message"></span>
            </div>
    
            <div class="form-group">
                <label for="password-login" class="form-label">Mật khẩu</label>
                <input type="password" id="password-login" name="password-login" placeholder="Nhập mật khẩu" class="form-control">
                <span class="form-message"></span>
            </div>

            <div class="remember">
                <input type="checkbox" id="remember_me" name="remember_me">
                <label for="remember_me">Remember me</label>
            </div>

            <button type="submit" class="form-submit">Đăng nhập</button>
            
            <div style="margin-top: 12px; text-align: center;">
                <span style="display: inline-block; font-size: 12px; color: blue">
                    <a id="to-register" href="#" style="cursor: pointer; text-decoration: none;">Tạo tài khoản mới!</a>
                </span>
                <div style="margin-top: 8px; font-size: 12px;" class="google-login">
                    <a id="google-button" href="https://accounts.google.com/o/oauth2/auth?scope=email profile openid&redirect_uri=http://localhost:8080/demo1/LoginGoogleHandler&response_type=code&client_id=341860101658-rg8odfj8pfkeqb2rmug1n5t26gmovba3.apps.googleusercontent.com&approval_prompt=force" style="text-decoration: none;">                    
                        <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/Google_%22G%22_logo.svg/24px-Google_%22G%22_logo.svg.png" alt="Google Logo" style="vertical-align: middle;">
                        Login With Google
                    </a>
                </div>
            </div>
        </form>

        <form action="${pageContext.request.contextPath}/register" method="POST" class="form ${googleUser != null ?"active":""}" id="form-2">
            
            <div class="logo_login">
                 <img src="${pageContext.request.contextPath}/assets/images/logo.png" alt="">
            </div>
            
            <h3 class="heading">REGISTER</h3>
            <div class="spacer"></div>
            
            <div class="form-group">
                <label for="email-register" class="form-label">Email</label>
                <input type="text" id="email-register" name="email-register" placeholder="VD: email@domain.com" class="form-control" value="${ googleUser != null ? googleUser.email : "" }" readonly>
                <span class="form-message"></span>
            </div>
            
            <div class="form-group">
                <label for="full-name-register" class="form-label">Họ và Tên</label>
                <input type="text" id="full-name-register" name="full-name-register" placeholder="Nhập họ và tên" class="form-control" value="${ googleUser != null ? googleUser.fullName : "" }" required>
                <span class="form-message"></span>
            </div>
            

            <div class="form-group" id="password-group" style="${ gooleUser ? "display: none;" : "" }">
                <label for="password-register" class="form-label">Mật khẩu</label>
                <input type="password" id="password-register" name="password-register" placeholder="Nhập mật khẩu" class="form-control">
                <span class="form-message"></span>
            </div>

            <div class="form-group" id="confirm-password-group" style="${ gooleUser ? "display: none;" : "" }">
                <label for="password_confirmation-register" class="form-label">Nhập lại mật khẩu</label>
                <input type="password" id="password_confirmation-register" name="password_confirmation-register" placeholder="Nhập lại mật khẩu" class="form-control">
                <span class="form-message"></span>
            </div>
            
            <div class="form-group">
                <label for="phone-register" class="form-label">Số điện thoại</label>
                <input type="text" id="phone-register" name="phone-register" placeholder="Nhập số điện thoại" class="form-control">
                <span class="form-message"></span>
            </div>
            
            <div class="form-group">
                <label for="address-register" class="form-label">Địa chỉ</label>
                <input type="text" id="address-register" name="address-register" placeholder="Nhập địa chỉ" class="form-control">
                <span class="form-message"></span>
            </div>

            <button type="submit" class="form-submit">Đăng ký</button>
            
            <span style="margin-top: 12px; display: inline-block; font-size: 12px; color: blue">
                <a id="to-login" href="#" style="cursor: pointer; text-decoration: none;">Trở về trang đăng nhập</a>
            </span>
        </form>
    </div>



    <script src="${pageContext.request.contextPath}/assets/js/validator.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/login.js"></script>
</body>
</html>