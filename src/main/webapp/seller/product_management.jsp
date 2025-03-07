
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    
    
<!--head-->
     <jsp:include page="../includes/head.jsp"></jsp:include>
     
     
<body>
    
       <!--HEADER-->
     <jsp:include page="../includes/header.jsp"></jsp:include>
    
    <div class="container">
        <h2>Quản Lý Sản Phẩm</h2>
        <button class="add-btn btn">Thêm Sản Phẩm</button>
        <table>
            <thead>
                <tr>
                    <th>Hình Ảnh</th>
                    <th>Tên Sản Phẩm</th>
                    <th>Giá</th>
                    <th>Hành Động</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><img src="product1.jpg" alt="Sản phẩm 1"></td>
                    <td>Sản phẩm 1</td>
                    <td>100,000 VND</td>
                    <td>
                        <button class="edit-btn btn">Chỉnh Sửa</button>
                        <button class="delete-btn btn">Xóa</button>
                    </td>
                </tr>
                <tr>
                    <td><img src="product2.jpg" alt="Sản phẩm 2"></td>
                    <td>Sản phẩm 2</td>
                    <td>200,000 VND</td>
                    <td>
                        <button class="edit-btn btn">Chỉnh Sửa</button>
                        <button class="delete-btn btn">Xóa</button>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
     
     
        <!--FOOTER-->
     <jsp:include page="../includes/footer.jsp"></jsp:include>
     
</body>
</html>
