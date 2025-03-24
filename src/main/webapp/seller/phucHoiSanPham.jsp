<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
        border-color: #28a745; /* Xanh lá chuối */
    }

    .search-icon {
        color: #fff; /* Xanh lá chuối */
        /*        font-size: 18px;*/
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
        /*        width: 35px;
                height: 35px;
                border-radius: 50%;*/
        text-decoration: none;
        color: #28a745;
        /*        background-color: #28a745;  Xanh lá chuối */
        /*        transition: background-color 0.3s;*/
    }
    .remove-btn {
        color: red;
    }
    .action-btn:hover {
        background-color: #2ecc71; /* Xanh lá sáng hơn khi hover */
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
        background-color: #28a745; /* Xanh lá chuối */
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

    reCreate-btn:hover {
        background-color: #E07B00;
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
    <h1>Danh Sách Sản Phẩm Đã Xóa</h1>
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
        <c:forEach var="product" items="${productRemovedList}">
            <tr>
                <td>${product.productID}</td>
                <td>${product.productName}</td>
                <td>${product.description}</td>
                <td>${product.price}</td>
                <td>${product.quantity}</td>
                <td>${product.categoryID.categoryName}</td>
                <td>
                    <fmt:formatDate value="${product.createdDate}" pattern="yyyy-MM-dd" />
                </td>

                <td>${product.cityID.cityName}</td>
                <td><button type="button" onclick="reCreateProduct(${product.productID})" class="reCreate-btn" style="color: white;
                            border: none;
                            padding: 6px 12px;
                            border-radius: 4px;
                            cursor: pointer;
                            background-color: #FF8C00;"
                            onmouseover="this.style.backgroundColor = '#E07B00'" 
                            onmouseout="this.style.backgroundColor = '#FF8C00'">Phục hồi </button></td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<div class="pagination">
    <c:if test="${totalPage >= 1}">
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