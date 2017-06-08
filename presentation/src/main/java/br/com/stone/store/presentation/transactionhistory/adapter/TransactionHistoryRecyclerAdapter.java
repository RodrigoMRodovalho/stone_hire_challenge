package br.com.stone.store.presentation.transactionhistory.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.stone.store.domain.model.Transaction;
import br.com.stone.store.presentation.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rrodovalho on 08/06/17.
 */

public class TransactionHistoryRecyclerAdapter
        extends RecyclerView.Adapter<TransactionHistoryRecyclerAdapter.TransactionHistoryViewHolder>{

    List<Transaction> transactionList;

    public TransactionHistoryRecyclerAdapter(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @Override
    public TransactionHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_transaction_history_item, parent, false);

        return new TransactionHistoryRecyclerAdapter.TransactionHistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TransactionHistoryViewHolder holder, int position) {
        holder.bind(transactionList.get(position));
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    static class TransactionHistoryViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.card_last_four_digits_textview)
        TextView cardLast4DigitsTextView;
        @BindView(R.id.card_holder_name_textview)
        TextView cardHolderNameTextView;
        @BindView(R.id.trans_price_textview)
        TextView transactionPriceTextView;
        @BindView(R.id.trans_date_textview)
        TextView transactionDateTimeTextView;

        TransactionHistoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void bind(Transaction transaction){
            cardLast4DigitsTextView.setText(transaction.getLast4Digits());
            cardHolderNameTextView.setText(transaction.getCardHolderName());
            transactionPriceTextView.setText(transaction.getAmount());
            transactionDateTimeTextView.setText(transaction.getTimestamp());
        }
    }
}
