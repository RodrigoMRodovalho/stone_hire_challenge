package br.com.stone.store.presentation.shoppingcart;

import javax.inject.Inject;

import br.com.stone.store.domain.model.ProductItem;
import br.com.stone.store.domain.shoppingcart.IShoppingCartManager;
import br.com.stone.store.presentation.base.BasePresenter;

/**
 * Created by rrodovalho on 05/06/17.
 */

public class ShoppingCartPresenter extends BasePresenter<ShoppingCartContract.View>
        implements ShoppingCartContract.Presenter {

    @Inject
    IShoppingCartManager shoppingCartManager;

    public ShoppingCartPresenter(IShoppingCartManager shoppingCartManager) {
        this.shoppingCartManager = shoppingCartManager;
    }

    @Override
    public void fetchShoppingCart() {
        getView().showShoppingCart(shoppingCartManager.getShoppingCart());
        getView().updateTotalPrice(shoppingCartManager.getTotalPrice());
    }

    @Override
    public void removeProduct(ProductItem productItem) {
        shoppingCartManager.removeProductItem(productItem);
        getView().updateTotalPrice(shoppingCartManager.getTotalPrice());
    }

    @Override
    public void updateProductQuantity(ProductItem productItem, int quantity) {
        shoppingCartManager.updateQuantity(productItem,quantity);
        getView().updateTotalPrice(shoppingCartManager.getTotalPrice());
    }

    @Override
    public void finishCheckout() {

    }

    @Override
    public void sendCheckoutToApproval() {

    }
}
