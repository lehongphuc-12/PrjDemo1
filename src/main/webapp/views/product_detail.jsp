<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
    
    <!--head-->
     <jsp:include page="../includes/head.jsp"></jsp:include>
     
<body>
    <!--HEADER-->
     <jsp:include page="../includes/header.jsp"></jsp:include>

    <!-- ==================== PRODUCT DETAILS ================== -->

    <div class="details_container">
        <div class="container">
            <div class=" row  product_detail">
                <!-- DETAIL LEFT -->
                <div class="product_detail_left col-sm-5 col-12">
                    <div style="--swiper-navigation-color: #fff; --swiper-pagination-color: #fff" class="swiper mySwiper2 product_detail_slide">
                        <div class="swiper-wrapper">
                            <div class="swiper-slide"><img src="../assets/images/detail1.png" alt="Hình ảnh sản phẩm chi tiết 1"></div>
                            <div class="swiper-slide"><img src="../assets/images/detail2.png" alt="Hình ảnh sản phẩm chi tiết 2"></div>
                            <div class="swiper-slide"><img src="../assets/images/detail3.png" alt="Hình ảnh sản phẩm chi tiết 3"></div>
                            <div class="swiper-slide"><img src="../assets/images/detail4.png" alt="Hình ảnh sản phẩm chi tiết 4"></div>
                            <div class="swiper-slide"><img src="../assets/images/detail4.png" alt="Hình ảnh sản phẩm chi tiết 4"></div>
                            <div class="swiper-slide"><img src="../assets/images/detail4.png" alt="Hình ảnh sản phẩm chi tiết 4"></div>
                        </div>
                        <!-- <div class="swiper-button-next"></div>
                        <div class="swiper-button-prev"></div> -->
                    </div>
                    <div thumbsSlider="" class="swiper mySwiper product_detail_slide">
                        <div class="swiper-wrapper">
                            <div class="swiper-slide"><img src="../assets/images/detail1.png" alt="Thumbnail sản phẩm chi tiết 1"></div>
                            <div class="swiper-slide"><img src="../assets/images/detail2.png" alt="Thumbnail sản phẩm chi tiết 2"></div>
                            <div class="swiper-slide"><img src="../assets/images/detail3.png" alt="Thumbnail sản phẩm chi tiết 3"></div>
                            <div class="swiper-slide"><img src="../assets/images/detail4.png" alt="Thumbnail sản phẩm chi tiết 4"></div>
                            <div class="swiper-slide"><img src="../assets/images/detail4.png" alt="Thumbnail sản phẩm chi tiết 4"></div>
                            <div class="swiper-slide"><img src="../assets/images/detail4.png" alt="Thumbnail sản phẩm chi tiết 4"></div>
                        </div>
                        <div class="swiper-button-next"></div>
                        <div class="swiper-button-prev"></div>
                    </div>
                </div>
                <!-- END DETAIL LEFT -->

                <!-- DETAIL RIGHT -->
                <div class="product_detail_right col-sm-7 col-12">
                    <form class="product_detail_info">
                        <div class="product_detail_name">
                            
                            <p class="text-name"><span class="tag">Yêu thích</span> OCOP - Hạt Điều Rang Củi Hải Bình - Gói 500gr</p>
                        </div>
                        <div class="product_detail_review">
                            <div class="star_rating text-warning">
                                <p class="total_rating text">(4.5)</p>
                                <span>&#9733;</span>
                                <span>&#9733;</span>
                                <span>&#9733;</span>
                                <span>&#9733;</span>
                                <span>&#9734;</span>
                            </div>
                            <div class="sold">
                                <p class="text-deleted">Đã bán: 1000</p>
                            </div>
                        </div>

                        <hr>

                        <div class="product_detail_price">

                            <div class="price text-primary">450.000&#8363;</div>
                            <div class="discount">
                                
                                <div class="discount_price text-deleted">
                                    <del style="font-size: 1.4rem;">99.000&#8363;</del>
                                </div>
                                <div class="discount_tag"><small>GIẢM 11%</small></div>
                                
                            </div>
                            
                        </div>

                        <hr>

                        <table class="detail_body">
                            <tr class="product_location">
                                <td>Gửi từ: </td>
                                <td class="text">Hải Bình</td>
                            </tr>
                            <tr class="seller">
                                <td>Được bán bởi: </td>
                                <td class="text"><a href="#">Củi Hải Bình</a></td>
                            </tr>
                        </table>
                        

                        <div class="product_quantity">
                            <div class="quantity_input">
                                <label for="quantity">Số lượng:  </label>
                                <input id="quantity" type="number" value="1" min="1" max="50">
                            </div>
                            <div class="available">
                                <p class="text-deleted">1000 sản phẩm có sẵn</p>
    
                            </div>
                        </div>

                        <div class="product_buying">
                            <div class="add_to_cart">
                                <button class="btn btn-primary">
                                    <span class="material-icons-sharp">add_shopping_cart</span>
                                    <p>Thêm vào giỏ hàng</p>
                                </button>
                            </div>
                            <div class="buy">
                                <button class="btn btn-primary"><p>Mua ngay</p></button>
                            </div>
                        </div>
                
                    </form>
                </div>
                <!-- END DETAIL RIGHT -->
            </div>
        </div>
    </div>
    <!--  END DETAIL PRODUCT -->


    <div class="store_container">
        <div class="container">
            <div class="row store">

                    <div class="box_store col-sm-5">
                        <div class="logo_store">
                            <a href="#"><img src="../assets/images/store_logo.png" title="logo" alt=""></a>
                            <span class="tag">Yêu thích</span>
                        </div>
                        <div class="info_store">
                            <h4 class="text-primary">VICO SHOP - Shop dừa Trà Vinh</h4>
                            <div class="btn_info_store">
                                <a href="#"><span class="material-icons-sharp">home</span> <p>Xem cửa hàng</p></a>
                            </div>
                        </div>
                    </div>
                    <div class="store_reviews col-sm-7">
                        <div class="reviews">
                            <div class="review">
                                <p>Đánh giá: </p> <span class="text-primary">123</span>
                            </div>
                            <div class="review">
                                <p>Đánh giá: </p> <span class="text-primary">123</span>
                            </div>
                            <div class="review">
                                <p>Đánh giá: </p> <span class="text-primary">123</span>
                            </div>
                            <div class="review">
                                <p>Đánh giá: </p> <span class="text-primary">123</span>
                            </div>
                        </div>
                    </div>

        </div>
    </div>
    </div>
    <!--  END STORE -->


    <div class="detail_content_container">
        <div class="container">
            <div class=" row">
                <div class="product_detail_content col-12">
                    <h3 class="title">CHI TIẾT SẢN PHẨM</h3>
                    <div class="content">
                        Lorem ipsum dolor sit amet consectetur, adipisicing elit. Voluptas facere corporis magnam laudantium, porro amet delectus debitis tenetur temporibus ipsa repudiandae culpa vitae error sed aperiam nulla tempora optio officiis.
                        Culpa corporis accusamus libero quae rem nemo accusantium! Dolores unde atque voluptas cumque nulla inventore consectetur aspernatur iusto, accusantium fuga quas, placeat aliquid odit distinctio, enim vero laboriosam et. Neque.
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="review_content_container">
        <div class="container">
            <div class=" row ">
                <div class="product_reviews_content col-12">
                    <h3 class="title">ĐÁNH GIÁ SẢN PHẨM</h3>
                    <!-- Vẫn đang suy nghĩ cách giải quyết -->
                </div>
            </div>
        </div>
    </div>


    <div class="product_of_store_container">
        <div class="container">
            <div class="row">
                <div class="product_of_store col-sm-12 col-12">
                    <h2 class="title">CÁC SẢN PHẨM KHÁC CỦA SHOP</h2>
                    <div class="list_products_store">
                        <div class="swiper mySwiper store_product">
                            <div class="swiper-wrapper">
                                <div class="swiper-slide">
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
                                </div>
                                <div class="swiper-slide">
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
                                </div>
                                <div class="swiper-slide">
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
                                </div>
                                <div class="swiper-slide">
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
                                </div>
                                <div class="swiper-slide">
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
                                </div>
                                <div class="swiper-slide">
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
                                </div>
                
                
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
                        <h2 class="title">CÓ THỂ BẠN CŨNG THÍCH</h2>
                    </div>

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


   <!--FOOTER-->
    <jsp:include page="../includes/footer.jsp"></jsp:include>

    <!-- Scripts -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
    <script src="../assets/js/swiper.js"></script>
</body>
</html>

