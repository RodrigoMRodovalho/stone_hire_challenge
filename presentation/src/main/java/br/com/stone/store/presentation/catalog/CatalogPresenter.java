package br.com.stone.store.presentation.catalog;

import java.util.List;

import br.com.stone.store.domain.model.ProductItem;
import br.com.stone.store.presentation.base.BasePresenter;

/**
 * Created by rrodovalho on 03/06/17.
 */

public class CatalogPresenter extends BasePresenter<CatalogContract.View>
        implements CatalogContract.Presenter {

    @Override
    public void fetchCatalog() {

    }

    @Override
    public void addToCart(ProductItem productItem) {

    }

    @Override
    public void goToBaskerScreen(List<ProductItem> selectedItems) {

    }

    @Override
    public void goToTransactionHistoryScreen() {

    }
}
