package training360.j5webshop.categories;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import training360.j5webshop.validation.ResponseStatus;
import training360.j5webshop.validation.ValidationStatus;
import training360.j5webshop.validation.Validator;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public List<Category> listCategories() {
        return categoryService.listCategories();
    }

    @PostMapping("/categories")
    public ResponseStatus createCategory(@RequestBody Category category) {
        Validator validator = new Validator(category);
        if (validator.getResponseStatus().getStatus() == ValidationStatus.FAIL) {
            return validator.getResponseStatus();
        }
        String message = categoryService.createCategory(category);
        if (!message.equals("")) {
            return validator.getResponseStatus()
                    .addMessage(message)
                    .setStatus(ValidationStatus.FAIL);
        }
        return validator.getResponseStatus().addMessage("Sikeresen létrejött a kategória!");
    }

    @PutMapping("/categories")
    public ResponseStatus updateCategories(@RequestBody List<Category> categories) {
        Validator validator = new Validator(categories);
        if (validator.getResponseStatus().getStatus() == ValidationStatus.FAIL) {
            return validator.getResponseStatus();
        } else {
            categoryService.updateCategories(categories);
            return validator.getResponseStatus().addMessage("Sikeres módosítás!");
        }
    }

    @ExceptionHandler({InvalidFormatException.class})
    public ResponseStatus handleParseException(Exception exception) {
        ResponseStatus status = new ResponseStatus().addMessage("A sorszám csak számokból állhat!");
        status.setStatus(ValidationStatus.FAIL);
        return status;
    }

}
