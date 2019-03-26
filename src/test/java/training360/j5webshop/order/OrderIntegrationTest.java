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
        System.out.println(orderController.listAllOrder(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")));
        assertThat(orderController.listAllOrder(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).size(), equalTo(2));
        assertThat(orderController.listActiveOrder(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).size(), equalTo(1));
        assertThat(orderController.listAllOrder(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).get(0).getUserId(), equalTo(2L));
        assertThat(orderController.listActiveOrder(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).get(0).getUserId(), equalTo(2L));
        assertThat(orderController.listAllOrder(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).get(0).getOrderedProduct().size(), equalTo(3));
        assertThat(basketController.listProductsOfBasket(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).size(), equalTo(0));
        assertThat(basketSizeBeforeCreateOrder, equalTo(3));
        assertThat(basketSizeAfterCreateOrder, equalTo(0));
    }

    @Test
    public void listAllOrderTest() {
        // When
        orderController.createOrder(2);

        // Then
        assertThat(orderController.listAllOrder(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).size(), equalTo(2));
        orderController.deleteOrders(2);
        assertThat(orderController.listAllOrder(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).size(), equalTo(1));
    }

    @Test
    public void listActiveOrderTest() {
        // When
        orderController.createOrder(2);
        orderController.createOrder(3);

        // Then
        assertThat(orderController.listAllOrder(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).size(), equalTo(2));
        orderController.deleteOrders(2);
        assertThat(orderController.listActiveOrders().size(), equalTo(1));
    }

    @Test
    public void listAllOrderWithDeletedTest() {
        // When
        orderController.createOrder(2);
        orderController.createOrder(3);

        // Then
        assertThat(orderController.listAllOrder(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).size(), equalTo(2));
        orderController.deleteOrders(2);
        assertThat(orderController.listAllOrderWithDeleted(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).size(), equalTo(2));
    }

    @Test
    public void findOrderedProductByOrderIdTest() {
        // When
        orderController.createOrder(2);

        // Then
        assertThat(orderController.findOrderedProductByOrderId(1).size(), equalTo(3));
    }
}
