package br.com.stone.store.domain.shoppingcart;

import java.util.HashMap;
import java.util.Map;

import br.com.stone.store.domain.model.ProductItem;

/**
 * Created by rrodovalho on 04/06/17.
 */

public class ShoppingCartManager implements IShoppingCartManager {

    private Map<ProductItem,Integer> shoppingCartMap;
    private int totalPrice;

    public ShoppingCartManager() {
        shoppingCartMap = new HashMap<>();
    }

    @Override
    public void addProductItem(ProductItem productItem) {

        int qnt;

        if (shoppingCartMap.containsKey(productItem)){
            qnt = shoppingCartMap.get(productItem) + 1;
        }
        else{
            qnt = 1;
        }

        shoppingCartMap.put(productItem, qnt);
        totalPrice += qnt * Integer.parseInt(productItem.getPrice());
    }

    @Override
    public void removeProductItem(ProductItem productItem) {

        if (shoppingCartMap.containsKey(productItem)){
            totalPrice -= shoppingCartMap.get(productItem) * Integer.parseInt(productItem.getPrice());
            shoppingCartMap.remove(productItem);
        }
    }

    public Map<ProductItem,Integer> getShoppingCart() {
        return shoppingCartMap;
    }

    @Override
    public void updateQuantity(ProductItem productItem, int quantity) {

        if (shoppingCartMap.containsKey(productItem)){
            int currentQuantity = shoppingCartMap.get(productItem);

            if (currentQuantity > quantity){
                totalPrice -= (currentQuantity - quantity) * Integer.parseInt(productItem.getPrice());
            }
            else{
                totalPrice += (quantity - currentQuantity ) * Integer.parseInt(productItem.getPrice());
            }

            shoppingCartMap.put(productItem, quantity);
        }
    }

    @Override
    public int getTotalItensCount() {
        return shoppingCartMap.size();
    }

    @Override
    public void cleanCart() {
        shoppingCartMap.clear();
        totalPrice = 0;
    }

    @Override
    public String getTotalPrice() {
        return String.valueOf(totalPrice);
    }
}
