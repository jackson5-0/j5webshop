package training360.j5webshop.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public ProductContainer findProductByAddress(String address) {
        return productDao.findProductByAddress(address);
    }

    public long createProduct(Product product) {
        while (true) {
            product.setCodeAndAddress();
            if (codeUnreserved(product) && addressUnreserved(product)) {
                long id =  productDao.createProduct(product);
                product.setId(id);
                createProductCategoryEntry(product);
                return id;
            } else if(!codeUnreserved(product)){
                product.incrementCodePostFix();
            } else if (!addressUnreserved(product)){
                product.incrementAddressPostFix();
            }
        }
    }

    private void createProductCategoryEntry(Product product) {
        for (Category category: product.getCategories()) {
            productDao.createProductCategoryEntry(product.getId(), category.getId());
        }
    }

    private boolean addressUnreserved(Product product) {
        for (Product p : productDao.listAllProducts()) {
            if (p.getAddress().equals(product.getAddress()) && product.getId() != p.getId()) {
                return false;
            }
        }
        return true;
    }

    private boolean codeUnreserved(Product product) {
        for (Product p : productDao.listAllProducts()) {
            if (p.getCode().equals(product.getCode()) && product.getId() != p.getId()) {
                return false;
            }
        }
        return true;
    }

    public List<Category> listProductsWithLimit(int start, int size) {
        List<Category> productsByCategory = productDao.listCategories();
        for (Category category: productsByCategory) {
            category.setProducts(productDao.listProductsWithLimit(start, size, category.getName()));
        }
        return productsByCategory;
    }

    public List<Product> listProductsWithLimitAdmin(int start, int size) {
        return productDao.listProductsWithLimitAdmin(start, size);
    }

    public List<Product> listAllProducts() {
        return productDao.listAllProducts();
    }

    public Integer getLengthOfProductList() {
        return productDao.getLengthOfProductList();
    }

    public void deleteProductById(long id) {
        productDao.deleteProductById(id);
    }

    public boolean updateProduct(Product product) {
        if (addressUnreserved(product) && codeUnreserved(product)) {
            productDao.updateProduct(product);
            createProductCategoryEntry(product);
            return true;
        } else {
            return false;
        }
    }

}
