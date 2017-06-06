package br.com.stone.store.data.repository;

import java.util.List;

import javax.inject.Inject;

import br.com.stone.store.domain.model.ProductItem;
import br.com.stone.store.domain.model.StoreCheckout;
import br.com.stone.store.domain.model.Transaction;
import br.com.stone.store.domain.repository.StoreRepository;
import io.reactivex.Observable;

/**
 * Created by rrodovalho on 03/06/17.
 */

public class RepositoryImpl implements StoreRepository.Repo {

    @Inject
    StoreRepository.Remote remoteSource;
    @Inject
    StoreRepository.Local localSource;

    public RepositoryImpl(StoreRepository.Remote remoteSource, StoreRepository.Local localSource) {
        this.remoteSource = remoteSource;
        this.localSource = localSource;
    }

    @Override
    public Observable<List<ProductItem>> getStoreProducts() {
        return remoteSource.getStoreProducts();
    }

    @Override
    public Observable<Void> confirmCheckout(StoreCheckout storeCheckout) {
        return remoteSource.confirmCheckout(storeCheckout);
    }

    @Override
    public Observable<Void> saveTransaction(StoreCheckout storeCheckout) {
        return localSource.saveTransaction(storeCheckout);
    }

    @Override
    public Observable<List<Transaction>> getFinalizedTransactions() {
        return localSource.getFinalizedTransactions();
    }
}
