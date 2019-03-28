package training360.j5webshop.statistics;

public class Statistics {
    private int numberOfActiveUsers;

    private int numberOfActiveProducts;
    private int numberOfAllProducts;

    private int numberOfActiveOrders;
    private int numberOfAllOrders;

    public Statistics(int numberOfActiveUsers, int numberOfActiveProducts, int numberOfAllProducts) {
        this.numberOfActiveUsers = numberOfActiveUsers;
        this.numberOfActiveProducts = numberOfActiveProducts;
        this.numberOfAllProducts = numberOfAllProducts;
    }

    public int getNumberOfActiveUsers() {
        return numberOfActiveUsers;
    }

    public int getNumberOfActiveProducts() {
        return numberOfActiveProducts;
    }

    public int getNumberOfAllProducts() {
        return numberOfAllProducts;
    }

    public int getNumberOfActiveOrders() {
        return numberOfActiveOrders;
    }

    public int getNumberOfAllOrders() {
        return numberOfAllOrders;
    }

    public void setNumberOfActiveUsers(int numberOfActiveUsers) {
        this.numberOfActiveUsers = numberOfActiveUsers;
    }

    public void setNumberOfActiveProducts(int numberOfActiveProducts) {
        this.numberOfActiveProducts = numberOfActiveProducts;
    }

    public void setNumberOfAllProducts(int numberOfAllProducts) {
        this.numberOfAllProducts = numberOfAllProducts;
    }

    public void setNumberOfActiveOrders(int numberOfActiveOrders) {
        this.numberOfActiveOrders = numberOfActiveOrders;
    }

    public void setNumberOfAllOrders(int numberOfAllOrders) {
        this.numberOfAllOrders = numberOfAllOrders;
    }
}
