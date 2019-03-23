package training360.j5webshop.products;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import training360.j5webshop.validation.ValidationStatus;
import training360.j5webshop.validation.Validator;
import training360.j5webshop.validation.ResponseStatus;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{address}")
    public Product findProductByAddress(@PathVariable String address) {
        return productService.findProductByAddress(address);

    }

    @PostMapping("/api/products")
    public ResponseStatus createProduct(@RequestBody Product product) {
        Validator validator = new Validator(product);
        long id;
        if (validator.getResponseStatus().getStatus() == ValidationStatus.SUCCESS) {
            id = productService.createProduct(product);
            validator.getResponseStatus().addMessage("A terméket (id: " + id + ") sikeresen hozzáadta az adatbázishoz.");
            return validator.getResponseStatus();
        }
        return validator.getResponseStatus();
    }


    @GetMapping("/products/count")
    public int getLengthOfProductList() {
        return productService.getLengthOfProductList();
    }

    @GetMapping("/products")
    public List<Product> listProducts(@RequestParam(required = false) int start, @RequestParam(required = false) int size) {
        if (size != 0) {
            return productService.listProductsWithLimit(start, size);
        } else {
            return productService.listAllProducts();
        }
    }

    @ExceptionHandler({InvalidFormatException.class})
    public ResponseStatus handleParseException(Exception exception) {
        ResponseStatus status = new ResponseStatus().addMessage("Hibás formátum!");
        status.setStatus(ValidationStatus.FAIL);
        return status;
    }

    @PutMapping("/admin/deleteproduct/{id}")
    public ResponseStatus deleteProductById(@PathVariable long id) {
        ResponseStatus status = new ResponseStatus().addMessage("Törlés sikerült!");
        productService.deleteProductById(id);
        return status;
    }

    @PostMapping("/admin/updateproduct/{id}")
    public ResponseStatus updateProduct(@PathVariable long id, @RequestBody Product product) {
//        new Validator(product);
        if (productService.updateProduct(id, product)) {
            return new ResponseStatus().addMessage("Sikeres módosítás!");
        } else {
            ResponseStatus status = new ResponseStatus().setStatus(ValidationStatus.FAIL);
            return status.addMessage("A megadott érték már használatban van!");
        }
    }
}
