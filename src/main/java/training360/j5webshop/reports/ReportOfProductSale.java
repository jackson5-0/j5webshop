package training360.j5webshop.reports;

import training360.j5webshop.products.Product;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class ReportOfProductSale {
    private int year;
    private String month;
    private String code;
    private String name;
    private int price;
    private int quantity;
    private int sum;

    public ReportOfProductSale(int year, Month month, String code, String name, int price, int quantity, int sum) {
        this.year = year;
        this.month = month.getDisplayName(TextStyle.FULL, new Locale("hu", "HU"));
        this.code = code;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.sum = sum;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass() != getClass()) return false;
        ReportOfProductSale other = (ReportOfProductSale) obj;
        return (year == other.getYear()) && (month.equals(other.getMonth())) && (code.equals(other.getCode())) && (price == other.getPrice());
    }
}
