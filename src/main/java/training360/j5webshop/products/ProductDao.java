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

    public List<Product> listProductsWithLimit(int start,int size) {
        System.out.println(start + " " + size);
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

    public void createProduct(String code, String name, String address, String publisher, int price){
        jdbcTemplate.update("insert into product(code,name,address,publisher,price) values(?,?,?,?,?)",
                code,name,address,publisher,price);
    }

    public Product findProductByAddress(String address) {
        return jdbcTemplate.queryForObject("select code, name, address, publisher, price from product where address = ?",
                (rs, rowNum) -> new Product(rs.getString("code"), rs.getString("name"),
                        rs.getString("address"), rs.getString("publisher"), rs.getInt("price")),
                address);
    }
}
