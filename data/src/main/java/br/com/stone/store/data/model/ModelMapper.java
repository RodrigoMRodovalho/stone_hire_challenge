package br.com.stone.store.data.model;

import br.com.stone.store.domain.model.Product;
import br.com.stone.store.domain.model.StoreCheckout;

/**
 * Created by rrodovalho on 04/06/17.
 */

public class ModelMapper {

    public Product transformProductModel(StoreItem storeItem){
        return Product.builder()
                .title(storeItem.getTitle())
                .price(storeItem.getPrice())
                .seller(storeItem.getSeller())
                .thumbnailImage(storeItem.getThumbnailImage())
                .zipCode(storeItem.getZipCode())
                .build();
    }

    public CheckoutModel transformCheckoutModel(StoreCheckout storeCheckout) {
        return CheckoutModel.builder()
                .cardNumber(storeCheckout.getCardNumber())
                .cardHolderName(storeCheckout.getCardHolderName())
                .expirationDate(storeCheckout.getExpirationDate())
                .cvv(storeCheckout.getCvv())
                .value(storeCheckout.getValue())
                .build();
    }
}
