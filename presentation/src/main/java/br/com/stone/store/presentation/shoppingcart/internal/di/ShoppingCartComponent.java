package br.com.stone.store.presentation.shoppingcart.internal.di;

import br.com.stone.store.presentation.di.components.ApplicationComponent;
import br.com.stone.store.presentation.di.scopes.PerView;
import br.com.stone.store.presentation.shoppingcart.ShoppingCartActivity;
import dagger.Component;

/**
 * Created by rrodovalho on 05/06/17.
 */
@PerView
@Component(dependencies = ApplicationComponent.class,modules = ShoppingCartModule.class)
public interface ShoppingCartComponent {
    void inject(ShoppingCartActivity shoppingCartActivity);
}
