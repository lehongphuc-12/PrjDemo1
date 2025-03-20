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
    <div class="main">
        <form action="${pageContext.request.contextPath}/logins" method="POST" class="form ${param.register == 'true' ? '' : 'active'}" id="form-1">
            <h3 class="heading">LOGIN</h3>
            <div class="spacer"></div>

            <c:if test="${not empty error}">
                <p style="color: red; text-align: center;">${error}</p>
            </c:if>

            <div class="form-group">
                <label for="email-login" class="form-label">Email</label>
                <input type="text" id="email-login" name="email-login" value="${rememberEmail}" placeholder="VD: email@domain.com" class="form-control">
                <span class="form-message"></span>
            </div>
    
            <div class="form-group">
                <label for="password-login" class="form-label">Mật khẩu</label>
                <input type="password" id="password-login" name="password-login" value="${rememberPassword}" placeholder="Nhập mật khẩu" class="form-control">
                <span class="form-message"></span>
            </div>

            <div class="remember">
                <input type="checkbox" id="remember_me" name="remember_me" ${not empty rememberPassword ? 'checked' : ''}>
                <label for="remember_me">Remember me</label>
            </div>

            <button type="submit" class="form-submit">Đăng nhập</button>
            
            <div style="margin-top: 12px; text-align: center;">
                <span style="display: inline-block; font-size: 12px; color: blue">
                    <a href="?register=true">Tạo tài khoản mới!</a>
                </span>
                <div style="margin-top: 8px; font-size: 12px;">
                    <a href="https://accounts.google.com/o/oauth2/auth?scope=email profile openid&redirect_uri=http://localhost:8080/demo1/LoginGoogleHandler&response_type=code&client_id=341860101658-rg8odfj8pfkeqb2rmug1n5t26gmovba3.apps.googleusercontent.com&approval_prompt=force" style="text-decoration: none;">
                        <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/Google_%22G%22_logo.svg/24px-Google_%22G%22_logo.svg.png" alt="Google Logo" style="vertical-align: middle;">
                        Login With Google
                    </a>
                </div>
            </div>
        </form>

        <form action="${pageContext.request.contextPath}/register" method="POST" class="form ${param.register == 'true' ? 'active' : ''}" id="form-2">
            <h3 class="heading">REGISTER</h3>
            <div class="spacer"></div>
            
            <c:if test="${not empty error}">
                <p style="color: red; text-align: center;">${error}</p>
            </c:if>

            <div class="form-group">
                <label for="full-name-register" class="form-label">Họ và Tên</label>
                <input type="text" id="full-name-register" name="full-name-register" value="${sessionScope.googleUser != null ? sessionScope.googleUser.name : ''}" placeholder="Nhập họ và tên" class="form-control" required>
                <span class="form-message"></span>
            </div>
            
            <div class="form-group">
                <label for="email-register" class="form-label">Email</label>
                <input type="text" id="email-register" name="email-register" value="${sessionScope.googleUser != null ? sessionScope.googleUser.email : ''}" placeholder="VD: email@domain.com" class="form-control" readonly>
                <span class="form-message"></span>
            </div>

            <c:if test="${empty sessionScope.googleUser}">
                <div class="form-group">
                    <label for="password-register" class="form-label">Mật khẩu</label>
                    <input type="password" id="password-register" name="password-register" placeholder="Nhập mật khẩu" class="form-control">
                    <span class="form-message"></span>
                </div>

                <div class="form-group">
                    <label for="password_confirmation-register" class="form-label">Nhập lại mật khẩu</label>
                    <input type="password" id="password_confirmation-register" name="password_confirmation-register" placeholder="Nhập lại mật khẩu" class="form-control">
                    <span class="form-message"></span>
                </div>
            </c:if>
            
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
                <a href="${pageContext.request.contextPath}/logins">Trở về trang đăng nhập</a>
            </span>
        </form>
    </div>

    <script src="${pageContext.request.contextPath}/assets/js/validator.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
    <script>
        Validator({
            form: "#form-2",
            formGroup: ".form-group",
            errorSelector: '.form-message',
            rules: [
                Validator.isRequired('#full-name-register', 'Vui lòng nhập họ và tên'),
                Validator.isRequired('#email-register'),
                Validator.isEmail('#email-register'),
                <c:if test="${empty sessionScope.googleUser}">
                    Validator.isRequired('#password-register'),
                    Validator.minLength('#password-register', 6),
                    Validator.isRequired('#password_confirmation-register'),
                    Validator.isConfirmed('#password_confirmation-register', function () {
                        return document.querySelector('#form-2 #password-register').value;
                    }, 'Mật khẩu nhập lại không chính xác'),
                </c:if>
                Validator.isRequired('#phone-register', 'Vui lòng nhập số điện thoại'),
                Validator.isPhone('#phone-register', 'Số điện thoại phải là 10 chữ số'),
                Validator.isRequired('#address-register', 'Vui lòng nhập địa chỉ'),
            ],
            onSubmit: function (data) {
                document.querySelector("#form-2").submit();
            }
        });

        Validator({
            form: "#form-1",
            formGroup: ".form-group",
            errorSelector: '.form-message',
            rules: [
                Validator.isRequired('#email-login'),
                Validator.isEmail('#email-login'),
                Validator.isRequired('#password-login'),
                Validator.minLength('#password-login', 6),
            ],
            onSubmit: function (data) {
                document.querySelector("#form-1").submit();
            }
        });
    </script>
</body>
</html>