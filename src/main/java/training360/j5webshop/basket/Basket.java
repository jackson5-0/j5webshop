package training360.j5webshop.basket;

import training360.j5webshop.products.Product;

import java.util.Map;

public class Basket {

    private Map<Product, Integer> products;

    public Basket() {
    }

    public Basket(Map<Product, Integer> products) {
        this.products = products;
    }

    public void addProduct(Product product, Integer numOfProduct) {
        if (products.containsKey(product)) {
            products.put(product, products.get(product) + numOfProduct);
        }
        products.put(product, numOfProduct);
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }
}
