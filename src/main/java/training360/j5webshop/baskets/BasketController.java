package training360.j5webshop.baskets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import training360.j5webshop.products.Product;

import java.util.Set;

@RestController
public class BasketController {

    @Autowired
    private BasketService basketService;

    @PutMapping("/createbasket")
    public void createBasket(@RequestBody long userId) {
        basketService.createBasket(userId);
    }

    @PutMapping("/addtobasket/{basketId}")
    public void addToBasket(@PathVariable long basketId,@RequestBody Product product) {
        basketService.addToBasket(basketId, product);
    }

    @DeleteMapping("/flushbasket/{basketId}")
    public void flushBasket(@PathVariable long basketId) {
        basketService.flushBasket(basketId);
    }

    @GetMapping("/productofbasket/{basketId}")
    public Set<Product> listProductsOfBasket(@PathVariable long basketId) {
        return basketService.listProductsOfBasket(basketId).keySet();
    }
}
