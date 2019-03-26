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
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/order_init.sql")

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
        assertThat(orderedProducts.get(0).getName(), equalTo(new OrderedProduct(productDao.findProductById(2), 1).getName()));
    }
    @Test
    public void listAllOrderTest(){
        //When
        orderDao.addOrderedProduct(4L, basketDao.findBasket(3));
        List<Order> orderList = orderDao.listAllOrder("kissbeci");
        // Than
        assertThat(orderList.size(), equalTo(3));
        assertThat(orderList.get(0).getPurchaseDate(), equalTo(LocalDate.of(2019, 03, 26)));
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



}
