package training360.j5webshop.users;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class UserDaoTest {

    @Autowired
    UserDao userDao;

    @Test
    public void testAddAndListUser() {
        // When
        userDao.addUser(new User("Anna", "Arany", "aranyanna", "Annarany55"));
        List<User> list = userDao.listUsers();
        // Then
        assertThat(list.size(), equalTo(5));
        assertThat(list.get(0), equalTo(new User("Adrienn", "Tóth", "tadri1988", "Tadri1988")));
        assertThat(list.get(1), equalTo(new User("Anna", "Arany", "aranyanna", "Annarany55")));
        assertThat(list.get(2), equalTo(new User("Béla", "Kiss", "kissbeci", "kissbeci00")));
    }

    @Test
    public void testGetUserId() {
        // When
        long id = userDao.addUser(new User("Anna", "Arany", "aranyanna", "Annarany55"));
        // Then
        assertThat(userDao.getUserId("aranyanna"), equalTo(id));
    }

    @Test
    public void testDeleteUserById() {
        // When
        long id = userDao.addUser(new User("Anna", "Arany", "aranyanna", "Annarany55"));
        List<User> listBeforeDelete = userDao.listUsers();
        userDao.deleteUserById(id);
        List<User> listAfterDelete = userDao.listUsers();
        // Then
        assertThat(listBeforeDelete.size(), equalTo(5));
        assertThat(listBeforeDelete.get(0).getUserName(), equalTo("tadri1988"));
        assertThat(listBeforeDelete.get(1).getUserName(), equalTo("aranyanna"));
        assertThat(listBeforeDelete.get(2).getUserName(), equalTo("kissbeci"));
        assertThat(listAfterDelete.size(), equalTo(4));
        assertThat(listAfterDelete.get(0).getUserName(), equalTo("tadri1988"));
        assertThat(listAfterDelete.get(1).getUserName(), equalTo("kissbeci"));
    }

    @Test
    public void testUpdateAndFindUserById() {
        // When
        long id = userDao.addUser(new User("Anna", "Arany", "aranyanna", "Annarany55"));
        userDao.updateUser(id, new User("Amália", "Arany", "aranyanna", "Annarany55"));
        // Then
        assertThat(userDao.findUserById(id), equalTo(new User("Amália", "Arany", "aranyanna", "Annarany55")));
    }
}
