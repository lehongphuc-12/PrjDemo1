<%-- 
    Document   : DonHang
    Created on : Mar 11, 2025, 9:32:41 AM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Xem Đơn Hàng</title>
        <style>
            .pagination {
                display: none; /* Ẩn mặc định */
                justify-content: center;
                gap: 10px;
                margin: 20px 0;
            }
            .pagination.active {
                display: flex !important; /* Hiện khi được chọn */
            }
            .pagination a {
                padding: 8px 12px;
                text-decoration: none;
                color: #28a745;
                border: 1px solid #ced4da;
                border-radius: 5px;
                transition: background-color 0.3s;
            }
            .pagination a:hover {
                background-color: #28a745;
                color: white;
                cursor: pointer;
            }
            .pagination a.active {
                background-color: #28a745;
                color: white;
                border-color: #28a745;
            }
            .pagination a.disabled {
                color: #ced4da;
                pointer-events: none;
            }
            .container {
                max-width: 1200px;
                margin: 0 auto;
            }
            .filter {
                margin-bottom: 20px;
            }
            select {
                padding: 8px;
                font-size: 16px;
                border: 1px solid #32CD32; /* Xanh lá chuối */
                background-color: #f0f7f0;
                color: #2f4f4f;
                border-radius: 4px;
            }
            select:hover {
                background-color: #e0f0e0;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                background-color: white;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                border-radius: 8px;
                overflow: hidden;
                display: none; /* Ẩn mặc định */
            }
            table.active {
                display: table !important;
                ; /* Hiện khi được chọn */
            }
            th, td {
                padding: 12px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }
            th {
                background-color: #32CD32; /* Xanh lá chuối */
                color: white;
            }
            tr:hover {
                background-color: #e0f0e0; /* Hover xanh nhạt */
            }
            .status {
                font-weight: bold;
            }
            .status.dang-giao {
                color: #FFA500;
            } /* Cam */
            .status.da-giao {
                color: #32CD32;
            } /* Xanh lá */
            .status.da-huy {
                color: #FF0000;
            } /* Đỏ */
            /* Nút chung */
            button {
                color: white;
                border: none;
                padding: 6px 12px;
                border-radius: 4px;
                cursor: pointer;
                display: inline-block; /* Đảm bảo nút hiển thị đồng đều */
                width: 120px; /* Chiều rộng cố định */
                height: 40px; /* Chiều cao cố định */
                line-height: 28px; /* Căn giữa nội dung theo chiều dọc */
                text-align: center; /* Căn giữa nội dung theo chiều ngang */
            }

            /* Nút Hủy đơn */
            .cancel-btn {
                background-color: #EB0029;
            }
            .cancel-btn:hover {
                background-color: #B3001F;
            }

            /* Nút Mua lại */
            .repurchase-btn {
                background-color: #FF8C00;
            }
            .repurchase-btn:hover {
                background-color: #E07B00;
            }

            /* Nút Đã nhận hàng */
            .received-btn {
                background-color: #32CD32;
            }
            .received-btn:hover {
                background-color: #228B22;
            }
            .rating-btn {
                background-color: #FFC107;
                color: black; /* Chữ đen để dễ đọc */
                border: none;
                padding: 6px 12px;
                border-radius: 4px;
                cursor: pointer;
            }
            .rating-btn:hover {
                background-color: #E0A800; /* Đậm hơn khi hover */
            }
            .pagination {
                display: none; /* Ẩn mặc định */
                justify-content: center;
                gap: 10px;
                margin: 20px 0;
            }
            .pagination.active {
                display: flex !important; /* Hiện khi được chọn */
            }
            .pagination a {
                padding: 8px 12px;
                text-decoration: none;
                color: #28a745;
                border: 1px solid #ced4da;
                border-radius: 5px;
                transition: background-color 0.3s;
            }
            .pagination a:hover {
                background-color: #28a745;
                color: white;
                cursor: pointer;
            }
            .pagination a.active {
                background-color: #28a745;
                color: white;
                border-color: #28a745;
            }
            .pagination a.disabled {
                color: #ced4da;
                pointer-events: none;
            }
        </style>
    </head>
    <body>
            <div class="container">
                <h2>Danh Sách Đơn Hàng</h2>
                <div class="filter">
                    <label for="statusFilter">Lọc theo trạng thái: </label>
                    <select id="statusFilter" onchange="filterOrders(this.value)">
                        <option value="all">Tất cả</option>
                        <option value="cho-xu-ly">Chờ xử lý</option>
                        <option value="dang-giao">Đang giao</option>
                        <option value="da-nhan">Đã nhận</option>
                        <option value="da-huy">Đã hủy</option>
                    </select>
                </div>

                <!-- Bảng hiển thị tất cả đơn hàng -->
                <table id="all" class="active">
                    <thead>
                        <tr>
                            <th>Mã Đơn Hàng</th>
                            <th>Tên Sản Phẩm</th>
                            <th>Số Lượng</th>
                            <th>Tổng Tiền</th>
                            <th>Trạng Thái</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="orderDetail" items="${orderList}">
                        <tr>
                            <td>${orderDetail.orderID.orderID}</td>
                            <td><p>${orderDetail.productID.productName}</p></td>
                            <td>${orderDetail.quantity}</td>
                            <td>
                                <fmt:formatNumber value="${orderDetail.price}" type="currency" currencySymbol="VND" />
                            </td>
                            <td class="status ${orderDetail.statusID.statusName eq 'Chờ xử lý' ? 'cho-xu-ly' : orderDetail.statusID.statusName eq 'Đang giao' ? 'dang-giao' : orderDetail.statusID.statusName eq 'Đã nhận' ? 'da-nhan' : 'da-huy'}">
                                ${orderDetail.statusID.statusName}
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${orderDetail.statusID.statusName eq 'Chờ xử lý'}">
                                        <button type="button" onclick="cancelOrder(${orderDetail.orderD1})" class="cancel-btn">Hủy đơn</button>
                                    </c:when>
                                    <c:when test="${orderDetail.statusID.statusName eq 'Đang giao'}">
                                        <button type="button" onclick="confirmReceived(${orderDetail.orderD1})" class="received-btn">Đã nhận</button>
                                    </c:when>
                                    <c:when test="${orderDetail.statusID.statusName eq 'Đã hủy'}">
                                        <button type="button" class="repurchase-btn">
                                            <a href="${pageContext.request.contextPath}/detail?productID=${orderDetail.productID.productID}" style="text-decoration: none">    
                                                Mua lại
                                            </a>
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <!-- Trạng thái 'Đã hủy' hoặc các trạng thái khác không có hành động -->
                                        <button type="button" onclick="rateProduct(${orderDetail.orderD1}, ${orderDetail.productID.productID})" class="rating-btn">
                                            <a href="${pageContext.request.contextPath}/detail?productID=${orderDetail.productID.productID}" style="text-decoration: none">    
                                                Đánh giá
                                            </a>
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="pagination" id="pagination-all" style="display: flex;">
                <c:if test="${totalPage >= 1}">
                    <!-- Nút Previous -->
                    <c:choose>
                        <c:when test="${currentStatus == 'all' && (currentPage == null || currentPage <= 1)}">
                            <a href="#" class="disabled">« Previous</a>
                        </c:when>
                        <c:otherwise>
                            <a onclick="loadSection('donHang&status=all&page=${currentPage-1}')">« Previous</a>
                        </c:otherwise>
                    </c:choose>

                    <!-- Các trang số -->
                    <c:forEach begin="1" end="${totalPage}" var="i">
                        <a class="${currentStatus == 'all' && (currentPage == i || (currentPage == null && i == 1)) ? 'active' : ''}" 
                           onclick="loadSection('donHang&status=all&page=${i}')">${i}</a>
                    </c:forEach>

                    <!-- Nút Next -->
                    <c:choose>
                        <c:when test="${currentStatus == 'all' && (currentPage >= totalPage || (currentPage == null && totalPage == 1))}">
                            <a href="#" class="disabled">Next »</a>
                        </c:when>
                        <c:otherwise>
                            <a onclick="loadSection('donHang&status=all&page=${(currentPage == null ? 1 : currentPage) + 1}')">Next »</a>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </div>
            <!-- Bảng Chờ xử lý -->
            <table id="cho-xu-ly" style="display: none;">
                <thead>
                    <tr>
                        <th>Mã Đơn Hàng</th>
                        <th>Tên Sản Phẩm</th>
                        <th>Số Lượng</th>
                        <th>Tổng Tiền</th>
                        <th>Trạng Thái</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="orderDetail" items="${choXuLyList}">
                        <tr>
                            <td>${orderDetail.orderID.orderID}</td>
                            <td><p>${orderDetail.productID.productName}</p></td>
                            <td>${orderDetail.quantity}</td>
                            <td>
                                <fmt:formatNumber value="${orderDetail.price}" type="currency" currencySymbol="VND" />
                            </td>
                            <td class="status cho-xu-ly">${orderDetail.statusID.statusName}</td>
                            <td><button type="button" onclick="cancelOrder(${orderDetail.orderD1})" class="cancel-btn">Hủy đơn</button></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Bảng Đang giao -->
            <table id="dang-giao" style="display: none;">
                <thead>
                    <tr>
                        <th>Mã Đơn Hàng</th>
                        <th>Tên Sản Phẩm</th>
                        <th>Số Lượng</th>
                        <th>Tổng Tiền</th>
                        <th>Trạng Thái</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="orderDetail" items="${dangGiaoList}">
                        <tr>
                            <td>${orderDetail.orderID.orderID}</td>
                            <td><p>${orderDetail.productID.productName}</p></td>
                            <td>${orderDetail.quantity}</td>
                            <td>
                                <fmt:formatNumber value="${orderDetail.price}" type="currency" currencySymbol="VND" />
                            </td>
                            <td class="status dang-giao">${orderDetail.statusID.statusName}</td>
                            <td> <button type="button" onclick="confirmReceived(${orderDetail.orderD1})" class="received-btn">Đã nhận</button></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Bảng Đã nhận -->
            <table id="da-nhan" style="display: none;">
                <thead>
                    <tr>
                        <th>Mã Đơn Hàng</th>
                        <th>Tên Sản Phẩm</th>
                        <th>Số Lượng</th>
                        <th>Tổng Tiền</th>
                        <th>Trạng Thái</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="orderDetail" items="${daNhanList}">
                        <tr>
                            <td>${orderDetail.orderID.orderID}</td>
                            <td><p>${orderDetail.productID.productName}</p></td>
                            <td>${orderDetail.quantity}</td>
                            <td>
                                <fmt:formatNumber value="${orderDetail.price}" type="currency" currencySymbol="VND" />
                            </td>
                            <td class="status da-nhan">${orderDetail.statusID.statusName}</td>
                            <td><button type="button" class="rating-btn">
                                    <a href="${pageContext.request.contextPath}/detail?productID=${orderDetail.productID.productID}" style="text-decoration: none">    
                                        Đánh giá
                                    </a>
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Bảng Đã hủy -->
            <table id="da-huy" style="display: none;">
                <thead>
                    <tr>
                        <th>Mã Đơn Hàng</th>
                        <th>Tên Sản Phẩm</th>
                        <th>Số Lượng</th>
                        <th>Tổng Tiền</th>
                        <th>Trạng Thái</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="orderDetail" items="${daHuyList}">
                        <tr>
                            <td>${orderDetail.orderID.orderID}</td>
                            <td><p>${orderDetail.productID.productName}</p></td>
                            <td>${orderDetail.quantity}</td>
                            <td>
                                <fmt:formatNumber value="${orderDetail.price}" type="currency" currencySymbol="VND" />
                            </td>
                            <td class="status da-huy">${orderDetail.statusID.statusName}</td>
                            <td><button type="button" class="repurchase-btn">
                                    <a href="${pageContext.request.contextPath}/detail?productID=${orderDetail.productID.productID}" style="text-decoration: none">    
                                        Mua lại
                                    </a>
                                </button></td>

                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </div>




        <script>
            var contextPath = "${pageContext.request.contextPath}";
        </script>
        <script src="${pageContext.request.contextPath}/assets/js/userPage.js"></script>
    </body>
</html>