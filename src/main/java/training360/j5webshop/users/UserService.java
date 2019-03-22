package training360.j5webshop.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import training360.j5webshop.baskets.BasketDao;

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
}
