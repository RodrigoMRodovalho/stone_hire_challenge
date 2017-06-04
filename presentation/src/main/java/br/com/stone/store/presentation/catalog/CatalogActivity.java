package br.com.stone.store.presentation.catalog;

import android.os.Bundle;

import java.util.List;

import br.com.stone.store.domain.model.ProductItem;
import br.com.stone.store.presentation.R;
import br.com.stone.store.presentation.base.BaseActivity;


public class CatalogActivity extends BaseActivity implements CatalogContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
    }

    @Override
    public void injectDependencies() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showCatalog(List<ProductItem> catalog) {

    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void showBasketScreen() {

    }

    @Override
    public void showTransactionHistoryScreen() {

    }
}
