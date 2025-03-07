var swiper = new Swiper(".mySwiper.banner", {
  spaceBetween: 30,
  centeredSlides: true,
  loop: true,
  autoplay: {
    delay: 2500,
    disableOnInteraction: false,
  },
  pagination: {
    el: ".mySwiper.banner .swiper-pagination",
    clickable: true,
  },
  navigation: {
    nextEl: " .mySwiper.banner .swiper-button-next",
    prevEl: ".mySwiper.banner .swiper-button-prev",
  },
  speed: 500,
});

var swiper = new Swiper(".mySwiper.category", {
  cssMode: true,
  navigation: {
    nextEl: ".mySwiper.category .swiper-button-next",
    prevEl: " .mySwiper.category .swiper-button-prev",
  },
  pagination: {
    el: ".swiper-pagination",
  },
  mousewheel: true,
  keyboard: true,
  slidesPerView: 5, // Hiển thị 5 slide mỗi lần
  slidesPerGroup: 1, // Chuyển 1 slide mỗi lần
  spaceBetween: 10, // Khoảng cách giữa các slide (tuỳ chỉnh)
  loop: true, // Hành động chuyển slide cho khói lặp
});

var swiper = new Swiper(".mySwiper.product_detail_slide", {
  spaceBetween: 10,
  slidesPerView: 4,
  freeMode: true,
  watchSlidesProgress: true,
});
var swiper2 = new Swiper(".mySwiper2.product_detail_slide", {
  spaceBetween: 10,
  navigation: {
    nextEl: ".swiper-button-next",
    prevEl: ".swiper-button-prev",
  },
  thumbs: {
    swiper: swiper,
  },
});

var swiper = new Swiper(".mySwiper.store_product", {
  cssMode: true,
  navigation: {
    nextEl: ".mySwiper.store_product .swiper-button-next",
    prevEl: " .mySwiper.store_product .swiper-button-prev",
  },
  pagination: {
    el: ".swiper-pagination",
  },
  mousewheel: true,
  keyboard: true,
  slidesPerView: 5, // Hiển thị 5 slide mỗi lần
  slidesPerGroup: 1, // Chuyển 1 slide mỗi lần
  spaceBetween: 10, // Khoảng cách giữa các slide (tuỳ chỉnh)
});

window.addEventListener("scroll", () => {
  document
    .querySelector("header")
    .classList.toggle("window-scroll", window.scrollY > 0);
});

var swiper = new Swiper(".newest_products .mySwiper", {
  spaceBetween: 30,
  loop: true,
  autoplay: {
    delay: 2000,
    disableOnInteraction: false,
  },
  pagination: {
    el: ".newest_products .mySwiper .swiper-pagination",
    clickable: true,
  },
  navigation: {
    nextEl: " .newest_products .mySwiper .swiper-button-next",
    prevEl: ".newest_products .mySwiper .swiper-button-prev",
  },
  speed: 1000,
  slidesPerView: 3, // Hiển thị 5 slide mỗi lần
  slidesPerGroup: 1,
  loop: true,
});
