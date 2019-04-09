package training360.j5webshop.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import training360.j5webshop.products.Product;
import training360.j5webshop.validation.ResponseStatus;

import java.util.List;
import java.util.Set;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/myorders")
    public ResponseStatus createOrder(Authentication authentication, @RequestParam long basketId, @RequestBody String address) {
        return orderService.createOrder(basketId, authentication.getName(), address);
    }

    @GetMapping("/myorders/top3")
    public Set<Product> listLast3OrderedItem(){
        return orderService.listLast3OrderedItem();
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
