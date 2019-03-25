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
    public ResponseStatus addToBasket(@RequestParam long basketId, @RequestParam long productId) {
        ResponseStatus rs = new ResponseStatus();
        if (basketService.addToBasket(basketId, productId)) {
            return rs.addMessage("A termék bekerült a kosárba!");
        } else {
            rs.setStatus(ValidationStatus.FAIL);
            return rs.addMessage("A termék már a kosárban van!");
        }
    }

    @DeleteMapping("/basket")
    public ResponseStatus flushBasket(@RequestParam long basketId) {
            if(basketService.flushBasket(basketId) != 0){
                return new ResponseStatus().addMessage("A kosár újra üres!");
            } else {
                return new ResponseStatus().setStatus(ValidationStatus.FAIL).addMessage("Nem letező kosár");
            }
        }

    @GetMapping("/basket")
    public Set<Product> listProductsOfBasket(@RequestParam long basketId) {
        return basketService.listProductsOfBasket(basketId).keySet();
    }

    @DeleteMapping("/basket/{basket}")
    public ResponseStatus deleteItemFromBasket(@PathVariable long basket, @RequestParam long productId) {
        int rows = basketService.deleteItemFromBasket(basket, productId);
        if (rows == 0) {
           return new ResponseStatus().setStatus(ValidationStatus.FAIL).addMessage("Nem letező kosár");
        }
        return new ResponseStatus().addMessage("A terméket sikeresen eltávolítottuk a kosárból.");
    }
}
