package training360.j5webshop.validation;

import training360.j5webshop.baskets.Basket;
import training360.j5webshop.products.Product;
import training360.j5webshop.reviews.Review;
import training360.j5webshop.users.User;

import java.util.List;

public class Validator {

    private ResponseStatus responseStatus;

    public Validator(User user, List<User> userList) {
        responseStatus = new ResponseStatus();
        checkUsername(user, userList);
        checkPassword(user.getPassword());
        if (responseStatus.getMessages().size() > 0) {
            responseStatus.setStatus(ValidationStatus.FAIL);
        }
    }

    public Validator(Product product) {
        responseStatus = new ResponseStatus();
        checkName(product.getName());
        checkPublisher(product.getPublisher());
        checkPrice(product.getPrice());
        if (responseStatus.getMessages().size() > 0) {
            responseStatus.setStatus(ValidationStatus.FAIL);
        }
    }

    public Validator(Basket basket){
        responseStatus = new ResponseStatus();
        checkBasket(basket);
        if(responseStatus.getMessages().size()>0){
            responseStatus.setStatus(ValidationStatus.FAIL);
        }
    }
    public Validator(int quantity){
        responseStatus = new ResponseStatus();
        checkQuantity(quantity);
        if(responseStatus.getMessages().size()>0){
            responseStatus.setStatus(ValidationStatus.FAIL);
        }
    }

    public Validator(Review review) {
        responseStatus = new ResponseStatus();
        checkReview(review);
        if(responseStatus.getMessages().size()>0){
            responseStatus.setStatus(ValidationStatus.FAIL);
        }
    }

    private void checkName(String name) {
        if (name == null || name.trim().equals("")) {
            responseStatus.addMessage("A név nem lehet üres!");
        } else if (name.length() < 3) {
            responseStatus.addMessage("A név nem lehet rövidebb 3 karakternél!");
        }
    }

    private void checkPublisher(String publisher) {
        if (publisher == null || publisher.trim().equals("")) {
            responseStatus.addMessage("A kiadó nem lehet üres!");
        } else if (publisher.length() < 3) {
            responseStatus.addMessage("A kiadó nem lehet rövidebb 3 karakternél!");
        }
    }

    private void checkPrice(int price) {
        if (price <= 0) {
            responseStatus.addMessage("Az ár nem lehet nulla, vagy negatív szám!");
        } else if (price > 2_000_000) {
            responseStatus.addMessage("Az ár nem lehet nagyobb 2.000.000 Ft-nál!");
        }
    }

    private void checkUsername(User user, List<User> userList){
        for (User userItem : userList){
            if (userItem.getUserName().equals(user.getUserName()) && user.getId() != userItem.getId()){
                responseStatus.addMessage("A kért felhasználónév foglalt!");
            }
        }
        String pattern = "^[a-zA-Z0-9._-]{3,}$";
        if (!user.getUserName().matches(pattern)){
            responseStatus.addMessage("A felhasználói név legalább 3 karakter hosszú legyen, csak számot, kis- és nagybetűt, pontot, alulvonást és kötőjelet tartalmazhat!");
        }

    }

    private void checkPassword(String password){
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
        String patternForHashedPassword = "^\\$2[aby]?\\$[\\d]+\\$[./A-Za-z0-9]{53}$";
        if (password.length() < 8 || !(password.matches(pattern) || password.matches(patternForHashedPassword))){
            responseStatus.addMessage("A jelszó minimum 8 karakter hosszú legyen, tartalmazzon kis- és nagybetűt, illetve számot!");
        }
    }

    private void checkBasket(Basket basket){
        if(basket.getProducts().size()==0){
            responseStatus.addMessage("Csak terméket tartalmazó kosarat lehet megrendelni");
        }
    }

    private void checkReview(Review review) {
        if (review.getRating() < 1 || review.getRating() > 5) {
            responseStatus.addMessage("Kérjük értékelje a terméket 1-től 5-ig terjedő skálán!");
        } else if (review.getMessage().length() > 255) {
            responseStatus.addMessage("A szöveges értékelés hossza nem lehet több 255 karakternél!");
        }
        for (int i = 0; i < review.getMessage().length(); i++) {
            if (Character.toString(review.getMessage().charAt(i)).matches("[<>]")) {
                responseStatus.addMessage("A szöveges értékelés nem tartalmazhatja a '<' , '>' karaktereket!");
            }
        }
    }

    private void checkQuantity(int quantity){
        if(quantity <=0 || quantity > 20){
            responseStatus.addMessage("Egy darabnál kevesebb vagy 20 darabnál több termék megrendelése nem lehetséges");
        }
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }
}
