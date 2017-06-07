package br.com.stone.store.presentation.shoppingcart;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pinball83.maskededittext.MaskedEditText;

import java.util.List;

import javax.inject.Inject;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.stone.store.domain.model.Product;
import br.com.stone.store.domain.model.ShoppingCartItem;
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

    private static final int CARD_NUMBER_SIZE = 16;
    private static final int CARDHOLDER_NAME_MIN_SIZE = 10;
    private static final int EXP_DATE_SIZE = 4;
    private static final int CVV_SIZE = 3;
    @BindView(R.id.shopping_cart_recyclerview)
    RecyclerView shoppingCartRecyclerView;
    @BindView(R.id.total_price_textview)
    TextView totalPriceTextView;
    @BindView(R.id.finish_checkout_button)
    CircularProgressButton finishCheckoutButton;

    @BindView(R.id.cart_relativelayout)
    RelativeLayout cartRelativeLayout;
    @BindView(R.id.empty_cart_relativeLayout)
    RelativeLayout emptyCartRelativeLayout;


    @Inject
    ShoppingCartContract.Presenter presenter;
    @Inject
    RecyclerView.LayoutManager linearLayoutManager;
    @Inject
    RecyclerView.ItemDecoration itemDecoration;
    private List<ShoppingCartItem> shoppingCartItemList;
    private ShoppingCartRecyclerAdapter shoppingCartAdapter;

    private MaskedEditText cardNumberMasketEditText;
    private EditText cardHolderNameEditText;
    private MaskedEditText expDateMasketEditText;
    private MaskedEditText cvvMasketEditText;

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
        showPaymentDataDialog();
    }

    private void showPaymentDataDialog(){
        View paymentInfoView =
                LayoutInflater.from(this).inflate(R.layout.dialog_payment_info, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(paymentInfoView);

        cardNumberMasketEditText =
                ButterKnife.findById(paymentInfoView,R.id.card_number_masked_edittext);
        cardHolderNameEditText =
                ButterKnife.findById(paymentInfoView,R.id.cardholder_name_edittext);
        expDateMasketEditText =
                ButterKnife.findById(paymentInfoView,R.id.exp_date_card_masked_edittext);
        cvvMasketEditText =
                ButterKnife.findById(paymentInfoView,R.id.cvv_card_masked_edittext);

        AlertDialog alertDialog = alertDialogBuilder
                                    .setCancelable(false)
                                    .setPositiveButton(getString(R.string.confirm_payment),null)
                                    .setNegativeButton(getString(R.string.cancel_payment),
                                            (dialog, id) -> dialog.cancel())
                                    .create();

        alertDialog.setOnShowListener(dialog -> {

            Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(view -> {

                boolean isValidPaymentInfo = true;

                if (cardNumberMasketEditText.getUnmaskedText().length() != CARD_NUMBER_SIZE){
                    cardNumberMasketEditText.setError(getString(R.string.invalid_cardnumber));
                    isValidPaymentInfo = false;
                }

                if (cardHolderNameEditText.getText().toString().length() < CARDHOLDER_NAME_MIN_SIZE) {
                    cardHolderNameEditText.setError(getString(R.string.invalid_cardholder_name));
                    isValidPaymentInfo = false;
                }

                if (expDateMasketEditText.getUnmaskedText().length() != EXP_DATE_SIZE){
                    expDateMasketEditText.setError(getString(R.string.invalid_date));
                    isValidPaymentInfo = false;
                }
                if(cvvMasketEditText.getUnmaskedText().length() != CVV_SIZE){
                    isValidPaymentInfo = false;
                    cvvMasketEditText.setError(getString(R.string.invalid_cvv));
                }

                if (isValidPaymentInfo){
                    presenter.sendCheckoutToApproval(
                            StoreCheckout.builder()
                                    .cardNumber(cardNumberMasketEditText.getUnmaskedText())
                                    .cardHolderName(cardHolderNameEditText.getText().toString())
                                    .expirationDate(expDateMasketEditText.getUnmaskedText())
                                    .cvv(cvvMasketEditText.getUnmaskedText())
                                    .build());

                    alertDialog.dismiss();
                }
            });
        });

        alertDialog.show();
    }

    @Override
    public void showShoppingCart(List<ShoppingCartItem> shoppingCartItemList) {

        emptyCartRelativeLayout.setVisibility(View.GONE);
        cartRelativeLayout.setVisibility(View.VISIBLE);

        this.shoppingCartItemList = shoppingCartItemList;
        this.shoppingCartAdapter = new ShoppingCartRecyclerAdapter(this,shoppingCartItemList,this);
        shoppingCartRecyclerView.setAdapter(shoppingCartAdapter);
    }

    @Override
    public void showEmptyCart() {
        emptyCartRelativeLayout.setVisibility(View.VISIBLE);
        cartRelativeLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        finishCheckoutButton.startAnimation();
    }

    @Override
    public void showPaymentCompleteSuccessfully() {

        finishCheckoutButton.doneLoadingAnimation(Color.GREEN,
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_shopping_cart));
    }

    @Override
    public void showPaymentFails() {
        Toast.makeText(this,"Algo ocorreu de errado, tente novamente",Toast.LENGTH_SHORT).show();
        finishCheckoutButton.revertAnimation();
    }

    @Override
    public void onQuantitySelected(Product product, int quantity) {
        presenter.updateProductQuantity(product,quantity);
    }

    @Override
    public void onRemovedProduct(Product product, int position) {
        shoppingCartItemList.remove(product);
        shoppingCartAdapter.notifyItemRemoved(position);
        shoppingCartAdapter.notifyItemRangeChanged(position, shoppingCartItemList.size());
        presenter.removeProduct(product);

    }
}
