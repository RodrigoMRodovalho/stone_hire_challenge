package br.com.stone.store.presentation.catalog.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.stone.store.domain.model.Product;
import br.com.stone.store.presentation.R;
import br.com.stone.store.presentation.catalog.CatalogContract;
import br.com.stone.store.presentation.util.CurrencyFormatter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rrodovalho on 04/06/17.
 */

public class CatalogRecyclerViewAdapter extends RecyclerView.Adapter<CatalogRecyclerViewAdapter.CatalogViewHolder> {

    private CatalogContract.OnProductSelectionListener productSelectionListener;
    private Context context;
    private List<Product> productList;

    public CatalogRecyclerViewAdapter(CatalogContract.OnProductSelectionListener productSelectionListener,
                                      Context context,
                                      List<Product> productList) {

        this.productSelectionListener = productSelectionListener;
        this.context = context;
        this.productList = productList;
    }

    @Override
    public CatalogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_catalog_product_card, parent, false);

        return new CatalogViewHolder(itemView,context);
    }

    @Override
    public void onBindViewHolder(CatalogViewHolder holder, int position) {
        holder.bind(productList.get(position),position,productSelectionListener);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class CatalogViewHolder extends RecyclerView.ViewHolder{

        private Context context;
        @BindView(R.id.title_textview)
        TextView titleTextView;
        @BindView(R.id.thumbnail_imageview)
        ImageView thumbnailImageView;
        @BindView(R.id.price_textview)
        TextView priceTextView;
        @BindView(R.id.seller_textview)
        TextView sellerTextView;
        @BindView(R.id.add_to_cart_button)
        Button addToCartButton;


        CatalogViewHolder(View itemView,Context context) {
            super(itemView);
            this.context = context;
            ButterKnife.bind(this,itemView);
        }

        void bind(Product product,
                  final int position,
                  final CatalogContract.OnProductSelectionListener productSelectionListener ){

            titleTextView.setText(product.getTitle());
            priceTextView.setText(CurrencyFormatter.formatCentavoToReal(product.getPrice()));
            sellerTextView.setText(product.getSeller());

            Picasso.with(context)
                    .load(product.getThumbnailImage())
                    .placeholder(R.drawable.product_loading_image)
                    .error(R.drawable.product_loading_error)
                    .into(thumbnailImageView);

            addToCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productSelectionListener.onProductSelected(position);
                }
            });

        }


    }

}
