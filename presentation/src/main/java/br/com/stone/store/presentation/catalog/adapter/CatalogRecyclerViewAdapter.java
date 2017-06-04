package br.com.stone.store.presentation.catalog.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.stone.store.domain.model.ProductItem;
import br.com.stone.store.presentation.catalog.CatalogContract;

/**
 * Created by rrodovalho on 04/06/17.
 */

public class CatalogRecyclerViewAdapter extends RecyclerView.Adapter<CatalogRecyclerViewAdapter.CatalogViewHolder> {

    private CatalogContract.OnProductSelectionListener productSelectionListener;
    private Context context;
    private List<ProductItem> productItemList;

    public CatalogRecyclerViewAdapter(CatalogContract.OnProductSelectionListener productSelectionListener,
                                      Context context,
                                      List<ProductItem> productItemList) {

        this.productSelectionListener = productSelectionListener;
        this.context = context;
        this.productItemList = productItemList;
    }

    @Override
    public CatalogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(CatalogViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class CatalogViewHolder extends RecyclerView.ViewHolder{

        public CatalogViewHolder(View itemView) {
            super(itemView);
        }
    }

}
