<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Trang với Sidebar Trượt</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/sellerStyle.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    </head>
    <body>
        <div class="sidebar">
            <div class="account-info">
                <img src="https://static.vecteezy.com/system/resources/previews/009/292/244/non_2x/default-avatar-icon-of-social-media-user-vector.jpg" alt="Avatar">
                <div>
                    <div class="username">${user.fullName}</div>
                    <a onclick="loadSection('hoso')" class="edit-profile"><i class="fas fa-pencil-alt" style="margin-right: 5px;"></i> Sửa Hồ Sơ</a>
                </div>
            </div>
            <ul>
                <li>
                    <a>Hồ sơ người bán <i class="fas fa-chevron-down"></i></a>
                    <ul>
                        <li><a onclick="loadSection('hoso')">Hồ sơ</a></li>
                        <li><a onclick="loadSection('password')">Đổi mật khẩu</a></li>
                    </ul>
                </li>
                <li>
                    <a>Tài chính <i class="fas fa-chevron-down"></i></a>
                    <ul>
                        <li><a onclick="loadSection('doanhthu')">Doanh thu</a></li>
                        <li><a href="#">Tài khoản ngân hàng</a></li>
                    </ul>
                </li>
                <li>
                    <a>Dữ liệu <i class="fas fa-chevron-down"></i></a>
                    <ul>
                        <li><a onclick="loadSection('phantich')">Phân tích bán hàng</a></li>
                        <li><a onclick="loadSection('hieuqua')">Hiệu quả hoạt động</a></li>
                    </ul>
                </li>
                <li>
                    <a>Quản lí Sản Phẩm <i class="fas fa-chevron-down"></i></a>
                    <ul>
                        <li><a onclick="loadSection('sanphamlist')">Tất cả sản phẩm</a></li>
                        <li><a onclick="loadSection('removedProduct')">Sản phẩm đã xóa</a></li>
                        <li><a onclick="loadSection('addSanpham')">Thêm sản phẩm</a></li>
                        <li><a onclick="loadSection('discountList')">Quản lí discount</a></li>
                    </ul>
                </li>
                <li>
                    <a>Quản lí Đơn Hàng <i class="fas fa-chevron-down"></i></a>
                    <ul>
                        <li><a onclick="loadSection('donhang')">Tất cả</a></li>
                        <li><a onclick="loadSection('xulidon')">Xác nhận đơn hàng</a></li>
                    </ul>
                </li>
            </ul>
            <a href="${pageContext.request.contextPath}/products" class="back-btn" style="display: inline-block;"><i class="fas fa-arrow-left" style="font-size: 15px; margin-right: 5px;"></i>Quay lại</a>
        </div>

        <div class="main-content" id="main-content">
            <jsp:include page="Hoso.jsp"></jsp:include>
            </div>


        </body>
        <script>
        var contextPath = "${pageContext.request.contextPath}";
    </script>
    <script src="${pageContext.request.contextPath}/assets/js/seller_page.js"></script>
    <script>
        
        
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
            
    </script>
</html>