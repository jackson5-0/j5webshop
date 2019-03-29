package training360.j5webshop.reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import training360.j5webshop.products.ProductDao;
import training360.j5webshop.validation.ResponseStatus;
import training360.j5webshop.validation.ValidationStatus;
import training360.j5webshop.validation.Validator;

@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/addreview")
    public ResponseStatus createReview(Authentication authentication, @RequestBody Review review) {
        return reviewService.createReview(authentication.getName(), review);
    }

//     Postman teszt
//    @PostMapping("/review")
//    public ResponseStatus createReview(@RequestBody Review review) {
//        return reviewService.createReview("admin", review);
//    }

    @GetMapping("/checkifuserhasdeliveredproduct")
    public Boolean[] checkIfUserHasDeliveredProduct(@RequestParam String username, @RequestParam long productid) {
        return reviewService.checkIfUserHasDeliveredProductAndHasReview(username, productid);
    }
}
