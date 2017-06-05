package br.com.stone.store.domain.shoppingcart;

import java.util.HashMap;
import java.util.Map;

import br.com.stone.store.domain.model.ProductItem;

/**
 * Created by rrodovalho on 04/06/17.
 */

public class ShoppingCartManager implements IShoppingCartManager {

    private Map<ProductItem,Integer> shoppingCartMap;

    public ShoppingCartManager() {
        shoppingCartMap = new HashMap<>();
    }

    @Override
    public void addProductItem(ProductItem productItem) {

        if (shoppingCartMap.containsKey(productItem)){
            shoppingCartMap.put(productItem, shoppingCartMap.get(productItem) + 1);
        }
        else{
            shoppingCartMap.put(productItem, 1);
        }
    }

    @Override
    public void removeProductItem(ProductItem productItem) {

        if (shoppingCartMap.containsKey(productItem)){
            shoppingCartMap.remove(productItem);
        }
    }

    public Map<ProductItem,Integer> getShoppingCart() {
        return shoppingCartMap;
    }

    @Override
    public void plusQuantity(ProductItem productItem, int quantity) {

        if (shoppingCartMap.containsKey(productItem)){
            shoppingCartMap.put(productItem, shoppingCartMap.get(productItem) + 1);
        }
    }

    @Override
    public void minusQuantity(ProductItem productItem, int quantity) {

        if (shoppingCartMap.containsKey(productItem)){
            shoppingCartMap.put(productItem, shoppingCartMap.get(productItem) - 1);
        }
    }

    @Override
    public int getTotalItensCount() {
        return shoppingCartMap.size();
    }

    @Override
    public void cleanCart() {
        shoppingCartMap.clear();
    }
}
