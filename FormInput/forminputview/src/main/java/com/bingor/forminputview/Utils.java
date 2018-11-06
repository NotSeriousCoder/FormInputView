package com.bingor.forminputview;

import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by HXB on 2018/11/6.
 */
public class Utils {

    public static Rect getTextSize(int textSize, String text) {
        Rect rect = new Rect();
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect;
    }
}
