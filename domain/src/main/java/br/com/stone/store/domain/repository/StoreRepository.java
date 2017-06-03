package br.com.stone.store.domain.repository;

import java.util.List;

import br.com.stone.store.domain.model.ProductItem;
import br.com.stone.store.domain.model.StoreCheckout;
import br.com.stone.store.domain.model.Transaction;
import io.reactivex.Observable;

/**
 * Created by rrodovalho on 03/06/17.
 */

public interface StoreRepository {

    Observable<List<ProductItem>> getStoreProducts();

    Observable<Void> confirmCheckout(StoreCheckout storeCheckout);

    Observable<List<Transaction>> getFinalizedTransactions();

}
