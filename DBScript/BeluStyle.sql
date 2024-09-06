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
    Sex VARCHAR(6) NULL,
    Email NVARCHAR(255) NOT NULL UNIQUE,
    PhoneNumber NVARCHAR(20) NULL,
    [Address] NVARCHAR(255) NULL,
    [Role] NVARCHAR(50) NOT NULL DEFAULT 'User', -- Role xác định quyền hạn
    Wallet DECIMAL(18, 2) NOT NULL DEFAULT 0.00 -- Ví tiền của người dùng
);
GO

-- Tạo bảng [Order]
CREATE TABLE [Order] (
    OrderID INT PRIMARY KEY,
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



INSERT INTO Product ([Name], BrandID, CategoryID, Quantity, [Image], Price, [Description])
VALUES 
('Basic Crew Neck T-Shirt', 10, 1, 100, '1.jpg', 149000, 'Simple style'),
('Short Sleeve Polo T-Shirt', 10, 1, 50, '2.jpg', 299000, 'Casual wear'),
('Blocktech Windproof Jacket', 10, 2, 20, '3.jpg', 599000, 'Windproof'),
('Light Down Jacket', 10, 2, 10, '4.jpg', 1299000, 'Lightweight'),
('EZY Straight Fit Jeans', 10, 3, 40, '5.jpg', 799000, 'Comfort fit'),
('Smart Ankle Jogger Pants', 10, 3, 60, '6.jpg', 399000, 'Stylish joggers'),
('Rayon Challis Dress', 10, 4, 30, '7.jpg', 599000, 'Elegant dress'),
('Cotton Lawn Pleated Skirt', 10, 4, 25, '8.jpg', 499000, 'Pleated design'),
('Heattech Extra Scarf', 10, 5, 15, '9.jpg', 399000, 'Extra warm'),
('Ribbed Beanie', 10, 5, 20, '10.jpg', 199000, 'Cozy beanie'),
('[DirtyCoins x B Ray] Girlfriend/Boyfriend T-Shirt', 9, 1, 99, '11.jpg', 350000, 'Limited edition'),
('DirtyCoins Gradient Regular T-Shirt - White', 9, 1, 4000, '12.jpg', 319000, 'Gradient design'),
('DirtyCoins Letters Monogram Denim Jersey Shirt - Black', 9, 1, 400, '13.jpg', 319000, 'Monogram style'),
('DirtyCoins Casual T-Shirt', 9, 1, 100, '14.jpg', 239000, 'Everyday wear'),
('DirtyCoins BaBy Dico T-Shirt', 9, 1, 500, '15.jpg', 350000, 'Fun graphic'),
('DirtyCoins Laurel Wreath Logo Pants - Cream', 9, 2, 500, '16.jpg', 699000, 'Logo detail'),
('DirtyCoins Baggy Jeans - Green Wash', 9, 2, 501, '17.jpg', 699000, 'Baggy fit'),
('DirtyCoins Slimfit Jeans', 9, 2, 501, '18.jpg', 569000, 'Slim fit'),
('DirtyCoins Dico Starry Jogger Pants', 9, 2, 200, '19.jpg', 349300, 'Starry pattern'),
('DirtyCoins Letters Monogram Denim Shorts - Black', 9, 2, 100, '20.jpg', 429000, 'Monogram print'),
('DirtyCoins Checkerboard Knit Cardigan', 9, 4, 150, '21.jpg', 719000, 'Checkerboard'),
('DirtyCoins Dico Puppies Hangouts Raglan Hoodie - White/Black', 9, 4, 200, '22.jpg', 599000, 'Raglan hoodie'),
('DirtyCoins Logo Relaxed Hoodie', 9, 4, 199, '23.jpg', 549000, 'Relaxed fit'),
('DirtyCoins Bandana Shirt Jacket - Black', 9, 4, 99, '24.jpg', 649000, 'Bandana print'),
('DirtyCoins Club Raglan Hoodie - Black/White', 9, 4, 40, '25.jpg', 599000, 'Club design'),
('DirtyCoins Logo Plaid Tennis Skirt', 9, 3, 70, '26.jpg', 299000, 'Plaid skirt'),
('DirtyCoins Logo Crossbody Bag', 9, 6, 700, '27.jpg', 467500, 'Crossbody style'),
('DirtyCoins Logo Bowler Bag', 9, 6, 100, '28.jpg', 1190000, 'Bowler shape'),
('DirtyCoins Y Pattern Signature Backpack - B Ray', 9, 9, 100, '29.jpg', 798000, 'Signature style'),
('DirtyCoins Y Pattern Signature Backpack - Dirty Coins', 9, 9, 100, '30.jpg', 749000, 'Y Pattern'),
('DirtyCoins Dico Wavy Backpack V3', 9, 9, 100, '31.jpg', 650000, 'Wavy design'),
('DirtyCoins Y Logo Cap - Black', 9, 7, 200, '32.jpg', 219000, 'Logo cap'),
('DirtyCoins Sticker Pack', 9, 8, 1000, '33.jpg', 39000, 'Fun stickers'),
('DirtyCoins Print Slides (Color: Black, Coffee) (Size: 36, 38, 39, 40, 41, 43, 44, 45)', 9, 5, 1000, '34.jpg', 350000, 'Printed slides'),
('DirtyCoins Print Slides (Color: Black, Coffee) (Size: 42)', 9, 5, 1000, '35.jpg', 420000, 'Printed slides'),
('2024 Red Envelopes - Pack of 5', 9, 8, 1000, '36.jpg', 39000, 'Festive pack'),
('DirtyCoins Signature Woman Shoulder Bag - Black', 9, 6, 100, '37.jpg', 699000, 'Signature bag'),
('DirtyCoins Leather Patch Beanie', 9, 7, 50, '38.jpg', 300000, 'Leather patch'),
('Men''s Ar- Micro Monologo Tee J322761BEH', 7, 1, 47, '39.jpg', 1422685, 'Micro monologo'),
('Men''s Compact Cotton T-Shirt 40611STDAJ', 7, 1, 35, '40.jpg', 931935, 'Compact cotton'),
('Men''s A- Ss Unisex Heart Tee J400222YAF', 7, 1, 23, '41.jpg', 1422685, 'Unisex heart'),
('Men''s A-Ss Unisex Rabbit Logo Tee J400232XA8', 7, 1, 56, '42.jpg', 1422685, 'Rabbit logo'),
('Men''s A- Ss Rel Triple Ck Logo Tee J323207YBH', 7, 1, 78, '43.jpg', 1785643, 'Triple CK'),
('Supreme Balloon T-Shirt - Heather Gray', 8, 1, 56, '44.jpg', 2000000, 'Balloon graphic'),
('Supreme Colorblock Soccer Polo - Purple', 8, 1, 38, '45.jpg', 2000000, 'Colorblock design'),
('Women''s Ar- Micro Monologo Slim Fit Tee J221217BEH', 8, 1, 5, '46.jpg', 735635, 'Slim fit'),
('Unisex Lsr R Dnm Jacket J4002351BJ', 7, 4, 10, '47.jpg', 7163969, 'Denim jacket'),
('Ar- B Clrbck Zip Hoodie J323390BEH', 7, 4, 29, '48.jpg', 2256960, 'Colorblock zip'),
('Supreme Hockey Hoodie - Black', 8, 4, 55, '49.jpg', 5500000, 'Hockey style'),
('Supreme Patchwork Sweatpants (FW20) - Black', 8, 2, 30, '50.jpg', 5000000, 'Patchwork'),
('Supreme SS24 Shoulder Bag - Green', 8, 6, 40, '51.jpg', 2500000, 'SS24 edition'),
('Supreme SS21 Shoulder Bag - Blue', 8, 6, 42, '52.jpg', 2000000, 'SS21 edition'),
('Supreme SS21 Backpack - Red Camo', 8, 9, 10, '53.jpg', 4500000, 'Red camo'),
('Supreme FW21 Backpack - Black', 8, 9, 20, '54.jpg', 3000000, 'FW21 edition'),
('Women''s Fashion Mini Skirt AS W NSW AIR WVN HR', 1, 3, 15, '55.jpg', 743000, 'Fashion mini'),
('Women''s Nike Court Dri-Fit Victory Flouncy Tennis Skirt - White', 1, 3, 30, '56.jpg', 1349000, 'Tennis skirt'),
('Women''s Nike High-Waisted Woven Skirt - Yellow', 1, 3, 50, '57.jpg', 743000, 'High-waisted'),
('NOCTA Hoodie', 1, 4, 33, '58.jpg', 2759000, 'NOCTA design'),
('NOCTA Men''s Fleece Pants', 1, 2, 34, '59.jpg', 2499000, 'Fleece pants'),
('Men''s Flip-Flops', 1, 5, 55, '60.jpg', 1479000, 'Summer wear'),
('Basketball Shoes', 1, 5, 13, '61.jpg', 5869000, 'Pro performance'),
('Women''s Nike In-Season TR 13 PRM Training Shoes', 1, 5, 20, '62.jpg', 1644299, 'Training shoes'),
('Women''s Nike Dri-FIT Swoosh Short-Sleeve Running Top', 1, 1, 100, '63.jpg', 969000, 'Running top'),
('Men''s Dri-FIT Nike Primary Long-Sleeve Multi-Purpose T-Shirt', 1, 1, 57, '64.jpg', 1379000, 'Multi-purpose'),
('Women''s Nike One Patterned Running Tank Top', 1, 1, 115, '65.jpg', 919000, 'Patterned tank'),
('Men''s Nike Dri-FIT Victory Long-Sleeve Golf Shirt', 1, 1, 20, '66.jpg', 1529000, 'Golf shirt'),
('Women''s Nike GP Challenge 1 Hard Court Tennis Shoes', 1, 5, 15, '67.jpg', 4699000, 'Tennis shoes'),
('Women''s Nike Motiva Walking Shoes', 1, 5, 27, '68.jpg', 3239000, 'Walking shoes'),
('Men''s Nike Dunk Low Retro Shoes', 1, 5, 47, '69.jpg', 2929000, 'Retro style'),
('Men''s Nike Defy All Day Walking Shoes', 1, 5, 100, '70.jpg', 1909000, 'Everyday wear'),
('Men''s Nike React Phantom Run Flyknit Running Shoes', 1, 5, 12, '71.jpg', 4109000, 'Flyknit design'),
('Three-Stripes Metallic Baseball Cap', 2, 7, 198, '72.jpg', 500000, 'Metallic look'),
('Four-Panel Mesh Running Cap AEROREADY', 2, 7, 278, '73.jpg', 550000, 'Mesh design'),
('W PRINTED Golf Bucket Hat', 2, 7, 78, '74.jpg', 900000, 'Golf style'),
('Twill Cap', 2, 7, 37, '75.jpg', 850000, 'Twill fabric'),
('PE BP Backpack', 2, 9, 55, '76.jpg', 1500000, 'PE edition'),
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
--INSERT INTO [User] (Username, [Password], FullName, DOB, Sex, Email, PhoneNumber, [Address], [Role], Wallet)
--VALUES 
   -- ('user1', '0b14d501a594442a01c6859541bcb3e8164d183d32937b851835442f69d5c94e', 'Nguoi1', '1990-01-01', 'Male', 'horigolrghs@gmail.com', '1234567890', '123 Main St', 'User', 100.00),
   -- ('user2', '6cf615d5bcaac778352a8f1f3360d23f02f34ec182e259897fd6ce485d7870d4', 'Nguoi2', '1985-05-15', 'Female', 'user2@example.com', '0987654321', '456 Elm St', 'User', 200.00),
   -- ('admin1', 'ebcfc99aa881883fd9a06b78b50b140df65f2794470e444d57470345dacdb536', 'Chu1', '1975-08-22', 'Male', 'admin1@example.com', '1112223333', '789 Pine St', 'Admin', 300.00),
    --('user3', '5906ac361a137e2d286465cd6588ebb5ac3f5ae955001100bc41577c3d751764', 'Nguoi3', '1992-12-12', 'Male', 'user3@example.com', '3334445555', '321 Oak St', 'User', 150.00),
   -- ('user4', 'b97873a40f73abedd8d685a7cd5e5f85e4a9cfb83eac26886640a0813850122b', 'Nguoi4', '1995-03-25', 'Female', 'user4@example.com', '5556667777', '654 Maple St', 'User', 50.00);
--GO

-- Nhập dữ liệu mẫu vào bảng [Order]
--INSERT INTO [Order] (Username, OrderDate)
--VALUES 
  --  ('user1', '2024-06-01 10:00:00'),
 --   ('user2', '2024-06-02 11:00:00'),
  --  ('user1', '2024-06-03 12:00:00'),
  ---  ('user3', '2024-06-04 13:00:00'),
  --  ('user4', '2024-06-05 14:00:00');
GO

-- Nhập dữ liệu mẫu vào bảng OrderDetail
--INSERT INTO OrderDetail (OrderID, ProductID, Quantity, Price)
--VALUES 
 --   (1, 80, 1, 599.99),
 --   (2, 81, 1, 499.99),
   -- (3, 83, 1, 89.99),
   -- (4, 82, 1, 59.99),
   -- (5, 78, 1, 799.99);
--GO

--delete from OrderDetail
--delete from [Order]
--delete from Product
--delete from [User]



-- Nhập dữ liệu mẫu vào bảng [User]
-- Căn cứ vào bảng Customers trong ShopDB
-- Username tự cho
-- DOB sẽ có dạng yyyy-mm-dd
-- Sex gồm F và M (đừng đổi giới tính của ai nhe)
-- Role gồm có 2 loại: User và Admin, hãy cho cả nhóm chúng ta làm Admin, còn lại là User
-- Wallet mỗi người vài chục triệu đến vài tỷ
-- QUAN TRỌNG: Mật khẩu được băm bằng hàm SHA-256, hãy tạo mật khẩu cho các tài khoản theo ý bạn, sau đó truy cập https://emn178.github.io/online-tools/sha256.html để mã hóa mật khẩu và điền vào cột Password, còn mật khẩu gốc thì comment cùng hàng như mẫu phía dưới 
INSERT INTO [User] (Username, [Password], FullName, DOB, Sex, Email, PhoneNumber, [Address], [Role], Wallet)
VALUES 
    (N'princess123', '7dc8e1ac1994e90391f6f5a35b7eec5f55655f0a2229a5c1678a0f67cab653f5', N'Lê Khắc Huy', '2004-09-28', 'Male', 'huylkce180311@fpt.edu.vn', '0976840745', N'Cần Thơ', 'Admin', 9858453525.00), -- rghs
	(N'minecrafter', '9970626666560a32465d4ce10d28f3233365af833e15eed59884d9477862c379', N'Mai Hoàng Ân', '2004-06-05', 'Male', 'anmhce180552@fpt.edu.vn', '0353480984', N'Sóc Trăng', 'Admin', 554284525.00), -- minecraft
	(N'katorivn321', '4697327cdd5bc21216e28e8fdb4d71d8b0e9d181786b18e478d112b5181b4174', N'Dương Nhật Anh', '2004-12-22', 'Male', 'anhdnce181079@fpt.edu.vn', '0379697687', N'Sóc Trăng', 'Admin', 9999999999.00), -- katorivn
	(N'arii3123', '691344282201b4cceee6e8ba59add03822e35a1eb7cea0a5e5865549aee880e6', N'Võ Trương Nhật Đăng', '2004-03-31', 'Male', 'dangvtnce181699@fpt.edu.vn', '0946680941', N'Cà Mau', 'Admin', 765865488.00), -- arii
	(N'tomandmeow', '404cdd7bc109c432f8cc2443b45bcfe95980f5107215c645236e577929ac3e52', N'Nguyễn Tấn Minh', '2004-03-03', 'Male', 'minhntce180111@fpt.edu.vn', '0976867579', N'Vĩnh Long', 'Admin', 564257661.00), -- meow
	(N'lythimy123', 'ef8c9d7c7629102f798222abf61655155efd81d0a1c478ca23f8a3b650d42b9d', N'Lý Thị Mỹ', '1990-01-01', 'Female', 'lythimy654@gmail.com', '0585423100', N'Hà Tĩnh', 'User', 153104009.00), --mythily
	(N'dangthang456', '6587a01f0d02251453f14af3f4856630f38447622e8b90d7e6b1f817afa31ed4', N'Đặng Văn Thắng', '1985-05-15', 'Male', 'dangvanthang987@gmail.com', '0846569967', N'Đồng Nai', 'User', 419440875.00), --thangvandang
	(N'vuthinga789', '33e16fa352e56454321d6708fdf186e04483d63c56d9b810c23f0d47eece4c76', N'Vũ Thị Nga', '1992-07-20', 'Female', 'vuthinga654@gmail.com', '0826747805', N'Bình Dương', 'User', 738530338.00), --ngathivu
	(N'phanvanduong', '7d77eef7229a4473c52aa7487fd9e5427e86bf37e0cf44dd49cec1cdde971de0', N'Phan Văn Dương', '1988-12-10', 'Male', 'phanvanduong789@gmail.com', '0941241488', N'Hải Dương', 'User', 11701891.00), --duongvanphan
	(N'buithinhu123', '63839aefa88eb208ee8acb7bd63f9acf85927518c2230409f5338ba260f5f0c4', N'Bùi Thị Như', '1995-03-25', 'Female', 'buithinhu123@gmail.com', '0791115717', N'Hải Phòng', 'User', 548755681.00), --nhuthibui
	(N'hoangthiloan456', '5bfca6a67bf65f6ef88062cf83ca8910d964292300869c196fa3693f61654843', N'Hoàng Thị Loan', '1972-02-11', 'Female', 'hoangthiloan456@gmail.com', '0374666559', N'Thái Bình', 'User', 2019243000.00), -- hoangthiloan
	(N'maivantam012', '88c67adee9d331a58b13d2d7179c618961347f99530cf55bad754020a7f73632', N'Mai Văn Tâm', '1993-12-27', 'Male', 'maivantam012@gmail.com', '0990221972', N'Tiền Giang', 'User', 723114000.00), -- maivantam
	(N'dothilan321', '895c05cd6240066a6419b73ba0926ee9ff8c1b9072304dac7a16c63ec4520b0d', N'Đỗ Thị Lan', '1990-03-17', 'Female', 'dothilan321@gmail.com', '0312363701', N'Hà Nam', 'User', 46039500.00), -- dothilan
	(N'luongvanlam654', '630bf6112e079d6d914eb72c1601ee8f1503d6b50214e72fec163aceee4cfc92', N'Lương Văn Lâm', '1998-04-01', 'Male', 'luongvanlam654@gmail.com', '0968196570', N'Quảng Bình', 'User', 803592010.00), -- luongvanlam
	(N'truongthilinh789', '8376fdf76cf93d22fd8f99a46121665562dd2d8306104cac9290f0cfeb469c0b', N'Trương Thị Linh', '1994-09-08', 'Female', 'truongthilinh789@gmail.com', '0825213303', N'Nghệ An', 'User', 671222000.00), -- truongthilinh
	(N'vovanson123', 'c82de2c6c54792fda9ceebd820134c70aa316a153dcb797bd72c6df59dd8c7c7', N'Võ Văn Sơn', '1984-02-29', 'Male', 'vovanson123@gmail.com', '0998586879', N'Quảng Ngãi', 'User', 43997373.00), -- vovanson
	(N'macthihue456', '8c39e9b3e0ae0f4227d40d4caee1e611622ede60f250b7824b7d0d910a26f608', N'Mạc Thị Huệ', '1992-01-30', 'Female', 'macthihue456@gmail.com', '0838819675', N'Bình Thuận', 'User', 2000000001.00), -- macthihue
	(N'chauvantung012', '06384077606f3f71d53ab6da9f2d4414e23786150e71361a2080b363c5522dd0', N'Châu Văn Tùng', '1979-06-10', 'Male', 'chauvantung012@gmail.com', '0989207533', N'Bà Rịa - Vũng Tàu', 'User', 6912130400.00), -- chauvantung
	(N'lethihoa321', '7eb5de9cb4b68f4bde9ebe4c1ccf01a658eb0d70f62f2f9512ea6ef258781a2c', N'Lê Thị Hoa', '1982-12-25', 'Female', 'lethihoa321@gmail.com', '0781781305', N'Long An', 'User', 774999810.00), -- lethihoa
	(N'nguyenvanloc654', '663539dd74f15752fbc21c97af6bfab147c8aeae6c65f9a8d34c196af5d5c934', N'Nguyễn Văn Lộc', '1997-08-13', 'Male', 'nguyenvanloc654@gmail.com', '0927952529', N'Lào Cai', 'User', 70539230.00) -- nguyenvanloc
