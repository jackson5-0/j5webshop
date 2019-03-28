package training360.j5webshop.reports;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import training360.j5webshop.orders.OrderStatus;

import java.time.Month;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class ReportIntegrationTest {
    @Autowired
    private ReportDao reportDao;

    @Test
    public void testSizeOfAllOrders() {
        //When
        int size = reportDao.sizeOfAllOrders();
        //Then
        assertThat(size, equalTo(6));
    }

    @Test
    public void testSizeOfActiveOrders() {
        //When
        int size = reportDao.sizeOfActiveOrders();
        //Then
        assertThat(size, equalTo(3));
    }

    @Test
    public void testListOrdersByMonthAndByStatus() {
        //When
        List<ReportOfOrders> list = reportDao.listOrdersByMonthAndByStatus();
        //Then
        assertThat(list.size(), equalTo(3));
        assertThat(list.get(0), equalTo(new ReportOfOrders(2019, Month.FEBRUARY, OrderStatus.ACTIVE, 1, 3290)));
        assertThat(list.get(1), equalTo(new ReportOfOrders(2019, Month.MARCH, OrderStatus.ACTIVE, 3, 47170)));
        assertThat(list.get(2), equalTo(new ReportOfOrders(2019, Month.MARCH, OrderStatus.DELIVERED, 3, 61970)));
    }

    @Test
    public void testListDeliveredProductsByMonth() {
        //When
        List<ReportOfProductSale> list = reportDao.listDeliveredProductsByMonth();
        //Then
        assertThat(list.size(), equalTo(3));
        assertThat(list.get(0), equalTo(new ReportOfProductSale(2019, Month.MARCH, "GEMDIX01", "Dixit", 7990, 1, 7990)));
        assertThat(list.get(1), equalTo(new ReportOfProductSale(2019, Month.MARCH, "AVALOR01", "Lord of Hellas", 35990, 1, 35990)));
        assertThat(list.get(0).getSum(), equalTo(list.get(0).getPrice() * list.get(0).getQuantity()));
        assertThat(list.get(1).getSum(), equalTo(list.get(1).getPrice() * list.get(1).getQuantity()));
        assertThat(list.get(2).getSum(), equalTo(list.get(2).getPrice() * list.get(2).getQuantity()));
    }
}
