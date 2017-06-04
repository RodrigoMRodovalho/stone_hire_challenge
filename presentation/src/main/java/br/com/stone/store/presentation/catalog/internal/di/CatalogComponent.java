package br.com.stone.store.presentation.catalog.internal.di;

import br.com.stone.store.presentation.catalog.CatalogActivity;
import br.com.stone.store.presentation.di.components.ApplicationComponent;
import br.com.stone.store.presentation.di.scopes.PerView;
import dagger.Component;

/**
 * Created by rrodovalho on 04/06/17.
 */
@PerView
@Component(dependencies = ApplicationComponent.class,modules = CatalogModule.class)
public interface CatalogComponent {
    void inject(CatalogActivity catalogActivity);
}
