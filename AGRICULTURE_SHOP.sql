-- use AGRICULTURE_SHOPPING
-- create database AGRICULTURE_SHOPPING;
use AGRICULTURE_SHOPPING
-- drop database demoPRJ;
CREATE TABLE [Role] ( 
    RoleID INT PRIMARY KEY IDENTITY(1,1),
    RoleName NVARCHAR(50) UNIQUE NOT NULL
);

-- drop table [User]
CREATE TABLE [User] (
    UserID INT PRIMARY KEY IDENTITY(1,1),
    FullName NVARCHAR(100) NOT NULL,
    Email NVARCHAR(100) UNIQUE NOT NULL,
    Password NVARCHAR(255)  NULL,
    PhoneNumber NVARCHAR(15) not null,
    RoleID INT NOT NULL,
    Address NVARCHAR(255),
    Status bit DEFAULT 1,
    shopName nvarchar(100),
    CreatedAt DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (RoleID) REFERENCES [Role](RoleID)
);


CREATE TABLE [CategoryGroup] (
    GroupID INT IDENTITY(1,1) PRIMARY KEY,
    GroupName NVARCHAR(100) NOT NULL UNIQUE,
    GroupDescription NVARCHAR(50) NULL
);

CREATE TABLE [Category] (
    CategoryID INT IDENTITY(1,1) PRIMARY KEY,
    CategoryName NVARCHAR(100) NOT NULL,
    GroupID INT NOT NULL, 
    CategoryDescription NVARCHAR(50) NULL
    FOREIGN KEY (GroupID) REFERENCES [CategoryGroup](GroupID)
);

CREATE TABLE [City] (
    CityID INT IDENTITY(1,1) PRIMARY KEY, 
    CityName NVARCHAR(100) NOT NULL UNIQUE 
);

CREATE TABLE [Product] (
    ProductID INT IDENTITY(1,1) PRIMARY KEY,
    SellerID INT NOT NULL,
    ProductName NVARCHAR(100) NOT NULL,
    Description NVARCHAR(MAX),
    Price DECIMAL(18, 2) NOT NULL CHECK (Price >= 0),
    Quantity DECIMAL(18, 2) NOT NULL,
    Unit NVARCHAR(20) null,
    CategoryID INT NOT NULL,
    CreatedDate DATETIME DEFAULT GETDATE(),
    CityID INT NOT NULL,
    Status bit DEFAULT 1,
    FOREIGN KEY (CityID) REFERENCES [City](CityID),
    FOREIGN KEY (SellerID) REFERENCES [User](UserID),
    FOREIGN KEY (CategoryID) REFERENCES [Category](CategoryID)
);


CREATE TABLE [ProductImage] (
    ImageID INT IDENTITY(1,1) PRIMARY KEY,
    ProductID INT,
    ImageURL NVARCHAR(255),
    FOREIGN KEY (ProductID) REFERENCES [Product](ProductID)
);

CREATE TABLE [PaymentMethod] (
    PaymentMethodID INT PRIMARY KEY IDENTITY(1,1),
    MethodName NVARCHAR(50) UNIQUE NOT NULL,
    Description NVARCHAR(255)
);

