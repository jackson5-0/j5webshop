package training360.j5webshop.baskets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import training360.j5webshop.users.UserService;
import training360.j5webshop.validation.ValidationStatus;
import training360.j5webshop.validation.ResponseStatus;
import training360.j5webshop.validation.Validator;

import java.util.List;
import java.util.Set;

@RestController
public class BasketController {

    @Autowired
    private BasketService basketService;
    @Autowired
    private UserService userService;

    @PostMapping("/basket")
    public ResponseStatus addToBasketWithQuantity(@RequestParam int quantity, @RequestParam long productId, Authentication auth) {
        Validator validator = new Validator(quantity);
        try {

            if (validator.getResponseStatus().getStatus() == ValidationStatus.FAIL) {
                return validator.getResponseStatus();
            }
            basketService.addToBasketWithQuantity(quantity, productId, auth.getName());
            validator.getResponseStatus().addMessage(quantity + " db termék bekerült a kosárba.");

        } catch (EmptyResultDataAccessException ea) {
            return new ResponseStatus().setStatus(ValidationStatus.FAIL).addMessage("Nem létező kosár vagy termék");
        }
        return validator.getResponseStatus();
    }

    @DeleteMapping("/basket/{basketId}/{productId}")
    public ResponseStatus decreaseAmountInBasket(@PathVariable long basketId, @PathVariable long productId, @RequestParam int quantity) {
        if (basketService.decreaseAmountInBasket(productId, basketId, quantity) != 0) {
            return new ResponseStatus().addMessage("Egy termék sikeresen kikerült a kosárból!");
        } else {
            return new ResponseStatus().setStatus(ValidationStatus.FAIL).addMessage("Nem létező termék");
        }
    }

    @DeleteMapping("/basket")
    public ResponseStatus flushBasket(Authentication authentication) {
        if (basketService.flushBasket(authentication.getName()) != 0) {
            return new ResponseStatus().addMessage("A kosár újra üres!");
        } else {
            return new ResponseStatus().setStatus(ValidationStatus.FAIL).addMessage("A kosár üres.");
        }
    }

    @GetMapping("/basket")
    public List<BasketItemContainer> basketItemsWithQuantity(Authentication auth) {
        String userName = auth.getName();
        Long basketId = userService.findBasketId(userName);
        return basketService.basketItemsWithQuantity(basketId);
    }

    @DeleteMapping("/basket/{basket}")
    public ResponseStatus deleteItemFromBasket(@PathVariable long basket, @RequestParam long productId) {
        int rows = basketService.deleteItemFromBasket(basket, productId);
        if (rows == 0) {
            return new ResponseStatus().setStatus(ValidationStatus.FAIL).addMessage("Nem létező kosár");
        }
        return new ResponseStatus().addMessage("A terméket sikeresen eltávolítottuk a kosárból.");
    }

    @GetMapping("/basket/addresses")
    public Set<String> listUserAddresses(Authentication authentication) {
        return basketService.listUserAddresses(authentication.getName());
    }
}