delete from product;
delete from users;
delete from basket;

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