package br.com.stone.store.domain.shoppingcart;

import java.util.List;

import br.com.stone.store.domain.model.Product;
import br.com.stone.store.domain.model.ShoppingCartItem;

/**
 * Created by rrodovalho on 04/06/17.
 */

public interface IShoppingCartManager {

    void addProductItem(Product product);
    void removeProductItem(Product product);
    List<ShoppingCartItem> getShoppingCart();
    void updateQuantity(Product product, int quantity);
    int getTotalItensCount();
    void cleanCart();
    String getTotalPrice();
}
