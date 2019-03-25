package training360.j5webshop.baskets;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import training360.j5webshop.products.Product;
import training360.j5webshop.users.User;
import training360.j5webshop.users.UserDao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BasketDaoTest {

    private BasketDao basketDao;
    private UserDao userDao;

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
        userDao = new UserDao(dataSource);

    }
    @Test
    public void createBasketTest() {
        //Given
       // userDao.addUser(new User("Gipsz","Jakab","GiJa","isler"));
        User jakab = new User("Gipsz","Jakab","GiJa","isler");
        userDao.addUser(jakab);
        basketDao.createBasket(jakab.getId());

        //When
        long basketId = basketDao.findBasketId(jakab.getId());
        //Then
        assertThat(basketId,equalTo(4));
    }

    @Test
    public void addToBasketTest(){
        //Given
        User jakab = new User("Gipsz","Jakab","GiJa","isler");
        userDao.addUser(jakab);
        basketDao.createBasket(jakab.getId());
        basketDao.addToBasket(4, new Product("GEMHAC01", "Hacker játszma", "hacker-jatszma", "Gém Klub Kft.",1500));
        //When
        int size =basketDao.listProductIdsOfBasket(4).size();
        //Then
        assertThat(size,equalTo(1));
    }
    @Test
    public void flushBasketTest(){
        //Given
        User jakab = new User("Gipsz","Jakab","GiJa","isler");
        userDao.addUser(jakab);
        basketDao.createBasket(jakab.getId());
        basketDao.addToBasket(4, new Product("GEMHAC01", "Hacker játszma", "hacker-jatszma", "Gém Klub Kft.",1500));
        //When
        basketDao.flushBasket(4);
        //Then
        assertThat(basketDao.listProductIdsOfBasket(4).size(),equalTo(0));
    }
    @Test
    public void listProductIdsOfBasketTest(){
        //Given
        User jakab = new User("Gipsz","Jakab","GiJa","isler");
        userDao.addUser(jakab);
        basketDao.createBasket(jakab.getId());
        basketDao.addToBasket(4, new Product("GEMHAC01", "Hacker játszma", "hacker-jatszma", "Gém Klub Kft.",1500));
        //When
        long productId = basketDao.listProductIdsOfBasket(4).get(0);
        //Then
        assertThat(productId, equalTo(1));
    }
    @Test
    public void findBasketIdTest() {
      //Given
        User jakab = new User("Gipsz","Jakab","GiJa","isler");
        userDao.addUser(jakab);
     //   basketDao.createBasket(jakab.getId());
        basketDao.addToBasket(4, new Product("GEMHAC01", "Hacker játszma", "hacker-jatszma", "Gém Klub Kft.",1500));
        //When
        long basketId = basketDao.findBasketId(4);
        //Then
        assertThat(basketId,equalTo(4));
    }
    @Test
    public void findUserByBasketIdTest(){
        //Given
        User jakab = new User("Gipsz","Jakab","GiJa","isler");
        userDao.addUser(jakab);
        basketDao.createBasket(jakab.getId());
        basketDao.addToBasket(4, new Product("GEMHAC01", "Hacker játszma", "hacker-jatszma", "Gém Klub Kft.",1500));
        //When
        long userId = basketDao.findUserByBasketId(4);
        //Then
        assertThat(userId,equalTo(4));

    }
}
