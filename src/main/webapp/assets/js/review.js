console.log("REVIEW JS");


 $(document).ready(function () {
     
//     CREATE REVIEW
    $(document).on('click', '.btn-submit-review', function () {
        console.log("SUBMIT REVIEW");

        let productID = $('#productID').val();
        let rating = $(this).closest('.create-review').find('.fa-star.checked').length;
        let comment = $('#comment').val();

        if (rating === 0) {
            alert("Vui lòng chọn số sao!");
            return;
        }

        console.log("ID:", productID, "Rating:", rating, "Comment:", comment);

        $.ajax({
            url: contextPath + '/review',
            type: 'POST',
            data: {
                action: 'createReview',
                productID: productID,
                rating: rating,
                comment: comment
            },
            success: function(response) {
                console.log("Response từ Servlet:", response);
                $('.list-reviews').html(response); // Cập nhật danh sách đánh giá
                $('#comment').val(""); // Đặt lại nội dung ô nhập nhận xét
                $('.fa-star').removeClass('checked'); // Bỏ chọn sao sau khi gửi đánh giá
                $('.review-form.create-review').css('display', 'none');
                $('.review-success').html("Bạn đã đánh giá sản phẩm thành công");
                $('.has-reviewed').css('display', 'none');
               
            },
            error: function(xhr) {
                console.error("Lỗi:", xhr.responseText);
                alert(xhr.responseText);
            }
        });
    });
    
//    DELETE REVIEW
    $(document).on('click', '.delete-review', function () {
        event.preventDefault(); // Ngăn chặn chuyển trang nếu là thẻ <a>
        
        console.log("DELETE REVIEW");

        let productID = $('#productID').val();
        let reviewID = $(this).data('reviewid'); // Lấy giá trị data-id
        console.log('Review ID:', reviewID);
        

        console.log("ID:", productID, "ReviewID: ",reviewID);

        $.ajax({
            url: contextPath + '/review',
            type: 'POST',
            data: {
                action: 'deleteReview',
                productID: productID,
                reviewID: reviewID
            },
            success: function(response) {
                console.log("Response từ Servlet:", response);
                $('.list-reviews').html(response); // Cập nhật danh sách đánh giá
                $('#comment').val(""); // Đặt lại nội dung ô nhập nhận xét
                $('.fa-star').removeClass('checked'); // Bỏ chọn sao sau khi gửi đánh giá
                $('.review-form').css('display', 'block');
                $('.has-reviewed').css('display', 'none');
                $('.review-success').html("Bạn đã xoá đánh giá sản phẩm thành công");
               
            },
            error: function(xhr) {
                console.error("Lỗi:", xhr.responseText);
                alert(xhr.responseText);
            }
        });
    });

//UPDATE REVIEW
    
$(document).on("click", ".btn-confirm-update-review", function (e) {
    e.preventDefault(); // Ngăn chặn gửi form mặc định (nếu cần)

    // Tìm form cha gần nhất
    let updateForm = $(this).closest(".update-review-container");

    // Lấy dữ liệu từ input và textarea
    let productID = updateForm.find("#productID").val();
    let reviewID = updateForm.find("#reviewID").val();
    let rating = updateForm.find("#rating").val();
    let comment = updateForm.find("#comment").val().trim();

    // Kiểm tra dữ liệu đã lấy được
    console.log("Product ID:", productID," ,Review ID:", reviewID," ,Rating:", rating," ,Comment:", comment);
    
    $.ajax({
            url: contextPath + '/review',
            type: 'POST',
            data: {
                action: 'updateReview',
                productID: productID,
                reviewID: reviewID,
                rating:rating,
                comment:comment
            },
            success: function(response) {
                console.log("Response từ Servlet:", response);
                $('.list-reviews').html(response); // Cập nhật danh sách đánh giá
                $('.has-reviewed').css('display', 'none');
                $('.review-success').html("Bạn đã cập nhật đánh giá sản phẩm thành công");
                updateForm.css('display','none');
            },
            error: function(xhr) {
                console.error("Lỗi:", xhr.responseText);
                alert(xhr.responseText);
            }
        });

});

    
    
    
//    UPDATE FORM SHOWED

 $(document).on("click", ".update-review", function (e) {
    e.preventDefault();

    // Lấy reviewID từ thuộc tính data
    let reviewID = $(this).data("reviewid");

    // Lấy nội dung của đánh giá hiện tại
    let reviewText = $(this).closest(".review-action").find("#comment").val().trim();
    let currentRating = parseInt($(this).closest(".review-action").find("#rating").val().trim()) || 0;
    
    console.log("ID: ",reviewID, "text: ",reviewText, "rating: ",currentRating);

    // Đưa dữ liệu vào form cập nhật
    let updateForm = $(".update-review-container"); // Giữ đúng phạm vi form
    if(!updateForm){
        console.log("KO TIM THAY UPDATE FORM");
    }else{
        console.log(updateForm.length);
    }
    
    updateForm.find("#comment").val(reviewText);
    updateForm.find("#rating").val(currentRating);
    updateForm.find("#reviewID").val(reviewID);

    // Xóa trạng thái cũ và cập nhật lại số sao
    updateForm.find(".star-rating span").removeClass("checked");
    updateForm.find(".star-rating span").each(function () {
        let starValue = $(this).data("value");
        if (starValue <= currentRating) {
            $(this).addClass("checked");
        }
    });

    // Hiển thị form cập nhật
    updateForm.show();

    // Xử lý ẩn form khi click ra ngoài
    setTimeout(function () { // Tránh ẩn ngay lập tức
        $(document).on("click.updateForm", function (event) {
            if (!$(event.target).closest(".update-review-container, .update-review").length) {
                updateForm.hide();
                $(document).off("click.updateForm"); // Xóa sự kiện sau khi ẩn
            }
        });
    }, 100);
});

// Cập nhật số sao khi click vào sao trong form cập nhật
$(document).on("click", ".update-review-container .star-rating span", function () {
    let selectedValue = $(this).data("value");
    let updateForm = $(this).closest(".update-review-container");

    updateForm.find("#rating").val(selectedValue);

    // Cập nhật trạng thái các sao
    updateForm.find(".star-rating span").removeClass("checked");
    updateForm.find(".star-rating span").each(function () {
        if ($(this).data("value") <= selectedValue) {
            $(this).addClass("checked");
        }
    });
});

    
    $(document).on("click", ".btn-confirm-update-review", function (e) {
        e.preventDefault(); // Ngăn chặn hành vi mặc định của button nếu nó nằm trong form

        let updateForm = $(this).closest('.update-review-form'); // Tìm form gần nhất
        if (updateForm.length) {
            updateForm.hide(); // Ẩn form cập nhật đánh giá
        } else {
            console.warn("Không tìm thấy form cập nhật!");
        }
    });

    
    
});