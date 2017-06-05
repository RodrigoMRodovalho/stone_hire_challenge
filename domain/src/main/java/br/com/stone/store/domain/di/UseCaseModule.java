package br.com.stone.store.domain.di;

import br.com.stone.store.domain.basket.BasketManager;
import br.com.stone.store.domain.basket.IBasketManager;
import br.com.stone.store.domain.repository.StoreRepository;
import br.com.stone.store.domain.usecase.FetchCatalogUseCase;
import dagger.Module;
import dagger.Provides;

/**
 * Created by rrodovalho on 03/06/17.
 */

@Module
public class UseCaseModule {

    @Provides
    FetchCatalogUseCase providesFetchCatalogUseCase(StoreRepository.Repo repository){
        return new FetchCatalogUseCase(repository);
    }

    @Provides
    IBasketManager providesBasketManager(){
        return new BasketManager();
    }

}
