package br.com.stone.store.presentation.shoppingcart;

import br.com.stone.store.domain.model.ProductItem;
import br.com.stone.store.presentation.base.MvpPresenter;
import br.com.stone.store.presentation.base.MvpView;

/**
 * Created by rrodovalho on 05/06/17.
 */

public interface ShoppingCartContract {

    interface View extends MvpView{
        void updateTotalPrice(String totalPrice);
        void requestPaymentInformation();
    }

    interface Presenter extends MvpPresenter<View>{
        void removeProduct(ProductItem productItem);
        void updateProductQuantity(ProductItem productItem, int quantity);
        void finishCheckout();
        void sendCheckoutToApproval();
    }

    interface OnProductQuantityListener{
        void onQuantitySelected(int quantity);
    }

}
