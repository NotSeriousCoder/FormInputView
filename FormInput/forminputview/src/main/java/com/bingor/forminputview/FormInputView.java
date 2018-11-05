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
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

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

    //标题偏移量
    private int titleOffset;
    private Rect titleRect = new Rect();
    private int textSizeTitle = 0;
    private Paint paint;
    private String title;
    private int strokeWidth = 5;
    private int radius = 20;
    private int topPadding;

    //////////////View/////////////////
    private View rootView;
    private ImageView ivLeftIcon;
    private EditText etInput;
    private ImageView ivMore;
    private View viewClick;
    private TextView tvInputReplace;
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
            title = ta.getString(R.styleable.FormInputView_title);
            textSizeTitle = ta.getDimensionPixelSize(R.styleable.FormInputView_textSizeTitle, UnitConverter.sp2px(getContext(), 14));
            strokeWidth = ta.getDimensionPixelSize(R.styleable.FormInputView_strokeWidth, UnitConverter.dip2px(getContext(), 2));
            titleOffset = ta.getDimensionPixelSize(R.styleable.FormInputView_titleOffset, UnitConverter.dip2px(getContext(), 2));
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
        tvInputReplace = rootView.findViewById(R.id.tv_m_view_form_input_p_input_replace);
        cbPswSwitch = rootView.findViewById(R.id.cb_m_view_form_input_p_psw_switch);

        switch (inputType) {
            case INPUTTYPE_NONE:
                viewClick.setVisibility(GONE);
                break;
            case INPUTTYPE_NUMBER:
                viewClick.setVisibility(GONE);
                etInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case INPUTTYPE_TEXT:
                viewClick.setVisibility(GONE);
                etInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                break;
            case INPUTTYPE_PHONE:
                viewClick.setVisibility(GONE);
                etInput.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case INPUTTYPE_NUMBERPASSWORD:
                viewClick.setVisibility(GONE);
                etInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                break;
            case INPUTTYPE_TEXTPASSWORD:
                viewClick.setVisibility(GONE);
                etInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case INPUTTYPE_LISTSELECTE:
                cbPswSwitch.setVisibility(GONE);
                tvInputReplace.setVisibility(VISIBLE);
                etInput.setVisibility(GONE);
                break;
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!TextUtils.isEmpty(title)) {
            paint.setTextSize(textSizeTitle);
            paint.getTextBounds(title, 0, title.length(), titleRect);
            topPadding = Math.max(titleRect.height() / 2, topPadding);
        }

        setPadding(0, topPadding + Math.max(titleRect.height() / 2, strokeWidth), 0, strokeWidth);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                (int) (MeasureSpec.getSize(widthMeasureSpec) - 2.2 * Math.max(strokeWidth, radius)),
                MeasureSpec.EXACTLY
        );

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                    child.getMeasuredHeight(),
                    MeasureSpec.EXACTLY
            );

            child.measure(
                    childWidthMeasureSpec,
                    childHeightMeasureSpec
            );
        }

//        Log.d("HXB", "etInput.getMeasuredHeight()===" + etInput.getMeasuredHeight());
//        ViewGroup.LayoutParams lp = viewInputReplace.getLayoutParams();
//        lp.height = etInput.getMeasuredHeight();
//        viewInputReplace.setLayoutParams(lp);
//        viewInputReplace.setMinimumHeight(etInput.getMeasuredHeight());

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            left = (int) (left + 1.1 * Math.max(strokeWidth, radius));
            right = (int) (right - 1.1 * Math.max(strokeWidth, radius));
            top = topPadding + Math.max(titleRect.height() / 2, strokeWidth);
            bottom = top + child.getMeasuredHeight();

            child.layout(
                    left,
                    top,
                    right,
                    bottom
            );
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

        //strokeWidth / 2 --- 保证外框贴边
        //topPadding --- 保证文字显示完全/符合用户自设padding
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(strokeWidth / 2, strokeWidth / 2 + topPadding, getMeasuredWidth() - strokeWidth / 2, getMeasuredHeight() - strokeWidth / 2, radius, radius, paint);
        } else {
            canvas.drawRoundRect(new RectF(strokeWidth / 2, strokeWidth / 2 + topPadding, getMeasuredWidth() - strokeWidth / 2, getMeasuredHeight() - strokeWidth / 2), radius, radius, paint);
        }

        if (!TextUtils.isEmpty(title)) {
            int lineStartX = strokeWidth + radius + titleOffset;
            int lineEndX = (int) (strokeWidth + radius + titleOffset + titleRect.width() * 1.2f);
            int textStartX = lineStartX + (lineEndX - lineStartX - titleRect.width()) / 2;
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(strokeWidth + 4);
            paint.setColor(getBgColor());
//            Log.d("HXB", (radius + titleOffset) + "," + strokeWidth + "," + (radius + titleOffset + titleRect.width()) + "," + strokeWidth);
            canvas.drawLine(lineStartX, (paint.getStrokeWidth() - 4) / 2 + topPadding, lineEndX, (paint.getStrokeWidth() - 4) / 2 + topPadding, paint);

//            int textStartX = (int) (1.5 * strokeWidth + textSizeTitle / 2);
            paint.setTextSize(textSizeTitle);
            paint.setColor(Color.parseColor("#666666"));
            Paint.FontMetricsInt fm = paint.getFontMetricsInt();
            canvas.drawText(title, textStartX, (strokeWidth / 2 + (fm.bottom - fm.ascent) / 4 + topPadding), paint);
            paint.setStrokeWidth(strokeWidth);
        }

//        paint.setStrokeWidth(3);
//        paint.setColor(Color.parseColor("#112233"));
//        int aa = topPadding + Math.max(titleRect.height(), strokeWidth);
//        canvas.drawLine(0, 2 * aa, 1080, 2 * aa, paint);
//        canvas.drawLine(0, 171, 100, 171, paint);
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
