package training360.j5webshop.baskets;

import training360.j5webshop.products.Product;

import java.util.HashMap;
import java.util.Map;

public class Basket {

    private long id;
    private long userId;
    private Map<Product, Integer> products = new HashMap<>();

    public Basket() {
    }

    public Basket(Map<Product, Integer> products) {
        this.products = products;
    }

    public Basket(long id, long userId) {
        this.id = id;
        this.userId = userId;
    }

    public Basket(long id, long userId, Map<Product, Integer> products) {
        this.id = id;
        this.userId = userId;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }
}
