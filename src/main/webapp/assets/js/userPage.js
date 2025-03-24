 function filterOrders(status) {
    console.log("PHAN TRANG");
    console.log("Status received in filterOrders:", status); // Log giá trị status
    document.querySelectorAll('table').forEach(table => table.classList.remove('active'));
    document.querySelectorAll('.pagination').forEach(pag => pag.classList.remove('active'));

    const table = document.getElementById(status);
    if (table)
        table.classList.add('active');
    var cha = document.getElementById('pagination-all');
    console.log(cha);
    if (status === 'all') {
        cha.style.display = "flex";
    } else {
        cha.style.display = "none";
    }
}
function loadSection(action) {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", contextPath + "/user?action=" + action, true);
    let cleanAction = action.split("&")[0];
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            document.getElementById("content-main").innerHTML = xhr.responseText;
            if (cleanAction === "donHang") {
                const urlParams = new URLSearchParams(action);
                const status = urlParams.get('status') || 'all';
                console.log("Status before filterOrders:", status); // Log để kiểm tra
                setTimeout(() => {
                    const statusFilter = document.getElementById('statusFilter');
                    if (statusFilter) {
                        statusFilter.value = status;
                        filterOrders(status);
                    } else {
                        console.error("Không tìm thấy #statusFilter!");
                    }
                }, 100);

            }
        }
    };
    xhr.send();
}
function updateProfile() {
    var email = document.getElementById("email").value;
    var phone = document.getElementById("phone").value;
    var address = document.getElementById("address").value;
    var isConfirmed = confirm("Bạn có chắc chắn muốn cập nhật hồ sơ?");
    if (!isConfirmed)
        return;
    var xhr = new XMLHttpRequest();
    xhr.open("POST", contextPath + "/user?action=updateProfile", true);
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

    xhr.send("email=" + encodeURIComponent(email) +
            "&phone=" + encodeURIComponent(phone) +
            "&address=" + encodeURIComponent(address));
}
function verifyCurrentPassword() {
    var currentPassword = document.getElementById("current-password").value;

    var xhr = new XMLHttpRequest();
    xhr.open("POST", contextPath + "/user?action=verifyPassword", true);
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
    xhr.open("POST", contextPath + "/user?action=updatePassword", true);
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




function cancelOrder(orderdetailId) {
    if (!confirm("Bạn có chắc chắn muốn hủy đơn này?")) {
        return;
    }

    fetch(contextPath + "/user", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "orderdetailId=" + encodeURIComponent(orderdetailId) + "&action=cancelOrder"
    })
            .then(response => response.text())
            .then(html => {

                document.getElementById("content-main").innerHTML = html;
                alert("Hủy đơn hàng thành công!");
            })
            .catch(error => console.error("Lỗi:", error));
}
                function confirmReceived(orderdetailId) {
                    if (!confirm("Xác nhận đơn này?")) {
                        return;
                    }

                    fetch(contextPath + "/user", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/x-www-form-urlencoded"
                        },
                        body: "orderdetailId=" + encodeURIComponent(orderdetailId) + "&action=confirmReceived"
                    })
                            .then(response => response.text())
                            .then(html => {

                                document.getElementById("content-main").innerHTML = html;
                                alert("Xác nhận đơn hàng thành công!");
                            })
                            .catch(error => console.error("Lỗi:", error));
                }
                function repurchaseOrder(orderdetailId) {
                    if (!confirm("Bạn có muốn mua lại đơn này?")) {
                        return;
                    }

                    fetch(contextPath + "/user", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/x-www-form-urlencoded"
                        },
                        body: "orderdetailId=" + encodeURIComponent(orderdetailId) + "&action=repurchaseOrder"
                    })
                            .then(response => response.text())
                            .then(html => {

                                document.getElementById("content-main").innerHTML = html;
                                alert("Mua đơn hàng thành công! Đợi seller xử lí");
                            })
                            .catch(error => console.error("Lỗi:", error));
                }