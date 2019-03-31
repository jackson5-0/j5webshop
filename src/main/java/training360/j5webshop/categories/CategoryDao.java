package training360.j5webshop.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import training360.j5webshop.products.Category;

import javax.sql.DataSource;
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


}
