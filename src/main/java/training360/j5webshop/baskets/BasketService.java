package training360.j5webshop.baskets;

import org.springframework.stereotype.Service;
import training360.j5webshop.products.Product;

import java.util.HashMap;
import java.util.Map;

@Service
public class BasketService {

    private BasketDao basketDao;

    public BasketService(BasketDao basketDao) {
        this.basketDao = basketDao;
    }

    public void createBasket(long userId) {
        basketDao.createBasket(userId);
    }

    public boolean addToBasket(long basketId, Product product) {
        if (productAlreadyAdded(basketId, product)) {
            return false;
        } else {
            basketDao.addToBasket(basketId, product);
            return true;
        }
    }

    public void flushBasket(long basketId) {
        basketDao.flushBasket(basketId);
    }

    public Map<Product, Integer> listProductsOfBasket(long basketId) {
        return basketDao.listProductsOfBasket(basketId);
    }

    private boolean productAlreadyAdded(long basketId, Product product) {
        for (Long l : basketDao.listProductIdsOfBasket(basketId)) {
            if (product.getId() == l) {
                return true;
            }
        }
        return false;
    }
}
