package training360.j5webshop.reports;

import training360.j5webshop.orders.OrderStatus;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class ReportOfOrders {
    private int year;
    private String month;
    private OrderStatus status;
    private int numberOfOrders;
    private int valueOfOrders;

    public ReportOfOrders(int year, Month month, OrderStatus status, int numberOfOrders, int valueOfOrders) {
        this.year = year;
        this.month = month.getDisplayName(TextStyle.FULL, new Locale("hu", "HU"));
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
