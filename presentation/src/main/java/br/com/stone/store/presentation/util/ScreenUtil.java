package br.com.stone.store.presentation.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by rrodovalho on 04/06/17.
 */

public class ScreenUtil {

    public static int dpToPx(Context context, int dp) {
        Resources r = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()));
    }

}
