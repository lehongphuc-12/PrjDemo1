<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="orderService" class="service.OrderService" scope="request"/>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xác Nhận Đơn Hàng</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/allA.min.css">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/confirm.css">
</head>
<jsp:include page="../includes/head.jsp"></jsp:include>
<body>
    <!-- Header -->
    <jsp:include page="../includes/header.jsp"></jsp:include>

    <!-- Main Content -->
    <div class="confirmation-container">
        <div class="order-header">
            <h2><i class="fas fa-check-circle"></i> Xác Nhận Đơn Hàng</h2>
        </div>

        <!-- Display order details -->
        <c:if test="${not empty order}">
            <div class="alert alert-success" role="alert">
                Đơn hàng của bạn đã được tạo thành công với mã: <strong>#${order.orderID}</strong>
            </div>

            <!-- Order Details -->
            <div class="order-details">
                <h5><i class="fas fa-info-circle"></i> Thông Tin Đơn Hàng</h5>
                <p><strong>Ngày Đặt Hàng:</strong> <fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm:ss" /></p>
                <p><strong>Tổng Tiền:</strong> <fmt:formatNumber value="${order.totalAmount}" type="currency" currencySymbol="₫" groupingUsed="true"/></p>
            </div>

            <!-- Shipping Information -->
            <div class="shipping-info">
                <h5><i class="fas fa-shipping-fast"></i> Thông Tin Giao Hàng</h5>
                <c:choose>
                    <c:when test="${not empty order.shippingAddress}">
                        <p><c:out value="${order.shippingAddress}" escapeXml="false"/></p>
                    </c:when>
                    <c:otherwise>
                        <p class="text-muted">Không có thông tin giao hàng.</p>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- Payment Information -->
            <div class="payment-info">
                <h5><i class="fas fa-credit-card"></i> Thông Tin Thanh Toán</h5>
                <p><strong>Phương Thức Thanh Toán:</strong> 
                    <c:choose>
                        <c:when test="${not empty order.paymentMethodID and not empty order.paymentMethodID.methodName}">
                            ${order.paymentMethodID.methodName}
                        </c:when>
                        <c:otherwise>
                            Không xác định
                        </c:otherwise>
                    </c:choose>
                </p>
            </div>

            <!-- Order Items -->
            <div class="order-items">
                <h5><i class="fas fa-shopping-cart"></i> Sản Phẩm Đã Đặt</h5>
                <c:choose>
                    <c:when test="${not empty order.orderDetailCollection}">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Hình Ảnh</th>
                                    <th>Sản Phẩm</th>
                                    <th>Số Lượng</th>
                                    <th>Giá</th>
                                    <th>Tổng</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="detail" items="${order.orderDetailCollection}">
                                    <c:set var="product" value="${detail.productID}"/>
                                    <tr>
                                        <td>
                                            <c:set var="images" value="${orderService.getProductImages(product.productID)}"/>
                                            <c:choose>
                                                <c:when test="${not empty images and not empty images[0]}">
                                                    <img src="${pageContext.request.getContextPath()}/assets/images/productImages/${images[0].imageURL}" alt="${product.productName}" width="50" height="50">
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="${pageContext.request.contextPath}/assets/images/default-product.jpg" alt="Default Image" width="50" height="50">
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${product.productName}</td>
                                        <td>${detail.quantity}</td>
                                        <td><fmt:formatNumber value="${detail.price}" type="currency" currencySymbol="₫" groupingUsed="true"/></td>
                                        <td><fmt:formatNumber value="${detail.price.multiply(detail.quantity)}" type="currency" currencySymbol="₫" groupingUsed="true"/></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <p class="text-muted">Không có sản phẩm nào trong đơn hàng.</p>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- Action Buttons -->
            <div class="d-flex justify-content-between mt-4">
                <a href="${pageContext.request.contextPath}/products" class="btn btn-primary btn-continue-shopping">
                    <i class="fas fa-shopping-bag"></i> Tiếp Tục Mua Sắm
                </a>
                <a href="${pageContext.request.contextPath}/user?action=donHang" class="btn btn-primary btn-view-orders">
                    <i class="fas fa-history"></i> Xem Lịch Sử Đơn Hàng
                </a>
            </div>
        </c:if>

        <!-- Error Handling -->
        <c:if test="${empty order}">
            <div class="alert alert-danger" role="alert">
                ${not empty errorMessage ? errorMessage : 'Không tìm thấy đơn hàng.'}
            </div>
            <div class="text-center">
                <a href="${pageContext.request.contextPath}/shop" class="btn btn-primary btn-continue-shopping">
                    <i class="fas fa-shopping-bag"></i> Tiếp Tục Mua Sắm
                </a>
            </div>
        </c:if>
    </div>

    <!-- Footer -->
    <jsp:include page="../includes/footer.jsp"></jsp:include>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>