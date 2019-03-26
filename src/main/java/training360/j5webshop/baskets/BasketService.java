package training360.j5webshop.baskets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import training360.j5webshop.products.Product;
import training360.j5webshop.products.ProductDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BasketService {

    @Autowired
    private BasketDao basketDao;
    @Autowired
    private ProductDao productDao;

    public boolean addToBasket(long basketId, long productId) throws DataIntegrityViolationException {
        if (productAlreadyAdded(basketId, productId)) {
            return false;
        } else {
            basketDao.addToBasket(basketId, productId);
            return true;
        }
    }

    public int flushBasket(long basketId) {
        return basketDao.flushBasket(basketId);
    }

    public Map<Product, Integer> listProductsOfBasket(long basketId) {
        List<Long> basketIds = basketDao.listProductIdsOfBasket(basketId);
        Map<Product, Integer> productMap = new HashMap<>();
        for (Long id : basketIds) {
            productMap.put(productDao.findProductById(id), 1);
        }
        return productMap;
    }

    private boolean productAlreadyAdded(long basketId, long productId) {
        for (Long l : basketDao.listProductIdsOfBasket(basketId)) {
            if (productId == l) {
                return true;
            }
        }
        return false;
    }

    public int deleteItemFromBasket(long basketId, long productId) {
        return basketDao.deleteItemFromBasket(basketId, productId);
    }
}
