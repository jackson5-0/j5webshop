package training360.j5webshop.baskets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import training360.j5webshop.products.Product;
import training360.j5webshop.products.ProductDao;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql({"/basket_init.sql", "/product_init.sql"})
public class BasketDaoTest {

    @Autowired
    BasketDao basketDao;
    @Autowired
    ProductDao productDao;

    @Test
    public void testAddToBasket() {
        // Given
        Product product = new Product("name", "publisher", 10_000);
        long id = productDao.createProduct(product);
        // When
        basketDao.addToBasket(2, id);
        // Then
        assertThat(basketDao.listProductIdsOfBasket(2).size(), equalTo(1));
        assertThat(basketDao.listProductIdsOfBasket(2).get(0), equalTo(id));
    }

    @Test
    public void testFlushBasket() {
        // Given
        Product product = new Product("name", "publisher", 10_000);
        Product product2 = new Product("name2", "publisher2", 10_000);
        long id = productDao.createProduct(product);
        long id2 = productDao.createProduct(product2);
        // When
        basketDao.addToBasket(2, id);
        basketDao.addToBasket(2, id2);
        basketDao.flushBasket(2);
        // Then
        assertThat(basketDao.listProductIdsOfBasket(2).size(), equalTo(0));
    }

    @Test
    public void testListProductIdsOfBasket() {
        // Given
        Product product = new Product("name", "publisher", 10_000);
        Product product2 = new Product("name", "publisher", 10_000);
        long id = productDao.createProduct(product);
        long id2 = productDao.createProduct(product2);
        // When
        basketDao.addToBasket(2, id);
        basketDao.addToBasket(2, id2);
        List<Long> ids = basketDao.listProductIdsOfBasket(2);
        // Then
        assertThat(ids.size(), equalTo(2));
        assertThat(ids.get(0), equalTo(id));
        assertThat(ids.get(1), equalTo(id2));
    }



    public void testFindBasketId(long userId) {
        // When
        long id = basketDao.findBasketId(2);
        // Then
        assertThat(id, equalTo(2));
    }

    public void testFindBasketIdIfDoesntExist(long userId) {
        // When
        long id = basketDao.findBasketId(2);
        // Then
        assertThat(id, equalTo(0));
    }


    public void testFindUserByBasketId() {
        // When
        long id = basketDao.findUserByBasketId(2);
        // Then
        assertThat(id, equalTo(2));
    }

    public void testFindUserByBasketIdIfDoesntExist() {
        // When
        long id = basketDao.findUserByBasketId(100);
        // Then
        assertThat(id, equalTo(0));
    }

    public void testDeleteItemFromBasket() {
        // Given
        Product product = new Product("name", "publisher", 10_000);
        long id = productDao.createProduct(product);
        // When
        basketDao.addToBasket(2, id);
        basketDao.deleteItemFromBasket(2, id);
        // Then
        assertThat(basketDao.listProductIdsOfBasket(2).size(), equalTo(0));
    }


}
