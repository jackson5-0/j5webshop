package training360.j5webshop.reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training360.j5webshop.products.ProductDao;
import training360.j5webshop.users.UserDao;
import training360.j5webshop.validation.ResponseStatus;
import training360.j5webshop.validation.ValidationStatus;
import training360.j5webshop.validation.Validator;

@Service
public class ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    public ResponseStatus createReview(String userName, Review review) {
        review.setUser(userDao.findUserByUserName(userName));
        Validator validator = new Validator(review);
        if (validator.getResponseStatus().getStatus() == ValidationStatus.FAIL) {
            return validator.getResponseStatus();
        }
        reviewDao.createReview(review);
        validator.getResponseStatus().addMessage("Az értékelést elmentettük!");
        return validator.getResponseStatus();
    }

    public Boolean[] checkIfUserHasDeliveredProductAndHasReview(String userName, long productId) {
        Boolean userHasDeliveredProduct = reviewDao.checkIfUserHasDeliveredProduct(userName, productId);
        Boolean userHasReview = reviewDao.checkIfUserHasDeliveredProduct(userName, productId);
        Boolean[] ret = new Boolean[]{userHasDeliveredProduct, userHasReview};
        return ret;
    }
}
