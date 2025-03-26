<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Giỏ Hàng</title>
        
         <jsp:include page="/includes/head.jsp"></jsp:include>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/cart.css">
    </head>
<body>
    
    <jsp:include page="/includes/header.jsp"></jsp:include>
    <!-- Nội dung chính -->
    <div class="container my-4">
        <!--Thông báo khi stock sản phẩm ko đủ-->
        <c:if test="${not empty outOfStockMessages}">
            <div id="outOfStockMessages" class="out-of-stock-message">
                <c:forEach var="message" items="${outOfStockMessages}">
                    <p>${message}</p>
                </c:forEach>
            </div>
        </c:if>
        
        <!-- Thông báo -->
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${errorMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                ${successMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

        <!-- Danh sách sản phẩm và tóm tắt -->
        <div class="row">
            <div class="col-lg-9 col-md-12">
                <div class="card">
                    <div class="cart-list">
                        <div class="cart-header">
                            <div class="form-check">
                                <input type="checkbox" class="form-check-input" id="select-all" onchange="toggleSelectAll(this)" checked>
                                <label class="form-check-label" for="select-all"></label>
                            </div>
                            <div>Sản Phẩm</div>
                            <div>Đơn Giá</div>
                            <div>Số Lượng</div>
                            <div>Số Tiền</div>
                            <div>Thao Tác</div>
                        </div>
                        <c:choose>
                            <c:when test="${groupedCartItems == null || groupedCartItems.size() == 0}">
                                <p class="text-center py-5">Giỏ hàng của bạn trống. <a href="${pageContext.request.contextPath}/products" class="text-decoration-none text-primary">Tiếp tục mua sắm</a>.</p>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="entry" items="${groupedCartItems}">
                                    <div class="shop-header">
                                        <div class="form-check">
                                            <input type="checkbox" class="shop-checkbox" data-seller-id="${entry.key}" checked>
                                            <label>${sellerNames[entry.key]}</label>
                                        </div>
                                    </div>
                                    <c:forEach var="cartItem" items="${entry.value}">
                                        <c:set var="product" value="${cartItem.productID}" />
                                        <div class="cart-item">
                                            <div class="form-check">
                                                <input type="checkbox" class="form-check-input item-checkbox" 
                                                       id="checkbox-${product.productID}" 
                                                       data-seller-id="${entry.key}"
                                                       data-product-id="${product.productID}" 
                                                       data-unit-price="${discountedPrices[product.productID] != null ? discountedPrices[product.productID].toString() : cartItem.price.toString()}"
                                                       data-price="${itemTotals[product.productID].toString()}" 
                                                       value="${product.productID}" 
                                                       onchange="updateTotal()" 
                                                        <c:if test="${outOfStockStatus[product.productID]}">disabled="disabled"</c:if>
                                                       checked>
                                            </div>
                                            <div class="cart-item-details">
                                                <c:set var="productImages" value="${cartItemImages[product.productID]}" />
                                                <c:set var="imageUrl" value="${not empty productImages and not empty productImages[0] ? productImages[0].imageURL : pageContext.request.contextPath + '/assets/images/detail1.png'}" />
                                                <img src="${pageContext.request.getContextPath()}/assets/images/productImages/${imageUrl}" alt="${product.productName != null ? product.productName : 'Không có ảnh'}">
                                                <div class="product-info">
                                                    <h6>
                                                        ${product.productName != null ? product.productName : 'Không có tên'}
                                                        <c:if test="${outOfStockStatus[product.productID]}">
                                                            <span class="out-of-stock-label">(HẾT HÀNG)</span>
                                                        </c:if>
                                                    </h6>
                                                    <p>Phân loại: ${product.categoryID != null ? product.categoryID.categoryName : 'N/A'}</p>
                                                </div>
                                            </div>
                                            <div class="price-section">
                                                <span class="discounted-price" data-bs-toggle="tooltip" data-bs-placement="top" 
                                                      title="Giá gốc: <fmt:formatNumber value='${cartItem.price}' type='number' groupingUsed='true' /> đ">
                                                    <fmt:formatNumber value="${discountedPrices[product.productID] != null ? discountedPrices[product.productID] : cartItem.price}" type="number" groupingUsed="true" /> đ
                                                </span>
                                            </div>
                                            <div class="quantity-control">
                                                <button type="button" class="btn-decrease" data-product-id="${product.productID}">-</button>
                                                <input type="number" name="quantity" value="${cartItem.quantity}" class="quantity-input" data-product-id="${product.productID}" min="1" max="${product.quantity}">
                                                <button type="button" class="btn-increase" data-product-id="${product.productID}">+</button>
                                            </div>
                                            <div class="total-price" id="item-total-${product.productID}">
                                                <fmt:formatNumber value="${itemTotals[product.productID]}" type="number" groupingUsed="true" /> đ
                                            </div>
                                            <div class="btn-remove">
                                                <a href="${pageContext.request.contextPath}/cart?action=remove&productID=${product.productID}" class="btn-remove">Xóa</a>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="cart-footer">
                        <div class="form-check">
                            <input type="checkbox" class="form-check-input" id="select-all-bottom" onchange="toggleSelectAll(this)">
                            <label class="form-check-label" for="select-all-bottom">Chọn tất cả (<span class="selected-count">0</span>)</label>
                        </div>
                        <button type="button" class="btn btn-danger" onclick="removeAllSelected()">Xóa tất cả</button>
                    </div>
                </div>
            </div>

            <!-- Tóm tắt đơn hàng -->
            <div class="col-lg-3 col-md-12">
                <div class="summary-card">
                    <div class="card-body">
                        <div class="coupon-group mb-3">
                            <form action="${pageContext.request.contextPath}/cart?action=applyDiscount" method="post">
                                <div class="input-group">
                                    <input type="text" class="form-control" name="discountCode" placeholder="Nhập mã giảm giá">
                                    <button type="submit" class="btn btn-outline-secondary">Áp dụng</button>
                                </div>
                                <c:if test="${not empty discountMessage}">
                                    <small class="${discountMessage.contains('success') ? 'text-success' : 'text-danger'} mt-2 d-block">${discountMessage}</small>
                                </c:if>
                            </form>
                        </div>
                        <hr>
                        <div class="summary-item">
                            <span>Tổng tiền hàng:</span>
                            <span id="selectedSubTotal"><fmt:formatNumber value="${subTotal}" type="number" groupingUsed="true" /> đ</span>
                        </div>
                        <div class="summary-item">
                            <span>Tổng số lượng:</span>
                            <span class="total-quantity">${cartItems.size()}</span>
                        </div>
                        <div class="summary-item discount">
                            <span>Giảm giá:</span>
                            <span id="selectedDiscount"><fmt:formatNumber value="${discountAmount}" type="number" groupingUsed="true" /> đ</span>
                        </div>
                        <hr>
                        <div class="summary-item total">
                            <strong>Tổng thanh toán: </strong>
                            <strong class="text-danger" id="selectedTotal"><fmt:formatNumber value="${total}" type="number" groupingUsed="true" /> đ</strong>
                        </div>
                        <button type="submit" class="btn btn-purchase" form="checkoutForm">Mua Hàng</button>
                        <form id="checkoutForm" action="${pageContext.request.contextPath}/cart" method="post" style="display: none;">
                            <input type="hidden" name="action" value="checkout">
                            <input type="hidden" name="productIds" id="selectedProductIds">
                        </form>
                        <a href="${pageContext.request.contextPath}/products" class="btn btn-back">Tiếp tục mua sắm</a>
                    </div>
                </div>
            </div>
        </div>
                    <div style="margin-top: 16px">
                        <a href="javascript:void(0);" onclick="history.back();" class="btn btn-primary"> Trở về trang trước</a>
                    </div>
    </div>
    
                   

    <!--FOOTER-->
    <jsp:include page="/includes/footer.jsp"></jsp:include>

    <!-- Scripts -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        const contextPath = '${pageContext.request.contextPath}';
    </script>
    <script src="${pageContext.request.contextPath}/assets/js/cart.js"></script>
</body>
</html>