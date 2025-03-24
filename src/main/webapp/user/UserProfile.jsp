<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Trang Hồ sơ - Nông sản Tươi Sạch</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/userProfile.css">
    </head>

    <body>
        <!-- Header -->
        <div class="header">
            <div class="container">
                <nav class="navbar navbar-expand-lg">
                    <a class="navbar-brand text-white" href="${pageContext.request.contextPath}/products" >Back</a>
                    <div class="ms-auto">
                        <a href="${pageContext.request.contextPath}/logout" class="text-white">Đăng xuất</a>
                    </div>
                </nav>
            </div>
        </div>

        <!-- Main Content -->
        <div class="container mt-4">
            <div class="profile-container">
                <!-- Sidebar -->
                <div class="sidebar">
                    <div class="account-info">
                        <div class="d-flex align-items-center justify-content-center">
                            <img src="https://static.vecteezy.com/system/resources/previews/009/292/244/non_2x/default-avatar-icon-of-social-media-user-vector.jpg" alt="Avatar">
                            <div>
                                <div class="username">${user.fullName}</div>
                                <a href="#" class="edit-profile"  onclick="loadSection('hoso')"><i class="bi bi-pencil"></i> Sửa Hồ Sơ</a>
                            </div>
                        </div>
                    </div>

                    <nav class="nav flex-column">
                        <a href="#collapseAccount" class="nav-link active" data-bs-toggle="collapse" aria-expanded="true"
                           aria-controls="collapseAccount">
                            <i class="bi bi-person"></i> Tài Khoản Của Tôi
                        </a>
                        <div class="collapse show" id="collapseAccount">
                            <div class="collapse-content">
                                <a href="#" class="nav-link" onclick="loadSection('hoso')"><i
                                        class="bi bi-house"></i> Hồ Sơ</a>
                                <a href="#" class="nav-link" onclick="loadSection('password')"><i
                                        class="bi bi-shield-lock"></i> Đổi Mật Khẩu</a>
                                <a href="#" class="nav-link" ><i
                                        class="bi bi-wallet"></i> Cài Đặt Thông Báo</a>
                            </div>
                        </div>
                        <a href="#" class="nav-link" onclick="loadSection('notifications')">
                            <i class="bi bi-bell"></i> Thông Báo
                        </a>
                        <a href="#" class="nav-link" onclick="loadSection('donHang')">
                            <i class="bi bi-cart"></i> Đơn mua
                        </a>

                    </nav>
                </div>
                <!-- Nội dung chính -->
                <div class="content-main" id="content-main">
                    <jsp:include page="Hoso.jsp"></jsp:include>
                    </div>
                </div>

                <!-- Bootstrap JS -->
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
                <script>
                    var contextPath = "${pageContext.request.contextPath}";
                </script>
                <script src="${pageContext.request.contextPath}/assets/js/userPage.js"></script>
           
    </body>
</html>