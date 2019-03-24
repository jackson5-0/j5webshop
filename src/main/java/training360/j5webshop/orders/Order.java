package training360.j5webshop.orders;

import training360.j5webshop.baskets.Basket;
import training360.j5webshop.products.Product;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private long id;
    private long basketId;
    private long userId;
    private LocalDateTime purchaseDate;
    private List<OrderedProduct> orderedProduct = new ArrayList<>();
    private OrderStatus orderStatus;

    public Order() {
    }

    public Order(Basket basket) {
        this.id = id;
        this.purchaseDate = LocalDateTime.now();
        this.orderStatus = OrderStatus.ACTIVE;
        this.userId = basket.getUserId();
        this.basketId = basket.getId();
        for(Product p: basket.getProducts().keySet()){
            int quantity = basket.getProducts().get(p);
            orderedProduct.add(new OrderedProduct(p, quantity));
        }
    }

    public Order(long id, long userId, LocalDateTime purchaseDate, List<OrderedProduct> orderedProduct, OrderStatus orderStatus) {
        this.id = id;
        this.basketId = basketId;
        this.userId = userId;
        this.purchaseDate = purchaseDate;
        this.orderedProduct = orderedProduct;
        this.orderStatus = orderStatus;
    }

    public long getBasketId() {
        return basketId;
    }

    public void setBasketId(long basketId) {
        this.basketId = basketId;
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

    @Override
    public String toString() {
        return "Order{" +
                "purchaseDate=" + purchaseDate +
                ", orderedProduct=" + orderedProduct +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
