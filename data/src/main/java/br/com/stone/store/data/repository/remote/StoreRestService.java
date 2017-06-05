package br.com.stone.store.data.repository.remote;

import java.util.List;

import br.com.stone.store.data.model.CheckoutModel;
import br.com.stone.store.data.model.StoreItem;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by rrodovalho on 03/06/17.
 */

public interface StoreRestService {

    String BASE_URL = "http://myurl.com";
    String STORE_PRODUCTS_URL =
            "https://raw.githubusercontent.com/stone-pagamentos/desafio-mobile/master/products.json";

    @GET
    Observable<List<StoreItem>> getStoreItems(@Url String storeItensUrl);

    @POST
    Observable<Response<Void>> finishCheckout(@Url String storeCheckoutUrl,
                                              @Body CheckoutModel checkoutModel);


}
