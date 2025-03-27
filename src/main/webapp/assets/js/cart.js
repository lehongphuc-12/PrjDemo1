// Chọn hoặc bỏ chọn tất cả sản phẩm
function toggleSelectAll(checkbox) {
    const itemCheckboxes = document.querySelectorAll('.item-checkbox');
    itemCheckboxes.forEach(cb => {
        if (!cb.disabled) { // Chỉ thay đổi trạng thái của các checkbox không bị vô hiệu hóa
            cb.checked = checkbox.checked;
        }
    });

    // Cập nhật trạng thái checkbox của shop
    const sellerIds = [...new Set(Array.from(itemCheckboxes).map(cb => cb.getAttribute('data-seller-id')))];
    sellerIds.forEach(sellerId => updateShopCheckbox(sellerId));

    updateTotal();
}

// Cập nhật trạng thái checkbox của shop
function updateShopCheckbox(sellerId) {
    const itemCheckboxes = document.querySelectorAll(`.item-checkbox[data-seller-id="${sellerId}"]:not([disabled])`); // Chỉ lấy các checkbox không bị vô hiệu hóa
    let checkedCount = 0;
    itemCheckboxes.forEach(itemCheckbox => {
        if (itemCheckbox.checked) checkedCount++;
    });
    const shopCheckbox = document.querySelector(`.shop-checkbox[data-seller-id="${sellerId}"]`);
    if (shopCheckbox) {
        if (checkedCount === itemCheckboxes.length && itemCheckboxes.length > 0) {
            shopCheckbox.checked = true;
            shopCheckbox.indeterminate = false;
        } else if (checkedCount === 0) {
            shopCheckbox.checked = false;
            shopCheckbox.indeterminate = false;
        } else {
            shopCheckbox.checked = false;
            shopCheckbox.indeterminate = true;
        }
    }
}

// Xử lý checkbox của shop
function setupShopCheckboxes() {
    document.querySelectorAll('.shop-checkbox').forEach(shopCheckbox => {
        shopCheckbox.addEventListener('change', function() {
            const sellerId = this.getAttribute('data-seller-id');
            const itemCheckboxes = document.querySelectorAll(`.item-checkbox[data-seller-id="${sellerId}"]`);
            itemCheckboxes.forEach(itemCheckbox => {
                if (!itemCheckbox.disabled) { // Chỉ thay đổi trạng thái của các checkbox không bị vô hiệu hóa
                    itemCheckbox.checked = shopCheckbox.checked;
                }
            });
            updateTotal();
        });
    });
}

// Xử lý checkbox của sản phẩm
function setupItemCheckboxes() {
    document.querySelectorAll('.item-checkbox').forEach(itemCheckbox => {
        itemCheckbox.addEventListener('change', function() {
            const sellerId = this.getAttribute('data-seller-id');
            updateShopCheckbox(sellerId);
            updateTotal();
        });
    });
}

// Xóa tất cả sản phẩm đã chọn
function removeAllSelected() {
    const selectedItems = document.querySelectorAll('.item-checkbox:checked');
    if (selectedItems.length === 0) {
        alert('Vui lòng chọn ít nhất một sản phẩm để xóa.');
        return;
    }
    if (confirm('Bạn có chắc chắn muốn xóa các sản phẩm đã chọn không?')) {
        const productIds = Array.from(selectedItems).map(item => item.getAttribute('data-product-id')).join(',');
        if (!productIds) {
            alert('Không tìm thấy ID sản phẩm để xóa.');
            return;
        }
        window.location.href = `${contextPath}/cart?action=removeMultiple&productIds=${productIds}`;
    }
}

// Xử lý giảm số lượng
document.querySelectorAll('.btn-decrease').forEach(button => {
    button.addEventListener('click', function() {
        const productId = this.getAttribute('data-product-id');
        const quantityInput = this.parentElement.querySelector('.quantity-input');
        let quantity = parseInt(quantityInput.value);
        if (quantity > 1) {
            quantity--;
            quantityInput.value = quantity;
            updateQuantity(productId, quantity);
        }
    });
});

// Xử lý tăng số lượng
document.querySelectorAll('.btn-increase').forEach(button => {
    button.addEventListener('click', function() {
        const productId = this.getAttribute('data-product-id');
        const quantityInput = this.parentElement.querySelector('.quantity-input');
        let quantity = parseInt(quantityInput.value);
        const maxQuantity = parseInt(quantityInput.getAttribute('max'));
        if (quantity < maxQuantity) {
            quantity++;
            quantityInput.value = quantity;
            updateQuantity(productId, quantity);
        }
    });
});

