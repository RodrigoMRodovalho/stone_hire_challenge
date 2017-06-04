package br.com.stone.store.presentation.di.scopes;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by rrodovalho on 03/06/17.
 */

@Scope
@Retention(RUNTIME)
public @interface PerView {}
