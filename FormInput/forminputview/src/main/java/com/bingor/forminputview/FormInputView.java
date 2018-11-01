package com.bingor.forminputview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by HXB on 2018/10/31.
 */
public class FormInputView extends FrameLayout {
    private Paint paint;
    private String hint;
    private float strokeWidth = 5;
    private int textOffset = 0;
    private int textSizeHint = 0;
    private int radius = 20;
    private Rect hintRext = new Rect();


    public FormInputView(@NonNull Context context) {
        this(context, null);
    }

    public FormInputView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FormInputView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        setWillNotDraw(false);

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FormInputView);
            hint = ta.getString(R.styleable.FormInputView_hint);
            textSizeHint = ta.getDimensionPixelSize(R.styleable.FormInputView_textSizeHint, UnitConverter.sp2px(getContext(), 14));
//            Log.d("HXB", "字号==" + );
            ta.recycle();
        }



    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!TextUtils.isEmpty(hint)) {
            paint.setTextSize(textSizeHint);
            paint.getTextBounds(hint, 0, hint.length(), hintRext);
        }
        if (changed) {
            for (int i = 0; i < getChildCount(); i++) {
                getChildAt(i).layout((int) (left + 4 * strokeWidth), (int) (top + strokeWidth + hintRext.height()), right, bottom);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置空心
        paint.setStyle(Paint.Style.STROKE);
        //设置笔画粗细度
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));

        float paddingTop = strokeWidth;
        if (!TextUtils.isEmpty(hint)) {
//            paint.setTextSize(textSizeHint);
//            paint.getTextBounds(hint, 0, hint.length(), hintRext);
            paddingTop += hintRext.height() / 2f;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(strokeWidth, paddingTop, getMeasuredWidth() - strokeWidth, getMeasuredHeight() - strokeWidth, radius, radius, paint);
        } else {
            canvas.drawRoundRect(new RectF(strokeWidth, paddingTop, getMeasuredWidth() - strokeWidth, getMeasuredHeight() - strokeWidth), radius, radius, paint);
        }

        if (paddingTop > strokeWidth) {
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(strokeWidth + 2);
            paint.setColor(getBgColor());
//            Log.d("HXB", (radius + textOffset) + "," + strokeWidth + "," + (radius + textOffset + hintRext.width()) + "," + strokeWidth);
            canvas.drawLine(radius + textOffset, paddingTop, radius + textOffset + hintRext.width() + textSizeHint, paddingTop, paint);

            int lineWidth = (radius + textOffset + hintRext.width() + textSizeHint) - (radius + textOffset);
//            int textStartX = (int) ((lineWidth - hintRext.width()) / 2f + radius + textOffset);
            int textStartX = (int) (textSizeHint / 2f + radius + textOffset);
            paint.setTextSize(textSizeHint);
            paint.setColor(Color.parseColor("#666666"));
            canvas.drawText(hint, textStartX, strokeWidth + hintRext.height(), paint);
        }
    }


    private int getBgColor() {
        ColorDrawable background = null;
        View view = this;
        while (background == null) {
            if (view == null) {
                break;
            }
            background = (ColorDrawable) (view.getBackground());
            if (view.getParent() instanceof View) {
                view = (View) view.getParent();
            }
        }
        if (background != null) {
            return background.getColor();
        }
        return Color.RED;
    }
}
