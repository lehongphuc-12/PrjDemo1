console.log("FILTER");

function filter(ID, search, page, action, filter) {
        console.log("ID:", ID, "Search:", search, "Page:", page, "Action:", action, "Filter:", filter);
        // Kích hoạt sự kiện resize một cách thủ công
        window.dispatchEvent(new Event('resize'));
        console.log("WINDOWN RESIZE");
        jQuery.ajax({
            url: "/demo1/filters",
            type: "GET",
            data: {
                ID: ID,
                search: search,
                pageNumber: page,
                action: action,
                filter: filter
            },
            success: function (data) {
                if (data && data.trim() !== "") {
                    jQuery("#search_results").html(data);
                } else {
                    jQuery("#search_results").html("<p>Không có dữ liệu trả về.</p>");
                }
            },
            error: function (xhr, status, error) {
                console.error("Error:", error, "Status:", status);
                jQuery("#search_results").html("<p>Đã xảy ra lỗi khi tải dữ liệu. Vui lòng thử lại.</p>");
            }
        });
    }