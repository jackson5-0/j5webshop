//package training360.j5webshop.baskets;
//
//import com.mysql.cj.jdbc.MysqlDataSource;
//import org.flywaydb.core.Flyway;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit4.SpringRunner;
//import training360.j5webshop.products.Product;
//import training360.j5webshop.users.User;
//import training360.j5webshop.users.UserDao;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.MatcherAssert.assertThat;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Sql({"basket_init.sql", "/product_init.sql"})
//public class BasketDaoTest {
//
//    @Autowired
//    BasketDao basketDao;
//
//    @Test
//    public void testAddToBasket() {
//        long basketId = basketDao.findBasketId(2);
//        Product product = new Product("Test", "Test", 10_000);
//        basketDao.addToBasket(basketId, product);
//        assertThat(basketDao.listProductIdsOfBasket(2), equalTo(basketDao.));
//    }
//
//    public void flushBasket(long basketId) {
//        jdbcTemplate.update("delete from basket_item where basket_id = ?", basketId);
//    }
//
//    public List<Long> listProductIdsOfBasket(long basketId) {
//        return jdbcTemplate.query("select product_id from basket_item where basket_id = ?", new RowMapper<Long>() {
//            @Override
//            public Long mapRow(ResultSet resultSet, int i) throws SQLException {
//                return resultSet.getLong("product_id");
//            }
//        }, basketId);
//    }
//
//    public Long findBasketId(long userId) {
//        return jdbcTemplate.queryForObject("select id from basket where users_id = ?",
//                (rs, rowNum) -> rs.getLong("id"), userId);
//    }
//
//    public long findUserByBasketId(long basketId) {
//        return jdbcTemplate.queryForObject("select users_id from basket where id = ?",
//                (rs, rowNum) -> rs.getLong("users_id"), basketId);
//    }
//
//    public void deleteItemFromBasket(long basketId, long productId) {
//        jdbcTemplate.update("delete from basket_item where basket_id=? and product_id=?", basketId, productId);
//    }
//
//}
