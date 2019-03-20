package training360.j5webshop.products;

import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Product findProductByAddress(String address) {
        return productDao.findProductByAddress(address);
    }
}
