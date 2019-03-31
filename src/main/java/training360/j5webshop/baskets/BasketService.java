package training360.j5webshop.baskets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import training360.j5webshop.products.Product;
import training360.j5webshop.products.ProductDao;

import java.util.*;

@Service
public class BasketService {

    @Autowired
    private BasketDao basketDao;
    @Autowired
    private ProductDao productDao;

    public void addToBasketWithQuantity(int quantity, long productId, String userName) throws DataIntegrityViolationException {
        Long basketId = basketDao.findBasketIdByUserName(userName);
        basketDao.addToBasketWithQuantity(quantity, productId, basketId);
    }

    public int decreaseAmountInBasket(long productId, long basketId, int quantity){
        return basketDao.decreaseAmountInBasket(productId, basketId, quantity);
    }

    public int flushBasket(String userName) {
        return basketDao.flushBasket(userName);
    }

    public Map<Product, Integer> listProductsOfBasket(long basketId) {
        List<Long> basketIds = basketDao.listProductIdsOfBasket(basketId);
        Map<Product, Integer> productMap = new HashMap<>();
        for (Long id : basketIds) {
            productMap.put(productDao.findProductById(id), 1);
        }
        return productMap;
//        Alternativ megoldas a kosar tartalmanak listazasara
//        return new HashSet<>(basketDao.findBasketProductsByUserName(userName));
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

    public List<BasketItemContainer> basketItemsWithQuantity(long basketId){
        return basketDao.basketItemsWithQuantity(basketId);
    }

}
