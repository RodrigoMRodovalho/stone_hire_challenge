package br.com.stone.store.domain.shoppingcart;

import java.util.Map;

import br.com.stone.store.domain.model.ProductItem;

/**
 * Created by rrodovalho on 04/06/17.
 */

public interface IShoppingCartManager {

    void addProductItem(ProductItem productItem);
    void removeProductItem(ProductItem productItem);
    Map<ProductItem,Integer> getShoppingCart();
    void updateQuantity(ProductItem productItem,int quantity);
    int getTotalItensCount();
    void cleanCart();
    String getTotalPrice();
}
