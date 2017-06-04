package br.com.stone.store.data.di.modules;

import javax.inject.Singleton;

import br.com.stone.store.data.repository.RepositoryImpl;
import br.com.stone.store.data.repository.local.LocalDataSource;
import br.com.stone.store.data.repository.remote.RemoteDataSource;
import br.com.stone.store.data.repository.remote.StoreRestService;
import br.com.stone.store.domain.repository.StoreRepository;
import dagger.Module;
import dagger.Provides;

/**
 * Created by rrodovalho on 03/06/17.
 */

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    public StoreRepository.Remote providesRemoteRepository(StoreRestService storeRestService){
        return new RemoteDataSource(storeRestService);
    }

    @Provides
    @Singleton
    public StoreRepository.Local providesLocalRepository(){
        return new LocalDataSource();
    }

    @Provides
    @Singleton
    public StoreRepository.Repo providesRepository(
            StoreRepository.Remote remote, StoreRepository.Local local){
        return new RepositoryImpl(remote,local);
    }

}
