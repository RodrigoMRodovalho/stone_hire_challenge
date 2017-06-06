package br.com.stone.store.presentation.shoppingcart;

import javax.inject.Inject;

import br.com.stone.store.data.executor.UseCaseHandler;
import br.com.stone.store.domain.base.UseCase;
import br.com.stone.store.domain.model.ProductItem;
import br.com.stone.store.domain.model.StoreCheckout;
import br.com.stone.store.domain.shoppingcart.IShoppingCartManager;
import br.com.stone.store.domain.usecase.FinishCheckoutUseCase;
import br.com.stone.store.presentation.base.BasePresenter;

/**
 * Created by rrodovalho on 05/06/17.
 */

public class ShoppingCartPresenter extends BasePresenter<ShoppingCartContract.View>
        implements ShoppingCartContract.Presenter {

    @Inject
    IShoppingCartManager shoppingCartManager;
    @Inject
    UseCase finishCheckoutUseCase;

    public ShoppingCartPresenter(IShoppingCartManager shoppingCartManager,
                                 UseCase finishCheckoutUseCase) {
        this.shoppingCartManager = shoppingCartManager;
        this.finishCheckoutUseCase = finishCheckoutUseCase;
    }

    @Override
    public void fetchShoppingCart() {

        if (shoppingCartManager.getTotalItensCount() == 0){
            getView().showEmptyCart();
        }
        else{
            getView().showShoppingCart(shoppingCartManager.getShoppingCart());
            getView().updateTotalPrice(shoppingCartManager.getTotalPrice());
        }
    }

    @Override
    public void removeProduct(ProductItem productItem) {
        shoppingCartManager.removeProductItem(productItem);

        if (shoppingCartManager.getTotalItensCount() == 0){
            getView().showEmptyCart();
        }
        else {
            getView().updateTotalPrice(shoppingCartManager.getTotalPrice());
        }
    }

    @Override
    public void updateProductQuantity(ProductItem productItem, int quantity) {
        shoppingCartManager.updateQuantity(productItem,quantity);
        getView().updateTotalPrice(shoppingCartManager.getTotalPrice());
    }

    @Override
    public void finishCheckout() {
        getView().requestPaymentInformation();
    }

    @Override
    public void sendCheckoutToApproval(StoreCheckout storeCheckout) {

        storeCheckout.setValue(shoppingCartManager.getTotalPrice());
        FinishCheckoutUseCase.CheckoutValue checkoutValue =
                new FinishCheckoutUseCase.CheckoutValue();
        checkoutValue.setStoreCheckout(storeCheckout);

        getView().showLoading();

        UseCaseHandler.execute(finishCheckoutUseCase,checkoutValue)
                .subscribe(aVoid -> {
                    getView().hideLoading();
                    getView().showPaymentCompleteSuccessfully();
                },throwable -> {
                    getView().hideLoading();
                    getView().showPaymentFails();
                });
    }
}
