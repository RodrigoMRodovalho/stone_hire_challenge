package br.com.stone.store.domain.usecase;

import java.util.List;

import javax.inject.Inject;

import br.com.stone.store.domain.base.UseCase;
import br.com.stone.store.domain.model.Transaction;
import br.com.stone.store.domain.repository.StoreRepository;
import io.reactivex.Observable;

/**
 * Created by rrodovalho on 08/06/17.
 */

public class LoadTransactionsUseCase extends UseCase<UseCase.RequestValues,List<Transaction>> {

    @Inject
    StoreRepository.Repo repository;

    public LoadTransactionsUseCase(StoreRepository.Repo repository) {
        this.repository = repository;
    }

    @Override
    public Observable<List<Transaction>> executeUseCase(RequestValues requestValues) {
        return repository.getFinalizedTransactions();
    }
}
