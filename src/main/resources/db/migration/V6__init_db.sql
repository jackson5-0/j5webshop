CREATE TABLE review (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
product_id BIGINT,
users_id BIGINT,
review_date DATETIME,
message VARCHAR(255),
rate TINYINT,
FOREIGN KEY (product_id) REFERENCES product(id),
FOREIGN KEY (users_id) REFERENCES users(id)
)
engine=InnoDb character set = UTF8 collate = utf8_hungarian_ci;
