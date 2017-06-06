package br.com.stone.store.presentation.shoppingcart;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.pinball83.maskededittext.MaskedEditText;

import java.util.Map;

import javax.inject.Inject;

import br.com.stone.store.domain.model.ProductItem;
import br.com.stone.store.domain.model.StoreCheckout;
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
        presenter.finishCheckout();
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

        LayoutInflater li = LayoutInflater.from(this);
        View paymentInfoView = li.inflate(R.layout.dialog_payment_info, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this );

        alertDialogBuilder.setView(paymentInfoView);


        MaskedEditText cardNumberMasketEditText =
                (MaskedEditText)paymentInfoView.findViewById(R.id.card_number_masked_edittext);

        EditText cardHolderNameEditText = (EditText) paymentInfoView
                .findViewById(R.id.cardholder_name_edittext);

        MaskedEditText expDateMasketEditText =
                (MaskedEditText)paymentInfoView.findViewById(R.id.exp_date_card_masked_edittext);

        MaskedEditText cvvMasketEditText =
                (MaskedEditText)paymentInfoView.findViewById(R.id.cvv_card_masked_edittext);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(getString(R.string.confirm_payment),
                        (dialog, id) -> {

                            if (cardNumberMasketEditText.getUnmaskedText().length() != 16){
                                cardNumberMasketEditText.setError("Número de Cartão Inválido");
                            }

                            if (cardHolderNameEditText.getText().toString().length() < 10)
                                cardHolderNameEditText.setError("Nome inválido");

                            if (expDateMasketEditText.getUnmaskedText().length() != 4){
                                expDateMasketEditText.setError("Data Inválida");
                            }
                            if(cvvMasketEditText.getUnmaskedText().length() != 3){
                                cvvMasketEditText.setError("CVV Inválido");
                            }




                            presenter.sendCheckoutToApproval(
                                    StoreCheckout.builder()
                                            .cardNumber(cardNumberMasketEditText.getUnmaskedText())
                                            .cardHolderName(cardHolderNameEditText.getText().toString())
                                            .expirationDate(expDateMasketEditText.getUnmaskedText())
                                            .cvv(cvvMasketEditText.getUnmaskedText())
                                            .build()
                            );

                        })
                .setNegativeButton(getString(R.string.cancel_payment),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();


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
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showPaymentCompleteSuccessfully() {

    }

    @Override
    public void showPaymentFails() {

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
