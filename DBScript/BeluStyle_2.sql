--use master
--drop database BeluStyle
--go


-- Tạo database BeluStyle
CREATE DATABASE BeluStyle;
GO

-- Sử dụng database BeluStyle
USE BeluStyle;
GO

-- Tạo bảng Category
CREATE TABLE Category (
    CategoryID INT IDENTITY(1,1) PRIMARY KEY,
    [Name] NVARCHAR(255) NOT NULL
);
GO

-- Tạo bảng Brand
CREATE TABLE Brand (
    BrandID INT IDENTITY(1,1) PRIMARY KEY,
    [Name] NVARCHAR(255) NOT NULL
);
GO

-- Tạo bảng Product
CREATE TABLE Product (
    ProductID INT IDENTITY(1,1) PRIMARY KEY,
    CategoryID INT NOT NULL,
    BrandID INT NOT NULL,
    [Name] NVARCHAR(255) NOT NULL,
	Quantity INT NOT NULL,
    [Image] NVARCHAR(255) NULL,
    Price DECIMAL(18, 2) NOT NULL,
    [Description] NVARCHAR(MAX) NULL,
    FOREIGN KEY (CategoryID) REFERENCES Category(CategoryID),
    FOREIGN KEY (BrandID) REFERENCES Brand(BrandID)
);
GO

-- Tạo bảng [User]
CREATE TABLE [User] (
    Username NVARCHAR(255) NOT NULL PRIMARY KEY,
    [Password] NVARCHAR(255) NOT NULL,
	FullName NVARCHAR(255) NOT NULL,
    DOB DATE NULL,
    Sex CHAR(1) NULL,
    Email NVARCHAR(255) NOT NULL UNIQUE,
    PhoneNumber NVARCHAR(20) NULL,
    [Address] NVARCHAR(255) NULL,
    [Role] NVARCHAR(50) NOT NULL DEFAULT 'User', -- Role xác định quyền hạn
    Wallet DECIMAL(18, 2) NOT NULL DEFAULT 0.00 -- Ví tiền của người dùng
);
GO

-- Tạo bảng [Order]
CREATE TABLE [Order] (
    OrderID INT IDENTITY(1,1) PRIMARY KEY,
    Username NVARCHAR(255) NOT NULL,
    OrderDate DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (Username) REFERENCES [User](Username)
);
GO

-- Tạo bảng OrderDetail để lưu chi tiết từng sản phẩm trong đơn hàng
CREATE TABLE OrderDetail (
    OrderID INT NOT NULL,
    ProductID INT NOT NULL,
	PRIMARY KEY(OrderID, ProductID),
    Quantity INT NOT NULL,
    Price DECIMAL(18, 2) NOT NULL,
    FOREIGN KEY (OrderID) REFERENCES [Order](OrderID),
    FOREIGN KEY (ProductID) REFERENCES Product(ProductID)
);
GO


-- Nhập dữ liệu mẫu vào bảng Category
INSERT INTO Category ([Name])
VALUES 
    ('Shirt'),
    ('Pants'),
    ('Dress/Skirt'),
    ('Jacket'),
    ('Shoe'),
	('Bag'),
	('Hat'),
	('Accessory'),
	('Backpack');
GO

-- Nhập dữ liệu mẫu vào bảng Brand
INSERT INTO Brand ([Name])
VALUES 
    ('Nike'),
    ('Adidas'),
    ('Zara'),
    ('Louis Vuitton'),
    ('Gucci'),
    ('Chanel'),
    ('Calvin Klein'),
    ('Supreme'),
    ('Dirty Coins'),
	('Uniqlo')
GO

-- Nhập dữ liệu mẫu vào bảng Product (Nếu bị lệch đổi 77 thành 76)
DBCC CHECKIDENT ('Product', RESEED, 77);
GO

