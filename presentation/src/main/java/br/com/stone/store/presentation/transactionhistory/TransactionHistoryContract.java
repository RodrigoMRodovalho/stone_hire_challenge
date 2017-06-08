package br.com.stone.store.presentation.transactionhistory;

import java.util.List;

import br.com.stone.store.domain.model.Transaction;
import br.com.stone.store.presentation.base.MvpPresenter;
import br.com.stone.store.presentation.base.MvpView;

/**
 * Created by rrodovalho on 08/06/17.
 */

public interface TransactionHistoryContract {

    interface View extends MvpView{
        void showLoading();
        void hideLoading();
        void showError();
        void showTransactions(List<Transaction> transactionList);
    }

    interface Presenter extends MvpPresenter<View>{
        void loadTransactions();
    }

}
