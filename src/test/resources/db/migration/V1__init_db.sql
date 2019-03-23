CREATE TABLE Product (
id BIGINT AUTO_INCREMENT,
code VARCHAR(100) UNIQUE,
name VARCHAR(255),
address VARCHAR(255) UNIQUE,
publisher VARCHAR(255),
price BIGINT,
status VARCHAR(255),
constraint pk_Product PRIMARY KEY (id)
)
engine=InnoDb character set = UTF8 collate = utf8_hungarian_ci;