package training360.j5webshop.reviews;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

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

    public Long checkIfUserHasDeliveredProduct(String userName, long productId) {
        return jdbcTemplate.queryForObject("select count(order_item.id) from order_item join orders on orders.id=order_item.orders_id" +
                " join users on users.id=orders.user_id where users.username = ? and product_id = ? and orders.status = 'DELIVERED'", new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getLong("count(order_item.id)");
            }
        }, userName, productId);
    }
}
