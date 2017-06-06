package br.com.stone.store.domain.usecase;

import javax.inject.Inject;

import br.com.stone.store.domain.base.UseCase;
import br.com.stone.store.domain.model.StoreCheckout;
import br.com.stone.store.domain.repository.StoreRepository;
import io.reactivex.Observable;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Created by rrodovalho on 06/06/17.
 */

public class FinishCheckoutUseCase extends UseCase<FinishCheckoutUseCase.CheckoutValue,Void> {

    @Inject
    StoreRepository.Repo repository;

    public FinishCheckoutUseCase(StoreRepository.Repo repository) {
        this.repository = repository;
    }

    @Override
    protected Observable<Void> executeUseCase(final CheckoutValue requestValues) {
        return repository.confirmCheckout(requestValues.getStoreCheckout())
                .map(aVoid -> {
                    repository.saveTransaction(requestValues.getStoreCheckout());
                    return aVoid;
                });
    }

    @RequiredArgsConstructor
    @Data
    public static class CheckoutValue implements UseCase.RequestValues{
        StoreCheckout storeCheckout;
    }

}
