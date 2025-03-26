<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Trang Hồ sơ - Nông sản Tươi Sạch</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/userProfile.css">
         <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Sharp" rel="stylesheet">
    
    <!--AWESOME-->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    </head>

    <body>
        <!-- Header -->
        <header>
    <c:set var="userRole" value="${sessionScope.user != null && sessionScope.user.roleID != null ? sessionScope.user.roleID.roleID : 0}" />

    <div class="top__header">
        <div class="container">
            <div class="top_header_content">
                <c:choose>
                    <c:when test="${userRole == 1}">
                        <span><a href="${pageContext.request.contextPath}/admin" class="text-banner">Kênh quản lý</a></span>
                        <span><a href="${pageContext.request.contextPath}/admin?action=hoso" class="text-banner">Hồ sơ của bạn</a></span>
                    </c:when>
                    <c:when test="${userRole == 2}">
                        <span><a href="${pageContext.request.contextPath}/seller?action=sellerPage" class="text-banner">Kênh bán hàng</a></span>
                        <span><a href="${pageContext.request.contextPath}/seller?action=sellerPage" class="text-banner">Hồ sơ của bạn</a></span>
                    </c:when>
                    <c:when test="${userRole == 3}">
                        <span><a href="${pageContext.request.contextPath}/user?action=userPage" class="text-banner">Hồ sơ của bạn</a></span>
                        <span><a href="${pageContext.request.contextPath}/SellerRegistrationServlet" class="text-banner">Đăng ký bán hàng</a></span>
                    </c:when>
                    <c:otherwise>
                        <!-- Guest không hiển thị gì -->
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="header_page">
            <div class="logo__header">
                <a href="${pageContext.request.contextPath}/products"><img src="${pageContext.request.contextPath}/assets/images/logo.png" alt=""></a>
                <div class="title__header">
                    <span><p class="text-warning">Hội chợ</p></span>
                    <span><p class="text-primary">Nông sản</p></span>
                </div>
            </div>
            
            <div class="right__header">
                <c:choose>
                    <c:when test="${userRole == 0}">
                        <a href="${pageContext.request.contextPath}/logins" class="user_profile">
                            <span class="material-icons-sharp">person</span>
                            <p>Đăng nhập</p>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/logout" class="user_profile">
                            <span class="material-icons-sharp">logout</span>
                            <p>Đăng xuất</p>
                        </a>
                        <a href="${pageContext.request.contextPath}/cart" class="cart-processing">
                            <span class="material-icons-sharp">add_shopping_cart</span>
                            <p>Giỏ hàng</p>
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</header>
        
        
        
        
        
        
        
        <!-- Main Content -->
        <div class="container mt-4" style="width: fit-content !important;">
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
                <script>
                    document.addEventListener("DOMContentLoaded", function () {
                        var urlParams = new URLSearchParams(window.location.search);
                        var autoLoad = urlParams.get("autoLoad");

                        if (autoLoad === "donHang") {
                            loadSection("donHang");
                        }
                    });

                </script>
           
    </body>
</html>