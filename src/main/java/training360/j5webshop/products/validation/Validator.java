package training360.j5webshop.products.validation;

import training360.j5webshop.products.Product;

public class Validator {

    private ResponseStatus responseStatus;

    public Validator(Product product) {
        responseStatus = new ResponseStatus();
        checkName(product.getName());
        checkPublisher(product.getPublisher());
        checkPrice(product.getPrice());
        if (responseStatus.getMessages().size() > 0) {
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

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

}
