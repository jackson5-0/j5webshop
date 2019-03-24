//package training360.j5webshop.order;
//
//import com.mysql.cj.jdbc.MysqlDataSource;
//import org.flywaydb.core.Flyway;
//import org.junit.Before;
//import org.junit.Test;
//import training360.j5webshop.baskets.Basket;
//import training360.j5webshop.baskets.BasketDao;
//import training360.j5webshop.orders.Order;
//import training360.j5webshop.orders.OrderDao;
//import training360.j5webshop.orders.OrderService;
//import training360.j5webshop.orders.OrderedProduct;
//import training360.j5webshop.products.Product;
//import training360.j5webshop.products.ProductDao;
//import training360.j5webshop.users.User;
//import training360.j5webshop.users.UserDao;
//
//import java.util.List;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.MatcherAssert.assertThat;
//
//public class OrderDaoTest {
//
//    private OrderDao orderDao;
//    private UserDao userDao;
//    private BasketDao basketDao;
//    private ProductDao productDao;
//    private OrderService orderService;
//
//    @Before
//    public void init() {
//        MysqlDataSource dataSource = new MysqlDataSource();
//        dataSource.setUrl("jdbc:mysql://localhost:3306/j5webshoptest?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
//        dataSource.setUser("j5webshop");
//        dataSource.setPassword("jacksonfive");
//
//        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
//        flyway.clean();
//        flyway.migrate();
//
//        orderDao = new OrderDao(dataSource);
//        userDao = new UserDao(dataSource);
//        basketDao = new BasketDao(dataSource);
//        productDao = new ProductDao(dataSource);
//        orderService = new OrderService(orderDao);
//    }
//
//    @Test
//    public void createOrderTest(){
//        //Given
//        userDao.addUser(new User("John", "Doe", "JODO", "xyk"));
//        userDao.addUser(new User("Jane", "Doe", "JADO", "ztf"));
//        basketDao.createBasket(userDao.getUserId("JODO"));
//        productDao.createProduct(new Product("GEMHAC01", "Hacker játszma", "hacker-jatszma", "Gém Klub Kft.", 3190));
//        productDao.createProduct(new Product("GEMDIX01", "Dixit", "dixit", "Gém Klub Kft.", 7990));
//        basketDao.addToBasket(basketDao.findBasketId(userDao.getUserId("JODO")), productDao.findProductById(1));
//        basketDao.addToBasket(basketDao.findBasketId(userDao.getUserId("JODO")),productDao.findProductById(2));
//
//
//        Long orderId = orderService.createOrder(new Basket(4L, 4L));
//
//
//
//        List<OrderedProduct> list = orderDao.findOrderedProductByOrderId(orderId);
//
//        assertThat(list.size(), equalTo(2));
//        assertThat(list.get(0).getName(), equalTo("Hacker játszma"));
//        assertThat(list.get(1).getName(), equalTo("Dixit"));
//    }
//
//
//}
