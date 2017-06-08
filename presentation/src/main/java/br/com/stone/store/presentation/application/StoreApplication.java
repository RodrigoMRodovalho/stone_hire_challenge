package br.com.stone.store.presentation.application;

import android.app.Application;

import br.com.stone.store.data.di.modules.NetworkModule;
import br.com.stone.store.data.di.modules.RepositoryModule;
import br.com.stone.store.domain.di.UseCaseModule;
import br.com.stone.store.presentation.di.components.ApplicationComponent;
import br.com.stone.store.presentation.di.components.DaggerApplicationComponent;
import br.com.stone.store.presentation.di.modules.AndroidModule;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by rrodovalho on 03/06/17.
 */

public class StoreApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjection();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("stonewars.db")
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);
    }

    private void initializeInjection() {

        applicationComponent = DaggerApplicationComponent.builder()
                .androidModule(new AndroidModule(this))
                .repositoryModule(new RepositoryModule())
                .networkModule(new NetworkModule())
                .useCaseModule(new UseCaseModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
