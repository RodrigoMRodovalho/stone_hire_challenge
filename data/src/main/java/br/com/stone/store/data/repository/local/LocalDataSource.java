package br.com.stone.store.data.repository.local;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.stone.store.data.model.TransactionRealm;
import br.com.stone.store.domain.model.StoreCheckout;
import br.com.stone.store.domain.model.Transaction;
import br.com.stone.store.domain.repository.StoreRepository;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by rrodovalho on 03/06/17.
 */

public class LocalDataSource implements StoreRepository.Local {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Override
    public Observable<Boolean> saveTransaction(StoreCheckout storeCheckout) {

        try{

            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            Number maxId = realm.where(TransactionRealm.class).max("id");
            int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;

            TransactionRealm transactionRealm =
                    realm.createObject(TransactionRealm.class,
                                    nextId);
            transactionRealm.setAmount(storeCheckout.getValue());
            transactionRealm.setLast4Digits(storeCheckout.getCardNumber().substring(11,15));
            transactionRealm.setCardHolderName(storeCheckout.getCardHolderName());
            transactionRealm.setTimestamp(simpleDateFormat.format(Calendar.getInstance().getTime()));
            realm.commitTransaction();

            return Observable.just(Boolean.TRUE);
        }
        catch (Exception e){
            return Observable.error(e.getCause());
        }
    }

    @Override
    public Observable<List<Transaction>> getFinalizedTransactions() {

        Realm realm = Realm.getDefaultInstance();

        final RealmResults<TransactionRealm> transactions = realm.where(TransactionRealm.class).findAll();

        if (transactions != null && transactions.size() > 0){

            List<Transaction> transactionList = new ArrayList<>();
            Transaction t;
            for (int i=0;i<transactions.size();i++){

                t = Transaction.builder()
                        .timestamp(transactions.get(i).getTimestamp())
                        .amount(transactions.get(i).getAmount())
                        .cardHolderName(transactions.get(i).getCardHolderName())
                        .last4Digits(transactions.get(i).getLast4Digits())
                        .build();

                transactionList.add(t);
            }

            return Observable.just(transactionList);
        }

        return Observable.error(new Throwable("Error"));
    }
}
