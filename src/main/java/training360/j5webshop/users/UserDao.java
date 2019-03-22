package training360.j5webshop.users;

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

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public long addUser(User user){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
                                @Override
                                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                                    PreparedStatement ps = connection.prepareStatement
                                            ("insert into users (firstname, lastname, username, password, enabled, role) values(?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                                    ps.setString(1, user.getFirstName());
                                    ps.setString(2, user.getLastName());
                                    ps.setString(3, user.getUserName());
                                    ps.setString(4, user.getPassword());
                                    ps.setInt(5, 1);
                                    ps.setString(6, user.getRole().name());

                                    return ps;
                                }
                            }, keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    public long getUserId(String userName){
        return jdbcTemplate.queryForObject("select id from users where username = ?",
                (rs, rowNum) -> rs.getLong("id"), userName);
    }
}