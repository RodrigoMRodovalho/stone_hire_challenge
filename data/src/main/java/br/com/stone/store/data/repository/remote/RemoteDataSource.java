package br.com.stone.store.data.repository.remote;

import java.util.List;

import javax.inject.Inject;

import br.com.stone.store.data.model.ModelMapper;
import br.com.stone.store.domain.model.Product;
import br.com.stone.store.domain.model.StoreCheckout;
import br.com.stone.store.domain.repository.StoreRepository;
import io.reactivex.Observable;

import static br.com.stone.store.data.repository.remote.ServerUrl.CHECKOUT_URL;
import static br.com.stone.store.data.repository.remote.ServerUrl.STORE_PRODUCTS_URL;

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
    public Observable<List<Product>> getStoreProducts() {
        return storeRestService.getStoreItems(STORE_PRODUCTS_URL)
                .flatMapIterable(storeItems -> storeItems)
                .map(modelMapper::transformProductModel)
                .toList()
                .toObservable();
    }

    @Override
    public Observable<Boolean> confirmCheckout(StoreCheckout storeCheckout) {
        return storeRestService.finishCheckout(
                CHECKOUT_URL,
                modelMapper.transformCheckoutModel(storeCheckout))
                .flatMap(voidResponse -> Observable.just(voidResponse.isSuccessful()));
    }
}
