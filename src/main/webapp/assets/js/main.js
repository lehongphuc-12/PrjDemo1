console.log("MAIN.JS");





const tabs = document.querySelectorAll.bind(document)('.tab-item');
const panes = document.querySelectorAll.bind(document)('.tab-pane');

const tabActive = document.querySelector.bind(document)(".tab-item.active");
const line = document.querySelector.bind(document)(".tabs .line");
//
if (line) {
    line.style.left = `${tabActive.offsetLeft}px`;
    line.style.width = `${tabActive.offsetWidth}px`;
}


tabs.forEach((tab, index) => {
    tab.onclick = function() {
        const pane = panes[index];
        document.querySelector(".tab-pane.active").classList.remove("active");
        pane.classList.add("active");


        document.querySelector(".tab-item.active").classList.remove("active");
        this.classList.add("active");

        line.style.left = `${tab.offsetLeft}px`;
        line.style.width = `${tab.offsetWidth}px`;
    }
});

//login

const login_form = document.querySelector("#form-1");
const register_form = document.querySelector("#form-2");
const to_login = document.querySelector("#to-login");
const to_register = document.querySelector("#to-register");


to_login?.addEventListener("click", (event) => {
    event.preventDefault(); // Ngăn chặn hành động mặc định của thẻ <a>
    console.log("Switch to login");
    login_form.classList.add("active");
    register_form.classList.remove("active");
});

to_register?.addEventListener("click", (event) => {
    event.preventDefault();
    console.log("Switch to register");
    login_form.classList.remove("active");
    register_form.classList.add("active");
});

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


//GET COOKIE

document.addEventListener("DOMContentLoaded", function() {
            let email = getCookie("remember_email");
            let password = getCookie("remember_password");

            if (email) {
                document.getElementById("email-login").value = email;
            }
            if (password) {
                document.getElementById("password-login").value = password;
                document.getElementById("remember_me").checked = true;
            }
        });

        function getCookie(name) {
            let cookies = document.cookie.split("; ");
            for (let i = 0; i < cookies.length; i++) {
                let parts = cookies[i].split("=");
                if (parts[0] === name) {
                    return decodeURIComponent(parts[1]);
                }
            }
            return "";
        }
        
        
//CHECK LIST_PRODUCT

    
const listProducts = document.querySelector(".list_products");

function updateGrid() {
    if (listProducts) {
        console.log(listProducts)
        console.log("Width:", listProducts.clientWidth); // Debug
        if (listProducts.clientWidth < 950) {
            listProducts.style.gridTemplateColumns = 'repeat(4, 1fr)';
        } else {
            listProducts.style.gridTemplateColumns = 'repeat(5, 1fr)';
        }
    }
}

// Gọi ngay khi trang load
updateGrid();

// Lắng nghe sự kiện resize của window
window.addEventListener("resize", updateGrid);
document.addEventListener("DOMContentLoaded",updateGrid);
    
