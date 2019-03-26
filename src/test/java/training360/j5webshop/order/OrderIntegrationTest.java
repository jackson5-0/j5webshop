package training360.j5webshop.order;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        int basketSizeBeforeCreateOrder = basketController.listProductsOfBasket(2).size();
        orderController.createOrder(2);
        int basketSizeAfterCreateOrder = basketController.listProductsOfBasket(2).size();

        // Then
        assertThat(orderController.listAllOrder().size(), equalTo(1));
        assertThat(orderController.listActiveOrder().size(), equalTo(1));
        assertThat(orderController.listAllOrder().get(0).getUserId(), equalTo(2L));
        assertThat(orderController.listActiveOrder().get(0).getUserId(), equalTo(2L));
        assertThat(orderController.listAllOrder().get(0).getOrderedProduct().size(), equalTo(3));
        assertThat(basketController.listProductsOfBasket(2).size(), equalTo(0));
        assertThat(basketSizeBeforeCreateOrder, equalTo(3));
        assertThat(basketSizeAfterCreateOrder, equalTo(0));
    }

    @Test
    public void listAllOrderTest() {
        // When
        orderController.createOrder(2);
        orderController.createOrder(3);

        // Then
        assertThat(orderController.listAllOrder().size(), equalTo(2));
        orderController.deleteOrders(2);
        assertThat(orderController.listAllOrder().size(), equalTo(1));
    }

    @Test
    public void listActiveOrderTest() {
        // When
        orderController.createOrder(2);
        orderController.createOrder(3);

        // Then
        assertThat(orderController.listAllOrder().size(), equalTo(2));
        orderController.deleteOrders(2);
        assertThat(orderController.listActiveOrders().size(), equalTo(1));
    }

    @Test
    public void listAllOrderWithDeletedTest() {
        // When
        orderController.createOrder(2);
        orderController.createOrder(3);

        // Then
        assertThat(orderController.listAllOrder().size(), equalTo(2));
        orderController.deleteOrders(2);
        assertThat(orderController.listAllOrderWithDeleted().size(), equalTo(2));
    }

    @Test
    public void findOrderedProductByOrderIdTest() {
        // When
        orderController.createOrder(2);

        // Then
        System.out.println(orderController.findOrderedProductByOrderId(1).size());
        System.out.println(orderController.findOrderedProductByOrderId(1));
    }
}
