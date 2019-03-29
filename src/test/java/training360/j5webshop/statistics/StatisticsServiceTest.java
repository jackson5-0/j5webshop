package training360.j5webshop.statistics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import training360.j5webshop.products.ProductDao;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class StatisticsServiceTest {

    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private ProductDao productDao;

    @Test
    public void getStatistics(){
        //When
        productDao.deleteProductById(1);
        //Then
        assertThat(statisticsService.getStatistics().getNumberOfActiveProducts(),equalTo(3));
        assertThat(statisticsService.getStatistics().getNumberOfAllProducts(),equalTo(4));
        assertThat(statisticsService.getStatistics().getNumberOfActiveOrders(),equalTo(3));
        assertThat(statisticsService.getStatistics().getNumberOfAllOrders(),equalTo(6));
        assertThat(statisticsService.getStatistics().getNumberOfActiveUsers(),equalTo(4));
    }

}