INSERT INTO Product ([Name], BrandID, CategoryID, Quantity, [Image], Price, [Description])
VALUES 
	('Xplorer Backpack', 2, 9, 43, '77.png', 1500000, 'XPLORER BACKPACK'),
    ('Backpack Adicolor', 2, 9, 71, '78.png', 850000, 'Adidas backpack Adicolor'),
    ('Parachute Straight Pants', 2, 2, 66, '79.png', 2300000, 'Adidas parachute straight pants'),
    ('Track Pants Nylon Adicolor', 2, 2, 23, '80.png', 1900000, 'Adidas track pants nylon Adicolor'),
    ('Astir SN Shoes', 2, 5, 132, '81.png', 1680000, 'Adidas Astir SN shoes'),
    ('Blouson Straight Jacket SST', 2, 4, 33, '82.png', 2400000, 'Adidas blouson straight jacket SST'),
    ('Satin Pleated Shirt', 3, 1, 123, '83.png', 630000, 'Zara satin pleated shirt'),
    ('Zara White Embroidered Maxi Dress', 3, 3, 75, '84.png', 620000, 'Zara white embroidered maxi dress'),
    ('Long Embroidered Floral Maxi Dress', 3, 3, 135, '85.png', 445000, 'Zara long embroidered floral maxi dress'),
    ('Zara Suede Leather Jacket (Belt Model)', 3, 4, 95, '86.png', 1199000, 'Zara suede leather jacket (belt model)'),
    ('Black Wide 4-Button Vest Limited Edition', 3, 1, 25, '87.png', 1450000, 'Zara black wide 4-button vest limited edition'),
    ('Long Shirt Dress with Gray Snake Pattern Zara Auth New Tag', 3, 3, 55, '88.png', 1050000, 'Zara long shirt dress with gray snake pattern'),
    ('Zara Backless Long Sleeve Shirt', 3, 1, 65, '89.png', 358000, 'Zara backless long sleeve shirt'),
    ('Zara Jean Skirt', 3, 3, 22, '90.png', 440000, 'Zara jean skirt'),
    ('Zara Croptop Pocket Shirt', 3, 1, 203, '91.png', 350000, 'Zara croptop pocket shirt'),
    ('Zara Authentic Flared Jeans', 3, 2, 43, '92.png', 770000, 'Zara authentic flared jeans'),
    ('Louis Vuitton Zippy Organizer Wallet M82081 Black', 4, 8, 12, '93.png', 25500000, 'Louis Vuitton zippy organizer wallet M82081 black'),
    ('Louis Vuitton Monogram Silver Scarf Black', 4, 8, 35, '94.png', 16000000, 'Louis Vuitton monogram silver scarf black'),
    ('Louis Vuitton Madeleine BB Shoulder Bag M45977 Black', 4, 6, 10, '95.png', 81000000, 'Louis Vuitton Madeleine BB shoulder bag M45977 black'),
    ('Louis Vuitton Tambour Watch Q118N Black', 4, 8, 1, '96.png', 94500000, 'Louis Vuitton Tambour watch Q118N black'),
    ('Louis Vuitton Monogram Hoodie 1AAKV0 Black Patterned', 4, 4, 25, '97.png', 63000000, 'Louis Vuitton monogram hoodie 1AAKV0 black patterned'),
    ('Louis Vuitton Shirt 1AFAXK White', 4, 1, 25, '98.png', 29450000, 'Louis Vuitton shirt 1AFAXK white'),
    ('Louis Vuitton Damier Pop Cap M7360M Red Black Size M', 4, 7, 5, '99.png', 22450000, 'Louis Vuitton Damier Pop cap M7360M red black size M'),
    ('Louis Vuitton 1.1 Millionaires Sunglasses Z1166W White Gold', 4, 8, 62, '100.png', 22750000, 'Louis Vuitton 1.1 Millionaires sunglasses Z1166W white gold'),
    ('Louis Vuitton Initials Taurillon 40mm Reversible Belt M0532U Navy', 4, 8, 325, '101.png', 18450000, 'Louis Vuitton initials Taurillon 40mm reversible belt M0532U navy'),
    ('Louis Vuitton Trunk Messenger Bag M45727 Black', 4, 6, 40, '102.png', 63000000, 'Louis Vuitton trunk messenger bag M45727 black'),
    ('Louis Vuitton Black Leather Upper Case Loafers', 4, 5, 25, '103.png', 9450000, 'Louis Vuitton black leather upper case loafers'),
    ('Gucci Web Slide Sandal Black Size 41.5', 5, 5, 80, '104.png', 8000000, 'Gucci web slide sandal black size 41.5'),
    ('Gucci Black Rubber Buckle Strap Sandal Black 463463-J8700-1000', 5, 5, 68, '105.png', 18300000, 'Gucci black rubber buckle strap sandal black 463463-J8700-1000'),
    ('Gucci GG Supreme Slide Tiger 407345-G0K00-1084', 5, 5, 60, '106.png', 14500000, 'Gucci GG Supreme slide Tiger 407345-G0K00-1084'),
    ('Gucci Women’s Slide Sandal with Double G 619893-2HK80-9795', 5, 5, 70, '107.png', 16900000, 'Gucci women’s slide sandal with double G 619893-2HK80-9795'),
    ('Gucci GG Cotton Silk Polo ‎742384 XJFGU 2270 Brown Beige', 5, 1, 110, '108.png', 24500000, 'Gucci GG cotton silk polo ‎742384 XJFGU 2270 brown beige'),
    ('Gucci Polo Shirt Black Size M', 5, 1, 120, '109.png', 17500000, 'Gucci polo shirt black size M'),
    ('Gucci Polo Shirt Ss2020 Beige Size S', 5, 1, 96, '110.png', 15490000, 'Gucci polo shirt SS2020 beige size S'),
    ('Gucci GG Jacquard Cotton Jacket 496919 X9V05 4245', 5, 1, 69, '111.png', 32500000, 'Gucci GG Jacquard cotton jacket 496919 X9V05 4245'),
    ('Gucci GG Cotton 60s Trousers With Web Khaki', 5, 2, 142, '112.png', 15800000, 'Gucci GG cotton 60s trousers with web khaki'),
    ('Gucci Ken Scott Printed Silk Shorts Black Size 46', 5, 2, 135, '113.png', 5200000, 'Gucci Ken Scott printed silk shorts black size 46'),
    ('Gucci GG Nylon Swim Short With Bee 410571 XR898 4824 Dark Blue', 5, 2, 160, '114.png', 8950000, 'Gucci GG nylon swim short with bee 410571 XR898 4824 dark blue'),
    ('Gucci GG Jacquard Jacket New Gucci GG', 5, 4, 110, '115.png', 69974000, 'Gucci GG Jacquard jacket new Gucci GG'),
    ('Gucci GG Panel Track Jacket 693022 XJD9V-2270 Multi-color', 5, 4, 97, '116.png', 43800000, 'Gucci GG panel track jacket 693022 XJD9V-2270 multi-color'),
    ('Gucci Technical Jersey Jacket', 5, 4, 100, '117.png', 21600000, 'Gucci technical jersey jacket'),
    ('Gucci GG Jacket 655159 Green Yellow Size S', 5, 4, 100, '118.png', 60500000, 'Gucci GG jacket 655159 green yellow size S'),
    ('Gucci Sweatshirt With Logo 653367 Green Size S', 5, 4, 102, '119.png', 33500000, 'Gucci sweatshirt with logo 653367 green size S'),
    ('Gucci GG Monogram Monogram Reversible Jacket', 5, 4, 55, '120.png', 46298000, 'Gucci GG monogram monogram reversible jacket'),
    ('Gucci Ophidia Small Top Handle Bag with Web Brown Beige', 5, 6, 127, '121.png', 58500000, 'Gucci Ophidia small top handle bag with web brown beige'),
    ('Gucci 1955 Horsebit Shoulder Bag', 5, 6, 99, '122.png', 80900000, 'Gucci 1955 Horsebit shoulder bag'),
    ('Gucci 1955 Horsebit Shoulder Bag Light Brown', 5, 6, 80, '123.png', 62500000, 'Gucci 1955 Horsebit shoulder bag light brown'),
    ('Gucci 2way Handbag Micro Shima White Satchel', 5, 6, 117, '124.png', 22900000, 'Gucci 2way handbag Micro Shima white satchel'),
    ('Gucci GG Baseball Hat With Tiger Print Black Yellow Size M', 5, 7, 120, '125.png', 13000000, 'Gucci GG baseball hat with Tiger print black yellow size M'),
    ('Gucci GG Supreme Baseball Hat With Angry Cat Size M', 5, 7, 100, '126.png', 35000000, 'Gucci GG Supreme baseball hat with Angry Cat size M'),
    ('Gucci Tigers GG Baseball Hat 426887 4HB13 2160 Multi-color Size S', 5, 7, 37, '127.png', 10159000, 'Gucci Tigers GG baseball hat 426887 4HB13 2160 multi-color size S'),
    ('Gucci Original GG Canvas Baseball With Web Beige Size L M S', 5, 7, 64, '128.png', 21590000, 'Gucci original GG canvas baseball with web beige size L M S'),
    ('Gucci Print Gucci Leather Baseball Size L M S', 5, 7, 19, '129.png', 20590000, 'Gucci print Gucci leather baseball size L M S'),
    ('Gucci GG Supreme Backpack 704017 FAA0R 9795 Brown', 5, 9, 55, '130.png', 42000000, 'Gucci GG Supreme backpack 704017 FAA0R 9795 brown'),
    ('Gucci Small Ophidia GG Supreme Backpack Brown', 5, 9, 81, '131.png', 40500000, 'Gucci small Ophidia GG Supreme backpack brown'),
    ('Chanel Classic Flap Bag Medium (Gold Lock)', 6, 6, 12, '132.png', 230000000, 'Chanel classic flap bag medium (gold lock)'),
    ('Chanel Hobo 22 Shoulder Bag Black (Silver Lock)', 6, 6, 30, '133.png', 150000000, 'Chanel Hobo 22 shoulder bag black (silver lock)'),
	('Chanel Vanity Top Caviar Handle Black', 6, 6, 15, '134.png', 160000000, 'Chanel Vanity top caviar handle black'),
	('Chanel Mini Womens Handbag Rectangular Green Lambskin Rainbow Hardware 2021', 6, 6, 20, '135.png', 168822000, 'Chanel Mini Womens Handbag Rectangular Green Lambskin Rainbow Hardware 2021'),
    ('Chanel Top Handle Bag 24P Black for Women', 6, 6, 41, '136.png', 166000000, 'Chanel Top Handle Bag 24P Black for Women'),
    ('Chanel Flap Bag With Top Handle Coco 10.5 Pearly', 6, 6, 33, '137.png', 137000000, 'Chanel Flap Bag With Top Handle Coco 10.5 Pearly'),
    ('Chanel Ankle Boots', 6, 5, 79, '138.png', 47000000, 'Chanel Ankle Boots'),
    ('Womens Loafers Chanel CC Gray Patent Loafers Gray Size 37', 6, 5, 80, '139.png', 38500000, 'Women''s Loafers Chanel CC Gray Patent Loafers Gray Size 37'),
    ('Chanel Metal Black CC Women''s Belt with Stone 3cm Color Black Size 80', 6, 8, 30, '140.png', 31500000, 'Chanel Metal Black CC Women''s Belt with Stone 3cm Color Black Size 80'),
    ('Chanel CC Women''s Choker Necklace Full White Pearl', 6, 8, 54, '141.png', 55000000, 'Chanel CC Women''s Choker Necklace Full White Pearl'),
    ('Chanel Necklace Pendant CC Yellow', 6, 8, 55, '142.png', 26500000, 'Chanel Necklace Pendant CC Yellow'),
    ('Chanel CC Necklace Black Yellow', 6, 8, 63, '143.png', 29000000, 'Chanel CC Necklace Black Yellow'),
    ('Chanel Pearl Necklace with Stone CC VCH6 White Ivory', 6, 8, 93, '144.png', 27500000, 'Chanel Pearl Necklace with Stone CC VCH6 White Ivory'),
    ('Chanel KT C Square Flower Earrings with Stones KTC120 White Yellow', 6, 8, 88, '145.png', 21500000, 'Chanel KT C Square Flower Earrings with Stones KTC120 White Yellow'),
    ('Chanel CC Logo Square Stone Earrings in White Gold', 6, 8, 101, '146.png', 22000000, 'Chanel CC Logo Square Stone Earrings in White Gold'),
    ('Chanel CC Mark Jewelry Yellow Earrings', 6, 8, 100, '147.png', 17800000, 'Chanel CC Mark Jewelry Yellow Earrings'),
    ('Chanel Classic Cashmere Wool Double C Logo Print Hat Grey', 6, 7, 110, '148.png', 18500000, 'Chanel Classic Cashmere Wool Double C Logo Print Hat Grey'),
    ('Chanel Mini Duma Backpack Black', 6, 9, 72, '149.png', 150000000, 'Chanel Mini Duma Backpack Black'),
    ('Chanel Backpack Grained Calfskin & Gold Black', 6, 9, 90, '150.png', 158000000, 'Chanel Backpack Grained Calfskin & Gold Black'),
    ('Chanel Small Backpack Lambskin & Gold Gray', 6, 9, 111, '151.png', 158000000, 'Chanel Small Backpack Lambskin & Gold Gray'),
    ('Chanel Small Backpack Lambskin & Gold White', 6, 9, 104, '152.png', 158000000, 'Chanel Small Backpack Lambskin & Gold White');

