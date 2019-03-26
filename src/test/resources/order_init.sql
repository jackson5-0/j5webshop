delete from basket_item;
delete from order_item;
delete from basket;
delete from orders;
delete from product;
delete from users;

ALTER TABLE orders AUTO_INCREMENT = 1;

insert into product (id, code, name, address, publisher, price, status) values
    (1, "GEMHAC01", "Hacker játszma", "hacker-jatszma", "Gém Klub Kft.", 3190, "ACTIVE");

insert into product (id, code, name, address, publisher, price, status) values
    (2, "GEMDIX01", "Dixit", "dixit", "Gém Klub Kft.", 7990, "ACTIVE");

insert into product (id, code, name, address, publisher, price, status) values
    (3, "AVALOR01", "Lord of Hellas", "lord-of-hellas", "Avaken Realms", 35990, "ACTIVE");

insert into product (id, code, name, address, publisher, price, status) values
    (4, "DELTRO01", "Trónok harca", "tronok-harca", "Delta Vision", 17990, "ACTIVE");


insert into users (id, firstname, lastname, username, password, enabled, role) values
    (1, "Géza", "Kovács", "kovacsgeza", "KovacsGeza90", 1, "ROLE_USER");

insert into users(id, firstname, lastname, username, password, enabled, role) values
    (2, "Gizella", "Nagy", "nagygizi22", "GiziAZizi11", 1, "ROLE_USER");

insert into users (id, firstname, lastname, username, password, enabled, role) values
    (3, "Adrienn", "Tóth", "tadri1988", "Tadri1988", 1, "ROLE_USER");

insert into users (id, firstname, lastname, username, password, enabled, role) values
    (4, "Béla", "Kiss", "kissbeci", "kissbeci00", 1, "ROLE_USER");

insert into basket(id, users_id) values (1,1);
insert into basket(id, users_id) values (2,2);
insert into basket(id, users_id) values (3,3);
insert into basket(id, users_id) values (4,4);

insert into basket_item(id, basket_id, product_id) values
    (1, 2, 2);
insert into basket_item(id, basket_id, product_id) values
    (2, 2, 3);
insert into basket_item(id, basket_id, product_id) values
    (3, 2, 4);
insert into basket_item(id, basket_id, product_id) values
    (4, 3, 2);
insert into basket_item(id, basket_id, product_id) values
    (5, 3, 3);

insert into orders (id, user_id, status, purchase_date) values
(1, 2, "DELETED", "2019-03-25");
insert into orders (id, user_id, status, purchase_date) values
(2, 3, "ACTIVE", "2019-03-26");
insert into orders (id, user_id, status, purchase_date) values
(3, 4, "ACTIVE", "2019-03-24");
insert into orders (id, user_id, status, purchase_date) values
(4, 4, "ACTIVE", "2019-03-26");
insert into orders (id, user_id, status, purchase_date) values
(5, 4, "DELIVERED", "2019-03-25");
insert into orders (id, user_id, status, purchase_date) values
(6, 4, "DELETED", "2019-03-23");



insert into orders (id, user_id, status, purchase_date) values
(3, 2, "DELIVERED", "2019-03-26");
insert into orders (id, user_id, status, purchase_date) values
(3, 4, "ACTIVE", "2019-03-24");
insert into orders (id, user_id, status, purchase_date) values
(4, 4, "ACTIVE", "2019-03-26");
insert into orders (id, user_id, status, purchase_date) values
(5, 4, "DELIVERED", "2019-03-25");
insert into orders (id, user_id, status, purchase_date) values
(6, 4, "DELETED", "2019-03-23");



