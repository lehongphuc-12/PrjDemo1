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

const login_form = document.querySelector("#form-1");
const register_form = document.querySelector("#form-2");
const to_login = document.querySelector("#to-login");
const to_register = document.querySelector("#to-register");

console.log(login_form)
console.log(register_form)

to_login.addEventListener("click", (event) => {
    event.preventDefault(); // Ngăn chặn hành động mặc định của thẻ <a>
    console.log("Switch to login");
    login_form.classList.add("active");
    register_form.classList.remove("active");
});

to_register.addEventListener("click", (event) => {
    event.preventDefault();
    console.log("Switch to register");
    login_form.classList.remove("active");
    register_form.classList.add("active");
});


//DROP DOWN CATEGORy
function toggleDropdown(element) {

            const categoryList = document.querySelectorAll(".category");
            categoryList.forEach(category => {
                if (category !== element.parentElement) {
                    category.classList.remove('active');
                }
            })

            let category = element.parentElement;
            category.classList.toggle('active');
        }