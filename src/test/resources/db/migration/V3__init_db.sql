create table orders (
id BIGINT AUTO_INCREMENT,
user_id BIGINT,
basket_id BIGINT,
status VARCHAR(100),
purchase_date datetime,
constraint pk_orders PRIMARY KEY (id),
constraint fk_orders_users FOREIGN KEY orders(user_id) REFERENCES users(id),
constraint fk_orders_basket FOREIGN KEY orders(basket_id) REFERENCES basket(id)
)
engine=InnoDb character set = UTF8 collate = utf8_hungarian_ci;

create table order_item (
id BIGINT AUTO_INCREMENT,
orders_id BIGINT,
product_id BIGINT,
price BIGINT,
constraint pk_order_item PRIMARY KEY(id),
constraint fk_order_item_orders FOREIGN KEY order_item(orders_id) REFERENCES orders(id),
constraint fk_order_item_product FOREIGN KEY order_item(product_id) REFERENCES product(id)
)
engine=InnoDb character set = UTF8 collate = utf8_hungarian_ci;