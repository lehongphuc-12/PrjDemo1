
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
                    <div class="categories">
                        <h4 class="title">Danh mục</h4>
                        <ul>
                            <li><a href="#"><p>Tất cả sản phẩm</p></a></li>
                            <li><a href="#"><p>Thực phẩm và đặc sản</p></a></li>
                            <li><a href="#"><p>Thực phẩm bổ dưỡng</p></a></li>
                            <li>
                                <a href="#"><p>Đồ uống</p></a>
                                <ul class="original_ul">
                                    <li><a href="#">Sữa</a></li>
                                    <li><a href="#">Nước ép</a></li>
                                    <li><a href="#">Nước ngọt</a></li>
                                </ul>
                            </li>
                            <li><a href="#"><p>Sức khoẻ và làm đẹp</p></a></li>
                        
                        </ul>
                    </div>

                    <div class="selling_location">
                        <h4 class="title">Nơi bán</h4>
                        <ul>
                            <li>
                                <input name="#" type="checkbox"> <label for="#">Hà Nội</label>
                            </li>
                            <li>
                                <input name="#" type="checkbox"> <label for="#">Hà Nội</label>
                            </li>
                            <li>
                                <input name="#" type="checkbox"> <label for="#">Hà Nội</label>
                            </li>
                            <li>
                                <input name="#" type="checkbox"> <label for="#">Hà Nội</label>
                            </li>
                            <li>
                                <input name="#" type="checkbox"> <label for="#">Hà Nội</label>
                            </li>
                        </ul> 
                    </div>

                    <div class="search_price_range">
                        <form action="#">
                            <h4 class="title">Chọn khoảng giá</h4>
                            <div class="price_range">
                                <input id="from" type="number">
                                <span> - </span>
                                <input id="to" type="number">
                            </div>
                            <div><button class="btn btn-primary" style="background: var(--white-color);
                                                                        color: var(--color-primary);
                                                                        border: 1px solid var(--color-primary);"
                                >Áp dụng</button></div>
                            <div><button class="btn btn-primary" type="reset">Đặt lại</button></div>

                        </form>
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
</body>
</html>
