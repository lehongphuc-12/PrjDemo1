<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<style>
    /* Reset cơ bản */
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }

    /* Style cho header */
    .content-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 20px;
        background-color: #f8f9fa;
        border-bottom: 1px solid #e9ecef;
    }

    .content-header h1 {
        color: #2c3e50;
        font-size: 24px;
    }

    /* Style cho search */
    .search-container {
        display: flex;
        align-items: center;
        gap: 10px;
    }

    .search-bar {
        padding: 8px 15px;
        border: 1px solid #ced4da;
        border-radius: 20px;
        width: 250px;
        outline: none;
        transition: border-color 0.3s;
    }

    .search-bar:focus {
        border-color: #28a745;
    }

    .search-icon {
        color: #28a745;
        font-size: 18px;
    }

    /* Style cho filter section */
    .filter-section {
        display: flex;
        gap: 15px;
    }

    /* Style chung cho các nút action */
    .action-btn {
        display: inline-flex;
        align-items: center;
        justify-content: center;
        text-decoration: none;
        color: #28a745;
        padding: 8px;
        transition: color 0.3s;
    }

    .remove-btn {
        color: red;
    }

    .action-btn:hover {
        color: #2ecc71;
    }

    .action-btn i {
        font-size: 16px;
    }

    /* Style cho bảng */
    .product-table {
        width: 100%;
        border-collapse: collapse;
        margin: 20px 0;
        background-color: #fff;
        box-shadow: 0 2px 5px rgba(0,0,0,0.1);
    }

    .product-table th {
        background-color: #28a745;
        color: white;
        padding: 12px 15px;
        text-align: left;
    }

    .product-table td {
        padding: 12px 15px;
        border-bottom: 1px solid #e9ecef;
    }

    .product-table tr:nth-child(even) {
        background-color: #f8f9fa;
    }

    .product-table tr:hover {
        background-color: #f1f3f5;
    }

    /* Style cho phân trang */
    .pagination {
        display: flex;
        justify-content: center;
        gap: 10px;
        margin: 20px 0;
    }

    .pagination a {
        padding: 8px 12px;
        text-decoration: none;
        color: #28a745;
        border: 1px solid #ced4da;
        border-radius: 5px;
        transition: background-color 0.3s;
    }

    .pagination a:hover {
        background-color: #28a745;
        color: white;
        cursor: pointer;
    }

    .pagination a.active {
        background-color: #28a745;
        color: white;
        border-color: #28a745;
    }

    .pagination a.disabled {
        color: #ced4da;
        pointer-events: none;
    }
</style>

<div class="content-header">
    <h1>Danh Sách Sản Phẩm</h1>
    <div class="search-container">
        <input type="text" id="searchInput" placeholder="Tìm kiếm sản phẩm..." class="search-bar">
        <a href="#" class="action-btn" onclick="return false;"><i class="fas fa-search search-icon"></i></a>
    </div>
    <div class="filter-section">
        <a href="#" class="action-btn" onclick="return false;"><i class="fas fa-filter"></i></a>
        <a href="#" class="action-btn" onclick="return false;"><i class="fas fa-sort"></i></a>
        <a href="#" class="action-btn" onclick="loadSection('addSanpham'); return false;"><i class="fas fa-plus-circle"></i></a>
    </div>
</div>

<table class="product-table">
    <thead>
        <tr>
            <th>ProductID</th>
            <th>ProductName</th>
            <th>Description</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>CategoryID</th>
            <th>CreatedDate</th>
            <th>CityID</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <c:choose>
            <c:when test="${empty productList}">
                <tr>
                    <td colspan="9" style="text-align: center;">Không có sản phẩm nào.</td>
                </tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="product" items="${productList}">
                    <tr>
                        <td>${product.productID}</td>
                        <td>${product.productName}</td>
                        <td>${product.description}</td>
                        <td>${product.price}</td>
                        <td>${product.quantity}</td>
                        <td>${product.categoryID.categoryName}</td>
                        <td><fmt:formatDate value="${product.createdDate}" pattern="yyyy-MM-dd" /></td>
                        <td>${product.cityID.cityName}</td>
                        <td>
                            <a href="#" class="action-btn" onclick="loadSection('updateProduct&productId=${product.productID}')">
                                <i class="fas fa-pencil-alt"></i>
                            </a>
                            <a href="#" class="action-btn remove-btn" onclick="removeProduct(${product.productID});">
                                <i class="fas fa-trash"></i>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </tbody>
</table>

<div class="pagination">
    <c:if test="${totalPage > 1}">
        <!-- Nút Previous -->
        <c:choose>
            <c:when test="${param.page == null || param.page == 1}">
                <a href="#" class="disabled">&laquo; Previous</a>
            </c:when>
            <c:otherwise>
                <a onclick="loadSection('sanphamlist&page=${param.page - 1}')">&laquo; Previous</a>
            </c:otherwise>
        </c:choose>

        <!-- Số trang -->
        <c:forEach begin="1" end="${totalPage}" var="i">
            <a class="${param.page == i || (param.page == null && i == 1) ? 'active' : ''}" onclick="loadSection('sanphamlist&page=${i}')">${i}</a>
        </c:forEach>

        <!-- Nút Next -->
        <c:choose>
            <c:when test="${param.page == totalPage || (param.page == null && totalPage == 1)}">
                <a href="#" class="disabled">Next &raquo;</a>
            </c:when>
            <c:otherwise>
                <a onclick="loadSection('sanphamlist&page=${(param.page == null ? 1 : param.page) + 1}')">Next »</a>
            </c:otherwise>
        </c:choose>
    </c:if>
</div>