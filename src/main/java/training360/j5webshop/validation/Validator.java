package training360.j5webshop.validation;

import training360.j5webshop.baskets.Basket;
import training360.j5webshop.orders.Order;
import training360.j5webshop.products.Product;
import training360.j5webshop.users.User;

import java.util.List;
import java.util.regex.Pattern;

public class Validator {

    private ResponseStatus responseStatus;

    public Validator(User user, List<User> userList) {
        responseStatus = new ResponseStatus();
        checkUsername(user.getUserName(), userList);
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

    private void checkUsername(String userName, List<User> userList){
        for (User user : userList){
            if (user.getUserName().equals(userName)){
                responseStatus.addMessage("A kért felhasználónév foglalt!");
            }
        }
        String pattern = "^[a-zA-Z0-9._-]{3,}$";
        if (!userName.matches(pattern)){
            responseStatus.addMessage("A felhasználói név legalább 3 karakter hosszú legyen, csak számot, kis- és nagybetűt, pontot, alulvonást és kötőjelet tartalmazhat!");
        }

    }

    private void checkPassword(String password){
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
        if (password.length() < 8 || !password.matches(pattern)){
            responseStatus.addMessage("A jelszó minimum 8 karakter hosszú legyen, tartalmazzon kis- és nagybetűt, illetve számot!");
        }
    }

    private void checkBasket(Basket basket){
        if(basket.getProducts().size()==0){
            responseStatus.addMessage("Csak terméket tartalmazó kosarat lehet megrendelni");
        }
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

}
