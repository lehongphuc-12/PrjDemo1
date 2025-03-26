<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<style>
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

    .voucher-table {
        width: 100%;
        border-collapse: collapse;
        margin: 20px 0;
        background-color: #fff;
        box-shadow: 0 2px 5px rgba(0,0,0,0.1);
    }

    .voucher-table th {
        background-color: #28a745;
        color: white;
        padding: 12px 15px;
        text-align: left;
    }

    .voucher-table td {
        padding: 12px 15px;
        border-bottom: 1px solid #e9ecef;
    }

    .voucher-table tr:nth-child(even) {
        background-color: #f8f9fa;
    }

    .voucher-table tr:hover {
        background-color: #f1f3f5;
    }

    .action-btn {
        padding: 8px;
        color: #28a745;
        text-decoration: none;
        border-radius: 50%;
        transition: all 0.3s ease;
    }

    .remove-btn {
        color: #dc3545;
    }

    .action-btn:hover {
        background-color: #28a745;
        color: #fff;
    }

    .remove-btn:hover {
        background-color: #dc3545;
        color: #fff;
    }

    .voucher-form {
        margin: 20px 0;
        display: flex;
        gap: 10px;
        align-items: center;
        flex-wrap: wrap;
    }

    .input-with-icon {
        position: relative;
        width: 200px;
    }

    .input-with-icon input {
        width: 100%;
        padding: 8px 30px 8px 8px;
        border: 1px solid #ced4da;
        border-radius: 5px;
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

    .voucher-form input, .voucher-form select {
        padding: 8px;
        border: 1px solid #ced4da;
        border-radius: 5px;
    }

    .voucher-form button {
        padding: 8px 15px;
        background-color: #28a745;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }

    .voucher-form button:hover {
        background-color: #218838;
    }
</style>

<div class="content-header">
    <h1>Quản lý Voucher</h1>
</div>

<!-- Bảng danh sách voucher -->
<table class="voucher-table">
    <thead>
        <tr>
            <th>Mã Voucher</th>
            <th>Phần trăm giảm</th>
            <th>Ngày bắt đầu</th>
            <th>Ngày kết thúc</th>
            <th>ID Sản phẩm</th>
            <th>Hành động</th>
        </tr>
    </thead>
    <tbody>
        <c:choose>
            <c:when test="${empty voucherList}">
                <tr>
                    <td colspan="6" style="text-align: center;">Không có voucher nào.</td>
                </tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="voucher" items="${voucherList}">
                    <tr>
                        <td>${voucher.discountCode}</td>
                        <td>${voucher.discountPercent}%</td>
                        <td><fmt:formatDate value="${voucher.startDate}" pattern="yyyy-MM-dd" /></td>
                        <td><fmt:formatDate value="${voucher.endDate}" pattern="yyyy-MM-dd" /></td>
                        <td>${voucher.productID != null ? voucher.productID.productID : 'Chưa gán'}</td>
                        <td>
                            <a class="action-btn remove-btn" onclick="removeVoucher('${voucher.discountCode}','${voucher.discountID}')">
                                <i class="fas fa-trash"></i>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </tbody>
</table>

<script>
    function generateRandomVoucherCode() {
        const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
        let randomCode = '';
        for (let i = 0; i < 10; i++) {
            const randomIndex = Math.floor(Math.random() * characters.length);
            randomCode += characters[randomIndex];
        }
        document.getElementById('discountCode').value = randomCode;
    }

    function addVoucher() {
        const code = document.getElementById('discountCode').value.trim();
        const discountPercent = document.getElementById('discountPercent').value;
        const startDate = document.getElementById('startDate').value;
        const endDate = document.getElementById('endDate').value;
        const productId = document.getElementById('productId').value;

        if (!code || !discountPercent || !startDate || !endDate) {
            alert("Vui lòng nhập đầy đủ thông tin bắt buộc!");
            return;
        }

        const today = new Date().toISOString().split('T')[0];
        if (startDate < today) {
            alert("Ngày bắt đầu không thể là ngày trong quá khứ!");
            return;
        }
        if (endDate < startDate) {
            alert("Ngày kết thúc phải sau ngày bắt đầu!");
            return;
        }

        const xhr = new XMLHttpRequest();
        const url = "${pageContext.request.contextPath}/seller?action=addVoucher" +
                    "&code=" + encodeURIComponent(code) +
                    "&discountPercent=" + encodeURIComponent(discountPercent) +
                    "&startDate=" + encodeURIComponent(startDate) +
                    "&endDate=" + encodeURIComponent(endDate) +
                    (productId ? "&productId=" + encodeURIComponent(productId) : "");

        xhr.open("GET", url, true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                document.getElementById("main-content").innerHTML = xhr.responseText;
                // Reset form
                document.getElementById('discountCode').value = '';
                document.getElementById('discountPercent').value = '';
                document.getElementById('startDate').value = '';
                document.getElementById('endDate').value = '';
                document.getElementById('productId').value = '';
            }
        };
        xhr.send();
    }

    function removeVoucher(code, discountID) {
        if (!confirm(`Bạn có chắc muốn xóa voucher ${code}?`)) return;

        const xhr = new XMLHttpRequest();
        const url = "${pageContext.request.contextPath}/voucher?action=removeVoucher&code=" + encodeURIComponent(code) +"&discountID=" + encodeURIComponent(discountID);

        xhr.open("POST", url, true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                document.getElementById("main-content").innerHTML = xhr.responseText;
                alert("Xóa thành công voucher!");
            }
        };
        xhr.send();
    }
</script>