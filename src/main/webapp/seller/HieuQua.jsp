<%-- 
    Document   : HieuQua
    Created on : Mar 11, 2025, 9:46:15 AM
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
        <title>Hiệu Quả Hoạt Động</title>
        <style>
            .container {
                max-width: 1200px;
                margin: 0 auto;
            }
            .summary {
                display: flex;
                justify-content: space-between;
                margin-bottom: 20px;
            }
            .card {
                background-color: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                width: 30%;
                text-align: center;
                border: 1px solid #32CD32; /* Viền xanh lá chuối */
            }
            .card h3 {
                margin: 0;
                font-size: 18px;
                color: #2f4f4f; /* Xám xanh đậm */
            }
            .card p {
                font-size: 24px;
                margin: 10px 0 0;
                color: #32CD32; /* Xanh lá chuối */
            }
            .section {
                margin-bottom: 30px;
            }
            h2 {
                color: #2f4f4f;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                background-color: white;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                border-radius: 8px;
                overflow: hidden;
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
            }
            /* Style cho phân trang */
            .pagination {
                display: flex;
                justify-content: center;
                gap: 10px;
                margin: 20px 0;
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
            <h1>Hiệu Quả Hoạt Động</h1>

            <!-- Tổng quan hiệu quả -->
<!--            <div class="section">
                <h2>Tổng Quan</h2>
                <div class="summary">
                    <div class="card">
                        <h3>Tỷ Lệ Giao Hàng Thành Công</h3>
                        <p>80%</p>
                    </div>
                    <div class="card">
                        <h3>Thời Gian Xử Lý Trung Bình</h3>
                        <p>2 giờ</p>
                    </div>
                    <div class="card">
                        <h3>Số Đơn Hàng Hoàn Thành</h3>
                        <p>4/5</p>
                    </div>
                </div>
            </div>-->

            <!-- Thống kê hiệu suất -->
            <div class="section">
                <h2>Thống Kê Hiệu Suất</h2>
                <div class="summary">
                    <div class="card">
                        <h3>Đơn Giao Trễ</h3>
                        <p>1</p>
                    </div>
                    <div class="card">
                        <h3>Tỷ Lệ Hủy Đơn</h3>
                        <p>20%</p>
                    </div>
                    <div class="card">
                        <h3>Đơn Đang Xử Lý</h3>
                        <p>${choXuLyListSize}</p>
                    </div>
                </div>
            </div>

            <!-- Chi tiết đơn hàng -->
            <div class="section">
                <h2>Chi Tiết Đơn Hàng</h2>
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
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="orderDetail" items="${orderList}">
                            <tr>
                                <td>${orderDetail.orderID.orderID}</td>
                                <td>${orderDetail.productID.productName}</td>
                                <td>${orderDetail.quantity}</td>
                                <td>
                                    <fmt:formatNumber value="${orderDetail.price}" type="currency" currencySymbol="VND" />
                                </td>
                                <td class="status ${orderDetail.statusID.statusName eq 'Chờ xử lý' ? 'cho-xu-ly' : orderDetail.statusID.statusName eq 'Đang giao' ? 'dang-giao' : orderDetail.statusID.statusName eq 'Đã nhận' ? 'da-nhan' : 'da-huy'}">
                                    ${orderDetail.statusID.statusName}
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <!-- Bảng Chờ xử lý -->
                <table id="cho-xu-ly" style="display: none;">
                    <thead>
                        <tr>
                            <th>Mã Đơn Hàng</th>
                            <th>Tên Sản Phẩm</th>
                            <th>Số Lượng</th>
                            <th>Tổng Tiền</th>
                            <th>Trạng Thái</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="orderDetail" items="${choXuLyList}">
                            <tr>
                                <td>${orderDetail.orderID.orderID}</td>
                                <td>${orderDetail.productID.productName}</td>
                                <td>${orderDetail.quantity}</td>
                                <td>
                                    <fmt:formatNumber value="${orderDetail.price}" type="currency" currencySymbol="VND" />
                                </td>
                                <td class="status cho-xu-ly">${orderDetail.statusID.statusName}</td>
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
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="orderDetail" items="${dangGiaoList}">
                            <tr>
                                <td>${orderDetail.orderID.orderID}</td>
                                <td>${orderDetail.productID.productName}</td>
                                <td>${orderDetail.quantity}</td>
                                <td>
                                    <fmt:formatNumber value="${orderDetail.price}" type="currency" currencySymbol="VND" />
                                </td>
                                <td class="status dang-giao">${orderDetail.statusID.statusName}</td>
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
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="orderDetail" items="${daNhanList}">
                            <tr>
                                <td>${orderDetail.orderID.orderID}</td>
                                <td>${orderDetail.productID.productName}</td>
                                <td>${orderDetail.quantity}</td>
                                <td>
                                    <fmt:formatNumber value="${orderDetail.price}" type="currency" currencySymbol="VND" />
                                </td>
                                <td class="status da-nhan">${orderDetail.statusID.statusName}</td>
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
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="orderDetail" items="${daHuyList}">
                            <tr>
                                <td>${orderDetail.orderID.orderID}</td>
                                <td>${orderDetail.productID.productName}</td>
                                <td>${orderDetail.quantity}</td>
                                <td>
                                    <fmt:formatNumber value="${orderDetail.price}" type="currency" currencySymbol="VND" />
                                </td>
                                <td class="status da-huy">${orderDetail.statusID.statusName}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div id="pagination-all" class="pagination">
                    <c:if test="${totalPage >= 1}">
                        <!-- Nút Previous -->
                        <c:choose>
                            <c:when test="${param.page == null || param.page == 1}">
                                <a href="#" class="disabled">&laquo; Previous</a>
                            </c:when>
                            <c:otherwise>
                                <a onclick="loadSection('hieuqua&page=${param.page - 1}')">&laquo; Previous</a>
                            </c:otherwise>
                        </c:choose>

                        <!-- Số trang -->
                        <c:forEach begin="1" end="${totalPage}" var="i">
                            <a class="${param.page == i || (param.page == null && i == 1) ? 'active' : ''}" onclick="loadSection('hieuqua&page=${i}')">${i}</a>
                        </c:forEach>

                        <!-- Nút Next -->
                        <c:choose>
                            <c:when test="${param.page == totalPage || (param.page == null && totalPage == 1)}">
                                <a href="#" class="disabled">Next &raquo;</a>
                            </c:when>
                            <c:otherwise>
                                <a onclick="loadSection('hieuqua&page=${(param.page == null ? 1 : param.page) + 1}')">Next »</a>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>
