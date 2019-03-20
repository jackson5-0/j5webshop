package training360.j5webshop.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{address}")
    public Product findProductById(@PathVariable String address) {
        return productService.findProductByAddress(address);

    }
    @PostMapping("/api/products")
    public void createProduct(@RequestBody Product product){
        productService.createProduct(product.getCode(),product.getName(),product.getAddress(),product.getPublisher()
        ,product.getPrice());
    }
}
