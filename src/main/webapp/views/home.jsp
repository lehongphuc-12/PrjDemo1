<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <!--head-->
         <jsp:include page="/includes/head.jsp"></jsp:include>
    </head>
  
<body>

    <jsp:include page="/includes/header.jsp"></jsp:include>
    
    <c:set var="userRole" value="${sessionScope.user != null && sessionScope.user.roleID != null ? sessionScope.user.roleID.roleID : 0}" />

    <main>
        <section class="sec_slide_home__main">
            <div class="container">
                <div class="row">
                    <div class="banner col-12">
                        <div class="blur"></div>
                        <h1 class="title">Nông sản tươi sạch <br> chất lượng từ thiên nhiên</h1>
                        <div class="category c-1"></div>
                        <div class="category c-2"></div>
                        <div class="category c-3"></div>
                        <div class="category c-4"></div>
                        <div class="category c-5"></div>
                        <div class="category c-6"></div>
                    </div>
                </div>
            </div>
        </section>

        <section class="sec_category">
            <div class="container">
                <div class="content_category">
                    <h2 class="title">Danh mục</h2>
                    <div class="list_category">
                        <div class="swiper mySwiper category">
                            <div class="swiper-wrapper">
                                
                            <c:forEach var="cg" items="${listCategoryGroup}">
                                <c:forEach var="category" items="${cg.categoryCollection}">
                                    <div class="swiper-slide">
                                        <a href="${pageContext.request.getContextPath()}/cates?ID=${category.categoryID}"  style="display: inline-block; width: 100%;height: 100%;position: absolute"></a>
                                        <img src="${pageContext.request.getContextPath()}/assets/images/Category${category.categoryID}.jpg" alt="">     
                                        <div class="category_type"><p class="text-category"><a href="${pageContext.request.getContextPath()}/cates?ID=${category.categoryID}"  style="color: white">${category.categoryName}</a></p></div>
                                    </div>
                                </c:forEach>
                            </c:forEach>
                              
                            </div>
                            <div class="swiper-button-next"></div>
                            <div class="swiper-button-prev"></div>
                          </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- CATEGORY -->


        <section class="sec_outstanding">
            <div class="container">
                <h2 class="title">Sản phẩm bán chạy</h2>
                <div class="list_products">
                    <c:forEach var="product" items="${listPopularProducts}">
                        <div class="product best_seller_product">
                            <div class="product_image">
                                <c:choose>
                                    <c:when test="${not empty product.productImageCollection}">
                                        <c:forEach var="image" items="${product.productImageCollection}" begin="0" end="0">
                                            <a href="${pageContext.request.contextPath}/detail?productID=${product.productID}">
                                                <img src="${pageContext.request.getContextPath()}/assets/images/productImages/${image.imageURL}" alt="" >
                                            </a>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${pageContext.request.contextPath}/assets/images/default-image.jpg" alt="No image available" style="height: 200px">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="product_info">
                                <div class="product_name text">
                                    <p><a href="${pageContext.request.contextPath}/detail?productID=${product.productID}">${product.productName}</a></p>
                                </div>

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
                                
                                <!--Đánh giá sản phẩm--> 
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

                <!--<div class="view_more"><a href="#">Xem sản phẩm</a></div>-->
            </div>
        </section>

        <!-- BEST SELLER -->
        
        
        <!--CATEGORY GROUP-->
        <section class="sec_product_category_list">
            <c:forEach var="entry" items="${listCategoryGroupProducts}">
                <div class="sec_product_category">
                     <!--Banner Category--> 
                    <div class="banner_product_category">
                        <a href="#">
                            <img src="${pageContext.request.getContextPath()}/assets/images/CategoryGroup${entry.key.groupID}.png" alt="" style="border-radius: 8px">
                        </a>
                        <div class="title_banner_product">
                            <h3 class="text-banner">${entry.key.groupName}</h3>
                        </div>
                    </div>

                     <!--Danh sách sản phẩm--> 
                    <div class="container">
                        <div class="list_products">  
                            <c:forEach var="product" items="${entry.value}">
                                <div class="product best_seller_product">
                                    <div class="product_image">
                                    <c:choose>
                                        <c:when test="${not empty product.productImageCollection}">
                                            <a href="${pageContext.request.contextPath}/detail?productID=${product.productID}"><img src="${pageContext.request.getContextPath()}/assets/images/productImages/${product.productImageCollection[0].imageURL}" alt=""></a>
                                        </c:when>
                                        <c:otherwise>
                                            <img src="default-image.jpg" alt="No image available">
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                    <div class="product_info">
                                        <div class="product_name text">
                                            <p><a href="${pageContext.request.contextPath}/detail?productID=${product.productID}">${product.productName}</a></p>
                                        </div>

                                         <!--Hiển thị giá sản phẩm--> 
                                        <div class="product_price">
                                            <c:choose>
                                                <c:when test="${not empty product.discountCollection}">
                                                    
                                                    <div class="price text-primary">
                                                        <fmt:formatNumber value="${product.price * (1 - product.discountCollection[0].discountPercent / 100)}"
                                                                          type="number" groupingUsed="true" />đ
                                                    </div>
                                                    <div class="discount">
                                                        <div class="discount_price text-deleted">
                                                            <del>
                                                                <fmt:formatNumber value="${product.price}" type="number" groupingUsed="true" />đ
                                                            </del>
                                                        </div>
                                                        <div class="discount_tag">
                                                            <small>GIẢM ${product.discountCollection[0].discountPercent}%</small>
                                                        </div>
                                                    </div>
                                                    
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="price text-primary">
                                                        <fmt:formatNumber value="${product.price}" type="number" groupingUsed="true" />đ
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>

                                         <!--Đánh giá sản phẩm--> 
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
                     <c:if test="${not empty entry.key.groupID}">
                        <div class="view_more">
                            <a href="${pageContext.request.getContextPath()}/cates?action=cateGroup&ID=${entry.key.groupID}">Xem thêm sản phẩm</a>
                        </div>
                    </c:if>
                </div>
            </c:forEach>
            
        </section>

         <!--PRODUCT CATEGORY--> 
         
       
  
         
    </main>
    
    

    <!--FOOTER-->
    <jsp:include page="/includes/footer.jsp"></jsp:include>




    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
    <script src="/demo1/assets/js/swiper.js"></script>
    <script src="/demo1/assets/js/main.js"></script>
    
    <script>
        var contextPath = "${pageContext.request.contextPath}";
    </script>
    <script type="module" src="/demo1/assets/js/product_detail.js"></script>
</body>
</html>