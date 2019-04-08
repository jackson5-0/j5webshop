package training360.j5webshop.baskets;

import training360.j5webshop.products.Product;

public class BasketItemContainer {

    private Product product;
    private int quantity;

    public BasketItemContainer(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "BasketItemContainer{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
