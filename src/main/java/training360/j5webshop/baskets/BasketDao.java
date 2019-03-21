package training360.j5webshop.baskets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import training360.j5webshop.products.Product;
import training360.j5webshop.products.ProductDao;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BasketDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProductDao productDao;

    public BasketDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void createBasket(long userId) {
        jdbcTemplate.update("insert into basket (users_id) values(?)", userId);
    }

    public void addToBasket(long basketId, Product product) {
        jdbcTemplate.update("insert into basket_item (basket_id, product_id) values(?, ?)", basketId, product.getId());
    }

    public void flushBasket(long basketId) {
        jdbcTemplate.update("delete from basket_item where basket_id = ?", basketId);
    }

    public List<Long> listProductIdsOfBasket(long basketId) {
        return jdbcTemplate.query("select product_id from basket_item where basket_id = ?", new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getLong("product_id");
            }
        }, basketId);
    }

    public Map<Product, Integer> listProductsOfBasket(long basketId) {
        Map<Product, Integer> productMap = new HashMap<>();
        for (Long id : listProductIdsOfBasket(basketId)) {
            productMap.put(productDao.findProductById(id), 1);
        }
        return productMap;
    }
}
