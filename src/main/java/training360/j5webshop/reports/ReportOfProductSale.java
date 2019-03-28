package training360.j5webshop.reports;

import training360.j5webshop.products.Product;

import java.time.Month;

public class ReportOfProductSale {
    private Month month;
    private Product product;
    private int quantity;
    private int sum;

    public ReportOfProductSale(Month month, Product product, int quantity, int sum) {
        this.month = month;
        this.product = product;
        this.quantity = quantity;
        this.sum = sum;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
