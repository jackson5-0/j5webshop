delete from basket_item;
delete from product;
delete from basket;

insert into basket(id, users_id) values (1,1);
insert into basket(id, users_id) values (2,2);

insert into product (id, code, name, address, publisher, price, status) values
    (1, "GEMHAC01", "Hacker játszma", "hacker-jatszma", "Gém Klub Kft.", 3190, "ACTIVE");

insert into product (id, code, name, address, publisher, price, status) values
    (2, "GEMDIX01", "Dixit", "dixit", "Gém Klub Kft.", 7990, "ACTIVE");

insert into product (id, code, name, address, publisher, price, status) values
    (3, "AVALOR01", "Lord of Hellas", "lord-of-hellas", "Avaken Realms", 35990, "ACTIVE");

insert into product (id, code, name, address, publisher, price, status) values
    (4, "DELTRO01", "Trónok harca", "tronok-harca", "Delta Vision", 17990, "ACTIVE");

insert into basket_item (basket_id, product_id) values(1, 1);
insert into basket_item (basket_id, product_id) values(1, 2);
insert into basket_item (basket_id, product_id) values(1, 3);