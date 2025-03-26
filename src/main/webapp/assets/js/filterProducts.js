import {attachEventBuyHandlers} from './product_detail.js'

console.log("FILTER");


function filterProducts(ID="", search="", page=1, action="", filterType="") {
    console.log("ID:", ID, "Search:", search, "Page:", page, "Action:", action, "Filter:", filterType);
    window.dispatchEvent(new Event('resize'));
    console.log("WINDOW RESIZE");

    jQuery.ajax({
        url: "/demo1/filters",
        type: "GET",
        data: {
            ID: ID,
            search: search,
            pageNumber: page,
            action: action,
            filterType: filterType
        },
        success: function (data) {
            if (data && data.trim() !== "") {
                jQuery("#search_results").html(data);
                attachEventBuyHandlers(); // 🚀 Gọi lại hàm gán sự kiện sau khi render xong
            } else {
                jQuery("#search_results").html("<p>Không có dữ liệu trả về.</p>");
            }
        },
        error: function (xhr, status, error) {
            console.error("Error:", error, "Status:", status);
            jQuery("#search_results").html("<p>Đã xảy ra lỗi khi tải dữ liệus. Vui lòng thử lại.</p>");
        }
    });
}

$(document).ready(function () {
    console.log("FILTER READY");
    attachEventBuyHandlers();
});

// ✅ Gán hàm vào `window` để có thể gọi từ HTML
window.filterProducts = filterProducts;