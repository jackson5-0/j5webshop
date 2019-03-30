package training360.j5webshop.products;

import training360.j5webshop.products.Product;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private long id;
    private String name;
    private int priority;
    private List<Product> products = new ArrayList<>();

    public Category() {
    }

    public Category(long id, String name, int priority) {
        this.id = id;
        this.name = name;
        this.priority = priority;
    }

    public Category(long id, String name, int priority, List<Product> products) {
        this(id, name, priority);
        this.products = products;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setName(String name) {
        this.name = name;
    }
}
