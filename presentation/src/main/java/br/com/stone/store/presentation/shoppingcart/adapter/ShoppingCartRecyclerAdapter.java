package br.com.stone.store.presentation.shoppingcart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.stone.store.domain.model.ProductItem;
import br.com.stone.store.presentation.R;
import br.com.stone.store.presentation.shoppingcart.ShoppingCartContract;
import br.com.stone.store.presentation.util.CurrencyFormatter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rrodovalho on 05/06/17.
 */

public class ShoppingCartRecyclerAdapter extends RecyclerView.Adapter<ShoppingCartRecyclerAdapter.ShoppingCartViewHolder> {

    private Context context;
    private Map<ProductItem,Integer> shoppingCartMap;
    private List<ProductItem> productsItemList;
    private ShoppingCartContract.OnProductQuantityListener productQuantityListener;

    public ShoppingCartRecyclerAdapter(
            Context context,
            Map<ProductItem, Integer> shoppingCartMap,
            ShoppingCartContract.OnProductQuantityListener productQuantityListener) {

        this.context = context;
        this.shoppingCartMap = shoppingCartMap;
        this.productsItemList = new ArrayList<>(shoppingCartMap.keySet());
        this.productQuantityListener = productQuantityListener;
    }

    @Override
    public ShoppingCartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_shopping_cart_item, parent, false);

        return new ShoppingCartRecyclerAdapter.ShoppingCartViewHolder(itemView,context);
    }

    @Override
    public void onBindViewHolder(ShoppingCartViewHolder holder, int position) {

        ProductItem productItem = productsItemList.get(position);

        holder.bind(productItem,
                shoppingCartMap.get(productItem),
                position,
                productQuantityListener);
    }

    @Override
    public int getItemCount() {
        return productsItemList.size();
    }

    static class ShoppingCartViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.product_imageview)
        ImageView productImageView;
        @BindView(R.id.product_name_textview)
        TextView productNameTextView;
        @BindView(R.id.product_price_textview)
        TextView productPriceTextView;
        @BindView(R.id.product_seller_textview)
        TextView productSellerTextView;
        @BindView(R.id.product_item_counter_number_picker)
        ScrollableNumberPicker productItemCounterNumberPicker;
        @BindView(R.id.remove_product_button)
        Button removeProductButton;

        private Context context;

        ShoppingCartViewHolder(View itemView,Context context) {
            super(itemView);
            this.context = context;
            ButterKnife.bind(this,itemView);
        }

        void bind(ProductItem productItem,
                  final int quantity,
                  final int position,
                  final ShoppingCartContract.OnProductQuantityListener productQuantityListener ){

            Picasso.with(context)
                    .load(productItem.getThumbnailImage())
                    .placeholder(R.drawable.product_loading_image)
                    .error(R.drawable.product_loading_error)
                    .into(productImageView);

            productNameTextView.setText(productItem.getTitle());
            productPriceTextView.setText(CurrencyFormatter.formatCentavoToReal(productItem.getPrice()));
            productSellerTextView.setText(productItem.getSeller());
            productItemCounterNumberPicker.setValue(quantity);

            productItemCounterNumberPicker
                    .setListener(value -> productQuantityListener.onQuantitySelected(productItem,value));

            removeProductButton
                    .setOnClickListener(view -> productQuantityListener.onRemovedProduct(productItem,position));

        }
    }

}
