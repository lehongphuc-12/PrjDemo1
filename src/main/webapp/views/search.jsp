
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    
    <!--head-->
     <jsp:include page="../includes/head.jsp"></jsp:include>
     
<body>
   <!--HEADER-->
     <jsp:include page="../includes/header.jsp"></jsp:include>



    <div class="search_container">
        <div class="container">
            <div class="row search g-5">
                <div class="search_type col-3">
                    <div class="category-container">
                        <div class="category">
                            <div class="category-header" onclick="toggleDropdown(this)">
                                <span>THỊT, CÁ, TRỨNG, HẢI SẢN</span>
                                <span class="arrow">&#9662;</span>
                            </div>
                            <ul class="category-list">
                                <li><a href="#">Thịt heo</a></li>
                                <li><a href="#">Thịt bò</a></li>
                                <li><a href="#">Thịt gà, vịt, chim</a></li>
                                <li><a href="#">Thịt sơ chế</a></li>
                                <li><a href="#">Cá, hải sản</a></li>
                                <li><a href="#">Trứng gà, vịt, cút</a></li>
                                <li><a href="#">Món ngon mỗi ngày</a></li>
                            </ul>
                        </div>

                        <div class="category">
                            <div class="category-header" onclick="toggleDropdown(this)">
                                <span>RAU, CỦ, NẤM, TRÁI CÂY</span>
                                <span class="arrow">&#9662;</span>
                            </div>
                            <ul class="category-list">
                                <li><a href="#">Rau xanh</a></li>
                                <li><a href="#">Củ quả</a></li>
                                <li><a href="#">Nấm</a></li>
                                <li><a href="#">Trái cây</a></li>
                            </ul>
                        </div>
                    </div>

                </div>
                <!-- =================================== -->

                <div class="search_results col-9">
                    <div class="ranking_criteria">
                        <div class="number_of_products">
                            <p>Hiện có <span class="text-primary">133</span> sản phẩm</p>
                        </div>
                        <div class="criterias">
                            <a href="#"><p>Phổ biến</p></a>
                            <a href="#"><p>Bán chạy</p></a>
                            <a href="#"><p>Hàng mới</p></a>
                            <a href="#"><p>Giá từ thấp đến cao</p></a>
                            <a href="#"><p>Giá từ cao đến thấp</p></a>
                        </div>
                    </div>

                    <!-- LIST PRODUCTS -->
                    <div class="list_products">
                        
                        <div class="product best_seller_product">
                            <div class="product_image"><img src="../assets/images/best_seller_product.png" alt=""></div>
                            <div class="product_info">
                                <div class="product_name text"><p>Bánh dừa nướng Quý Thu - Gói 150gr ádasdas ádadasdas ádasdasd</p></div>
                                <div class="product_price">
                                    <div class="discount">
                                        <div class="discount_price text-deleted"><del>99.000đ</del></div>
                                        <div class="discount_tag"><small>GIẢM 11%</small></div>
                                    </div>
                                    <div class="price text-primary">450.000đ</div>
                                </div>
                                <div class="product_rate text-warning">
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9734;</span>
                                    <p class="total_rating text">(4.5)</p>
                                </div>
                            </div>

                            <div class="product_actions">
                            <a href="#">
                                <span class="material-icons-sharp">
                                    add_shopping_cart
                                </span>
                            </a>
                        </div>

                        </div>

                        <div class="product best_seller_product">
                            <div class="product_image"><img src="../assets/images/best_seller_product.png" alt=""></div>
                            <div class="product_info">
                                <div class="product_name text"><p>Bánh dừa nướng Quý Thu - Gói 150gr ádasdas ádadasdas ádasdasd</p></div>
                                <div class="product_price">
                                    <div class="discount">
                                        <div class="discount_price text-deleted"><del>99.000đ</del></div>
                                        <div class="discount_tag"><small>GIẢM 11%</small></div>
                                    </div>
                                    <div class="price text-primary">450.000đ</div>
                                </div>
                                <div class="product_rate text-warning">
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9734;</span>
                                    <p class="total_rating text">(4.5)</p>
                                </div>
                            </div>

                            <div class="product_actions">
                            <a href="#">
                                <span class="material-icons-sharp">
                                    add_shopping_cart
                                </span>
                            </a>
                        </div>

                        </div>
    
                        <div class="product best_seller_product">
                            <div class="product_image"><img src="../assets/images/best_seller_product.png" alt=""></div>
                            <div class="product_info">
                                <div class="product_name text"><p>Bánh dừa nướng Quý Thu - Gói 150gr</p></div>
                                <div class="product_price">
                                    <div class="discount">
                                        <div class="discount_price text-deleted"><del>99.000đ</del></div>
                                        <div class="discount_tag"><small>GIẢM 11%</small></div>
                                    </div>
                                    <div class="price text-primary">450.000đ</div>
                                </div>
                                <div class="product_rate text-warning">
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9734;</span>
                                    <p class="total_rating text">(4.5)</p>
                                </div>
                            </div>

                            <div class="product_actions">
                            <a href="#">
                                <span class="material-icons-sharp">
                                    add_shopping_cart
                                </span>
                            </a>
                        </div>

                        </div>
    
                        <div class="product best_seller_product">
                            <div class="product_image"><img src="../assets/images/best_seller_product.png" alt=""></div>
                            <div class="product_info">
                                <div class="product_name text"><p>Bánh dừa nướng Quý Thu - Gói 150gr</p></div>
                                <div class="product_price">
                                    <div class="discount">
                                        <div class="discount_price text-deleted"><del>99.000đ</del></div>
                                        <div class="discount_tag"><small>GIẢM 11%</small></div>
                                    </div>
                                    <div class="price text-primary">450.000đ</div>
                                </div>
                                <div class="product_rate text-warning">
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9734;</span>
                                    <p class="total_rating text">(4.5)</p>
                                </div>
                            </div>

                            <div class="product_actions">
                            <a href="#">
                                <span class="material-icons-sharp">
                                    add_shopping_cart
                                </span>
                            </a>
                        </div>

                        </div>
    
                        <div class="product best_seller_product">
                            <div class="product_image"><img src="../assets/images/best_seller_product.png" alt=""></div>
                            <div class="product_info">
                                <div class="product_name text"><p>Bánh dừa nướng Quý Thu - Gói 150gr</p></div>
                                <div class="product_price">
                                    <div class="discount">
                                        <div class="discount_price text-deleted"><del>99.000đ</del></div>
                                        <div class="discount_tag"><small>GIẢM 11%</small></div>
                                    </div>
                                    <div class="price text-primary">450.000đ</div>
                                </div>
                                <div class="product_rate text-warning">
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9734;</span>
                                    <p class="total_rating text">(4.5)</p>
                                </div>
                            </div>

                            <div class="product_actions">
                            <a href="#">
                                <span class="material-icons-sharp">
                                    add_shopping_cart
                                </span>
                            </a>
                        </div>

                        </div>
    
                        <div class="product best_seller_product">
                            <div class="product_image"><img src="../assets/images/best_seller_product.png" alt=""></div>
                            <div class="product_info">
                                <div class="product_name text"><p>Bánh dừa nướng Quý Thu - Gói 150gr</p></div>
                                <div class="product_price">
                                    <div class="discount">
                                        <div class="discount_price text-deleted"><del>99.000đ</del></div>
                                        <div class="discount_tag"><small>GIẢM 11%</small></div>
                                    </div>
                                    <div class="price text-primary">450.000đ</div>
                                </div>
                                <div class="product_rate text-warning">
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9734;</span>
                                    <p class="total_rating text">(4.5)</p>
                                </div>
                            </div>

                            <div class="product_actions">
                            <a href="#">
                                <span class="material-icons-sharp">
                                    add_shopping_cart
                                </span>
                            </a>
                        </div>

                        </div>
    
                        <div class="product best_seller_product">
                            <div class="product_image"><img src="../assets/images/best_seller_product.png" alt=""></div>
                            <div class="product_info">
                                <div class="product_name text"><p>Bánh dừa nướng Quý Thu - Gói 150gr</p></div>
                                <div class="product_price">
                                    <div class="discount">
                                        <div class="discount_price text-deleted"><del>99.000đ</del></div>
                                        <div class="discount_tag"><small>GIẢM 11%</small></div>
                                    </div>
                                    <div class="price text-primary">450.000đ</div>
                                </div>
                                <div class="product_rate text-warning">
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9734;</span>
                                    <p class="total_rating text">(4.5)</p>
                                </div>
                            </div>

                            <div class="product_actions">
                            <a href="#">
                                <span class="material-icons-sharp">
                                    add_shopping_cart
                                </span>
                            </a>
                        </div>

                        </div>
    
                        <div class="product best_seller_product">
                            <div class="product_image"><img src="../assets/images/best_seller_product.png" alt=""></div>
                            <div class="product_info">
                                <div class="product_name text"><p>Bánh dừa nướng Quý Thu - Gói 150gr</p></div>
                                <div class="product_price">
                                    <div class="discount">
                                        <div class="discount_price text-deleted"><del>99.000đ</del></div>
                                        <div class="discount_tag"><small>GIẢM 11%</small></div>
                                    </div>
                                    <div class="price text-primary">450.000đ</div>
                                </div>
                                <div class="product_rate text-warning">
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9734;</span>
                                    <p class="total_rating text">(4.5)</p>
                                </div>
                            </div>

                            <div class="product_actions">
                            <a href="#">
                                <span class="material-icons-sharp">
                                    add_shopping_cart
                                </span>
                            </a>
                        </div>

                        </div>
    
                        <div class="product best_seller_product">
                            <div class="product_image"><img src="../assets/images/best_seller_product.png" alt=""></div>
                            <div class="product_info">
                                <div class="product_name text"><p>Bánh dừa nướng Quý Thu - Gói 150gr</p></div>
                                <div class="product_price">
                                    <div class="discount">
                                        <div class="discount_price text-deleted"><del>99.000đ</del></div>
                                        <div class="discount_tag"><small>GIẢM 11%</small></div>
                                    </div>
                                    <div class="price text-primary">450.000đ</div>
                                </div>
                                <div class="product_rate text-warning">
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9734;</span>
                                    <p class="total_rating text">(4.5)</p>
                                </div>
                            </div>

                            <div class="product_actions">
                            <a href="#">
                                <span class="material-icons-sharp">
                                    add_shopping_cart
                                </span>
                            </a>
                        </div>

                        </div>
    
                        <div class="product best_seller_product">
                            <div class="product_image"><img src="../assets/images/best_seller_product.png" alt=""></div>
                            <div class="product_info">
                                <div class="product_name text"><p>Bánh dừa nướng Quý Thu - Gói 150gr</p></div>
                                <div class="product_price">
                                    <div class="discount">
                                        <div class="discount_price text-deleted"><del>99.000đ</del></div>
                                        <div class="discount_tag"><small>GIẢM 11%</small></div>
                                    </div>
                                    <div class="price text-primary">450.000đ</div>
                                </div>
                                <div class="product_rate text-warning">
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9733;</span>
                                    <span>&#9734;</span>
                                    <p class="total_rating text">(4.5)</p>
                                </div>
                            </div>

                            <div class="product_actions">
                            <a href="#">
                                <span class="material-icons-sharp">
                                    add_shopping_cart
                                </span>
                            </a>
                        </div>

                        </div>
    
                       
                    </div>
    
                    <div href="#" class="view_more"><a href="#">Xem thêm sản phẩm</a></div>

                </div>
            </div>
        </div>
    </div>



     <!--FOOTER-->
    <jsp:include page="../includes/footer.jsp"></jsp:include>
    

    <!-- Scripts -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
    <script src="../assets/js/swiper.js"></script>
    <script src="../assets/js/main.js"></script>
</body>
</html>
