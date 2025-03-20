<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                        <span><a href="${pageContext.request.contextPath}/seller" class="text-banner">Kênh bán hàng</a></span>
                        <span><a href="${pageContext.request.contextPath}/profile" class="text-banner">Hồ sơ của bạn</a></span>
                    </c:when>
                    <c:when test="${userRole == 3}">
                        <span><a href="${pageContext.request.contextPath}/profile" class="text-banner">Hồ sơ của bạn</a></span>
                        <span><a href="${pageContext.request.contextPath}/seller/register" class="text-banner">Đăng ký bán hàng</a></span>
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
            
            <div class="search__header">
                <form action="${pageContext.request.contextPath}/cates" method="GET">
                    <input type="hidden" name="action" value="search">
                    <div class="dropdown">
                        <select name="searchType" id="searchType">
                            <option value="products">Hàng hoá</option>
                            <option value="stores">Cửa hàng</option>
                        </select>
                    </div>
                    <div class="search_input">
                        <input name="searchbar" type="text" placeholder="Nhập từ khóa" value="${search}">
                    </div>
                    <button>
                        <span class="material-icons-sharp btn btn-primary">search</span>
                    </button>
                </form>
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