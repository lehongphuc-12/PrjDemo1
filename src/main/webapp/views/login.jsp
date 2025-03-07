
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>VALIDATION FORM</title>
    <link rel="stylesheet" href="./assets/css/login.css">
</head>
<body>
    
    <div class="main">

        <form action="" method="POST" class="form active" id="form-1">
            <h3 class="heading">LOGIN</h3>

            <div class="spacer"></div>

            <div class="form-group ">
                <label for="email" class="form-label">Email</label>
                <input type="text" id="email-login" name="email-login" placeholder="VD: email@domain.com" class="form-control">
                <span class="form-message"></span>
            </div>
    
            <div class="form-group">
                <label for="password" class="form-label">Mật khẩu</label>
                <input type="password" id="password-login" name="password-login" placeholder="Nhập mật khẩu" class="form-control">
                <span class="form-message"></span>
            </div>

            <div class="remember">
                <input type="checkbox" id="remember_me" name="remember_me">
                <label for="remember_me">Remember me</label>
            </div>

            <button class="form-submit">Đăng nhập</button>
            
            <span style="margin-top: 12px ; display: inline-block; font-size: 12px; color: blue">
                <a id="to-register">Tạo tài khoản mới!</a>
            </span>

        </form>


        <form action="" method="POST" class="form" id="form-2">
            <h3 class="heading">REGISTER</h3>

            <div class="spacer"></div>

            <div class="form-group ">
                <label for="email" class="form-label">Email</label>
                <input type="text" id="email-register" name="email-register" placeholder="VD: email@domain.com" class="form-control">
                <span class="form-message"></span>
            </div>

            <div class="form-group">
                <label for="password" class="form-label">Mật khẩu</label>
                <input type="password" id="password-register" name="password-register" placeholder="Nhập mật khẩu" class="form-control">
                <span class="form-message"></span>
            </div>

            <div class="form-group">
                <label for="password_confirmation" class="form-label">Nhập lại mật khẩu</label>
                <input type="password" id="password_confirmation-register" name="password_confirmation-register" placeholder="Nhập lại mật khẩu" class="form-control">
                <span class="form-message"></span>
            </div>

            <button class="form-submit">Đăng ký</button>
            
            <span style="margin-top: 12px ; display: inline-block; font-size: 12px; color: blue">
                <a id="to-login">Trở về trang đăng nhập</a>
            </span>
            
        </form>

    </div>






    

    <script src="./assets/js/validator.js"></script>
    <script src="./assets/js/main.js"></script>
    <script>
        Validator({
            form : "#form-2",
            formGroup : ".form-group",
            errorSelector: '.form-message',
            rules: [

                Validator.isRequired('#email'),
                Validator.isEmail('#email'),

                Validator.isRequired('#password'),
                Validator.minLength('#password',6),

                Validator.isRequired('#password_confirmation'),
                Validator.isConfirmed('#password_confirmation', function(){
                    return document.querySelector('#form-1 #password').value;
                },'Mật khẩu nhập lại không chính xác'),

            ],
            onSubmit : function (data) {
                // call api
                console.log(data)
            }
        })


         Validator({
            form : "#form-1",
            formGroup : ".form-group",
            errorSelector: '.form-message',
            rules :[
            Validator.isRequired('#email'),
                Validator.isEmail('#email'),

                Validator.isRequired('#password'),
                Validator.minLength('#password',6),
            ],
            onSubmit : function (data) {
                // call api
                console.log(data)
            }
        })
    </script>
</body>
</html>
