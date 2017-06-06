package br.com.stone.store.presentation.shoppingcart.internal.di;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rrodovalho on 05/06/17.
 */

@Module
public class ShoppingCartModule {

    @Provides
    RecyclerView.LayoutManager providesLayoutManager(Context context){
        return new LinearLayoutManager(context);
    }

}
