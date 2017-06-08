package br.com.stone.store.presentation.shoppingcart;

import br.com.stone.store.domain.model.Product;
import br.com.stone.store.domain.model.StoreCheckout;
import br.com.stone.store.presentation.base.MvpPresenter;
import br.com.stone.store.presentation.base.MvpView;

/**
 * Created by rrodovalho on 05/06/17.
 */

public interface ShoppingCartContract {

    interface View extends MvpView{
        void updateTotalPrice(String totalPrice);
        void requestPaymentInformation();
        void showEmptyCart();
        void showLoading();
        void showPaymentCompleteSuccessfully();
        void showPaymentFails();
    }

    interface Presenter extends MvpPresenter<View>{
        void obtainShoppingCartTotalPrice();
        void removeProduct(Product product);
        void updateProductQuantity(Product product, int quantity);
        void finishCheckout();
        void sendCheckoutToApproval(StoreCheckout storeCheckout);
    }

    interface OnProductQuantityListener{
        void onQuantitySelected(Product product, int quantity);
        void onRemovedProduct(Product product, int position);
    }

}
