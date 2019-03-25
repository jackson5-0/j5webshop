package training360.j5webshop.baskets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import training360.j5webshop.products.Product;
import training360.j5webshop.products.ProductDao;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BasketDao {

    private JdbcTemplate jdbcTemplate;

    public BasketDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void createBasket(long userId) {
        jdbcTemplate.update("insert into basket (users_id) values(?)", userId);
    }

    public void addToBasket(long basketId, long productId) {
        jdbcTemplate.update("insert into basket_item (basket_id, product_id) values(?, ?)", basketId, productId);
    }

    public int flushBasket(long basketId) {
            return jdbcTemplate.update("delete from basket_item where basket_id = ?", basketId);
    }

    public List<Long> listProductIdsOfBasket(long basketId) {
        return jdbcTemplate.query("select product_id from basket_item where basket_id = ?", new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getLong("product_id");
            }
        }, basketId);
    }

    public Long findBasketId(long userId) {
        return jdbcTemplate.queryForObject("select id from basket where users_id = ?",
                (rs, rowNum) -> rs.getLong("id"), userId);
    }

    public Long findUserByBasketId(long basketId) {
        return jdbcTemplate.queryForObject("select users_id from basket where id = ?",
                (rs, rowNum) -> rs.getLong("users_id"), basketId);
    }

    public int deleteItemFromBasket(long basketId, long productId) {
        return jdbcTemplate.update("delete from basket_item where basket_id=? and product_id=?", basketId, productId);
    }
}
