package training360.j5webshop.products;

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

    @GetMapping("/products")
    public List<Product> listProducts(@RequestParam(required = false) int start, @RequestParam(required = false) int size) {
        if (size != 0) {
            return productService.listProductsWithLimit(start, size);
        } else {
            return productService.listAllProducts();
        }
    }

    @GetMapping("/products/{address}")
    public Product findProductByAddress(@PathVariable String address) {
        return productService.findProductByAddress(address);

    }

    @PostMapping("/admin/products")
    public ResponseStatus createProduct(@RequestBody Product product) {
        Validator validator = new Validator(product);
        if (validator.getResponseStatus().getStatus() == ValidationStatus.SUCCESS) {
            long id = productService.createProduct(product);
            validator.getResponseStatus().addMessage("A terméket (id: " + id + ") sikeresen hozzáadta az adatbázishoz.");
        }
        return validator.getResponseStatus();
    }

    @PutMapping("/admin/products")
    public ResponseStatus updateProduct(@RequestParam long id, @RequestBody Product product) {
        if (productService.updateProduct(id, product)) {
            return new ResponseStatus().addMessage("Sikeres módosítás!");
        } else {
            ResponseStatus status = new ResponseStatus().setStatus(ValidationStatus.FAIL);
            return status.addMessage("A megadott érték már használatban van!");
        }
    }

    @DeleteMapping("/admin/products")
    public ResponseStatus deleteProductById(@RequestParam long id) {
        ResponseStatus status = new ResponseStatus().addMessage("Törlés sikerült!");
        productService.deleteProductById(id);
        return status;
    }

    @GetMapping("/products/count")
    public int getLengthOfProductList() {
        return productService.getLengthOfProductList();
    }

    @ExceptionHandler({InvalidFormatException.class})
    public ResponseStatus handleParseException(Exception exception) {
        ResponseStatus status = new ResponseStatus().addMessage("Hibás formátum!");
        status.setStatus(ValidationStatus.FAIL);
        return status;
    }

}
