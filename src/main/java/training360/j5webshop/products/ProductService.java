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

    public long createProduct(Product product) {
        long id;
        while (true) {
            product.setCodeAndAddress();
            if (codeUnreserved(product) && addressUnreserved(product)) {
                id = productDao.createProduct(product);
                break;
            } else {
                product.incrementPostFix();
            }
        }
        return id;
    }

    private boolean addressUnreserved(Product product) {
        for (Product p : productDao.listAllProducts()) {
            if (p.getAddress().equals(product.getAddress())) {
                return false;
            }
        }
        return true;
    }

    private boolean codeUnreserved(Product product) {
        for (Product p : productDao.listAllProducts()) {
            if (p.getCode().equals(product.getCode())) {
                return false;
            }
        }
        return true;
    }

    public List<Product> listProductsWithLimit(int start, int size) {
        return productDao.listProductsWithLimit(start, size);
    }

    public List<Product> listAllProducts() {
        return productDao.listAllProducts();
    }

    public int getLengthOfProductList() {
        return productDao.getLengthOfProductList();
    }

    public void deleteProductById(long id) {
        productDao.deleteProductById(id);
    }

    public boolean updateProduct(long id, Product product) {
        if (addressUnreserved(product) && codeUnreserved(product)) {
            productDao.updateProduct(id, product);
            return true;
        } else {
            return false;
        }
    }
}
