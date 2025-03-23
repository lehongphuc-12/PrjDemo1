<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý yêu cầu đăng ký bán hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin_requests.css">
</head>
<body>
    <h2>Danh sách yêu cầu đăng ký bán hàng</h2>
    <div class="message">
        <c:if test="${not empty sessionScope.message}">
            <p class="success">${sessionScope.message}</p>
            <c:remove var="message" scope="session"/>
        </c:if>
        <c:if test="${not empty sessionScope.error}">
            <p class="error">${sessionScope.error}</p>
            <c:remove var="error" scope="session"/>
        </c:if>
    </div>
    <table>
        <tr>
            <th>ID Yêu cầu</th>
            <th>ID Người dùng</th>
            <th>Tên cửa hàng</th>
            <th>Trạng thái</th>
            <th>Hành động</th>
        </tr>
        <c:forEach var="request" items="${requestList}">
            <tr>
                <td>${request.requestID}</td>
                <td>${request.user != null ? request.user.userID : 'N/A'}</td>
                <td>${request.shopName}</td>
                <td>${request.status}</td>
                <td class="action-links">
                    <c:if test="${request.status == 'pending'}">
                        <a href="${pageContext.request.contextPath}/admin?action=approveRequest&requestId=${request.requestID}" class="approve">Duyệt</a>
                        <a href="${pageContext.request.contextPath}/admin?action=rejectRequest&requestId=${request.requestID}" class="reject">Từ chối</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>

    <!-- Phân trang -->
    <div class="pagination">
        <c:if test="${totalPages > 1}">
            <c:forEach begin="1" end="${totalPages}" var="i">
                <c:choose>
                    <c:when test="${i == currentPage}">
                        <span>${i}</span>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/admin?action=listRequests&page=${i}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </c:if>
    </div>
</body>
</html>