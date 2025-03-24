import { handleHttpStatus, showAlert } from './handleStatus.js';

console.log("PRODUCT DETAIL JS");

// Khi trang tải xong
$(document).ready(function () {

    // Nút quay lại đầu trang
    $(window).scroll(function () {
        if ($(this).scrollTop() > 100) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });

    $('.back-to-top').click(function () {
        $('html, body').animate({ scrollTop: 0 }, 1500, 'easeInOutExpo');
        return false;
    });

    // Hệ thống đánh giá sao
    $('.star-rating .fa-star').on('click mouseover', function (event) {
        let ratingValue = $(this).data('value');
        $('.star-rating .fa-star').removeClass('checked');

        $('.star-rating .fa-star').each(function () {
            if ($(this).data('value') <= ratingValue) {
                $(this).addClass('checked');
            }
        });

        if (event.type === "click") {
            $('#rating').val(ratingValue);
        }
    });

    // Định dạng số có dấu phân cách
    function formatNumber(number) {
        return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
    }

    // Giảm số lượng
    $('.decrease-btn').click(function () {
        let quantityInput = $('#quantity');
        if (!quantityInput.length) {
            console.error("Không tìm thấy input số lượng!");
            return;
        }

        let minQuantity = parseInt(quantityInput.attr("min")) || 1;
        let currentQuantity = parseInt(quantityInput.val().replace(/\./g, "")) || minQuantity;

        if (currentQuantity > minQuantity) {
            currentQuantity--;
            quantityInput.val(formatNumber(currentQuantity));
        }
    });

    // Tăng số lượng
    $('.increase-btn').click(function () {
        let quantityInput = $('#quantity');
        if (!quantityInput.length) {
            console.error("Không tìm thấy input số lượng!");
            return;
        }

        let maxQuantity = quantityInput.attr("max") ? parseInt(quantityInput.attr("max")) : Infinity;
        let currentQuantity = parseInt(quantityInput.val().replace(/\./g, "")) || 1;

        if (currentQuantity < maxQuantity) {
            currentQuantity++;
            quantityInput.val(formatNumber(currentQuantity));
        }
    });

    // Định dạng số lượng khi tải trang
    let quantityInput = $('#quantity');
    if (quantityInput.length) {
        let initialValue = parseInt(quantityInput.val().replace(/\./g, "")) || parseInt(quantityInput.attr("min"));
        quantityInput.val(formatNumber(initialValue));
    }

    // Validate số lượng
    $('#quantity').on('input', function () {
        let input = $(this);
        let value = input.val().replace(/\./g, "");
        let parsedValue = parseInt(value);

        if (value === "" || isNaN(parsedValue)) {
            input.val("");
            return;
        }

        let min = parseInt(input.attr("min"));
        let max = parseInt(input.attr("max"));

        if (parsedValue < min) {
            parsedValue = min;
        } else if (parsedValue > max) {
            parsedValue = max;
        }

        input.val(formatNumber(parsedValue));
    });

    // Xử lý thêm vào giỏ hàng
    $('.add-to-cart-btn').click(function () {
        console.log("add to cart");

        let productId = $(this).data('product-id');
        let quantity = $('#quantity').val()?.replace(/\./g, "") || 1;

        $.post(contextPath + '/cart', {
            action: 'add',
            productID: productId,
            quantity: quantity
        })
            .done(function (response) {
                if (response === 'success') {
                    $('#cart-message').removeClass('alert-danger').addClass('alert-success')
                        .text('Sản phẩm đã được thêm vào giỏ hàng!').show();
                    setTimeout(() => $('#cart-message').fadeOut(), 3000);
                } else {
                    $('#cart-message').removeClass('alert-success').addClass('alert-danger')
                        .text(response).show();
                    setTimeout(() => $('#cart-message').fadeOut(), 5000);
                }
                showAlert("Thành công", "Đã thêm sản phẩm vào giỏ hàng", "success");
            })
            .fail(function (xhr) {
                handleHttpStatus(contextPath, xhr);
            });
    });

    // Xử lý mua ngay
    $('.buy-now-btn').click(function () {
        console.log("buy now");

        let productId = $(this).data('product-id');
        let quantity = $('#quantity').val()?.replace(/\./g, "") || 1;

        $.post(contextPath + '/cart', {
            action: 'buyNow',
            productID: productId,
            quantity: quantity
        })
            .done(function (response) {
                if (response === 'success') {
                    window.location.href = contextPath + '/cart';
                } else {
                    $('#cart-message').removeClass('alert-success').addClass('alert-danger')
                        .text(response).show();
                    setTimeout(() => $('#cart-message').fadeOut(), 5000);
                }
            })
            .fail(function (xhr) {
                handleHttpStatus(contextPath, xhr);
            });
    });

});
