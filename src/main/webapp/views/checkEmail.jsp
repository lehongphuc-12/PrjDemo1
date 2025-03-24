<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quên Mật Khẩu</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login.css">
    <style>
        
        .form{
            position: relative;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
        }
    </style>
</head>
<body>
    
    <form action="${pageContext.request.contextPath}/checkEmail" method="post" id="form" class="form" style="display: block">
        <h3 class="heading">QUÊN MẬT KHẨU</h3>
        <div class="spacer"></div>
            <div class="form-group">
                <label for="email-register" class="form-label">Email</label>
                    <input type="text" id="email-register" name="email-register" placeholder="VD: email@domain.com" class="form-control">
                <span class="form-message"></span>
            </div>        
                
                <button type="submit" class="form-submit">Gửi yêu cầu</button>
    </form>
    <p>${message}</p>
    
    <script src="${pageContext.request.contextPath}/assets/js/validator.js"></script>
    <script>
        
        Validator({
            form: "#form",
            formGroup: ".form-group",
            errorSelector: '.form-message',
            rules: [
                
                Validator.isRequired('#email-register'),
                Validator.isEmail('#email-register')
            
            ],
            onSubmit: function (data) {
                document.querySelector("#form").submit();
            }
        });
    </script>
</body>
</html>
