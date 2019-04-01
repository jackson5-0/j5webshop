CREATE TABLE address (
    id bigint AUTO_INCREMENT,
    user_id bigint,
    order_id bigint,
    country varchar(255),
    city_code varchar(255),
    city varchar(255),
    street varchar(255),
    number varchar(255),
    other_info varchar(255),
    CONSTRAINT pk_address PRIMARY KEY (id),
    CONSTRAINT fk_users FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_orders FOREIGN KEY (order_id) REFERENCES orders(id)
)
engine=InnoDb character set = UTF8 collate = utf8_hungarian_ci;