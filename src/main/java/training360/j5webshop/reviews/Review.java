package training360.j5webshop.reviews;

import training360.j5webshop.products.Product;
import training360.j5webshop.users.User;

import java.time.LocalDateTime;

public class Review {

    private long id;
    private Product product;
    private User user;
    private LocalDateTime reviewDate = LocalDateTime.now();
    private String message;
    private int rating;

    public Review(Product product, User user, String message, int rating) {
        this.product = product;
        this.user = user;
        this.message = message;
        this.rating = rating;
    }

    public Review(Product product, String message, int rating) {
        this.product = product;
        this.message = message;
        this.rating = rating;
    }

    public Review(String message, int rating) {
        this.message = message;
        this.rating = rating;
    }

    public Review() {
    }

    public long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public String getMessage() {
        return message;
    }

    public int getRating() {
        return rating;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", product=" + product +
                ", user=" + user +
                ", reviewDate=" + reviewDate +
                ", message='" + message + '\'' +
                ", rating=" + rating +
                '}';
    }
}
