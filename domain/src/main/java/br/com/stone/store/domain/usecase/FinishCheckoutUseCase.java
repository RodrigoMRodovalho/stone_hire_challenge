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

public class FinishCheckoutUseCase extends UseCase<FinishCheckoutUseCase.CheckoutValue,Boolean> {

    @Inject
    StoreRepository.Repo repository;

    public FinishCheckoutUseCase(StoreRepository.Repo repository) {
        this.repository = repository;
    }

    @Override
    public Observable<Boolean> executeUseCase(final CheckoutValue requestValues) {
        return repository.confirmCheckout(requestValues.getStoreCheckout())
                .map(aBoolean -> {
                    repository.saveTransaction(requestValues.getStoreCheckout());
                    return aBoolean;
                });
    }

    @RequiredArgsConstructor
    @Data
    public static class CheckoutValue implements UseCase.RequestValues{
        StoreCheckout storeCheckout;
    }

}
