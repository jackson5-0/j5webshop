//package training360.j5webshop.order;
//
//public class OrderIntegrationTest {
//
//    package training360.j5webshop.products;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.MatcherAssert.assertThat;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit4.SpringRunner;
//import training360.j5webshop.validation.ResponseStatus;
//import training360.j5webshop.validation.ValidationStatus;
//
//import java.util.List;
//
//    @RunWith(SpringRunner.class)
//    @SpringBootTest
//    @Sql(scripts = "/product_init.sql")
//    public class ProductIntegrationTest {
//
//        @Autowired
//        ProductController productController;
//
//        @Test
//        public void testListProducts() {
//            // When
//            List<Product> productList = productController.listProducts(1, 2);
//            // Then
//            assertThat(productList.size(), equalTo(2));
//            assertThat(productList.get(0), equalTo(new Product("GEMHAC01", "Hacker játszma", "hacker-jatszma", "Gém Klub Kft.", 3190)));
//            assertThat(productList.get(1), equalTo(new Product("AVALOR01", "Lord of Hellas", "lord-of-hellas", "Avaken Realms", 35990)));
//        }
//
//        @Test
//        public void testFindProductByAddress() {
//            //When
//            Product product = productController.findProductByAddress("hacker-jatszma");
//            //Then
//            assertThat(product, equalTo(new Product("Hacker játszma", "Gém Klub Kft.", 3190)));
//            assertThat(product.getStatus(), equalTo(ProductStatus.ACTIVE));
//        }
//
//        @Test
//        public void testCreateProduct() {
//            Product product = new Product("Arkham Horror", "Games Inc.", 10_000);
//            ResponseStatus status = productController.createProduct(product);
//            long id = productController.findProductByAddress("arkham-horror").getId();
//            assertThat(status.getStatus(), equalTo(ValidationStatus.SUCCESS));
//            assertThat(status.getMessages().size(), equalTo(1));
//            assertThat(status.getMessages().get(0), equalTo("A terméket (id: " + id + ") sikeresen hozzáadta az adatbázishoz."));
//        }
//
//        public void testCreateProductWithAccent() {
//            Product product = new Product("Gazdálkodj Okosan", "Játék Inc.", 10_000);
//            ResponseStatus status = productController.createProduct(product);
//            long id = productController.findProductByAddress("gazdalkodj-okosan").getId();
//            assertThat(status.getMessages().size(), equalTo(1));
//            assertThat(status.getMessages().get(0), equalTo("A terméket (id: " + id + ") sikeresen hozzáadta az adatbázishoz."));
//        }
//
////    public void testCreateProductWithSameDetails() {
////        Product product = new Product("Arham Horror", "Games Inc.", 10_000);
////        Product product1 = new Product("Gazdálkodj Okosan", "Játék Inc.", 10_000);
////        ResponseStatus status = productController.createProduct(product);
////        ResponseStatus status1 = productController.createProduct(product1);
////        assertThat();
////    }
////
////    public void testUpdateProduct() {
////
////    }
////
////    public void testDeleteProductById() {
////
////    }
////
////    public void testGetLengthOfProductList() {
////
////    }
//
//    }
//}
