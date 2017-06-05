package br.com.stone.store.presentation.di.components;

import android.content.Context;

import javax.inject.Singleton;

import br.com.stone.store.data.di.modules.NetworkModule;
import br.com.stone.store.data.di.modules.RepositoryModule;
import br.com.stone.store.data.repository.remote.StoreRestService;
import br.com.stone.store.domain.shoppingcart.IShoppingCartManager;
import br.com.stone.store.domain.di.UseCaseModule;
import br.com.stone.store.domain.repository.StoreRepository;
import br.com.stone.store.domain.usecase.FetchCatalogUseCase;
import br.com.stone.store.presentation.di.modules.AndroidModule;
import dagger.Component;

/**
 * Created by rrodovalho on 03/06/17.
 */

@Singleton
@Component(modules = {AndroidModule.class,UseCaseModule.class,RepositoryModule.class, NetworkModule.class})
public interface ApplicationComponent {
    Context getContext();
    StoreRestService getRestService();
    StoreRepository.Repo getRepository();
    FetchCatalogUseCase getFetchCatalogUseCase();
    IShoppingCartManager getShoppingCartManager();
}
