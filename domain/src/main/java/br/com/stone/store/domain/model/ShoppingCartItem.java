package br.com.stone.store.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by rrodovalho on 06/06/17.
 */

@Data
@AllArgsConstructor
public class ShoppingCartItem {
    private Product product;
    private int quantity;

}
