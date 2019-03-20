package training360.j5webshop.products;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Product findProductByAddress(String address) {
        return productDao.findProductByAddress(address);
    }
    public void createProduct(String code, String name, String address, String publisher, int price){
        productDao.createProduct(code,name,address,publisher,price);
    };

    public List<Product> listProductsWithLimit(int start, int size) {
        return productDao.listProductsWithLimit(start, size);
    }

    public List<Product> listAllProducts() {
        return productDao.listAllProducts();
    }

    public int getLengthOfProductList() {
        return productDao.getLengthOfProductList();
    }
}
