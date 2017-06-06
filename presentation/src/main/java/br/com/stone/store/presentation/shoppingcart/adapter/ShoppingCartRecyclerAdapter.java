package br.com.stone.store.presentation.shoppingcart.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by rrodovalho on 05/06/17.
 */

public class ShoppingCartRecyclerAdapter extends RecyclerView.Adapter<ShoppingCartRecyclerAdapter.ShoppingCartViewHolder> {


    @Override
    public ShoppingCartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ShoppingCartViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ShoppingCartViewHolder extends RecyclerView.ViewHolder{

        public ShoppingCartViewHolder(View itemView) {
            super(itemView);
        }
    }

}
