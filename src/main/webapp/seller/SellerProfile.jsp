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
</html>