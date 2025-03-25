export function handleHttpStatus(contextPath,xhr) {
    let title = "Lỗi";
    let message = "";
    let alertType = "error"; // Các loại: success, info, warning, error

    switch (xhr.status) {
        case 200:
            title = "Thành công!";
            message = "Yêu cầu đã được xử lý.";
            alertType = "success";
            break;
        case 201:
            title = "Tạo thành công!";
            message = "Tài nguyên mới đã được tạo.";
            alertType = "success";
            break;
        case 204:
            title = "Không có nội dung!";
            message = "Yêu cầu được xử lý nhưng không có dữ liệu trả về.";
            alertType = "info";
            break;
        case 400:
            message = xhr.responseText;
            break;
        case 401:
            message = "Bạn cần đăng nhập để tiếp tục.";
            window.location.href = contextPath +`/views/login.jsp?error-message=${message}`;
            break;
        case 403:
            message = "Bạn không có quyền truy cập vào tài nguyên này.";
            break;
        case 404:
            message = "Tài nguyên không được tìm thấy.";
            break;
        case 405:
            message = "Phương thức không được phép.";
            break;
        case 500:
            message = xhr.responseText;
            break;
        case 502:
            message = "Lỗi từ máy chủ phản hồi không hợp lệ.";
            break;
        case 503:
            message = "Dịch vụ hiện không khả dụng. Hãy thử lại sau.";
            break;
        case 504:
            message = "Hết thời gian chờ phản hồi từ máy chủ.";
            break;
        default:
            message = "Bạn cần đăng nhập để tiếp tục.";
            window.location.href = contextPath +`/views/login.jsp?error-message=${message}`;
//            message = `Lỗi không xác định (Mã lỗi: ${xhr.status})`;
    }

    console.error(`${title}: ${message}`);
    showAlert(title, message, alertType);
}
export function showAlert(title, message, type) {
    let alertClass = "alert-danger"; // Mặc định là lỗi (red)
    if (type === "success") alertClass = "alert-success"; // Xanh lá
    if (type === "info") alertClass = "alert-info"; // Xanh dương
    if (type === "warning") alertClass = "alert-warning"; // Vàng

    let alertBox = `
        <div class="alert ${alertClass} alert-dismissible fade show">
            <div class="alert-title">
                <strong>${title}</strong> 
            </div>
            <div class="alert-message">
                ${message}
            </div>
        </div>
    `;
    
    //THÊM VÀO NẾU MUỐN TẮT THÔNG BÁO
    //            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
//                <span aria-hidden="true">&times;</span>
//            </button>

    let alertContainer = $("#alert-container");
    
    // Hiển thị alert-container nếu đang bị ẩn
    alertContainer.html(alertBox).fadeIn();

    // Ẩn tự động sau 5 giây
    setTimeout(() => {
        $(".alert").fadeOut(500, function () {
            $(this).remove(); // Xóa phần tử alert khi ẩn xong
            if ($("#alert-container").children().length === 0) {
                alertContainer.fadeOut(); // Ẩn container nếu không còn alert nào
            }
        });
    }, 1000);
}



//EXAMPLE
//$.ajax({
//    url: "your-api-endpoint",
//    type: "POST",
//    data: { key: "value" },
//    success: function (response) {
//        showAlert("Thành công!", "Dữ liệu đã được gửi.", "success");
//    },
//    error: function (xhr) {
//        handleHttpStatus(xhr);
//    }
//});
