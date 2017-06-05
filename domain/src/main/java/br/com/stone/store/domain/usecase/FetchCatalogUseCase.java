package br.com.stone.store.domain.usecase;

import java.util.List;

import javax.inject.Inject;

import br.com.stone.store.domain.base.UseCase;
import br.com.stone.store.domain.model.ProductItem;
import br.com.stone.store.domain.repository.StoreRepository;
import io.reactivex.Observable;

/**
 * Created by rrodovalho on 04/06/17.
 */

public class FetchCatalogUseCase extends UseCase<UseCase.RequestValues,List<ProductItem>> {

    @Inject
    StoreRepository.Repo repository;

    public FetchCatalogUseCase(StoreRepository.Repo repository) {
        this.repository = repository;
    }

    @Override
    protected Observable<List<ProductItem>> executeUseCase(RequestValues requestValues) {
        return repository.getStoreProducts();
    }
}
