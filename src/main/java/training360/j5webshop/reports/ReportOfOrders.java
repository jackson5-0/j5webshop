package training360.j5webshop.reports;

import training360.j5webshop.orders.OrderStatus;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class ReportOfOrders {
    private String month;
    private OrderStatus status;
    private int numberOfOrders;
    private int valueOfOrders;

    public ReportOfOrders(Month month, OrderStatus status, int numberOfOrders, int valueOfOrders) {
        this.month = month.getDisplayName(TextStyle.FULL, new Locale("hu", "Hu"));
        this.status = status;
        this.numberOfOrders = numberOfOrders;
        this.valueOfOrders = valueOfOrders;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public int getValueOfOrders() {
        return valueOfOrders;
    }

    public void setValueOfOrders(int valueOfOrders) {
        this.valueOfOrders = valueOfOrders;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
