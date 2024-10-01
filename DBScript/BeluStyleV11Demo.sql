-- Tạo database
DROP DATABASE IF EXISTS BeluStyle;
CREATE DATABASE BeluStyle;
USE BeluStyle;

CREATE TABLE user_role (
    role_id INT PRIMARY KEY AUTO_INCREMENT,
    role_name ENUM('ADMIN', 'CUSTOMER', 'STAFF') NOT NULL
);

-- Tạo bảng Account
CREATE TABLE `user` (
  user_id VARCHAR(255) NOT NULL PRIMARY KEY,
  username VARCHAR(255) UNIQUE NULL,
  google_id VARCHAR(255) DEFAULT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  password_hash VARCHAR(255) DEFAULT NULL,
  full_name VARCHAR(50) CHARACTER SET UTF8MB4 DEFAULT NULL,
  user_image VARCHAR(255) DEFAULT NULL,
  `enable` BOOLEAN DEFAULT NULL,
  role_id INT,
  current_payment_method VARCHAR(50) DEFAULT NULL,
  user_address VARCHAR(255) CHARACTER SET UTF8MB4 DEFAULT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (role_id) REFERENCES user_role(role_id)
);

-- Tạo bảng Category
CREATE TABLE category (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(255) CHARACTER SET UTF8MB4 NOT NULL,
    category_description TEXT CHARACTER SET UTF8MB4,
    image_url VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Để tạm nếu không cho thiết kế shop local brand
CREATE TABLE brand (
    brand_id INT PRIMARY KEY AUTO_INCREMENT,
    brand_name VARCHAR(255) CHARACTER SET UTF8MB4 NOT NULL,
    brand_description TEXT CHARACTER SET UTF8MB4,
    logo_url VARCHAR(255),
    website_url VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tạo bảng Product
CREATE TABLE product (
    product_id VARCHAR(255) PRIMARY KEY,
    product_name VARCHAR(255) CHARACTER SET UTF8MB4 NOT NULL,
    category_id INT,
    brand_id INT,
    product_description TEXT CHARACTER SET UTF8MB4,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES category(category_id),
    FOREIGN KEY (brand_id) REFERENCES brand(brand_id)
);

CREATE TABLE size (
size_id INT PRIMARY KEY AUTO_INCREMENT,
size_name VARCHAR(10)
);

CREATE TABLE color (
color_id INT PRIMARY KEY AUTO_INCREMENT,
color_name VARCHAR(10) CHARACTER SET UTF8MB4,
hex_code VARCHAR(10)
);

-- Sản phẩm đó với kích thước, màu, già biến đổi còn bao nhiu hàng
CREATE TABLE stock(
 stock_id INT PRIMARY KEY AUTO_INCREMENT,
 stock_name VARCHAR(255) CHARACTER SET UTF8MB4 NOT NULL,
 stock_address VARCHAR(255) CHARACTER SET UTF8MB4 NOT NULL
);

CREATE TABLE product_variation (
    variation_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id VARCHAR(255),
    size_id INT,
    color_id INT,
    product_variation_image VARCHAR(255),
    product_price DECIMAL(10,2), -- Giá biến đổi khi khác màu,....
    FOREIGN KEY (product_id) REFERENCES product(product_id),
    FOREIGN KEY (size_id) REFERENCES size(size_id),
    FOREIGN KEY (color_id) REFERENCES color(color_id)
);

CREATE TABLE stock_product(
    variation_id INT NOT NULL,
    stock_id INT NOT NULL,
    quantity INT,
    PRIMARY KEY (variation_id, stock_id),
    FOREIGN KEY (variation_id) REFERENCES product_variation(variation_id),
    FOREIGN KEY (stock_id) REFERENCES stock(stock_id)
);

CREATE TABLE stock_transaction(
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    stock_id INT NOT NULL,
    variation_id INT NOT NULL,
    transaction_type ENUM('IN', 'OUT') NOT NULL,
    quantity INT NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (stock_id) REFERENCES stock(stock_id),
    FOREIGN KEY (variation_id) REFERENCES product_variation(variation_id)
);


CREATE TABLE `order` (
    order_id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255),
    order_date DATETIME,
    total_amount DECIMAL(10,2),
    order_status ENUM('pending', 'processing', 'shipped', 'delivered', 'cancelled'),
    user_address VARCHAR(255) CHARACTER SET UTF8MB4,
    payment_method ENUM('COD', 'VNPAY', 'PayOS', 'TRANSFER'),
    shipping_method VARCHAR(50),
    tracking_number VARCHAR(50),
    notes TEXT CHARACTER SET UTF8MB4,
    discount_code VARCHAR(50),
    billing_address VARCHAR(255),
    expected_delivery_date DATE,
    transaction_reference VARCHAR(255), -- Mã thanh toán của VNPAY là 6 số và MOMO là 15 cả chữ và số
    staff_id VARCHAR(255), -- Nhân viên trực sẽ xác nhận đơn hàng.(pending -> processing)
    FOREIGN KEY (user_id) REFERENCES `user`(user_id) ON DELETE SET NULL,
    FOREIGN KEY (staff_id) REFERENCES `user`(user_id) ON DELETE SET NULL
);

CREATE TABLE order_detail (
    order_detail_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id VARCHAR(255),
    variation_id INT,
    order_quantity INT,
    unit_price DECIMAL(10, 2),
    discount_amount DECIMAL(10, 2),
    FOREIGN KEY (order_id) REFERENCES `order`(order_id),
    FOREIGN KEY (variation_id) REFERENCES product_variation(variation_id)
);

CREATE TABLE review (
    review_id INT PRIMARY KEY AUTO_INCREMENT,
	order_detail_id INT,
    product_id VARCHAR(255),
    review_rating INT CHECK (review_rating BETWEEN 1 AND 5), 
    review_comment TEXT CHARACTER SET UTF8MB4,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_detail_id) REFERENCES order_detail(order_detail_id)
);

