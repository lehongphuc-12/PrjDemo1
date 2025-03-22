$(document).ready(function() {
    $('#checkoutForm').on('submit', function(e) {
        let isValid = true;

        // Kiểm tra các trường required
        $(this).find('input[required]').each(function() {
            if (!$(this).val()) {
                isValid = false;
                $(this).addClass('is-invalid');
            } else {
                $(this).removeClass('is-invalid');
            }
        });

        // Kiểm tra phương thức thanh toán
        if (!$('input[name="paymentMethod"]:checked').val()) {
            isValid = false;
            alert('Vui lòng chọn phương thức thanh toán.');
        }

        // Kiểm tra địa chỉ giao hàng
        let fullName = $('#tempFullName').val() || $('#fullName').val();
        let phoneNumber = $('#tempPhoneNumber').val() || $('#phoneNumber').val();
        let address = $('#tempAddress').val() || $('#address').val();

        if (fullName.length < 2) {
            isValid = false;
            alert('Họ và tên phải có ít nhất 2 ký tự.');
            $('#fullName').addClass('is-invalid');
        }

        let phoneRegex = /^0\d{9}$/;
        if (phoneNumber && !phoneRegex.test(phoneNumber)) {
            isValid = false;
            alert('Số điện thoại không hợp lệ. Vui lòng nhập số điện thoại 10 chữ số, bắt đầu bằng 0.');
            $('#phoneNumber').addClass('is-invalid');
        }

        if (address.length < 5) {
            isValid = false;
            alert('Địa chỉ phải có ít nhất 5 ký tự.');
            $('#address').addClass('is-invalid');
        }

        if (!isValid) {
            e.preventDefault();
            alert('Vui lòng điền đầy đủ và đúng thông tin.');
        }
    });

    // Cập nhật phương thức thanh toán
    $('input[name="paymentMethod"]').on('change', function() {
        var methodName = $(this).data('method-name');
        $('#selectedPaymentMethod').val(methodName);
    });

    // Khởi tạo phương thức thanh toán mặc định
    var selectedRadio = $('input[name="paymentMethod"]:checked');
    if (selectedRadio.length) {
        $('#selectedPaymentMethod').val(selectedRadio.data('method-name'));
    }
});

function updateTempAddress() {
    let fullName = $('#editFullName').val();
    let phoneNumber = $('#editPhone').val();
    let address = $('#editAddress').val();

    if (!fullName || !phoneNumber || !address) {
        alert('Vui lòng điền đầy đủ thông tin.');
        return;
    }

    if (fullName.length < 2) {
        alert('Họ và tên phải có ít nhất 2 ký tự.');
        return;
    }

    let phoneRegex = /^0\d{9}$/;
    if (!phoneRegex.test(phoneNumber)) {
        alert('Số điện thoại không hợp lệ. Vui lòng nhập số điện thoại 10 chữ số, bắt đầu bằng 0.');
        return;
    }

    if (address.length < 5) {
        alert('Địa chỉ phải có ít nhất 5 ký tự.');
        return;
    }

    $('#displayFullName').text(fullName);
    $('#displayPhoneNumber').text(phoneNumber);
    $('#displayAddress').text(address);

    $('#tempFullName').val(fullName);
    $('#tempPhoneNumber').val(phoneNumber);
    $('#tempAddress').val(address);

    var modal = bootstrap.Modal.getInstance($('#editAddressModal')[0]);
    modal.hide();
}


window.addEventListener('beforeunload', function() {
        fetch('${pageContext.request.contextPath}/checkout?action=clearSession', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
        });
    });
