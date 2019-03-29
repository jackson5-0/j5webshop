package training360.j5webshop.reports;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import training360.j5webshop.orders.OrderStatus;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class ReportDaoTest {

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
        assertThat(list.get(0).getYear(), equalTo(2019));
        assertThat(list.get(0).getMonth(), equalTo("február"));
        assertThat(list.get(0).getStatus(), equalTo(OrderStatus.ACTIVE));
        assertThat(list.get(0).getNumberOfOrders(), equalTo(1));
        assertThat(list.get(0).getValueOfOrders(), equalTo(3290));
        assertThat(list.get(1).getMonth(), equalTo("március"));
        assertThat(list.get(1).getStatus(), equalTo(OrderStatus.ACTIVE));
        assertThat(list.get(1).getNumberOfOrders(), equalTo(3));
        assertThat(list.get(1).getValueOfOrders(), equalTo(47170));
        assertThat(list.get(2).getStatus(), equalTo(OrderStatus.DELIVERED));
        assertThat(list.get(2).getNumberOfOrders(), equalTo(3));
        assertThat(list.get(2).getValueOfOrders(), equalTo(61970));
    }

    @Test
    public void testListDeliveredProductsByMonth() {
        //When
        List<ReportOfProductSale> list = reportDao.listDeliveredProductsByMonth();
        //Then
        assertThat(list.size(), equalTo(3));
        assertThat(list.get(0).getYear(), equalTo(2019));
        assertThat(list.get(0).getMonth(), equalTo("március"));
        assertThat(list.get(0).getCode(), equalTo("GEMDIX01"));
        assertThat(list.get(0).getName(), equalTo("Dixit"));
        assertThat(list.get(0).getPrice(), equalTo(7990));
        assertThat(list.get(0).getQuantity(), equalTo(1));
        assertThat(list.get(0).getSum(), equalTo(7990));
        assertThat(list.get(1).getYear(), equalTo(2019));
        assertThat(list.get(1).getMonth(), equalTo("március"));
        assertThat(list.get(1).getCode(), equalTo("AVALOR01"));
        assertThat(list.get(1).getName(), equalTo("Lord of Hellas"));
        assertThat(list.get(1).getPrice(), equalTo(35990));
        assertThat(list.get(1).getQuantity(), equalTo(1));
        assertThat(list.get(1).getSum(), equalTo(35990));
    }
}
