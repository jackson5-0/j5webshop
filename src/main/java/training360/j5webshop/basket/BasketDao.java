package training360.j5webshop.basket;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import training360.j5webshop.products.Product;

import javax.sql.DataSource;
import java.util.Map;

@Repository
public class BasketDao {

    private JdbcTemplate jdbcTemplate;

    public BasketDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void createBasket(long userId) {
        jdbcTemplate.update("insert into basket (users_id) values(?)", userId);
    }

    public void addToBasket(long id, Product product) {
//        jdbcTemplate.update("insert into basket_item (basket_id, product_id) values(?, ?)", id, product.getId);
    }
}
