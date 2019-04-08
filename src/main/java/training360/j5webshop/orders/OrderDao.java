package training360.j5webshop.orders;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import training360.j5webshop.baskets.Basket;
import training360.j5webshop.products.Product;
import training360.j5webshop.products.ProductDao;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class OrderDao {


    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProductDao productDao;

    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final RowMapper<Order> ORDER_ROW_MAPPER = (rs, rowNum) -> new Order(
            rs.getLong("id"),
            rs.getLong("user_id"),
            LocalDateTime.parse(rs.getString("purchase_date"), DATE_FORMATTER),
            findOrderedProductByOrderId(rs.getLong("id")),
            OrderStatus.valueOf(rs.getString("status")),
            rs.getString("shipping_address")
    );

    private final RowMapper<OrderInfo> ORDERINFO_ROW_MAPPER = (rs, rowNum) -> new OrderInfo(
            rs.getLong("id"),
            rs.getString("username"),
            LocalDateTime.parse(rs.getString("purchase_date"), DATE_FORMATTER),
            OrderStatus.valueOf(rs.getString("status")),
            totalPrice(rs.getLong("id")),
            rs.getString("shipping_address")
    );

    private final RowMapper<OrderedProduct> ORDEREDPRODUCT_ROW_MAPPER = (rs, rowNum) -> new OrderedProduct(
            new Product(
                    rs.getString("product.name"),
                    rs.getString("product.address"),
                    rs.getString("product.publisher"),
                    rs.getInt("order_item.price")),
            rs.getInt("COUNT(product_id)"),
            rs.getInt("order_item.price")
    );

    public OrderDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public long createOrder(long userId, String address) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement
                    ("insert into orders (user_id, status, purchase_date, shipping_address) values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    ps.setLong(1, userId);
                    ps.setString(2, OrderStatus.ACTIVE.toString());
                    ps.setString(3, LocalDateTime.now().format(DATE_FORMATTER));
                    ps.setString(4, address);
                    return ps;
            }, keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    public void addOrderedProduct(Long orderId, Basket basket) {
        for (Product p : basket.getProducts().keySet()) {
            for (int i = 0; i < basket.getProducts().get(p); i++) {
                jdbcTemplate.update("insert into order_item (orders_id, product_id, price) values(?, ?, ?)", orderId, p.getId(), p.getPrice());
            }
        }
    }

    //MYORDERS
    public List<Order> listAllOrder(String userName) {
        return jdbcTemplate.query("SELECT orders.id, orders.user_id, orders.purchase_date, orders.status, orders.shipping_address FROM `orders` JOIN users on orders.user_id = users.id where orders.status !='deleted' and users.username =? order by purchase_date DESC",
                ORDER_ROW_MAPPER, userName);
    }

    public List<Order> listActiveOrder(String userName) {
        return jdbcTemplate.query("SELECT orders.id, orders.user_id, orders.purchase_date, orders.status, orders.shipping_address FROM `orders` JOIN users on orders.user_id = users.id where orders.status ='active' and users.username =? order by purchase_date DESC",
                ORDER_ROW_MAPPER, userName);
    }

    public List<Order> listAllOrderWithDeleted(String userName) {
        return jdbcTemplate.query("SELECT orders.id, orders.user_id, orders.purchase_date, orders.status, orders.shipping_address FROM `orders` JOIN users on orders.user_id = users.id where users.username =? order by purchase_date DESC",
                ORDER_ROW_MAPPER, userName);
    }

    public List<Product> listLast3OrderedItem() {
        return jdbcTemplate.query("SELECT product.id, product.code, product.name, product.address, product.publisher, product.price, product.status, product.image FROM `product`" +
                        " JOIN order_item on product.id = order_item.product_id" +
                        " join orders on order_item.orders_id=orders.id" +
                        " join users on users.id=orders.user_id" +
                        " where orders.status !='deleted'" +
                        " group by order_item.product_id, order_item.orders_id"+
                        " order by order_item.id DESC" +
                        " LIMIT 3",
                (resultSet, i) -> new Product(resultSet.getLong("id"),resultSet.getString("code"),resultSet.getString("name"),resultSet.getString("address"), resultSet.getString("publisher"),resultSet.getInt("price"),resultSet.getString("status"), resultSet.getBytes("image")));
    }



    //ADMINORDERS
    public List<OrderInfo> listAdminOrders(){
        return jdbcTemplate.query("SELECT orders.id, users.username, purchase_date, status, orders.shipping_address FROM `orders` JOIN `users` on orders.user_id=users.id order by purchase_date DESC",
                ORDERINFO_ROW_MAPPER);
    }
    public List<OrderInfo> listActiveAdminOrders(){
        return jdbcTemplate.query("SELECT orders.id, users.username, purchase_date, status, orders.shipping_address FROM `orders` JOIN `users` on orders.user_id=users.id where status ='active' order by purchase_date DESC",
                ORDERINFO_ROW_MAPPER);
    }

    public void deleteItem(long orderId, String productAddress){
        long productId = productDao.findProductByAddress(productAddress).getProduct().getId();
        jdbcTemplate.update("delete from order_item where orders_id=? and product_id=?", orderId, productId);
    }
    public void deleteWholeOrder(long orderId){
        jdbcTemplate.update("update orders set status='DELETED' where id=?", orderId);
        jdbcTemplate.update("delete from order_item where orders_id=?",orderId);
    }

    public int totalPrice(long orderId){
        int sum = 0;
        for (OrderedProduct op : findOrderedProductByOrderId(orderId)){
            sum+=op.getPriceAtPurchase()*op.getQuantity();
        }
        return sum;
    }

    public List<OrderedProduct> findOrderedProductByOrderId(long orderId){ // az id a kapcsolódó order id-ja lesz
        return jdbcTemplate.query
                ("SELECT COUNT(product_id), product.name, product.publisher, product.address, order_item.price from product " +
                                "join order_item on product.id = order_item.product_id " +
                                "WHERE order_item.orders_id = ? GROUP BY product_id, price",
                        ORDEREDPRODUCT_ROW_MAPPER
                        ,orderId);
    }

    public void changeStatusById(long orderId){
        jdbcTemplate.update("update orders set status='DELIVERED' where id=?", orderId);
    }
}
