package br.com.stone.store.domain.shoppingcart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.stone.store.domain.model.Product;
import br.com.stone.store.domain.model.ShoppingCartItem;

/**
 * Created by rrodovalho on 04/06/17.
 */

public class ShoppingCartManager implements IShoppingCartManager {

    private Map<Product,Integer> shoppingCartMap;
    private int totalPrice;
    private ArrayList shoppingCartItemList;

    public ShoppingCartManager() {
        shoppingCartMap = new HashMap<>();
    }

    @Override
    public void addProduct(Product product) {

        int qnt;

        if (shoppingCartMap.containsKey(product)){
            qnt = shoppingCartMap.get(product) + 1;
        }
        else{
            qnt = 1;
        }

        shoppingCartMap.put(product, qnt);
        totalPrice += qnt * Integer.parseInt(product.getPrice());
    }

    @Override
    public void removeProduct(Product product) {

        if (shoppingCartMap.containsKey(product)){
            totalPrice -= shoppingCartMap.get(product) * Integer.parseInt(product.getPrice());
            shoppingCartMap.remove(product);
        }
    }

    public List<ShoppingCartItem> getShoppingCart() {

        shoppingCartItemList = new ArrayList<>(shoppingCartMap.size());

        for (Map.Entry<Product, Integer> entry : shoppingCartMap.entrySet()) {
            shoppingCartItemList.add(new ShoppingCartItem(entry.getKey(), entry.getValue()));
        }

        return shoppingCartItemList;
    }

    @Override
    public void updateQuantity(Product product, int quantity) {

        if (shoppingCartMap.containsKey(product)){
            int currentQuantity = shoppingCartMap.get(product);

            if (currentQuantity > quantity){
                totalPrice -= (currentQuantity - quantity) * Integer.parseInt(product.getPrice());
            }
            else{
                totalPrice += (quantity - currentQuantity ) * Integer.parseInt(product.getPrice());
            }

            shoppingCartMap.put(product, quantity);
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
