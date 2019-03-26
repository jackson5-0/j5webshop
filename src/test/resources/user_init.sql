delete from basket;
delete from users;

insert into users (firstname, lastname, username, password, enabled, role) values
    ("Géza", "Kovács", "kovacsgeza", "KovacsGeza90", 1, "ROLE_USER");

insert into users(firstname, lastname, username, password, enabled, role) values
    ("Gizella", "Nagy", "nagygizi22", "GiziAZizi11", 1, "ROLE_USER");

insert into users (firstname, lastname, username, password, enabled, role) values
    ("Adrienn", "Tóth", "tadri1988", "Tadri1988", 1, "ROLE_USER");

insert into users (firstname, lastname, username, password, enabled, role) values
    ("Béla", "Kiss", "kissbeci", "kissbeci00", 1, "ROLE_USER");