package br.com.stone.store.presentation.catalog;

import java.util.List;

import javax.inject.Inject;

import br.com.stone.store.data.executor.UseCaseHandler;
import br.com.stone.store.domain.base.UseCase;
import br.com.stone.store.domain.shoppingcart.IShoppingCartManager;
import br.com.stone.store.domain.model.Product;
import br.com.stone.store.presentation.base.BasePresenter;
import br.com.stone.store.presentation.util.EspressoIdlingResource;

/**
 * Created by rrodovalho on 03/06/17.
 */

public class CatalogPresenter extends BasePresenter<CatalogContract.View>
        implements CatalogContract.Presenter {

    @Inject
    UseCase fetchCatalogUseCase;
    @Inject
    IShoppingCartManager shoppingCartManager;

    public CatalogPresenter(UseCase fetchCatalogUseCase, IShoppingCartManager shoppingCartManager) {
        this.fetchCatalogUseCase = fetchCatalogUseCase;
        this.shoppingCartManager = shoppingCartManager;
    }

    @Override
    public void fetchCatalog() {

        getView().showLoading();

        EspressoIdlingResource.increment();

        UseCaseHandler.execute(fetchCatalogUseCase,null)
                .subscribe(
                        productItemList -> {
                            getView().hideLoading();
                            getView().showCatalog((List<Product>) productItemList);
                            EspressoIdlingResource.decrement();
                        },
                        throwable -> {
                            getView().hideLoading();
                            getView().showError(throwable.toString());
                            EspressoIdlingResource.decrement();
                        }
                );
    }

    @Override
    public void addToCart(Product product) {
        shoppingCartManager.addProduct(product);
        getView().updateCartItemCounter(shoppingCartManager.getTotalItensCount());
    }

    @Override
    public void goToShoppingCartScreen() {
        getView().showShoppingCartScreen(shoppingCartManager.getShoppingCart());
    }

    @Override
    public void goToTransactionHistoryScreen() {
        getView().showTransactionHistoryScreen();
    }

    @Override
    public int getShoppingCartSize() {
        return shoppingCartManager.getTotalItensCount();
    }
}
