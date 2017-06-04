package br.com.stone.store.domain.usecase;

import java.util.List;

import br.com.stone.store.domain.base.UseCase;
import br.com.stone.store.domain.model.ProductItem;
import io.reactivex.Observable;

/**
 * Created by rrodovalho on 04/06/17.
 */

public class FetchCatalogUseCase extends UseCase<UseCase.RequestValues,List<ProductItem>> {

    @Override
    protected Observable<List<ProductItem>> executeUseCase(RequestValues requestValues) {
        return null;
    }
}
