package br.com.stone.store.presentation.shoppingcart.internal.di;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import br.com.stone.store.domain.shoppingcart.IShoppingCartManager;
import br.com.stone.store.domain.usecase.FinishCheckoutUseCase;
import br.com.stone.store.presentation.di.scopes.PerView;
import br.com.stone.store.presentation.shoppingcart.ShoppingCartContract;
import br.com.stone.store.presentation.shoppingcart.ShoppingCartPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by rrodovalho on 05/06/17.
 */

@Module
public class ShoppingCartModule {

    ShoppingCartContract.View view;

    public ShoppingCartModule(ShoppingCartContract.View view) {
        this.view = view;
    }

    @PerView
    @Provides
    RecyclerView.LayoutManager providesLayoutManager(Context context){
        return new LinearLayoutManager(context);
    }

    @PerView
    @Provides
    ShoppingCartContract.Presenter providesPresenter(
            IShoppingCartManager shoppingCartManager,
            FinishCheckoutUseCase useCase){

        ShoppingCartContract.Presenter presenter =
                new ShoppingCartPresenter(shoppingCartManager,useCase);

        presenter.attachView(view);
        return presenter;
    }

    @PerView
    @Provides
    RecyclerView.ItemDecoration providesItemDecoration(Context context){
        return new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL);
    }

}
