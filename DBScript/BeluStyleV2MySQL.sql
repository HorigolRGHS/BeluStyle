-- Tạo database
CREATE DATABASE BeluStyle;
USE BeluStyle;

-- Tạo bảng Account
CREATE TABLE users (
  user_id VARCHAR(255) NOT NULL PRIMARY KEY,
  username VARCHAR(255) UNIQUE NULL,
  google_id VARCHAR(255) DEFAULT NULL,
  email VARCHAR(255) DEFAULT NULL,
  password_hash VARCHAR(255) DEFAULT NULL,
  full_name VARCHAR(50) DEFAULT NULL,
  user_image VARCHAR(255) DEFAULT NULL,
  `enable` BOOLEAN DEFAULT NULL,
  current_payment_method VARCHAR(50) DEFAULT NULL,
  create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE user_roles (
  username VARCHAR(255) NOT NULL,
  authority ENUM('ROLE_STAFF', 'ROLE_ADMIN', 'ROLE_CUSTOMER') NOT NULL,
  PRIMARY KEY (username, authority),
  FOREIGN KEY (username) REFERENCES users(username)
);

-- Tạo bảng Category
CREATE TABLE category (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(255) NOT NULL,
    category_description TEXT,
    image_url VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tạo bảng Brand
CREATE TABLE brand (
    brand_id INT PRIMARY KEY AUTO_INCREMENT,
    brand_name VARCHAR(255) NOT NULL,
    brand_description TEXT,
    logo_url VARCHAR(255),
    website_url VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tạo bảng Product
CREATE TABLE product (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(255) NOT NULL,
    category_id INT,
    brand_id INT,
    product_price DECIMAL(10,2) NOT NULL,
    product_saleprice DECIMAL(10,2),
    product_description TEXT,
    product_totalquantity INT,
    product_image VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES category(category_id),
    FOREIGN KEY (brand_id) REFERENCES brand(brand_id)
);

CREATE TABLE review (
    review_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id VARCHAR(255),
    product_id INT,
    review_rating INT CHECK (review_rating BETWEEN 1 AND 5), 
    review_comment TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES product(product_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) 
);

CREATE TABLE product_attributes (
    product_attribute_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT,
    product_size VARCHAR(50),
    product_color VARCHAR(50),
    product_quantity INT,
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

CREATE TABLE `order` (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id VARCHAR(255),
    order_date DATETIME,
    total__total_amount DECIMAL(10,2),
    order_status ENUM('pending', 'processing', 'shipped', 'delivered', 'cancelled'),
    shipping_address VARCHAR(255),
    payment_method ENUM('cod', 'VNPAY', 'Momo'),
    shipping_method VARCHAR(50),
    tracking_number VARCHAR(50),
    notes TEXT,
    coupon_code VARCHAR(50),
    discount_amount DECIMAL(10,2),
    billing_address VARCHAR(255),
    expected_delivery_date DATE,
    transaction_reference VARCHAR(255),
    bank_code VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES users(user_id) 
);

CREATE TABLE order_detail (
    order_detail_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    product_id INT,
    order_quantity INT,
    unit_price DECIMAL(10, 2),
    discount_amount DECIMAL(10, 2),
    total_price DECIMAL(10, 2),
    FOREIGN KEY (order_id) REFERENCES `order`(order_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

CREATE TABLE discount (
    discount_id INT PRIMARY KEY AUTO_INCREMENT,
    discount_code VARCHAR(20) NOT NULL,
    discount_type ENUM('percentage', 'fixed_amount') NOT NULL,
    discount_value DECIMAL(10, 2) NOT NULL,
    start_date DATE,
    end_date DATE,
    discount_status ENUM('active', 'expired', 'used') NOT NULL,
    discount_description TEXT,
    minimum_order_value DECIMAL(10, 2),
    maximum_discount_value DECIMAL(10, 2),
    usage_limit INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE user_discount (
    user_discount_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id VARCHAR(255), -- Assuming you're using VARCHAR for user_id
    discount_id INT NOT NULL,
    is_used BOOLEAN, -- Track if the user has used this discount
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (discount_id) REFERENCES discount(discount_id)
);

CREATE TABLE sale (
    sale_id INT PRIMARY KEY AUTO_INCREMENT,
	discount_type ENUM('percentage', 'fixed_amount') NOT NULL,
    discount_value DECIMAL(10, 2) NOT NULL,
    start_date DATE,
    end_date DATE,
    sale_status ENUM('active', 'inactive') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE sale_product (
  sale_id INT NOT NULL,
  product_id INT NOT NULL,
  FOREIGN KEY (sale_id) REFERENCES sale(sale_id),
  FOREIGN KEY (product_id) REFERENCES product(product_id)
)
