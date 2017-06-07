package br.com.stone.store.data.repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import br.com.stone.store.domain.model.Product;
import br.com.stone.store.domain.model.StoreCheckout;
import br.com.stone.store.domain.model.Transaction;
import br.com.stone.store.domain.repository.StoreRepository;
import io.reactivex.Observable;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



/**
 * Created by rrodovalho on 07/06/17.
 */
public class RepositoryImplTest {

    StoreRepository.Repo repository;

    @Mock
    StoreRepository.Remote remote;

    @Mock
    StoreRepository.Local local;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        repository = new RepositoryImpl(remote,local);
    }

    @Test
    public void getStoreProducts() throws Exception {

        List<Product> productList = new ArrayList<>();
        io.reactivex.Observable expectedObservable = Observable.just(productList);

        when(remote.getStoreProducts()).thenReturn(expectedObservable);

        Observable observable = repository.getStoreProducts();
        verify(remote).getStoreProducts();

        assertEquals(expectedObservable,observable);
    }

    @Test
    public void confirmCheckout() throws Exception {

        StoreCheckout storeCheckout = StoreCheckout.builder().build();
        Observable<Boolean> expectedObservable = Observable.just(Boolean.TRUE);

        when(remote.confirmCheckout(storeCheckout)).thenReturn(expectedObservable);

        Observable observable = repository.confirmCheckout(storeCheckout);

        verify(remote).confirmCheckout(eq(storeCheckout));

        assertEquals(expectedObservable,observable);

    }

    @Test
    public void saveTransaction() throws Exception {

        StoreCheckout storeCheckout = StoreCheckout.builder().build();
        Observable expectedObservable = Observable.just(Boolean.TRUE);

        when(local.saveTransaction(storeCheckout)).thenReturn(expectedObservable);

        Observable observable = repository.saveTransaction(storeCheckout);

        verify(local).saveTransaction(eq(storeCheckout));

        assertEquals(expectedObservable,observable);

    }

    @Test
    public void getFinalizedTransactions() throws Exception {

        List<Transaction> transactionList = new ArrayList<>();
        Observable expectedObservable = Observable.just(transactionList);

        when(local.getFinalizedTransactions()).thenReturn(expectedObservable);

        Observable observable = repository.getFinalizedTransactions();

        verify(local).getFinalizedTransactions();

        assertEquals(expectedObservable,observable);

    }

}