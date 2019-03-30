package training360.j5webshop.reviews;

public class ReviewInfo {

    private Boolean hasDeliveredProduct;
    private String userReview;
    private int userRating;

    public ReviewInfo(Boolean hasDeliveredProduct, String userReview, int userRating) {
        this.hasDeliveredProduct = hasDeliveredProduct;
        this.userReview = userReview;
        this.userRating = userRating;
    }

    public Boolean getHasDeliveredProduct() {
        return hasDeliveredProduct;
    }

    public void setHasDeliveredProduct(Boolean hasDeliveredProduct) {
        this.hasDeliveredProduct = hasDeliveredProduct;
    }

    public String getUserReview() {
        return userReview;
    }

    public void setUserReview(String userReview) {
        this.userReview = userReview;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    @Override
    public String toString() {
        return "ReviewInfo{" +
                "hasDeliveredProduct=" + hasDeliveredProduct +
                ", userReview='" + userReview + '\'' +
                ", userRating=" + userRating +
                '}';
    }
}
