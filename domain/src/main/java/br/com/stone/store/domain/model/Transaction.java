package br.com.stone.store.domain.model;

import lombok.Builder;
import lombok.Data;

/**
 * Created by rrodovalho on 03/06/17.
 */

@Builder
@Data
public class Transaction {
    String amount;
    String timestamp;
    String last4Digits;
    String cardHolderName;
}
