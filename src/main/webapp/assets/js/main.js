console.log("MAIN.JS");


const $ = document.querySelector.bind(document);
const $$ = document.querySelectorAll.bind(document);


const a = document.querySelector;

const tabs = $$('.tab-item');
const panes = $$('.tab-pane');

const tabActive = $(".tab-item.active");
const line = $(".tabs .line");
//
if (line) {
    line.style.left = `${tabActive.offsetLeft}px`;
    line.style.width = `${tabActive.offsetWidth}px`;
}


tabs.forEach((tab, index) => {
    tab.onclick = function() {
        const pane = panes[index];
        $(".tab-pane.active").classList.remove("active");
        pane.classList.add("active");


        $(".tab-item.active").classList.remove("active");
        this.classList.add("active");

        line.style.left = `${tab.offsetLeft}px`;
        line.style.width = `${tab.offsetWidth}px`;
    }
});

//login

//const login_form = document.querySelector("#form-1");
//const register_form = document.querySelector("#form-2");
//const to_login = document.querySelector("#to-login");
//const to_register = document.querySelector("#to-register");
//
//console.log(login_form)
//console.log(register_form)
//
//to_login.addEventListener("click", (event) => {
//    event.preventDefault(); // Ngăn chặn hành động mặc định của thẻ <a>
//    console.log("Switch to login");
//    login_form.classList.add("active");
//    register_form.classList.remove("active");
//});
//
//to_register.addEventListener("click", (event) => {
//    event.preventDefault();
//    console.log("Switch to register");
//    login_form.classList.remove("active");
//    register_form.classList.add("active");
//});
//
//
//DROP DOWN CATEGORy
 function toggleDropdown(element) {
    console.log("TOGGLE DOWN");
    let activeCategory = document.querySelector(".category.active");
    let category = element.parentElement;
    let list = category.querySelector(".category-list");

    if (activeCategory && activeCategory !== category) {
        activeCategory.classList.remove("active");
        activeCategory.querySelector(".category-list").style.maxHeight = "0";
    }

    if (category.classList.contains("active")) {
        list.style.maxHeight = "0";
        category.classList.remove("active");
    } else {
        list.style.maxHeight = list.scrollHeight + "px";
        category.classList.add("active");
    }
}

// sự kiện click cho tất cả các category con
document.addEventListener("DOMContentLoaded", function () {
    
    console.log("LOAD CATE")
    let categoryLinks = document.querySelectorAll(".category-list a");

    categoryLinks.forEach(link => {
        link.addEventListener("click", function () {
            // Loại bỏ lớp active từ tất cả các category con
            categoryLinks.forEach(item => item.classList.remove("text-primary"));
            console.log(this)
            // Thêm lớp active cho mục được chọn
            this.classList.add("text-primary");
        });
    });
});

// sự kiện click cho tất cả các filter event

document.addEventListener("DOMContentLoaded", function () {
    
    console.log("LOAD FILTERS")
    let categoryLinks = document.querySelectorAll(".criterias a p");

    categoryLinks.forEach(link => {
        link.addEventListener("click", function () {
            categoryLinks.forEach(item => item.classList.remove("text-primary"));
            console.log(this)

            this.classList.add("text-primary");
        });
    });
});
