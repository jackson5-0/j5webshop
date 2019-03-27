package training360.j5webshop.order;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import training360.j5webshop.baskets.Basket;
import training360.j5webshop.baskets.BasketDao;
import training360.j5webshop.orders.*;
import training360.j5webshop.products.Product;
import training360.j5webshop.products.ProductDao;
import training360.j5webshop.users.User;
import training360.j5webshop.users.UserDao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")

public class OrderDaoTest {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private BasketDao basketDao;
    @Autowired
    private ProductDao productDao;


    @Test
    public void addOrderedPorductTest(){
        Basket basket = basketDao.findBasket(2L);
        //When
        orderDao.addOrderedProduct(1L, basket);
        List<OrderedProduct> orderedProducts = orderDao.findOrderedProductByOrderId(1L);
        //Than
        assertThat(orderedProducts.size(), equalTo(3));
        assertThat(orderedProducts.get(0).getName(), equalTo(new OrderedProduct(productDao.findProductById(2), 1, 7000).getName()));
    }
    @Test
    public void listAllOrderTest(){
        //When
        orderDao.addOrderedProduct(4L, basketDao.findBasket(3));
        List<Order> orderList = orderDao.listAllOrder("kissbeci");
        // Than
        assertThat(orderList.size(), equalTo(3));
        assertThat(orderList.get(0).getPurchaseDate(), equalTo(LocalDateTime.of(2019, 03, 26, 0, 0)));
    }

    @Test
    public void listActiveOrderTest(){
        //When
        orderDao.addOrderedProduct(4L, basketDao.findBasket(3));
        List<Order> orderList = orderDao.listActiveOrder("kissbeci");
        System.out.println(orderList);
        // Than
        assertThat(orderList.size(), equalTo(2));
        assertThat(orderList.get(0).getOrderedProduct().size(), equalTo(2));
        assertThat(orderList.get(1).getOrderStatus(), equalTo(OrderStatus.valueOf("ACTIVE")));

    }

    @Test
    public void testListAllOrderWithDeleted() {
        //Given
        orderDao.createOrder(2L);
        orderDao.addOrderedProduct(2L, basketDao.findBasket(2L));
        orderDao.deleteWholeOrder(2L);
        //When
        List<Order> list = orderDao.listAllOrderWithDeleted("nagygizi22");
        //Then
        assertThat(list.size(), equalTo(2));
    }


    @Test
    public void testListAdminOrders() {
        //When
        List<OrderInfo> list = orderDao.listAdminOrders();
        //Then
        assertThat(list.size(), equalTo(6));
    }


    @Test
    public void testListActiveAdminOrders() {
        //Given
        orderDao.addOrderedProduct(2L, basketDao.findBasket(2L));
        //When
        List<OrderInfo> list = orderDao.listActiveAdminOrders();
        //Then
        assertThat(list.size(), equalTo(3));
    }

    @Test
    public void testDeleteItem() {
        //Given
        orderDao.addOrderedProduct(2L, basketDao.findBasket(2L));
        //When
        List<OrderedProduct> listBeforeDelete = orderDao.findOrderedProductByOrderId(2);
        orderDao.deleteItem(2L, "dixit");
        List<OrderedProduct> listAfterDelete = orderDao.findOrderedProductByOrderId(2);
        //Then
        assertThat(listBeforeDelete.size(), equalTo(3));
        assertThat(listAfterDelete.size(), equalTo(2));
    }

    @Test
    public void testDeleteWholeOrder() {
        //Given
        orderDao.addOrderedProduct(2L, basketDao.findBasket(2L));
        //When
        List<OrderedProduct> listBeforeDelete = orderDao.findOrderedProductByOrderId(2);
        orderDao.deleteWholeOrder(2L);
        List<OrderedProduct> listAfterDelete = orderDao.findOrderedProductByOrderId(2);
        //Then
        assertThat(listBeforeDelete.size(), equalTo(3));
        assertThat(listAfterDelete.size(), equalTo(0));
    }

    public void testTotalPrice() {
        //Given
        orderDao.addOrderedProduct(2L, basketDao.findBasket(2L));
        //When
        int totalPrice = orderDao.totalPrice(2L);
        //Then
        assertThat(totalPrice, equalTo(61970));
    }

    @Test
    public void testFindOrderedProductByOrderId() {
        //Given
        orderDao.addOrderedProduct(2L, basketDao.findBasket(2L));
        //When
        List<OrderedProduct> list = orderDao.findOrderedProductByOrderId(2);
        //Then
        assertThat(list.size(), equalTo(3));
    }


    @Test
    public void testChangeStatuseByIdAndListAllOrder() {
         //When
        orderDao.changeStatusById(2);
        //Then
        assertThat(orderDao.listAllOrder("tadri1988").get(0).getOrderStatus(), equalTo(OrderStatus.DELIVERED));
    }
}
