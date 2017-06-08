package br.com.stone.store.presentation.catalog;

import java.util.List;

import br.com.stone.store.domain.model.Product;
import br.com.stone.store.domain.model.ShoppingCartItem;
import br.com.stone.store.presentation.base.MvpPresenter;
import br.com.stone.store.presentation.base.MvpView;

/**
 * Created by rrodovalho on 03/06/17.
 */

public interface CatalogContract {

    interface View extends MvpView{
        void showLoading();
        void hideLoading();
        void showCatalog(List<Product> catalog);
        void showError(String errorMessage);
        void showShoppingCartScreen(List<ShoppingCartItem> shoppingCartItems);
        void showTransactionHistoryScreen();
        void updateCartItemCounter(int counter);
    }

    interface Presenter extends MvpPresenter<View>{
        void fetchCatalog();
        void addToCart(Product product);
        void goToShoppingCartScreen();
        void goToTransactionHistoryScreen();
        int getShoppingCartSize();
    }

    interface OnProductSelectionListener{
        void onProductSelected(int position);
    }
}
