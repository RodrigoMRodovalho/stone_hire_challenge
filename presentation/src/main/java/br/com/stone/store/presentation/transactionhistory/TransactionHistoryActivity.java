package br.com.stone.store.presentation.transactionhistory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.stone.store.presentation.R;


public class TransactionHistoryActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, TransactionHistoryActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
    }
}
