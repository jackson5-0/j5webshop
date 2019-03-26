package training360.j5webshop.order;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import training360.j5webshop.baskets.BasketController;
import training360.j5webshop.orders.OrderController;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/order_init.sql")
public class OrderIntegrationTest {

    @Autowired
    private OrderController orderController;

    @Autowired
    private BasketController basketController;

    @Test
    public void createOrderTest() {
        // When
        int basketSizeBeforeCreateOrder = basketController.listProductsOfBasket(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).size();
        orderController.createOrder(2);
        int basketSizeAfterCreateOrder = basketController.listProductsOfBasket(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).size();

        // Then
        assertThat(orderController.listAllOrderWithDeleted(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).size(), equalTo(3));
        assertThat(orderController.listActiveOrder(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).size(), equalTo(1));
        assertThat(orderController.listAllOrder(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).get(0).getOrderedProduct().size(), equalTo(3));
        assertThat(basketController.listProductsOfBasket(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).size(), equalTo(0));
        assertThat(basketSizeBeforeCreateOrder, equalTo(3));
        assertThat(basketSizeAfterCreateOrder, equalTo(0));
    }

    @Test
    public void listAllAndListAllOrderWithDeletedOrderTest() {
        // When
        orderController.createOrder(2);
        int sizeOfListWithDeleted = orderController.listAllOrderWithDeleted(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).size();
        int sizeOfListWithoutDeleted = orderController.listAllOrder(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).size();

        // Then
        assertThat(sizeOfListWithDeleted, equalTo(3));
        assertThat(sizeOfListWithoutDeleted, equalTo(2));
    }

    @Test
    public void listActiveOrderTest() {
        // When
        orderController.createOrder(2);
        int sizeOfListWithDeleted = orderController.listAllOrderWithDeleted(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).size();
        int sizeOfActiveList = orderController.listActiveOrder(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).size();

        // Then
        assertThat(sizeOfListWithDeleted, equalTo(3));
        assertThat(sizeOfActiveList, equalTo(1));
    }

    @Test
    public void findOrderedProductByOrderIdTest() {
        // When
        orderController.createOrder(2);
        int numberOfProducts = orderController.findOrderedProductByOrderId(4).size();

        // Then
        assertThat(numberOfProducts, equalTo(3));
    }

    @Test
    public void listAdminOrdersTest() {
        // When
        int numberOfOrders = orderController.listAdminOrders().size();

        // Then
        assertThat(numberOfOrders, equalTo(3));
    }

    @Test
    public void listActiveOrdersTest() {
        // When
        int numberOfOrders = orderController.listActiveOrders().size();

        // Then
        assertThat(numberOfOrders, equalTo(1));
    }
}
