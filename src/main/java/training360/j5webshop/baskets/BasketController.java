package training360.j5webshop.baskets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import training360.j5webshop.products.Product;
import training360.j5webshop.validation.ValidationStatus;
import training360.j5webshop.validation.ResponseStatus;

import java.util.Map;
import java.util.Set;

@RestController
public class BasketController {

    @Autowired
    private BasketService basketService;

    @PostMapping("/basket")
    public ResponseStatus addToBasket(@RequestParam long basketId, @RequestBody Product product) {
        ResponseStatus rs = new ResponseStatus();
        if (basketService.addToBasket(basketId, product)) {
            return rs.addMessage("A termék bekerült a kosárba!");
        } else {
            rs.setStatus(ValidationStatus.FAIL);
            return rs.addMessage("A termék már a kosárban van!");
        }
    }

    @DeleteMapping("/basket")
    public ResponseStatus flushBasket(@RequestParam long basketId) {
            basketService.flushBasket(basketId);
            return new ResponseStatus().addMessage("A kosár újra üres!");
        }

    @GetMapping("/basket")
    public Set<Product> listProductsOfBasket(@RequestParam long basketId) {
        return basketService.listProductsOfBasket(basketId).keySet();
    }
}
