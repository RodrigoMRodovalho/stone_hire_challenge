package br.com.stone.store.domain.basket;

import java.util.List;

import br.com.stone.store.domain.model.ProductItem;

/**
 * Created by rrodovalho on 04/06/17.
 */

public interface IBasketManager {

    void addProductItem(ProductItem productItem);
    void removeProductItem(ProductItem productItem);
    List<ProductItem> getBasket();
    void plusQuantity(ProductItem productItem,int quantity);
    void minusQuantity(ProductItem productItem, int quantity);
    int getTotalItensCount();
    void cleanBasket();
}
