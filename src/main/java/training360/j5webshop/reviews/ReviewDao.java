package training360.j5webshop.reviews;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import training360.j5webshop.products.Product;
import training360.j5webshop.users.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Repository
public class ReviewDao {

    private JdbcTemplate jdbcTemplate;

    public ReviewDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public long createReview(Review review) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                        "insert into review(product_id, users_id, review_date, message, rating) values(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS
                );
                ps.setLong(1, review.getProduct().getId());
                ps.setLong(2, review.getUser().getId());
                ps.setString(3, LocalDateTime.now().format(DATE_FORMATTER));
                ps.setString(4, review.getMessage());
                ps.setInt(5, review.getRating());
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void deleteReview(Review review) {
        jdbcTemplate.update(    "DELETE review.* " +
                                    "from review " +
                                    "join users on users.id=review.users_id " +
                                    "where users.username = ? and review.product_id = ?",
                                    review.getUser().getUserName(), review.getProduct().getId());
    }

    public void updateReview(Review review) {
        jdbcTemplate.update("update review join users on users.id=review.users_id " +
                                "set review.review_date= ?, review.message= ?, review.rating= ? " +
                                "where users.username = ? and review.product_id = ?",
                LocalDateTime.now().format(DATE_FORMATTER), review.getMessage(), review.getRating(),
                review.getUser().getUserName(), review.getProduct().getId());
    }

    public boolean checkIfUserHasDeliveredProduct(String userName, long productId) {
        boolean ret = false;
        try {
            ret = 0 < (jdbcTemplate.queryForObject("select count(order_item.id) from order_item join orders on orders.id=order_item.orders_id" +
                    " join users on users.id=orders.user_id where users.username = ? and product_id = ? and orders.status = 'DELIVERED'", new RowMapper<Long>() {
                @Override
                public Long mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getLong("count(order_item.id)");
                }
            }, userName, productId));
        } catch (NullPointerException npe) {
            return true;
        }
        return ret;
    }

    public ReviewInfo checkIfUserHasDeliveredProductAndHasReview(String userName, long productId) {
        try {
            return jdbcTemplate.queryForObject("select message, rating from review join users on " +
                    "review.users_id=users.id where users.username = ? and review.product_id = ?", new RowMapper<ReviewInfo>() {
                @Override
                public ReviewInfo mapRow(ResultSet resultSet, int i) throws SQLException {
                    return new ReviewInfo(checkIfUserHasDeliveredProduct(userName, productId),
                            resultSet.getString("message"), resultSet.getInt("rating"));
                }
            }, userName, productId);
        } catch (EmptyResultDataAccessException erdae) {
            return new ReviewInfo(checkIfUserHasDeliveredProduct(userName, productId), null, 0);
        }
    }
}
