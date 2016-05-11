package com.cookbeans.boredapp.utils;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;

/**
 * Created by dongsj on 16/5/12.
 */
public final class StringStyleUtils {

    public static SpannableString format(Context context, String text, int style) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new TextAppearanceSpan(context, style), 0, text.length(),
                0);
        return spannableString;
    }
}
