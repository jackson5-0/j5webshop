package training360.j5webshop.users;

import training360.j5webshop.orders.Order;

public class Address {
    private long id;
    private User user;
    private Order order;
    private String country;
    private String city_code;
    private String city;
    private String street;
    private int number;
    private String otherInfo;

    public Address(long id, User user, Order order, String country, String city_code, String city, String street, int number, String otherInfo) {
        this.id = id;
        this.user = user;
        this.order = order;
        this.country = country;
        this.city_code = city_code;
        this.city = city;
        this.street = street;
        this.number = number;
        this.otherInfo = otherInfo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }
}
