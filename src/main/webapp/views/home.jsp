<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
    
    <!--head-->
     <jsp:include page="/includes/head.jsp"></jsp:include>
     
  
<body>

    <jsp:include page="/includes/header.jsp"></jsp:include>
    

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
                                
<!--                              <div class="swiper-slide"><img src="../assets/images/category.png" alt=""><div class="category_type"><p class="text-category">Sản phẩm bổ trợ</p></div></div>
                              <div class="swiper-slide"><img src="../assets/images/category.png" alt=""><div class="category_type"><p class="text-category">Sản phẩm</p></div></div>
                              <div class="swiper-slide"><img src="../assets/images/category.png" alt=""><div class="category_type"><p class="text-category">Sản phẩm</p></div></div>
                              <div class="swiper-slide"><img src="../assets/images/category.png" alt=""><div class="category_type"><p class="text-category">Sản phẩm</p></div></div>
                              <div class="swiper-slide"><img src="../assets/images/category.png" alt=""><div class="category_type"><p class="text-category">Sản phẩm</p></div></div>
                              <div class="swiper-slide"><img src="../assets/images/category.png" alt=""><div class="category_type"><p class="text-category">Sản phẩm</p></div></div>
                              <div class="swiper-slide"><img src="../assets/images/category.png" alt=""><div class="category_type"><p class="text-category">Sản phẩm</p></div></div>
                              <div class="swiper-slide"><img src="../assets/images/category.png" alt=""><div class="category_type"><p class="text-category">Sản phẩm</p></div></div>
                              <div class="swiper-slide"><img src="../assets/images/category.png" alt=""><div class="category_type"><p class="text-category">Sản phẩm</p></div></div>-->

                            <c:forEach var="cg" items="${listCategoryGroup}">
                                <c:forEach var="category" items="${cg.categoryCollection}">
                                    <div class="swiper-slide"><img src="${pageContext.request.getContextPath()}/assets/images/Category${category.categoryID}.jpg" alt=""><div class="category_type"><p class="text-category"><a href="${pageContext.request.getContextPath()}/cates?ID=${category.categoryID}"  style="color: white">${category.categoryName}</a></p></div></div>
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
                                            <img src="${pageContext.request.getContextPath()}/assets/images/productImages/${image.imageURL}" alt="">
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${pageContext.request.contextPath}/assets/images/default-image.jpg" alt="No image available" style="height: 200px">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="product_info">
                                <div class="product_name text">
                                    <p>${product.productName}</p>
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
                            <img src="${pageContext.request.getContextPath()}/assets/images/CategoryGroup${entry.key.groupID}.png" alt="">
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
                                            <img src="${pageContext.request.getContextPath()}/assets/images/productImages/${product.productImageCollection[0].imageURL}" alt="">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="default-image.jpg" alt="No image available">
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                    <div class="product_info">
                                        <div class="product_name text">
                                            <p>${product.productName}</p>
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
                                         <div class="product_rate text-warning">
                                            <span>&#9733;</span>
                                            <span>&#9733;</span>
                                            <span>&#9733;</span>
                                            <span>&#9733;</span>
                                            <span>&#9734;</span>
                                            <p class="total_rating text">(4.5)</p>
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
    <!-- Chatbot Container -->
    <div id="chatbot-container" class="chatbot-container minimized">
        <div id="chatbot-header" class="chatbot-header">
            <span><i class="fa fa-robot" style="margin-right: 8px;"></i>Chatbot Hỗ Trợ</span>
            <button id="chatbot-toggle-size" class="chatbot-toggle-btn"><i class="fa fa-comment"></i></button>
            <button id="chatbot-close" class="chatbot-close-btn">✖</button>
        </div>
        <div id="chatbot-body" class="chatbot-body">
            <div id="chatbot-messages" class="chatbot-messages"></div>
            <div class="chatbot-input">
                <input type="text" id="chatbot-input" placeholder="Nhập tin nhắn...">
                <button id="chatbot-send"><i class="fa fa-paper-plane" style="margin-right: 5px;"></i>Gửi</button>
            </div>
        </div>
    </div>


    <script>
        document.addEventListener('DOMContentLoaded', () => {
            const chatbotContainer = document.getElementById('chatbot-container');
            const toggleSizeBtn = document.getElementById('chatbot-toggle-size');
            const closeBtn = document.getElementById('chatbot-close');
            const sendBtn = document.getElementById('chatbot-send');
            const input = document.getElementById('chatbot-input');
            const messages = document.getElementById('chatbot-messages');

            // Toggle size of chatbot
            toggleSizeBtn.addEventListener('click', () => {
                chatbotContainer.classList.toggle('minimized');
                // Không cần thay đổi nội dung icon nữa vì đã dùng Font Awesome
            });

            // Close chatbot
            closeBtn.addEventListener('click', () => {
                chatbotContainer.style.display = 'none';
            });

            // Send message
            sendBtn.addEventListener('click', sendMessage);
            input.addEventListener('keypress', (e) => {
                if (e.key === 'Enter') sendMessage();
            });

            function sendMessage() {
                const message = input.value.trim();
                if (!message) return;

                // Add user message
                const userMsg = document.createElement('p');
                userMsg.classList.add('user-message');
                userMsg.textContent = message;
                messages.appendChild(userMsg);

                // Send to ChatbotServlet
                fetch('${pageContext.request.contextPath}/chatbot', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    body: 'message=' + encodeURIComponent(message)
                })
                .then(response => response.text())
                .then(data => {
                    // Add bot response
                    const botMsg = document.createElement('p');
                    botMsg.classList.add('bot-message');
                    botMsg.innerHTML = data; // HTML từ servlet
                    messages.appendChild(botMsg);
                    messages.scrollTop = messages.scrollHeight; // Auto scroll to bottom
                })
                .catch(error => {
                    console.error('Error:', error);
                    const errorMsg = document.createElement('p');
                    errorMsg.classList.add('bot-message');
                    errorMsg.textContent = 'Có lỗi xảy ra, vui lòng thử lại.';
                    messages.appendChild(errorMsg);
                });

                input.value = '';
            }
        });
    </script>


    <!--FOOTER-->
    <jsp:include page="/includes/footer.jsp"></jsp:include>








    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
    <script src="/demo1/assets/js/swiper.js"></script>

</body>
</html>