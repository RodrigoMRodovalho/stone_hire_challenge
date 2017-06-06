package br.com.stone.store.presentation.shoppingcart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.Map;

import javax.inject.Inject;

import br.com.stone.store.domain.model.ProductItem;
import br.com.stone.store.presentation.R;
import br.com.stone.store.presentation.application.StoreApplication;
import br.com.stone.store.presentation.base.BaseActivity;
import br.com.stone.store.presentation.shoppingcart.adapter.ShoppingCartRecyclerAdapter;
import br.com.stone.store.presentation.shoppingcart.internal.di.DaggerShoppingCartComponent;
import br.com.stone.store.presentation.shoppingcart.internal.di.ShoppingCartModule;
import br.com.stone.store.presentation.util.CurrencyFormatter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ShoppingCartActivity extends BaseActivity
        implements ShoppingCartContract.View, ShoppingCartContract.OnProductQuantityListener {

    @BindView(R.id.shopping_cart_recyclerview)
    RecyclerView shoppingCartRecyclerView;
    @BindView(R.id.total_price_textview)
    TextView totalPriceTextView;

    @Inject
    ShoppingCartContract.Presenter presenter;
    @Inject
    RecyclerView.LayoutManager linearLayoutManager;
    @Inject
    RecyclerView.ItemDecoration itemDecoration;
    private Map<ProductItem, Integer> shoppingCart;
    private ShoppingCartRecyclerAdapter shoppingCartAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, ShoppingCartActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);

        shoppingCartRecyclerView.setLayoutManager(linearLayoutManager);
        shoppingCartRecyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.fetchShoppingCart();
    }

    @OnClick(R.id.finish_checkout_button)
    public void finishCheckout(){

    }


    @Override
    public void injectDependencies() {
        DaggerShoppingCartComponent.builder()
                .applicationComponent(((StoreApplication)getApplication()).getApplicationComponent())
                .shoppingCartModule(new ShoppingCartModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void updateTotalPrice(String totalPrice) {
        totalPriceTextView.setText(CurrencyFormatter.formatCentavoToReal(totalPrice));
    }

    @Override
    public void requestPaymentInformation() {

    }

    @Override
    public void showShoppingCart(Map<ProductItem, Integer> shoppingCart) {

        this.shoppingCart = shoppingCart;
        this.shoppingCartAdapter = new ShoppingCartRecyclerAdapter(this,shoppingCart,this);
        shoppingCartRecyclerView.setAdapter(shoppingCartAdapter);
    }

    @Override
    public void showEmptyCart() {

    }

    @Override
    public void onQuantitySelected(ProductItem productItem, int quantity) {
        presenter.updateProductQuantity(productItem,quantity);
    }

    @Override
    public void onRemovedProduct(ProductItem productItem,int position) {
        shoppingCart.remove(productItem);
        shoppingCartAdapter.notifyItemRemoved(position);
        presenter.removeProduct(productItem);

    }
}
