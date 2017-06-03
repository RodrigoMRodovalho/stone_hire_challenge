package br.com.stone.store.domain.model;

import lombok.Builder;
import lombok.Data;

/**
 * Created by rrodovalho on 03/06/17.
 */

@Builder
@Data
public class StoreCheckout {
    String cardNumber;
    String value;
    String cvv;
    String cardHolderName;
    String expirationDate;
}

