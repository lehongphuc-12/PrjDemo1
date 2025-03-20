<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="model.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang với Sidebar Trượt</title>
    
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/adminStyle.css">
    
</head>
<body>
    <div class="sidebar">
        <div class="account-info">
            <img src="https://static.vecteezy.com/system/resources/previews/009/292/244/non_2x/default-avatar-icon-of-social-media-user-vector.jpg" alt="Avatar">
            <div>
                <div class="username">${session.user.fullName}</div>
                <a href="${pageContext.request.contextPath}/admin?action=editProfile" class="edit-profile">
                    <i class="fas fa-pencil-alt"></i> Sửa Hồ Sơ
                </a>
            </div>
        </div>
        <ul>
            <!-- Mục Trang Chủ -->
            <li>
                <li>
                    <a href="${pageContext.request.contextPath}/admin">Trang Chủ</a>
                </li>
            </li>
            <li>
                <a>Hồ sơ admin <i class="fas fa-chevron-down"></i></a>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/admin?action=hoso">Hồ sơ</a></li>
                </ul>
            </li>
            <li>
                <a>Quản lí người dùng <i class="fas fa-chevron-down"></i></a>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/admin?action=nguoidung">Người dùng</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin?action=adduser">Thêm User</a></li>
                </ul>
            </li>
            <li>
                <c:choose>
                    <c:when test="${pageContext.request.requestURI.endsWith('/admin')}">
                        <a>Quản lí User <i class="fas fa-chevron-down"></i></a>
                        <ul>
                            <li><a href="${pageContext.request.contextPath}/admin?action=nguoiban">Danh Sách User</a></li>
                            <li><a href="${pageContext.request.contextPath}/admin?action=adduser">Thêm User</a></li>
                        </ul>
                    </c:when>
                    <c:otherwise>
                        <a>Quản lí người bán <i class="fas fa-chevron-down"></i></a>
                        <ul>
                            <li><a href="${pageContext.request.contextPath}/admin?action=nguoiban">Người bán</a></li>
                            <li><a href="${pageContext.request.contextPath}/admin?action=adduser">Thêm User</a></li>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </li>
            <li>
                <a>Thống kê <i class="fas fa-chevron-down"></i></a>
                <ul>
                    <li>
                        <a href="${pageContext.request.contextPath}/admin?action=stats">Thống kê</a>
                    </li>
                </ul>
            </li>
        </ul>
        <a href="${pageContext.request.contextPath}/" class="back-btn" style="display: inline-block;"><i class="fas fa-arrow-left" style="font-size: 15px;"></i> Đăng xuất</a>
    </div>

    <div class="main-content">
        <jsp:include page="${part}"></jsp:include>
    </div>

    <script>
        const menuItems = document.querySelectorAll('.sidebar ul li');
        menuItems.forEach(item => {
            const link = item.querySelector('a');
            const submenu = item.querySelector('ul');
            if (submenu) {
                link.addEventListener('click', e => {
                    e.preventDefault();
                    const isActive = item.classList.contains('active');
                    menuItems.forEach(i => i.classList.remove('active'));
                    if (!isActive) item.classList.add('active');
                });
            }
        });
    </script>
</body>
</html>