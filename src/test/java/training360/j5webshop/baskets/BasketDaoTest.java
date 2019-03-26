package training360.j5webshop.baskets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import training360.j5webshop.products.Product;
import training360.j5webshop.products.ProductDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql({"/basket_init.sql"})
public class BasketDaoTest {

    @Autowired
    BasketDao basketDao;
    @Autowired
    ProductDao productDao;

    @Test
    public void testAddToBasket() {
        // Given
        Product product = new Product(1, "GEMHAC01", "Hacker játszma", "hacker-jatszma", "Gém Klub Kft.", 3190, "ACTIVE");
        // When
        basketDao.addToBasket(2, 1);
        // Then
        assertThat(basketDao.listProductIdsOfBasket(2).size(), equalTo(1));
        assertThat(basketDao.findBasketItems(2).get(0), equalTo(product));
    }

    @Test
    public void testFlushBasket() {
        // When
        basketDao.flushBasket(1);
        // Then
        assertThat(basketDao.listProductIdsOfBasket(2).size(), equalTo(0));
    }

    @Test
    public void testListProductIdsOfBasket() {
        // When
        List<Long> ids = basketDao.listProductIdsOfBasket(1);
        // Then
        assertThat(ids.size(), equalTo(3));
        assertThat(ids.get(0), equalTo(1L));
        assertThat(ids.get(1), equalTo(2L));
        assertThat(ids.get(2), equalTo(3L));
    }



    public void testFindBasketId(long userId) {
        // When
        long id = basketDao.findBasketId(2);
        // Then
        assertThat(id, equalTo(2));
    }

    @Test
    public void testFindUserByBasketId() {
        // When
        long id = basketDao.findUserByBasketId(2);
        // Then
        assertThat(id, equalTo(2L));
    }

    public void testDeleteItemFromBasket() {
        // Given
        Product product = new Product();
        // When
        basketDao.deleteItemFromBasket(1, 3);
        // Then
        assertThat(basketDao.listProductIdsOfBasket(1).size(), equalTo(2));
        assertThat(basketDao.listProductIdsOfBasket(1).get(0), equalTo(1L));
        assertThat(basketDao.listProductIdsOfBasket(1).get(1), equalTo(2L));
    }


}
