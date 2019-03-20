package training360.j5webshop.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import training360.j5webshop.products.validation.ProductStatus;
import training360.j5webshop.products.validation.Validator;
import training360.j5webshop.products.validation.ResponseStatus;

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
            productService.createProduct(product);
            validator.getResponseStatus().addMessage("A termék sikeresen hozzáadva!");
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
}
