package training360.j5webshop.products;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class ProductDaoTest {

    private ProductDao productDao;

    @Before
    public void init() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/j5webshoptest?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        dataSource.setUser("j5webshop");
        dataSource.setPassword("jacksonfive");

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

        productDao = new ProductDao(dataSource);
    }

    @Test
    public void testFindByAddress() {
        //Given
        productDao.createProduct(new Product("GEMHAC01", "Hacker játszma", "hacker-jatszma", "Gém Klub Kft.", 3190));
        //When
        Product product = productDao.findProductByAddress("hacker-jatszma");
        //Then
        assertThat(product.getName(), equalTo("Hacker játszma"));
        assertThat(product.getCode(), equalTo("GEMHAC01"));
        assertThat(product.getPrice(), equalTo(3190));
    }

    @Test
    public void testListAllProducts() {
        //Given
        productDao.createProduct(new Product("GEMHAC01", "Hacker játszma", "hacker-jatszma", "Gém Klub Kft.", 3190));
        productDao.createProduct(new Product("GEMDIX01", "Dixit", "dixit", "Gém Klub Kft.", 7990));
        //When
        List<Product> list = productDao.listAllProducts();
        //Then
        assertThat(list.size(), equalTo(2));
        assertThat(list.get(0).getName(), equalTo("Dixit"));
        assertThat(list.get(1).getAddress(), equalTo("hacker-jatszma"));
    }

    @Test
    public void testGetLengthOfProductList() {
        //Given
        productDao.createProduct(new Product("GEMHAC01", "Hacker játszma", "hacker-jatszma", "Gém Klub Kft.", 3190));
        productDao.createProduct(new Product("GEMDIX01", "Dixit", "dixit", "Gém Klub Kft.", 7990));
        //When
        int num = productDao.getLengthOfProductList();
        //Then
        assertThat(num, equalTo(2));
    }

    @Test
    public void testListProductsWithLimit() {
        //Given
        productDao.createProduct(new Product("AVALOR01", "Lord of Hellas", "lord-of-hellas", "Avaken Realms", 35990));
        productDao.createProduct(new Product("DELTRO01", "Trónok harca", "tronok-harca", "Delta Vision", 17990));
        productDao.createProduct(new Product("GEMHAC01", "Hacker játszma", "hacker-jatszma", "Gém Klub Kft.", 3190));
        productDao.createProduct(new Product("GEMDIX01", "Dixit", "dixit", "Gém Klub Kft.", 7990));
        //When
        List<Product> list = productDao.listProductsWithLimit(0,2);
        //Then
        assertThat(list.size(), equalTo(2));
        assertThat(list.get(0).getName(), equalTo("Dixit"));
        assertThat(list.get(1).getName(), equalTo("Hacker játszma"));
    }

    @Test
    public void testUpdateProduct() {
        //Given
        productDao.createProduct(new Product("AVALOR01", "Lord of Hellas", "lord-of-hellas", "Avaken Realms", 35990));
        long id = productDao.createProduct(new Product("DELTRO01", "Trónok harca", "tronok-harca", "Delta Vision", 17990));
        productDao.createProduct(new Product("GEMHAC01", "Hacker játszma", "hacker-jatszma", "Gém Klub Kft.", 3190));
        //When
        Product modifiedProduct = new Product("Trónok harca II.", "Hasbro", 25900);
        productDao.updateProduct(id, modifiedProduct);
        //Then
        assertThat(productDao.findProductById(id).getName(), equalTo("Trónok harca II."));
        assertThat(productDao.findProductById(id).getPublisher(), equalTo("Hasbro"));
        assertThat(productDao.findProductById(id).getPrice(), equalTo(25900));
    }

    @Test
    public void testDeleteProduct() {
        //Given
        productDao.createProduct(new Product("DELTRO01", "Trónok harca", "tronok-harca", "Delta Vision", 17990));
        long id = productDao.createProduct(new Product("GEMHAC01", "Hacker játszma", "hacker-jatszma", "Gém Klub Kft.", 3190));
        productDao.createProduct(new Product("GEMDIX01", "Dixit", "dixit", "Gém Klub Kft.", 7990));
        //When
        int sizeBeforeDelete = productDao.getLengthOfProductList();
        productDao.deleteProductById(id);
        int sizeAfterDelete = productDao.getLengthOfProductList();
        //Then
        assertThat(sizeBeforeDelete, equalTo(3));
        assertThat(sizeAfterDelete, equalTo(2));
        assertThat(productDao.listProductsWithLimit(0,3).get(0).getCode(), equalTo("GEMDIX01"));
        assertThat(productDao.listProductsWithLimit(0, 3).get(1).getCode(), equalTo("DELTRO01"));
    }
}
