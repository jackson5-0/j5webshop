package training360.j5webshop.products;

import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import training360.j5webshop.products.validation.ProductStatus;
import training360.j5webshop.products.validation.Validator;
import training360.j5webshop.products.validation.ResponseStatus;

import java.text.MessageFormat;
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
    public ResponseStatus createProduct(@RequestBody Product product){
        Validator validator = new Validator(product);
        if (validator.getResponseStatus().getStatus() == ProductStatus.SUCCESS) {
            long id = productService.createProduct(product);
            validator.getResponseStatus().addMessage(MessageFormat.format("A terméket (id: {0}) sikeresen hozzáadta az adatbázishoz.", id));
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
        }
        else {
            return productService.listAllProducts();
        }
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseStatus handleParseException(Exception exception) {
        ResponseStatus status = new ResponseStatus().addMessage("Hibás formátum!");
        status.setStatus(ProductStatus.FAIL);
        return status;
    }
}
