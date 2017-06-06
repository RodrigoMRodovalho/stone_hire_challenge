package br.com.stone.store.presentation.shoppingcart;

import java.util.Map;

import br.com.stone.store.domain.model.ProductItem;
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
        void showShoppingCart(Map<ProductItem,Integer> shoppingCart);
        void showEmptyCart();
        void showLoading();
        void hideLoading();
        void showPaymentCompleteSuccessfully();
        void showPaymentFails();
    }

    interface Presenter extends MvpPresenter<View>{
        void fetchShoppingCart();
        void removeProduct(ProductItem productItem);
        void updateProductQuantity(ProductItem productItem, int quantity);
        void finishCheckout();
        void sendCheckoutToApproval(StoreCheckout storeCheckout);
    }

    interface OnProductQuantityListener{
        void onQuantitySelected(ProductItem productItem,int quantity);
        void onRemovedProduct(ProductItem productItem,int position);
    }

}
