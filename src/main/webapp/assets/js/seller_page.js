/* global param, page */

function isConfirm(message) {
    return confirm(message);
}

const menuItems = document.querySelectorAll('.sidebar ul li');
menuItems.forEach(item => {
    const link = item.querySelector('a');
    const submenu = item.querySelector('ul');
    if (submenu) {
        link.addEventListener('click', e => {
            e.preventDefault();
            const isActive = item.classList.contains('active');
            menuItems.forEach(i => i.classList.remove('active'));
            if (!isActive)
                item.classList.add('active');
        });
    }
});
function attachSearchListener() {
    const searchInput = document.getElementById("searchInput");
    if (searchInput) {
        searchInput.addEventListener('keypress', function (e) {
            if (e.key === 'Enter') {
                const searchValue = this.value.trim();
                loadSection(`sanphamlist&page=1&search=${searchValue}`);
            }
        });
    }
}
function loadSection(action) {
    const xhr = new XMLHttpRequest();
    const urlParams = new URLSearchParams(action.split('&')[1] || '');
    let page = urlParams.get('page') || 1;
    let search = urlParams.get('search') || document.getElementById('searchInput')?.value || '';
    let url = contextPath + "/seller?action=" + action.split('&')[0];
    if (page)
        url += "&page=" + page;
    if (search)
        url += "&search=" + encodeURIComponent(search);
    if (action.split('&')[0] === 'updateProduct')
        url += "&" + action.split('&')[1];
    console.log("Action:", action);
    console.log("Page:", page);
    console.log("Request URL:", url);
    console.log("Request URL:", url);

    xhr.open("GET", url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            document.getElementById("main-content").innerHTML = xhr.responseText;

            let cleanAction = action.split("&")[0];
            if (cleanAction === "sanphamlist") {
                attachSearchListener(); // Gắn lại sự kiện tìm kiếm sau khi load
            } else if (cleanAction === "donhang") {
                const status = urlParams.get('status') || 'all';
                setTimeout(() => {
                    const statusFilter = document.getElementById('statusFilter');
                    if (statusFilter) {
                        statusFilter.value = status;
                        filterOrders(status);
                    }
                }, 100);
            } else if (cleanAction === "doanhthu") {
                setTimeout(() => {
                    if (typeof initChart === "function")
                        initChart();
                }, 500);
            } else if (cleanAction === "addSanpham" || cleanAction === "updateProduct") {
                setTimeout(() => {
                    initializeProductTypeLoader();
                }, 500);
            }
        }
    };
    xhr.send();
}


function filterOrders(status) {
    console.log("Status received in filterOrders:", status); // Log giá trị status
    document.querySelectorAll('table').forEach(table => table.classList.remove('active'));
    document.querySelectorAll('.pagination').forEach(pag => pag.classList.remove('active'));

    const table = document.getElementById(status);
    if (table)
        table.classList.add('active');
    var cha = document.getElementById('pagination-all');
    if (status === 'all') {
        cha.style.display = "flex";
    } else {
        cha.style.display = "none";
    }
}
// Khai báo biến toàn cục để lưu dữ liệu từ server
let dailyData = {labels: [], values: []};
let weeklyData = {labels: [], values: []};

// Lấy dữ liệu từ server khi trang tải
document.addEventListener('DOMContentLoaded', () => {
    fetch(contextPath + "/api/data")
            .then(response => {
                if (!response.ok)
                    throw new Error('Lỗi khi lấy dữ liệu từ server');
                return response.json();
            })
            .then(data => {
                // Gán dữ liệu từ server vào biến toàn cục
                dailyData = data.dailyData;
                weeklyData = data.weeklyData;
                // Khởi tạo biểu đồ ban đầu với dữ liệu hàng ngày
                initChart();
                // Gắn sự kiện change cho timeFilter
                document.getElementById('timeFilter')?.addEventListener('change', updateChart);
            })
            .catch(error => console.error('Error:', error));
});


