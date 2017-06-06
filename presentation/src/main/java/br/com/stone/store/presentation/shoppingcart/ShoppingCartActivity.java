package br.com.stone.store.presentation.shoppingcart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

import br.com.stone.store.presentation.R;
import br.com.stone.store.presentation.base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ShoppingCartActivity extends BaseActivity implements ShoppingCartContract.View{

    @BindView(R.id.shopping_cart_recyclerview)
    RecyclerView shoppingCartRecyclerView;
    @BindView(R.id.total_price_textview)
    TextView totalPriceTextView;

    public static void start(Context context,String shoppingCartJson) {
        Intent starter = new Intent(context, ShoppingCartActivity.class);
        starter.putExtra("shoppingCart", shoppingCartJson);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);

        shoppingCartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();

        if (intent != null){

            String shoppingCartJson = intent.getStringExtra("shoppingCart");
            if (shoppingCartJson != null){
                Type typeOfHashMap = new TypeToken<Map<String, String>>() { }.getType();
                Map<String, String> newMap = new Gson().fromJson(shoppingCartJson, typeOfHashMap);
            }

        }

    }

    @OnClick(R.id.finish_checkout_button)
    public void finishCheckout(){

    }


    @Override
    public void injectDependencies() {

    }

    @Override
    public void updateTotalPrice(String totalPrice) {

    }

    @Override
    public void requestPaymentInformation() {

    }
}
