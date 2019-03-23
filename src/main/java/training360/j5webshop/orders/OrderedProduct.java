package training360.j5webshop.orders;

import training360.j5webshop.products.Product;

public class OrderedProduct {

    private String name;
    private int quantity;
    private int priceAtPurchase;

    public OrderedProduct() {
    }

    public OrderedProduct(Product product, int quantity, int priceAtPurchase) {
        this.name = product.getName();
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public void setPriceAtPurchase(int priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }

    @Override
    public String toString() {
        return "OrderedProduct{" +
                "name=" + name +
                ", quantity=" + quantity +
                ", priceAtPurchase=" + priceAtPurchase +
                '}';
    }
}
