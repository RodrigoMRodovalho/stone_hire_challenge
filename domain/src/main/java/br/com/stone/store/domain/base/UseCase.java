package br.com.stone.store.domain.base;

import io.reactivex.Observable;

/**
 * Created by rrodovalho on 03/06/17.
 */

public abstract class UseCase<RV extends UseCase.RequestValues,T> {

    private RV mRequestValues;

    public void setRequestValues(RV requestValues) {
        mRequestValues = requestValues;
    }

    public Observable<T> run() {
        return executeUseCase(mRequestValues);
    }

    public abstract Observable<T> executeUseCase(RV requestValues);


    public interface RequestValues {
    }

}
