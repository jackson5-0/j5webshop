package training360.j5webshop.reports;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import training360.j5webshop.orders.OrderStatus;

import javax.sql.DataSource;
import java.time.Month;
import java.util.List;

@Repository
public class ReportDao {
    private JdbcTemplate jdbcTemplate;

    public ReportDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Integer sizeOfAllOrders(){
        return jdbcTemplate.queryForObject("Select count(id) from orders", (resultSet, i) -> resultSet.getInt("count(id)"));
    }

    public Integer sizeOfActiveOrders(){
        return jdbcTemplate.queryForObject("Select count(id) from orders where status='ACTIVE'", (resultSet, i) -> resultSet.getInt("count(id)"));
    }

    public List<ReportOfOrders> listOrdersByMonthAndByStatus() {
        return jdbcTemplate.query("SELECT YEAR(orders.purchase_date) as year, MONTH(orders.purchase_date) as month, " +
                        "orders.status as status, COUNT(orders.id) as num_of_orders, SUM(order_item.price) as sum " +
                        "FROM order_item JOIN orders ON order_item.orders_id = orders.id GROUP BY YEAR(orders.purchase_date), " +
                        "MONTH(orders.purchase_date), orders.status order by orders.purchase_date",
                (rs, rowNum) -> new ReportOfOrders(
                        rs.getInt("year"),
                        Month.of(rs.getInt("month")),
                        OrderStatus.valueOf(rs.getString("status")),
                        rs.getInt("num_of_orders"),
                        rs.getInt("sum")));
    }

    public List<ReportOfProductSale> listDeliveredProductsByMonth() {
        return jdbcTemplate.query("SELECT YEAR(orders.purchase_date) as year, MONTH(orders.purchase_date) as month, product.code, product.name, " +
                        "order_item.price, COUNT(order_item.id) as count, SUM(order_item.price) as sum FROM order_item " +
                        "JOIN orders ON orders.id = order_item.orders_id JOIN product ON order_item.product_id = product.id " +
                        "WHERE orders.status = 'DELIVERED' GROUP BY product.id, order_item.price order by orders.purchase_date",
                (rs, rowNum) -> new ReportOfProductSale(
                        rs.getInt("year"),
                        Month.of(rs.getInt("month")),
                        rs.getString("product.code"),
                        rs.getString("product.name"),
                        rs.getInt("order_item.price"),
                        rs.getInt("count"),
                        rs.getInt("sum")));
    }
}
