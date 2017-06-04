package br.com.stone.store.data.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import br.com.stone.store.data.repository.remote.StoreRestService;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static br.com.stone.store.data.repository.remote.StoreRestService.BASE_URL;

/**
 * Created by rrodovalho on 03/06/17.
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    public StoreRestService providesStoreRestService(Retrofit retrofit){
        return retrofit.create(StoreRestService.class);
    }

    @Provides
    @Singleton
    public Retrofit providesRetrofit(OkHttpClient okHttpClient){

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Context context) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(context.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .build();

    }

}
