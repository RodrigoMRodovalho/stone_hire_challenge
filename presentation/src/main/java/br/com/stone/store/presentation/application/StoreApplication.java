package br.com.stone.store.presentation.application;

import android.app.Application;

import br.com.stone.store.data.di.modules.NetworkModule;
import br.com.stone.store.data.di.modules.RepositoryModule;
import br.com.stone.store.presentation.di.components.ApplicationComponent;
import br.com.stone.store.presentation.di.components.DaggerApplicationComponent;
import br.com.stone.store.presentation.di.modules.AndroidModule;

/**
 * Created by rrodovalho on 03/06/17.
 */

public class StoreApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjection();
    }

    private void initializeInjection() {

        applicationComponent = DaggerApplicationComponent.builder()
                .androidModule(new AndroidModule(this))
                .repositoryModule(new RepositoryModule())
                .networkModule(new NetworkModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
