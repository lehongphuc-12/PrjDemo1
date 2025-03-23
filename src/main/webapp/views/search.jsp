<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="/includes/head.jsp"/>

</head>
<body>
<jsp:include page="/includes/header.jsp"/>
    <c:set var="userRole" value="${sessionScope.user != null && sessionScope.user.roleID != null ? sessionScope.user.roleID.roleID : 0}" />

<div class="search_container">
    <div class="container">
        <div class="row search g-5">
            <div class="search_type col-3">
                <div class="category-container">
                    <c:forEach var="cg" items="${listCategoryGroup}">
                        <div class="category">
                            <div class="category-header" onclick="toggleDropdown(this)">
                                <span style="text-transform: uppercase;">${cg.groupName}</span>
                                <span class="arrow">▾</span>
                            </div>
                            <ul class="category-list">
                                <c:forEach var="category" items="${cg.categoryCollection}">
                                    <li>
                                        <a href="javascript:void(0);"
                                           onclick="filter(${category.categoryID}, '', 1, '', 'popular')">
                                            ${category.categoryName}
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <div class="search_results col-9" id="search_results">
                <c:if test="${empty listPro}">
                    <p>Không tìm thấy sản phẩm nào.</p>
                </c:if>
                <c:if test="${not empty listPro}">
                    <div class="ranking_criteria">
                        <div class="number_of_products">
                            <p>Hiện có <span class="text-primary">${totalProducts}</span> sản phẩm <span
                                    class="text-primary">${typeOfProducts}</span></p>
                        </div>
                        <div class="criterias">
                            <a href="javascript:void(0);"
                               onclick="filter(${ID}, '${search}', ${page}, '${action}', 'popular')"
                               class="${filter == 'popular' ? 'text-primary' : ''}"><p>Bán chạy</p></a>
                            <a href="javascript:void(0);"
                               onclick="filter(${ID}, '${search}', ${page}, '${action}', 'newest')"
                               class="${filter == 'newest' ? 'text-primary' : ''}"><p>Hàng mới</p></a>
                            <a href="javascript:void(0);"
                               onclick="filter(${ID}, '${search}', ${page}, '${action}', 'asc')"
                               class="${filter == 'asc' ? 'text-primary' : ''}"><p>Giá từ thấp đến cao</p></a>
                            <a href="javascript:void(0);"
                               onclick="filter(${ID}, '${search}', ${page}, '${action}', 'desc')"
                               class="${filter == 'desc' ? 'text-primary' : ''}"><p>Giá từ cao đến thấp</p></a>
                        </div>
                    </div>

                    <div class="list_products">
                        <c:forEach var="product" items="${listPro}">
                            <div class="product">
                                <div class="product_image">
                                    <c:choose>
                                        <c:when test="${not empty product.productImageCollection}">
                                            <a href="${pageContext.request.contextPath}/detail?productID=${product.productID}">
                                                <img src="${pageContext.request.contextPath}/assets/images/productImages/${product.productImageCollection[0].imageURL}"
                                                     alt="${product.productName}">
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <img src="${pageContext.request.contextPath}/assets/images/default-image.jpg"
                                                 alt="No image available"
                                                 style = "height: 200px">
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="product_info">
                                    <div class="product_name text">
                                        <p><a href="${pageContext.request.contextPath}/detail?productID=${product.productID}">${product.productName}</a></p>
                                    </div>
                                    <div class="product_price">
                                        <c:choose>
                                            <c:when test="${not empty product.discountCollection}">
                                                <div class="price text-primary">
                                                    <fmt:formatNumber
                                                            value="${product.price * (1 - product.discountCollection[0].discountPercent / 100)}"
                                                            type="number" groupingUsed="true"/>đ
                                                </div>
                                                <div class="discount">
                                                    <div class="discount_price text-deleted">
                                                        <del><fmt:formatNumber value="${product.price}" type="number"
                                                                               groupingUsed="true"/>đ</del>
                                                    </div>
                                                    <div class="discount_tag">
                                                        <small>GIẢM <fmt:formatNumber
                                                                value="${product.discountCollection[0].discountPercent}"
                                                                type="number" maxFractionDigits="0"/>%</small>
                                                    </div>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="price text-primary">
                                                    <fmt:formatNumber value="${product.price}" type="number"
                                                                      groupingUsed="true"/>đ
                                                </div>
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
                                                <p class="total_rating">${String.format("%.1f", rating)} ★</p>
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
                                    
                                <div class="buy">
                                    <button class="btn btn-primary buy-now-btn" data-product-id="${product.productID}" type="button">
                                        <p>Mua ngay</p>
                                    </button>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <div class="page_numbers">
                        <c:forEach var="index" begin="1" end="${endPage}">
                            <span class="page-item ${index == page ? 'active' : ''}">
                                <a href="javascript:void(0);"
                                   onclick="filter(${ID}, '${search}', ${index}, '${action}', '${filter}')">${index}</a>
                            </span>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/includes/footer.jsp"/>


<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
<script src="/demo1/assets/js/swiper.js"></script>
<script src="/demo1/assets/js/main.js"></script>
<script src="/demo1/assets/js/filter.js"></script>
<script>
    var contextPath = "${pageContext.request.contextPath}";
</script>
<script type="module" src="/demo1/assets/js/product_detail.js"></script>


</body>
</html>