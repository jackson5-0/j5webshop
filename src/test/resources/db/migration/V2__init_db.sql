CREATE TABLE users (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
firstname VARCHAR(255),
lastname VARCHAR(255),
username VARCHAR(255) UNIQUE,
password VARCHAR(255),
enabled tinyint,
role VARCHAR(255)
)
engine=InnoDb character set = UTF8 collate = utf8_hungarian_ci;

CREATE TABLE basket (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
users_id BIGINT,
FOREIGN KEY (users_id) REFERENCES users(id)
)
engine=InnoDb character set = UTF8 collate = utf8_hungarian_ci;

CREATE TABLE basket_item (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
basket_id BIGINT,
product_id BIGINT,
FOREIGN KEY (basket_id) REFERENCES basket(id),
FOREIGN KEY (product_id) REFERENCES product(id)
)
engine=InnoDb character set = UTF8 collate = utf8_hungarian_ci;
