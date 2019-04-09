package training360.j5webshop.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training360.j5webshop.baskets.Basket;
import training360.j5webshop.baskets.BasketDao;
import training360.j5webshop.products.Product;
import training360.j5webshop.validation.ResponseStatus;
import training360.j5webshop.validation.ValidationStatus;
import training360.j5webshop.validation.Validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private BasketDao basketDao;


    public ResponseStatus createOrder(long basketId, String userName, String address) {
        ResponseStatus rs = new ResponseStatus();
        Basket basket = createBasket(basketId, userName);
        String newAddress = address.substring(address.indexOf('w') + 1).replaceAll("\"", "");
        Validator validator = new Validator(basket, newAddress);
        if (validator.getResponseStatus().getStatus() == ValidationStatus.FAIL) {
            rs.setStatus(ValidationStatus.FAIL);
            rs.addMessage(validator.getResponseStatus().getMessages().get(0));
        } else if (address.replaceAll("\"", "").indexOf("new") == 0 && addressAlreadyRegisteredForUser(newAddress, userName)){
            rs.setStatus(ValidationStatus.FAIL);
            rs.addMessage("A megadott címre már korábban rendeltél terméket. Kérlek, válaszd ki a fenti listából!");
        } else {
            long orderId = orderDao.createOrder(basket.getUserId(), newAddress);
            rs.addMessage("A " + orderId + " számú rendelését sikeresen feladta.");
            rs.setStatus(ValidationStatus.SUCCESS);
            orderDao.addOrderedProduct(orderId, basket);
            basketDao.flushBasket(userName);
        }
        return rs;
    }

    private boolean addressAlreadyRegisteredForUser(String address, String userName) {
        List<Order> orderList = orderDao.listAllOrder(userName);
        if (orderList.isEmpty()) {
            return false;
        }
        for (Order order : orderList){
            String a = order.getShippingAddress();
            if (a != null && (a.trim().replaceAll("[^A-Za-z0-9]", ""))
                    .equalsIgnoreCase(address.trim().replaceAll("[^A-Za-z0-9]", ""))) {
                return true;
            }
        }
        return false;
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

    public Set<Product> listLast3OrderedItem(){
        return new HashSet<>(orderDao.listLast3OrderedItem());
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
