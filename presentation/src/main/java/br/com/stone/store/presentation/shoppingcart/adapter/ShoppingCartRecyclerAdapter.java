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

import java.util.List;

import br.com.stone.store.domain.model.ShoppingCartItem;
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
    private List<ShoppingCartItem> shoppingCartItemList;
    private ShoppingCartContract.OnProductQuantityListener productQuantityListener;

    public ShoppingCartRecyclerAdapter(
            Context context,
            List<ShoppingCartItem> shoppingCartItemList,
            ShoppingCartContract.OnProductQuantityListener productQuantityListener) {

        this.context = context;
        this.shoppingCartItemList = shoppingCartItemList;
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

        holder.bind(shoppingCartItemList.get(position),
                    position,
                    productQuantityListener);
    }

    @Override
    public int getItemCount() {
        return shoppingCartItemList.size();
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

        void bind(ShoppingCartItem shoppingCartItem,
                  final int position,
                  final ShoppingCartContract.OnProductQuantityListener productQuantityListener ){

            Picasso.with(context)
                    .load(shoppingCartItem.getProduct().getThumbnailImage())
                    .placeholder(R.drawable.product_loading_image)
                    .error(R.drawable.product_loading_error)
                    .into(productImageView);

            productNameTextView.setText(shoppingCartItem.getProduct().getTitle());
            productPriceTextView.setText(CurrencyFormatter.formatCentavoToReal(shoppingCartItem.getProduct().getPrice()));
            productSellerTextView.setText(shoppingCartItem.getProduct().getSeller());
            productItemCounterNumberPicker.setValue(shoppingCartItem.getQuantity());

            productItemCounterNumberPicker
                    .setListener(value ->
                            productQuantityListener.onQuantitySelected(shoppingCartItem.getProduct(),value));

            removeProductButton
                    .setOnClickListener(view ->
                            productQuantityListener.onRemovedProduct(shoppingCartItem.getProduct(),position));

        }
    }

}
