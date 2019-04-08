package training360.j5webshop.orders;

import java.time.LocalDateTime;

public class OrderInfo {
    private long id;
    private String userName;
    private LocalDateTime purchaseDate;
    private OrderStatus orderStatus;
    private int totalPrice;
    private String shippingAddress;

    public OrderInfo(long id,String userName, LocalDateTime purchaseDate, OrderStatus orderStatus, int totalPrice) {
        this.id = id;
        this.userName = userName;
        this.purchaseDate = purchaseDate;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
    }

    public OrderInfo(long id, String userName, LocalDateTime purchaseDate, OrderStatus orderStatus, int totalPrice, String shippingAddress) {
        this.id = id;
        this.userName = userName;
        this.purchaseDate = purchaseDate;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.shippingAddress = shippingAddress;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
