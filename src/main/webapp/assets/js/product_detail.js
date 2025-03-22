console.log("PRODUCT DETAIL JS")

// Back to top button
$(window)?.scroll(function () {
    if ($(this).scrollTop() > 100) {
        $('.back-to-top').fadeIn('slow');
    } else {
        $('.back-to-top').fadeOut('slow');
    }
});
$('.back-to-top').click(function () {
    $('html, body').animate({scrollTop: 0}, 1500, 'easeInOutExpo');
    return false;
});

// Hệ thống đánh giá bằng sao
$(document).ready(function(){
    $('.star-rating .fa-star').on('click', function(){
        var ratingValue = $(this).data('value');
        $('#rating').val(ratingValue);
        $('.star-rating .fa-star').removeClass('checked');
        $('.star-rating .fa-star').each(function(){
            if ($(this).data('value') <= ratingValue) {
                $(this).addClass('checked');
            }
        });
    });

    $('.star-rating .fa-star').on('mouseover', function(){
        var ratingValue = $(this).data('value');
        $('.star-rating .fa-star').removeClass('checked');
        $('.star-rating .fa-star').each(function(){
            if ($(this).data('value') <= ratingValue) {
                $(this).addClass('checked');
            }
        });
    });

    $('.star-rating .fa-star').on('mouseout', function(){
        var ratingValue = $('#rating').val();
        $('.star-rating .fa-star').removeClass('checked');
        $('.star-rating .fa-star').each(function(){
            if ($(this).data('value') <= ratingValue) {
                $(this).addClass('checked');
            }
        });
    });
});

// Hàm định dạng số với dấu phân cách hàng nghìn
function formatNumber(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}

// Giảm số lượng
function decreaseQuantity() {
    const quantityInput = document.getElementById("quantity");
    const minQuantity = parseInt(quantityInput.getAttribute("min"));
    let currentQuantity = parseInt(quantityInput.value.replace(/\./g, "")) || minQuantity;

    if (currentQuantity > minQuantity) {
        currentQuantity--;
        quantityInput.value = formatNumber(currentQuantity);
    }
}

// Tăng số lượng
function increaseQuantity() {
    const quantityInput = document.getElementById("quantity");
    const maxQuantity = parseInt(quantityInput.getAttribute("max"));
    let currentQuantity = parseInt(quantityInput.value.replace(/\./g, "")) || 1;

    if (currentQuantity < maxQuantity) {
        currentQuantity++;
        quantityInput.value = formatNumber(currentQuantity);
    }
}

// Định dạng ban đầu khi tải trang
document.addEventListener("DOMContentLoaded", function() {
    const quantityInput = document.getElementById("quantity");
    if (quantityInput) {
        let initialValue = parseInt(quantityInput.value.replace(/\./g, "")) || parseInt(quantityInput.getAttribute("min"));
        quantityInput.value = formatNumber(initialValue);
    }
});

// Hàm validate số lượng
function validateQuantity(input) {
    let value = input.value.replace(/\./g, "");
    let parsedValue = parseInt(value);

    if (value === "" || isNaN(parsedValue)) {
        input.value = "";
        return;
    }

    const min = parseInt(input.getAttribute("min"));
    const max = parseInt(input.getAttribute("max"));

    if (parsedValue < min) {
        parsedValue = min;
    } else if (parsedValue > max) {
        parsedValue = max;
    }

    input.value = formatNumber(parsedValue);
}

function attachEventBuyHandlers() {
    console.log("Gán lại sự kiện cho nút");

    // Xử lý thêm vào giỏ hàng
    $('.add-to-cart-btn').off('click').on('click', function() {
        console.log("add to cart");
        
        var productId = $(this).data('product-id');
        var quantity = $('#quantity').val()?.replace(/\./g, "") || 1;

        $.ajax({
            url: contextPath + '/cart',
            type: 'POST',
            data: {
                action: 'add',
                productID: productId,
                quantity: quantity
            },
            success: function(response) {
                if (response === 'success') {
                    $('#cart-message').removeClass('alert-danger').addClass('alert-success')
                        .text('Sản phẩm đã được thêm vào giỏ hàng!').show();
                    setTimeout(() => $('#cart-message').fadeOut(), 3000);
                } else {
                    $('#cart-message').removeClass('alert-success').addClass('alert-danger')
                        .text(response).show();
                    setTimeout(() => $('#cart-message').fadeOut(), 5000);
                }
            },
            error: function(xhr) {
                var errorMsg = xhr.responseText || 'Lỗi khi thêm vào giỏ hàng. Vui lòng thử lại.';
                $('#cart-message').removeClass('alert-success').addClass('alert-danger')
                    .text(errorMsg).show();
                setTimeout(() => $('#cart-message').fadeOut(), 5000);
            }
        });
    });

    // Xử lý mua ngay
    $('.buy-now-btn').off('click').on('click', function() {
        console.log("buy now");
        
        var productId = $(this).data('product-id');
        var quantity = $('#quantity').val()?.replace(/\./g, "") || 1;

        $.ajax({
            url: contextPath + '/cart',
            type: 'POST',
            data: {
                action: 'buyNow',
                productID: productId,
                quantity: quantity
            },
            success: function(response) {
                if (response === 'success') {
                    window.location.href = contextPath + '/cart';
                } else {
                    $('#cart-message').removeClass('alert-success').addClass('alert-danger')
                        .text(response).show();
                    setTimeout(() => $('#cart-message').fadeOut(), 5000);
                }
            },
            error: function(xhr) {
                var errorMsg = xhr.responseText || 'Vui lòng đăng nhập để tiếp tục mua hàng.';
                $('#cart-message').removeClass('alert-success').addClass('alert-danger')
                    .text(errorMsg).show();
                setTimeout(() => $('#cart-message').fadeOut(), 5000);
            }
        });
    });
}

attachEventBuyHandlers();
