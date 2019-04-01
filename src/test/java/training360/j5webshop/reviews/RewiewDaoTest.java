package training360.j5webshop.reviews;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import training360.j5webshop.products.Product;
import training360.j5webshop.users.Role;
import training360.j5webshop.users.User;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql({"/init.sql"})
public class RewiewDaoTest {

    @Autowired
    ReviewDao reviewDao;

    @Test
    public void createReviewAndlistReviewByProductIdTest() {
        // Given
        Product product = new Product(1, "GEMHAC01", "Hacker játszma",
                "hacker-jatszma", "Gém Klub Kft.", 3190, "ACTIVE");
        User user = new User(1, "Géza", "Kovács", "kovacsgeza",
                "KovacsGeza90", Role.ROLE_USER);
        Review newReview = new Review(product, user, "Kiváló termék, ajánlom!", 5);
        // When
        int numberOfreviewsBeforeCreate = reviewDao.listReviewByProductId(1).size();
        long id = reviewDao.createReview(newReview);
        int numberOfreviewsAfterCreate = reviewDao.listReviewByProductId(1).size();
        // Then
        assertThat(numberOfreviewsBeforeCreate, equalTo(0));
        assertThat(numberOfreviewsAfterCreate, equalTo(1));
        assertThat(id, equalTo(1L));
    }

    @Test
    public void deleteReviewTest() {
        // Given
        Product product = new Product(1, "GEMHAC01", "Hacker játszma",
                "hacker-jatszma", "Gém Klub Kft.", 3190, "ACTIVE");
        User user1 = new User(1, "Géza", "Kovács", "kovacsgeza",
                "KovacsGeza90", Role.ROLE_USER);
        Review review1 = new Review(product, user1, "Kiváló termék, ajánlom!", 5);
        reviewDao.createReview(review1);
        User user2 = new User(2, "Gizella", "Nagy", "nagygizi22",
                "GiziAZizi11", Role.ROLE_USER);
        Review review2 = new Review(product, user2, "Nem tetszett!", 1);
        reviewDao.createReview(review2);
        // When
        int numberOfreviewsBeforeDelete = reviewDao.listReviewByProductId(1).size();
        reviewDao.deleteReview(review1);
        int numberOfreviewsAfterDelete = reviewDao.listReviewByProductId(1).size();
        // Then
        assertThat(numberOfreviewsBeforeDelete, equalTo(2));
        assertThat(numberOfreviewsAfterDelete, equalTo(1));
        assertThat(reviewDao.listReviewByProductId(1).get(0).getId(), equalTo(2L));
    }

    @Test
    public void updateReviewTest() {
        // Given
        Product product = new Product(1, "GEMHAC01", "Hacker játszma",
                "hacker-jatszma", "Gém Klub Kft.", 3190, "ACTIVE");
        User user = new User(1, "Géza", "Kovács", "kovacsgeza",
                "KovacsGeza90", Role.ROLE_USER);
        Review review = new Review(product, user, "Kiváló termék, ajánlom!", 5);
        reviewDao.createReview(review);
        // When
        String messageBeforeModify = reviewDao.listReviewByProductId(1).get(0).getMessage();
        int ratingBeforeModify = reviewDao.listReviewByProductId(1).get(0).getRating();
        Review newReview = new Review(product, user, "Nem tetszik.", 1);
        reviewDao.updateReview(newReview);
        String messageAfterModify = reviewDao.listReviewByProductId(1).get(0).getMessage();
        int ratingAfterModify = reviewDao.listReviewByProductId(1).get(0).getRating();
        // Then
        assertThat(messageBeforeModify, equalTo("Kiváló termék, ajánlom!"));
        assertThat(ratingBeforeModify, equalTo(5));
        assertThat(messageAfterModify, equalTo("Nem tetszik."));
        assertThat(ratingAfterModify, equalTo(1));
    }

    @Test
    public void checkIfUserHasDeliveredProductTest() {
        // When
        boolean hasDeliveredProduct = reviewDao.checkIfUserHasDeliveredProduct("kissbeci", 2);
        boolean hasNoDeliveredProduct = reviewDao.checkIfUserHasDeliveredProduct("kovacsgeza", 2);
        // Then
        assertThat(hasDeliveredProduct, equalTo(true));
        assertThat(hasNoDeliveredProduct, equalTo(false));
    }

    @Test
    public void checkIfUserHasDeliveredProductAndHasReview() {
        // When
        ReviewInfo beforeHasReview = reviewDao.checkIfUserHasDeliveredProductAndHasReview("kissbeci", 2);
        User user = new User(4, "Béla", "Kiss", "kissbeci", "kissbeci00",
                Role.ROLE_USER);
        Product product = new Product(2, "GEMDIX01", "Dixit", "dixit",
                "Gém Klub Kft.", 7990, "ACTIVE");
        Review review = new Review(product, user, "Kiváló termék, ajánlom!", 5);
        reviewDao.createReview(review);
        ReviewInfo afterHasReview = reviewDao.checkIfUserHasDeliveredProductAndHasReview("kissbeci", 2);
        // Then
        assertThat(beforeHasReview.getUserReview(), equalTo(null));
        assertThat(beforeHasReview.getUserRating(), equalTo(0));
        assertThat(afterHasReview.getUserReview(), equalTo("Kiváló termék, ajánlom!"));
        assertThat(afterHasReview.getUserRating(), equalTo(5));
    }
}
