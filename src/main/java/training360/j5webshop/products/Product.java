package training360.j5webshop.products;

public class Product {
   private String code;
   private String name;
   private String address;
   private String publisher;
   private int price;

    public Product() {

    }

    public Product(String code, String name, String address, String publisher, int price) {
        this.code = code;
        this.name = name;
        this.address = address;
        this.publisher = publisher;
        this.price = price;
    }

    public Product(String name, String publisher, int price) {
        this.name = name;
        this.publisher = publisher;
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
