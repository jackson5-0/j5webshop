package training360.j5webshop.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import training360.j5webshop.baskets.Basket;
import training360.j5webshop.products.Product;
import training360.j5webshop.validation.ValidationStatus;
import training360.j5webshop.validation.Validator;
import training360.j5webshop.validation.ResponseStatus;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

//    @PostMapping("/myorders")
//    public ResponseStatus createOrder(@RequestBody Basket basket){
//        Validator validator = new Validator(basket);
//        long id;
//        if(validator.getResponseStatus().getStatus() == ValidationStatus.SUCCESS){
//            id = orderService.createOrder(basket);
//            validator.getResponseStatus().addMessage("A " + id +" számú rendelését sikeresen feladta.");
//            return validator.getResponseStatus();
//        }
//        return validator.getResponseStatus();
//    }

    @PostMapping("/myorders")
    public ResponseStatus createOrder(@RequestParam long basketId) {
        return orderService.createOrder(basketId);
    }

    @GetMapping("/myorders")
    public List<Order> listAllOrder(){
        return orderService.listAllOrder();
    }

    @GetMapping("/myorders/{id}")
    public List<OrderedProduct> findOrderedProductByOrderId(@PathVariable long id){
        return orderService.findOrderedProductByOrderId(id);
    }
    @GetMapping("/orders")
    public List<OrderInfo> listAdminOrders(){
        return orderService.listAdminOrders();
    }
    @GetMapping("/activeorders")
    public List<OrderInfo> listActiveOrders(){
        return orderService.listActiveAdminOrders();
    }
    @DeleteMapping("/orders/delete/{id}")
    public void deleteOrders(@PathVariable long id){
        orderService.deleteOrders(id);
    }

    @GetMapping("/order/{order}")
    public List<Product> listOrder(@PathVariable long orderId) {
        return orderService.listOrder(orderId);
    }
}
