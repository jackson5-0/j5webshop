package training360.j5webshop.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    public ResponseStatus createOrder(Authentication authentication, @RequestParam long basketId) {
        return orderService.createOrder(basketId, authentication.getName());
    }
    @GetMapping("/myorders/top3")
    public List<Product> listLast3OrderedItem(Authentication auth){
        return orderService.listLast3OrderedItem(auth.getName());
    }

    @GetMapping("/myorders")
    public List<Order> listAllOrder(Authentication auth){
        return orderService.listAllOrder(auth.getName());
    }

    @GetMapping("/myorders/active")
    public List<Order> listActiveOrder(Authentication auth){
        return orderService.listActiveOrder(auth.getName());
    }

    @GetMapping("myorders/all")
    public List<Order> listAllOrderWithDeleted(Authentication auth){
        return orderService.listAllOrderWithDeleted(auth.getName());
    }

    @GetMapping("/myorders/{orderId}")
    public List<OrderedProduct> findOrderedProductByOrderId(@PathVariable long orderId){
        return orderService.findOrderedProductByOrderId(orderId);
    }
    @GetMapping("/orders")
    public List<OrderInfo> listAdminOrders(){
        return orderService.listAdminOrders();
    }

    @GetMapping("/activeorders") //
    public List<OrderInfo> listActiveOrders(){
        return orderService.listActiveAdminOrders();
    }

    @DeleteMapping("/orders/delete/{id}")
    public void deleteOrders(@PathVariable long id){
        orderService.deleteOrders(id);
    }

    @DeleteMapping("/order/{id}/{address}")
    public void deleteItem(@PathVariable long id, @PathVariable String address){
        orderService.deleteItem(id,address);
    }

    @PostMapping("/orders/{orderId}/status")
    public void changeStatusById(@PathVariable long orderId){
        orderService.changeStatusById(orderId);
    }
}
