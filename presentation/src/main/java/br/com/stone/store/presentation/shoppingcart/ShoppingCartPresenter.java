package br.com.stone.store.presentation.shoppingcart;

import br.com.stone.store.domain.model.ProductItem;
import br.com.stone.store.presentation.base.BasePresenter;

/**
 * Created by rrodovalho on 05/06/17.
 */

public class ShoppingCartPresenter extends BasePresenter<ShoppingCartContract.View>
        implements ShoppingCartContract.Presenter {

    @Override
    public void removeProduct(ProductItem productItem) {

    }

    @Override
    public void updateProductQuantity(ProductItem productItem, int quantity) {

    }

    @Override
    public void finishCheckout() {

    }

    @Override
    public void sendCheckoutToApproval() {

    }
}
