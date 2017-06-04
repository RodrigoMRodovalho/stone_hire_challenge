package br.com.stone.store.presentation.util;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by rrodovalho on 04/06/17.
 */

public class CurrencyFormatter {

    public static String formatCentavoToReal(String value){

        return NumberFormat
                .getCurrencyInstance(new Locale("pt", "BR"))
                .format(Float.parseFloat(value)/100);
    }
}
