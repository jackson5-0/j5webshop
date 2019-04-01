//package training360.j5webshop.order;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.MatcherAssert.assertThat;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.authentication.TestingAuthenticationToken;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit4.SpringRunner;
//import training360.j5webshop.baskets.BasketController;
//import training360.j5webshop.orders.OrderController;
//import training360.j5webshop.orders.OrderStatus;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Sql(scripts = "/init.sql")
//public class OrderIntegrationTest {
//
//    @Autowired
//    private OrderController orderController;
//
//    @Autowired
//    private BasketController basketController;
//
//    @Test
//    public void createOrderTest() {
//        // When
//        int basketSizeBeforeCreateOrder = basketController.basketItemsWithQuantity(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).size();
//        orderController.createOrder(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11"), 2, "Magyarország, 1111 Budapest, Fő utca 11.");
//        int basketSizeAfterCreateOrder = basketController.basketItemsWithQuantity(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).size();
//
//        // Then
//        assertThat(orderController.listAllOrderWithDeleted(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).size(), equalTo(2));
//        assertThat(orderController.listActiveOrder(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).size(), equalTo(1));
//        assertThat(orderController.listAllOrder(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).get(0).getOrderedProduct().size(), equalTo(3));
//        assertThat(basketController.basketItemsWithQuantity(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).size(), equalTo(0));
//        assertThat(basketSizeBeforeCreateOrder, equalTo(3));
//        assertThat(basketSizeAfterCreateOrder, equalTo(0));
//    }
//
//    @Test
//    public void listAllAndListAllOrderWithDeletedOrderTest() {
//        // When
//        orderController.createOrder(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11"), 2, "Magyarország, 1111 Budapest, Fő utca 11.");
//        int sizeOfListWithDeleted = orderController.listAllOrderWithDeleted(new TestingAuthenticationToken("kissbeci", "kissbeci00")).size();
//        int sizeOfListWithoutDeleted = orderController.listAllOrder(new TestingAuthenticationToken("kissbeci", "kissbeci00")).size();
//
//        // Then
//        assertThat(sizeOfListWithDeleted, equalTo(4));
//        assertThat(sizeOfListWithoutDeleted, equalTo(3));
//    }
//
//    @Test
//    public void listActiveOrderTest() {
//        // When
//        orderController.createOrder(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11"), 2, "Magyarország, 1111 Budapest, Fő utca 11.");
//        int sizeOfListWithDeleted = orderController.listAllOrderWithDeleted(new TestingAuthenticationToken("kissbeci", "kissbeci00")).size();
//        int sizeOfActiveList = orderController.listActiveOrder(new TestingAuthenticationToken("kissbeci", "kissbeci00")).size();
//
//        // Then
//        assertThat(sizeOfListWithDeleted, equalTo(4));
//        assertThat(sizeOfActiveList, equalTo(2));
//    }
//
//    @Test
//    public void findOrderedProductByOrderIdTest() {
//        // When
//        orderController.createOrder(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11"), 2, "Magyarország, 1111 Budapest, Fő utca 11.");
//        int numberOfProducts = orderController.findOrderedProductByOrderId(7).size();
//
//        // Then
//        assertThat(numberOfProducts, equalTo(3));
//    }
//
//    @Test
//    public void listAdminOrdersTest() {
//        // When
//        int numberOfOrders = orderController.listAdminOrders().size();
//
//        // Then
//        assertThat(numberOfOrders, equalTo(6));
//    }
//
//    @Test
//    public void listActiveOrdersTest() {
//        // When
//        int numberOfOrders = orderController.listActiveOrders().size();
//
//        // Then
//        assertThat(numberOfOrders, equalTo(3));
//    }
//
//    @Test
//    public void deleteOrdersTest() {
//        // When
//        int numberOfOrdersBeforeDelete = orderController.listActiveOrders().size();
//        orderController.deleteOrders(4);
//        int numberOfOrdersAfterDelete = orderController.listActiveOrders().size();
//
//        // Then
//        assertThat(numberOfOrdersBeforeDelete, equalTo(3));
//        assertThat(numberOfOrdersAfterDelete, equalTo(2));
//    }
//
//    @Test
//    public void deleteItemTest() {
//        // When
//        orderController.createOrder(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11"), 2, "Magyarország, 1111 Budapest, Fő utca 11.");
//        int numberOfProductsBeforeDelete = orderController.listAllOrder(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).get(0).getOrderedProduct().size();
//        orderController.deleteItem(7, "lord-of-hellas");
//        int numberOfProductsAfterDelete = orderController.listAllOrder(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11")).get(0).getOrderedProduct().size();
//
//        // Then
//        assertThat(numberOfProductsBeforeDelete, equalTo(3));
//        assertThat(numberOfProductsAfterDelete, equalTo(2));
//    }
//
//    @Test
//    public void changeStatusByIdTest() {
//        // When
//        orderController.createOrder(new TestingAuthenticationToken("nagygizi22", "GiziAZizi11"), 2, "Magyarország, 1111 Budapest, Fő utca 11.");
//        OrderStatus orderStatusBeforeChange = orderController.listAdminOrders().get(0).getOrderStatus();
//        orderController.changeStatusById(7);
//        OrderStatus orderStatusAfterChange = orderController.listAdminOrders().get(0).getOrderStatus();
//
//        // Then
//        assertThat(orderStatusBeforeChange, equalTo(OrderStatus.ACTIVE));
//        assertThat(orderStatusAfterChange, equalTo(OrderStatus.DELIVERED));
//    }
//
//    @Test
//    public void listLast3ItemsTest(){
//        //When
//        int sizeOfListBefore = orderController.listLast3OrderedItem().size();
//        orderController.deleteOrders(3);
//        orderController.deleteOrders(4);
//        orderController.deleteOrders(5);
//        int sizeOfListAfter = orderController.listLast3OrderedItem().size();
//        //Then
//        assertThat(sizeOfListBefore, equalTo(3));
//        assertThat(sizeOfListAfter,equalTo(1));
//    }
//}
