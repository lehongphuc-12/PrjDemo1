<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/product_detail.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/store_page.css">
    <!--<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/cart.css">-->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <!--head-->
    <jsp:include page="/includes/head.jsp"></jsp:include>
</head>


<body>
    <!--HEADER-->
    <jsp:include page="/includes/header.jsp"></jsp:include>
    <c:set var="userRole" value="${sessionScope.user != null && sessionScope.user.roleID != null ? sessionScope.user.roleID.roleID : 0}" />

    <!-- ==================== CHI TIẾT SẢN PHẨM ================== -->
    <div class="details_container">
        <div class="container">
            <div class="row product_detail">
                <!-- BÊN TRÁI CHI TIẾT -->
                <div class="product_detail_left col-sm-6 col-12">
                    <div class="swiper mySwiper2 product_detail_slide">
                        <div class="swiper-wrapper">
                            <c:forEach var="image" items="${product.productImageCollection}">
                                <div class="swiper-slide">
                                    <img src="${pageContext.request.getContextPath()}/assets/images/productImages/${image.imageURL}" alt="Hình ảnh sản phẩm">
                                </div>
                            </c:forEach>
                            <c:if test="${empty product.productImageCollection}">
                                <div class="swiper-slide">
                                    <img src="${pageContext.request.getContextPath()}/assets/images/productImages/default_product_image.jpg" alt="Không có hình ảnh">
                                </div>
                            </c:if>
                        </div>
                    </div>
                    <div thumbsSlider="" class="swiper mySwiper product_detail_slide">
                        <div class="swiper-wrapper">
                            <c:forEach var="image" items="${product.productImageCollection}">
                                <div class="swiper-slide">
                                    <img src="${pageContext.request.getContextPath()}/assets/images/productImages/${image.imageURL}" alt="Hình ảnh sản phẩm">
                                </div>
                            </c:forEach>
                            <c:if test="${empty product.productImageCollection}">
                                <div class="swiper-slide">
                                    <img src="${pageContext.request.getContextPath()}/assets/images/productImages/defaultproduct_image.jpg" alt="Không có hình ảnh">
                                </div>
                            </c:if>
                        </div>
                        <div class="swiper-button-next"></div>
                        <div class="swiper-button-prev"></div>
                    </div>
                </div>
                <!-- KẾT THÚC BÊN TRÁI CHI TIẾT -->

                <!-- BÊN PHẢI CHI TIẾT -->
                <div class="product_detail_right col-sm-6 col-12">
                    <form class="product_detail_info">
                        <div class="product_detail_name">
                            <p class="text-name"><span class="tag">Yêu thích</span>  ${product.productName}</p>
                        </div>
                        <div class="product_detail_review">
                            
                            <c:set var="rating" value="${product.averageRating}" />
                            <div class="product_rate text-warning">
                                <c:choose>
                                    <c:when test="${rating == -1}">
                                        <p class="no-rating">Chưa có đánh giá</p>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach begin="1" end="5" var="i">
                                            <c:choose>
                                                <c:when test="${i <= rating}">★</c:when>  <%-- Ngôi sao đầy --%>
                                                <c:otherwise>☆</c:otherwise>  <%-- Ngôi sao rỗng --%>
                                            </c:choose>
                                        </c:forEach>
                                        <p class="total_rating">(${String.format("%.1f", rating)})</p>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="sold">
                                <p class="text-deleted">Đã bán: ${product.getTotalSold()}</p>
                            </div>
                        </div>

                        <hr>

           
                        
                        <!-- Hiển thị giá sản phẩm và giảm giá nếu có -->
                        <div class="product_price">
                            <c:choose>
                                <c:when test="${not empty product.discountCollection}">
                                    <!--//set giá trị discount-->
                                    <c:set var="discount" value="${product.discountCollection.iterator().next()}" />
                                    <div class="price text-primary">
                                        <fmt:formatNumber value="${product.price * (1 - discount.discountPercent / 100)}"
                                                          type="number" groupingUsed="true" />đ
                                    </div>
                                    <div class="discount">
                                        <div class="discount_price text-deleted">
                                            <del>
                                                <fmt:formatNumber value="${product.price}" type="number" groupingUsed="true" />đ
                                            </del>
                                        </div>
                                        <div class="discount_tag">
                                            <small>GIẢM <fmt:formatNumber value="${discount.discountPercent}" type="number" maxFractionDigits="0" />%</small>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="price text-primary">
                                        <fmt:formatNumber value="${product.price}" type="number" groupingUsed="true" />đ
                                    </div>
                                    <div class="sold"></div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        
                        
                        <hr>
                        <p class="discount_tags">Mã giảm giá của Shop: Giảm giá 11%</p>
                        <table class="detail_body">
                            <tr class="product_location">
                                <td>Gửi từ: </td>
                                <td class="text">
                                    <c:choose>
                                        <c:when test="${not empty product.cityID}">
                                            ${product.cityID.cityName}
                                        </c:when>
                                        <c:otherwise>
                                            Không có thông tin
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <tr class="seller">
                                <td>Được bán bởi: </td>
                                <td class="text">
                                    <a href="#">
                                        <c:choose>
                                            <c:when test="${not empty product.sellerID}">
                                                ${product.sellerID.fullName}
                                            </c:when>
                                            <c:otherwise>
                                                Không có thông tin
                                            </c:otherwise>
                                        </c:choose>
                                    </a>
                                </td>
                            </tr>
                        </table>
                        
                        <!-- PHẦN VẬN CHUYỂN VÀ LIÊN HỆ -->
                        <div class="shipping_contact">
                            <div class="shipping_info">
                                <p class="shipping_trigger" tabindex="0">
                                    <i class="fas fa-truck"></i> <strong>Miễn phí vận chuyển</strong><br>
                                    Nhận hàng từ ${requestScope.currentShippingDate} đến ${requestScope.futureShippingDate}, phí giao hàng 0₫
                                    <span class="dropdown_arrow">▼</span>
                                </p>
                                <div class="shipping_dropdown">
                                    <div class="shipping_options">
                                        <h4>Thông tin vận chuyển</h4>
                                        <p>Giao đến: Huyện Ba Vì <span>▼</span></p>
                                        <div class="shipping_option">
                                            <div class="shipping_detail">
                                                <h5>Nhanh</h5>
                                                <p>
                                                    🕒 Nhận hàng từ ${requestScope.currentShippingDate} đến ${requestScope.futureShippingDate}<br>
                                                    Tặng Voucher 15.000₫ nếu giao hàng sớm hơn thời gian trên.<br>
                                                    Miễn phí vận chuyển nếu đáp ứng điều kiện.
                                                </p>
                                                <p class="shipping_fee"><del>37.300₫</del> <span>Miễn phí vận chuyển</span></p>
                                            </div>
                                        </div>
                                        <div class="shipping_option">
                                            <div class="shipping_detail">
                                                <h5>Hàng Cồng Kềnh</h5>
                                                <p>
                                                    🕒 Nhận hàng từ ${requestScope.startBulkyShippingDate} đến ${requestScope.endBulkyShippingDate}<br>
                                                    Tặng Voucher 15.000₫ nếu giao hàng sớm hơn thời gian trên.<br>
                                                    Miễn phí vận chuyển nếu đáp ứng điều kiện.
                                                </p>
                                                <p class="shipping_fee"><del>47.300₫</del> <span>Miễn phí vận chuyển</span></p>
                                            </div>
                                        </div>
                                        <div class="shipping_option">
                                            <div class="shipping_row">
                                                <div class="shipping_detail">Hỏa Tốc</div>
                                                <div class="shipping_status">Không hỗ trợ</div>
                                            </div>
                                        </div>
                                        <div class="shipping_option">
                                            <div class="shipping_row">
                                                <div class="shipping_detail">Tiết kiệm</div>
                                                <div class="shipping_status">Không hỗ trợ</div>
                                            </div>
                                        </div>
                                        <button class="btn btn-primary understand_btn">Đã hiểu</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="return_policy">
                            <div class="return_policy_trigger" tabindex="0">
                                <span class="badge">✔</span> Miễn phí đổi trả trong 15 ngày
                                <span class="dropdown_arrow">▼</span>
                            </div>
                            <div class="return_policy_dropdown">
                                <p class="return_policy_description">
                                    Yên tâm mua sắm<br>
                                    <span class="return_policy_icon">ⓘ</span> Chính sách đổi trả miễn phí trong 15 ngày: Được miễn phí đổi trả trong vòng 15 ngày nếu sản phẩm có lỗi bảo hành và có thể kiểm tra tại nhà. Sản phẩm áp dụng chính sách này phải được mua tại cửa hàng của chúng tôi.
                                </p>
                            </div>
                        </div>
                        
                        <div class="product_quantity">
                            <div class="quantity_input">
                                <label for="quantity">Số lượng: </label>
                                <div class="quantity-control">
                                    <button type="button" class="btn btn-outline-secondary" onclick="decreaseQuantity()">-</button>
                                    <input id="quantity" type="text" value="<fmt:formatNumber value='1' type='number' maxFractionDigits='0' />" min="1" max="${not empty product.quantity ? product.quantity : 1}" oninput="validateQuantity(this)">
                                    <button type="button" class="btn btn-outline-secondary" onclick="increaseQuantity()">+</button>
                                </div>
                            </div>
                            <div class="available">
                                <c:set var="quantityValue" value="${not empty product.quantity ? product.quantity.toString() : '0'}" />
                                <c:set var="quantityNumber" value="${not empty quantityValue ? quantityValue : 0}" />
                                <fmt:formatNumber value="${quantityNumber}" type="number" maxFractionDigits="0" /> sản phẩm có sẵn
                            </div>
                        </div>
                 
                        <div class="product_buying">
                            <div class="add_to_cart">
                                <button class="btn btn-primary add-to-cart-btn" data-product-id="${product.productID}" type="button">
                                    <span class="material-icons-sharp">add_shopping_cart</span>
                                    <p>Thêm vào giỏ hàng</p>
                                </button>
                            </div>
                            <div class="buy">
                                <button class="btn btn-primary buy-now-btn" data-product-id="${product.productID}" type="button">
                                    <p>Mua ngay</p>
                                </button>
                            </div>
                        </div>

                        <!-- Thông báo thành công/lỗi -->
                        <div id="cart-message" class="mt-2" style="display: none;color: red;font-weight: 500"></div>
                    </form>
                </div>
                <!-- KẾT THÚC BÊN PHẢI CHI TIẾT -->
            </div>
        </div>
    </div>
    <!-- KẾT THÚC CHI TIẾT SẢN PHẨM -->

    <div class="store_container">
        <div class="container">
            <div class="row store">
                <div class="box_store col-sm-5">
                    <div class="logo_store">
                        <a href="#"><img src="${pageContext.request.contextPath}/assets/images/store_logo.png" title="Logo cửa hàng" alt="Logo"></a>
                        <span class="tag">Yêu thích</span>
                    </div>
                    <div class="info_store">
                        <h4 class="text-primary">
                            <c:choose>
                                <c:when test="${not empty product.sellerID.fullName}">
                                    ${product.sellerID.fullName}
                                </c:when>
                                <c:otherwise>
                                    ${product.sellerID.fullName} <!-- Hoặc "Không có thông tin" -->
                                </c:otherwise>
                            </c:choose>
                        </h4>
                        <div class="btn_info_store">
                            <a href="#"><span class="material-icons-sharp">home</span> <p>Xem cửa hàng</p></a>
                        </div>
                    </div>
                </div>
                <div class="store_reviews col-sm-7">
                    <div class="reviews">
                        <div class="review">
                            <p class="review-label text-primary">Đánh giá:</p>
                            <div class="review-value">
                                <c:choose>
                                    <c:when test="${averageShopRating > 0}">
                                        <span class="rating-stars">
                                            <c:forEach begin="1" end="5" var="i">
                                                <c:choose>
                                                    <c:when test="${i <= averageShopRating}">
                                                        <span class="fa fa-star text-warning"></span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="fa fa-star-o text-warning"></span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                            <span class="rating-value">(${averageShopRating})</span>
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="rating-stars">
                                            <c:forEach begin="1" end="5" var="i">
                                                <span class="fa fa-star-o text-warning"></span>
                                            </c:forEach>
                                            <span class="rating-value">Chưa có đánh giá</span>
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="review">
                            <div class="review-value">
                                <span class="text-primary">Sản phẩm: ${sellerProducts.size()}</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- KẾT THÚC THÔNG TIN CỬA HÀNG -->
    
    <div class="detail_content_container">
        <div class="container">
            <div class="row">
                <div class="product_detail_content product_reviews_content col-12">
                    <h3 class="title">CHI TIẾT SẢN PHẨM</h3>
                    <div class="content">
                        ${product.description}
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- PHẦN ĐÁNH GIÁ SẢN PHẨM -->
    <div class="review_content_container">
        <div class="container">
            <div class="row">
                <div class="product_reviews_content col-12">
                    <h3 class="title">ĐÁNH GIÁ SẢN PHẨM</h3>
                    <p class="text-primary review-success"></p>
                    <!-- Form gửi đánh giá -->
                    <c:choose>
                        <c:when test="${hasReviewed != null and hasReviewed}">
                            <p class="text-primary has-reviewed">Bạn đã đánh giá sản phẩm này</p>
                            <form action="detail?action=review" method="post" class="review-form" style ="display:none">
                                <input type="hidden" name="productID" id="productID" value="${product.productID}">
                                <div class="form-group rating-group">
                                    <label for="rating">Đánh giá của bạn:</label>
                                    <div class="star-rating" id="star-rating">
                                        <span class="fa fa-star" data-value="1"></span>
                                        <span class="fa fa-star" data-value="2"></span>
                                        <span class="fa fa-star" data-value="3"></span>
                                        <span class="fa fa-star" data-value="4"></span>
                                        <span class="fa fa-star" data-value="5"></span>
                                    </div>
                                    <input type="hidden" id="rating" name="rating" value="0" required>
                                </div>
                                <div class="form-group comment-group">
                                    <label for="comment">Nhận xét của bạn:</label>
                                    <textarea id="comment" name="comment" rows="3" maxlength="255" placeholder="Nhập nhận xét của bạn (tối đa 255 ký tự)..." required></textarea>
                                </div>
                                <div class="form-group submit-group">
                                    <button type="button"  class="btn btn-submit-review">Gửi nhận xét</button>
                                </div>
                            </form>
                        </c:when> 
                        <c:otherwise>
                            <form action="detail?action=review" method="post" class="review-form">
                                <input type="hidden" name="productID" id="productID" value="${product.productID}">
                                <div class="form-group rating-group">
                                    <label for="rating">Đánh giá của bạn:</label>
                                    <div class="star-rating" id="star-rating">
                                        <span class="fa fa-star" data-value="1"></span>
                                        <span class="fa fa-star" data-value="2"></span>
                                        <span class="fa fa-star" data-value="3"></span>
                                        <span class="fa fa-star" data-value="4"></span>
                                        <span class="fa fa-star" data-value="5"></span>
                                    </div>
                                    <input type="hidden" id="rating" name="rating" value="0" required>
                                </div>
                                <div class="form-group comment-group">
                                    <label for="comment">Nhận xét của bạn:</label>
                                    <textarea id="comment" name="comment" rows="3" maxlength="255" placeholder="Nhập nhận xét của bạn (tối đa 255 ký tự)..." required></textarea>
                                </div>
                                <div class="form-group submit-group">
                                    <button type="button"  class="btn btn-submit-review">Gửi nhận xét</button>
                                </div>
                            </form>
                        </c:otherwise>
                    </c:choose>    
                    <!-- Hiển thị danh sách đánh giá -->
                    <div class="list-reviews">
                        <c:choose>
                            <c:when test="${not empty reviews}">
                                <c:forEach var="review" items="${reviews}">
                                    <div class="review">
                                        <div class="review-header">
                                            <span class="user-avatar-review">
                                                <img src="${pageContext.request.contextPath}/assets/images/userImages/user-avatar.jpg">
                                            </span> 
                                            <p>
                                                <c:choose>
                                                    <c:when test="${not empty review.userID}">${review.userID.fullName}</c:when>
                                                    <c:otherwise>Ẩn danh</c:otherwise>
                                                </c:choose>
                                            </p>
                                        </div>
                                        <p class="text-muted" style="font-size: 12px">${review.reviewDate}</p>
                                        <p>
                                            <span class="rating-stars">
                                                <c:forEach begin="1" end="5" var="i">
                                                    <c:choose>
                                                        <c:when test="${i <= review.rating}">
                                                            <span class="fa fa-star"></span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="fa fa-star-o"></span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </span>
                                        </p>
                                        <p><strong>Nhận xét:</strong> ${review.comment != null ? review.comment : "Không có nhận xét"}</p>
                                        
                                        <!--NEU CÓ REVIEW RỒI THÌ CÓ THỂ CHỈNH SỬA ĐÁNH GIÁ--> 
                                        <c:if test="${hasReviewed != null and hasReviewed and review.userID.userID == user.userID}">

                                            <div class = "review-action" style="font-size: 12px">
                                                <input type="hidden" name="productID" id="productID" value="${product.productID}">
                                                <input type="hidden" name="rating" id="rating" value="${review.rating}">
                                                <input type="hidden" name="comment" id="comment" value="${review.comment}">
                                               
                                                <a href="#" class = "text-muted update-review" data-reviewid="${review.reviewID}">Chỉnh sửa đánh giá</a>
                                                <a href="#" class = "text-muted delete-review" data-reviewid="${review.reviewID}">Xoá</a>
                                            </div>  
                                        </c:if>
                                         
                                           
                                            
                                    </div>

                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <p>Chưa có đánh giá nào cho sản phẩm này.</p>
                            </c:otherwise>
                        </c:choose>
       
                                
                    </div>
                    
                                 
                                
                    <!-------------------UPDATE REVIEW------------------->
                    <div class="update-review-container" style="display: none">
                        <form action="detail?action=review" method="post" class="review-form update-form">
                            <input type="hidden" name="productID" id="productID" value="${product.productID}">
                            <input type="hidden" name="reviewID" id="reviewID" value="${review.reviewID}">
                            <input type="hidden" id="rating" name="rating" value="0" >
                            
                            <div class="form-group rating-group">
                                <label for="rating">Đánh giá của bạn:</label>
                                <div class="star-rating">
                                    <c:forEach begin="1" end="5" var="i">
                                        <span class="fa fa-star" data-value="${i}"></span>
                                    </c:forEach>
                                </div>
                                
                            </div>

                            <div class="form-group comment-group">
                                <label for="comment">Nhận xét của bạn:</label>
                                <textarea id="comment" name="comment" rows="3"></textarea>
                            </div>

                            <div class="form-group submit-group">
                                <button type="button" class="btn btn-confirm-update-review">Cập nhật đánh giá</button>
                            </div>
                        </form>
                    </div>

                    <!-------------------END UPDATE REVIEW------------------->
                                
                                

                </div>
            </div>
        </div>
    </div>
                        
    <div class="product_of_store_container">
        <div class="container">
            <div class="row">
                <div class="product_of_store col-sm-12 col-12">
                    <h2 class="title">CÁC SẢN PHẨM KHÁC CỦA CỬA HÀNG</h2>
                    <div class="list_products_store">
                        <div class="swiper mySwiper store_product">
                            <div class="swiper-wrapper">
                                <c:forEach var="product" items="${sellerProducts}">
                                    <div class="swiper-slide">
                                        <div class="product best_seller_product">
                                            <div class="product_image">
                                                <c:choose>
                                                    <c:when test="${not empty product.productImageCollection}">
                                                        <c:forEach var="image" items="${product.productImageCollection}" begin="0" end="0">
                                                            <a href="${pageContext.request.contextPath}/detail?productID=${product.productID}"><img src="${pageContext.request.getContextPath()}/assets/images/productImages/${image.imageURL}" alt="" ></a>
                                                        </c:forEach>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="${pageContext.request.contextPath}/assets/images/default-image.jpg" alt="No image available" style="height: 200px">
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div class="product_info">
                                                <div class="product_name text"><p><a href="detail?productID=${product.productID}" class="text-dark">${product.productName}</a></p></div>
                                                <div class="product_price">
                                                    <c:choose>
                                                        <c:when test="${not empty product.discountCollection}">
                                                            <!--//set giá trị discount-->
                                                            <c:set var="discount" value="${product.discountCollection.iterator().next()}" />
                                                            <div class="price text-primary">
                                                                <fmt:formatNumber value="${product.price * (1 - discount.discountPercent / 100)}"
                                                                                  type="number" groupingUsed="true" />đ
                                                            </div>
                                                            <div class="discount">
                                                                <div class="discount_price text-deleted">
                                                                    <del>
                                                                        <fmt:formatNumber value="${product.price}" type="number" groupingUsed="true" />đ
                                                                    </del>
                                                                </div>
                                                                <div class="discount_tag">
                                                                    <small>GIẢM <fmt:formatNumber value="${discount.discountPercent}" type="number" maxFractionDigits="0" />%</small>
                                                                </div>
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="price text-primary">
                                                                <fmt:formatNumber value="${product.price}" type="number" groupingUsed="true" />đ
                                                            </div>
                                                            <div class="sold"></div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <c:set var="rating" value="${product.averageRating}" />

                                                <div class="product_rate text-warning">
                                                    <c:choose>
                                                        <c:when test="${rating == -1}">
                                                            <p class="no-rating">Chưa có đánh giá</p>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:forEach begin="1" end="5" var="i">
                                                                <c:choose>
                                                                    <c:when test="${i <= rating}">★</c:when>  <%-- Ngôi sao đầy --%>
                                                                    <c:otherwise>☆</c:otherwise>  <%-- Ngôi sao rỗng --%>
                                                                </c:choose>
                                                            </c:forEach>
                                                            <p class="total_rating">(${String.format("%.1f", rating)})</p>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>
                                            <c:if test="${userRole != 0}">
                                                <div class="product_actions">
                                                    <a href="#">
                                                        <span class="material-icons-sharp">
                                                            add_shopping_cart
                                                        </span>
                                                    </a>
                                                </div>
                                            </c:if>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                            <div class="swiper-button-next"></div>
                            <div class="swiper-button-prev"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="recommendation_container">
        <div class="container">
            <div class="row">
                <div class="col-sm-12 recommendation_products">
                    <div class="title_recommendation_products">
                        <h2 class="title">CÓ THỂ BẠN SẼ THÍCH</h2>
                    </div>
                    <div class="list_products">
                        <c:forEach var="product" items="${similarProducts}">
                            <div class="product best_seller_product">
                                <div class="product_image">
                                    <c:choose>
                                    <c:when test="${not empty product.productImageCollection}">
                                        <c:forEach var="image" items="${product.productImageCollection}" begin="0" end="0">
                                            <a href="${pageContext.request.contextPath}/detail?productID=${product.productID}"><img src="${pageContext.request.getContextPath()}/assets/images/productImages/${image.imageURL}" alt="" ></a>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                    <img src="${pageContext.request.contextPath}/assets/images/default-image.jpg" alt="No image available" style="height: 200px">
                                    </c:otherwise>
                                </c:choose>
                                </div>
                                <div class="product_info">
                                    <div class="product_name text"><p><a href="detail?productID=${product.productID}" class="text-dark">${product.productName}</a></p></div>
                                    <div class="product_price">
                                        <c:choose>
                                            <c:when test="${not empty product.discountCollection}">
                                                <!--//set giá trị discount-->
                                                <c:set var="discount" value="${product.discountCollection.iterator().next()}" />
                                                <div class="price text-primary">
                                                    <fmt:formatNumber value="${product.price * (1 - discount.discountPercent / 100)}"
                                                                      type="number" groupingUsed="true" />đ
                                                </div>
                                                <div class="discount">
                                                    <div class="discount_price text-deleted">
                                                        <del>
                                                            <fmt:formatNumber value="${product.price}" type="number" groupingUsed="true" />đ
                                                        </del>
                                                    </div>
                                                    <div class="discount_tag">
                                                        <small>GIẢM <fmt:formatNumber value="${discount.discountPercent}" type="number" maxFractionDigits="0" />%</small>
                                                    </div>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="price text-primary">
                                                    <fmt:formatNumber value="${product.price}" type="number" groupingUsed="true" />đ
                                                </div>
                                                <div class="sold"></div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    
                                    
                                    <c:set var="rating" value="${product.averageRating}" />

                                    <div class="product_rate text-warning">
                                        <c:choose>
                                            <c:when test="${rating == -1}">
                                                <p class="no-rating">Chưa có đánh giá</p>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach begin="1" end="5" var="i">
                                                    <c:choose>
                                                        <c:when test="${i <= rating}">★</c:when>  <%-- Ngôi sao đầy --%>
                                                        <c:otherwise>☆</c:otherwise>  <%-- Ngôi sao rỗng --%>
                                                    </c:choose>
                                                </c:forEach>
                                                <p class="total_rating">(${String.format("%.1f", rating)})</p>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    
                                    
                                    
                                </div>
                                <c:if test="${userRole != 0}">
                                    <div class="product_actions">
                                        <a href="#">
                                            <span class="material-icons-sharp">
                                                add_shopping_cart
                                            </span>
                                        </a>
                                    </div>
                                </c:if>
                            </div>
                        </c:forEach>
                    </div>
                    <div href="#" class="view_more"><a href="#">Xem thêm sản phẩm</a></div>
                </div>
            </div>
        </div>
    </div>

    <a href="#" class="btn btn-primary back-to-top" title="Quay lại đầu trang">
        <i class="fa fa-angle-double-up"></i>
    </a>
    <!--FOOTER-->
    <jsp:include page="/includes/footer.jsp"></jsp:include>

    <!-- Scripts -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/swiper.js"></script>
    <script>
        var contextPath = "${pageContext.request.contextPath}";
    </script>
    <script src="${pageContext.request.contextPath}/assets/js/product_detail.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/review.js"></script>

    
</body>
</html>