package br.com.stone.store.data.model;

import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.Data;

/**
 * Created by rrodovalho on 03/06/17.
 */

@Builder
@Data
public class CheckoutModel {
    @SerializedName("card_number")
    String cardNumber;
    String value;
    String cvv;
    @SerializedName("card_holder_name")
    String cardHolderName;
    @SerializedName("exp_date")
    String expirationDate;
}