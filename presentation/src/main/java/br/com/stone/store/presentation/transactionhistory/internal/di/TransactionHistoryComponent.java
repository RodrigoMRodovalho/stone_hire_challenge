package br.com.stone.store.presentation.transactionhistory.internal.di;

import br.com.stone.store.presentation.di.components.ApplicationComponent;
import br.com.stone.store.presentation.di.scopes.PerView;
import br.com.stone.store.presentation.transactionhistory.TransactionHistoryActivity;
import dagger.Component;

/**
 * Created by rrodovalho on 08/06/17.
 */
@PerView
@Component(dependencies = ApplicationComponent.class,modules = TransactionHistoryModule.class)
public interface TransactionHistoryComponent {
    void inject(TransactionHistoryActivity transactionHistoryActivity);
}