// Xử lý nhập số lượng thủ công
document.querySelectorAll('.quantity-input').forEach(input => {
    input.addEventListener('change', function() {
        const productId = this.getAttribute('data-product-id');
        let quantity = parseInt(this.value);
        const min = parseInt(this.getAttribute('min'));
        const max = parseInt(this.getAttribute('max'));
        if (quantity < min) {
            quantity = min;
        } else if (quantity > max) {
            quantity = max;
        }
        this.value = quantity;
        updateQuantity(productId, quantity);
    });
});

// Cập nhật số lượng qua AJAX
function updateQuantity(productId, newQuantity) {
    fetch(`${contextPath}/cart?action=update&productID=${productId}&newQuantity=${newQuantity}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            const itemCheckbox = document.querySelector(`.item-checkbox[data-product-id="${productId}"]`);
            const unitPrice = parseFloat(itemCheckbox.getAttribute('data-unit-price'));
            const newTotal = unitPrice * newQuantity;
            itemCheckbox.setAttribute('data-price', newTotal);
            const totalPriceElement = itemCheckbox.closest('.cart-item').querySelector('.total-price');
            totalPriceElement.innerText = newTotal.toLocaleString('vi-VN') + ' đ';
            updateTotal();
        } else {
            alert('Cập nhật số lượng thất bại: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Lỗi:', error);
        alert('Có lỗi xảy ra khi cập nhật số lượng.');
    });
}

// Cập nhật tổng tiền và số lượng
function updateTotal() {
    const selectedItems = document.querySelectorAll('.item-checkbox:checked:not([disabled])'); // Chỉ lấy các checkbox được chọn và không bị vô hiệu hóa
    let selectedSubTotal = 0;
    let totalQuantity = 0;

    selectedItems.forEach(item => {
        const price = parseFloat(item.getAttribute('data-price')) || 0;
        const quantityInput = item.closest('.cart-item').querySelector('.quantity-input');
        const quantity = parseInt(quantityInput.value) || 0;
        if (quantity > 0) {
            selectedSubTotal += price;
            totalQuantity += quantity;
        }
    });

    let selectedDiscount = parseFloat(document.getElementById('selectedDiscount').innerText.replace(/[^0-9.-]+/g, '')) || 0;
    const totalPrice = selectedSubTotal - selectedDiscount;

    document.getElementById('selectedSubTotal').innerText = selectedSubTotal.toLocaleString('vi-VN') + ' đ';
    document.querySelector('.total-quantity').textContent = totalQuantity;
    document.getElementById('selectedDiscount').innerText = selectedDiscount.toLocaleString('vi-VN') + ' đ';
    document.getElementById('selectedTotal').innerText = totalPrice.toLocaleString('vi-VN') + ' đ';
    document.querySelectorAll('.selected-count').forEach(el => {
        el.textContent = selectedItems.length;
    });
}

// Xử lý nút "Mua Hàng"
document.querySelector('.btn-purchase').addEventListener('click', function(e) {
    const selectedItems = document.querySelectorAll('.item-checkbox:checked:not([disabled])'); // Chỉ lấy các checkbox được chọn và không bị vô hiệu hóa
    if (selectedItems.length === 0) {
        alert('Vui lòng chọn ít nhất một sản phẩm để thanh toán.');
        e.preventDefault();
        return;
    }
    const productIds = Array.from(selectedItems).map(item => item.getAttribute('data-product-id')).join(',');
    document.getElementById('selectedProductIds').value = productIds;
});

// Khởi tạo khi trang tải xong
document.addEventListener('DOMContentLoaded', function() {
    // Khởi tạo tooltip
    const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
    tooltipTriggerList.forEach(tooltipTriggerEl => {
        new bootstrap.Tooltip(tooltipTriggerEl);
    });

    // Thiết lập checkbox cho shop và sản phẩm
    setupShopCheckboxes();
    setupItemCheckboxes();

    // Cập nhật trạng thái ban đầu của checkbox shop
    const sellerIds = [...new Set(Array.from(document.querySelectorAll('.item-checkbox')).map(cb => cb.getAttribute('data-seller-id')))];
    sellerIds.forEach(sellerId => updateShopCheckbox(sellerId));

    // Cập nhật tổng tiền ban đầu
    updateTotal();

    // Đặt trạng thái ban đầu cho checkbox "Chọn tất cả"
    const selectAllCheckbox = document.getElementById('select-all');
    if (selectAllCheckbox) {
        selectAllCheckbox.checked = true;
        toggleSelectAll(selectAllCheckbox);
    }
});