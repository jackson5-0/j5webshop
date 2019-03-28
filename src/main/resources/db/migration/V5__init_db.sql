CREATE TABLE Category(
    id bigint AUTO_INCREMENT,
    name varchar(100) UNIQUE,
    priority bigint,
    CONSTRAINT pk_category PRIMARY KEY (id)
)

engine=InnoDb character set = UTF8 collate = utf8_hungarian_ci;

CREATE TABLE Product_category (
    id bigint AUTO_INCREMENT,
    product_id bigint,
    category_id bigint,
    CONSTRAINT pk_category PRIMARY KEY (id),
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES Product(id),
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES Category(id)
)

engine=InnoDb character set = UTF8 collate = utf8_hungarian_ci;