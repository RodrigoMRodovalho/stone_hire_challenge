package br.com.stone.store.data.repository.remote;

import java.util.List;

import javax.inject.Inject;

import br.com.stone.store.data.model.ModelMapper;
import br.com.stone.store.domain.model.ProductItem;
import br.com.stone.store.domain.model.StoreCheckout;
import br.com.stone.store.domain.repository.StoreRepository;
import io.reactivex.Observable;

/**
 * Created by rrodovalho on 03/06/17.
 */

public class RemoteDataSource implements StoreRepository.Remote{

    @Inject
    StoreRestService storeRestService;
    @Inject
    ModelMapper modelMapper;

    public RemoteDataSource(StoreRestService storeRestService, ModelMapper modelMapper) {
        this.storeRestService = storeRestService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Observable<List<ProductItem>> getStoreProducts() {
        return storeRestService.getStoreItems(StoreRestService.STORE_PRODUCTS_URL)
                .flatMapIterable(storeItems -> storeItems)
                .map(modelMapper::transformProductModel)
                .toList()
                .toObservable();
    }

    @Override
    public Observable<Void> confirmCheckout(StoreCheckout storeCheckout) {
        return storeRestService.finishCheckout(
                StoreRestService.CHECKOUT_URL,
                modelMapper.transformCheckoutModel(storeCheckout))
                .flatMap(voidResponse -> Observable.empty());

    }
}
