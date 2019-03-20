package training360.j5webshop.products;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class ProductDao {
    private JdbcTemplate jdbcTemplate;

    public ProductDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Product findProductById(long id) {
        return jdbcTemplate.queryForObject("select code, name, address, publisher, price from j5webshop where id = ?",
                (rs, rowNum) -> new Product(rs.getString("code"), rs.getString("name"), rs.getString("address"),
                        rs.getString("publisher"), rs.getInt("price")),
                id);
    }
}
