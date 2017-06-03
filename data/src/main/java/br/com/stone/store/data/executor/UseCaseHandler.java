package br.com.stone.store.data.executor;


import br.com.stone.store.domain.base.UseCase;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rrodovalho on 03/06/17.
 */

public class UseCaseHandler {

    public static <RV extends UseCase.RequestValues,T> Observable<T> execute(UseCase<RV, T> useCase, RV values){

        useCase.setRequestValues(values);
        return useCase.run()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());

    }
}
