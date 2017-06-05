package br.com.stone.store.domain.basket;

import java.util.List;

import br.com.stone.store.domain.model.ProductItem;

/**
 * Created by rrodovalho on 04/06/17.
 */

public class BasketManager implements IBasketManager {

    @Override
    public void addProductItem(ProductItem productItem) {

    }

    @Override
    public void removeProductItem(ProductItem productItem) {

    }

    @Override
    public List<ProductItem> getBasket() {
        return null;
    }

    @Override
    public void plusQuantity(ProductItem productItem, int quantity) {

    }

    @Override
    public void minusQuantity(ProductItem productItem, int quantity) {

    }

    @Override
    public int getTotalItensCount() {
        return 0;
    }

    @Override
    public void cleanBasket() {

    }
}
