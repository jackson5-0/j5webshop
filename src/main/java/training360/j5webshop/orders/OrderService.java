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
//        orderDao.addOrderdProduct(id, basket);
//        basketDao.flushBasket(basket.getId());
//        return id;
//    }

    public ResponseStatus createOrder(long basketId) {
        Basket basket = createBasket(basketId);
        Validator validator = new Validator(basket);
        if (validator.getResponseStatus().getStatus() == ValidationStatus.FAIL) {
            return validator.getResponseStatus();
        }
        long orderId = orderDao.createOrder(basket.getUserId());
        validator.getResponseStatus().addMessage("A " + orderId +" számú rendelését sikeresen feladta.");
        orderDao.addOrderedProduct(orderId, basket);
        basketDao.flushBasket(basketId);
        return validator.getResponseStatus();
    }

    private Basket createBasket(long basketId) {
        List<Long> productIds = basketDao.listProductIdsOfBasket(basketId);
        long userId = basketDao.findUserByBasketId(basketId);
        Basket basket = new Basket(basketId, userId);
        for (Long productId : productIds) {
            basket.addProduct(productDao.findProductById(productId), 1);
        }
        return basket;
    }




    public List<Order> listAllOrder(){
        return orderDao.listAllOrder();
    }

    public List<Order> listActiveOrder(){
        return orderDao.listActiveOrder();
    }

    public List<Order> listAllOrderWithDeleted(){
        return orderDao.listAllOrderWithDeleted();
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
