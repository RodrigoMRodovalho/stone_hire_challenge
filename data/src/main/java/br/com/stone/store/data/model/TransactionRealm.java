package br.com.stone.store.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;

/**
 * Created by rrodovalho on 08/06/17.
 */

@Data
public class TransactionRealm extends RealmObject{
    @PrimaryKey
    private long id;
    String amount;
    String timestamp;
    String last4Digits;
    String cardHolderName;
}
