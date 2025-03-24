<%-- 
    Document   : RateProduct
    Created on : Mar 22, 2025
    Author     : Grok & YourName
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đánh giá sản phẩm</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        .container {
            max-width: 600px;
            margin: 20px auto;
            padding: 20px;
            background-color: white;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            border-radius: 8px;
        }
        h2 {
            color: #32CD32; /* Xanh lá chuối */
            text-align: center;
        }
        .product-info {
            margin-bottom: 20px;
            text-align: center;
        }
        .rating {
            margin: 20px 0;
            text-align: center;
        }
        .rating .star {
            font-size: 30px;
            color: #ccc; /* Màu xám mặc định */
            cursor: pointer;
            transition: color 0.2s;
        }
        .rating .star:hover,
        .rating .star.active {
            color: #FFC107; /* Vàng cho sao được chọn */
        }
        .comment-box {
            margin: 20px 0;
        }
        textarea {
            width: 100%;
            height: 100px;
            padding: 10px;
            font-size: 16px;
            border: 1px solid #32CD32;
            border-radius: 4px;
            resize: vertical;
            background-color: #f0f7f0;
        }
        textarea:focus {
            outline: none;
            border-color: #228B22;
            background-color: #e0f0e0;
        }
        .submit-btn {
            background-color: #FFC107; /* Màu vàng */
            color: black;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            display: block;
            margin: 0 auto;
            font-size: 16px;
        }
        .submit-btn:hover {
            background-color: #E0A800;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Đánh giá sản phẩm</h2>
        
        <!-- Thông tin sản phẩm -->
        <div class="product-info">
            <h3>${product.productName}</h3>
            <p>Mã đơn hàng: ${orderDetail.orderID.orderID}</p>
        </div>

        <!-- Đánh giá sao -->
        <div class="rating">
            <span class="star" data-value="1"><i class="fas fa-star"></i></span>
            <span class="star" data-value="2"><i class="fas fa-star"></i></span>
            <span class="star" data-value="3"><i class="fas fa-star"></i></span>
            <span class="star" data-value="4"><i class="fas fa-star"></i></span>
            <span class="star" data-value="5"><i class="fas fa-star"></i></span>
        </div>

        <!-- Bình luận -->
        <div class="comment-box">
            <label for="comment">Bình luận của bạn:</label>
            <textarea id="comment" name="comment" placeholder="Nhập bình luận của bạn..."></textarea>
        </div>

        <!-- Nút gửi -->
        <button class="submit-btn" onclick="submitRating()">Gửi đánh giá</button>
    </div>

    <script>
        let selectedRating = 0;

        // Xử lý chọn sao
        document.querySelectorAll('.star').forEach(star => {
            star.addEventListener('click', function() {
                selectedRating = this.getAttribute('data-value');
                document.querySelectorAll('.star').forEach(s => {
                    if (s.getAttribute('data-value') <= selectedRating) {
                        s.classList.add('active');
                    } else {
                        s.classList.remove('active');
                    }
                });
            });
        });

        // Gửi đánh giá qua AJAX
        function submitRating() {
            if (selectedRating === 0) {
                alert("Vui lòng chọn số sao để đánh giá!");
                return;
            }

            const comment = document.getElementById('comment').value.trim();
            const orderDetailId = ${orderDetail.orderDetailID};
            const productId = ${product.productID};

            const formData = new FormData();
            formData.append('orderDetailId', orderDetailId);
            formData.append('productId', productId);
            formData.append('rating', selectedRating);
            formData.append('comment', comment);

            fetch('${pageContext.request.contextPath}/rateProduct', {
                method: 'POST',
                body: formData
            })
            .then(response => response.text())
            .then(result => {
                if (result === 'success') {
                    alert('Cảm ơn bạn đã đánh giá!');
                    window.location.href = '${pageContext.request.contextPath}/orders'; // Quay lại trang đơn hàng
                } else {
                    alert('Có lỗi xảy ra khi gửi đánh giá. Vui lòng thử lại.');
                }
            })
            .catch(error => {
                console.error('Lỗi:', error);
                alert('Đã xảy ra lỗi. Vui lòng thử lại sau.');
            });
        }
    </script>
</body>
</html>