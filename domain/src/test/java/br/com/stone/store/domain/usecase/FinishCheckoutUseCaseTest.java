package br.com.stone.store.domain.usecase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.stone.store.domain.base.UseCase;
import br.com.stone.store.domain.model.StoreCheckout;
import br.com.stone.store.domain.repository.StoreRepository;
import io.reactivex.Observable;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by rrodovalho on 07/06/17.
 */
public class FinishCheckoutUseCaseTest {

    UseCase finishCheckoutUseCase;

    @Mock
    StoreRepository.Repo repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        finishCheckoutUseCase = new FinishCheckoutUseCase(repository);
    }

    @Test
    public void executeUseCase() throws Exception {

        //// FIXME: 07/06/17 
        StoreCheckout storeCheckout = StoreCheckout.builder().build();
        FinishCheckoutUseCase.CheckoutValue checkoutValue = new FinishCheckoutUseCase.CheckoutValue();
        checkoutValue.setStoreCheckout(storeCheckout);

        Observable<Boolean> expectedObservable = Observable.just(Boolean.TRUE);
        when(repository.confirmCheckout(storeCheckout)).thenReturn(Observable.just(Boolean.TRUE));
//        when(repository.saveTransaction(storeCheckout)).thenReturn(Observable.<Void>just());

        Observable observable = finishCheckoutUseCase.executeUseCase(checkoutValue);

        assertEquals(checkoutValue.getStoreCheckout(),storeCheckout);

        verify(repository).saveTransaction(eq(storeCheckout));

        assertEquals(observable,expectedObservable);
    }



}