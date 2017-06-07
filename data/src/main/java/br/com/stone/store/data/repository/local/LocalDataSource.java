package br.com.stone.store.data.repository.local;

import java.util.List;

import br.com.stone.store.domain.model.StoreCheckout;
import br.com.stone.store.domain.model.Transaction;
import br.com.stone.store.domain.repository.StoreRepository;
import io.reactivex.Observable;

/**
 * Created by rrodovalho on 03/06/17.
 */

public class LocalDataSource implements StoreRepository.Local {

    @Override
    public Observable<Boolean> saveTransaction(StoreCheckout storeCheckout) {
        return null;
    }

    @Override
    public Observable<List<Transaction>> getFinalizedTransactions() {
        return null;
    }
}
