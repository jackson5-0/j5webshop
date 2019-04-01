package training360.j5webshop.reviews;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import training360.j5webshop.products.Product;
import training360.j5webshop.users.Role;
import training360.j5webshop.users.User;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class ReviewIntegrationTest {

    @Autowired
    ReviewController reviewController;

    Authentication kissbeci = new Authentication() {
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getDetails() {
            return null;
        }

        @Override
        public Object getPrincipal() {
            return null;
        }

        @Override
        public boolean isAuthenticated() {
            return true;
        }

        @Override
        public void setAuthenticated(boolean b) throws IllegalArgumentException {

        }

        @Override
        public String getName() {
            return "kissbeci";
        }
    };

    @Test
    public void createReviewAndlistReviewByProductIdTest() {
        // Given
        Product product = new Product(2, "GEMDIX01", "Dixit", "dixit",
                "Gém Klub Kft.", 7990, "ACTIVE");
        User user = new User(4, "Béla", "Kiss", "kissbeci",
                "kissbeci00", Role.ROLE_USER);
        Review newReview = new Review(product, user, "Kiváló termék, ajánlom!", 5);
        // When
        int numberOfreviewsBeforeCreate = reviewController.listReviewByProductId(2).size();
        String responseMessage = reviewController.createReview(kissbeci, newReview).getMessages().get(0);
        int numberOfreviewsAfterCreate = reviewController.listReviewByProductId(2).size();
        // Then
        assertThat(numberOfreviewsBeforeCreate, equalTo(0));
        assertThat(numberOfreviewsAfterCreate, equalTo(1));
        assertThat(responseMessage, equalTo("Az értékelést elmentettük!"));
    }

    @Test
    public void deleteReviewTest() {
        // Given
        Product product = new Product(2, "GEMDIX01", "Dixit", "dixit",
                "Gém Klub Kft.", 7990, "ACTIVE");
        User user = new User(4, "Béla", "Kiss", "kissbeci",
                "kissbeci00", Role.ROLE_USER);
        Review review = new Review(product, user, "Kiváló termék, ajánlom!", 5);
        reviewController.createReview(kissbeci, review);
        // When
        int numberOfreviewsBeforeDelete = reviewController.listReviewByProductId(2).size();
        String responseMessage = reviewController.deleteReview(kissbeci, review).getMessages().get(0);
        int numberOfreviewsAfterDelete = reviewController.listReviewByProductId(2).size();
        // Then
        assertThat(numberOfreviewsBeforeDelete, equalTo(1));
        assertThat(numberOfreviewsAfterDelete, equalTo(0));
        assertThat(responseMessage, equalTo("Az értékelést sikeresen törölte!"));
    }

    @Test
    public void updateReviewTest() {
        // Given
        Product product = new Product(2, "GEMDIX01", "Dixit", "dixit",
                "Gém Klub Kft.", 7990, "ACTIVE");
        User user = new User(4, "Béla", "Kiss", "kissbeci",
                "kissbeci00", Role.ROLE_USER);
        Review review = new Review(product, user, "Kiváló termék, ajánlom!", 5);
        reviewController.createReview(kissbeci, review);
        // When
        String messageBeforeModify = reviewController.listReviewByProductId(2).get(0).getMessage();
        int ratingBeforeModify = reviewController.listReviewByProductId(2).get(0).getRating();
        Review newReview = new Review(product, user, "Nem tetszik.", 1);
        String responseMessage = reviewController.updateReview(kissbeci, newReview).getMessages().get(0);
        String messageAfterModify = reviewController.listReviewByProductId(2).get(0).getMessage();
        int ratingAfterModify = reviewController.listReviewByProductId(2).get(0).getRating();
        // Then
        assertThat(messageBeforeModify, equalTo("Kiváló termék, ajánlom!"));
        assertThat(ratingBeforeModify, equalTo(5));
        assertThat(messageAfterModify, equalTo("Nem tetszik."));
        assertThat(ratingAfterModify, equalTo(1));
        assertThat(responseMessage, equalTo("Az értékelést módosította!"));
    }

    @Test
    public void checkIfUserHasDeliveredProductAndHasReview() {
        // When
        ReviewInfo beforeHasReview = reviewController.checkIfUserHasDeliveredProductAndHasReview(kissbeci, 2);
        boolean hasDeliveredProduct = reviewController.checkIfUserHasDeliveredProductAndHasReview(kissbeci, 2).getHasDeliveredProduct();
        boolean hasNoDeliveredProduct = reviewController.checkIfUserHasDeliveredProductAndHasReview(kissbeci, 1).getHasDeliveredProduct();
        User user = new User(4, "Béla", "Kiss", "kissbeci", "kissbeci00",
                Role.ROLE_USER);
        Product product = new Product(2, "GEMDIX01", "Dixit", "dixit",
                "Gém Klub Kft.", 7990, "ACTIVE");
        Review review = new Review(product, user, "Kiváló termék, ajánlom!", 5);
        reviewController.createReview(kissbeci, review);
        ReviewInfo afterHasReview = reviewController.checkIfUserHasDeliveredProductAndHasReview(kissbeci, 2);
        // Then
        assertThat(beforeHasReview.getUserReview(), equalTo(null));
        assertThat(beforeHasReview.getUserRating(), equalTo(0));
        assertThat(afterHasReview.getUserReview(), equalTo("Kiváló termék, ajánlom!"));
        assertThat(afterHasReview.getUserRating(), equalTo(5));
        assertThat(hasDeliveredProduct, equalTo(true));
        assertThat(hasNoDeliveredProduct, equalTo(false));
    }

    @Test
    public void testCreateValidation() {
        // Given
        Product product = new Product(2, "GEMDIX01", "Dixit", "dixit",
                "Gém Klub Kft.", 7990, "ACTIVE");
        Product notDeliveredProduct = new Product(1, "GEMHAC01", "Hacker játszma", "hacker-jatszma",
                "Gém Klub Kft.", 3190, "ACTIVE");
        User user = new User(4, "Béla", "Kiss", "kissbeci",
                "kissbeci00", Role.ROLE_USER);
        Review reviewWithNoRating = new Review(product, user, "Kiváló termék, ajánlom!", 0);
        Review reviewWithTooLongMessage = new Review(product, user, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
                "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip" +
                " ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
                3);
        Review reviewWithAngleBrackets = new Review(product, user, "L<br>O<br>V<br>E", 5);
        Review reviewForNotDeliveredProduct = new Review(notDeliveredProduct, user, "Kiváló termék, ajánlom!", 5);
        Review review = new Review(product, user, "Kiváló termék, ajánlom!", 5);
        Review newReview = new Review(product, user, "Nem tetszik!", 2);
        // When
        String responseWhenNoRating = "";
        String responseWhenMessageTooLong = "";
        String responseWhenContainsAngleBrackets = "";
        String responseWhenNoDeliveredProduct = "";
        String responseWhenReviewExist = "";
        try {
            responseWhenNoRating = reviewController.createReview(kissbeci, reviewWithNoRating).getMessages().get(0);
            responseWhenMessageTooLong = reviewController.createReview(kissbeci, reviewWithTooLongMessage).getMessages().get(0);
            responseWhenContainsAngleBrackets = reviewController.createReview(kissbeci, reviewWithAngleBrackets).getMessages().get(0);
            responseWhenNoDeliveredProduct = reviewController.createReview(kissbeci, reviewForNotDeliveredProduct).getMessages().get(0);
            reviewController.createReview(kissbeci, review);
            responseWhenReviewExist = reviewController.createReview(kissbeci, newReview).getMessages().get(0);
        } catch (NullPointerException npe) {};
        // Then
        assertThat(responseWhenNoRating, equalTo("Kérjük értékelje a terméket 1-től 5-ig terjedő skálán!"));
        assertThat(responseWhenMessageTooLong, equalTo("A szöveges értékelés hossza nem lehet több 255 karakternél!"));
        assertThat(responseWhenContainsAngleBrackets, equalTo("A szöveges értékelés nem tartalmazhatja a '<' , '>' karaktereket!"));
        assertThat(responseWhenNoDeliveredProduct, equalTo("A termékhez csak akkor írhat értékelést, ha már rendelt belőle!"));
        assertThat(responseWhenReviewExist, equalTo("Ehhez a termékhez már írt értékelést!"));
    }

    @Test
    public void testUpdateValidation() {
        // Given
        Product product = new Product(2, "GEMDIX01", "Dixit", "dixit",
                "Gém Klub Kft.", 7990, "ACTIVE");
        User user = new User(4, "Béla", "Kiss", "kissbeci",
                "kissbeci00", Role.ROLE_USER);
        Review newReview = new Review(product, user, "Nem tetszik!", 1);
        reviewController.createReview(kissbeci, newReview);
        Review reviewWithNoRating = new Review(product, user, "Kiváló termék, ajánlom!", 0);
        Review reviewWithTooLongMessage = new Review(product, user, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
                "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip" +
                " ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
                3);
        Review reviewWithAngleBrackets = new Review(product, user, "L<br>O<br>V<br>E", 5);
        // When
        String responseWhenNoRating = "";
        String responseWhenMessageTooLong = "";
        String responseWhenContainsAngleBrackets = "";
        try {
            responseWhenNoRating = reviewController.updateReview(kissbeci, reviewWithNoRating).getMessages().get(0);
            responseWhenMessageTooLong = reviewController.updateReview(kissbeci, reviewWithTooLongMessage).getMessages().get(0);
            responseWhenContainsAngleBrackets = reviewController.updateReview(kissbeci, reviewWithAngleBrackets).getMessages().get(0);
        } catch (NullPointerException | DataIntegrityViolationException e) {};
        // Then
        assertThat(responseWhenNoRating, equalTo("Kérjük értékelje a terméket 1-től 5-ig terjedő skálán!"));
        assertThat(responseWhenMessageTooLong, equalTo("A szöveges értékelés hossza nem lehet több 255 karakternél!"));
        assertThat(responseWhenContainsAngleBrackets, equalTo("A szöveges értékelés nem tartalmazhatja a '<' , '>' karaktereket!"));
    }
}
