package training360.j5webshop.baskets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import training360.j5webshop.orders.OrderStatus;
import training360.j5webshop.products.Product;
import training360.j5webshop.products.ProductDao;
import training360.j5webshop.products.ProductStatus;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class BasketDao {

    private JdbcTemplate jdbcTemplate;

    public BasketDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

   // public void createBasket(long userId) {
//        jdbcTemplate.update("insert into basket (users_id) values(?)", userId);
//                }

    public long createBasket(long userId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement
                            ("insert into basket (users_id) values(?)", Statement.RETURN_GENERATED_KEYS);
                    ps.setLong(1, userId);
                    return ps;
                }, keyHolder
        );
        return keyHolder.getKey().longValue();
    }

//    public void addToBasket(long basketId, long productId) throws DataIntegrityViolationException {
//            jdbcTemplate.update("insert into basket_item (basket_id, product_id) values(?, ?)", basketId, productId);
//    }

    public void addToBasketWithQuantity(int quantity, long productId, long basketId) throws DataIntegrityViolationException{
        for(int i =0; i<quantity;i++)
        jdbcTemplate.update("insert into basket_item (basket_id, product_id) values(?, ?)", basketId, productId);
    }

    public int flushBasket(String userName) {
        return jdbcTemplate.update("DELETE basket_item FROM basket_item \n" +
                "join basket on basket.id = basket_item.basket_id \n" +
                "where basket.users_id = (select users.id from users where username = ?)", userName);
    }

    public List<Long> listProductIdsOfBasket(long basketId) {
        return jdbcTemplate.query("select product_id from basket_item where basket_id = ?", new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getLong("product_id");
            }
        }, basketId);
    }

    public List<Product> findBasketItems(long basketId) {
        return jdbcTemplate.query("select product.id, code, name, address, publisher, price, status from product " +
                "join basket_item on product.id = basket_item.product_id " +
                "where basket_item.basket_id = ?",
                (rs, rowNum) -> new Product(
                        rs.getLong("product.id"),
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("publisher"),
                        rs.getInt("price"),
                        rs.getString("status")), basketId
                );
    }

    public List<Product> findBasketProductsByUserName(String userName) {
        return jdbcTemplate.query("select product.id, code, name, address, publisher, price, status from product \n" +
                "join basket_item on product.id = basket_item.product_id \n" +
                "join basket on basket_item.basket_id = basket.id\n" +
                "where basket.users_id =(select users.id from users where users.username = ?)",
                (rs, rowNum) -> new Product(
                        rs.getLong("product.id"),
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("publisher"),
                        rs.getInt("price"),
                        rs.getString("status")), userName
        );
    }

    public Basket findBasket(long basketId) {
        List<Long> productIds = listProductIdsOfBasket(basketId);
        long userId = findUserByBasketId(basketId);
        Basket basket = new Basket(basketId, userId);
        for (Long productId : productIds) {
            Product product = jdbcTemplate.queryForObject("select id, code, name, address, publisher, price, status from product where id = ?",
                    (rs, rowNum) -> new Product(
                            rs.getLong("id"),
                            rs.getString("code"),
                            rs.getString("name"),
                            rs.getString("address"),
                            rs.getString("publisher"),
                            rs.getInt("price"),
                            rs.getString("status")),
                    productId);
            basket.addProduct(product, 1);
        }
        return basket;
    }

    public Long findBasketId(Long userId) {
        return jdbcTemplate.queryForObject("select id from basket where users_id = ?",
                (rs, rowNum) -> rs.getLong("id"), userId);
    }

    public Long findBasketIdByUserName(String userName){
        return jdbcTemplate.queryForObject("select basket.id from basket join users on basket.users_id = users.id where users.username=?",
                (rs, rowNum) -> rs.getLong("basket.id"), userName);
    }

    public Long findUserByBasketId(long basketId) {
        return jdbcTemplate.queryForObject("select users_id from basket where id = ?",
                (rs, rowNum) -> rs.getLong("users_id"), basketId);
    }


    public int deleteItemFromBasket(long basketId, long productId) {
        return jdbcTemplate.update("delete from basket_item where basket_id=? and product_id=?", basketId, productId);
    }

    public List<BasketItemContainer> basketItemsWithQuantity(long basketId){
        return jdbcTemplate.query("select COUNT(product_id), product.id, code, name, address, publisher, price, status " +
                        "from product join basket_item on product.id = basket_item.product_id where basket_item.basket_id = ? GROUP by basket_item.product_id",
                (rs, rowNum) -> new BasketItemContainer(new Product(
                        rs.getLong("product.id"),
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("publisher"),
                        rs.getInt("price"),
                        rs.getString("status")),
                        rs.getInt("COUNT(product_id)")), basketId
        );
    }
}
