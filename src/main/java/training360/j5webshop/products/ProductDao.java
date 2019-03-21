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

    public List<Product> listProductsWithLimit(int start, int size) {
        return jdbcTemplate.query("select id, code, name, address, publisher, price, status from product where status != 'DELETED' order by name, publisher limit ?,?", new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Product(resultSet.getLong("id"), resultSet.getString("code"), resultSet.getString("name"),
                        resultSet.getString("address"), resultSet.getString("publisher"),
                        resultSet.getInt("price"), resultSet.getString("status"));
            }
        }, start, size);
    }

    public List<Product> listAllProducts() {
        return jdbcTemplate.query("select id, code, name, address, publisher, price, status from product order by name, publisher", new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Product(resultSet.getLong("id"), resultSet.getString("code"), resultSet.getString("name"),
                        resultSet.getString("address"), resultSet.getString("publisher"),
                        resultSet.getInt("price"), resultSet.getString("status"));
            }
        });
    }

    public int getLengthOfProductList() {
        return jdbcTemplate.queryForObject("select count(id) from product where status != 'DELETED'", new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("count(id)");
            }
        });
    }

    //    public void createProduct(Product product){
//        jdbcTemplate.update("insert into product (code, name, address, publisher, price) values(?, ?, ?, ?, ?)",
//                product.getCode(), product.getName(), product.getAddress(), product.getPublisher(), product.getPrice());
    public long createProduct(Product product) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
                                @Override
                                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                                    PreparedStatement ps = connection.prepareStatement
                                            ("insert into product (code, name, address, publisher, price, status) values(?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                                    ps.setString(1, product.getCode());
                                    ps.setString(2, product.getName());
                                    ps.setString(3, product.getAddress());
                                    ps.setString(4, product.getPublisher());
                                    ps.setInt(5, product.getPrice());
                                    ps.setString(6, product.getStatus().name());
                                    return ps;
                                }
                            }, keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    public Product findProductByAddress(String address) {
        return jdbcTemplate.queryForObject("select id, code, name, address, publisher, price, status from product where address = ?",
                (rs, rowNum) -> new Product(rs.getLong("id"), rs.getString("code"), rs.getString("name"),
                        rs.getString("address"), rs.getString("publisher"), rs.getInt("price"), rs.getString("status")),
                address);
    }

    public Product findProductById(long id) {
        return jdbcTemplate.queryForObject("select id, code, name, address, publisher, price, status from product where id = ?",
                (rs, rowNum) -> new Product(rs.getLong("id"), rs.getString("code"), rs.getString("name"),
                        rs.getString("address"), rs.getString("publisher"), rs.getInt("price"), rs.getString("status")), id);
    }

    public void updateProduct(long id, Product product) {
        String code = product.getCode()== null ? findProductById(id).getCode() : product.getCode();
        String name = product.getName() == null ? findProductById(id).getName() : product.getName();
        String address = product.getAddress() == null? findProductById(id).getAddress() : product.getAddress();
        String publisher = product.getPublisher() == null ? findProductById(id).getPublisher() : product.getPublisher();
        int price = product.getPrice() == 0 ? findProductById(id).getPrice() : product.getPrice();
        String status = product.getStatus() == null ? findProductById(id).getStatus().name() : product.getStatus().name();

        jdbcTemplate.update("update product set code = ?, name = ?, address = ?, publisher=?, price=? , status = ? where id = ?",
               code, name, address, publisher, price, status, id);
    }

    public void deleteProductById(long id) {
        jdbcTemplate.update("update product set status = 'DELETED' where id=?", id);
    }
}
