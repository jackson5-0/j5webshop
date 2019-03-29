package training360.j5webshop.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training360.j5webshop.baskets.Basket;
import training360.j5webshop.baskets.BasketDao;
import training360.j5webshop.products.Product;
import training360.j5webshop.products.ProductDao;
import training360.j5webshop.validation.ResponseStatus;
import training360.j5webshop.validation.ValidationStatus;
import training360.j5webshop.validation.Validator;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private BasketDao basketDao;
    @Autowired
    private ProductDao productDao;


//    public long createOrder(Basket basket){
//        long id = orderDao.createOrder(basket);
//        orderDao.addOrderedProduct(id, basket);
//        basketDao.flushBasket(basket.getId());
//        return id;
//    }

    public ResponseStatus createOrder(long basketId, String userName) {
        Basket basket = createBasket(basketId, userName);
        Validator validator = new Validator(basket);
        if (validator.getResponseStatus().getStatus() == ValidationStatus.FAIL) {
            return validator.getResponseStatus();
        }
        long orderId = orderDao.createOrder(basket.getUserId());
        validator.getResponseStatus().addMessage("A " + orderId +" számú rendelését sikeresen feladta.");
        orderDao.addOrderedProduct(orderId, basket);
        basketDao.flushBasket(userName);
        return validator.getResponseStatus();
    }

    private Basket createBasket(long basketId, String userName) {
        List<Product> products = basketDao.findBasketProductsByUserName(userName);
        long userId = basketDao.findUserByBasketId(basketId);
        Basket basket = new Basket(basketId, userId);
        for (Product product: products) {
            basket.addProduct(product, 1);
        }
        return basket;
    }




    public List<Order> listAllOrder(String userName){
        return orderDao.listAllOrder(userName);
    }

    public List<Order> listActiveOrder(String userName){
        return orderDao.listActiveOrder(userName);
    }

    public List<Order> listAllOrderWithDeleted(String userName){
        return orderDao.listAllOrderWithDeleted(userName);
    }

    public List<OrderedProduct> findOrderedProductByOrderId(long id){
        return orderDao.findOrderedProductByOrderId(id);
    }
    public List<OrderInfo> listActiveAdminOrders(){
        return orderDao.listActiveAdminOrders();
    }
    public List<OrderInfo> listAdminOrders(){
        return orderDao.listAdminOrders();
    }
    public void deleteOrders(long id){
        orderDao.deleteWholeOrder(id);
    }
    public void deleteItem(long id, String address){
        orderDao.deleteItem(id,address);
    }

    public void changeStatusById(long orderId){
        orderDao.changeStatusById(orderId);
    }

}
