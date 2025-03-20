function Validator(options) {
    function getParent(element, fromGroup) {
        while (element.parentElement) {
            if (element.parentElement.matches(fromGroup)) {
                return element.parentElement;
            }
            element = element.parentElement;
        }
        console.error('Không tìm thấy phần tử cha với selector:', fromGroup);
        return null;
    }

    var selectorRules = {};

    function validator(inputElement, rule) {
        var errorElement = getParent(inputElement, options.formGroup)?.querySelector(options.errorSelector);
        console.log('inputElement:', inputElement);
        console.log('errorElement:', errorElement);
        if (!errorElement) {
            console.error('Không tìm thấy errorElement cho', inputElement);
            return false;
        }

        var errorMessage;
        var rules = selectorRules[rule.selector];
        console.log('rules:', rules);

        for (let i = 0; i < rules.length; i++) {
            switch (inputElement.type) {
                case 'checkbox':
                case 'radio':
                    errorMessage = rules[i](
                        formElement.querySelector(rule.selector + ':checked') ? "checked" : " "
                    );
                    break;
                default:
                    errorMessage = rules[i](inputElement.value);
            }
            if (errorMessage) break;
        }

        if (errorMessage) {
            errorElement.innerText = errorMessage;
            getParent(inputElement, options.formGroup).classList.add('invalid');
        } else {
            errorElement.innerText = "";
            getParent(inputElement, options.formGroup).classList.remove('invalid');
        }
        return !errorMessage;
    }

    var formElement = document.querySelector(options.form);
    if (!formElement) {
        console.error('Không tìm thấy form với selector:', options.form);
        return;
    }

    formElement.onsubmit = function(e) {
        e.preventDefault();
        var isFormValid = true;

        options.rules.forEach(function(rule) {
            var inputElement = formElement.querySelector(rule.selector);
            var isValid = validator(inputElement, rule);
            if (!isValid) {
                isFormValid = false;
            }
        });

        if (isFormValid) {
            if (typeof options.onSubmit === 'function') {
                var enableInputs = formElement.querySelectorAll('[name]:not([disabled])');
                var formValues = Array.from(enableInputs).reduce(function(values, input) {
                    switch (input.type) {
                        case 'checkbox':
                        case 'radio':
                            if (input.matches(':checked')) {
                                if (Array.isArray(values[input.name])) {
                                    values[input.name].push(input.value);
                                } else {
                                    values[input.name] = [input.value];
                                }
                            } else if (!values[input.name]) {
                                values[input.name] = [];
                            }
                            break;
                        default:
                            values[input.name] = input.value;
                    }
                    return values;
                }, {});
                options.onSubmit(formValues);
            } else {
                formElement.submit();
            }
        }
    };

    options.rules.forEach(function(rule) {
        if (Array.isArray(selectorRules[rule.selector])) {
            selectorRules[rule.selector].push(rule.test);
        } else {
            selectorRules[rule.selector] = [rule.test];
        }

        var inputElements = formElement.querySelectorAll(rule.selector);
        console.log('selector:', rule.selector, 'inputElements:', inputElements);

        Array.from(inputElements).forEach(function(inputElement) {
            inputElement.onblur = function() {
                console.log('onblur triggered for', inputElement.id);
                validator(inputElement, rule);
            };

            inputElement.oninput = function() {
                var errorElement = getParent(inputElement, options.formGroup)?.querySelector(options.errorSelector);
                if (errorElement) {
                    errorElement.innerText = "";
                    getParent(inputElement, options.formGroup).classList.remove('invalid');
                }
            };
        });
    });
}

Validator.isRequired = function(selector, message) {
    return {
        selector: selector,
        test: function(value) {
            return value.trim() ? undefined : message || 'Vui lòng nhập trường này';
        }
    };
};

Validator.isEmail = function(selector, message) {
    return {
        selector: selector,
        test: function(value) {
            var regex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
            return regex.test(value) ? undefined : message || "Trường này phải là email";
        }
    };
};

Validator.minLength = function(selector, min, message) {
    return {
        selector: selector,
        test: function(value) {
            return value.length >= min ? undefined : message || `Vui lòng nhập tối thiểu ${min} ký tự`;
        }
    };
};

Validator.isConfirmed = function(selector, getConfirmValue, message) {
    return {
        selector: selector,
        test: function(value) {
            return value === getConfirmValue() ? undefined : message || 'Giá trị nhập vào không chính xác';
        }
    };
};

Validator.isPhone = function(selector, message) {
    return {
        selector: selector,
        test: function(value) {
            const phoneRegex = /^(0|\+84)(3[2-9]|5[2689]|7[06-9]|8[1-9]|9[0-9])\d{7}$/;
            return phoneRegex.test(value) ? undefined : message || 'Số điện thoại không hợp lệ';
        }
    };
};