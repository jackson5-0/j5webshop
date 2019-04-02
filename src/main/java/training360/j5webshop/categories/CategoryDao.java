package training360.j5webshop.categories;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryDao {

    private JdbcTemplate jdbcTemplate;

    public CategoryDao (DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Category> listCategories() {
        return jdbcTemplate.query("select id, name, priority from category order by priority",
                (rs, rowNum) -> new Category(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("priority"),
                        new ArrayList<>()
                ));
    }

    public long createCategory(Category category) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
                                @Override
                                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                                    PreparedStatement ps = connection.prepareStatement(
                                            "insert into category (name, priority) values (?, ?)",
                                            Statement.RETURN_GENERATED_KEYS);
                                    ps.setString(1, category.getName());
                                    ps.setLong(2, category.getPriority());
                                    return ps;
                                }
                            }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void updateCategories(List<Category> categories) {
        String query = "update category " +
                        "set name = ?, " +
                            "priority = ? " +
                        "where id = ?";
        jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Category category = categories.get(i);
                ps.setString(1, category.getName());
                ps.setInt(2, category.getPriority());
                ps.setLong(3, category.getId());
            }
            @Override
            public int getBatchSize() {
                return categories.size();
            }
        });
    }

    public Integer isCategoryNameTaken(Category category) {
        return jdbcTemplate.queryForObject("select count(id) from category where name = ?", Integer.class, category.getName());
    }

    public void deleteCategory(Category category) {
        jdbcTemplate.update("delete from category where id = ?", category.getId());
    }
    public void updateCategoryNameById(Category category){
        jdbcTemplate.update("update category set name = ? where id=?",category.getName(),category.getId());
    }

}
