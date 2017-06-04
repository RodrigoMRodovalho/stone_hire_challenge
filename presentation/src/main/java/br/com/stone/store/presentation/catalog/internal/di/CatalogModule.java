package br.com.stone.store.presentation.catalog.internal.di;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import br.com.stone.store.domain.usecase.FetchCatalogUseCase;
import br.com.stone.store.presentation.catalog.CatalogContract;
import br.com.stone.store.presentation.catalog.CatalogPresenter;
import br.com.stone.store.presentation.catalog.adapter.GridSpacingItemDecoration;
import br.com.stone.store.presentation.di.scopes.PerView;
import br.com.stone.store.presentation.util.ScreenUtil;
import dagger.Module;
import dagger.Provides;

/**
 * Created by rrodovalho on 04/06/17.
 */

@Module
public class CatalogModule {

    CatalogContract.View view;

    public CatalogModule(CatalogContract.View view) {
        this.view = view;
    }

    @PerView
    @Provides
    CatalogContract.Presenter providesPresenter(FetchCatalogUseCase useCase){
        CatalogContract.Presenter presenter = new CatalogPresenter(useCase);
        presenter.attachView(view);
        return presenter;
    }

    @PerView
    @Provides
    RecyclerView.LayoutManager providesRecyclerViewLayoutManager(Context context){
        return new GridLayoutManager(context, 2);
    }

    @PerView
    @Provides
    RecyclerView.ItemDecoration providesRecyclerViewItemDecoration(Context context){
        return new GridSpacingItemDecoration(2, ScreenUtil.dpToPx(context,5), true);
    }

    @PerView
    @Provides
    RecyclerView.ItemAnimator providesRecyclerViewItemAnimator(){
        return new DefaultItemAnimator();
    }


}
