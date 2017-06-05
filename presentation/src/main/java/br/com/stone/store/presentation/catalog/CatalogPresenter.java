package br.com.stone.store.presentation.catalog;

import java.util.List;

import javax.inject.Inject;

import br.com.stone.store.data.executor.UseCaseHandler;
import br.com.stone.store.domain.base.UseCase;
import br.com.stone.store.domain.basket.IBasketManager;
import br.com.stone.store.domain.model.ProductItem;
import br.com.stone.store.presentation.base.BasePresenter;

/**
 * Created by rrodovalho on 03/06/17.
 */

public class CatalogPresenter extends BasePresenter<CatalogContract.View>
        implements CatalogContract.Presenter {

    @Inject
    UseCase fetchCatalogUseCase;
    @Inject
    IBasketManager basketManager;

    public CatalogPresenter(UseCase fetchCatalogUseCase, IBasketManager basketManager) {
        this.fetchCatalogUseCase = fetchCatalogUseCase;
        this.basketManager = basketManager;
    }

    @Override
    public void fetchCatalog() {

        getView().showLoading();

        UseCaseHandler.execute(fetchCatalogUseCase,null)
                .subscribe(
                        productItemList -> {
                            getView().hideLoading();
                            getView().showCatalog((List<ProductItem>) productItemList);
                        },
                        throwable -> {
                            getView().hideLoading();
                            getView().showError(throwable.toString());
                        }
                );
    }

    @Override
    public void addToCart(ProductItem productItem) {
        basketManager.addProductItem(productItem);
        getView().updateCartItemCounter(basketManager.getTotalItensCount());
    }

    @Override
    public void goToBaskerScreen() {
        getView().showBasketScreen(basketManager.getBasket());
    }

    @Override
    public void goToTransactionHistoryScreen() {
        getView().showTransactionHistoryScreen();
    }

    @Override
    public int getBasketSize() {
        return basketManager.getTotalItensCount();
    }
}
