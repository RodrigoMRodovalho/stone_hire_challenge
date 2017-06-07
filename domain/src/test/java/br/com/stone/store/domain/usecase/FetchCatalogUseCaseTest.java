package br.com.stone.store.domain.usecase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import br.com.stone.store.domain.base.UseCase;
import br.com.stone.store.domain.model.Product;
import br.com.stone.store.domain.repository.StoreRepository;
import io.reactivex.Observable;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by rrodovalho on 07/06/17.
 */
public class FetchCatalogUseCaseTest {

    UseCase fetchCatalogUseCase;

    @Mock
    StoreRepository.Repo repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        fetchCatalogUseCase = new FetchCatalogUseCase(repository);
    }

    @Test
    public void executeUseCase() throws Exception {

        List<Product> productList = new ArrayList<>();
        Observable<List<Product>> expectedObservable = Observable.just(productList);

        when(repository.getStoreProducts()).thenReturn(expectedObservable);

        Observable observable = fetchCatalogUseCase.executeUseCase(null);

        assertEquals(observable,expectedObservable);
    }

}