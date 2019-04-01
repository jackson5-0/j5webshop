package training360.j5webshop.reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training360.j5webshop.products.ProductDao;
import training360.j5webshop.users.UserDao;
import training360.j5webshop.validation.ResponseStatus;
import training360.j5webshop.validation.ValidationStatus;
import training360.j5webshop.validation.Validator;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    public ResponseStatus createReview(String userName, Review review) {
        if (reviewDao.checkIfUserHasDeliveredProductAndHasReview(userName, review.getProduct().getId()).getUserReview() != null) {
            return new ResponseStatus().addMessage("Ehhez a termékhez már írt értékelést!");
        }
        if (!reviewDao.checkIfUserHasDeliveredProductAndHasReview(userName, review.getProduct().getId()).getHasDeliveredProduct()) {
            return new ResponseStatus().addMessage("A termékhez csak akkor írhat értékelést, ha már rendelt belőle!");
        }
        review.setUser(userDao.findUserByUserName(userName));
        Validator validator = new Validator(review);
        if (validator.getResponseStatus().getStatus() == ValidationStatus.FAIL) {
            return validator.getResponseStatus();
        }
        reviewDao.createReview(review);
        validator.getResponseStatus().addMessage("Az értékelést elmentettük!");
        return validator.getResponseStatus();
    }

    public ResponseStatus uploadReview(String userName, Review review) {
        review.setUser(userDao.findUserByUserName(userName));
        Validator validator = new Validator(review);
        if (validator.getResponseStatus().getStatus() == ValidationStatus.FAIL) {
            return validator.getResponseStatus();
        }
        reviewDao.updateReview(review);
        validator.getResponseStatus().addMessage("Az értékelést módosította!");
        return validator.getResponseStatus();
    }

    public ResponseStatus deleteReview(String userName, Review review) {
        review.setUser(userDao.findUserByUserName(userName));
        reviewDao.deleteReview(review);
        return new ResponseStatus().setStatus(ValidationStatus.SUCCESS).addMessage("Az értékelést sikeresen törölte!");
    }

    public ReviewInfo checkIfUserHasDeliveredProductAndHasReview(String userName, long productId) {
        return reviewDao.checkIfUserHasDeliveredProductAndHasReview(userName, productId);
    }

    public List<Review> listReviewByProductId(long productId) {
        return reviewDao.listReviewByProductId(productId);
    }
}
