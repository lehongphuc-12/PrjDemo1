<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Thêm Sản Phẩm</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <style>
 body {
    font-family: Arial, sans-serif;
    background-color: #f9f9f9;
}

.form-container {
    margin: 10px auto;
    background-color: #fff;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    width: 800px;
}

h2 {
    text-align: center;
    margin-bottom: 20px;
    font-size: 24px;
}

/* Form layout */
.form-container form {
    display: grid;
    grid-template-columns: 1fr 1fr; /* Two columns */
    gap: 20px;
}

/* Form group styling */
.form-group {
    display: flex;
    flex-direction: column;
    margin-bottom: 15px;
}

.form-group label {
    font-weight: bold;
    margin-bottom: 5px;
}

.form-group input[type="text"],
.form-group input[type="number"],
.form-group textarea,
.form-group select,
.form-group input[type="file"] {
    width: 100%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}

.form-group textarea {
    height: 60px;
    resize: vertical;
}

.form-group select {
    appearance: none;
    background-color: #fff;
    cursor: pointer;
    padding-right: 30px;
    background-image: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 24 24"><path fill="black" d="M7 10l5 5 5-5z"/></svg>');
    background-repeat: no-repeat;
    background-position: right 10px center;
}

/* Assign fields to columns */
.left-column {
    grid-column: 1;
}

.right-column {
    grid-column: 2;
}

/* Tạo container cho các file input để xếp 2x2 */
.image-upload-group {
    display: grid;
    grid-template-columns: 1fr 1fr; /* 2 cột */
    gap: 15px;
}

/* Đảm bảo mỗi form-group trong image-upload-group không bị ảnh hưởng margin */
.image-upload-group .form-group {
    margin-bottom: 0;
}

/* Submit button */
.submit-btn {
    background-color: #28a745;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    width: 100%;
    font-size: 16px;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-top: 20px;
    grid-column: 1 / span 2; /* Span cả hai cột */
}

.submit-btn:hover {
    background-color: #218838;
}

.submit-btn i {
    margin-right: 5px;
}

/* Responsive adjustments */
@media (max-width: 800px) {
    .form-container {
        width: 90%;
    }

    .form-container form {
        grid-template-columns: 1fr; /* Stack columns trên màn hình nhỏ */
    }

    .left-column,
    .right-column {
        grid-column: 1;
    }

    .image-upload-group {
        grid-template-columns: 1fr; /* Stack file inputs trên màn hình nhỏ */
    }

    .submit-btn {
        grid-column: 1;
    }
}
        </style>
    </head>
    <body>   
 <div class="form-container">
    <h2>Thêm Sản Phẩm Mới</h2>
    <form action="addProduct" method="POST" enctype="multipart/form-data">
        <div class="left-column">
            <div class="form-group">
                <label for="productName">Tên Sản Phẩm:</label>
                <input type="text" id="productName" name="productName" maxlength="100" required>
            </div>
            <div class="form-group">
                <label for="description">Mô Tả:</label>
                <textarea id="description" name="description"></textarea>
            </div>
            <div class="form-group">
                <label for="price">Giá:</label>
                <input type="number" id="price" name="price" step="0.01" min="0" required>
            </div>
            <div class="form-group">
                <label for="quantity">Số Lượng:</label>
                <input type="number" id="quantity" name="quantity" step="1" min="0" required>
            </div>
        </div>
        <div class="right-column">
            <div class="form-group">
                <label for="productGroupId">Nhóm Sản Phẩm:</label>
                <select id="productGroupId" name="productGroupId" required>
                    <option value="">Chọn Nhóm</option>
                    <c:forEach var="cateGroup" items="${cateGroupList}">
                        <option value="${cateGroup.groupID}">${cateGroup.groupName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="productTypeId">Loại Sản Phẩm:</label>
                <select id="productTypeId" name="productTypeId" required>
                    <option value="">Chọn Loại</option>
                </select>
            </div>
            <div class="form-group">
                <label for="cityId">Thành Phố:</label>
                <select id="cityId" name="cityId" required>
                    <option value="">Chọn Thành Phố</option>
                     <c:forEach var="city" items="${cityList}">
                        <option value="${city.cityID}">${city.cityName}</option>
                    </c:forEach>
                </select>
            </div>
            <!-- Nhóm các trường upload ảnh vào một container -->
            <div class="image-upload-group">
                <div class="form-group">
                    <label for="mainImage">Ảnh Trưng Bày:</label>
                    <input type="file" id="mainImage" name="mainImage" accept="image/*" required onchange="previewImage(this, 'subImage1Preview')">
                    <div id="subImage1Preview" style="margin-top: 10px;"></div>
                </div>
                <div class="form-group">
                    <label for="subImage1">Ảnh Con 1:</label>
                    <input type="file" id="subImage1" name="subImage1" accept="image/*"onchange="previewImage(this, 'subImage2Preview')">
                    <div id="subImage2Preview" style="margin-top: 10px;"></div>
                </div>
                <div class="form-group">
                    <label for="subImage2">Ảnh Con 2:</label>
                    <input type="file" id="subImage2" name="subImage2" accept="image/*" onchange="previewImage(this, 'subImage3Preview')">
                    <div id="subImage3Preview" style="margin-top: 10px;"></div>
                </div>
                <div class="form-group">
                    <label for="subImage3">Ảnh Con 3:</label>
                    <input type="file" id="subImage3" name="subImage3" accept="image/*" onchange="previewImage(this, 'subImage4Preview')">
                    <div id="subImage4Preview" style="margin-top: 10px;"></div>
                </div>
            </div>
        </div>
        <button type="button" class="submit-btn" onclick="addProduct()">
            <i class="fas fa-plus"></i> Thêm Sản Phẩm
        </button>
    </form>
</div>
    </body>
</html>