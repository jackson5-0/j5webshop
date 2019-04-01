package training360.j5webshop.orders;

import training360.j5webshop.baskets.Basket;
import training360.j5webshop.products.Product;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private long id;
    private long userId;
    private LocalDateTime purchaseDate;
    private List<OrderedProduct> orderedProduct = new ArrayList<>();
    private OrderStatus orderStatus;
    private String shippingAddress;

    public Order() {
    }

    public Order(Basket basket) {
        this.id = id;
        this.purchaseDate = LocalDateTime.now();
        this.orderStatus = OrderStatus.ACTIVE;
        this.userId = basket.getUserId();
        for(Product p: basket.getProducts().keySet()){
            int quantity = basket.getProducts().get(p);
            orderedProduct.add(new OrderedProduct(p, quantity, p.getPrice()));
        }
    }

    public Order(long id, long userId, LocalDateTime purchaseDate, List<OrderedProduct> orderedProduct, OrderStatus orderStatus) {
        this.id = id;
        this.userId = userId;
        this.purchaseDate = purchaseDate;
        this.orderedProduct = orderedProduct;
        this.orderStatus = orderStatus;
    }

    public Order(long id, long userId, LocalDateTime purchaseDate, OrderStatus orderStatus) {
        this.id = id;
        this.userId = userId;
        this.purchaseDate = purchaseDate;
        this.orderStatus = orderStatus;
    }

    public Order(long id, long userId, LocalDateTime purchaseDate, List<OrderedProduct> orderedProduct, OrderStatus orderStatus, String shippingAddress) {
        this.id = id;
        this.userId = userId;
        this.purchaseDate = purchaseDate;
        this.orderedProduct = orderedProduct;
        this.orderStatus = orderStatus;
        this.shippingAddress = shippingAddress;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public List<OrderedProduct> getOrderedProduct() {
        return orderedProduct;
    }

    public void setOrderedProduct(List<OrderedProduct> orderedProduct) {
        this.orderedProduct = orderedProduct;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @Override
    public String toString() {
        return "Order{" +
                "purchaseDate=" + purchaseDate +
                ", orderedProduct=" + orderedProduct +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
