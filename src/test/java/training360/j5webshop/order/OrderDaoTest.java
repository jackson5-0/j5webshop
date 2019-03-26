package training360.j5webshop.order;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import training360.j5webshop.baskets.Basket;
import training360.j5webshop.baskets.BasketDao;
import training360.j5webshop.orders.Order;
import training360.j5webshop.orders.OrderDao;
import training360.j5webshop.orders.OrderService;
import training360.j5webshop.orders.OrderedProduct;
import training360.j5webshop.products.Product;
import training360.j5webshop.products.ProductDao;
import training360.j5webshop.users.User;
import training360.j5webshop.users.UserDao;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
@RunWith(SpringRunner.class)
@SpringBootTest
@Sql({ "/product_init.sql", "/user_init.sql", "/basket_init.sql"} )
public class OrderDaoTest {

    private OrderDao orderDao;
    private UserDao userDao;
    private BasketDao basketDao;
    private ProductDao productDao;
    private OrderService orderService;


    @Test
    public void createOrderTest(){
        //when




    }


}
