<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Đặt Lại Mật Khẩu</title>
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
    <form action="${pageContext.request.contextPath}/reset-password" method="post" id="form" class="form" style="display: block">
        <h3 class="heading">ĐẶT LẠI MẬT KHẨU</h3>
        <div class="spacer"></div>
        
            <div class="form-group">
                <label for="email-register" class="form-label">Email</label>
                    <input type="text" id="email-register" name="email-register" placeholder="VD: email@domain.com" class="form-control" value="${email}">
                <span class="form-message"></span>
            </div> 
            <div class="form-group">
                <label for="email-register" class="form-label">Password</label>
                    <input type="text" id="password-register" name="password-register" placeholder="" class="form-control">
                <span class="form-message"></span>
            </div>        
                
                <div style="margin-top: 12px">
                <p style="color: black;font-size: 12px;font-weight: 500">
                    <c:out value="${message}" default=""/>
                    
                </p><!-- comment -->
            </div>
                
                <button type="submit" class="form-submit">Gửi yêu cầu</button>
                
                
    </form>    
    <script src="${pageContext.request.contextPath}/assets/js/validator.js"></script>
    <script>
        
        Validator({
            form: "#form",
            formGroup: ".form-group",
            errorSelector: '.form-message',
            rules: [
                
                Validator.isRequired('#email-register'),
                Validator.isEmail('#email-register'),
                Validator.isRequired('#password-register', 'Vui lòng nhập mật khẩu'),
                Validator.minLength('#password-register', 8),
            
            ],
            onSubmit: function (data) {
                document.querySelector("#form").submit();
            }
        });
    </script>
</body>
</html>
