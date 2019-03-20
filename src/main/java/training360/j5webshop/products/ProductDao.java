package training360.j5webshop.products;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
@Repository
public class ProductDao {
    private JdbcTemplate jdbcTemplate;

    public ProductDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Product> listProductsWithLimit(int start,int size) {
        return jdbcTemplate.query("select code, name, address, publisher, price from product limit ?,?", new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Product(resultSet.getString("code"), resultSet.getString("name"),
                        resultSet.getString("address"), resultSet.getString("publisher"), resultSet.getInt("price"));
            }
        }, start, size);
    }

    public List<Product> listAllProducts() {
        return jdbcTemplate.query("select code, name, address, publisher, price from product", new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Product(resultSet.getString("code"), resultSet.getString("name"),
                        resultSet.getString("address"), resultSet.getString("publisher"), resultSet.getInt("price"));
            }
        });
    }

    public int getLengthOfProductList() {
        return jdbcTemplate.queryForObject("select count(id) from product", new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("count(id)");
            }
        });
    }

//    public void createProduct(Product product){
//        jdbcTemplate.update("insert into product (code, name, address, publisher, price) values(?, ?, ?, ?, ?)",
//                product.getCode(), product.getName(), product.getAddress(), product.getPublisher(), product.getPrice());
        public long createProduct(Product product){
        KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement
                            ("insert into product (code, name, address, publisher, price) values(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, product.getCode());
                    ps.setString(2, product.getName());
                    ps.setString(3, product.getAddress());
                    ps.setString(4, product.getPublisher());
                    ps.setInt(5, product.getPrice());
                    return ps;
                    }
                }, keyHolder
            );
            return keyHolder.getKey().longValue();
        }

    public Product findProductByAddress(String address) {
        return jdbcTemplate.queryForObject("select code, name, address, publisher, price from product where address = ?",
                (rs, rowNum) -> new Product(rs.getString("code"), rs.getString("name"),
                        rs.getString("address"), rs.getString("publisher"), rs.getInt("price")),
                address);
    }
}
