package training360.j5webshop.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        productService.createProduct(product);
    }

    @GetMapping("/products/withlimit?start={start}&size={size}")
    public List<Product> listProductsWithLimit(@PathVariable int start, @PathVariable int size) {
        System.out.println(start + " " +size);
        return productService.listProductsWithLimit(start, size);
    }

    @GetMapping("/products/count")
    public int getLengthOfProductList() {
        return productService.getLengthOfProductList();
    }

    @GetMapping("/products")
    public List<Product> listAllProducts() {
        return productService.listAllProducts();
    }
}
