package br.com.stone.store.presentation.catalog;

import javax.inject.Inject;

import br.com.stone.store.domain.base.UseCase;
import br.com.stone.store.domain.model.ProductItem;
import br.com.stone.store.presentation.base.BasePresenter;

/**
 * Created by rrodovalho on 03/06/17.
 */

public class CatalogPresenter extends BasePresenter<CatalogContract.View>
        implements CatalogContract.Presenter {

    @Inject
    UseCase fetchCatalogUseCase;

    public CatalogPresenter(UseCase fetchCatalogUseCase) {
        this.fetchCatalogUseCase = fetchCatalogUseCase;
    }

    @Override
    public void fetchCatalog() {

    }

    @Override
    public void addToCart(ProductItem productItem) {

    }

    @Override
    public void goToBaskerScreen() {

    }

    @Override
    public void goToTransactionHistoryScreen() {

    }
}
