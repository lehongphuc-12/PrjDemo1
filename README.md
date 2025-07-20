# Dự án Website Bán Nông Sản Online - PrjDemo1

## 📝 Giới thiệu

**PrjDemo1** là một dự án website thương mại điện tử chuyên cung cấp các mặt hàng nông sản sạch, an toàn và chất lượng. Dự án được xây dựng nhằm mục đích tạo ra một nền tảng mua sắm trực tuyến tiện lợi, kết nối người nông dân và người bán hàng với người tiêu dùng.

Website cho phép người dùng mua sản phẩm, người bán đăng ký gian hàng và quản lý sản phẩm của mình, cùng với trang quản trị (admin) để giám sát toàn bộ hoạt động của hệ thống.

## ✨ Tính năng chính

Hệ thống được phân chia thành 3 vai trò chính: **Người mua (Khách hàng)**, **Người bán (Seller)**, và **Quản trị viên (Admin)**.

### 👤 Người mua (Client)
- **Đăng ký / Đăng nhập:** Quản lý tài khoản cá nhân.
- **Trang chủ:** Hiển thị các sản phẩm nổi bật, sản phẩm mới, khuyến mãi từ nhiều gian hàng.
- **Xem sản phẩm:**
    - Liệt kê sản phẩm theo danh mục (rau, củ, quả, ...).
    - Tìm kiếm sản phẩm theo tên.
    - Xem chi tiết thông tin sản phẩm (hình ảnh, mô tả, giá, thông tin người bán).
- **Giỏ hàng:**
    - Thêm/xóa sản phẩm vào giỏ hàng.
    - Cập nhật số lượng sản phẩm.
- **Thanh toán:**
    - Điền thông tin giao hàng.
    - Thực hiện quy trình đặt hàng cho các sản phẩm từ một hoặc nhiều người bán.
    - Thanh toán tiền mặt hoặc thanh toán online(VNPAY)
    - **Tài Khoản thanh toán online (VNpay):**
        + Ngân hàng:	NCB
        + Số thẻ:	9704198526191432198
        + Tên chủ thẻ:	NGUYEN VAN A
        + Ngày phát hành:	07/15
        + Mật khẩu OTP:	123456
- **Lịch sử mua hàng:** Xem lại các đơn hàng đã đặt và trạng thái của chúng.

### 🧑‍🌾 Người bán (Seller)
- **Đăng ký gian hàng:** Form đăng ký để trở thành người bán trên nền tảng.
- **Quản lý gian hàng:** Cập nhật thông tin cửa hàng, logo, mô tả.
- **Quản lý Sản phẩm:**
    - Đăng bán sản phẩm mới.
    - Cập nhật thông tin, hình ảnh, giá cả, số lượng tồn kho.
    - Xóa các sản phẩm không còn kinh doanh.
- **Quản lý Đơn hàng:**
    - Nhận và xem các đơn hàng cho sản phẩm của mình.
    - Cập nhật trạng thái đơn hàng (xác nhận, đang giao, đã giao thành công).
- **Thống kê:** Xem báo cáo doanh thu, số lượng sản phẩm đã bán.

### ⚙️ Quản trị viên (Admin)
- **Dashboard:** Bảng điều khiển tổng quan về doanh thu, số lượng người dùng, đơn hàng trên toàn hệ thống.
- **Quản lý Người bán:**
    - Duyệt/từ chối yêu cầu đăng ký bán hàng.
    - Quản lý các tài khoản người bán.
- **Quản lý Sản phẩm:** Xem và có thể ẩn/xóa các sản phẩm vi phạm.
- **Quản lý Danh mục:** Quản lý các loại nông sản chung cho toàn bộ website.
- **Quản lý Đơn hàng:** Xem tất cả đơn hàng trên hệ thống.
- **Quản lý Người dùng:** Xem thông tin và quản lý tài khoản khách hàng.

## 🚀 Công nghệ sử dụng

- **Backend:**
  - **Ngôn ngữ:** Java
  - **Database:** SQL Server
- **Frontend:**
  - **Ngôn ngữ:** HTML, CSS, JavaScript
  - **Template Engine:** JSP
- **Build Tool:** Maven

## 🛠️ Hướng dẫn Cài đặt và Chạy dự án

### Yêu cầu
- JDK 17 hoặc mới hơn
- Maven 3.6+
- SQL Server

### Các bước cài đặt

1.  **Clone repository về máy của bạn:**
    ```bash
    git clone https://github.com/lehongphuc-12/PrjDemo1.git
    cd PrjDemo1
    ```

2.  **Cấu hình Cơ sở dữ liệu (Database):**
    - Mở SQL Server Management Studio, tạo một database mới (ví dụ: `db_nongsan`).
    - Tìm file script SQL trong thư mục dự án và chạy script này để tạo các bảng cần thiết.
    - Mở file `src/main/resources/META-INF/persistence.xml`.
    - Cập nhật thông tin kết nối đến database của bạn:
      ```properties
      spring.datasource.url=jdbc:sqlserver://[TEN_SERVER]:1433;databaseName=[TEN_DATABASE];encrypt=true;trustServerCertificate=true
      spring.datasource.username=[USERNAME]
      spring.datasource.password=[PASSWORD]
      spring.jpa.hibernate.ddl-auto=update
      ```
      *Lưu ý: `encrypt=true;trustServerCertificate=true` có thể cần thiết cho các phiên bản JDBC driver mới.*

3.  **Build dự án với Maven:**
    Sử dụng terminal hoặc command prompt tại thư mục gốc của dự án:
    ```bash
    mvn clean install
    ```

4.  **Chạy ứng dụng:**
    Sau khi build thành công, chạy lệnh sau:
    ```bash
    mvn spring-boot:run
    ```
    Hoặc bạn có thể chạy file JAR đã được build trong thư mục `target/`:
    ```bash
    java -jar target/PrjDemo1-0.0.1-SNAPSHOT.jar
    ```

5.  **Truy cập ứng dụng:**
    - **Trang người dùng:** Mở trình duyệt và truy cập `http://localhost:8080`
    - **Trang người bán / quản trị:** (Thông thường sẽ có đường dẫn đăng nhập riêng, chỉ cần đăng nhập các tài khoản đúng với vai trò người bán hoặc admin)
    - **Các tài khoản test:**
        + Admin: nguyenvanhung@gmail.com/Hung@1234
        + Seller: nguyenthilan@gmail.com/Lan@34567
        + Customer: nguyenvanan@gmail.com/An@123456 (hoặc đăng nhập google)


## 🤝 Đóng góp

Nếu bạn muốn đóng góp cho dự án, vui lòng thực hiện theo các bước sau:
1.  **Fork** dự án này.
2.  Tạo một **Branch** mới (`git checkout -b feature/AmazingFeature`).
3.  **Commit** những thay đổi của bạn (`git commit -m 'Add some AmazingFeature'`).
4.  **Push** lên Branch (`git push origin feature/AmazingFeature`).
5.  Mở một **Pull Request**.


---
*Dự án được tạo ra với mục đích học tập và demo.*
