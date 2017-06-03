package br.com.stone.store.data.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by rrodovalho on 03/06/17.
 */

@Data
public class StoreItem {
    String title;
    String price;
    @SerializedName("zipcode")
    String zipCode;
    String seller;
    @SerializedName("thumbnailHd")
    String thumbnailImage;
    String date;
}
