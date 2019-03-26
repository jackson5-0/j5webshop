package training360.j5webshop.baskets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import training360.j5webshop.products.Product;
import training360.j5webshop.users.UserService;
import training360.j5webshop.validation.ValidationStatus;
import training360.j5webshop.validation.ResponseStatus;

import java.util.Map;
import java.util.Set;

@RestController
public class BasketController {

    @Autowired
    private BasketService basketService;
    @Autowired
    private UserService userService;

    @PostMapping("/basket")
    public ResponseStatus addToBasket(@RequestParam long basketId, @RequestParam long productId) {
        try {
            ResponseStatus rs = new ResponseStatus();
            if (basketService.addToBasket(basketId, productId)) {
                return rs.addMessage("A termék bekerült a kosárba!");
            } else {
                rs.setStatus(ValidationStatus.FAIL);
                return rs.addMessage("A termék már a kosárban van!");
            }
        } catch (DataIntegrityViolationException de) {
            return new ResponseStatus().setStatus(ValidationStatus.FAIL).addMessage("Nem létező kosár vagy termék");
        }
    }

    @DeleteMapping("/basket")
    public ResponseStatus flushBasket(@RequestParam long basketId) {
            if(basketService.flushBasket(basketId) != 0){
                return new ResponseStatus().addMessage("A kosár újra üres!");
            } else {
                return new ResponseStatus().setStatus(ValidationStatus.FAIL).addMessage("Nem létező kosár");
            }
        }

    @GetMapping("/basket")
    public Set<Product> listProductsOfBasket(Authentication auth) {
        String userName = auth.getName();
        Long id = userService.findBasketId(userName);
        return basketService.listProductsOfBasket(id).keySet();
    }

    @DeleteMapping("/basket/{basket}")
    public ResponseStatus deleteItemFromBasket(@PathVariable long basket, @RequestParam long productId) {
        int rows = basketService.deleteItemFromBasket(basket, productId);
        if (rows == 0) {
           return new ResponseStatus().setStatus(ValidationStatus.FAIL).addMessage("Nem létező kosár");
        }
        return new ResponseStatus().addMessage("A terméket sikeresen eltávolítottuk a kosárból.");
    }
}
