//package training360.j5webshop.baskets;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.authentication.TestingAuthenticationToken;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit4.SpringRunner;
//import training360.j5webshop.products.Product;
//import training360.j5webshop.products.ProductDao;
//import training360.j5webshop.validation.ResponseStatus;
//import training360.j5webshop.validation.ValidationStatus;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.junit.Assert.assertTrue;
//
//import java.util.Set;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Sql({"/init.sql"})
//public class BasketIntegrationTest {
//
//    @Autowired
//    BasketController basketController;
//    @Autowired
//    ProductDao productDao;
//
//    @Test
//    public void testAddToBasket() {
//        // Given
//        Product product = new Product(1, "GEMHAC01", "Hacker játszma", "hacker-jatszma", "Gém Klub Kft.", 3190, "ACTIVE");
//        // When
//        ResponseStatus rs = basketController.addToBasket(1, 1);
//        Set<Product> products = basketController.listProductsOfBasket(new TestingAuthenticationToken("kovacsgeza", "KovacsGeza90"));
//        // Then
//        assertThat(products.size(), equalTo(1));
//        assertTrue(products.contains(product));
//        assertThat(rs.getStatus(), equalTo(ValidationStatus.SUCCESS));
//        assertThat(rs.getMessages().size(), equalTo(1));
//        assertThat(rs.getMessages().get(0), equalTo("A termék bekerült a kosárba!"));
//    }
//
//    @Test
//    public void testAddToBasketAlreadyExistingProduct() {
//        // Given
//        Product product = new Product(3, "AVALOR01", "Lord of Hellas", "lord-of-hellas", "Avaken Realms", 35990, "ACTIVE");
//        // When
//        ResponseStatus rs = basketController.addToBasket(3, 3);
//        Set<Product> products = basketController.listProductsOfBasket(new TestingAuthenticationToken("tadri1988", "Tadri1988"));
//        // Then
//        assertThat(products.size(), equalTo(2));
//        assertThat(rs.getStatus(), equalTo(ValidationStatus.FAIL));
//        assertThat(rs.getMessages().get(0), equalTo("A termék már a kosárban van!"));
//    }
//
//    @Test
//    public void testAddToBasketWithNotValidBasketId() {
//        // When
//        ResponseStatus rs = basketController.addToBasket(100, 1);
//        // Then
//        assertThat(rs.getStatus(), equalTo(ValidationStatus.FAIL));
//        assertThat(rs.getMessages().get(0), equalTo("Nem létező kosár vagy termék"));
//    }
//
//    @Test
//    public void testFlushBasket() {
//        // When
//        ResponseStatus rs = basketController.flushBasket(new TestingAuthenticationToken("tadri1988", "Tadri1988"));
//        // Then
//        assertThat(rs.getStatus(), equalTo(ValidationStatus.SUCCESS));
//        assertThat(rs.getMessages().get(0), equalTo("A kosár újra üres!"));
//    }
//
//    @Test
//    public void testFlushBasketIfDoesntExist() {
//        // When
//        ResponseStatus rs = basketController.flushBasket(new TestingAuthenticationToken("", ""));
//        // Then
//        assertThat(rs.getStatus(), equalTo(ValidationStatus.FAIL));
//        assertThat(rs.getMessages().get(0), equalTo("Nem létező kosár"));
//    }
//
//    @Test
//    public void testListProductsOfBasket() {
//        // Given
//        Product product = new Product(3, "AVALOR01", "Lord of Hellas", "lord-of-hellas", "Avaken Realms", 35990, "ACTIVE");
//        Product product2 = new Product(2, "GEMDIX01", "Dixit", "dixit", "Gém Klub Kft.", 7990, "ACTIVE");
//        // When
//        Set<Product> products = basketController.listProductsOfBasket(new TestingAuthenticationToken("tadri1988", "Tadri1988"));
//        // Then
//        assertTrue(products.contains(product));
//        assertTrue(products.contains(product2));
//    }
//
//    @Test
//    public void testDeleteItemFromBasket() {
//        // When
//        ResponseStatus rs = basketController.deleteItemFromBasket(3, 2);
//        // Then
//        assertThat(rs.getStatus(), equalTo(ValidationStatus.SUCCESS));
//        assertThat(rs.getMessages().get(0), equalTo("A terméket sikeresen eltávolítottuk a kosárból."));
//    }
//
//    @Test
//    public void testDeleteItemFromBasketIfDoesntExists() {
//        // When
//        ResponseStatus rs = basketController.deleteItemFromBasket(100, 100);
//        // Then
//        assertThat(rs.getStatus(), equalTo(ValidationStatus.FAIL));
//        assertThat(rs.getMessages().get(0), equalTo("Nem létező kosár"));
//    }
//
//}
