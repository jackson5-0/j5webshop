package training360.j5webshop.products;

public class ProductContainer {

    private Product product;
    private String message;

    public ProductContainer(Product product) {
        this.product = product;
    }

    public ProductContainer(String message) {
        this.message = message;
    }

    public Product getProduct() {
        return product;
    }

    public String getMessage() {
        return message;
    }

}
