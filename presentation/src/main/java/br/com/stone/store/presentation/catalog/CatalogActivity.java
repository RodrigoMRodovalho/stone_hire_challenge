package br.com.stone.store.presentation.catalog;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.test.espresso.IdlingResource;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mikepenz.actionitembadge.library.ActionItemBadge;

import java.util.List;

import javax.inject.Inject;

import br.com.stone.store.domain.model.Product;
import br.com.stone.store.presentation.R;
import br.com.stone.store.presentation.application.StoreApplication;
import br.com.stone.store.presentation.base.BaseActivity;
import br.com.stone.store.presentation.catalog.adapter.CatalogRecyclerViewAdapter;
import br.com.stone.store.presentation.catalog.internal.di.CatalogModule;
import br.com.stone.store.presentation.catalog.internal.di.DaggerCatalogComponent;
import br.com.stone.store.presentation.shoppingcart.ShoppingCartActivity;
import br.com.stone.store.presentation.transactionhistory.TransactionHistoryActivity;
import br.com.stone.store.presentation.util.EspressoIdlingResource;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CatalogActivity extends BaseActivity
        implements CatalogContract.View,CatalogContract.OnProductSelectionListener {

    @BindView(R.id.catalog_recyclerview)
    RecyclerView catalogRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.loading_framelayout)
    FrameLayout loadingFrameLayout;
    @BindView(R.id.error_relativelayout)
    RelativeLayout errorRelativeLayout;
    @BindView(R.id.error_description_textview)
    TextView errorDescriptionTextView;


    @Inject
    CatalogContract.Presenter presenter;
    @Inject
    RecyclerView.LayoutManager catalogRecyclerViewLayoutManager;
    @Inject
    RecyclerView.ItemDecoration catalogRecyclerViewItemDecotation;
    @Inject
    RecyclerView.ItemAnimator catalogRecyclerViewItemAnimator;

    private List<Product> productList;
    private CatalogRecyclerViewAdapter catalogAdapter;
    private MenuItem shoppingCartMenuItem;
    private Drawable shoppingCartMenuIconDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initCollapsingToolbar();

        catalogRecyclerView.setLayoutManager(catalogRecyclerViewLayoutManager);
        catalogRecyclerView.addItemDecoration(catalogRecyclerViewItemDecotation);
        catalogRecyclerView.setItemAnimator(catalogRecyclerViewItemAnimator);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.catalog_menu, menu);

        shoppingCartMenuItem = menu.findItem(R.id.menu_shopping_cart);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            shoppingCartMenuIconDrawable = getDrawable(R.drawable.ic_action_shopping_cart);
        }
        else{
            shoppingCartMenuIconDrawable = getResources().getDrawable(R.drawable.ic_action_shopping_cart);
        }

        updateShoppingCartMenuBadge();

        return super.onCreateOptionsMenu(menu);
    }

    private void updateShoppingCartMenuBadge(){

        ActionItemBadge.update(
                this,
                shoppingCartMenuItem,
                shoppingCartMenuIconDrawable,
                ActionItemBadge.BadgeStyles.RED,
                presenter.getShoppingCartSize());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_transaction_history) presenter.goToTransactionHistoryScreen();
        else presenter.goToShoppingCartScreen();

        return super.onOptionsItemSelected(item);
    }

    private void initCollapsingToolbar() {

        collapsingToolbar.setTitle(" ");
        appBarLayout.setExpanded(true);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    @Override
    public void injectDependencies() {
        DaggerCatalogComponent.builder()
                .applicationComponent(((StoreApplication)getApplication()).getApplicationComponent())
                .catalogModule(new CatalogModule(this))
                .build()
                .inject(this);
    }

    @OnClick(R.id.retryButton)
    public void retryGetCatalog(){
        presenter.fetchCatalog();
    }

    @Override
    public void showLoading() {
        loadingFrameLayout.setVisibility(View.VISIBLE);
        errorRelativeLayout.setVisibility(View.GONE);
        catalogRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        loadingFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void showCatalog(List<Product> catalog) {

        catalogRecyclerView.setVisibility(View.VISIBLE);
        productList = catalog;
        catalogAdapter = new CatalogRecyclerViewAdapter(
                                        CatalogActivity.this,
                                        CatalogActivity.this,
                                        productList);

        catalogRecyclerView.setAdapter(catalogAdapter);

    }

    @Override
    public void showError(String errorMessage) {
        errorRelativeLayout.setVisibility(View.VISIBLE);
        errorDescriptionTextView.setText(errorMessage);
    }

    @Override
    public void showShoppingCartScreen() {
        ShoppingCartActivity.start(this);
    }

    @Override
    public void showTransactionHistoryScreen() {
        TransactionHistoryActivity.start(this);
    }

    @Override
    public void updateCartItemCounter(int counter) {
        ActionItemBadge.update(shoppingCartMenuItem, counter);
    }


    @Override
    public void onProductSelected(int position) {
        presenter.addToCart(productList.get(position));
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateShoppingCartMenuBadge();
        presenter.fetchCatalog();
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
