package br.com.stone.store.data.repository;

import java.util.List;

import br.com.stone.store.domain.model.ProductItem;
import br.com.stone.store.domain.model.StoreCheckout;
import br.com.stone.store.domain.model.Transaction;
import br.com.stone.store.domain.repository.StoreRepository;
import io.reactivex.Observable;

/**
 * Created by rrodovalho on 03/06/17.
 */

public class RepositoryImpl implements StoreRepository.Repo {

    StoreRepository.Remote remoteSource;
    StoreRepository.Local localSource;

    @Override
    public Observable<List<ProductItem>> getStoreProducts() {
        return null;
    }

    @Override
    public Observable<Void> confirmCheckout(StoreCheckout storeCheckout) {
        return null;
    }

    @Override
    public Observable<List<Transaction>> getFinalizedTransactions() {
        return null;
    }
}
