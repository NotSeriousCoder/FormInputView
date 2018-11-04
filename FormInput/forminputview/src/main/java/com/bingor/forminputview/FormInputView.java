package com.bingor.forminputview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by HXB on 2018/10/31.
 */
public class FormInputView extends FrameLayout {
    public static final int INPUTTYPE_NONE = 0;
    public static final int INPUTTYPE_NUMBER = 1;
    public static final int INPUTTYPE_TEXT = 2;
    public static final int INPUTTYPE_PHONE = 3;
    public static final int INPUTTYPE_NUMBERPASSWORD = 4;
    public static final int INPUTTYPE_TEXTPASSWORD = 5;
    public static final int INPUTTYPE_LISTSELECTE = 6;


    private Paint paint;
    private String hint;
    private int strokeWidth = 5;
    private int textOffset = 0;
    private int textSizeHint = 0;
    private int radius = 20;
    private Rect hintRext = new Rect();
    private int topPadding;

    //////////////View/////////////////
    private View rootView;
    private ImageView ivLeftIcon;
    private EditText etInput;
    private ImageView ivMore;
    private View viewClick;
    private CheckBox cbPswSwitch;


    ///////////////////////////////////////
    private int inputType = 0;

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
            strokeWidth = ta.getDimensionPixelSize(R.styleable.FormInputView_strokeWidth, UnitConverter.dip2px(getContext(), 2));
            Log.d("HXB", "strokeWidth==" + strokeWidth);
            Log.d("HXB", "strokeWidth222==" + ta.getDimension(R.styleable.FormInputView_strokeWidth, UnitConverter.dip2px(getContext(), 2)));
            inputType = ta.getInteger(R.styleable.FormInputView_inputType, INPUTTYPE_TEXT);
            topPadding = ta.getInteger(R.styleable.FormInputView_textPaddingTop, 0);
            ta.recycle();
        }
        initView();
    }

    private void initView() {
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.view_form_input, this);
        ivLeftIcon = rootView.findViewById(R.id.iv_m_view_form_input_p_left_icon);
        etInput = rootView.findViewById(R.id.et_m_view_form_input_p_input);
        ivMore = rootView.findViewById(R.id.iv_m_view_form_input_p_more);
        viewClick = rootView.findViewById(R.id.view_m_view_form_input_p_click);
        cbPswSwitch = rootView.findViewById(R.id.cb_m_view_form_input_p_psw_switch);

        switch (inputType) {
            case INPUTTYPE_NONE:
                viewClick.setVisibility(GONE);

                break;
            case INPUTTYPE_NUMBER:
                viewClick.setVisibility(GONE);
//                etInput.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);
                break;
            case INPUTTYPE_TEXT:
                viewClick.setVisibility(GONE);
//                etInput.setInputType(InputType.TYPE_CLASS_TEXT);

                break;
            case INPUTTYPE_PHONE:
                viewClick.setVisibility(GONE);
//                etInput.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);

                break;
            case INPUTTYPE_NUMBERPASSWORD:
                viewClick.setVisibility(GONE);
//                etInput.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);

                break;
            case INPUTTYPE_TEXTPASSWORD:
                viewClick.setVisibility(GONE);
//                etInput.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

                break;
            case INPUTTYPE_LISTSELECTE:
                cbPswSwitch.setVisibility(GONE);

                break;
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!TextUtils.isEmpty(hint)) {
            paint.setTextSize(textSizeHint);
            paint.getTextBounds(hint, 0, hint.length(), hintRext);
            topPadding = Math.max(hintRext.height() / 2, topPadding);
        }
        setPadding(2 * Math.max(strokeWidth, radius),
                Math.max(strokeWidth, hintRext.height()) + hintRext.height() / 2 + topPadding,
                2 * Math.max(strokeWidth, radius),
                strokeWidth);
//        (int) (3 * strokeWidth)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        Log.d("HXB", "wid,hei==" + MeasureSpec.getSize(widthMeasureSpec) + "," + MeasureSpec.getSize(heightMeasureSpec));
//        for (int i = 0; i < getChildCount(); i++) {
//            getChildAt(i).measure(MeasureSpec.makeMeasureSpec(300, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(3000, MeasureSpec.UNSPECIFIED));
////            getChildAt(i).measure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec) - 2 * radius, MeasureSpec.getMode(widthMeasureSpec)), MeasureSpec.makeMeasureSpec(100, MeasureSpec.UNSPECIFIED));
//        }
//        int height = 0;
//        for (int i = 0; i < getChildCount(); i++) {
//            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
//            height = Math.max(height, getChildAt(i).getMeasuredHeight());
//        }

//        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height + 2 * radius + hintRext.height(), MeasureSpec.getMode(heightMeasureSpec)));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//        if (changed) {
        top = 0;


        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            top = (getMeasuredHeight() - topPadding - child.getMeasuredHeight()) / 2 + top;
            bottom = top + child.getMeasuredHeight();
//                getChildAt(i).layout((int) (left + 4 * strokeWidth), (int) (top + strokeWidth + hintRext.height()), right, bottom);
            Log.d("HXB", "l,t,r,b==" + left + "," + top + "," + right + "," + bottom);
//            top = (int) (top + Math.max(strokeWidth, hintRext.height()) + hintRext.height() / 2 + topPadding * 1.5f);
            getChildAt(i).layout(
                    left + 2 * Math.max(strokeWidth, radius),
                    getPaddingTop(),
                    right - 2 * Math.max(strokeWidth, radius),
                    /*(int) (bottom - 3 * strokeWidth) + 100*/
                    getPaddingTop() + child.getMeasuredHeight()
            );
        }
//        }
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


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(strokeWidth, paint.getStrokeWidth() + topPadding, getMeasuredWidth() - strokeWidth, getMeasuredHeight() - strokeWidth, radius, radius, paint);
        } else {
            canvas.drawRoundRect(new RectF(strokeWidth, paint.getStrokeWidth() + topPadding, getMeasuredWidth() - strokeWidth, getMeasuredHeight() - strokeWidth), radius, radius, paint);
        }

        if (!TextUtils.isEmpty(hint)) {
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(strokeWidth + 4);
            paint.setColor(getBgColor());
//            Log.d("HXB", (radius + textOffset) + "," + strokeWidth + "," + (radius + textOffset + hintRext.width()) + "," + strokeWidth);
            canvas.drawLine(strokeWidth * 1.5f + textOffset, paint.getStrokeWidth() - 4 + topPadding, strokeWidth * 1.5f + textOffset + hintRext.width() + textSizeHint, paint.getStrokeWidth() - 4 + topPadding, paint);

            int textStartX = (int) (1.5 * strokeWidth + textSizeHint / 2);
            paint.setTextSize(textSizeHint);
            paint.setColor(Color.parseColor("#666666"));
            Paint.FontMetricsInt fm = paint.getFontMetricsInt();
            canvas.drawText(hint, textStartX, (strokeWidth + (fm.bottom - fm.ascent) / 3 + topPadding), paint);
            paint.setStrokeWidth(strokeWidth);
        }

        paint.setStrokeWidth(30);
        paint.setColor(Color.parseColor("#112233"));
        canvas.drawLine(0, getPaddingTop(), 100, getPaddingTop(), paint);
        canvas.drawLine(0, getMeasuredHeight() , 100, getMeasuredHeight() , paint);
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
