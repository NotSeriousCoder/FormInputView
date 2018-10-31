package com.bingor.forminputview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
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

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FormInputView);
        hint = ta.getString(R.styleable.FormInputView_hint);
        ta.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!TextUtils.isEmpty(hint)) {
            Rect hintRext = new Rect();
            paint.getTextBounds(hint, 0, hint.length(), hintRext);

//            canvas.clipRect(new RectF(strokeWidth / 2 + 10, 0, strokeWidth / 2 + 10 + hintRext.width(), strokeWidth / 2 + 10 + hintRext.width()));
//            canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);

            Path path = new Path();
            path.moveTo(0, 0);
            path.lineTo(strokeWidth + 30, 0);
            path.lineTo(strokeWidth + 30, 10);
            path.lineTo(strokeWidth + 30 + hintRext.width(), 10);
            path.lineTo(strokeWidth + 30 + hintRext.width(), 0);
            path.lineTo(getMeasuredWidth(), 0);
            path.lineTo(getMeasuredWidth(), getMeasuredHeight());
            path.lineTo(0, getMeasuredHeight());
            path.lineTo(0, 0);
            canvas.clipPath(path);
        }


        Log.d("HXB", "onDraw(Canvas canvas)===");

        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(strokeWidth / 2, strokeWidth / 2, getMeasuredWidth() - strokeWidth / 2, getMeasuredHeight() - strokeWidth / 2, 20, 20, paint);
        } else {
            canvas.drawRoundRect(new RectF(strokeWidth / 2, strokeWidth / 2, getMeasuredWidth() - strokeWidth / 2, getMeasuredHeight() - strokeWidth / 2), 20, 20, paint);
        }


//        canvas.restore();
    }
}
