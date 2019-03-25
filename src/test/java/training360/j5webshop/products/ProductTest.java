package training360.j5webshop.products;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class ProductTest {

    @Test
    public void testSetCodeAndAddress() {
        Product product = new Product("Eldritch Horror", "Games Inc.", 10_000);
        product.setCodeAndAddress();
        assertThat(product.getAddress(), equalTo("eldritch-horror"));
        assertThat(product.getCode(), equalTo("ELDGAM01"));
    }

    @Test
    public void testSetCodeAndAddressWithAccent() {
        Product product = new Product("Gazdálkodj Okosan", "Játék Inc.", 10_000);
        product.setCodeAndAddress();
        assertThat(product.getAddress(), equalTo("gazdalkodj-okosan"));
        assertThat(product.getCode(), equalTo("GAZJAT01"));
    }

}
