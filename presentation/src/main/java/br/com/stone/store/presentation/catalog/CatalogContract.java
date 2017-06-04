package br.com.stone.store.presentation.catalog;

import java.util.List;

import br.com.stone.store.domain.model.ProductItem;
import br.com.stone.store.presentation.base.MvpPresenter;
import br.com.stone.store.presentation.base.MvpView;

/**
 * Created by rrodovalho on 03/06/17.
 */

public interface CatalogContract {

    interface View extends MvpView{
        void showLoading();
        void hideLoading();
        void showCatalog(List<ProductItem> catalog);
        void showError(String errorMessage);
        void showBasketScreen();
        void showTransactionHistoryScreen();
    }

    interface Presenter extends MvpPresenter<View>{
        void fetchCatalog();
        void addToCart(ProductItem productItem);
        void goToBaskerScreen(List<ProductItem> selectedItems);
        void goToTransactionHistoryScreen();
    }

    interface OnProductSelectionListener{
        void onProductSelected(int position);
    }
}
