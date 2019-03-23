package training360.j5webshop.orders;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import training360.j5webshop.products.Product;

import java.sql.*;
import java.util.List;

@Repository
public class OrderDao {

    private JdbcTemplate jdbcTemplate;

    public OrderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long createOrderedProduct(Order order){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        for(OrderedProduct op:order.getOrderedProduct()) {
            jdbcTemplate.update(new PreparedStatementCreator() {
                                    @Override
                                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                                        PreparedStatement ps = connection.prepareStatement
                                                ("insert into order_item (orders_id, product_id, price) values(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                                        ps.setLong(1, createOrder(order));

                                        ps.setLong(2, op.getProduct().getId());
                                        ps.setInt(3, op.getPriceAtPurchase());

                                        return ps;
                                    }
                                }, keyHolder
            );
        }
        return keyHolder.getKey().longValue();
    }

    public long createOrder(Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
                                @Override
                                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                                    PreparedStatement ps = connection.prepareStatement
                                            ("insert into orders (user_id, status, purchase_date) values(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                                    ps.setLong(1, order.getUserId());
                                    ps.setString(2, order.getOrderStatus().name());
                                    ps.setDate(3, Date.valueOf(order.getPurchaseDate()));
                                    return ps;
                                }
                            }, keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    public List<Order> listAllOrder() {
        List<Order> allOrder = jdbcTemplate.query("select id, purchase_date, status from orders order by purchase_date DESC",
                (rs, rowNum) -> new Order(rs.getLong("id"), rs.getDate("purchase_date").toLocalDate(),
                OrderStatus.valueOf(rs.getString("status"))));
        return allOrder;
    }
    public List<OrderedProduct> findOrderedProductByOrderId(long id){ // az id a kapcsolódó order id-ja lesz

        List<OrderedProduct> foundByOrderId = jdbcTemplate.query
                ("SELECT COUNT(product_id), product.name, order_item.price from product join order_item on product.id = order_item.product_id WHERE order_item.orders_id = ? GROUP BY product_id",
                        (rs, rowNum) -> new OrderedProduct(new Product("product.name"), rs.getInt("COUNT(product_id)"), rs.getInt("order_item.price"))
                        ,id);
        return foundByOrderId;
    }

}
