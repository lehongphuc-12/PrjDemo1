<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/user_list.css">

<div class="content-header">
    <h1>${param.action == 'nguoidung' ? 'Danh Sách Khách Hàng' : param.action == 'nguoiban' ? 'Danh Sách Người Bán' : 'Danh Sách Người Dùng'}</h1>
    <div class="search-container">
        <input type="text" id="searchInput" placeholder="Tìm kiếm người dùng..." class="search-bar">
        <a href="#" class="action-btn" onclick="return false;"><i class="fas fa-search search-icon"></i></a>
    </div>
    <div class="filter-section">
        <a href="#" class="action-btn" onclick="return false;"><i class="fas fa-filter"></i></a>
        <a href="#" class="action-btn" onclick="return false;"><i class="fas fa-sort"></i></a>
        <a href="${pageContext.request.contextPath}/admin?action=adduser" class="action-btn"><i class="fas fa-plus-circle"></i></a>            
    </div>
</div>

<!-- Hiển thị thông báo -->
<c:if test="${not empty message}">
    <div class="message success">${message}</div>
    <c:remove var="message" scope="session"/>
</c:if>
<c:if test="${not empty error}">
    <div class="message error">${error}</div>
    <c:remove var="error" scope="session"/>
</c:if>

<table class="product-table">
    <thead>
        <tr>
            <th>Mã Người Dùng</th>
            <th>Họ Tên</th>
            <th>Email</th>
            <th>Vai Trò</th>
            <th>Số Điện Thoại</th>
            <th>Địa Chỉ</th>
            <th>Ngày Tạo</th>
            <th>Hành Động</th>
        </tr>
    </thead>
    <tbody>
        <c:choose>
            <c:when test="${empty listUser}">
                <tr><td colspan="8">Không có dữ liệu người dùng nào.</td></tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="user" items="${listUser}">
                    <tr class="${user.status == false ? 'inactive-row' : ''}">
                        <td>${user.userID}</td>
                        <td>
                            <div class="name-container">
                                <span>${user.fullName}</span>
                                <c:if test="${user.status == false}">
                                    <span class="inactive-name">(bị chặn)</span>
                                </c:if>
                            </div>
                        </td>
                        <td>${user.email}</td>
                        <td>${user.roleID.roleName}</td>
                        <td>${user.phoneNumber}</td>
                        <td>${user.address}</td>
                        <td>
                            <fmt:formatDate value="${user.createdAt}" pattern="yyyy-MM-dd HH:mm" />
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${user.status == true}">
                                    <a href="${pageContext.request.contextPath}/admin?action=edit&id=${user.userID}" 
                                       class="action-btn edit-btn" 
                                       title="Sửa"><i class="fas fa-edit"></i></a>
                                    <a href="${pageContext.request.contextPath}/admin?action=delete&id=${user.userID}" 
                                       class="action-btn delete-btn" 
                                       onclick="return confirm('Bạn có chắc chắn muốn xóa?')"
                                       title="Xóa"><i class="fas fa-trash"></i></a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/admin?action=restore&id=${user.userID}" 
                                       class="action-btn restore-btn" 
                                       onclick="return confirm('Bạn có chắc chắn muốn khôi phục?')"
                                       title="Khôi phục"><i class="fas fa-undo"></i></a>
                                </c:otherwise>
                            </c:choose>
                            <a href="${pageContext.request.contextPath}/admin?action=profile&id=${user.userID}" 
                               class="action-btn profile-btn"
                               title="Xem hồ sơ"><i class="fas fa-user"></i></a>
                        </td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </tbody>
</table>
<div class="pagination">
    <c:if test="${currentPage > 1}">
        <a href="${pageContext.request.contextPath}/admin?action=${param.action}&page=${currentPage - 1}">Trước</a>
    </c:if>
    <c:forEach begin="1" end="${totalPages}" var="i">
        <a href="${pageContext.request.contextPath}/admin?action=${param.action}&page=${i}" 
           class="${i == currentPage ? 'active' : ''}">${i}</a>
    </c:forEach>
    <c:if test="${currentPage < totalPages}">
        <a href="${pageContext.request.contextPath}/admin?action=${param.action}&page=${currentPage + 1}">Tiếp Theo</a>
    </c:if>
</div>