CREATE TABLE [OrderStatus] (
    StatusID INT IDENTITY(1,1) PRIMARY KEY,
    StatusName NVARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE [Order] (
    OrderID INT IDENTITY(1,1) PRIMARY KEY,
    UserID INT NOT NULL,
    ShippingAddress NVARCHAR(255),
    OrderDate DATETIME DEFAULT GETDATE(),
    TotalAmount DECIMAL(18, 2) NOT NULL CHECK (TotalAmount >= 0),
    PaymentMethodID INT NOT NULL,
    FOREIGN KEY (UserID) REFERENCES [User](UserID),
    FOREIGN KEY (PaymentMethodID) REFERENCES [PaymentMethod](PaymentMethodID)
);

CREATE TABLE [OrderDetail] (
    OrderDetailID INT IDENTITY(1,1) PRIMARY KEY,
    OrderID INT NOT NULL,
    ProductID INT NOT NULL,
    StatusID INT NOT NULL,
    Quantity INT NOT NULL CHECK (Quantity > 0),
    Price DECIMAL(18, 2) NOT NULL CHECK (Price >= 0),
    FOREIGN KEY (OrderID) REFERENCES [Order](OrderID),
    FOREIGN KEY (ProductID) REFERENCES [Product](ProductID),
    FOREIGN KEY (StatusID) REFERENCES [OrderStatus](StatusID),
);

CREATE TABLE [PaymentStatus] (
    StatusID INT PRIMARY KEY IDENTITY(1,1),
    StatusName NVARCHAR(50) NOT NULL UNIQUE,
    Description NVARCHAR(255)
);

CREATE TABLE [Payment] (
    PaymentID INT IDENTITY(1,1) PRIMARY KEY,
    OrderID INT NOT NULL,
    PaymentDate DATETIME DEFAULT GETDATE(),
    Amount DECIMAL(18, 2) NOT NULL CHECK (Amount >= 0),
    PaymentMethodID INT NOT NULL,
    StatusID INT NOT NULL,
    FOREIGN KEY (OrderID) REFERENCES [Order](OrderID),
    FOREIGN KEY (PaymentMethodID) REFERENCES [PaymentMethod](PaymentMethodID),
    FOREIGN KEY (StatusID) REFERENCES [PaymentStatus](StatusID)
);

CREATE TABLE [Review] (
    ReviewID INT IDENTITY(1,1) PRIMARY KEY,
    ProductID INT NOT NULL,
    UserID INT NOT NULL,
    Rating INT NOT NULL CHECK (Rating BETWEEN 1 AND 5),
    Comment NVARCHAR(255),
    ReviewDate DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (ProductID) REFERENCES [Product](ProductID),
    FOREIGN KEY (UserID) REFERENCES [User](UserID)
);

CREATE TABLE [Discount] (
    DiscountID INT IDENTITY(1,1) PRIMARY KEY,
    DiscountCode NVARCHAR(50) NOT NULL UNIQUE,
    DiscountPercent DECIMAL(5, 2) NOT NULL CHECK (DiscountPercent BETWEEN 0 AND 100),
    StartDate DATETIME NOT NULL,
    EndDate DATETIME NOT NULL,
    ProductID INT,
    FOREIGN KEY (ProductID) REFERENCES [Product](ProductID)
);

CREATE TABLE [ProductView] (
    ViewID INT IDENTITY(1,1) PRIMARY KEY,
    ProductID INT NOT NULL,
    UserID INT,
    ViewDate DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (ProductID) REFERENCES [Product](ProductID),
    FOREIGN KEY (UserID) REFERENCES [User](UserID)
);

CREATE TABLE [ActivityLog] (
    LogID INT IDENTITY(1,1) PRIMARY KEY,
    UserID INT NOT NULL,
    Action NVARCHAR(255) NOT NULL,
    Timestamp DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (UserID) REFERENCES [User](UserID)
);
CREATE TABLE SellerRegistrationRequest (
    requestID INT PRIMARY KEY IDENTITY(1,1),
    userID INT NOT NULL,
    shopName VARCHAR(255) NOT NULL,
    status VARCHAR(50) DEFAULT 'pending',
    createdAt DATETIME DEFAULT GETDATE(),
    updatedAt DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (UserID) REFERENCES [User](UserID)
);




select * from [OrderDetail]
-- Bảng Role:
INSERT INTO [Role] ([RoleName])
VALUES ('Admin'), ('Seller'), ('Customer');

select * from [Role]

-- Table [CategoryGroup]:
INSERT INTO [CategoryGroup] ([GroupName],[GroupDescription]) VALUES (N'Rau củ quả	',N'Các loại rau, củ, quả tươi'); --1
INSERT INTO [CategoryGroup] ([GroupName],[GroupDescription]) VALUES (N'Trái cây ',N'Các loại trái cây tươi'); --2
INSERT INTO [CategoryGroup] ([GroupName],[GroupDescription]) VALUES (N'Đồ khô ',N'Các loại hạt, mứt, sấy khô'); --3
INSERT INTO [CategoryGroup] ([GroupName],[GroupDescription]) VALUES (N'Đồ uống ',N'Nước ép, trà, cà phê'); --4
INSERT INTO [CategoryGroup] ([GroupName],[GroupDescription]) VALUES (N'Đặc sản vùng miền ',N'Đặc sản từ các vùng miền'); --5
INSERT INTO [CategoryGroup] ([GroupName],[GroupDescription]) VALUES (N'Ngũ cốc & Gạo',N'	Gạo, lúa mì, các loại ngũ cốc'); --6
INSERT INTO [CategoryGroup] ([GroupName],[GroupDescription]) VALUES (N'Thịt & Hải sản	',N'Các loại thịt tươi, hải sản'); --7

select * from CategoryGroup

-- Table [Category]:
INSERT INTO [Category] ([CategoryName], [GroupID],[CategoryDescription]) VALUES (N'Rau xanh', 1,N'Các loại rau xanh tươi');--1
INSERT INTO [Category] ([CategoryName], [GroupID],[CategoryDescription]) VALUES (N'Củ quả	', 1,N'Bắp cải, khoai tây, dưa leo...');--2
INSERT INTO [Category] ([CategoryName], [GroupID],[CategoryDescription]) VALUES (N'Nấm', 1,N'Nấm các loại');--3
INSERT INTO [Category] ([CategoryName], [GroupID],[CategoryDescription]) VALUES (N'Trái cây nội địa', 2,N'Các loại trái cây nội địa');--4
INSERT INTO [Category] ([CategoryName], [GroupID],[CategoryDescription]) VALUES (N'Trái cây nhập khẩu', 2,N'Các loại trái cây nhập khẩu');--5
INSERT INTO [Category] ([CategoryName], [GroupID],[CategoryDescription]) VALUES (N'Các loại hạt', 3,N'Hạt điều, hạt dẻ, hạnh nhân,...');--6
INSERT INTO [Category] ([CategoryName], [GroupID],[CategoryDescription]) VALUES (N'Trái cây sấy', 3,N'Các loại trái cây sấy');--7
INSERT INTO [Category] ([CategoryName], [GroupID],[CategoryDescription]) VALUES (N'Nước ép trái cây', 4,N'Các loại nước ép tươi mát');--8
INSERT INTO [Category] ([CategoryName], [GroupID],[CategoryDescription]) VALUES (N'Trà', 4,N'Trà khô, túi lọc');--9
INSERT INTO [Category] ([CategoryName], [GroupID],[CategoryDescription]) VALUES (N'Cà phê', 4,N'Cà phê pha phin');--10
INSERT INTO [Category] ([CategoryName], [GroupID],[CategoryDescription]) VALUES (N'Gạo', 6,N'Gạo dẻo thơm ngon');--11
INSERT INTO [Category] ([CategoryName], [GroupID],[CategoryDescription]) VALUES (N'Đặc sản mọi miền', 5,N'Các loại đặc sản vùng miền');--12
INSERT INTO [Category] ([CategoryName], [GroupID],[CategoryDescription]) VALUES (N'Ngũ cốc, yến mạch',5 ,N'Bổ sug dinh dưỡng');--13
INSERT INTO [Category] ([CategoryName], [GroupID],[CategoryDescription]) VALUES (N'Thịt heo', 7,N'Thịt ba chỉ, thịt nạc vai,...');--14
INSERT INTO [Category] ([CategoryName], [GroupID],[CategoryDescription]) VALUES (N'Thịt bò', 7,N'Bắp bò, nạm bò, sườn bò...');--15
INSERT INTO [Category] ([CategoryName], [GroupID],[CategoryDescription]) VALUES (N'Hải sản tươi sống', 7,N'Cá, tôm, cua, ghẹ,...');--16

select * from Category



INSERT INTO [PaymentStatus] (StatusName) VALUES 
(N'Đang xử lý'),
(N'Chưa thanh toán'),
(N'Đã thanh toán');
INSERT INTO [OrderStatus] (StatusName) VALUES
(N'Chờ xử lý'),      -- Pending
(N'Đang giao'),      -- Shipping
(N'Đã nhận'),        -- Delivered
(N'Đã hủy');         -- Cancelled

select * from OrderStatus


-- DROP PROCEDURE IF EXISTS GetSimilarProducts6;

go
CREATE PROCEDURE GetSimilarProducts6
    @ProductID INT
AS
BEGIN
    DECLARE @CategoryID INT;
    DECLARE @FirstTwoWords NVARCHAR(MAX);
    
    SELECT @CategoryID = CategoryID,
           @FirstTwoWords = 
               CASE
                   WHEN CHARINDEX(' ', ProductName, CHARINDEX(' ', ProductName) + 1) > 0 THEN
                       SUBSTRING(ProductName, 1, CHARINDEX(' ', ProductName, CHARINDEX(' ', ProductName) + 1) - 1)
                   ELSE
                       ProductName
               END
    FROM Product
    WHERE ProductID = @ProductID;

    -- Debug: In giá trị của @FirstTwoWords để kiểm tra
    PRINT 'First Two Words: ' + @FirstTwoWords;
    PRINT 'CategoryID: ' + CAST(@CategoryID AS NVARCHAR(10));
    
    SELECT TOP 30 *
    FROM Product p
    WHERE p.ProductID != @ProductID and p.[status] =1
    AND (
        CategoryID = @CategoryID
        OR ProductName LIKE @FirstTwoWords + '%' COLLATE Vietnamese_100_CI_AS
    );
END;
EXEC GetSimilarProducts6 @ProductID = 82;


SELECT * from [User]

-- delete from [User]
--– Table Users:
INSERT INTO [User] ([FullName], [Email], [Password], [PhoneNumber], [RoleID], [Address])
VALUES
    -- 5 Admin (RoleID = 1)
    (N'Nguyễn Văn Hùng', 'nguyenvanhung@gmail.com', 'Hung@1234', '0912345678', 1, N'123 Đường Láng, Hà Nội'),
    (N'Trần Thị Mai', 'tranthimai@gmail.com', 'Mai#56789', '0987654321', 1, N'45 Lê Lợi, TP.HCM'),
    (N'Phạm Quốc Anh', 'phamquocanh@gmail.com', 'Anh$90123', '0933445566', 1, N'78 Trần Phú, Đà Nẵng'),
    (N'Lê Hồng Ngọc', 'lehongngoc@gmail.com', 'Ngoc%4567', '0909123456', 1, N'12 Nguyễn Huệ, Huế'),
    (N'Hoàng Minh Tuấn', 'hoangminhtuan@gmail.com', 'Tuan&8901', '0977889900', 1, N'56 Phạm Ngũ Lão, Hà Nội'),

    -- 30 Seller (RoleID = 2)
    (N'Nguyễn Thị Lan', 'nguyenthilan@gmail.com', 'Lan@34567', '0911223344', 2, N'89 Hùng Vương, Hải Phòng'),
    (N'Trần Văn Nam', 'tranvannam@gmail.com', 'Nam#78901', '0388776655', 2, N'23 Nguyễn Trãi, Thanh Hóa'),
    (N'Phạm Thị Hoa', 'phamthihoa@gmail.com', 'Hoa$12345', '0799665544', 2, N'67 Bạch Đằng, Đà Nẵng'),
    (N'Lê Minh Đức', 'leminhduc@gmail.com', 'Duc%56789', '0933557799', 2, N'34 Lê Đại Hành, Hà Nội'),
    (N'Hoàng Thị Thủy', 'hoangthithuy@gmail.com', 'Thuy&9012', '0977112233', 2, N'12 Trần Hưng Đạo, TP.HCM'),
    (N'Nguyễn Văn Bảo', 'nguyenvanbao@gmail.com', 'Bao@34567', '0911998877', 2, N'56 Lý Thường Kiệt, Cần Thơ'),
    (N'Trần Thị Hương', 'tranthihuong@gmail.com', 'Huong#7890', '0388445566', 2, N'78 Nguyễn Văn Cừ, Vinh'),
    (N'Phạm Văn Long', 'phamvanlong@gmail.com', 'Long$12345', '0799332211', 2, N'23 Hùng Vương, Nha Trang'),
    (N'Lê Thị Thu', 'lethithu@gmail.com', 'Thu%56789', '0933221100', 2, N'45 Lê Lợi, Huế'),
    (N'Hoàng Văn Phúc', 'hoangvanphuc@gmail.com', 'Phuc&9012', '0977554433', 2, N'67 Trần Phú, Đà Lạt'),
    (N'Nguyễn Thị Ngọc', 'nguyenthingoc@gmail.com', 'Ngoc@3456', '0911887766', 2, N'89 Nguyễn Huệ, Hà Nội'),
    (N'Trần Văn Khoa', 'tranvankhoa@gmail.com', 'Khoa#7890', '0388995544', 2, N'12 Phạm Ngũ Lão, TP.HCM'),
    (N'Phạm Thị Yến', 'phamthiyen@gmail.com', 'Yen$12345', '0799443322', 2, N'34 Lý Thường Kiệt, Đà Nẵng'),
    (N'Lê Văn Tâm', 'levantam@gmail.com', 'Tam%56789', '0933778899', 2, N'56 Lê Đại Hành, Hải Phòng'),
    (N'Hoàng Thị Linh', 'hoangthilinh@gmail.com', 'Linh&9012', '0977223344', 2, N'78 Nguyễn Trãi, Thanh Hóa'),
    (N'Nguyễn Văn Dũng', 'nguyenvandung@gmail.com', 'Dung@3456', '0911556677', 2, N'23 Bạch Đằng, Hà Nội'),
    (N'Trần Thị Hà', 'tranthiha@gmail.com', 'Ha#78901', '0388667788', 2, N'45 Hùng Vương, TP.HCM'),
    (N'Phạm Văn Hậu', 'phamvanhau@gmail.com', 'Hau$12345', '0799112233', 2, N'67 Nguyễn Văn Cừ, Vinh'),
    (N'Lê Thị Hồng', 'lethihong@gmail.com', 'Hong%5678', '0933441122', 2, N'12 Lý Thường Kiệt, Đà Nẵng'),
    (N'Hoàng Văn Tài', 'hoangvantai@gmail.com', 'Tai&90123', '0977885544', 2, N'34 Nguyễn Huệ, Huế'),
    (N'Nguyễn Thị Mai', 'nguyenthimai@gmail.com', 'Mai@34567', '0911334455', 2, N'56 Trần Phú, Hà Nội'),
    (N'Trần Văn Bình', 'tranvanbinh@gmail.com', 'Binh#7890', '0388223344', 2, N'78 Lê Lợi, TP.HCM'),
    (N'Phạm Thị Dung', 'phamthidung@gmail.com', 'Dung$1234', '0799556677', 2, N'23 Hùng Vương, Hải Phòng'),
    (N'Lê Văn Sơn', 'levanson@gmail.com', 'Son%56789', '0933667788', 2, N'45 Nguyễn Trãi, Thanh Hóa'),
    (N'Hoàng Thị Ánh', 'hoangthianh@gmail.com', 'Anh&90123', '0977334455', 2, N'67 Bạch Đằng, Đà Nẵng'),
    (N'Nguyễn Văn Tuấn', 'nguyenvantuan@gmail.com', 'Tuan@3456', '0911778899', 2, N'89 Lê Đại Hành, Hà Nội'),
    (N'Trần Thị Loan', 'tranthiloan@gmail.com', 'Loan#7890', '0388446677', 2, N'12 Trần Hưng Đạo, TP.HCM'),
    (N'Phạm Văn Kiên', 'phamvankien@gmail.com', 'Kien$1234', '0799223344', 2, N'34 Lý Thường Kiệt, Cần Thơ'),
    (N'Lê Thị Hạnh', 'lethihanh@gmail.com', 'Hanh%5678', '0933558899', 2, N'56 Nguyễn Văn Cừ, Vinh'),
    -- 10 Seller mới (RoleID = 2)
    (N'Nguyễn Thị Băng', 'nguyenthibang@gmail.com', 'Bang@12345', '0912667788', 2, N'23 Nguyễn Thị Minh Khai, TP.HCM'),
    (N'Trần Văn Chương', 'tranvanchuong@gmail.com', 'Chuong#678', '0388112233', 2, N'45 Phạm Văn Đồng, Hà Nội'),
    (N'Phạm Thị Diễm', 'phamthidiem@gmail.com', 'Diem$90123', '0799778899', 2, N'67 Nguyễn Đình Chiểu, Đà Nẵng'),
    (N'Lê Văn Hoàng', 'levanhoang@gmail.com', 'Hoang%4567', '0933889900', 2, N'12 Lê Duẩn, Hải Phòng'),
    (N'Hoàng Thị Kim', 'hoangthikim@gmail.com', 'Kim&89012', '0977556677', 2, N'34 Trần Quốc Toản, Huế'),
    (N'Nguyễn Văn Lợi', 'nguyenvanloi@gmail.com', 'Loi@34567', '0911337788', 2, N'56 Võ Thị Sáu, Hà Nội'),
    (N'Trần Thị Mỹ', 'tranthimy@gmail.com', 'My#78901', '0388665544', 2, N'78 Nguyễn Công Trứ, TP.HCM'),
    (N'Phạm Văn Nghĩa', 'phamvannghia@gmail.com', 'Nghia$1234', '0799446677', 2, N'23 Lê Thánh Tôn, Đà Nẵng'),
    (N'Lê Thị Phương', 'lethiphuong@gmail.com', 'Phuong%567', '0933774455', 2, N'45 Hùng Vương, Cần Thơ'),
    (N'Hoàng Văn Quốc', 'hoangvanquoc@gmail.com', 'Quoc&9012', '0977228899', 2, N'67 Nguyễn Văn Linh, Vinh'),

    (N'Nguyễn Thị Anh', 'nguyenthianh@gmail.com', 'Anh@123456', '0912778899', 2, N'12 Nguyễn Thị Định, TP.HCM'),
    (N'Trần Văn Bắc', 'tranvanbac@gmail.com', 'Bac#789012', '0388224455', 2, N'34 Nguyễn Văn Trỗi, Hà Nội'),
    (N'Phạm Thị Cúc', 'phamthicuc@gmail.com', 'Cuc$34567', '0799551122', 2, N'56 Lê Văn Sỹ, Đà Nẵng'),
    (N'Lê Văn Đạt', 'levandat@gmail.com', 'Dat%90123', '0933662233', 2, N'78 Trần Quang Khải, Hải Phòng'),
    (N'Hoàng Thị Giang', 'hoangthigiang@gmail.com', 'Giang&4567', '0977333344', 2, N'23 Nguyễn Khánh Toàn, Huế'),
    (N'Nguyễn Văn Hảo', 'nguyenvanhao@gmail.com', 'Hao@89012', '0911445566', 2, N'45 Phạm Hồng Thái, Hà Nội'),
    (N'Trần Thị Hiền', 'tranthihien@gmail.com', 'Hien#34567', '0388778899', 2, N'67 Nguyễn Thị Minh Khai, TP.HCM'),
    (N'Phạm Văn Khoa', 'phamvankhoa@gmail.com', 'Khoa$90123', '0799226677', 2, N'12 Lê Lai, Đà Nẵng'),
    (N'Lê Thị Liên', 'lethilien@gmail.com', 'Lien%45678', '0933557788', 2, N'34 Nguyễn Văn Cừ, Vinh'),
    (N'Hoàng Văn Minh', 'hoangvanminh@gmail.com', 'Minh&12345', '0977448899', 2, N'56 Hùng Vương, Cần Thơ'),
    (N'Nguyễn Thị Nga', 'nguyenthinga@gmail.com', 'Nga@67890', '0911886677', 2, N'78 Trần Phú, Nha Trang'),
    (N'Trần Văn Phát', 'tranvanphat@gmail.com', 'Phat#23456', '0388667788', 2, N'23 Lê Đại Hành, Hà Nội'),
    (N'Phạm Thị Quyên', 'phamthiquyen@gmail.com', 'Quyen$7890', '0799119900', 2, N'45 Lý Thường Kiệt, TP.HCM'),
    (N'Lê Văn Sơn', 'levanson67@gmail.com', 'Son%34567', '0933446677', 2, N'67 Nguyễn Huệ, Đà Nẵng'),
    (N'Hoàng Thị Thùy', 'hoangthithuy12@gmail.com', 'Thuy&90123', '0977225566', 2, N'12 Bạch Đằng, Hải Phòng'),



    -- 65 Customer (RoleID = 3)
    (N'Nguyễn Văn An', 'nguyenvanan@gmail.com', 'An@123456', '0912346789', 3, N'123 Nguyễn Huệ, Hà Nội'),
    (N'Trần Thị Bích', 'tranthibich@gmail.com', 'Bich#7890', '0988776655', 3, N'45 Trần Phú, TP.HCM'),
    (N'Phạm Văn Cường', 'phamvancuong@gmail.com', 'Cuong$123', '0799445566', 3, N'67 Lê Lợi, Đà Nẵng'),
    (N'Lê Thị Duyên', 'lethiduyen@gmail.com', 'Duyen%567', '0933223344', 3, N'12 Hùng Vương, Hải Phòng'),
    (N'Hoàng Văn Giang', 'hoangvangiang@gmail.com', 'Giang&901', '0977114455', 3, N'34 Nguyễn Trãi, Thanh Hóa'),
    (N'Nguyễn Thị Hiền', 'nguyenthihien@gmail.com', 'Hien@3456', '0911889900', 3, N'56 Bạch Đằng, Hà Nội'),
    (N'Trần Văn Hòa', 'tranvanhoa@gmail.com', 'Hoa#78901', '0388556677', 3, N'78 Lê Đại Hành, TP.HCM'),
    (N'Phạm Thị Kim', 'phamthikim@gmail.com', 'Kim$12345', '0799334455', 3, N'23 Lý Thường Kiệt, Đà Nẵng'),
    (N'Lê Văn Lộc', 'levanloc@gmail.com', 'Loc%56789', '0933668899', 3, N'45 Nguyễn Văn Cừ, Vinh'),
    (N'Hoàng Thị Minh', 'hoangthiminh@gmail.com', 'Minh&9012', '0977225566', 3, N'67 Trần Hưng Đạo, Huế'),
    (N'Nguyễn Văn Nguyên', 'nguyenvannguyen@gmail.com', 'Nguyen@34', '0911557788', 3, N'89 Nguyễn Huệ, Hà Nội'),
    (N'Trần Thị Oanh', 'tranthioanh@gmail.com', 'Oanh#7890', '0388668899', 3, N'12 Trần Phú, TP.HCM'),
    (N'Phạm Văn Phát', 'phamvanphat@gmail.com', 'Phat$1234', '0799115566', 3, N'34 Lê Lợi, Đà Nẵng'),
    (N'Lê Thị Quyên', 'lethiquyen@gmail.com', 'Quyen%567', '0933446677', 3, N'56 Hùng Vương, Hải Phòng'),
    (N'Hoàng Văn Sang', 'hoangvansang@gmail.com', 'Sang&9012', '0977337788', 3, N'78 Nguyễn Trãi, Thanh Hóa'),
    (N'Nguyễn Thị Thanh', 'nguyenthithanh@gmail.com', 'Thanh@345', '0911779900', 3, N'23 Bạch Đằng, Hà Nội'),
    (N'Trần Văn Thắng', 'tranvanthang@gmail.com', 'Thang#789', '0388447788', 3, N'45 Lê Đại Hành, TP.HCM'),
    (N'Phạm Thị Uyên', 'phamthiuyen@gmail.com', 'Uyen$1234', '0799226677', 3, N'67 Lý Thường Kiệt, Đà Nẵng'),
    (N'Lê Văn Việt', 'levanviet@gmail.com', 'Viet%5678', '0933559900', 3, N'12 Nguyễn Văn Cừ, Vinh'),
    (N'Hoàng Thị Xuân', 'hoangthixuan@gmail.com', 'Xuan&9012', '0977441122', 3, N'34 Trần Hưng Đạo, Huế'),
    (N'Nguyễn Văn Dương', 'nguyenvanduong@gmail.com', 'Duong@345', '0911335566', 3, N'56 Nguyễn Huệ, Hà Nội'),
    (N'Trần Thị Hằng', 'tranthihang@gmail.com', 'Hang#7890', '0388226677', 3, N'78 Trần Phú, TP.HCM'),
    (N'Phạm Văn Khánh', 'phamvankhanh@gmail.com', 'Khanh$123', '0799557788', 3, N'23 Lê Lợi, Đà Nẵng'),
    (N'Lê Thị Lệ', 'lethile@gmail.com', 'Le%56789', '0933669900', 3, N'45 Hùng Vương, Hải Phòng'),
    (N'Hoàng Văn Mạnh', 'hoangvanmanh@gmail.com', 'Manh&9012', '0977338899', 3, N'67 Nguyễn Trãi, Thanh Hóa'),
    (N'Nguyễn Thị Nhung', 'nguyenthinhung@gmail.com', 'Nhung@345', '0911446677', 3, N'89 Bạch Đằng, Hà Nội'),
    (N'Trần Văn Phú', 'tranvanphu@gmail.com', 'Phu#78901', '0388557788', 3, N'12 Lê Đại Hành, TP.HCM'),
    (N'Phạm Thị Quỳnh', 'phamthiquynh@gmail.com', 'Quynh$123', '0799336677', 3, N'34 Lý Thường Kiệt, Đà Nẵng'),
    (N'Lê Văn Sỹ', 'levansy@gmail.com', 'Sy%56789', '0933447788', 3, N'56 Nguyễn Văn Cừ, Vinh'),
    (N'Hoàng Thị Thảo', 'hoangthithao@gmail.com', 'Thao&9012', '0977226677', 3, N'78 Trần Hưng Đạo, Huế'),
    (N'Nguyễn Văn Thịnh', 'nguyenvanthinh@gmail.com', 'Thinh@345', '0911888899', 3, N'23 Nguyễn Huệ, Hà Nội'),
    (N'Trần Thị Trâm', 'tranthitram@gmail.com', 'Tram#7890', '0388669900', 3, N'45 Trần Phú, TP.HCM'),
    (N'Phạm Văn Trung', 'phamvantrung@gmail.com', 'Trung$123', '0799117788', 3, N'67 Lê Lợi, Đà Nẵng'),
    (N'Lê Thị Vân', 'lethivan@gmail.com', 'Van%56789', '0933556677', 3, N'12 Hùng Vương, Hải Phòng'),
    (N'Hoàng Văn Vỹ', 'hoangvanvy@gmail.com', 'Vy&90123', '0977447788', 3, N'34 Nguyễn Trãi, Thanh Hóa'),
    (N'Nguyễn Thị Yến', 'nguyenthiyen@gmail.com', 'Yen@34567', '0911338899', 3, N'56 Bạch Đằng, Hà Nội'),
    (N'Trần Văn Ánh', 'tranvananh@gmail.com', 'Anh#78901', '0388227788', 3, N'78 Lê Đại Hành, TP.HCM'),
    (N'Phạm Thị Bình', 'phamthibinh@gmail.com', 'Binh$1234', '0799558899', 3, N'23 Lý Thường Kiệt, Đà Nẵng'),
    (N'Lê Văn Cảnh', 'levancanh@gmail.com', 'Canh%5678', '0933666677', 3, N'45 Nguyễn Văn Cừ, Vinh'),
    (N'Hoàng Thị Đào', 'hoangthidao@gmail.com', 'Dao&90123', '0977339900', 3, N'67 Trần Hưng Đạo, Huế'),
    (N'Nguyễn Văn Đông', 'nguyenvandong@gmail.com', 'Dong@3456', '0911447788', 3, N'89 Nguyễn Huệ, Hà Nội'),
    (N'Trần Thị Gấm', 'tranthigam@gmail.com', 'Gam#78901', '0388558899', 3, N'12 Trần Phú, TP.HCM'),
    (N'Phạm Văn Hiếu', 'phamvanhieu@gmail.com', 'Hieu$1234', '0799337788', 3, N'34 Lê Lợi, Đà Nẵng'),
    (N'Lê Thị Huyền', 'lethihuyen@gmail.com', 'Huyen%567', '0933448899', 3, N'56 Hùng Vương, Hải Phòng'),
    (N'Hoàng Văn Khang', 'hoangvankhang@gmail.com', 'Khang&901', '0977227788', 3, N'78 Nguyễn Trãi, Thanh Hóa'),
    (N'Nguyễn Thị Lâm', 'nguyenthilam@gmail.com', 'Lam@34567', '0911889900', 3, N'23 Bạch Đằng, Hà Nội'),
    (N'Trần Văn Lập', 'tranvanlap@gmail.com', 'Lap#78901', '0388666677', 3, N'45 Lê Đại Hành, TP.HCM'),
    (N'Phạm Thị Mỹ', 'phamthimy@gmail.com', 'My$123456', '0799118899', 3, N'67 Lý Thường Kiệt, Đà Nẵng'),
    (N'Lê Văn Nghĩa', 'levannghia@gmail.com', 'Nghia%567', '0933557788', 3, N'12 Nguyễn Văn Cừ, Vinh'),
    (N'Hoàng Thị Nhi', 'hoangthinhii@gmail.com', 'Nhi&90123', '0977448899', 3, N'34 Trần Hưng Đạo, Huế'),
    (N'Nguyễn Văn Phong', 'nguyenvanphong@gmail.com', 'Phong@345', '0911339900', 3, N'56 Nguyễn Huệ, Hà Nội'),
    (N'Trần Thị Phương', 'tranthiphuong@gmail.com', 'Phuong#78', '0388228899', 3, N'78 Trần Phú, TP.HCM'),
    (N'Phạm Văn Quang', 'phamvanquang@gmail.com', 'Quang$123', '0799559900', 3, N'23 Lê Lợi, Đà Nẵng'),
    (N'Lê Thị Rạng', 'lethirang@gmail.com', 'Rang%5678', '0933667788', 3, N'45 Hùng Vương, Hải Phòng'),
    (N'Hoàng Văn Sinh', 'hoangvansinh@gmail.com', 'Sinh&9012', '0977336677', 3, N'67 Nguyễn Trãi, Thanh Hóa'),
    (N'Nguyễn Thị Sương', 'nguyenthisuong@gmail.com', 'Suong@345', '0911448899', 3, N'89 Bạch Đằng, Hà Nội'),
    (N'Trần Văn Tài', 'tranvantai@gmail.com', 'Tai#78901', '0388559900', 3, N'12 Lê Đại Hành, TP.HCM'),
    (N'Phạm Thị Thắm', 'phamthitham@gmail.com', 'Tham$1234', '0799338899', 3, N'34 Lý Thường Kiệt, Đà Nẵng'),
    (N'Lê Văn Thiện', 'levanthien@gmail.com', 'Thien%567', '0933449900', 3, N'56 Nguyễn Văn Cừ, Vinh'),
    (N'Hoàng Thị Thuận', 'hoangthithuan@gmail.com', 'Thuan&901', '0977228899', 3, N'78 Trần Hưng Đạo, Huế'),
    (N'Nguyễn Văn Tiến', 'nguyenvantien@gmail.com', 'Tien@3456', '0911886677', 3, N'23 Nguyễn Huệ, Hà Nội'),
    (N'Trần Thị Trang', 'tranthitrang@gmail.com', 'Trang#789', '0388667788', 3, N'45 Trần Phú, TP.HCM'),
    (N'Phạm Văn Trường', 'phamvantruong@gmail.com', 'Truong$12', '0799119900', 3, N'67 Lê Lợi, Đà Nẵng'),
    (N'Lê Thị Vẹn', 'lethiven@gmail.com', 'Ven%56789', '0933558899', 3, N'12 Hùng Vương, Hải Phòng'),
    (N'Hoàng Văn Vượng', 'hoangvanvuong@gmail.com', 'Vuong&901', '0977449900', 3, N'34 Nguyễn Trãi, Thanh Hóa');


UPDATE [User]
SET createdAt = DATEADD(DAY, ABS(CHECKSUM(NEWID())) % 83, '2025-01-01');




select * from [User] where RoleID = 2

update Product 
set status = 1
select * from [USER] where RoleID = 1

INSERT INTO [City] ([CityName]) VALUES (N'Hà Nội');
INSERT INTO [City] ([CityName]) VALUES (N'Hà Giang');
INSERT INTO [City] ([CityName]) VALUES (N'Cao Bằng');
INSERT INTO [City] ([CityName]) VALUES (N'Bắc Kạn');
INSERT INTO [City] ([CityName]) VALUES (N'Tuyên Quang');
INSERT INTO [City] ([CityName]) VALUES (N'Lào Cai');
INSERT INTO [City] ([CityName]) VALUES (N'Điện Biên');
INSERT INTO [City] ([CityName]) VALUES (N'Lai Châu');
INSERT INTO [City] ([CityName]) VALUES (N'Sơn La');
INSERT INTO [City] ([CityName]) VALUES (N'Yên Bái');
INSERT INTO [City] ([CityName]) VALUES (N'Hòa Bình');
INSERT INTO [City] ([CityName]) VALUES (N'Thái Nguyên');
INSERT INTO [City] ([CityName]) VALUES (N'Lạng Sơn');
INSERT INTO [City] ([CityName]) VALUES (N'Quảng Ninh');
INSERT INTO [City] ([CityName]) VALUES (N'Bắc Giang');
INSERT INTO [City] ([CityName]) VALUES (N'Phú Thọ');
INSERT INTO [City] ([CityName]) VALUES (N'Vĩnh Phúc');
INSERT INTO [City] ([CityName]) VALUES (N'Bắc Ninh');
INSERT INTO [City] ([CityName]) VALUES (N'Hải Dương');
INSERT INTO [City] ([CityName]) VALUES (N'Hải Phòng');
INSERT INTO [City] ([CityName]) VALUES (N'Hưng Yên');
INSERT INTO [City] ([CityName]) VALUES (N'Thái Bình');
INSERT INTO [City] ([CityName]) VALUES (N'Hà Nam');
INSERT INTO [City] ([CityName]) VALUES (N'Nam Định');
INSERT INTO [City] ([CityName]) VALUES (N'Ninh Bình');
INSERT INTO [City] ([CityName]) VALUES (N'Thanh Hóa');
INSERT INTO [City] ([CityName]) VALUES (N'Nghệ An');
INSERT INTO [City] ([CityName]) VALUES (N'Hà Tĩnh');
INSERT INTO [City] ([CityName]) VALUES (N'Quảng Bình');
INSERT INTO [City] ([CityName]) VALUES (N'Quảng Trị');
INSERT INTO [City] ([CityName]) VALUES (N'Thừa Thiên Huế');
INSERT INTO [City] ([CityName]) VALUES (N'Đà Nẵng');
INSERT INTO [City] ([CityName]) VALUES (N'Quảng Nam');
INSERT INTO [City] ([CityName]) VALUES (N'Quảng Ngãi');
INSERT INTO [City] ([CityName]) VALUES (N'Bình Định');
INSERT INTO [City] ([CityName]) VALUES (N'Phú Yên');
INSERT INTO [City] ([CityName]) VALUES (N'Khánh Hòa');
INSERT INTO [City] ([CityName]) VALUES (N'Ninh Thuận');
INSERT INTO [City] ([CityName]) VALUES (N'Bình Thuận');
INSERT INTO [City] ([CityName]) VALUES (N'Kon Tum');
INSERT INTO [City] ([CityName]) VALUES (N'Gia Lai');
INSERT INTO [City] ([CityName]) VALUES (N'Đắk Lắk');
INSERT INTO [City] ([CityName]) VALUES (N'Đắk Nông');
INSERT INTO [City] ([CityName]) VALUES (N'Lâm Đồng');
INSERT INTO [City] ([CityName]) VALUES (N'Bình Phước');
INSERT INTO [City] ([CityName]) VALUES (N'Tây Ninh');
INSERT INTO [City] ([CityName]) VALUES (N'Bình Dương');
INSERT INTO [City] ([CityName]) VALUES (N'Đồng Nai');
INSERT INTO [City] ([CityName]) VALUES (N'Bà Rịa - Vũng Tàu');
INSERT INTO [City] ([CityName]) VALUES (N'Thành phố Hồ Chí Minh');
INSERT INTO [City] ([CityName]) VALUES (N'Long An');
INSERT INTO [City] ([CityName]) VALUES (N'Tiền Giang');
INSERT INTO [City] ([CityName]) VALUES (N'Bến Tre');
INSERT INTO [City] ([CityName]) VALUES (N'Trà Vinh');
INSERT INTO [City] ([CityName]) VALUES (N'Vĩnh Long');
INSERT INTO [City] ([CityName]) VALUES (N'Đồng Tháp');
INSERT INTO [City] ([CityName]) VALUES (N'An Giang');
INSERT INTO [City] ([CityName]) VALUES (N'Kiên Giang');
INSERT INTO [City] ([CityName]) VALUES (N'Cần Thơ');
INSERT INTO [City] ([CityName]) VALUES (N'Hậu Giang');
INSERT INTO [City] ([CityName]) VALUES (N'Sóc Trăng');
INSERT INTO [City] ([CityName]) VALUES (N'Bạc Liêu');
INSERT INTO [City] ([CityName]) VALUES (N'Cà Mau');


INSERT INTO [PaymentMethod] (MethodName, Description) VALUES
(N'Chuyển khoản VNPAY', N'Thanh toán qua chuyển khoản ngân hàng'),
(N'Thanh toán khi nhận hàng', N'Thanh toán bằng tiền mặt khi nhận hàng');

select * from PaymentMethod



-- select p.ProductID, p.ProductName, p.Price , sum() 
-- from Product p
-- inner join 

select * from Category c where c.GroupID  = 1
select * from discount
select * from Product  

select * from Product p 
inner join Category c on c.CategoryID = p.CategoryID
inner join CategoryGroup cg on cg.GroupID=c.GroupID
where cg.GroupID = 1

select * from [Order]

select * from Discount d 
join Product p on p.ProductID = d.ProductID

select * from CategoryGroup cg 
join Category c on c.GroupID = cg.GroupID

select c.CategoryName , COUNT(p.ProductID) from Product p 
join Category c on p.CategoryID = c.CategoryID
GROUP by c.CategoryName

select * from Product p
where LOWER(p.ProductName) like N'%gạo%'

create index index_product_name on Product(ProductName) 

CREATE INDEX idx_product_category 
ON Product(CategoryID);

CREATE INDEX idx_category_categoryGroup
ON Category(GroupID);


select * from ProductImage p
where p.ProductID = 371 like N'%trà%'

select * from [User] u  where userID = 9
WHERE u.roleID = 2

join [Order] o on o.userId = u.userId
join [OrderDetail] od on od.OrderID = o.OrderID 

select * from [OrderDetail] od 
join [Order] o on od.OrderID = o.OrderID
where o.userId = 125 and od.ProductID = 15

select * from [OrderStatus]
select * from [Review] where reviewId = 
insert into [Review](ProductID,UserID,Rating,Comment)
values(4,120,2,N'ncl')

select * from Product WHERE CategoryID=1

update Product 
set status = 1
select * from [USER] where RoleID = 1



Select * from Discount


SELECT * from [User] where Email  LIKE 'lethiven@gmail.com'

select * from cart

delete from Cart 
where CartID = 1073