package training360.j5webshop.users;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


//    public long addUser(User user){
//
//    }

    public long getUserId(String userName){
        return jdbcTemplate.queryForObject("select id from users where username = ?",
                (rs, rowNum) -> rs.getLong("id"), userName);
    }
}
