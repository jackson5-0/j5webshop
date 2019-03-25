package training360.j5webshop.baskets;

import org.junit.Test;
import training360.j5webshop.products.Product;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class BasketTest {

    @Test
    public void testAddProduct() {
        // Given
        Basket basket = new Basket();
        Product product = new Product("Test", "Test", 10_000);
        Map<Product, Integer> expected = new HashMap<>();
        expected.put(product, 1);
        // When
        basket.addProduct(product, 1);
        // Then
        assertThat(basket.getProducts(), equalTo(expected));
    }

    @Test
    public void testAddMultipleProducts() {
        // Given
        Basket basket = new Basket();
        Product product = new Product("Test", "Test", 10_000);
        Map<Product, Integer> expected = new HashMap<>();
        expected.put(product, 3);
        // When
        basket.addProduct(product, 1);
        basket.addProduct(product, 2);
        // Then
        assertThat(basket.getProducts(), equalTo(expected));
    }

}
