package training360.j5webshop.validation;

import org.junit.Test;
import training360.j5webshop.baskets.Basket;
import training360.j5webshop.products.Product;
import training360.j5webshop.reviews.Review;
import training360.j5webshop.users.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ValidatorTest {

    @Test
    public void checkUserValidatorWhenResponseOk() {
        //Given
        User user = new User("Pista", "Kiss", "kissPista", "kissPista2");
        User userlistItem1 = new User("Gizi", "Nagy", "nagyGizi", "nagyGizi2");
        User userlistItem2 = new User("Gizi", "Németh", "némethGizi", "némethGizi2");
        //When
        Validator validator = new Validator(user, Arrays.asList(userlistItem1, userlistItem2));
        //Then
        assertThat(validator.getResponseStatus().getMessages().size(), equalTo(0));
    }

    @Test
    public void checkUserValidatorWhenPasswordNotEnoughStrong() {
        //Given
        User user = new User("Pista", "Kiss", "kissPista", "kissP");
        User userlistItem1 = new User("Gizi", "Nagy", "nagyGizi", "nagyGizi2");
        User userlistItem2 = new User("Gizi", "Németh", "némethGizi", "némethGizi2");
        //When
        Validator validator = new Validator(user, Arrays.asList(userlistItem1, userlistItem2));
        //Then
        assertThat(validator.getResponseStatus().getMessages().size(), equalTo(1));
        assertThat(validator.getResponseStatus().getMessages().get(0), equalTo("A jelszó minimum 8 karakter hosszú legyen, tartalmazzon kis- és nagybetűt, illetve számot!"));
    }

    @Test
    public void checkUserValidatorWhenUsernameIsNotProper() {
        //Given
        User user = new User("Gizi", "Nagy", "ng", "nagyGizi2");
        User userlistItem1 = new User("Pista", "Kiss", "kiss", "kissPista2");
        User userlistItem2 = new User("Gizi", "Németh", "nagyGizi", "némethGizi2");
        //When
        Validator validator = new Validator(user, Arrays.asList(userlistItem1, userlistItem2));
        //Then
        assertThat(validator.getResponseStatus().getMessages().size(), equalTo(1));
        assertThat(validator.getResponseStatus().getMessages().get(0), equalTo("A felhasználói név legalább 3 karakter hosszú legyen, csak számot, kis- és nagybetűt, pontot, alulvonást és kötőjelet tartalmazhat!"));
    }

    @Test
    public void checkUserValidatorWhenUsernameIsAlreadyTaken() {
        //Given
        User user = new User(3, "Gizi", "Nagy", "nagyGizi", "nagyGizi2");
        User userlistItem1 = new User(1, "Pista", "Kiss", "kiss", "kissPista2");
        User userlistItem2 = new User(2, "Gizi", "Németh", "nagyGizi", "némethGizi2");
        //When
        Validator validator = new Validator(user, Arrays.asList(userlistItem1, userlistItem2));
        //Then
        assertThat(validator.getResponseStatus().getMessages().size(), equalTo(1));
        assertThat(validator.getResponseStatus().getMessages().get(0), equalTo("A kért felhasználónév foglalt!"));
    }

    @Test
    public void checkProductValidatorWhenResponseOk() {
        //Given
        Product product = new Product("Game of Thrones", "HBO", 12990);
        //When
        Validator validator = new Validator(product);
        //Then
        assertThat(validator.getResponseStatus().getMessages().size(), equalTo(0));
    }

    @Test
    public void checkProductValidatorWhenNameIsNull() {
        //Given
        Product product = new Product(null, "HBO", 12990);
        //When
        Validator validator = new Validator(product);
        //Then
        assertThat(validator.getResponseStatus().getMessages().size(), equalTo(1));
        assertThat(validator.getResponseStatus().getMessages().get(0), equalTo("A név nem lehet üres!"));
    }

    @Test
    public void checkProductValidatorWhenNameIsTooShort() {
        //Given
        Product product = new Product("GT", "HBO", 12990);
        //When
        Validator validator = new Validator(product);
        //Then
        assertThat(validator.getResponseStatus().getMessages().size(), equalTo(1));
        assertThat(validator.getResponseStatus().getMessages().get(0), equalTo("A név nem lehet rövidebb 3 karakternél!"));
    }

    @Test
    public void checkProductValidatorWhenPublisherNameIsEmpty() {
        //Given
        Product product = new Product("Game of Thrones", "", 12990);
        //When
        Validator validator = new Validator(product);
        //Then
        assertThat(validator.getResponseStatus().getMessages().size(), equalTo(1));
        assertThat(validator.getResponseStatus().getMessages().get(0), equalTo("A kiadó nem lehet üres!"));
    }

    @Test
    public void checkProductValidatorWhenNameIsEmptyAndPublisherNameIsShorterThanThreeCharacters() {
        //Given
        Product product = new Product("", "HB", 12990);
        //When
        Validator validator = new Validator(product);
        //Then
        assertThat(validator.getResponseStatus().getMessages().size(), equalTo(2));
        assertThat(validator.getResponseStatus().getMessages().get(0), equalTo("A név nem lehet üres!"));
        assertThat(validator.getResponseStatus().getMessages().get(1), equalTo("A kiadó nem lehet rövidebb 3 karakternél!"));
    }

    @Test
    public void checkProductValidatorWhenPriceIsNegative() {
        //Given
        Product product = new Product("Game Of Thrones", "HBO", -12990);
        //When
        Validator validator = new Validator(product);
        //Then
        assertThat(validator.getResponseStatus().getMessages().size(), equalTo(1));
        assertThat(validator.getResponseStatus().getMessages().get(0), equalTo("Az ár nem lehet nulla, vagy negatív szám!"));
    }

    @Test
    public void checkProductValidatorWhenPriceIsTooHigh() {
        //Given
        Product product = new Product("Game Of Thrones", "HBO", 2312990);
        //When
        Validator validator = new Validator(product);
        //Then
        assertThat(validator.getResponseStatus().getMessages().size(), equalTo(1));
        assertThat(validator.getResponseStatus().getMessages().get(0), equalTo("Az ár nem lehet nagyobb 2.000.000 Ft-nál!"));
    }

    @Test
    public void checkBasketValidatorWhenResponseOk() {
        //Given
        Product got = new Product("Game of Thrones", "HBO", 12990);
        Product dixit =  new Product("Dixit", "Gém Klub Kft.", 8990);
        Map<Product, Integer> productIntegerMap = new HashMap<>();
        productIntegerMap.put(got, 1);
        productIntegerMap.put(dixit, 2);
        Basket basket = new Basket(2, 2, productIntegerMap);
        //When
        Validator validator = new Validator(basket);
        //Then
        assertThat(validator.getResponseStatus().getMessages().size(), equalTo(0));
    }

    @Test
    public void checkBasketValidatorWhenBasketIsEmpty() {
        //Given

        Map<Product, Integer> productIntegerMap = new HashMap<>();
        Basket basket = new Basket(2, 2, productIntegerMap);
        //When
        Validator validator = new Validator(basket);
        //Then
        assertThat(validator.getResponseStatus().getMessages().size(), equalTo(1));
        assertThat(validator.getResponseStatus().getMessages().get(0), equalTo("Csak terméket tartalmazó kosarat lehet megrendelni"));
    }

    @Test
    public void checkReviewTest() {
        // Given
        Review reviewWithNoRating = new Review("Kiváló termék, ajánlom!", 0);
        Review reviewWithTooLongMessage = new Review("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
                "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip" +
                " ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
                3);
        Review reviewWithAngleBrackets = new Review("L<br>O<br>V<br>E", 5);
        // When
        Validator validatorWithNoRating = new Validator();
        Validator validatorWithTooLongMessage = new Validator();;
        Validator validatorWithAngleBrackets = new Validator();;
        try {
            validatorWithNoRating = new Validator(reviewWithNoRating);
            validatorWithTooLongMessage = new Validator(reviewWithTooLongMessage);
            validatorWithAngleBrackets = new Validator(reviewWithAngleBrackets);
        } catch (NullPointerException npe) {};
        // Then
        assertThat(validatorWithNoRating.getResponseStatus().getMessages().get(0), equalTo("Kérjük értékelje a terméket 1-től 5-ig terjedő skálán!"));
        assertThat(validatorWithNoRating.getResponseStatus().getStatus(), equalTo(ValidationStatus.FAIL));
        assertThat(validatorWithTooLongMessage.getResponseStatus().getMessages().get(0), equalTo("A szöveges értékelés hossza nem lehet több 255 karakternél!"));
        assertThat(validatorWithTooLongMessage.getResponseStatus().getStatus(), equalTo(ValidationStatus.FAIL));
        assertThat(validatorWithAngleBrackets.getResponseStatus().getMessages().get(0), equalTo("A szöveges értékelés nem tartalmazhatja a '<' , '>' karaktereket!"));
        assertThat(validatorWithAngleBrackets.getResponseStatus().getStatus(), equalTo(ValidationStatus.FAIL));
    }
}
