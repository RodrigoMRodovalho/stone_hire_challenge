package br.com.stone.store.domain.di;

import br.com.stone.store.domain.usecase.FetchCatalogUseCase;
import dagger.Module;
import dagger.Provides;

/**
 * Created by rrodovalho on 03/06/17.
 */

@Module
public class UseCaseModule {

    @Provides
    FetchCatalogUseCase providesFetchCatalogUseCase(){
        return new FetchCatalogUseCase();
    }

}
