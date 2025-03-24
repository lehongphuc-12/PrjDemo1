console.log("LOGIN JS")

 // Cấu hình Validator cho form-2
        Validator({
            form: "#form-2",
            formGroup: ".form-group",
            errorSelector: '.form-message',
            rules: [
                Validator.isRequired('#full-name-register', 'Vui lòng nhập họ và tên'),
                Validator.isRequired('#email-register'),
                Validator.isEmail('#email-register'),
                    Validator.isRequired('#password-register', 'Vui lòng nhập mật khẩu'),
                    Validator.minLength('#password-register', 8),
                    Validator.isRequired('#password_confirmation-register', 'Vui lòng nhập lại mật khẩu'),
                    Validator.isConfirmed('#password_confirmation-register', function () {
                        return document.querySelector('#form-2 #password-register').value;
                    }, 'Mật khẩu nhập lại không chính xác'),
                Validator.isPhone('#phone-register', 'Vui lòng nhập số điện thoại'),
                Validator.isRequired('#address-register', 'Vui lòng nhập địa chỉ'),
            ],
            onSubmit: function (data) {
                document.querySelector("#form-2").submit();
            }
        });

        // Cấu hình Validator cho form-1
        Validator({
            form: "#form-1",
            formGroup: ".form-group",
            errorSelector: '.form-message',
            rules: [
                Validator.isRequired('#email-login', 'Vui lòng nhập email'),
                Validator.isEmail('#email-login'),
                Validator.isRequired('#password-login', 'Vui lòng nhập mật khẩu'),
                Validator.minLength('#password-login', 8),
            ],
            onSubmit: function (data) {
                document.querySelector("#form-1").submit();
            }
        });