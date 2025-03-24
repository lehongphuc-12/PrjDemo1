<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Báo Cáo Doanh Thu</title>
    <!-- Thêm Chart.js từ CDN -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <link rel="stylesheet" href="../assets/css/seller_doanhthu.css"/>
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
        }
        .card h3 {
            margin: 0;
            font-size: 18px;
            color: #2f4f4f; 
        }
        .card p {
            font-size: 24px;
            margin: 10px 0 0;
            color: #32CD32;  
        }
        .chart-container {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        select {
            padding: 5px;
            font-size: 16px;
            margin-bottom: 10px;
            border: 1px solid #32CD32;
            background-color: #f0f7f0;
            color: #2f4f4f;
        }
        select:hover {
            background-color: #e0f0e0;  
        }
    </style>
        
</head>
<body>
    <div class="container">
        <!-- Tổng quan doanh thu -->
        <div class="summary">
            <div class="card">
                <h3>Tổng Tiền Bán Hàng</h3>
                <p id="totalRevenue"><fmt:formatNumber value="${totalMoney}" type="currency" currencyCode="VND"/></p>
            </div>
            <div class="card">
                <h3>Tiền Đã Thanh Toán</h3>
                <p id="paidRevenue"><fmt:formatNumber value="${paidMoney}" type="currency" currencyCode="VND"/></p>
            </div>
            <div class="card">
                <h3>Tiền Chờ Xử Lý</h3>
                <p id="pendingRevenue"><fmt:formatNumber value="${pendingMoney}" type="currency" currencyCode="VND"/></p>
            </div>
        </div>

        <!-- Biểu đồ doanh thu -->
        <div class="chart-container">
            <select id="timeFilter" onchange="updateChart()">
                <option value="day">Theo Ngày</option>
                <option value="week">Theo Tuần</option>
            </select>
            <canvas id="revenueChart"></canvas>
        </div>
    </div>
</body>
</html>