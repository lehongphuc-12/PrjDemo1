
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    
    <!--head-->
     <jsp:include page="../includes/head.jsp"></jsp:include>

<body>
    <!--HEADER-->
     <jsp:include page="../includes/header.jsp"></jsp:include>


    <div class="seller_container">
        <div class="container">
            <div class="row seller_page">

                <div class="seller_info_container col-sm-12">
                    <div class="seller_info col-6 mx-auto">
                        <div class="row">
                            <div class="seller_info_left col-sm-4">
                                <img src="../assets/images/store_logo.png" alt="">
                            </div>
                            <div class="seller_info_right col-sm-8">
                               <div class="row">
                                    <div class="top col-12">
                                        <div class="seller_name text-primary" ><h3>VICCO STORE</h3></div>
                                        <div class="seller_address text-deleted">
                                            <p>123 Lê Văn Thiện, P.12, Q.1, TP.HCM</p>
                                            <p>09012345678</p>
                                        </div>
                                    </div>
                                    <div class="bottom col-12">
                                        <div class="sold"><p>Sản phẩm đã bán : 20</p></div>
                                        <div class="amount"><p>Số lượng sản phẩm : 5</p></div>
                                    </div>
                               </div>
                        </div>   
                    </div>
                </div>

                <hr>

                <div class="nav_bar">
                    <div class="container">
                        <div class="tabs">
                            
                            <div class="tab-item active">
                                Gian hàng
                              </div>
                              <!-- <div class="tab-item">
                                Sản phẩm mới
                              </div> -->
                              <div class="tab-item">
                                Bán chạy nhất
                              </div>
                              <div class="tab-item">
                                Khuyến mãi
                              </div>
                              <div class="line"></div>
                        </div>

                        <!-- Tab content -->
                        <div class="tab-content">
                            <div class="tab-pane active">
                                <div class="newest_products">
                                    <div class="title"><h3>Sản phẩm mới</h3></div>
                                    <div class="list_products">
                                     
                                        <div class="swiper mySwiper ">
                                            <div class="swiper-wrapper">
                                                
                                            <div class="swiper-slide">
                                                <div class="product newest_product">
                                                <div class="product_image"><img src="../assets/images/best_seller_product.png" alt=""></div>
                                                <div class="product_info">
                                                <div class="product_name text"><p>Bánh dừa nướng Quý Thu - Gói 150gr ádasdas ádadasdas ádasdasd</p></div>
                                                <div class="product_price">
                                                       <div class="price text-primary">450.000đ</div>
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
                                                <div class="product newest_product">
                                            <div class="product_image"><img src="../assets/images/best_seller_product.png" alt=""></div>
                                            <div class="product_info">
                                                <div class="product_name text"><p>Bánh dừa nướng Quý  - Gói 150gr ádasdas ádadasdas ádasdasd</p></div>
                                                <div class="product_price">
                                                       <div class="price text-primary">450.000đ</div>
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
                                            <!-- END -->

                                            <div class="swiper-slide">
                                                <div class="product newest_product">
                                            <div class="product_image"><img src="../assets/images/best_seller_product.png" alt=""></div>
                                            <div class="product_info">
                                                <div class="product_name text"><p>Bánh dừa nướng Quý  - Gói 150gr ádasdas ádadasdas ádasdasd</p></div>
                                                <div class="product_price">
                                                       <div class="price text-primary">450.000đ</div>
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
                                            <!-- END -->

                                            <div class="swiper-slide">
                                                <div class="product newest_product">
                                            <div class="product_image"><img src="../assets/images/best_seller_product.png" alt=""></div>
                                            <div class="product_info">
                                                <div class="product_name text"><p>Bánh dừa nướng  - Gói 150gr ádasdas ádadasdas ádasdasd</p></div>
                                                <div class="product_price">
                                                       <div class="price text-primary">450.000đ</div>
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
                                            <!-- END -->
                                            </div>

                                            <div class="swiper-button-next"></div>
                                            <div class="swiper-button-prev"></div>

                                            </div>
                    
                                    </div>
                                </div>
                                <!-- NEW PRODUCT ------------------------------>


                                <div class="all_products">
                                    <div class="title"><h3>Tất cả sản phẩm</h3></div>
                                    <div class="list_products">
                                     
                    
                                        <div class="product ">
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
                    
                                        <div class="product ">
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
                    
                                        <div class="product ">
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
                    
                                        <div class="product ">
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
                    
                                        <div class="product ">
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
                                </div>
                            </div>
                            
                                    <div class="page_numbers">
                                        <span><a href="#">1</a></span>
                                        <span><a href="#">2</a></span>
                                        <span><a href="#">3</a></span>
                                        <span><a href="#">4</a></span>
                                        <span><a href="#">5</a></span>
                                    </div>
                            <div class="tab-pane ">
                                <!--END ALL PRODUCTS-->
                                
                                <div class="title"><h3>Sản phẩm bán chạy</h3></div>
                                    <div class="list_products">
                                     
                    
                                        <div class="product ">
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
                    
                                        <div class="product ">
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
                    
                                        <div class="product ">
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
                    
                                        <div class="product ">
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
                    
                                        <div class="product ">
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
                                    <div class="page_numbers">
                                        <span><a href="#">1</a></span>
                                        <span><a href="#">2</a></span>
                                        <span><a href="#">3</a></span>
                                        <span><a href="#">4</a></span>
                                        <span><a href="#">5</a></span>
                                    </div>
                    
                                </div>
                                <!-- end best seller -->


                                <div class="tab-pane ">
                                
                                        <div class="title"><h3>Sản phẩm khuyến mãi</h3></div>
                                        <div class="list_products">
                                         
                        
                                            <div class="product ">
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
                        
                                            <div class="product ">
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
                        
                                            <div class="product ">
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
                        
                                            <div class="product ">
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
                        
                                            <div class="product ">
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
                                        
                                        <div class="page_numbers">
                                            <span><a href="#">1</a></span>
                                            <span><a href="#">2</a></span>
                                            <span><a href="#">3</a></span>
                                            <span><a href="#">4</a></span>
                                            <span><a href="#">5</a></span>
                                        </div>

                                    </div>


                                    <!-- END SALE PRODUCT -->
                            <!-- <div class="tab-pane">
                            <h2>Vue.js</h2>
                            <p>Vue (pronounced /vjuː/, like view) is a progressive framework for building user interfaces. Unlike other monolithic frameworks, Vue is designed from the ground up to be incrementally adoptable. </p>
                            </div> -->
                        </div>
                    </div>
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

