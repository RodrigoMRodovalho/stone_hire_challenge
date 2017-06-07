package br.com.stone.store.domain.shoppingcart;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import br.com.stone.store.domain.model.Product;
import br.com.stone.store.domain.model.ShoppingCartItem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Created by rrodovalho on 07/06/17.
 */
public class ShoppingCartManagerTest {

    IShoppingCartManager iShoppingCartManager;

    @Before
    public void setUp() throws Exception {
        iShoppingCartManager = new ShoppingCartManager();
    }

    @Test
    public void addProduct() throws Exception {

        assertEquals(0,iShoppingCartManager.getTotalItensCount());

        Product product = Product.builder().price("20").build();

        iShoppingCartManager.addProduct(product);

        assertEquals(1,iShoppingCartManager.getTotalItensCount());
        assertEquals("20",iShoppingCartManager.getTotalPrice());

        iShoppingCartManager.addProduct(product);

        assertEquals(1,iShoppingCartManager.getTotalItensCount());
        assertEquals("40",iShoppingCartManager.getTotalPrice());
    }

    @Test
    public void removeProduct() throws Exception {

        assertEquals(0,iShoppingCartManager.getTotalItensCount());
        Product product = Product.builder().price("30").build();
        Product product2 = Product.builder().price("60").build();
        Product product3 = Product.builder().price("90").build();
        iShoppingCartManager.addProduct(product);
        iShoppingCartManager.addProduct(product2);
        iShoppingCartManager.addProduct(product3);

        assertEquals(3,iShoppingCartManager.getTotalItensCount());
        assertEquals("180",iShoppingCartManager.getTotalPrice());

        iShoppingCartManager.removeProduct(product2);

        assertEquals(2,iShoppingCartManager.getTotalItensCount());
        assertEquals("120",iShoppingCartManager.getTotalPrice());
    }

    @Test
    public void getShoppingCart() throws Exception {

        assertEquals(0,iShoppingCartManager.getTotalItensCount());
        Product product = Product.builder().price("30").build();
        Product product2 = Product.builder().price("60").build();
        Product product3 = Product.builder().price("90").build();
        iShoppingCartManager.addProduct(product);
        iShoppingCartManager.addProduct(product2);
        iShoppingCartManager.addProduct(product2);
        iShoppingCartManager.addProduct(product3);
        iShoppingCartManager.addProduct(product3);
        iShoppingCartManager.addProduct(product3);

        Product[] products = {product3,product2,product};
        Integer[] quantities = {3,2,1};

        List<ShoppingCartItem> shoppingCartItemList = iShoppingCartManager.getShoppingCart();

        for (int i=0;i<shoppingCartItemList.size();i++){
            assertEquals(products[i],shoppingCartItemList.get(i).getProduct());
            assertSame(quantities[i],shoppingCartItemList.get(i).getQuantity());
        }

    }

    @Test
    public void updateQuantity() throws Exception {

        assertEquals(0,iShoppingCartManager.getTotalItensCount());
        Product product = Product.builder().price("30").build();
        Product product2 = Product.builder().price("60").build();
        Product product3 = Product.builder().price("90").build();
        iShoppingCartManager.addProduct(product);
        iShoppingCartManager.addProduct(product2);
        iShoppingCartManager.addProduct(product3);

        iShoppingCartManager.updateQuantity(product2,5);
        assertEquals("420",iShoppingCartManager.getTotalPrice());

        iShoppingCartManager.updateQuantity(product3,10);
        assertEquals("1230",iShoppingCartManager.getTotalPrice());

        iShoppingCartManager.updateQuantity(product3,2);
        assertEquals("510",iShoppingCartManager.getTotalPrice());

        iShoppingCartManager.updateQuantity(product2,1);
        assertEquals("270",iShoppingCartManager.getTotalPrice());

    }

    @Test
    public void getTotalItensCount() throws Exception {

        assertEquals(0,iShoppingCartManager.getTotalItensCount());
        Product product = Product.builder().price("30").build();
        Product product2 = Product.builder().price("60").build();
        Product product3 = Product.builder().price("90").build();

        iShoppingCartManager.addProduct(product);
        assertEquals(1,iShoppingCartManager.getTotalItensCount());

        iShoppingCartManager.addProduct(product2);
        assertEquals(2,iShoppingCartManager.getTotalItensCount());

        iShoppingCartManager.addProduct(product3);
        assertEquals(3,iShoppingCartManager.getTotalItensCount());

        iShoppingCartManager.addProduct(product2);
        assertEquals(3,iShoppingCartManager.getTotalItensCount());

        iShoppingCartManager.removeProduct(product);
        assertEquals(2,iShoppingCartManager.getTotalItensCount());

        iShoppingCartManager.removeProduct(product2);
        assertEquals(1,iShoppingCartManager.getTotalItensCount());

        iShoppingCartManager.cleanCart();

        assertEquals(0,iShoppingCartManager.getTotalItensCount());
    }

    @Test
    public void cleanCart() throws Exception {

        assertEquals(0,iShoppingCartManager.getTotalItensCount());
        Product product = Product.builder().price("30").build();
        Product product2 = Product.builder().price("60").build();
        Product product3 = Product.builder().price("90").build();

        iShoppingCartManager.addProduct(product);
        iShoppingCartManager.addProduct(product2);
        iShoppingCartManager.addProduct(product3);

        iShoppingCartManager.cleanCart();

        assertEquals(0,iShoppingCartManager.getTotalItensCount());
        assertEquals("0",iShoppingCartManager.getTotalPrice());

    }

    @Test
    public void getTotalPrice() throws Exception {

        assertEquals(0,iShoppingCartManager.getTotalItensCount());
        Product product = Product.builder().price("30").build();
        Product product2 = Product.builder().price("60").build();
        Product product3 = Product.builder().price("90").build();

        iShoppingCartManager.addProduct(product);
        iShoppingCartManager.addProduct(product2);
        iShoppingCartManager.addProduct(product3);

        assertEquals("180",iShoppingCartManager.getTotalPrice());

        iShoppingCartManager.addProduct(product);
        assertEquals("210",iShoppingCartManager.getTotalPrice());

        iShoppingCartManager.removeProduct(product3);
        assertEquals("120",iShoppingCartManager.getTotalPrice());

        iShoppingCartManager.updateQuantity(product2,10);

        assertEquals("660",iShoppingCartManager.getTotalPrice());

    }

}