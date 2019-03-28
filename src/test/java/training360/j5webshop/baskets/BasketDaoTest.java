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
//
//import java.util.List;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.MatcherAssert.assertThat;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Sql({"/init.sql"})
//public class BasketDaoTest {
//
//    @Autowired
//    BasketDao basketDao;
//    @Autowired
//    ProductDao productDao;
//
//    @Test
//    public void testAddToBasket() {
//        // Given
//        Product product = new Product(1, "GEMHAC01", "Hacker játszma", "hacker-jatszma", "Gém Klub Kft.", 3190, "ACTIVE");
//        Product product2 = new Product(2, "GEMDIX01", "Dixit", "dixit", "Gém Klub Kft.", 7990, "ACTIVE");
//        // When
//        basketDao.addToBasket(1, 1);
//        basketDao.addToBasket(1, 2);
//        // Then
//        assertThat(basketDao.listProductIdsOfBasket(1).size(), equalTo(2));
//        assertThat(basketDao.findBasketItems(1).get(0), equalTo(product));
//        assertThat(basketDao.findBasketItems(1).get(1), equalTo(product2));
//    }
//
//    @Test
//    public void testFlushBasket() {
//        // Given
//        basketDao.addToBasket(1, 1);
//        basketDao.addToBasket(1, 2);
//        // When
//        basketDao.flushBasket(new TestingAuthenticationToken("kovacsgeza", "KovacsGeza90").getName());
//        // Then
//        assertThat(basketDao.listProductIdsOfBasket(1).size(), equalTo(0));
//    }
//
//    @Test
//    public void testListProductIdsOfBasket() {
//        // Given
//        basketDao.addToBasket(1, 1);
//        basketDao.addToBasket(1, 2);
//        basketDao.addToBasket(1, 3);
//        // When
//        List<Long> ids = basketDao.listProductIdsOfBasket(1);
//        // Then
//        assertThat(ids.size(), equalTo(3));
//        assertThat(ids.get(0), equalTo(1L));
//        assertThat(ids.get(1), equalTo(2L));
//        assertThat(ids.get(2), equalTo(3L));
//    }
//
//
//    @Test
//    public void testFindBasketId() {
//        // When
//        long id = basketDao.findBasketId(2);
//        long id2 = basketDao.findBasketId(3);
//        // Then
//        assertThat(id, equalTo(2L));
//        assertThat(id2, equalTo(3L));
//    }
//
//    @Test
//    public void testFindUserByBasketId() {
//        // When
//        long id = basketDao.findUserByBasketId(2);
//        long id2 = basketDao.findUserByBasketId(3);
//        // Then
//        assertThat(id, equalTo(2L));
//        assertThat(id2, equalTo(3L));
//    }
//
//    @Test
//    public void testDeleteItemFromBasket() {
//        // Given
//        basketDao.addToBasket(1, 1);
//        basketDao.addToBasket(1, 2);
//        basketDao.addToBasket(1, 3);
//        // When
//        basketDao.deleteItemFromBasket(1, 3);
//        // Then
//        assertThat(basketDao.listProductIdsOfBasket(1).size(), equalTo(2));
//        assertThat(basketDao.listProductIdsOfBasket(1).get(0), equalTo(1L));
//        assertThat(basketDao.listProductIdsOfBasket(1).get(1), equalTo(2L));
//    }
//
//
//}
