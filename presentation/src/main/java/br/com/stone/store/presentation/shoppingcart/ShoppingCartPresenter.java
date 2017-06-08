package br.com.stone.store.presentation.shoppingcart;

import javax.inject.Inject;

import br.com.stone.store.data.executor.UseCaseHandler;
import br.com.stone.store.domain.base.UseCase;
import br.com.stone.store.domain.model.Product;
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
    public void obtainShoppingCartTotalPrice() {
        getView().updateTotalPrice(shoppingCartManager.getTotalPrice());
    }

    @Override
    public void removeProduct(Product product) {
        shoppingCartManager.removeProduct(product);

        if (shoppingCartManager.getTotalItensCount() == 0){
            getView().showEmptyCart();
        }
        else {
            getView().updateTotalPrice(shoppingCartManager.getTotalPrice());
        }
    }

    @Override
    public void updateProductQuantity(Product product, int quantity) {
        shoppingCartManager.updateQuantity(product,quantity);
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
                .subscribe(aBoolean -> {
                            if ((boolean)aBoolean){
                                shoppingCartManager.cleanCart();
                                getView().showPaymentCompleteSuccessfully();
                            }
                            else{
                                getView().showPaymentFails();
                            }
                        }
                        ,throwable -> {
                            getView().showPaymentFails();
                        });
    }
}
