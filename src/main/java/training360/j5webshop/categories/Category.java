package training360.j5webshop.categories;

import training360.j5webshop.products.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public void setId(long id) {
        this.id = id;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
