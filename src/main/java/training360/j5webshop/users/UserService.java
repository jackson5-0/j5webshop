package training360.j5webshop.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import training360.j5webshop.baskets.BasketDao;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private BasketDao basketDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Long findBasketId(String userName) {
        long userId = userDao.getUserId(userName);
        return basketDao.findBasketId(userId);
    }

    public long addUser(User user){
        String savedPassword = passwordEncoder.encode(user.getPassword());
        long id =userDao.addUser(new User(user.getFirstName(), user.getLastName(), user.getUserName(), savedPassword));
        basketDao.createBasket(id);
        return id;
    }

    public List<User> listUsers(){
        return userDao.listUsers();
    }

    public void deleteUserById(long id) {
        userDao.deleteUserById(id);
    }

    public void updateUserDatasByAdmin(long id, User user) {
        userDao.updateUser(id, user);
    }

    public User findUserById(long id) throws EmptyResultDataAccessException {
        return userDao.findUserById(id);
    }

    public long getUserId(String userName) throws EmptyResultDataAccessException {
        return userDao.getUserId(userName);
    }

    public List<Long> listUserIds() {
        return userDao.listUserIds();
    }

    public User findUserByUserName(String userName){
        User user = userDao.findUserByUserName(userName);
        return user;
    }

    public boolean updateUserDatasByUser(long id, User user){
        if (givenPasswordIsCorrect(id, user)) {
            userDao.updateUser(id, new User(user.getFirstName(), user.getLastName(), user.getUserName(), findUserById(id).getPassword()));
            return true;
        }
        return false;
    }

    public void changePassword(long id, User user){
        String savedPassword = passwordEncoder.encode(user.getPassword());
        userDao.updateUser(id, new User(user.getFirstName(), user.getLastName(), user.getUserName(), savedPassword));
    }

    public boolean givenPasswordIsCorrect(long id, User user) throws EmptyResultDataAccessException {
        return passwordEncoder.matches(user.getPassword(), findUserById(id).getPassword());
    }
}
