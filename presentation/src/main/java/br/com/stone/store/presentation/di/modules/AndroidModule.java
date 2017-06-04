package br.com.stone.store.presentation.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import br.com.stone.store.presentation.application.StoreApplication;
import dagger.Module;
import dagger.Provides;

/**
 * Created by rrodovalho on 03/06/17.
 */

@Module
public class AndroidModule {

    StoreApplication storeApplication;

    public AndroidModule(StoreApplication storeApplication) {
        this.storeApplication = storeApplication;
    }

    @Singleton
    @Provides
    Context providesContext(){
        return this.storeApplication;
    }
}
