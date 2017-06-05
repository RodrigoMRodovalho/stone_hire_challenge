package br.com.stone.store.domain.di;

import javax.inject.Singleton;

import br.com.stone.store.domain.shoppingcart.IShoppingCartManager;
import br.com.stone.store.domain.shoppingcart.ShoppingCartManager;
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
    @Singleton
    IShoppingCartManager providesShoppingCartManager(){
        return new ShoppingCartManager();
    }

}
