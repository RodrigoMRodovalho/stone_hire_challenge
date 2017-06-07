package br.com.stone.store.presentation.catalog;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import br.com.stone.store.data.executor.UseCaseHandler;
import br.com.stone.store.domain.base.UseCase;
import br.com.stone.store.domain.model.Product;
import br.com.stone.store.domain.shoppingcart.IShoppingCartManager;
import io.reactivex.Observable;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by rrodovalho on 04/06/17.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(UseCaseHandler.class)
public class CatalogPresenterTest {

    @Mock
    CatalogContract.View view;

    @Mock
    UseCase fetchCatalogUseCase;

    @Mock
    IShoppingCartManager shoppingCartManager;

    CatalogContract.Presenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new CatalogPresenter(fetchCatalogUseCase, shoppingCartManager);
        presenter.attachView(view);
    }

    @Test
    public void fetchCatalogExpectedSuccess() throws Exception {

        List<Product> productList = new ArrayList<>();

        PowerMockito.mockStatic(UseCaseHandler.class);

        when(UseCaseHandler.execute(fetchCatalogUseCase,null))
                .thenReturn(Observable.just(productList));

        presenter.fetchCatalog();

        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view).showCatalog(eq(productList));
    }

    @Test
    public void fetchCatalogExpectedFailure() throws Exception {

        String trowableMessage = "message";

        PowerMockito.mockStatic(UseCaseHandler.class);

        when(UseCaseHandler.execute(fetchCatalogUseCase,null))
                .thenReturn(Observable.error(new Throwable(trowableMessage)));

        presenter.fetchCatalog();

        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view).showError(eq("java.lang.Throwable: ".concat(trowableMessage)));
    }

    @Test
    public void addToCart() throws Exception {

        int cartSize = 1;
        Product product = null;

        when(shoppingCartManager.getTotalItensCount()).thenReturn(cartSize);

        presenter.addToCart(product);

        verify(shoppingCartManager).addProductItem(eq(product));
        verify(view).updateCartItemCounter(eq(cartSize));
    }

    @Test
    public void goToShoppingCartScreen() throws Exception {
        presenter.goToShoppingCartScreen();
        verify(view).showShoppingCartScreen();
    }

    @Test
    public void goToTransactionHistoryScreen() throws Exception {
        presenter.goToTransactionHistoryScreen();
        verify(view).showTransactionHistoryScreen();
    }

    @Test
    public void getBasketSize() throws Exception {

        int cartSize = 5;

        when(shoppingCartManager.getTotalItensCount()).thenReturn(cartSize);
        int s = presenter.getBasketSize();

        assertEquals(cartSize,s);
    }

}