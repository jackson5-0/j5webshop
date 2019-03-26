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

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql({"/basket_init.sql"})
public class BasketIntegrationTest {

    @Autowired
    BasketController basketController;
    @Autowired
    ProductDao productDao;

    @Test
    public void testAddToBasket() {
        // Given
        Product product = new Product("name", "publisher", 10_000);
        long id = productDao.createProduct(product);
        // When
        ResponseStatus rs = basketController.addToBasket(2, id);
        Set<Product> products = basketController.listProductsOfBasket(new TestingAuthenticationToken("user", "user"));
        // Then
        assertThat(products.size(), equalTo(1));
        assertTrue(products.contains(product));
        assertThat(rs.getStatus(), equalTo(ValidationStatus.SUCCESS));
        assertThat(rs.getMessages().size(), equalTo(1));
        assertThat(rs.getMessages().get(0), equalTo("A termék bekerült a kosárba!"));
    }

    @Test
    public void testAddToBasketAlreadyExistingProduct() {
        // Given
        Product product = new Product("name", "publisher", 10_000);
        long id = productDao.createProduct(product);
        // When
        basketController.addToBasket(2, id);
        ResponseStatus rs = basketController.addToBasket(2, id);
        Set<Product> products = basketController.listProductsOfBasket(new TestingAuthenticationToken("user", "user"));
        // Then
        assertThat(products.size(), equalTo(1));
        assertThat(rs.getStatus(), equalTo(ValidationStatus.FAIL));
        assertThat(rs.getMessages().get(0), equalTo("A termék már a kosárban van!"));
    }

    @Test
    public void testAddToBasketWithNotValidBasketId() {
        // When
        ResponseStatus rs = basketController.addToBasket(100, 1);
        // Then
        assertThat(rs.getStatus(), equalTo(ValidationStatus.FAIL));
        assertThat(rs.getMessages().get(0), equalTo("Nem létező kosár vagy termék"));
    }

    @Test
    public void testFlushBasket() {
        // When
        ResponseStatus rs = basketController.flushBasket(1);
        // Then
        assertThat(rs.getStatus(), equalTo(ValidationStatus.SUCCESS));
        assertThat(rs.getMessages().get(0), equalTo("A kosár újra üres!"));
    }

    @Test
    public void testFlushBasketIfDoesntExist() {
        // When
        ResponseStatus rs = basketController.flushBasket(100);
        // Then
        assertThat(rs.getStatus(), equalTo(ValidationStatus.FAIL));
        assertThat(rs.getMessages().get(0), equalTo("Nem létező kosár"));
    }

    @Test
    public void testListProductsOfBasket() {
        Product product = new Product(1, "GEMHAC01", "Hacker játszma", "hacker-jatszma", "Gém Klub Kft.", 3190, "ACTIVE");
        Product product2 = new Product(2, "GEMDIX01", "Dixit", "dixit", "Gém Klub Kft.", 7990, "ACTIVE");
        basketController.addToBasket(2, 1);
        basketController.addToBasket(2, 2);
        Set<Product> products = basketController.listProductsOfBasket(new TestingAuthenticationToken("user", "user"));
        assertTrue(products.contains(product));
        assertTrue(products.contains(product2));
    }

    @Test
    public void testDeleteItemFromBasket() {
        // When
        ResponseStatus rs = basketController.deleteItemFromBasket(1, 2);
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

}
