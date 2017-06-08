package br.com.stone.store.presentation.shoppingcart;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.stone.store.data.executor.UseCaseHandler;
import br.com.stone.store.domain.base.UseCase;
import br.com.stone.store.domain.model.Product;
import br.com.stone.store.domain.model.StoreCheckout;
import br.com.stone.store.domain.shoppingcart.IShoppingCartManager;
import br.com.stone.store.domain.usecase.FinishCheckoutUseCase;
import io.reactivex.Observable;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by rrodovalho on 07/06/17.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(UseCaseHandler.class)
public class ShoppingCartPresenterTest {

    ShoppingCartContract.Presenter presenter;

    @Mock
    ShoppingCartContract.View view;

    @Mock
    IShoppingCartManager shoppingCartManager;

    @Mock
    UseCase finishCheckoutUseCase;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new ShoppingCartPresenter(shoppingCartManager,finishCheckoutUseCase);
        presenter.attachView(view);
    }

    @Test
    public void fetchShoppingCartExpectedNonEmptyCart() throws Exception {


        String totalPrice = "12000";

        when(shoppingCartManager.getTotalItensCount()).thenReturn(1);
        when(shoppingCartManager.getTotalPrice()).thenReturn(totalPrice);

        presenter.obtainShoppingCartTotalPrice();

        verify(view).updateTotalPrice(eq(totalPrice));

    }

    @Test
    public void removeProductExpectedAfterEmptyCart() throws Exception {

        Product product = Product.builder().build();
        when(shoppingCartManager.getTotalItensCount()).thenReturn(0);

        presenter.removeProduct(product);

        verify(shoppingCartManager).removeProduct(eq(product));
        verify(view).showEmptyCart();
    }

    @Test
    public void removeProductExpectedAfterNonEmptyCart() throws Exception {

        Product product = Product.builder().build();
        String totalPrice = "1200";
        when(shoppingCartManager.getTotalItensCount()).thenReturn(1);
        when(shoppingCartManager.getTotalPrice()).thenReturn(totalPrice);

        presenter.removeProduct(product);

        verify(shoppingCartManager).removeProduct(eq(product));
        verify(view).updateTotalPrice(eq(totalPrice));
    }

    @Test
    public void updateProductQuantity() throws Exception {

        Product product = Product.builder().build();
        int quantity = 10;
        String totalPrice = "1200";
        when(shoppingCartManager.getTotalPrice()).thenReturn(totalPrice);

        presenter.updateProductQuantity(product,quantity);

        verify(shoppingCartManager).updateQuantity(eq(product),eq(quantity));
        verify(view).updateTotalPrice(eq(totalPrice));
    }

    @Test
    public void finishCheckout() throws Exception {

        presenter.finishCheckout();
        verify(view).requestPaymentInformation();
    }

    @Test
    public void sendCheckoutToApprovalExpectedSuccess() throws Exception {

        testSendCheckoutApprovalRequestWithStatus(Boolean.TRUE,false);
        verify(shoppingCartManager).cleanCart();
        verify(view).showPaymentCompleteSuccessfully();
    }

    @Test
    public void sendCheckoutToApprovalExpectedFailure() throws Exception {
        testSendCheckoutApprovalRequestWithStatus(Boolean.FALSE,false);
        verify(view).showPaymentFails();
    }

    @Test
    public void sendCheckoutToApprovalExpectedFailureByException() throws Exception {
        testSendCheckoutApprovalRequestWithStatus(Boolean.FALSE,true);
        verify(view).showPaymentFails();
    }

    private void testSendCheckoutApprovalRequestWithStatus(
            Boolean success,boolean shouldthrowException){

        String price = "1200";
        when(shoppingCartManager.getTotalPrice()).thenReturn(price);

        StoreCheckout storeCheckout = StoreCheckout.builder().build();

        FinishCheckoutUseCase.CheckoutValue checkoutValue =
                new FinishCheckoutUseCase.CheckoutValue();

        checkoutValue.setStoreCheckout(storeCheckout);

        PowerMockito.mockStatic(UseCaseHandler.class);

        Observable retObservable;

        if (shouldthrowException){
            retObservable = Observable.error(new Throwable("error"));
        }
        else{
            retObservable = Observable.just(success);
        }

        PowerMockito.when(UseCaseHandler.execute(finishCheckoutUseCase,checkoutValue))
                .thenReturn(retObservable);

        presenter.sendCheckoutToApproval(storeCheckout);

        assertEquals(storeCheckout.getValue(),price);

    }

}