package training360.j5webshop.products;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import training360.j5webshop.validation.ResponseStatus;
import training360.j5webshop.validation.ValidationStatus;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class ProductIntegrationTest {

    @Autowired
    ProductController productController;

    @Test
    public void testListProducts() {
        // When
        List<Product> productList = productController.listProducts(1, 2);
        // Then
        assertThat(productList.size(), equalTo(2));
        assertThat(productList.get(0), equalTo(new Product("GEMHAC01", "Hacker játszma", "hacker-jatszma", "Gém Klub Kft.", 3190)));
        assertThat(productList.get(1), equalTo(new Product("AVALOR01", "Lord of Hellas", "lord-of-hellas", "Avaken Realms", 35990)));
    }

    @Test
    public void testFindProductByAddress() {
        //When
        Product product = productController.findProductByAddress("hacker-jatszma").getProduct();
        //Then
        assertThat(product, equalTo(new Product("Hacker játszma", "Gém Klub Kft.", 3190)));
        assertThat(product.getStatus(), equalTo(ProductStatus.ACTIVE));
    }

    @Test
    public void testFindProductByAddressNotFound() {
        //When
        ProductContainer product = productController.findProductByAddress("not valid");
        //Then
        assertNull(product.getProduct());
        assertThat(product.getMessage(), equalTo("A keresett termék nem található!"));
    }

    @Test
    public void testCreateProduct() {
        // Given
        Product product = new Product("Arkham Horror", "Games Inc.", 10_000);
        // When
        ResponseStatus status = productController.createProduct(product);
        long id = productController.findProductByAddress("arkham-horror").getProduct().getId();
        // Then
        assertThat(status.getStatus(), equalTo(ValidationStatus.SUCCESS));
        assertThat(status.getMessages().size(), equalTo(1));
        assertThat(status.getMessages().get(0),
                equalTo("A terméket (id: " + id + ") sikeresen hozzáadta az adatbázishoz."));
    }

    @Test
    public void testCreateProductWithAccent() {
        // Given
        Product product = new Product("Gazdálkodj Okosan", "Játék Inc.", 10_000);
        // When
        ResponseStatus status = productController.createProduct(product);
        long id = productController.findProductByAddress("gazdalkodj-okosan").getProduct().getId();
        // Then
        assertThat(status.getStatus(), equalTo(ValidationStatus.SUCCESS));
        assertThat(status.getMessages().size(), equalTo(1));
        assertThat(status.getMessages().get(0),
                equalTo("A terméket (id: " + id + ") sikeresen hozzáadta az adatbázishoz."));
    }

    @Test
    public void testCreateProductWithSameDetails() {
        // Given
        Product product = new Product("Arkham Horror", "Games Inc.", 10_000);
        Product product1 = new Product("Arkham Horror", "Games Inc.", 10_000);
        // When
        ResponseStatus status = productController.createProduct(product);
        ResponseStatus status1 = productController.createProduct(product1);
        Product createdProduct = productController.findProductByAddress("arkham-horror").getProduct();
        Product createdProduct1 = productController.findProductByAddress("arkham-horror02").getProduct();
        // Then
        assertThat(status.getStatus(), equalTo(ValidationStatus.SUCCESS));
        assertThat(status1.getStatus(), equalTo(ValidationStatus.SUCCESS));
        assertThat(createdProduct.getCode(), equalTo("ARKGAM01"));
        assertThat(createdProduct1.getCode(), equalTo("ARKGAM02"));
        assertThat(status.getMessages().size(), equalTo(1));
        assertThat(status1.getMessages().size(), equalTo(1));
        assertThat(status.getMessages().get(0),
                equalTo("A terméket (id: " + createdProduct.getId() + ") sikeresen hozzáadta az adatbázishoz."));
        assertThat(status1.getMessages().get(0),
                equalTo("A terméket (id: " + createdProduct1.getId() + ") sikeresen hozzáadta az adatbázishoz."));
    }

    @Test
    public void testCreateWithWrongData() {
        // Given
        Product product = new Product("Ar", "Ga", 0);
        // When
        ResponseStatus status = productController.createProduct(product);
        // Then
        assertThat(status.getMessages().size(), equalTo(3));
        assertThat(status.getMessages().get(0), equalTo("A név nem lehet rövidebb 3 karakternél!"));
        assertThat(status.getMessages().get(1), equalTo("A kiadó nem lehet rövidebb 3 karakternél!"));
        assertThat(status.getMessages().get(2), equalTo("Az ár nem lehet nulla, vagy negatív szám!"));
    }

    @Test
    public void testUpdateProduct() {
        // Before
        Product product = productController.findProductByAddress("hacker-jatszma").getProduct();
        Product updated = new Product("Monopoly", "Test Company", 20_000);
        // When
        productController.updateProduct(product.getId(), updated);
        // Then
        assertThat(productController.findProductByAddress("hacker-jatszma").getProduct(), equalTo(updated));
    }

    @Test
    public void testDeleteProductById() {
        Product product = productController.findProductByAddress("hacker-jatszma").getProduct();
        productController.deleteProductById(product.getId());
        Product deleted = productController.findProductByAddress("hacker-jatszma").getProduct();
        assertThat(deleted.getStatus(), equalTo(ProductStatus.DELETED));
    }

    @Test
    public void testGetLengthOfProductList() {
        // When
        long length = productController.getLengthOfProductList();
        // Then
        assertThat(length, equalTo(4L));
    }

}
