package training360.j5webshop.products.validation;

import java.util.ArrayList;
import java.util.List;

public class ResponseStatus {

    private ProductStatus status = ProductStatus.SUCCESS;
    private List<String> messages = new ArrayList<>();

    public ResponseStatus addMessage(String message) {
        messages.add(message);
        return this;
    }

    public ResponseStatus setStatus(ProductStatus status) {
        this.status = status;
        return this;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public List<String> getMessages() {
        return messages;
    }
}
