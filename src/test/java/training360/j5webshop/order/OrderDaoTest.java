package training360.j5webshop.order;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import training360.j5webshop.orders.Order;
import training360.j5webshop.orders.OrderDao;
import training360.j5webshop.products.Product;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class OrderDaoTest {

    private OrderDao orderDao;

    @Before
    public void init() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/j5webshoptest?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        dataSource.setUser("j5webshop");
        dataSource.setPassword("jacksonfive");

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

        orderDao = new OrderDao(dataSource);
    }


}