function initChart() {
    const canvas = document.getElementById('revenueChart');
    if (!canvas) {
        console.error("Không tìm thấy phần tử canvas với id 'revenueChart'");
        return;
    }

    const ctx = canvas.getContext('2d');
    if (!ctx)
        return;
    // Hủy biểu đồ cũ nếu tồn tại
    if (window.revenueChart && typeof window.revenueChart.destroy === "function") {
        window.revenueChart.destroy();
    }

    // Tạo biểu đồ mới với dữ liệu ban đầu là dailyData
    window.revenueChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: dailyData.labels,
            datasets: [{
                    label: 'Doanh Thu',
                    data: dailyData.values,
                    borderColor: '#32CD32',
                    backgroundColor: 'rgba(50, 205, 50, 0.2)',
                    fill: true,
                    tension: 0.4
                }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        callback: function (value) {
                            return value.toLocaleString('vi-VN') + ' VND';
                        }
                    }
                }
            }
        }
    });
    if (!(window.revenueChart instanceof Chart)) {
        console.error("Lỗi: window.revenueChart không phải là một đối tượng Chart hợp lệ!");
    }
}

function updateChart() {
    const filter = document.getElementById('timeFilter').value;
    let newLabels, newData;
    if (filter === 'day') {
        newLabels = dailyData.labels;
        newData = dailyData.values;
    } else {
        newLabels = weeklyData.labels;
        newData = weeklyData.values;
    }

    if (window.revenueChart) {
        window.revenueChart.data.labels = newLabels;
        window.revenueChart.data.datasets[0].data = newData;
        window.revenueChart.update();
    } else {
        console.error("Lỗi: Biểu đồ chưa được khởi tạo!");
    }
}
function updateProfile() {
    var shopName = document.getElementById("shopName").value;
    console.log(shopName);
    var email = document.getElementById("email").value;
    var phone = document.getElementById("phone").value;
    var address = document.getElementById("address").value;
    var isConfirmed = confirm("Bạn có chắc chắn muốn cập nhật hồ sơ?");
    if (!isConfirmed)
        return;
    var xhr = new XMLHttpRequest();
    xhr.open("POST", contextPath + "/seller?action=updateProfile", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200 && xhr.responseText === "success") {
                alert("Cập nhật hồ sơ thành công!");
                window.location.reload();
            } else {
                alert("Có lỗi xảy ra, vui lòng thử lại.");
            }
        }
    };
    console.log("Sending: " + "email=" + encodeURIComponent(email) +
            "&phone=" + encodeURIComponent(phone) +
            "&shopName=" + encodeURIComponent(shopName) +
            "&address=" + encodeURIComponent(address));
    xhr.send("email=" + encodeURIComponent(email) +
            "&phone=" + encodeURIComponent(phone) +
            "&shopName=" + encodeURIComponent(shopName) +
            "&address=" + encodeURIComponent(address));
}
function verifyCurrentPassword() {
    var currentPassword = document.getElementById("current-password").value;
    var xhr = new XMLHttpRequest();
    xhr.open("POST", contextPath + "/seller?action=verifyPassword", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            if (xhr.responseText === "success") {
                document.getElementById("change-password-form").style.display = "none";
                document.getElementById("new-password-section").style.display = "block";
            } else {
                alert("Mật khẩu hiện tại không đúng!");
            }
        }
    };
    xhr.send("currentPassword=" + encodeURIComponent(currentPassword));
}
function updatePassword() {
    var newPassword = document.getElementById("new-password").value;
    var confirmPassword = document.getElementById("confirm-password").value;
    if (newPassword !== confirmPassword) {
        alert("Mật khẩu xác nhận không khớp!");
        return;
    }
    var isConfirmed = confirm("Bạn có chắc chắn muốn thay đổi mật khẩu?");
    if (!isConfirmed) {
        return; // Nếu người dùng chọn "Hủy", dừng quá trình
    }
    var xhr = new XMLHttpRequest();
    xhr.open("POST", contextPath + "/seller?action=updatePassword", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            if (xhr.responseText === "success") {
                alert("Mật khẩu đã được thay đổi thành công!");
                location.reload(); // Refresh page or redirect if needed
            } else {
                alert("Có lỗi xảy ra, vui lòng thử lại.");
            }
        }
    };
    xhr.send("newPassword=" + encodeURIComponent(newPassword));
}
function initializeProductTypeLoader() {
    const productGroupSelect = document.getElementById('productGroupId');
    const productTypeSelect = document.getElementById('productTypeId');
    if (productGroupSelect && productTypeSelect) {
        productGroupSelect.addEventListener('change', function () {
            const groupId = this.value;
            productTypeSelect.innerHTML = '<option value="">Chọn Loại</option>';
            if (groupId && groupId.trim() !== "") { // Chỉ gửi request nếu groupId không rỗng
                fetch(contextPath + '/getProductTypes?groupId=' + groupId, {
                    method: 'GET',
                    headers: {'Content-Type': 'application/json'}
                })
                        .then(response => {
                            if (!response.ok)
                                throw new Error('Network response was not ok');
                            return response.json();
                        })
                        .then(data => {
                            console.log(data)
                            data.forEach(type => {
                                const option = document.createElement('option');
                                option.value = type.categoryID; // Sửa từ typeId thành categoryId
                                option.textContent = type.name; // Sửa từ typeName thành name
                                productTypeSelect.appendChild(option);
                            });
                        })
                        .catch(error => console.error('Lỗi khi tải loại sản phẩm:', error));
            } else {
                console.log("groupId rỗng, không gửi request");
            }
        });
    } else {
        console.error("Không tìm thấy productGroupId hoặc productTypeId trong DOM");
    }
}
function updateProduct(productId) {
    var id = productId;
    var name = document.getElementById("productName").value;
    var price = document.getElementById("price").value;
    var quantity = document.getElementById("quantity").value;
    var city = document.getElementById("cityId").value;
    var category = document.getElementById("productTypeId").value;
    var description = document.getElementById("description").value;
    var mainImage = document.getElementById("mainImage").files[0];
    var subImage1 = document.getElementById("subImage1").files[0];
    var subImage2 = document.getElementById("subImage2").files[0];
    var subImage3 = document.getElementById("subImage3").files[0];

    if (!isConfirm("Bạn có chắc chắn muốn cập nhật sản phẩm này?")) {
        return;
    }

    var formData = new FormData();
    formData.append("id", id);
    formData.append("name", name);
    formData.append("price", price);
    formData.append("quantity", quantity);
    formData.append("category", category);
    formData.append("description", description);
    formData.append("city", city);

    // Chỉ thêm file nếu tồn tại và có kích thước
    if (mainImage && mainImage.size > 0)
        formData.append("mainImage", mainImage);
    if (subImage1 && subImage1.size > 0)
        formData.append("subImage1", subImage1);
    if (subImage2 && subImage2.size > 0)
        formData.append("subImage2", subImage2);
    if (subImage3 && subImage3.size > 0)
        formData.append("subImage3", subImage3);

    fetch(contextPath + "/updateProduct", {
        method: "POST",
        body: formData
    })
            .then(response => response.text())
            .then(result => {
                if (result === "success") {
                    loadSection('sanphamlist');
                    alert("Update sản phẩm thành công!");
                } else {
                    alert("Có lỗi xảy ra: " + result);
                }
            })
            .catch(error => console.error("Lỗi:", error));
}

