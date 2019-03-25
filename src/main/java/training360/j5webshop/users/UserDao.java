package training360.j5webshop.users;

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

    public List<User> listUsers() {
        return jdbcTemplate.query("select id, firstname, lastname, username, password from users where enabled != 0 order by firstname, lastname", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                return new User(resultSet.getLong("id"), resultSet.getString("lastname"), resultSet.getString("firstname"),
                        resultSet.getString("username"), resultSet.getString("password"));
            }
        });
    }

    public void deleteUserById(long id) {
        String deletedUserName = "Törölt" + id;
        jdbcTemplate.update("update users set firstname = 'Törölt', lastname = 'Törölt', username = ?, password = 'Törölt', enabled = 0 where id = ?", deletedUserName, id);
    }

    public void updateUser(long id, User user) {
        String firstName = user.getFirstName()== null ? findUserById(id).getFirstName() : user.getFirstName();
        String lastName = user.getLastName() == null ? findUserById(id).getLastName() : user.getLastName();
        String userName = user.getUserName() == null? findUserById(id).getUserName() : user.getUserName();
        String password = user.getPassword() == null ? findUserById(id).getPassword() : user.getPassword();

        jdbcTemplate.update("update users set firstname = ?, lastname = ?, username = ?, password = ? where id = ?",
               firstName, lastName, userName, password, id);
    }

    public User findUserById(long id){
        return jdbcTemplate.queryForObject("select firstname, lastname, username, password from users where id = ?",
                (rs, rowNum) -> new User(rs.getString("firstname"), rs.getString("lastname"), rs.getString("username"),
                        rs.getString("password")), id);
    }
}