package br.com.stone.store.data.repository.remote;

import java.util.List;

import br.com.stone.store.domain.model.ProductItem;
import br.com.stone.store.domain.model.StoreCheckout;
import br.com.stone.store.domain.repository.StoreRepository;
import io.reactivex.Observable;

/**
 * Created by rrodovalho on 03/06/17.
 */

public class RemoteDataSource implements StoreRepository.Remote{

    StoreRestService storeRestService;

    @Override
    public Observable<List<ProductItem>> getStoreProducts() {
        return null;
    }

    @Override
    public Observable<Void> confirmCheckout(StoreCheckout storeCheckout) {
        return null;
    }
}
