package training360.j5webshop.users;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import training360.j5webshop.validation.ResponseStatus;
import training360.j5webshop.validation.ValidationStatus;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/user_init.sql")
public class UserIntegrationTest {
    @Autowired
    UserController userController;

    @Test
    public void testListUsers() {
        // When
        List<User> userList = userController.listUsers();
        // Then
        assertThat(userList.size(), equalTo(4));
        assertThat(userList.get(0), equalTo(new User("Adrienn", "Tóth", "tadri1988", "Tadri1988")));
        assertThat(userList.get(1), equalTo(new User("Béla", "Kiss", "kissbeci", "kissbeci00")));
    }

    @Test
    public void testAddUserWhenResponseOk() {
        // Given
        User user = new User("Anna", "Arany", "aranyanna", "Annarany55");
        // When
        ResponseStatus status = userController.addUser(user);
        // Then
        assertThat(status.getStatus(), equalTo(ValidationStatus.SUCCESS));
        assertThat(status.getMessages().size(), equalTo(1));
        assertThat(status.getMessages().get(0), equalTo("A regisztráció sikeres volt, bejelentkezhet oldalunkra!"));
    }

    @Test
    public void testAddUserWhenUsernameIsTaken() {
        // Given
        User user = new User("Anna", "Arany", "tadri1988", "Annarany55");
        // When
        ResponseStatus status = userController.addUser(user);
        // Then
        assertThat(status.getStatus(), equalTo(ValidationStatus.FAIL));
        assertThat(status.getMessages().size(), equalTo(1));
        assertThat(status.getMessages().get(0), equalTo("A kért felhasználónév foglalt!"));
    }

    @Test
    public void testAddUserWhenUsernameIsNotProper() {
        // Given
        User user = new User("Anna", "Arany", "A: :A", "Annarany55");
        // When
        ResponseStatus status = userController.addUser(user);
        // Then
        assertThat(status.getStatus(), equalTo(ValidationStatus.FAIL));
        assertThat(status.getMessages().size(), equalTo(1));
        assertThat(status.getMessages().get(0), equalTo("A felhasználói név legalább 3 karakter hosszú legyen, csak számot, kis- és nagybetűt, pontot, alulvonást és kötőjelet tartalmazhat!"));
    }

    @Test
    public void testAddUserWhenPasswordIsNotProper() {
        // Given
        User user = new User("Anna", "Arany", "aranyanna", "Anna55");
        // When
        ResponseStatus status = userController.addUser(user);
        // Then
        assertThat(status.getStatus(), equalTo(ValidationStatus.FAIL));
        assertThat(status.getMessages().size(), equalTo(1));
        assertThat(status.getMessages().get(0), equalTo("A jelszó minimum 8 karakter hosszú legyen, tartalmazzon kis- és nagybetűt, illetve számot!"));
    }

    @Test
    public void testDeleteUserByIdAndGetUserId() {
        // Given
        User user = new User("Anna", "Arany", "aranyanna", "Annacska55");
        userController.addUser(user);
        // When
        ResponseStatus status = userController.deleteUserById(userController.getUserId("aranyanna"));
        // Then
        assertThat(status.getStatus(), equalTo(ValidationStatus.SUCCESS));
        assertThat(status.getMessages().size(), equalTo(1));
        assertThat(status.getMessages().get(0), equalTo("A felhasználó törlése sikeres volt!"));
        assertThat(userController.listUsers().size(), equalTo(4));
        assertThat(userController.listUsers().get(0), equalTo(new User("Adrienn", "Tóth", "tadri1988", "Tadri1988")));
        assertThat(userController.listUsers().get(1), equalTo(new User("Béla", "Kiss", "kissbeci", "kissbeci00")));
        assertThat(userController.listUsers().get(2), equalTo(new User("Géza", "Kovács", "kovacsgeza", "KovacsGeza90")));
        assertThat(userController.listUsers().get(3), equalTo(new User("Gizella", "Nagy", "nagygizi22", "GiziAZizi11")));
    }

    @Test
    public void testUpdateUserAndFindUserById() {
        // Given
        userController.addUser(new User("Anna", "Arany", "aranyanna", "Annacska55"));
        long id = userController.getUserId("aranyanna");
        // When
        ResponseStatus status = userController.updateUser(id, new User(id,"Anna", "Arany-Kiss", "aranyanna", "Annacska55"));
        // Then
        assertThat(status.getStatus(), equalTo(ValidationStatus.SUCCESS));
        assertThat(status.getMessages().size(), equalTo(1));
        assertThat(status.getMessages().get(0), equalTo("Sikeres módosítás!"));
        assertThat(userController.listUsers().size(), equalTo(5));
        assertThat(userController.findUserById(id), equalTo(new User("Anna", "Arany-Kiss", "aranyanna", "Annacska55")));
    }

    @Test
    public void testUpdateUserWhenUsernameIsAlreadyTaken() {
        // Given
        userController.addUser(new User("Anna", "Arany", "aranyanna", "Annacska55"));
        long id = userController.getUserId("aranyanna");
        // When
        ResponseStatus status = userController.updateUser(id, new User(id,"Anna", "Arany", "nagygizi22", "Annacska55"));
        // Then
        assertThat(status.getStatus(), equalTo(ValidationStatus.FAIL));
        assertThat(status.getMessages().size(), equalTo(1));
        assertThat(status.getMessages().get(0), equalTo("A kért felhasználónév foglalt!"));
    }



}
