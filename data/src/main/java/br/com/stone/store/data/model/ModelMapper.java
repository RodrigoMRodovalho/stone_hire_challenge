package br.com.stone.store.data.model;

import br.com.stone.store.domain.model.ProductItem;

/**
 * Created by rrodovalho on 04/06/17.
 */

public class ModelMapper {

    public ProductItem transformProductModel(StoreItem storeItem){
        return ProductItem.builder()
                .title(storeItem.getTitle())
                .price(storeItem.getPrice())
                .seller(storeItem.getSeller())
                .thumbnailImage(storeItem.getThumbnailImage())
                .zipCode(storeItem.getZipCode())
                .build();
    }

}
