package training360.j5webshop.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training360.j5webshop.baskets.BasketDao;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private BasketDao basketDao;

    public Long findBasketId(String userName) {
        long userId = userDao.getUserId(userName);
        return basketDao.findBasketId(userId);
    }
}
