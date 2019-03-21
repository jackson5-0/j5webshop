package training360.j5webshop.products;

import java.text.Normalizer;

public class Product {
   private String code;
   private String name;
   private String address;
   private String publisher;
   private int price;
   private String postfix = "01";

    public Product() {

    }

    public Product(String code, String name, String address, String publisher, int price) {
        this(name,publisher,price);
        this.code = code;
        this.address = address;
    }

    public Product(String name, String publisher, int price) {
        this.name = name;
        this.publisher = publisher;
        this.price = price;
    }

    public void setCodeAndAddress(){
        String validName = Normalizer.normalize(this.name,Normalizer.Form.NFKD).replaceAll("\\p{M}", "");
        String validPublisher = Normalizer.normalize(this.publisher,Normalizer.Form.NFKD).replaceAll("\\p{M}", "");

        String code = validName.substring(0,3).toUpperCase() + validPublisher.substring(0,3).toUpperCase() + postfix;
        String address = validName.trim().toLowerCase().replace(' ','-') + postfix;

        setCode(code);
        setAddress(address);
    }
    public void incrementPostFix(){
        postfix = String.format("%02d",(Integer.parseInt(postfix)+1));
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

    @Override
    public String toString() {
        return "Product{" +
                "code='" + code + '\'' +
                '}';
    }
}
