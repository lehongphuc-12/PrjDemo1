<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thanh Toán</title>
    
    <!--head-->
    <jsp:include page="/includes/head.jsp"></jsp:include>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/checkout.css">
    
</head>
<body>
    <!-- Header -->
    <jsp:include page="/includes/header.jsp"></jsp:include>

    <!-- Main Content -->
    <div class="checkout-container">
        <div class="text-left mt-2">
            <button type="button" onclick="window.history.back()" class="back-button">
               <i class="fas fa-arrow-left"></i>
            </button>
        </div>
        <h1 class="text-center mb-4">Thanh Toán</h1>
        <!-- Thông báo lỗi -->
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${errorMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <form id="checkoutForm" action="${pageContext.request.contextPath}/checkout" method="post">
            <input type="hidden" name="productIds" value="${param.productIds}">
            <input type="hidden" name="action" value="checkout">
            <!-- Các trường ẩn để lưu thông tin giao hàng tạm thời -->
            <input type="hidden" name="tempFullName" id="tempFullName">
            <input type="hidden" name="tempPhoneNumber" id="tempPhoneNumber">
            <input type="hidden" name="tempAddress" id="tempAddress">

            <div class="row">
                <!-- Cột trái: Địa chỉ giao hàng & Phương thức thanh toán -->
                <div class="col-md-4">
                    <!-- Địa chỉ giao hàng -->
                    <div class="section-frame">
                        <h2 class="section-title"><i class="fas fa-shipping-fast"></i> Địa Chỉ Giao Hàng</h2>
                        <c:choose>
                            <c:when test="${not empty sessionScope.user and not empty sessionScope.user.address}">
                                <div class="shipping-info-item">
                                    <p><strong>Họ và Tên:</strong> <span id="displayFullName">${sessionScope.user.fullName}</span></p>
                                </div>
                                <div class="shipping-info-item">
                                    <p><strong>Số Điện Thoại:</strong> <span id="displayPhoneNumber">${sessionScope.user.phoneNumber}</span></p>
                                </div>
                                <div class="shipping-info-item">
                                    <p><strong>Địa Chỉ:</strong> <span id="displayAddress">${sessionScope.user.address}</span></p>
                                </div>
                                <a href="#" class="text-primary" data-bs-toggle="modal" data-bs-target="#editAddressModal">Thay Đổi</a>
                                <!-- Modal chỉnh sửa địa chỉ -->
                                <div class="modal fade" id="editAddressModal" tabindex="-1" aria-labelledby="editAddressModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="editAddressModalLabel">Chỉnh Sửa Địa Chỉ</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <div class="mb-3">
                                                    <label for="editFullName" class="form-label">Họ và Tên</label>
                                                    <input type="text" class="form-control" id="editFullName" value="${sessionScope.user.fullName}" required>
                                                </div>
                                                <div class="mb-3">
                                                    <label for="editPhone" class="form-label">Số Điện Thoại</label>
                                                    <input type="text" class="form-control" id="editPhone" value="${sessionScope.user.phoneNumber}" required>
                                                </div>
                                                <div class="mb-3">
                                                    <label for="editAddress" class="form-label">Địa Chỉ</label>
                                                    <input type="text" class="form-control" id="editAddress" value="${sessionScope.user.address}" required>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                                                <button type="button" class="btn btn-primary" onclick="updateTempAddress()">Lưu Thay Đổi</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="mb-3">
                                    <label for="fullName" class="form-label">Họ và Tên</label>
                                    <input type="text" class="form-control" id="fullName" name="tempFullName" required>
                                </div>
                                <div class="mb-3">
                                    <label for="phoneNumber" class="form-label">Số Điện Thoại</label>
                                    <input type="text" class="form-control" id="phoneNumber" name="tempPhoneNumber" required>
                                </div>
                                <div class="mb-3">
                                    <label for="address" class="form-label">Địa Chỉ Giao Hàng</label>
                                    <input type="text" class="form-control" id="address" name="tempAddress" required>
                                </div>
                            </c:otherwise>
                        </c:choose>
                        <!-- Thêm ô chọn thời gian giao hàng -->
                        <div class="mb-3">
                            <label for="deliveryTime" class="form-label">Thời Gian Giao Hàng</label>
                            <select class="form-select" id="deliveryTime" name="deliveryTime">
                                <option value="morning">Buổi Sáng (8:00 - 12:00)</option>
                                <option value="afternoon">Buổi Chiều (13:00 - 17:00)</option>
                                <option value="evening">Buổi Tối (18:00 - 21:00)</option>
                            </select>
                        </div>
                    </div>

                    <!-- Phương thức thanh toán -->
                    <div class="section-frame">
                        <h2 class="section-title"><i class="fas fa-credit-card"></i> Phương Thức Thanh Toán</h2>
                        <c:if test="${empty paymentMethods}">
                            <div class="alert alert-warning">Không có phương thức thanh toán nào khả dụng.</div>
                        </c:if>
                        <c:if test="${not empty paymentMethods}">
                            <div class="list-group">
                                <c:forEach var="method" items="${paymentMethods}" varStatus="loop">
                                    <div class="list-group-item">
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" 
                                                   name="paymentMethod" 
                                                   id="paymentMethod${method.paymentMethodID}" 
                                                   value="${method.paymentMethodID}" 
                                                   data-method-name="${method.methodName}" 
                                                   ${loop.first ? 'checked' : ''} required>
                                            <label class="form-check-label" for="paymentMethod${method.paymentMethodID}">
                                                ${method.methodName}
                                            </label>
                                            <c:if test="${not empty method.description}">
                                                <p class="d-block text-muted">${method.description}</p>
                                            </c:if>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                            <input type="hidden" name="paymentMethodCode" id="selectedPaymentMethod">
                        </c:if>
                    </div>
                </div> 

                <!-- Cột phải: Tóm tắt đơn hàng -->
                <div class="col-md-8">
                    <div class="order-summary">
                        <h2 class="section-title"><i class="fas fa-shopping-cart"></i> Tóm Tắt Đơn Hàng</h2>
                        <c:if test="${empty cartItems}">
                            <div class="empty-cart-message">
                                Giỏ hàng của bạn trống. 
                                <a href="${pageContext.request.contextPath}/products" class="continue-shopping-btn">Tiếp tục mua sắm</a>
                            </div>
                        </c:if>
                        <c:if test="${not empty cartItems}">
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
                                    <c:forEach var="cartItem" items="${cartItems}">
                                        <c:set var="product" value="${cartItem.productID}" />
                                        <tr>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty cartItemImages[product.productID] and not empty cartItemImages[product.productID][0]}">
                                                        <img src="${pageContext.request.getContextPath()}/assets/images/productImages/${cartItemImages[product.productID][0].imageURL}" 
                                                             alt="${product.productName}" 
                                                             width="50" height="50" class="me-2">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="${pageContext.request.getContextPath()}/assets/images/productImages/default-product.jpg" 
                                                             alt="Default Image" 
                                                             width="50" height="50" class="me-2">
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${product.productName}</td>
                                            <td>${cartItem.quantity}</td>
                                            <td><fmt:formatNumber value="${cartItem.price}" type="currency" currencySymbol="₫" /></td>
                                            <td><fmt:formatNumber value="${cartItem.price.multiply(cartItem.quantity)}" type="currency" currencySymbol="₫" /></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <hr>
                            <div class="d-flex justify-content-between mb-2">
                                <span>Tổng Tiền Hàng:</span>
                                <span><fmt:formatNumber value="${subTotal != null ? subTotal : 0}" type="currency" currencySymbol="₫" /></span>
                            </div>
                            <div class="d-flex justify-content-between mb-2">
                                <span>Giảm Giá:</span>
                                <span><fmt:formatNumber value="${discountAmount != null ? discountAmount : 0}" type="currency" currencySymbol="₫" /></span>
                            </div>
                            <div class="d-flex justify-content-between">
                                <strong>Tổng Thanh Toán:</strong>
                                <strong><fmt:formatNumber value="${total != null ? total : 0}" type="currency" currencySymbol="₫" /></strong>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>

            <!-- Nút đặt hàng -->
            <c:if test="${not empty cartItems}">
                <div class="text-end mt-4">
                    <button type="submit" class="btn btn-primary btn-place-order">Đặt Hàng</button>
                </div>
            </c:if>
        </form>
    </div>

    <!-- Footer -->
    <jsp:include page="/includes/footer.jsp"></jsp:include>

    <!-- Scripts -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/checkout.js"></script>
</body>
</html>