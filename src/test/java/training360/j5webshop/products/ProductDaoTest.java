package training360.j5webshop.products;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class ProductDaoTest {

    @Autowired
    private ProductDao productDao;

//    @Test
//    public void testListProductsWithLimit() {
//        // When
//        List<Product> list = productDao.listProductsWithLimit(1, 2);
//        // Then
//        assertThat(list.size(), equalTo(2));
//        assertThat(list.get(0), equalTo(new Product("GEMHAC01", "Hacker játszma", "hacker-jatszma", "Gém Klub Kft.", 3190)));
//        assertThat(list.get(1), equalTo(new Product("AVALOR01", "Lord of Hellas", "lord-of-hellas", "Avaken Realms", 35990)));
//    }

    @Test
    public void testListAllProducts() {
        //When
        List<Product> list = productDao.listAllProducts();
        //Then
        assertThat(list.size(), equalTo(4));
        assertThat(list.get(0), equalTo(new Product("GEMDIX01", "Dixit", "dixit", "Gém Klub Kft.", 7990)));
        assertThat(list.get(3), equalTo(new Product("DELTRO01", "Trónok harca", "tronok-harca", "Delta Vision", 17990)));
    }

    @Test
    public void testGetLengthOfProductList() {
        //When
        int num = productDao.getLengthOfProductList();
        //Then
        assertThat(num, equalTo(4));
    }

//    @Test
//    public void testCreateProduct() {
//        // Given
//        Product product = new Product("name", "publisher", 10_000);
//        // When
//        long id = productDao.createProduct(product);
//        // Then
//        assertThat(productDao.getLengthOfProductList(), equalTo(5));
//        assertThat(productDao.findProductById(id), equalTo(product));
//    }

    @Test
    public void testFindProductByAddress() {
        //When
        Product product = productDao.findProductByAddress("hacker-jatszma").getProduct();
        //Then
        assertThat(product, equalTo(new Product("Hacker játszma", "Gém Klub Kft.", 3190)));
        assertThat(product.getStatus(), equalTo(ProductStatus.ACTIVE));
    }

    @Test
    public void testFindProductByAddressNotFound() {
        //When
        ProductContainer product = productDao.findProductByAddress("not valid");
        //Then
        assertNull(product.getProduct());
        assertThat(product.getMessage(), equalTo("A keresett termék nem található!"));
    }

    @Test
    public void testFindProductById() {
        // Given
        Product product = new Product(1, "GEMHAC01", "Hacker játszma", "hacker-jatszma", "Gém Klub Kft.", 3190, "ACTIVE");;
        // Then
        assertThat(productDao.findProductById(1), equalTo(product));
    }

//    @Test
//    public void testUpdateProduct() {
//        // Given
//        Product updated = new Product("nameUpdated", "publisherUpdated", 20_000);
//        // When
//        productDao.updateProduct(1, updated);
//        // Then
//        assertThat(productDao.findProductById(1), equalTo(updated));
//    }

    @Test
    public void testDeleteProductById() {
        // When
        productDao.deleteProductById(1);
        // Then
        assertThat(productDao.findProductById(1).getStatus(), equalTo(ProductStatus.DELETED));
        assertThat(productDao.listAllProducts().size(), equalTo(4));
    }


}