// Swiper 1: Banner
var swiperBanner = new Swiper(".mySwiper.banner", {
  spaceBetween: 30,
  centeredSlides: true,
  loop: true,
  autoplay: {
    delay: 5000,
    disableOnInteraction: false,
  },
  pagination: {
    el: ".mySwiper.banner .swiper-pagination",
    clickable: true,
  },
  navigation: {
    nextEl: ".mySwiper.banner .swiper-button-next",
    prevEl: ".mySwiper.banner .swiper-button-prev",
  },
  speed: 500,
});

// Swiper 2: Category
var swiperCategory = new Swiper(".mySwiper.category", {
  cssMode: true,
  navigation: {
    nextEl: ".mySwiper.category .swiper-button-next",
    prevEl: ".mySwiper.category .swiper-button-prev",
  },
  pagination: {
    el: ".swiper-pagination",
  },
  mousewheel: true,
  keyboard: true,
  slidesPerView: 1,
  slidesPerGroup: 1,
  spaceBetween: 10,
  loop: true,
  autoplay: {
    delay: 3000,
    disableOnInteraction: false,
  },
  breakpoints: {
    576: { slidesPerView: 3 },
    768: { slidesPerView: 4 },
    992: { slidesPerView: 5 },
  },
});

// Swiper 3 & 4: Product Detail Slide
var swiperProductThumbs = new Swiper(".mySwiper.product_detail_slide", {
  spaceBetween: 10,
  slidesPerView: 4,
  freeMode: true,
  watchSlidesProgress: true,
});
var swiperProductMain = new Swiper(".mySwiper2.product_detail_slide", {
  spaceBetween: 10,
  navigation: {
    nextEl: ".swiper-button-next",
    prevEl: ".swiper-button-prev",
  },
  thumbs: {
    swiper: swiperProductThumbs,
  },
  autoplay: {
    delay: 3000,
    disableOnInteraction: false,
  },
});

// Swiper 5: Store Product
var swiperStore = new Swiper(".mySwiper.store_product", {
  cssMode: true,
  navigation: {
    nextEl: ".mySwiper.store_product .swiper-button-next",
    prevEl: ".mySwiper.store_product .swiper-button-prev",
  },
  pagination: {
    el: ".swiper-pagination",
  },
  mousewheel: true,
  keyboard: true,
  slidesPerView: 1,
  slidesPerGroup: 1,
  spaceBetween: 10,
  autoplay: {
    delay: 3000,
    disableOnInteraction: false,
  },
  breakpoints: {
    576: { slidesPerView: 3 },
    768: { slidesPerView: 4 },
    992: { slidesPerView: 5 },
  },
});

// Swiper 6: Newest Products
var swiperNewest = new Swiper(".newest_products .mySwiper", {
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
    nextEl: ".newest_products .mySwiper .swiper-button-next",
    prevEl: ".newest_products .mySwiper .swiper-button-prev",
  },
  speed: 1000,
  slidesPerView: 1,
  slidesPerGroup: 1,
  breakpoints: {
    576: { slidesPerView: 2 },
    768: { slidesPerView: 3 },
    992: { slidesPerView: 3 },
  },
});

// Sự kiện cuộn với debounce
function debounce(func, wait) {
  let timeout;
  return function executedFunction(...args) {
    const later = () => {
      clearTimeout(timeout);
      func(...args);
    };
    clearTimeout(timeout);
    timeout = setTimeout(later, wait);
  };
}

window.addEventListener("scroll", debounce(() => {
  document
    .querySelector("header")
    ?.classList.toggle("window-scroll", window.scrollY > 0);
}, 100));

 