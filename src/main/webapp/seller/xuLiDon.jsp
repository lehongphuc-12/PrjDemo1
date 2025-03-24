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
            .container {
                max-width: 1200px;
                margin: 0 auto;
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
            .confirm-btn {
                background-color: #32CD32; /* Xanh lá */
                color: white;
                border: none;
                padding: 6px 12px;
                border-radius: 4px;
                cursor: pointer;
            }
            .confirm-btn:hover {
                background-color: #228B22; /* Xanh đậm hơn khi hover */
            }
            .no-orders {
                text-align: center;
                color: #555;
                font-size: 18px;
                padding: 20px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Danh Sách Đơn Hàng Cần Xử Lý</h2>

            <!-- Bảng luôn hiển thị với tiêu đề -->
            <table id="cho-xu-ly" class="active">
                <thead>
                    <tr>
                        <th>Mã Đơn Hàng</th>
                        <th>Tên Sản Phẩm</th>
                        <th>Số Lượng</th>
                        <th>Tổng Tiền</th>
                        <th>Hành Động</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Kiểm tra danh sách rỗng -->
                    <c:choose>
                        <c:when test="${empty choXuLyList}">
                            <tr>
                                <td colspan="5" class="no-orders">Không có đơn nào cần xác nhận</td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="orderDetail" items="${choXuLyList}">
                                <tr>
                                    <td>${orderDetail.orderID.orderID}</td>
                                    <td>${orderDetail.productID.productName}</td>
                                    <td>${orderDetail.quantity}</td>
                                    <td>
                                        <fmt:formatNumber value="${orderDetail.price}" type="currency" currencySymbol="VND" />
                                    </td>
                                    <td>
                                        
                                            <button type="button" onclick="confirmOrder(${orderDetail.orderD1})" class="confirm-btn">Xác Nhận</button>
                                        
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </body>
</html>