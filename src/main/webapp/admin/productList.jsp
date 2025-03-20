<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách sản phẩm</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/product_list.css">

</head>
<body>
    <div class="product-container">
        <h2>Danh sách sản phẩm của người bán</h2>
        
        <c:choose>
            <c:when test="${not empty message}">
                <div class="message">${message}</div>
            </c:when>
            <c:otherwise>
                <table>
                    <tr>
                        <th>Mã Sản Phẩm</th>
                        <th>Tên sản phẩm</th>
                        <th>Hình ảnh</th> 
                        <th>Mô tả</th>
                        <th>Giá</th>
                        <th>Số lượng</th>
                        <th>Ngày tạo</th>
                        <th>Danh mục</th>
                        <th>Thành phố</th>
                        <th>Hành Động</th>
                    </tr>
                    <c:forEach var="product" items="${productList}">
                        <tr>
                            <td>${product.productID}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${product.productName == 'INACTIVE'}">
                                        <span class="inactive">
                                            ${fn:substringAfter(product.description, 'Original Name: ')} + inactive
                                        </span>
                                    </c:when>
                                    <c:otherwise>${product.productName}</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty product.productImageCollection}">
                                        <!-- Hiển thị hình ảnh đầu tiên -->
                                        <c:forEach var="image" items="${product.productImageCollection}" begin="0" end="0">
                                            <img src="${pageContext.request.getContextPath()}/assets/images/productImages/${image.imageURL}" alt="Product Image" class="product-image">
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="no-image">Không có hình ảnh</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${product.description}</td>
                            <td>${product.price}</td>
                            <td>${product.quantity}</td>
                            <td>${product.createdDate}</td>
                            <td>${product.categoryID.categoryName}</td>
                            <td>${product.cityID.cityName}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${product.productName != 'INACTIVE'}">
                                        <a href="${pageContext.request.contextPath}/products?action=delete&productId=${product.productID}&sellerId=${sellerId}" 
                                           class="action-btn delete-btn" 
                                           onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')">Xóa</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="${pageContext.request.contextPath}/products?action=restore&productId=${product.productID}&sellerId=${sellerId}" 
                                           class="action-btn restore-btn" 
                                           onclick="return confirm('Bạn có chắc chắn muốn khôi phục sản phẩm này?')">Restore</a>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>

        <div class="pagination">
            <c:if test="${currentPage > 1}">
                <a href="${pageContext.request.contextPath}/products?action=listProductsBySeller&sellerId=${sellerId}&page=${currentPage - 1}">Previous</a>
            </c:if>
            <c:forEach begin="1" end="${totalPages}" var="i">
                <a href="${pageContext.request.contextPath}/products?action=listProductsBySeller&sellerId=${sellerId}&page=${i}" 
                   class="${i == currentPage ? 'active' : ''}">${i}</a>
            </c:forEach>
            <c:if test="${currentPage < totalPages}">
                <a href="${pageContext.request.contextPath}/products?action=listProductsBySeller&sellerId=${sellerId}&page=${currentPage + 1}">Next</a>
            </c:if>
        </div>

        <div class="btn-container">
            <a href="${pageContext.request.contextPath}/admin?action=profile&id=${sellerId}" class="btn">Quay lại hồ sơ</a>
        </div>
    </div>
</body>
</html>