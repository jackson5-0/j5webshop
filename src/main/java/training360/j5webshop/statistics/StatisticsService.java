package training360.j5webshop.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training360.j5webshop.products.ProductDao;
import training360.j5webshop.users.UserDao;

@Service
public class StatisticsService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserDao userDao;

    public Statistics getStatistics(){
        int numberOfActiveUsers = userDao.listUsers().size();
        int numberOfActieProducts = productDao.getLengthOfProductList();
        int numberOfAllProducts = productDao.listAllProducts().size();
        return new Statistics(numberOfActiveUsers,numberOfActieProducts,numberOfAllProducts);
    }
}