function addProduct() {
    var name = document.getElementById("productName").value;
    var price = document.getElementById("price").value;
    var quantity = document.getElementById("quantity").value;
    var city = document.getElementById("cityId").value;
    var category = document.getElementById("productTypeId").value;
    console.log(category);
    var description = document.getElementById("description").value;
    var mainImage = document.getElementById("mainImage").files[0];
    var subImage1 = document.getElementById("subImage1").files[0];
    var subImage2 = document.getElementById("subImage2").files[0];
    var subImage3 = document.getElementById("subImage3").files[0];
    if (!isConfirm("Bạn có chắc chắn muốn thêm sản phẩm này?")) {
        return;
    }

    var formData = new FormData();
    formData.append("name", name);
    formData.append("price", price);
    formData.append("quantity", quantity);
    formData.append("category", category);
    formData.append("description", description);
    formData.append("city", city);
    formData.append("mainImage", mainImage);
    if (subImage1)
        formData.append("subImage1", subImage1);
    if (subImage2)
        formData.append("subImage2", subImage2);
    if (subImage3)
        formData.append("subImage3", subImage3);
    fetch(contextPath + "/addProduct", {
        method: "POST",
        body: formData
    })
            .then(response => response.text())
            .then(result => {
                if (result === "success") {
                    alert("Thêm sản phẩm thành công!");
                    loadSection('sanphamlist');
                } else {
                    alert("Có lỗi xảy ra, vui lòng thử lại.");
                }
            })
            .catch(error => console.error("Lỗi:", error));
}

function removeProduct(productId) {
    if (!confirm("Bạn có chắc chắn muốn xóa sản phẩm này?")) {
        return;
    }

    fetch(contextPath + "/removeProduct", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "productId=" + encodeURIComponent(productId)
    })
            .then(response => response.text())
            .then(html => {

                document.getElementById("main-content").innerHTML = html;
                alert("Xóa sản phẩm thành công!");
            })
            .catch(error => console.error("Lỗi:", error));
}



