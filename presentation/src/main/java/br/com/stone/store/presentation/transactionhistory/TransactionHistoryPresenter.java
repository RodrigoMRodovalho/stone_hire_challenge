package br.com.stone.store.presentation.transactionhistory;

import java.util.List;

import javax.inject.Inject;

import br.com.stone.store.data.executor.UseCaseHandler;
import br.com.stone.store.domain.base.UseCase;
import br.com.stone.store.domain.model.Transaction;
import br.com.stone.store.presentation.base.BasePresenter;

/**
 * Created by rrodovalho on 08/06/17.
 */

public class TransactionHistoryPresenter extends BasePresenter<TransactionHistoryContract.View>
        implements TransactionHistoryContract.Presenter {

    @Inject
    UseCase loadTransactionUseCase;

    public TransactionHistoryPresenter(UseCase loadTransactionUseCase) {
        this.loadTransactionUseCase = loadTransactionUseCase;
    }

    @Override
    public void loadTransactions() {

        getView().showLoading();

        UseCaseHandler.execute(loadTransactionUseCase,null)
                .subscribe(
                        transactions -> {
                            getView().hideLoading();
                            getView().showTransactions((List<Transaction>) transactions);
                        },
                        throwable -> {
                            getView().hideLoading();
                            getView().showError();
                        });

    }
}
