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

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql({"/init.sql"})
public class BasketDaoTest {

    @Autowired
    BasketDao basketDao;
    @Autowired
    ProductDao productDao;

    @Test
    public void testAddToBasket() {
        // When
        basketDao.addToBasketWithQuantity(5,1, 1);
        basketDao.addToBasketWithQuantity(4,1, 2);
        basketDao.addToBasketWithQuantity(2,2,2);

        // Then
        assertThat(basketDao.listProductIdsOfBasket(1).size(), equalTo(5));
        assertThat(basketDao.listProductIdsOfBasket(2).size(), equalTo(9));
    }

    @Test
    public void testFlushBasket() {
        // Given
        basketDao.addToBasketWithQuantity(5,1, 1);
        basketDao.addToBasketWithQuantity(4,1, 2);
        // When
        basketDao.flushBasket(new TestingAuthenticationToken("kovacsgeza", "KovacsGeza90").getName());
        // Then
        assertThat(basketDao.listProductIdsOfBasket(1).size(), equalTo(0));
    }

    @Test
    public void testListProductIdsOfBasket() {
        // Given
        basketDao.addToBasketWithQuantity(5,1, 1);
        basketDao.addToBasketWithQuantity(4,2, 1);
        basketDao.addToBasketWithQuantity(2,3, 1);
        // When
        List<Long> ids = basketDao.listProductIdsOfBasket(1);
        // Then
        assertThat(ids.size(), equalTo(11));
        assertThat(ids.get(0), equalTo(1L));
        assertThat(ids.get(6), equalTo(2L));
        assertThat(ids.get(10), equalTo(3L));
    }


    @Test
    public void testFindBasketId() {
        // When
        long id = basketDao.findBasketId(2L);
        long id2 = basketDao.findBasketId(3L);
        // Then
        assertThat(id, equalTo(2L));
        assertThat(id2, equalTo(3L));
    }

    @Test
    public void testFindUserByBasketId() {
        // When
        long id = basketDao.findUserByBasketId(2);
        long id2 = basketDao.findUserByBasketId(3);
        // Then
        assertThat(id, equalTo(2L));
        assertThat(id2, equalTo(3L));
    }

    @Test
    public void testDeleteItemFromBasket() {
        // Given

        basketDao.addToBasketWithQuantity(5,1, 1);
        basketDao.addToBasketWithQuantity(4,2, 1);
        basketDao.addToBasketWithQuantity(2,3, 1);
        // When
        basketDao.deleteItemFromBasket(1, 3);
        // Then
        assertThat(basketDao.listProductIdsOfBasket(1).size(), equalTo(9));
        assertThat(basketDao.listProductIdsOfBasket(1).get(0), equalTo(1L));
        assertThat(basketDao.listProductIdsOfBasket(1).get(6), equalTo(2L));
    }

    @Test
    public void testBasketItemsWithQuantity() {
        // Given

        basketDao.addToBasketWithQuantity(5,1, 1);
        basketDao.addToBasketWithQuantity(4,2, 1);
        // When
        basketDao.basketItemsWithQuantity(1);
        // Then
        assertThat(basketDao.basketItemsWithQuantity(1).size(), equalTo(2));
        assertThat(basketDao.basketItemsWithQuantity(1).get(0).getQuantity(), equalTo(5));
        assertThat(basketDao.basketItemsWithQuantity(1).get(1).getProduct().getAddress(), equalTo("dixit"));
    }
    @Test
    public void testDecreaseAmountInBasket() {
        // Given

        basketDao.addToBasketWithQuantity(5,1, 1);
        basketDao.addToBasketWithQuantity(4,2, 1);
        // When
        basketDao.decreaseAmountInBasket(1,1,2);
        // Then
        assertThat(basketDao.listProductIdsOfBasket(1).size(), equalTo(7));
        assertThat(basketDao.listProductIdsOfBasket(1).get(0), equalTo(1L));
        assertThat(basketDao.listProductIdsOfBasket(1).get(4), equalTo(2L));
}


}
