<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="/includes/head.jsp"></jsp:include>    
</head>
<body>
    <jsp:include page="/includes/header.jsp"></jsp:include>
    <c:set var="userRole" value="${sessionScope.user != null && sessionScope.user.roleID != null ? sessionScope.user.roleID.roleID : 0}" />
    <div class="seller_container">
        <div class="container">
            <div class="row seller_page">
                <div class="seller_info_container col-sm-12">
                    <div class="seller_info col-6 mx-auto">
                        <div class="row">
                            <div class="seller_info_left col-sm-4">
                                <img src="${pageContext.request.contextPath}/assets/images/store_logo.png" alt="Store Logo">
                            </div>
                            <div class="seller_info_right col-sm-8">
                                <div class="row">
                                    <div class="top col-12">
                                        <div class="seller_name text-primary">
                                            <h3>${sellerName}</h3>
                                        </div>
                                        <div class="seller_address text-muted">
                                            <p><strong>Địa chỉ:</strong> ${sellerAddress}</p>
                                            <p><strong>Số điện thoại:</strong> ${sellerPhone}</p>
                                        </div>
                                    </div>
                                    <div class="bottom col-12">
                                        <div class="sold"><p>Sản phẩm đã bán: ${soldProducts}</p></div>
                                        <div class="amount"><p>Số lượng sản phẩm: ${productCount}</p></div>
                                    </div>
                                </div>
                            </div>   
                        </div>
                    </div>
                </div>

                <hr>

                <div class="nav_bar">
                    <div class="container">
                        <div class="tabs">
                            <div class="tab-item active">Gian hàng</div>
                            <div class="tab-item">Bán chạy nhất</div>
                            <div class="tab-item">Khuyến mãi</div>
                            <div class="line"></div>
                        </div>

                        <div class="tab-content">
                            <!-- Tab Gian hàng -->
                            <div class="tab-pane active">
                                <div class="newest_products">
                                    <div class="title"><h3>Sản phẩm mới</h3></div>
                                    <div class="list_products">
                                        <div class="swiper mySwiper">
                                            <div class="swiper-wrapper">
                                                <c:forEach var="product" items="${newProducts}">
                                                    <div class="swiper-slide" style="width: 350px; height: auto;">
                                                        <div class="product newest_product horizontal">
                                                            <div class="product_image_container">
                                                                <a href="${pageContext.request.contextPath}/detail?productID=${product.productID}">
                                                                    <div class="product_image">
                                                                        <c:choose>
                                                                            <c:when test="${not empty product.productImageCollection}">
                                                                                <img src="${pageContext.request.contextPath}/assets/images/productImages/${product.productImageCollection[0].imageURL}" alt="${product.productName}">
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <img src="${pageContext.request.contextPath}/assets/images/default-image.jpg" alt="No image">
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </div>
                                                                </a>
                                                            </div>
                                                            <div class="product_info">
                                                                <a href="${pageContext.request.contextPath}/detail?productID=${product.productID}">
                                                                    <div class="product_name text"><p>${product.productName}</p></div>
                                                                </a>
                                                                
                                                                <!-- Phần giá sản phẩm -->
                                                                <div class="product_price">
                                                                    <c:if test="${not empty product.discountCollection}">
                                                                        <div class="discount">
                                                                            <div class="discount_price text-deleted">
                                                                                <del><fmt:formatNumber value="${product.price}" type="number" maxFractionDigits="0"/>đ</del>
                                                                            </div>
                                                                            <div class="discount_tag">
                                                                                <small>GIẢM ${product.discountCollection[0].discountPercent}%</small>
                                                                            </div>
                                                                        </div>
                                                                        <div class="price text-primary">
                                                                            <fmt:formatNumber value="${product.price * (1 - product.discountCollection[0].discountPercent / 100)}" type="number" maxFractionDigits="0"/>đ
                                                                        </div>
                                                                    </c:if>
                                                                    <c:if test="${empty product.discountCollection}">
                                                                        <div class="price text-primary">
                                                                            <fmt:formatNumber value="${product.price}" type="number" maxFractionDigits="0"/>đ
                                                                        </div>
                                                                    </c:if>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                            <div class="swiper-button-next"></div>
                                            <div class="swiper-button-prev"></div>
                                        </div>
                                    </div>
                                </div>

                                <div class="all_products">
                                    <div class="title"><h3>Tất cả sản phẩm</h3></div>
                                    <div class="list_products">
                                        <c:forEach var="product" items="${allProducts}">
                                            <div class="product">
                                                <a href="${pageContext.request.contextPath}/detail?productID=${product.productID}">
                                                    <div class="product_image">
                                                        <c:choose>
                                                            <c:when test="${not empty product.productImageCollection}">
                                                                <img src="${pageContext.request.contextPath}/assets/images/productImages/${product.productImageCollection[0].imageURL}" alt="${product.productName}">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <img src="${pageContext.request.contextPath}/assets/images/default-image.jpg" alt="No image">
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                </a>
                                                <div class="product_info">
                                                    <a href="${pageContext.request.contextPath}/detail?productID=${product.productID}">
                                                        <div class="product_name text"><p>${product.productName}</p></div>
                                                    </a>
                                                    <!-- Đánh giá sản phẩm (đặt trên phần giá) -->
                                                    <div class="product_rate text-warning">
                                                        <c:set var="rating" value="${product.getAverageRating()}" />
                                                        <c:choose>
                                                            <c:when test="${rating == -1}">
                                                                <span class="no-rating">Chưa có đánh giá</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:forEach begin="1" end="5" var="i">
                                                                    <c:choose>
                                                                        <c:when test="${i <= rating}">
                                                                            <span class="star filled">★</span>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <span class="star">☆</span>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:forEach>
                                                                <span class="rating-score">(${String.format("%.1f", rating)} - ${product.getTotalReview()} đánh giá)</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                    <!-- Phần giá sản phẩm -->
                                                    <div class="product_price">
                                                        <c:if test="${not empty product.discountCollection}">
                                                            <div class="discount">
                                                                <div class="discount_price text-deleted">
                                                                    <del><fmt:formatNumber value="${product.price}" type="number" maxFractionDigits="0"/>đ</del>
                                                                </div>
                                                                <div class="discount_tag">
                                                                    <small>GIẢM ${product.discountCollection[0].discountPercent}%</small>
                                                                </div>
                                                            </div>
                                                            <div class="price text-primary">
                                                                <fmt:formatNumber value="${product.price * (1 - product.discountCollection[0].discountPercent / 100)}" type="number" maxFractionDigits="0"/>đ
                                                            </div>
                                                        </c:if>
                                                        <c:if test="${empty product.discountCollection}">
                                                            <div class="price text-primary">
                                                                <fmt:formatNumber value="${product.price}" type="number" maxFractionDigits="0"/>đ
                                                            </div>
                                                        </c:if>
                                                    </div>
                                                </div>
                                                <c:if test="${userRole != 0}">
                                                    <div class="product_actions">
                                                        <button class="btn btn-primary add-to-cart-btn" data-product-id="${product.productID}" type="button">
                                                            <span class="material-icons-sharp">add_shopping_cart</span>
                                                        </button>
                                                    </div>
                                                </c:if>

                                                <div class="buy">
                                                    <button class="btn btn-primary buy-now-btn" data-product-id="${product.productID}" type="button">
                                                        <p>Mua ngay</p>
                                                    </button>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>

                            <!-- Tab Bán chạy nhất -->
                            <div class="tab-pane">
                                <div class="title"><h3>Sản phẩm bán chạy</h3></div>
                                <div class="list_products">
                                    <c:forEach var="product" items="${bestSellers}">
                                        <div class="product">
                                            <a href="${pageContext.request.contextPath}/detail?productID=${product.productID}">
                                                <div class="product_image">
                                                    <c:choose>
                                                        <c:when test="${not empty product.productImageCollection}">
                                                            <img src="${pageContext.request.contextPath}/assets/images/productImages/${product.productImageCollection[0].imageURL}" alt="${product.productName}">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="${pageContext.request.contextPath}/assets/images/default-image.jpg" alt="No image">
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </a>
                                            <div class="product_info">
                                                <a href="${pageContext.request.contextPath}/detail?productID=${product.productID}">
                                                    <div class="product_name text"><p>${product.productName}</p></div>
                                                </a>
                                                <!-- Đánh giá sản phẩm (đặt trên phần giá) -->
                                                <div class="product_rate text-warning">
                                                    <c:set var="rating" value="${product.getAverageRating()}" />
                                                    <c:choose>
                                                        <c:when test="${rating == -1}">
                                                            <span class="no-rating">Chưa có đánh giá</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:forEach begin="1" end="5" var="i">
                                                                <c:choose>
                                                                    <c:when test="${i <= rating}">
                                                                        <span class="star filled">★</span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="star">☆</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:forEach>
                                                            <span class="rating-score">(${String.format("%.1f", rating)} - ${product.getTotalReview()} đánh giá)</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <!-- Phần giá sản phẩm -->
                                                <div class="product_price">
                                                    <c:if test="${not empty product.discountCollection}">
                                                        <div class="discount">
                                                            <div class="discount_price text-deleted">
                                                                <del><fmt:formatNumber value="${product.price}" type="number" maxFractionDigits="0"/>đ</del>
                                                            </div>
                                                            <div class="discount_tag">
                                                                <small>GIẢM ${product.discountCollection[0].discountPercent}%</small>
                                                            </div>
                                                        </div>
                                                        <div class="price text-primary">
                                                            <fmt:formatNumber value="${product.price * (1 - product.discountCollection[0].discountPercent / 100)}" type="number" maxFractionDigits="0"/>đ
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${empty product.discountCollection}">
                                                        <div class="price text-primary">
                                                            <fmt:formatNumber value="${product.price}" type="number" maxFractionDigits="0"/>đ
                                                        </div>
                                                    </c:if>
                                                </div>
                                            </div>
                                            <c:if test="${userRole != 0}">
                                                <div class="product_actions">
                                                    <button class="btn btn-primary add-to-cart-btn" data-product-id="${product.productID}" type="button">
                                                        <span class="material-icons-sharp">add_shopping_cart</span>
                                                    </button>
                                                </div>
                                            </c:if>
                                                    
                                                <div class="buy">
                                                    <button class="btn btn-primary buy-now-btn" data-product-id="${product.productID}" type="button">
                                                        <p>Mua ngay</p>
                                                    </button>
                                                </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>

                            <!-- Tab Khuyến mãi -->
                            <div class="tab-pane">
                                <div class="title"><h3>Sản phẩm khuyến mãi</h3></div>
                                <div class="list_products">
                                    <c:choose>
                                        <c:when test="${empty promotions}">
                                          <p style="color: #28a745; font-weight: 500;">Hiện tại không có sản phẩm khuyến mãi nào.</p>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="product" items="${promotions}">
                                                <div class="product">
                                                    <a href="${pageContext.request.contextPath}/detail?productID=${product.productID}">
                                                        <div class="product_image">
                                                            <c:choose>
                                                                <c:when test="${not empty product.productImageCollection}">
                                                                    <img src="${pageContext.request.contextPath}/assets/images/productImages/${product.productImageCollection[0].imageURL}" alt="${product.productName}">
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <img src="${pageContext.request.contextPath}/assets/images/default-image.jpg" alt="No image">
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                    </a>
                                                    <div class="product_info">
                                                        <a href="${pageContext.request.contextPath}/detail?productID=${product.productID}">
                                                            <div class="product_name text"><p>${product.productName}</p></div>
                                                        </a>
                                                        <!-- Đánh giá sản phẩm (đặt trên phần giá) -->
                                                        <div class="product_rate text-warning">
                                                            <c:set var="rating" value="${product.getAverageRating()}" />
                                                            <c:choose>
                                                                <c:when test="${rating == -1}">
                                                                    <span class="no-rating">Chưa có đánh giá</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:forEach begin="1" end="5" var="i">
                                                                        <c:choose>
                                                                            <c:when test="${i <= rating}">
                                                                                <span class="star filled">★</span>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <span class="star">☆</span>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </c:forEach>
                                                                    <span class="rating-score">(${String.format("%.1f", rating)} - ${product.getTotalReview()} đánh giá)</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                        <!-- Phần giá sản phẩm -->
                                                        <div class="product_price">
                                                            <c:if test="${not empty product.discountCollection}">
                                                                <div class="discount">
                                                                    <div class="discount_price text-deleted">
                                                                        <del><fmt:formatNumber value="${product.price}" type="number" maxFractionDigits="0"/>đ</del>
                                                                    </div>
                                                                    <div class="discount_tag">
                                                                        <small>GIẢM ${product.discountCollection[0].discountPercent}%</small>
                                                                    </div>
                                                                </div>
                                                                <div class="price text-primary">
                                                                    <fmt:formatNumber value="${product.price * (1 - product.discountCollection[0].discountPercent / 100)}" type="number" maxFractionDigits="0"/>đ
                                                                </div>
                                                            </c:if>
                                                            <c:if test="${empty product.discountCollection}">
                                                                <div class="price text-primary">
                                                                    <fmt:formatNumber value="${product.price}" type="number" maxFractionDigits="0"/>đ
                                                                </div>
                                                            </c:if>
                                                        </div>
                                                    </div>
                                                    <c:if test="${userRole != 0}">
                                                        <div class="product_actions">
                                                            <button class="btn btn-primary add-to-cart-btn" data-product-id="${product.productID}" type="button">
                                                                <span class="material-icons-sharp">add_shopping_cart</span>
                                                            </button>
                                                        </div>
                                                    </c:if>

                                                    <div class="buy">
                                                        <button class="btn btn-primary buy-now-btn" data-product-id="${product.productID}" type="button">
                                                            <p>Mua ngay</p>
                                                        </button>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div> 
                </div>
            </div>
        </div>
    </div>

   <jsp:include page="/includes/footer.jsp"/>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/swiper.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
    <script>
        var contextPath = "${pageContext.request.contextPath}";
    </script>
    <script type="module" src="${pageContext.request.contextPath}/assets/js/product_detail.js"></script>
</body>
</html>