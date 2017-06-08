package br.com.stone.store.presentation.transactionhistory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.List;

import javax.inject.Inject;

import br.com.stone.store.domain.model.Transaction;
import br.com.stone.store.presentation.R;
import br.com.stone.store.presentation.application.StoreApplication;
import br.com.stone.store.presentation.base.BaseActivity;
import br.com.stone.store.presentation.transactionhistory.adapter.TransactionHistoryRecyclerAdapter;
import br.com.stone.store.presentation.transactionhistory.internal.di.DaggerTransactionHistoryComponent;
import br.com.stone.store.presentation.transactionhistory.internal.di.TransactionHistoryModule;
import butterknife.BindView;
import butterknife.ButterKnife;


public class TransactionHistoryActivity extends BaseActivity
        implements TransactionHistoryContract.View {


    @BindView(R.id.loading_framelayout)
    FrameLayout loadingFrameLayout;
    @BindView(R.id.empty_trans_history_relativeLayout)
    RelativeLayout emptyTransactionHistoryRelativeLayout;
    @BindView(R.id.trans_history_recyclerview)
    RecyclerView transactionHistoryRecyclerView;

    @Inject
    TransactionHistoryContract.Presenter presenter;
    @Inject
    RecyclerView.LayoutManager linearLayoutManager;
    @Inject
    RecyclerView.ItemDecoration itemDecoration;

    public static void start(Context context) {
        Intent starter = new Intent(context, TransactionHistoryActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        ButterKnife.bind(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.loadTransactions();
    }

    @Override
    public void injectDependencies() {
        DaggerTransactionHistoryComponent.builder()
                .applicationComponent(((StoreApplication)getApplication()).getApplicationComponent())
                .transactionHistoryModule(new TransactionHistoryModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showLoading() {
        loadingFrameLayout.setVisibility(View.VISIBLE);
        emptyTransactionHistoryRelativeLayout.setVisibility(View.GONE);
        transactionHistoryRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        loadingFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        emptyTransactionHistoryRelativeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTransactions(List<Transaction> transactionList) {

        transactionHistoryRecyclerView.setVisibility(View.VISIBLE);
        transactionHistoryRecyclerView.setLayoutManager(linearLayoutManager);
        transactionHistoryRecyclerView.addItemDecoration(itemDecoration);
        transactionHistoryRecyclerView.setAdapter(
                new TransactionHistoryRecyclerAdapter(transactionList));
    }
}
