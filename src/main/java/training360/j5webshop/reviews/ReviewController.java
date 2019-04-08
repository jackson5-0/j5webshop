package training360.j5webshop.reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import training360.j5webshop.validation.ResponseStatus;

import java.util.List;

@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/addreview")
    public ResponseStatus createReview(Authentication authentication, @RequestBody Review review) {
        return reviewService.createReview(authentication.getName(), review);
    }

    @PutMapping("/updatereview")
    public ResponseStatus updateReview(Authentication authentication, @RequestBody Review review) {
        return reviewService.uploadReview(authentication.getName(), review);
    }

    @GetMapping("/checkifuserhasdeliveredproduct")
    public ReviewInfo checkIfUserHasDeliveredProductAndHasReview(Authentication authentication, @RequestParam long productid) {
        return reviewService.checkIfUserHasDeliveredProductAndHasReview(authentication.getName(), productid);
    }

    @DeleteMapping("/deletereview")
    public ResponseStatus deleteReview(Authentication authentication, @RequestBody Review review) {
        return reviewService.deleteReview(authentication.getName(), review);
    }

    @GetMapping("/listreview")
    public List<Review> listReviewByProductId(@RequestParam long productid) {
        return reviewService.listReviewByProductId(productid);
    }
}
