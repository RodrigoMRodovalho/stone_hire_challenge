package br.com.stone.store.presentation.transactionhistory.internal.di;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import br.com.stone.store.domain.usecase.LoadTransactionsUseCase;
import br.com.stone.store.presentation.di.scopes.PerView;
import br.com.stone.store.presentation.transactionhistory.TransactionHistoryContract;
import br.com.stone.store.presentation.transactionhistory.TransactionHistoryPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by rrodovalho on 08/06/17.
 */

@Module
public class TransactionHistoryModule {

    TransactionHistoryContract.View view;

    public TransactionHistoryModule(TransactionHistoryContract.View view) {
        this.view = view;
    }

    @PerView
    @Provides
    TransactionHistoryContract.Presenter providesPresenter(LoadTransactionsUseCase useCase){

        TransactionHistoryContract.Presenter presenter = new TransactionHistoryPresenter(useCase);
        presenter.attachView(view);
        return presenter;
    }

    @PerView
    @Provides
    RecyclerView.LayoutManager providesLayoutManager(Context context){
        return new LinearLayoutManager(context);
    }

    @PerView
    @Provides
    RecyclerView.ItemDecoration providesItemDecoration(Context context){
        return new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL);
    }

}
