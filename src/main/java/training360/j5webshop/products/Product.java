package training360.j5webshop.products;

import java.text.Normalizer;

public class Product {
    private long id;
    private String code;
    private String name;
    private String address;
    private String publisher;
    private int price;
    private String postfix = "01";
    private String urlPostFix = "01";
    private ProductStatus status = ProductStatus.ACTIVE;

    public Product() {}

    public Product(long id, String code, String name, String address, String publisher, int price, String status) {
        this(name, publisher, price);
        this.id = id;
        this.code = code;
        this.address = address;
        this.status = ProductStatus.valueOf(status);
    }

    public Product(String code, String name, String address, String publisher, int price) {
        this(name, publisher, price);
        this.code = code;
        this.address = address;
    }

    public Product(String name, String publisher, int price) {
        this.name = name;
        this.publisher = publisher;
        this.price = price;
    }

    public Product(String name) {
        this.name = name;
    }

    public void setCodeAndAddress() {
        String validName = Normalizer.normalize(this.name, Normalizer.Form.NFKD).replaceAll("\\p{M}", "");
        String validPublisher = Normalizer.normalize(this.publisher, Normalizer.Form.NFKD).replaceAll("\\p{M}", "");

        String code = validName.replaceAll(" ","").substring(0, 3).toUpperCase() + validPublisher.substring(0, 3).toUpperCase() + postfix;
        String address = validName.replaceAll(" ","-").toLowerCase();
        if (Integer.parseInt(urlPostFix)!=1){
            address+=urlPostFix;
        }

        setCode(code);
        setAddress(address);
    }

    public void incrementCodePostFix() {
        postfix = String.format("%02d", (Integer.parseInt(postfix) + 1));
    }

    public void incrementAddressPostFix(){ urlPostFix = String.format("%02d", (Integer.parseInt(urlPostFix) + 1));}

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", publisher='" + publisher + '\'' +
                ", price=" + price +
                ", status=" + status +
                '}';
    }

}
