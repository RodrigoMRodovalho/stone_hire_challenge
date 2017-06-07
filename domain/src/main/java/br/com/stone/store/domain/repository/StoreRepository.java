package br.com.stone.store.domain.repository;

import java.util.List;

import br.com.stone.store.domain.model.Product;
import br.com.stone.store.domain.model.StoreCheckout;
import br.com.stone.store.domain.model.Transaction;
import io.reactivex.Observable;

/**
 * Created by rrodovalho on 03/06/17.
 */

public interface StoreRepository{

    interface Repo extends Remote,Local{}

    interface Remote{
        Observable<List<Product>> getStoreProducts();
        Observable<Boolean> confirmCheckout(StoreCheckout storeCheckout);
    }

    interface Local{
        Observable<Boolean> saveTransaction(StoreCheckout storeCheckout);
        Observable<List<Transaction>> getFinalizedTransactions();
    }
}