CREATE TABLE discount (
    discount_id INT PRIMARY KEY AUTO_INCREMENT,
    discount_code VARCHAR(20) NOT NULL,
    discount_type ENUM('percentage', 'fixed_amount') NOT NULL,
    discount_value DECIMAL(10, 2) NOT NULL,
    start_date DATE,
    end_date DATE,
    discount_status ENUM('active', 'expired', 'used') NOT NULL,
    discount_description TEXT CHARACTER SET UTF8MB4,
    minimum_order_value DECIMAL(10, 2),
    maximum_discount_value DECIMAL(10, 2),
    usage_limit INT DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE user_discount (
    user_discount_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id VARCHAR(255),
    discount_id INT NOT NULL,
    usage_count INT DEFAULT 0, -- Đếm số lần mã giảm giá đã được sử dụng
    used_at DATETIME DEFAULT NULL, -- Thời gian mã giảm giá được sử dụng
    FOREIGN KEY (user_id) REFERENCES `user`(user_id) ON DELETE SET NULL,
    FOREIGN KEY (discount_id) REFERENCES discount(discount_id) ON DELETE CASCADE
);


CREATE TABLE sale (
    sale_id INT PRIMARY KEY AUTO_INCREMENT,
	sale_type ENUM('percentage', 'fixed amount') NOT NULL,
    sale_value DECIMAL(10, 2) NOT NULL,
    start_date DATE,
    end_date DATE,
    sale_status ENUM('active', 'inactive', 'expired') NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE sale_product (
  sale_id INT NOT NULL,
  product_id VARCHAR(255) NOT NULL,
  FOREIGN KEY (sale_id) REFERENCES sale(sale_id) ON DELETE CASCADE,
  FOREIGN KEY (product_id) REFERENCES product(product_id)
);

CREATE TABLE notification (
    notification_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) CHARACTER SET UTF8MB4 NOT NULL,
    message TEXT CHARACTER SET UTF8MB4 NOT NULL,
    target_role_id INT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (target_role_id) REFERENCES user_role(role_id)
);

-- Dữ liệu --
INSERT INTO size (size_name) VALUES
('S'),
('M'),
('L'),
('XL'),
('XXL');

INSERT INTO color (color_name, hex_code) VALUES
('Red', '#FF0000'),
('Green', '#00FF00'),
('Blue', '#0000FF'),
('Black', '#000000'),
('White', '#FFFFFF'),
('Yellow', '#FFFF00'),
('Cyan', '#00FFFF'),
('Magenta', '#FF00FF');

-- CÁC EVENT TỰ ĐỘNG CẬP NHẬT --
SET GLOBAL event_scheduler = ON;
-- cập nhật trạng thái mã giảm giá
CREATE EVENT update_discount_status
ON SCHEDULE EVERY 1 DAY
STARTS '2024-09-08 00:00:00' -- Bắt đầu từ ngày và giờ cụ thể
DO
  UPDATE discount
  SET discount_status = 'expired'
  WHERE end_date < CURDATE()
  AND discount_status != 'expired';
-- Cập nhật trạng thái sale 
CREATE EVENT update_sale_status
ON SCHEDULE EVERY 1 DAY
STARTS '2024-09-08 00:00:00' -- Thời điểm bắt đầu chạy event
DO
  UPDATE sale
  SET sale_status = 'expire'
  WHERE end_date < CURDATE()
  AND sale_status != 'expire';

-- admin view user permission
CREATE VIEW user_admin_view AS
SELECT
    user_id,
    username,
    email,
    full_name
FROM
    user;

-- Insert data into user_role
INSERT INTO user_role (role_name) VALUES ('ADMIN'), ('CUSTOMER'), ('STAFF');

-- SET SQL_SAFE_UPDATES = 0;
-- delete from `user`

-- Insert data into `user`
INSERT INTO `user` (user_id, username, google_id, email, password_hash, full_name, user_image, `enable`, role_id, current_payment_method, user_address)
VALUES 
('U006', 'alicewonder', NULL, 'alice@example.com', '$2a$10$HTR7ETduTLTWSlP/7Eaks.CtvTCc5Cq78CcOC3SCT.twp/jkTIS1K', 'Alice Wonder', 'image6.jpg', 1, 1, 'VISA', '789 Someplace, City'), -- password12345




-- Insert data into category
INSERT INTO category (category_name, category_description, image_url)
VALUES 
('Electronics', 'Devices and gadgets', 'electronics.jpg'),
('Clothing', 'Men and women clothing', 'clothing.jpg'),
('Books', 'Educational and fictional books', 'books.jpg'),
('Home Appliances', 'Appliances for home use', 'home_appliances.jpg'),
('Toys', 'Toys for kids of all ages', 'toys.jpg');

-- Insert data into brand
INSERT INTO brand (brand_name, brand_description, logo_url, website_url)
VALUES 
('BrandA', 'Description for BrandA', 'logo1.jpg', 'https://www.brandA.com'),
('BrandB', 'Description for BrandB', 'logo2.jpg', 'https://www.brandB.com'),
('BrandC', 'Description for BrandC', 'logo3.jpg', 'https://www.brandC.com'),
('BrandD', 'Description for BrandD', 'logo4.jpg', 'https://www.brandD.com'),
('BrandE', 'Description for BrandE', 'logo5.jpg', 'https://www.brandE.com');


-- Insert data into product
INSERT INTO product (product_id, product_name, category_id, brand_id, product_description)
VALUES 
('P001', 'Smartphone', 1, 1, 'Latest smartphone with advanced features'),
('P002', 'Jeans', 2, 2, 'Comfortable blue jeans'),
('P003', 'Novel', 3, 3, 'Fictional novel by a famous author'),
('P004', 'Washing Machine', 4, 4, 'Energy-efficient washing machine'),
('P005', 'Action Figure', 5, 5, 'Collectible action figure');

-- Insert data into size
INSERT INTO size (size_name) 
VALUES ('S'), ('M'), ('L'), ('XL'), ('XXL');

-- Insert data into color
INSERT INTO color (color_name, hex_code) 
VALUES ('Red', '#FF0000'), ('Blue', '#0000FF'), ('Green', '#008000'), ('Yellow', '#FFFF00'), ('Black', '#000000');

-- Insert data into stock
INSERT INTO stock (stock_name, stock_address)
VALUES 
('Warehouse 1', '123 Storage Road, City'),
('Warehouse 2', '456 Supply Blvd, City'),
('Warehouse 3', '789 Goods Ave, City'),
('Warehouse 4', '1010 Distribution Ln, City'),
('Warehouse 5', '1111 Reserve Dr, City');

-- Insert data into product_variation
INSERT INTO product_variation (product_id, size_id, color_id, product_variation_image, product_price)
VALUES 
('P001', 1, 1, 'smartphone_red.jpg', 499.99),
('P002', 2, 2, 'jeans_blue.jpg', 39.99),
('P003', 3, 3, 'novel_green.jpg', 9.99),
('P004', 4, 4, 'washingmachine_yellow.jpg', 299.99),
('P005', 5, 5, 'actionfigure_black.jpg', 19.99);

-- Insert data into stock_product
INSERT INTO stock_product (variation_id, stock_id, quantity)
VALUES 
(1, 1, 50), 
(2, 2, 100), 
(3, 3, 200), 
(4, 4, 75), 
(5, 5, 150);

-- Insert data into stock_transaction
INSERT INTO stock_transaction (stock_id, variation_id, transaction_type, quantity)
VALUES 
(1, 1, 'IN', 50),
(2, 2, 'OUT', 20),
(3, 3, 'IN', 100),
(4, 4, 'OUT', 10),
(5, 5, 'IN', 150);

-- Insert data into `order`
INSERT INTO `order` (order_id, user_id, order_date, total_amount, order_status, user_address, payment_method, shipping_method, tracking_number, discount_code, billing_address, expected_delivery_date, transaction_reference, staff_id)
VALUES 
('O001', 'U001', '2024-09-27 10:00:00', 100.00, 'pending', '123 Street, City', 'VNPAY', 'Standard', 'TN123', 'DISCOUNT10', '123 Street, City', '2024-10-01', 'VN123456', 'U003'),
('O002', 'U002', '2024-09-26 14:00:00', 50.00, 'shipped', '456 Avenue, City', 'COD', 'Express', 'TN124', 'DISCOUNT20', '456 Avenue, City', '2024-09-30', 'VN654321', 'U003'),
('O003', 'U003', '2024-09-25 16:00:00', 75.00, 'delivered', '789 Boulevard, City', 'TRANSFER', 'Standard', 'TN125', 'DISCOUNT15', '789 Boulevard, City', '2024-09-29', 'VN789101', 'U003'),
('O004', 'U004', '2024-09-24 12:00:00', 120.00, 'processing', '111 Road, City', 'PayOS', 'Standard', 'TN126', 'DISCOUNT5', '111 Road, City', '2024-09-28', 'VN321654', 'U005'),
('O005', 'U005', '2024-09-23 18:00:00', 150.00, 'cancelled', '222 Street, City', 'COD', 'Express', 'TN127', 'DISCOUNT25', '222 Street, City', '2024-09-27', 'VN852963', 'U003');

-- Insert data into order_detail
INSERT INTO order_detail (order_id, variation_id, order_quantity, unit_price, discount_amount)
VALUES 
('O001', 1, 2, 49.99, 5.00),
('O002', 2, 1, 39.99, 2.00),
('O003', 3, 3, 9.99, 1.00),
('O004', 4, 1, 299.99, 10.00),
('O005', 5, 2, 19.99, 3.00);

-- Insert data into review
INSERT INTO review (order_detail_id, product_id, review_rating, review_comment)
VALUES 
(1, 'P001', 5, 'Great product!'),
(2, 'P002', 4, 'Very comfortable.'),
(3, 'P003', 5, 'Amazing read.'),
(4, 'P004', 3, 'Good performance, but a bit noisy.'),
(5, 'P005', 5, 'Awesome action figure!');

-- Insert data into discount
INSERT INTO discount (discount_code, discount_type, discount_value, start_date, end_date, discount_status, discount_description, minimum_order_value, maximum_discount_value, usage_limit)
VALUES 
('DISCOUNT10', 'percentage', 10.00, '2024-09-01', '2024-10-01', 'active', '10% off on orders above $50', 50.00, 20.00, 5),
('DISCOUNT20', 'fixed_amount', 20.00, '2024-08-15', '2024-09-30', 'expired', 'Flat $20 off on orders above $100', 100.00, 25.00, 2),
('DISCOUNT5', 'percentage', 5.00, '2024-07-01', '2024-12-31', 'active', '5% off on all orders', 0.00, 10.00, 10),
('DISCOUNT15', 'percentage', 15.00, '2024-09-01', '2024-10-15', 'active', '15% off on orders above $75', 75.00, 25.00, 3),
('DISCOUNT25', 'fixed_amount', 25.00, '2024-06-01', '2024-09-30', 'expired', 'Flat $25 off on orders above $150', 150.00, 30.00, 1);

-- Insert data into user_discount
INSERT INTO user_discount (user_id, discount_id, usage_count, used_at)
VALUES 
('U001', 1, 1, '2024-09-25 14:00:00'),
('U002', 2, 2, '2024-09-20 12:00:00'),
('U003', 3, 0, NULL),
('U004', 4, 1, '2024-09-23 16:00:00'),
('U005', 5, 1, '2024-09-21 10:00:00');

-- Insert data into sale
INSERT INTO sale (sale_type, sale_value, start_date, end_date, sale_status)
VALUES 
('percentage', 20.00, '2024-09-01', '2024-09-30', 'active'),
('fixed amount', 50.00, '2024-08-01', '2024-09-15', 'expired'),
('percentage', 10.00, '2024-10-01', '2024-10-31', 'inactive'),
('fixed amount', 25.00, '2024-09-15', '2024-10-15', 'active'),
('percentage', 5.00, '2024-07-01', '2024-12-31', 'active');

-- Insert data into sale_product
INSERT INTO sale_product (sale_id, product_id)
VALUES 
(1, 'P001'),
(2, 'P002'),
(3, 'P003'),
(4, 'P004'),
(5, 'P005');

-- Insert data into notification
INSERT INTO notification (title, message, target_role_id)
VALUES 
('New Sale!', '20% off on all products!', 2),
('Order Shipped', 'Your order has been shipped!', 2),
('Admin Alert', 'System maintenance scheduled.', 1),
('Staff Meeting', 'Staff meeting on Friday at 3 PM.', 3),
('New Discount', 'Get $10 off on your next purchase.', 2);
