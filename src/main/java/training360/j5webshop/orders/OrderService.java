package training360.j5webshop.orders;

import org.springframework.stereotype.Service;
import training360.j5webshop.baskets.BasketDao;

import java.util.List;

@Service
public class OrderService {

    private OrderDao orderDao;
    private BasketDao basketDao;

    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public long createOrder(Order order){
        long id = orderDao.createOrder(order);
        orderDao.createOrderedProduct(order);
//        basketDao.flushBasket(order.getBasketId());
        return id;
    }

    public List<Order> listAllOrder(){
        return orderDao.listAllOrder();
    }

    public List<OrderedProduct> findOrderedProductByOrderId(long id){
        return orderDao.findOrderedProductByOrderId(id);
    }
}
