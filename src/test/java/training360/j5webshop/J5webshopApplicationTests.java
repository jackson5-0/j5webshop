package training360.j5webshop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import training360.j5webshop.baskets.BasketController;
import training360.j5webshop.baskets.BasketDao;
import training360.j5webshop.orders.OrderController;
import training360.j5webshop.products.Product;
import training360.j5webshop.products.ProductController;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class J5webshopApplicationTests {

	@Autowired
	ProductController productController = new ProductController();

	@Autowired
	OrderController orderController = new OrderController();

	@Autowired
	BasketController basketController = new BasketController();

	@Test
	public void contextLoads() {
		productController.createProduct(new Product("Lord of Hellas","Avaken Realms", 35990));
		productController.createProduct(new Product("Trónok harca", "Delta Vision", 17990));
		productController.createProduct(new Product("Hacker játszma", "Gém Klub Kft.", 3190));

		List<Product> products = productController.listProducts(0,2);

        assertEquals(2, products.size());
        assertEquals("Hacker játszma", products.get(0).getName());
	}

    @Test
    public void testDeleteProduct() {
        // Given
        productController.createProduct(new Product("Lord of Hellas","Avaken Realms", 35990));
		productController.createProduct(new Product("Trónok harca", "Delta Vision", 17990));
		productController.createProduct(new Product("Hacker játszma", "Gém Klub Kft.", 3190));

        // When
        List<Product> products = productController.listProducts(0,3);
        Product product = products.stream().filter(p -> p.getName().equals("Lord of Hellas")).findFirst().get();
        long id = product.getId();
        productController.deleteProductById(id);
        products = productController.listProducts(0,3);

        // Then
        assertEquals(2, products.size());
        assertEquals("Trónok harca", products.get(1).getName());
    }

	@Test
	public void testUpdateProduct() {
		// Given
		productController.createProduct(new Product("Hacker játszma", "Gém Klub Kft.", 3190));

		// When
		long id = productController.listProducts(0,1).get(0).getId();
		Product product = new Product("Hacker játszma", "Gémer Kft.", 3190);
		productController.updateProduct(id, product);
		List<Product> products = productController.listProducts(0,1);

		// Then
		assertEquals(1, products.size());
		assertEquals("Gémer Kft.", products.get(0).getPublisher());
	}

//	@Test
//	public void testDeleteItemFromBasket() {
//		// Given
//		productController.createProduct(new Product("Lord of Hellas","Avaken Realms", 35990));
//		productController.createProduct(new Product("Trónok harca", "Delta Vision", 17990));
//		productController.createProduct(new Product("Hacker játszma", "Gém Klub Kft.", 3190));
//
//		// When
//		long id1 = productController.listProducts(0,0).get(0).getId();
//		long id2 = productController.listProducts(0,0).get(1).getId();
//		long id3 = productController.listProducts(0,0).get(2).getId();
//
//		// Then
//
//	}
}
