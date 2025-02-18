<%-- Document : listProduct Created on : Feb 18, 2025, 6:02:45 PM Author : ASUS --%>

    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>JSP Page</title>
            </head>

            <body>
                <h1>List Product</h1>
                <%-- Thiết lập số sản phẩm hiển thị trên mỗi trang --%>
                    <c:set var="pageSize" value="10" />
                    <c:set var="currentPage" value="${param.page != null ? param.page : 1}" />
                    <c:set var="start" value="${(currentPage - 1) * pageSize}" />
                    <c:set var="end" value="${start + pageSize}" />
                    <c:set var="totalProducts" value="${products.size()}" />
                    <c:set var="totalPages" value="${Math.ceil(totalProducts / pageSize)}" />
                    <table border="1">

                        <tr>
                            <th>Images</th>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Price</th>
                            <th>Description</th>
                            <th>quantity</th>
                        </tr>
                        <c:forEach var="product" items="${products}" varStatus="status">
                            <c:if test="${status.index >= start && status.index < end}">
                                <tr>
                                    <td><img src="${not empty product.images ? product.images.get(0).imageURL : 'Không có ảnh'}"
                                            alt="" width="100px" /></td>
                                    <td>${product.productID}</td>
                                    <td>${product.productName}</td>
                                    <td>${product.price}</td>
                                    <td>${product.description}</td>
                                    <td>${product.quantity}</td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </table>
                    <!-- Phân trang -->
                    <div>
                        <c:if test="${currentPage > 1}">
                            <a href="products?page=${currentPage - 1}">Previous</a>
                        </c:if>
                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <a href="products?page=${i}">${i}</a>
                        </c:forEach>
                        <c:if test="${currentPage < totalPages}">
                            <a href="products?page=${currentPage + 1}">Next</a>
                        </c:if>
                    </div>
            </body>

            </html>