-- Nhập dữ liệu mẫu vào bảng [User]
INSERT INTO [User] (Username, [Password], FullName, DOB, Sex, Email, PhoneNumber, [Address], [Role], Wallet)
VALUES 
    ('user1', '0b14d501a594442a01c6859541bcb3e8164d183d32937b851835442f69d5c94e', 'Nguoi1', '1990-01-01', 'M', 'horigolrghs@gmail.com', '1234567890', '123 Main St', 'User', 100.00),
    ('user2', '6cf615d5bcaac778352a8f1f3360d23f02f34ec182e259897fd6ce485d7870d4', 'Nguoi2', '1985-05-15', 'F', 'user2@example.com', '0987654321', '456 Elm St', 'User', 200.00),
    ('admin1', 'ebcfc99aa881883fd9a06b78b50b140df65f2794470e444d57470345dacdb536', 'Chu1', '1975-08-22', 'M', 'admin1@example.com', '1112223333', '789 Pine St', 'Admin', 300.00),
    ('user3', '5906ac361a137e2d286465cd6588ebb5ac3f5ae955001100bc41577c3d751764', 'Nguoi3', '1992-12-12', 'M', 'user3@example.com', '3334445555', '321 Oak St', 'User', 150.00),
    ('user4', 'b97873a40f73abedd8d685a7cd5e5f85e4a9cfb83eac26886640a0813850122b', 'Nguoi4', '1995-03-25', 'F', 'user4@example.com', '5556667777', '654 Maple St', 'User', 50.00);
GO

-- Nhập dữ liệu mẫu vào bảng [Order]
INSERT INTO [Order] (Username, OrderDate)
VALUES 
    ('user1', '2024-06-01 10:00:00'),
    ('user2', '2024-06-02 11:00:00'),
    ('user1', '2024-06-03 12:00:00'),
    ('user3', '2024-06-04 13:00:00'),
    ('user4', '2024-06-05 14:00:00');
GO

-- Nhập dữ liệu mẫu vào bảng OrderDetail
INSERT INTO OrderDetail (OrderID, ProductID, Quantity, Price)
VALUES 
    (1, 80, 1, 599.99),
    (2, 81, 1, 499.99),
    (3, 83, 1, 89.99),
    (4, 82, 1, 59.99),
    (5, 78, 1, 799.99);
GO

--delete from OrderDetail
--delete from [Order]
--delete from Product





