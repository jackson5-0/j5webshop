//package training360.j5webshop.products;
//
//import com.mysql.cj.jdbc.MysqlDataSource;
//import org.flywaydb.core.Flyway;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.MatcherAssert.assertThat;
//
//public class ProductDaoTest {
//
//    private ProductDao productDao;
//
//    @Before
//    public void init() {
//        MysqlDataSource dataSource = new MysqlDataSource();
//        dataSource.setUrl("jdbc:mysql://localhost:3306/j5webshop?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
//
//        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
//        flyway.clean();
//        flyway.migrate();
//
//        productDao = new ProductDao(dataSource);
//    }
//
//    @Test
//    public void testInsertThanQuery() {
//        //Given
//        productDao.createProduct("GEMHAC01", "Hacker játszma", "hacker-jatszma", "Gém Klub Kft.", 3190);
//        //When
//        Product product = productDao.findProductByAddress("hacker-jatszma");
//        //Then
//        assertThat(product.getName(), equalTo("Hacker játszma"));
//        assertThat(product.getCode(), equalTo("GEMHAC01"));
//        assertThat(product.getPrice(), equalTo(3190));
//
//    }
//

//}