function confirmOrder(orderdetailId) {
    if (!confirm("Bạn có chắc chắn muốn xác nhận sản phẩm này?")) {
        return;
    }

    fetch(contextPath + "/confirmOrder", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "orderdetailId=" + encodeURIComponent(orderdetailId)
    })
            .then(response => response.text())
            .then(html => {

                document.getElementById("main-content").innerHTML = html;
                alert("Xác nhận đơn hàng thành công!");
            })
            .catch(error => console.error("Lỗi:", error));
}


function reCreateProduct(productID) {
    if (!confirm("Bạn có muốn đăng lại sản phẩm này?")) {
        return;
    }

    fetch(contextPath + "/seller", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "productID=" + encodeURIComponent(productID) + "&action=reCreate"
    })
            .then(response => response.text())
            .then(html => {

                document.getElementById("main-content").innerHTML = html;
                alert("Đăng lại thành công!");
            })
            .catch(error => console.error("Lỗi:", error));
}
function showVoucherForm() {
    document.getElementById('voucherFormContainer').style.display = 'block';
}

function hideVoucherForm() {
    document.getElementById('voucherFormContainer').style.display = 'none';
    // Reset form
    document.getElementById('discountCode').value = '';
    document.getElementById('discountPercent').value = '';
    document.getElementById('startDate').value = '';
    document.getElementById('endDate').value = '';
    document.getElementById('productId').value = '';
}
function generateRandomVoucherCode() {
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    let randomCode = '';
    for (let i = 0; i < 10; i++) {
        const randomIndex = Math.floor(Math.random() * characters.length);
        randomCode += characters[randomIndex];
    }
    document.getElementById('discountCode').value = randomCode;
}
function addVoucherToProduct() {
    const code = document.getElementById('discountCode').value.trim();
    const discountPercent = document.getElementById('discountPercent').value;
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;
    const productId = document.getElementById('productId').value;

    if (!code || !discountPercent || !startDate || !endDate || !productId) {
        alert("Vui lòng nhập đầy đủ thông tin!");
        return;
    }
    console.log(discountPercent);
    const today = new Date().toISOString().split('T')[0];
    if (startDate < today) {
        alert("Ngày bắt đầu không thể là ngày trong quá khứ!");
        return;
    }
    if (endDate < startDate) {
        alert("Ngày kết thúc phải sau ngày bắt đầu!");
        return;
    }

    const xhr = new XMLHttpRequest();
    const url = contextPath + "/voucher?action=addVoucherToProduct";

    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    const data = "code=" + encodeURIComponent(code) +
            "&discountPercent=" + encodeURIComponent(discountPercent) +
            "&startDate=" + encodeURIComponent(startDate) +
            "&endDate=" + encodeURIComponent(endDate) +
            "&productId=" + encodeURIComponent(productId);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            document.getElementById("main-content").innerHTML = xhr.responseText;
            attachSearchListener();
            alert("Add success!")
        }
    };
    xhr.send(data);
}
function removeVoucher(code, discountID) {
    if (!confirm(`Bạn có chắc muốn xóa voucher ${code}?`))
        return;

    const xhr = new XMLHttpRequest();
    const url = contextPath + "/voucher?action=removeVoucher";

    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    const data = "discountID=" + encodeURIComponent(discountID); // Sửa cú pháp dữ liệu

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                document.getElementById("main-content").innerHTML = xhr.responseText;
                alert("Xóa thành công voucher!");
            } else {
                console.error("Lỗi: ", xhr.status, xhr.responseText);
                alert("Có lỗi xảy ra khi xóa voucher: " + xhr.responseText);
            }
        }
    };
    xhr.send(data);
}



function previewImage(input, previewId) {
    var preview = document.getElementById(previewId);
    preview.innerHTML = ''; // Xóa preview cũ nếu có

    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            var img = document.createElement('img');
            img.src = e.target.result; // Đường dẫn base64 của ảnh
            img.style.maxWidth = '100px'; // Giới hạn kích thước ảnh preview
            img.style.marginTop = '5px';
            preview.appendChild(img);
        };
        reader.readAsDataURL(input.files[0]); // Đọc file thành base64
    }
}

function togglePassword(button) {
    var pwd = button.previousElementSibling; // Lấy input gần nhất trước nút bấm
    pwd.type = (pwd.type === "password") ? "text" : "password";
}