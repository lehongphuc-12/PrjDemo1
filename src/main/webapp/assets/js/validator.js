

//Đối tượng validator
function Validator(options){

    function getParent (element , fromGroup){
        while(element.parentElement){      
            if(element.parentElement.matches(fromGroup)){
                return element.parentElement;
            }
            element = element.parentElement;
            }
    }


    //Chứa các rule của các selector
    var selectorRules = {}

    //Hàm thực hiện validate
    function validator(inputElement, rule){
        var errorElement = getParent(inputElement, options.formGroup).querySelector(options.errorSelector);
        // var errorMessage = rule.test (inputElement.value);
        var errorMessage;

        //Lấy qua các rule của selector
        var rules = selectorRules[rule.selector];

        //Lặp qua từng rule và kiểm tra
        //Nếu có lỗi thì dừng việc kiểm tra
        for(let i = 0; i< rules.length; i++){
            switch (inputElement.type){
                case 'checkbox':
                case 'radio':
                    errorMessage = rules[i](
                        formElement.querySelector(rule.selector + ':checked') ? "checked" : " " //nếu có thì trả về "checked" để hàm test ko báo lỗi,
                                                                                                // nếu ko có thì query sẽ trả về null làm sai hàm trim() ở test
                    );
                    break;
                default:
                    errorMessage = rules[i](inputElement.value);
            }
            
            if(errorMessage) break;
        }

        if(errorMessage){
            errorElement.innerText = errorMessage;
            getParent(inputElement, options.formGroup).classList.add('invalid');
        }else{
            errorElement.innerText = "";
            getParent(inputElement, options.formGroup).classList.remove('invalid');
            }

        // !! : Chuyển đổi kiểu dữ liệu sang boolean.    
        return !errorMessage;
    }

    //Lấy element của form
    var formElement = document.querySelector(options.form);

    if(formElement){


        

        //Khi submit form, prevent sự kiện submit
        formElement.onsubmit = function(e){
            e.preventDefault();

            var isFormValid = true;

            //Lặp qua từng rule và validate
            options.rules.forEach( function (rule) {
                var inputElement = formElement.querySelector(rule.selector);
                var isValid = validator(inputElement,rule);
                if(!isValid){
                    isFormValid = false;
                }
            });


           

            if(isFormValid){
                //Trường hợp submit với javascript
                if( typeof options.onSubmit === 'function'){

                    var enableInputs = formElement.querySelectorAll('[name]:not([disabled])')//Lấy tất cả element có thuộc tính [name] và ko disable
                    var formValues = Array.from(enableInputs).reduce(function(values, input){
                        switch(input.type){
                            case 'checkbox':
                            case 'radio':
                                //Nếu submit với checkbox/radio thì chỉ lấy checked point
                                if(input.matches(':checked')){
                                    if(Array.isArray(values[input.name])){
                                        values[input.name] .push(input.value)
                                    }else{
                                        values[input.name] = [input.value]
                                    }
                                }else{
                                    //Để nếu như ko check vào ô nào thì checkbox vẫn in ra trong console là  [],
                                    // * Chỉ test không cần thêm vào code
                                    if(!Array.isArray(values[input.name])){
                                        values[input.name] = []
                                    }
                                }
                                break;

                            case 'file': 
                                values[input.name] = input.files; //là một FileList chứa danh sách các tệp mà người dùng đã chọn qua
                                break;
                            
                            default:
                                values[input.name] = input.value; 
                        }
                        // return (values[input.name] = input.value) && values;
                        
                        return values;
                    }, {} );
                    options.onSubmit(formValues)
                }
                //Trường hợp submit với hành vi mặc định
                else{
                    formElement.submit();
                }
            }
        }

        //Lăp qua mỗi rule và xử lý (Lắng nghe sự kiên blur,oninput......)
        options.rules.forEach( function (rule) {

            //Lưu lại các rule cho mỗi input

            if(Array.isArray(selectorRules[rule.selector])){
                selectorRules[rule.selector].push(rule.test);
            }else{
                selectorRules[rule.selector] = [rule.test];
            }

            var inputElements = formElement.querySelectorAll(rule.selector);

            Array.from(inputElements).forEach(function (inputElement) {
                //Xử lý trường hợp blur khỏi input
                inputElement.onblur = function(){
                    validator(inputElement,rule);
                }

                //Xử lý mỗi khi người dùng nhập vào input
                inputElement.oninput = function () {
                    var errorElement = getParent(inputElement, options.formGroup).querySelector(options.errorSelector);
                    errorElement.innerText = "";
                    inputElement.parentElement.classList.remove('invalid');
                }
            })
        })
    }

}

//Định nghĩa các rule
//Nguyên tắc của các rule
// 1. Khi có lỗi => Trả ra message lỗi
// 2. Khhi hợp lệ => Không trả gì cả (underfined)
Validator.isRequired = function(selector, message){
    return {
        selector: selector,
        test: function (value) {
            return value.trim() ? undefined : message  || 'Vui lòng nhập trường này' 
        }
    }
}
Validator.isEmail = function(selector, message){
    return {
            selector: selector,
            test: function (value) {
                var regex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
                return regex.test(value) ? undefined : message  || "Trường này phải là email"
            }
        }
}

Validator.minLength = function(selector, min, message){
    return {
            selector: selector,
            test: function (value) {
                return value.length >= min ? undefined : message  || `Vui lòng nhập tối thiểu ${min} ký tự`
            }
        }
}

Validator.isConfirmed = function(selector, getConfirmValue, message){
    return{
        selector :selector,
        test : function (value) {
            return value === getConfirmValue() ? undefined : message  || 'Giá trị nhập vào không chính xác'
         }
    }
}
