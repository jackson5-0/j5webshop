package training360.j5webshop.products;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductDao {
    private JdbcTemplate jdbcTemplate;

    public ProductDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Product findProductByAddress(String address) {
        return jdbcTemplate.queryForObject("select code, name, address, publisher, price from j5webshop where address = ?",
                (rs, rowNum) -> new Product(rs.getString("code"), rs.getString("name"),
                        rs.getString("address"), rs.getString("publisher"), rs.getInt("price")),
                address);
    }

    public List<Product> listProductsWithLimit(int start,int size) {
        return jdbcTemplate.query("select code, name, address, publisher, price from j5webshop where rownum > ? limit ?", new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Product(resultSet.getString("code"), resultSet.getString("name"),
                        resultSet.getString("address"), resultSet.getString("publisher"), resultSet.getInt("price"));
            }
        }, start, size);
    }

    public List<Product> listAllProducts() {
        return jdbcTemplate.query("select code, name, address, publisher, price from j5webshop", new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Product(resultSet.getString("code"), resultSet.getString("name"),
                        resultSet.getString("address"), resultSet.getString("publisher"), resultSet.getInt("price"));
            }
        });
    }
}
