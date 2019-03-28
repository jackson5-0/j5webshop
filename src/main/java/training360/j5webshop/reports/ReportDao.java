package training360.j5webshop.reports;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import training360.j5webshop.orders.Order;
import training360.j5webshop.orders.OrderInfo;
import training360.j5webshop.orders.OrderStatus;
import training360.j5webshop.products.Product;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class ReportDao {
    private JdbcTemplate jdbcTemplate;
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ReportDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<ReportOfOrders> listOrdersByMonthAndByStatus() {
        return jdbcTemplate.query("SELECT MONTH(orders.purchase_date) as month, orders.status as status, COUNT(order_item.id) as num_of_orders, SUM(order_item.price) as sum FROM order_item JOIN orders ON order_item.orders_id = orders.id GROUP BY MONTH(orders.purchase_date), orders.status",
                (rs, rowNum) -> new ReportOfOrders(
                        Month.of(rs.getInt("month")),
                        OrderStatus.valueOf(rs.getString("status")),
                        rs.getInt("num_of_orders"),
                        rs.getInt("sum")));
    }

//    public List<ReportOfProductSale> listDeliveredProductsByMonth() {
//        return
//    }

}
