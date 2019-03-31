package training360.j5webshop.baskets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import training360.j5webshop.products.Product;
import training360.j5webshop.products.ProductDao;
import training360.j5webshop.validation.ResponseStatus;
import training360.j5webshop.validation.ValidationStatus;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql({"/init.sql"})
public class BasketIntegrationTest {

    @Autowired
    BasketController basketController;
    @Autowired
    ProductDao productDao;

    @Test
    public void testAddToBasket() {
        // Given
        Product product = new Product(1, "GEMHAC01", "Hacker játszma", "hacker-jatszma", "Gém Klub Kft.", 3190, "ACTIVE");
        // When
        ResponseStatus rs = basketController.addToBasketWithQuantity(2,1, new TestingAuthenticationToken("kovacsgeza", "KovacsGeza90"));
        List<BasketItemContainer> products = basketController.basketItemsWithQuantity(new TestingAuthenticationToken("kovacsgeza", "KovacsGeza90"));
        // Then
        System.out.println(products);
        assertThat(products.size(), equalTo(1));
        assertThat(products.get(0).getProduct().getAddress(), equalTo("hacker-jatszma"));
        assertThat(rs.getStatus(), equalTo(ValidationStatus.SUCCESS));
        assertThat(rs.getMessages().size(), equalTo(1));
        assertThat(rs.getMessages().get(0), equalTo("2 db termék bekerült a kosárba."));
    }

    @Test
    public void testAddToBasketWithInvalidQuantity() {
        // Given
        Product product = new Product(3, "AVALOR01", "Lord of Hellas", "lord-of-hellas", "Avaken Realms", 35990, "ACTIVE");
        // When
        ResponseStatus rs = basketController.addToBasketWithQuantity(-1,3, new TestingAuthenticationToken("tadri1988", "Tadri1988"));
        ResponseStatus rs2 = basketController.addToBasketWithQuantity(25,3, new TestingAuthenticationToken("tadri1988", "Tadri1988"));
        List<BasketItemContainer> products = basketController.basketItemsWithQuantity(new TestingAuthenticationToken("tadri1988", "Tadri1988"));
        // Then
        assertThat(products.size(), equalTo(2));
        assertThat(rs.getStatus(), equalTo(ValidationStatus.FAIL));
        assertThat(rs.getMessages().get(0), equalTo("Egy darabnál kevesebb vagy 20 darabnál több termék megrendelése nem lehetséges"));
        assertThat(rs2.getStatus(), equalTo(ValidationStatus.FAIL));
        assertThat(rs2.getMessages().get(0), equalTo("Egy darabnál kevesebb vagy 20 darabnál több termék megrendelése nem lehetséges"));
    }

    @Test
    public void testAddToBasketWithNotValidBasketId() {
        // When
        ResponseStatus rs = basketController.addToBasketWithQuantity(3,3, new TestingAuthenticationToken("tadri19889", "Tadri19889"));
        // Then
        System.out.println(rs);
        assertThat(rs.getStatus(), equalTo(ValidationStatus.FAIL));
        assertThat(rs.getMessages().get(0), equalTo("Nem létező kosár vagy termék"));
    }

    @Test
    public void testFlushBasket() {
        // When
        ResponseStatus rs = basketController.flushBasket(new TestingAuthenticationToken("tadri1988", "Tadri1988"));
        // Then
        assertThat(rs.getStatus(), equalTo(ValidationStatus.SUCCESS));
        assertThat(rs.getMessages().get(0), equalTo("A kosár újra üres!"));
    }

    @Test
    public void testFlushBasketIfDoesntExist() {
        // When
        ResponseStatus rs = basketController.flushBasket(new TestingAuthenticationToken("", ""));
        // Then
        assertThat(rs.getStatus(), equalTo(ValidationStatus.FAIL));
        assertThat(rs.getMessages().get(0), equalTo("Nem létező kosár"));
    }

    @Test
    public void testBasketItemWithQuantity() {
        // Given
        Product product = new Product(3, "AVALOR01", "Lord of Hellas", "lord-of-hellas", "Avaken Realms", 35990, "ACTIVE");
        Product product2 = new Product(2, "GEMDIX01", "Dixit", "dixit", "Gém Klub Kft.", 7990, "ACTIVE");
        // When
        basketController.addToBasketWithQuantity(3,3, new TestingAuthenticationToken("tadri1988", "Tadri1988"));
        basketController.addToBasketWithQuantity(3,2, new TestingAuthenticationToken("tadri1988", "Tadri1988"));
        List<BasketItemContainer> products = basketController.basketItemsWithQuantity(new TestingAuthenticationToken("tadri1988", "Tadri1988"));
        // Then
        assertThat(products.get(0).getProduct().getAddress(), equalTo("dixit"));
        assertThat(products.get(0).getQuantity(), equalTo(4));
        assertThat(products.get(1).getProduct().getAddress(), equalTo("lord-of-hellas"));
        assertThat(products.get(1).getQuantity(), equalTo(4));
    }

    @Test
    public void testDeleteItemFromBasket() {
        // When
        ResponseStatus rs = basketController.deleteItemFromBasket(3, 2);
        // Then
        assertThat(rs.getStatus(), equalTo(ValidationStatus.SUCCESS));
        assertThat(rs.getMessages().get(0), equalTo("A terméket sikeresen eltávolítottuk a kosárból."));
    }

    @Test
    public void testDeleteItemFromBasketIfDoesntExists() {
        // When
        ResponseStatus rs = basketController.deleteItemFromBasket(100, 100);
        // Then
        assertThat(rs.getStatus(), equalTo(ValidationStatus.FAIL));
        assertThat(rs.getMessages().get(0), equalTo("Nem létező kosár"));
    }
    @Test
    public void testDecreaseAmountInBasket() {
        // Given
        basketController.addToBasketWithQuantity(5,1, new TestingAuthenticationToken("tadri1988", "Tadri1988"));
        // When
        ResponseStatus rs = basketController.decreaseAmountInBasket(3,1,2);
        // Then
        assertThat(rs.getStatus(), equalTo(ValidationStatus.SUCCESS));
        assertThat(rs.getMessages().get(0), equalTo("Egy termék sikeresen kikerült a kosárból!"));
    }
    @Test
    public void testDecreaseAmountInBasketIfDoesntExists() {
        basketController.addToBasketWithQuantity(5,1, new TestingAuthenticationToken("tadri19889", "Tadri19889"));
        // When
        ResponseStatus rs = basketController.decreaseAmountInBasket(3,1,2);
        // Then
        assertThat(rs.getStatus(), equalTo(ValidationStatus.FAIL));
        assertThat(rs.getMessages().get(0), equalTo("Nem létező termék"));
    }

}
