package training360.j5webshop.baskets;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BasketDaoTest {

    private BasketDao basketDao;

    @Before
    public void init() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/j5webshoptest?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        dataSource.setUser("j5webshop");
        dataSource.setPassword("jacksonfive");

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

        basketDao = new BasketDao(dataSource);
    }
    @Test
    public void testFindByAddress() {
        //Given
        basketDao.createBasket(10);

        //When
        long basketId = basketDao.findBasketId(10);
        //Then
        assertThat(basketId,equalTo(1));
    }
}
