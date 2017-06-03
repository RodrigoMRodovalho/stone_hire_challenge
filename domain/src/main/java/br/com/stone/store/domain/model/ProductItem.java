package br.com.stone.store.domain.model;

import lombok.Data;

/**
 * Created by rrodovalho on 03/06/17.
 */

@Data
public class ProductItem {
    String title;
    String price;
    String zipCode;
    String seller;
    String thumbnailImage;
}
