package training360.j5webshop.baskets;

import com.fasterxml.jackson.core.JsonParseException;
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

    @PutMapping("/createbasket")
    public void createBasket(@RequestBody long userId) {
        basketService.createBasket(userId);
    }

    @PutMapping("/addtobasket/{basketId}")
    public ResponseStatus addToBasket(@PathVariable long basketId,@RequestBody Product product) {
        ResponseStatus rs = new ResponseStatus();
        if (basketService.addToBasket(basketId, product)) {
            return rs.addMessage("A termék bekerült a kosárba!");
        } else {
            rs.setStatus(ValidationStatus.FAIL);
            return rs.addMessage("A termék csak egyszer kerülhet a kosárba!");
        }
    }

    @DeleteMapping("/flushbasket/{basketId}")
    public ResponseStatus flushBasket(@PathVariable long basketId) {
            basketService.flushBasket(basketId);
            return new ResponseStatus().addMessage("A kosár újra üres!");
        }

    @GetMapping("/productofbasket/{basketId}")
    public Set<Product> listProductsOfBasket(@PathVariable long basketId) {
        return basketService.listProductsOfBasket(basketId).keySet();
    }
}
