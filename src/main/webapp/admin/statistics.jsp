<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thống kê</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/statistics.css">

</head>
<body>
    <div class="stats-container">
        <h2>Thống kê</h2>
        <div class="stats-box-container">
            <div class="stats-box">
                <h3>Số lượng Customer</h3>
                <p>${customerCount}</p>
            </div>
            <div class="stats-box">
                <h3>Số lượng Seller</h3>
                <p>${sellerCount}</p>
            </div>
            <div class="stats-box">
                <h3>Số lượng Sản phẩm</h3>
                <p>${productCount}</p>
            </div>
            <div class="stats-box">
                <h3>Người dùng đang đăng nhập</h3>
                <p>${activeUsers}</p>
            </div>
        </div>

        <div class="chart-container">
            <h3 class="chart-title">Thống kê đăng ký người dùng</h3>
            <div class="filter-form">
                <label for="month">Tháng:</label>
                <select name="month" id="month">
                    <c:forEach var="i" begin="1" end="12">
                        <option value="${i}" <c:if test="${i == selectedMonth}">selected</c:if>>${i}</option>
                    </c:forEach>
                </select>
                <label for="year">Năm:</label>
                <select name="year" id="year">
                    <c:forEach var="i" begin="2020" end="2025">
                        <option value="${i}" <c:if test="${i == selectedYear}">selected</c:if>>${i}</option>
                    </c:forEach>
                </select>
                <button type="button" id="viewChartBtn">Xem</button> <!-- Thay form bằng button -->
            </div>
            <canvas id="registrationChart"></canvas>
        </div>
    </div>

    <script>
        let chartInstance = null; // Lưu instance của Chart để có thể hủy và tạo lại

        // Hàm vẽ biểu đồ
        function drawChart(days, counts, maxDays, month, year) {
            const ctx = document.getElementById('registrationChart').getContext('2d');

            // Hủy biểu đồ cũ nếu tồn tại
            if (chartInstance) {
                chartInstance.destroy();
            }

            const fullDays = Array.from({length: maxDays}, (_, i) => i + 1);
            const fullCounts = fullDays.map(day => {
                const index = days.indexOf(day);
                return index !== -1 ? counts[index] : 0;
            });

            chartInstance = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: fullDays,
                    datasets: [{
                        label: `Số người dùng đăng ký (Tháng ${month}/${year})`,
                        data: fullCounts,
                        borderColor: '#27ae60',
                        backgroundColor: 'rgba(39, 174, 96, 0.2)',
                        fill: true,
                        tension: 0.4
                    }]
                },
                options: {
                    responsive: true,
                    scales: {
                        x: {
                            title: { display: true, text: 'Ngày trong tháng' }
                        },
                        y: {
                            title: { display: true, text: 'Số lượng người dùng' },
                            beginAtZero: true
                        }
                    },
                    plugins: {
                        legend: { display: true, position: 'top' }
                    }
                }
            });
        }

        // Vẽ biểu đồ ban đầu khi trang được tải
        const initialDays = [<c:forEach items="${registrationDays}" var="day" varStatus="loop">${day}<c:if test="${!loop.last}">,</c:if></c:forEach>];
        const initialCounts = [<c:forEach items="${registrationCounts}" var="count" varStatus="loop">${count}<c:if test="${!loop.last}">,</c:if></c:forEach>];
        const initialMaxDays = ${maxDays};
        const initialMonth = ${selectedMonth};
        const initialYear = ${selectedYear};
        drawChart(initialDays, initialCounts, initialMaxDays, initialMonth, initialYear);

        // Xử lý sự kiện nhấn nút "Xem"
        $('#viewChartBtn').click(function() {
            const month = $('#month').val();
            const year = $('#year').val();

            $.ajax({
                url: 'admin',
                type: 'GET',
                data: {
                    action: 'stats',
                    month: month,
                    year: year,
                    ajax: 'true' // Thêm tham số để servlet biết đây là yêu cầu AJAX
                },
                dataType: 'json',
                success: function(response) {
                    drawChart(response.days, response.counts, response.maxDays, month, year);
                },
                error: function(xhr, status, error) {
                    console.error('Error fetching data:', error);
                }
            });
        });
    </script>
</body>
</html>