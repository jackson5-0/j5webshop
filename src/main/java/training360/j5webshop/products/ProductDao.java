package training360.j5webshop.products;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDao {
    private JdbcTemplate jdbcTemplate;

    public ProductDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Product> listProductsWithLimit(int start, int size, String category) {
        return jdbcTemplate.query(
                "SELECT product.id, code, product.name, address, publisher, price, status FROM product \n" +
                "join product_category on product.id = product_category.product_id\n" +
                "join category on product_category.category_id = category.id\n" +
                "where category.name = ?\n" +
                "and status != 'DELETED' order by name, publisher limit ?, ?", new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Product(resultSet.getLong("id"), resultSet.getString("code"), resultSet.getString("name"),
                        resultSet.getString("address"), resultSet.getString("publisher"),
                        resultSet.getInt("price"), resultSet.getString("status"));
            }
        }, category, start, size);
    }

    public List<Product> listProductsWithLimitAdmin(int start, int size) {
        return jdbcTemplate.query("select id, code, name, address, publisher, price, status from product where status != 'DELETED' order by name, publisher limit ?,?", new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Product(resultSet.getLong("id"), resultSet.getString("code"), resultSet.getString("name"),
                        resultSet.getString("address"), resultSet.getString("publisher"),
                        resultSet.getInt("price"), resultSet.getString("status"));
            }
        }, start, size);
    }

    public List<Category> listCategories() {
        return jdbcTemplate.query("select id, name, priority from category",
                (rs, rowNum) -> new Category(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("priority"),
                        new ArrayList<>()
                ));
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

    public Integer getLengthOfProductList() {
        return jdbcTemplate.queryForObject("select count(id) from product where status != 'DELETED'", new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("count(id)");
            }
        });
    }

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

    public ProductContainer findProductByAddress(String address) {
        try {
            Product product = jdbcTemplate.queryForObject("select id, code, name, address, publisher, price, status from product where address = ?",
                (rs, rowNum) -> new Product(rs.getLong("id"), rs.getString("code"), rs.getString("name"),
                        rs.getString("address"), rs.getString("publisher"), rs.getInt("price"), rs.getString("status")),
                address);
            return new ProductContainer(product);
        } catch (EmptyResultDataAccessException ere) {
            return new ProductContainer("A keresett termék nem található!");
        }
    }

    public Product findProductById(long id) {
        return jdbcTemplate.queryForObject("select id, code, name, address, publisher, price, status from product where id = ?",
                (rs, rowNum) -> new Product(rs.getLong("id"), rs.getString("code"), rs.getString("name"),
                        rs.getString("address"), rs.getString("publisher"), rs.getInt("price"), rs.getString("status")), id);
    }

    public void updateProduct(Product product) {
        String code = product.getCode()== null ? findProductById(product.getId()).getCode() : product.getCode();
        String name = product.getName() == null ? findProductById(product.getId()).getName() : product.getName();
        String address = product.getAddress() == null? findProductById(product.getId()).getAddress() : product.getAddress();
        String publisher = product.getPublisher() == null ? findProductById(product.getId()).getPublisher() : product.getPublisher();
        int price = product.getPrice() == 0 ? findProductById(product.getId()).getPrice() : product.getPrice();
        String status = product.getStatus() == null ? findProductById(product.getId()).getStatus().name() : product.getStatus().name();

        jdbcTemplate.update("update product set code = ?, name = ?, address = ?, publisher = ?, price = ? , status = ? where id = ?",
               code, name, address, publisher, price, status, product.getId());
    }

    public void deleteProductById(long id) {
        jdbcTemplate.update("update product set status = 'DELETED' where id = ?", id);
    }

    public void createProductCategoryEntry(long productId, long categoryId) {
        jdbcTemplate.update("insert into product_category (product_id, category_id) values (?, ?)", productId, categoryId);
    }

}
