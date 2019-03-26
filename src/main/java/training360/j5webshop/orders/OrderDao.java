package training360.j5webshop.orders;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import training360.j5webshop.baskets.Basket;
import training360.j5webshop.products.Product;
import training360.j5webshop.products.ProductDao;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Repository
public class OrderDao {


    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ProductDao productDao;

    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public OrderDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addOrderdProduct(Long orderId, Basket basket) {
        for (Product p : basket.getProducts().keySet()) {
            jdbcTemplate.update("insert into order_item (orders_id, product_id, price) values(?, ?, ?)", orderId, p.getId(), p.getPrice());
        }
    }

//    public long createOrderedProduct(Basket basket){
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        System.out.println(basket.getProducts().keySet());
//        for(Product p :basket.getProducts().keySet()) {
//            jdbcTemplate.update(new PreparedStatementCreator() {
//                                    @Override
//                                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//                                        PreparedStatement ps = connection.prepareStatement
//                                                ("insert into order_item (orders_id, product_id, price) values(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
//                                        ps.setLong(1, createOrder(basket));
//
//                                        ps.setLong(2, p.getId());
//                                        ps.setInt(3, p.getPrice());
//                                        return ps;
//                                    }
//                                }, keyHolder
//            );
//        }
//        return keyHolder.getKey().longValue();
//    }

//    public long createOrder(Basket basket) {
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        jdbcTemplate.update(new PreparedStatementCreator() {
//                                @Override
//                                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//                                    PreparedStatement ps = connection.prepareStatement
//                                            ("insert into orders (user_id, basket_id, status, purchase_date) values(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
//                                    ps.setLong(1, basket.getUserId());
//                                    ps.setLong(2, basket.getId());
//                                    ps.setString(3, OrderStatus.ACTIVE.toString());
//                                    ps.setDate(4, Date.valueOf(LocalDate.now()));
//                                    return ps;
//                                }
//                            }, keyHolder
//        );
//        return keyHolder.getKey().longValue();
//    }

    public long createOrder(long userId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
                                @Override
                                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                                    PreparedStatement ps = connection.prepareStatement
                                            ("insert into orders (user_id, status, purchase_date) values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                                    ps.setLong(1, userId);
                                    ps.setString(2, OrderStatus.ACTIVE.toString());
                                    ps.setString(3, LocalDateTime.now().format(DATE_FORMATTER));
                                    return ps;
                                }
                            }, keyHolder
        );
        return keyHolder.getKey().longValue();
    }



    public List<Order> listAllOrder() {
        return jdbcTemplate.query("SELECT id, user_id, purchase_date, status FROM `orders` where status !='deleted' order by purchase_date DESC",
                (rs, rowNum) -> new Order(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
//                        LocalDateTime.parse(rs.getString("purchase_date"), DATE_FORMATTER) ,
                        LocalDate.parse(rs.getString("purchase_date"), DATE_FORMATTER) ,
                        findOrderedProductByOrderId(rs.getLong("id")),
                OrderStatus.valueOf(rs.getString("status"))));
    }

    public List<Order> listActiveOrder() {
        return jdbcTemplate.query("SELECT id, user_id, purchase_date, status FROM `orders` where status ='active' order by purchase_date DESC",
                (rs, rowNum) -> new Order(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
//                        LocalDateTime.parse(rs.getString("purchase_date"), DATE_FORMATTER) ,
                        LocalDate.parse(rs.getString("purchase_date"), DATE_FORMATTER) ,
                        findOrderedProductByOrderId(rs.getLong("id")),
                        OrderStatus.valueOf(rs.getString("status"))));
    }

    public List<Order> listAllOrderWithDeleted() {
        return jdbcTemplate.query("SELECT id, user_id, purchase_date, status FROM `orders` order by purchase_date DESC",
                (rs, rowNum) -> new Order(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
//                        LocalDateTime.parse(rs.getString("purchase_date"), DATE_FORMATTER) ,
                        LocalDate.parse(rs.getString("purchase_date"), DATE_FORMATTER) ,
                        findOrderedProductByOrderId(rs.getLong("id")),
                        OrderStatus.valueOf(rs.getString("status"))));
    }

    public List<OrderInfo> listAdminOrders(){
        return jdbcTemplate.query("SELECT orders.id, users.username, purchase_date, status FROM `orders` JOIN `users` on orders.user_id=users.id order by purchase_date DESC",
                (rs, rowNum) -> new OrderInfo(
                        rs.getLong("id"),
                        rs.getString("username"),
                        LocalDateTime.parse(rs.getString("purchase_date"), DATE_FORMATTER),
                        OrderStatus.valueOf(rs.getString("status")),
                        totalPrice(rs.getLong("id"))));
    }
    public List<OrderInfo> listActiveAdminOrders(){
        return jdbcTemplate.query("SELECT orders.id, users.username, purchase_date, status FROM `orders` JOIN `users` on orders.user_id=users.id where status ='active' order by purchase_date DESC",
                (rs, rowNum) -> new OrderInfo(
                        rs.getLong("id"),
                        rs.getString("username"),
                        LocalDateTime.parse(rs.getString("purchase_date"), DATE_FORMATTER),
                        OrderStatus.valueOf(rs.getString("status")),
                        totalPrice(rs.getLong("id"))));
    }


    public void deleteItem(long orderId, String productAddress){
        long productId = productDao.findProductByAddress(productAddress).getProduct().getId();
        jdbcTemplate.update("delete from order_item where orders_id=? and product_id=?", orderId, productId);
    }
    public void deleteWholeOrder(long orderId){
        jdbcTemplate.update("update orders set status='DELETED' where id=?", orderId);
        jdbcTemplate.update("delete from order_item where orders_id=?",orderId);
    }

    public int totalPrice(long id){
        int sum = 0;
        for (OrderedProduct op : findOrderedProductByOrderId(id)){
            sum+=op.getPriceAtPurchase();
        }
        return sum;
    }

    public List<OrderedProduct> findOrderedProductByOrderId(long id){ // az id a kapcsolódó order id-ja lesz
        return jdbcTemplate.query
                ("SELECT COUNT(product_id), product.name, product.publisher,product.address, order_item.price from product join order_item on product.id = order_item.product_id WHERE order_item.orders_id = ? GROUP BY product_id, price",
                        (rs, rowNum) -> new OrderedProduct(new Product(rs.getString("product.name"), rs.getString("product.address"),rs.getString("product.publisher"), rs.getInt("order_item.price")), rs.getInt("COUNT(product_id)") /*, rs.getInt("order_item.price")*/)
                        ,id);
    }

    public void changeStatusById(long orderId){
        jdbcTemplate.update("update orders set status='DELIVERED' where id=?", orderId);
    }
}
