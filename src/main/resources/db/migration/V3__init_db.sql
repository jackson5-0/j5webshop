CREATE TABLE order (
id BIGINT AUTO_INCREMENT,
user_id BIGINT,
status VARCHAR(100),
purchase_date datetime,
constraint pk_order PRIMARY KEY (id),
constraint fk_order_users FOREIGN KEY order(user_id) REFERENCES users(id),
)
engine=InnoDb character set = UTF8 collate = utf8_hungarian_ci;

CREATE TABLE order_item(
id BIGINT AUTO_INCREMENT,
order_id BIGINT,
product_id BIGINT,
price BIGINT,
constraint pk_order_item PRIMARY KEY(id),
constraint fk_order_item_order FOREIGN KEY order_item(order_id) REFERENCES order(id),
constraint fk_order_item_product FOREIGN KEY order_item(product_id) REFERENCES product(id)
)
engine=InnoDb character set = UTF8 collate = utf8_hungarian_ci;