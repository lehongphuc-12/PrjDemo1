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

    /* Style cho search container */
    .search-container {
        display: flex;
        align-items: center;
        position: relative; /* Thêm để định vị icon */
    }

    .search-bar {
        padding: 8px 15px 8px 40px; /* Thêm padding trái cho icon */
        border: 1px solid #ced4da;
        border-radius: 20px;
        width: 250px;
        outline: none;
        transition: border-color 0.3s, box-shadow 0.3s;
    }

    .search-bar:focus {
        border-color: #28a745;
        box-shadow: 0 0 5px rgba(40, 167, 69, 0.3);
    }

    .search-icon {
        position: absolute;
        left: 12px; /* Đặt icon bên trong input */
        color: #28a745;
        font-size: 18px;
        pointer-events: none; /* Ngăn icon bị click */
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
        border-radius: 50%; /* Làm tròn nút */
        width: 32px; /* Kích thước đồng nhất */
        height: 32px;
        transition: all 0.3s ease;
    }

    .remove-btn {
        color: #dc3545; /* Màu đỏ đậm hơn */
    }

    .action-btn:hover {
        color: #fff;
        background-color: #28a745;
    }

    .remove-btn:hover {
        background-color: #dc3545;
        color: #fff;
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
        transition: all 0.3s ease;
    }

    .pagination a:hover {
        background-color: #28a745;
        color: white;
        border-color: #28a745;
    }

    .pagination a.active {
        background-color: #28a745;
        color: white;
        border-color: #28a745;
    }

    .pagination a.disabled {
        color: #ced4da;
        pointer-events: none;
        background-color: #f8f9fa;
    }
    .voucher-wrapper {
        position: relative;
        display: inline-block;
    }

    .voucher-form-container {
        position: absolute;
        top: 40px; /* Khoảng cách từ nút icon xuống form */
        right: 0; /* Căn phải với nút icon */
        background-color: #fff;
        padding: 15px;
        border-radius: 10px;
        box-shadow: 0 4px 10px rgba(0,0,0,0.2);
        z-index: 1000;
        width: 300px; /* Đặt chiều rộng cố định cho form */
    }

    .voucher-form h3 {
        margin-bottom: 10px;
        color: #2c3e50;
        font-size: 16px;
    }

    .voucher-form input {
        display: block;
        width: 100%;
        padding: 6px;
        margin-bottom: 8px;
        border: 1px solid #ced4da;
        border-radius: 5px;
        font-size: 14px;
    }

    .voucher-form button {
        padding: 6px 12px;
        margin-right: 8px;
        background-color: #28a745;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-size: 14px;
    }

    .voucher-form button:hover {
        background-color: #218838;
    }

    .voucher-form button:nth-child(2) {
        background-color: #dc3545;
    }

    .voucher-form button:nth-child(2):hover {
        background-color: #c82333;
    }
    .input-with-icon {
        position: relative;
        width: 100%;
    }

    .input-with-icon input {
        padding-right: 30px; /* Để chừa chỗ cho icon */
    }

    .random-icon {
        position: absolute;
        right: 10px;
        top: 50%;
        transform: translateY(-50%);
        color: #28a745;
        cursor: pointer;
        font-size: 14px;
    }

    .random-icon:hover {
        color: #218838;
    }
</style>

<div class="content-header">
    <h1>Danh Sách Sản Phẩm</h1>
    <div class="search-container">
        <input type="text" id="searchInput" placeholder="Tìm kiếm sản phẩm..." class="search-bar" value="${param.search != null ? param.search : ''}">
        <i class="fas fa-search search-icon"></i> 
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
                            <a  class="action-btn" onclick="loadSection('updateProduct&productId=${product.productID}')">
                                <i class="fas fa-pencil-alt"></i>
                            </a>
                            <a  class="action-btn remove-btn" onclick="removeProduct(${product.productID});">
                                <i class="fas fa-trash"></i>
                            </a>
                            <a href="#" class="action-btn" onclick="showVoucherForm(); return false;"><i class="fas fa-ticket-alt"></i></a>
                            <div id="voucherFormContainer" class="voucher-form-container" style="display: none;">
                                <div class="voucher-form">
                                    <h3>Thêm Voucher</h3>
                                    <div class="input-with-icon">
                                        <input type="text" id="discountCode" placeholder="Mã giảm giá (VD: DISCOUNT10)">
                                        <i class="fas fa-sync-alt random-icon" onclick="generateRandomVoucherCode()"></i>
                                    </div>
                                    <input type="number" id="discountPercent" placeholder="Phần trăm giảm (%)" min="0" max="100">
                                    <input type="date" id="startDate" placeholder="Ngày bắt đầu">
                                    <input type="date" id="endDate" placeholder="Ngày kết thúc">
                                    <input type="hidden" id="productId" placeholder="ID sản phẩm" value="${product.productID}" >
                                    <button onclick="addVoucherToProduct()">Gán Voucher</button>
                                    <button onclick="hideVoucherForm()">Hủy</button>
                                </div>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </tbody>
</table>

<div class="pagination" id="pagination">
    <c:if test="${totalPage > 1}">
        <c:choose>
            <c:when test="${param.page == null || param.page == 1}">
                <a href="#" class="disabled">« Previous</a>
            </c:when>
            <c:otherwise>
                <a onclick="loadSection('sanphamlist&page=${param.page - 1}&search=${param.search != null ? param.search : ''}')">« Previous</a>
            </c:otherwise>
        </c:choose>
        <c:forEach begin="1" end="${totalPage}" var="i">
            <a class="${param.page == i || (param.page == null && i == 1) ? 'active' : ''}" onclick="loadSection('sanphamlist&page=${i}&search=${param.search != null ? param.search : ''}')">${i}</a>
        </c:forEach>
        <c:choose>
            <c:when test="${param.page == totalPage || (param.page == null && totalPage == 1)}">
                <a href="#" class="disabled">Next »</a>
            </c:when>
            <c:otherwise>
                <a onclick="loadSection('sanphamlist&page=${(param.page == null ? 1 : param.page) + 1}&search=${param.search != null ? param.search : ''}')">Next »</a>
            </c:otherwise>
        </c:choose>
    </c:if>
</div>

<<script>
    function showVoucherForm() {
        document.getElementById('voucherFormContainer').style.display = 'block';
    }

    function hideVoucherForm() {
        document.getElementById('voucherFormContainer').style.display = 'none';
        // Reset form
        document.getElementById('discountCode').value = '';
        document.getElementById('discountPercent').value = '';
        document.getElementById('startDate').value = '';
        document.getElementById('endDate').value = '';
        document.getElementById('productId').value = '';
    }


</script>
