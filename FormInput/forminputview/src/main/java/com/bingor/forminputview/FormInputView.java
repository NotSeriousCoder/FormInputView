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
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.bingor.forminputview.InputType.*;

/**
 * Created by HXB on 2018/10/31.
 */
public class FormInputView extends FrameLayout {
    public static int ICON_DEFAULT_WIDTH;

    //标题偏移量
    private int titleOffset;
    private Rect titleRect = new Rect();
    private int textSizeTitle, textSize;
    private Paint paint;
    private String title;
    private int borderWidth = 5;
    private int radius;
    private boolean showLeftIcon;
    private boolean showPswSwitch;
    private boolean showRightIcon;
    private int leftIconRes;
    private int pswSwitchIconRes;
    private int rightIconRes;
    private CharSequence hint;
    private CharSequence text;

    private int lines;
    private int maxLength;
    private int maxEms;
    private int maxLines;

    private int textColorTitle;
    private int borderColor;
    private int textColorHighlight;
    private int textColorLink;
    private int textColorHint;
    private int textColor;
    //////////////View/////////////////
    private View rootView, viewContentParent;
    private ImageView ivLeftIcon;
    private EditText etInput;
    private ImageView ivMore;
    private View viewClick;
    private TextView tvInputReplace;
    private CheckBox cbPswSwitch;
    private View btClick;


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
        ICON_DEFAULT_WIDTH = UnitConverter.dip2px(context, 26);
        paint = new Paint();
        setWillNotDraw(false);

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FormInputView);
            title = ta.getString(R.styleable.FormInputView_title);
            textSizeTitle = ta.getDimensionPixelSize(R.styleable.FormInputView_textSizeTitle, UnitConverter.sp2px(getContext(), 14));
            textSize = ta.getDimensionPixelSize(R.styleable.FormInputView_textSize, UnitConverter.sp2px(getContext(), 12));
            borderWidth = ta.getDimensionPixelSize(R.styleable.FormInputView_borderWidth, UnitConverter.dip2px(getContext(), 2));
            titleOffset = ta.getDimensionPixelSize(R.styleable.FormInputView_titleOffset, UnitConverter.dip2px(getContext(), 2));
            radius = ta.getDimensionPixelSize(R.styleable.FormInputView_radius, UnitConverter.dip2px(getContext(), 10));
            inputType = ta.getInteger(R.styleable.FormInputView_inputType, INPUTTYPE_NONE);
            showLeftIcon = ta.getBoolean(R.styleable.FormInputView_showLeftIcon, true);
            showPswSwitch = ta.getBoolean(R.styleable.FormInputView_showPswSwitch, true);
            showRightIcon = ta.getBoolean(R.styleable.FormInputView_showRightIcon, true);
            leftIconRes = ta.getResourceId(R.styleable.FormInputView_leftIcon, 0);
            pswSwitchIconRes = ta.getResourceId(R.styleable.FormInputView_pswSwitchIcon, 0);
//            switchBg = ta.getDrawable(R.styleable.FormInputView_switchBg);
            rightIconRes = ta.getResourceId(R.styleable.FormInputView_rightIcon, 0);
            borderColor = ta.getColor(R.styleable.FormInputView_borderColor, getResources().getColor(R.color.default_border_color));
            hint = ta.getString(R.styleable.FormInputView_hint);
            text = ta.getString(R.styleable.FormInputView_text);
            lines = ta.getInteger(R.styleable.FormInputView_lines, -1);
            maxLength = ta.getInteger(R.styleable.FormInputView_maxLength, -1);
            maxEms = ta.getInteger(R.styleable.FormInputView_maxEms, -1);
            maxLines = ta.getInteger(R.styleable.FormInputView_maxLines, -1);
            textColorTitle = ta.getColor(R.styleable.FormInputView_textColorTitle, getResources().getColor(R.color.gray_6));
            textColorHighlight = ta.getColor(R.styleable.FormInputView_textColorHighlight, getResources().getColor(R.color.deep_green));
            textColorLink = ta.getColor(R.styleable.FormInputView_textColorLink, getResources().getColor(R.color.deep_blue));
            textColorHint = ta.getColor(R.styleable.FormInputView_textColorHint, getResources().getColor(R.color.gray_6));
            textColor = ta.getColor(R.styleable.FormInputView_textColor, getResources().getColor(R.color.gray_6));
            ta.recycle();
        }
        findView();
        initView();
    }

    private void findView() {
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.view_form_input, this);
        ivLeftIcon = rootView.findViewById(R.id.iv_m_view_form_input_p_left_icon);
        etInput = rootView.findViewById(R.id.et_m_view_form_input_p_input);
        ivMore = rootView.findViewById(R.id.iv_m_view_form_input_p_more);
        viewClick = rootView.findViewById(R.id.view_m_view_form_input_p_click);
        tvInputReplace = rootView.findViewById(R.id.tv_m_view_form_input_p_input_replace);
        cbPswSwitch = rootView.findViewById(R.id.cb_m_view_form_input_p_psw_switch);
        btClick = rootView.findViewById(R.id.view_m_view_form_input_p_click);
        viewContentParent = rootView.findViewById(R.id.view_m_view_form_input_p_content_parent);
    }

    private void initView() {
        ivLeftIcon.setVisibility(GONE);
        etInput.setVisibility(GONE);
        ivMore.setVisibility(GONE);
        viewClick.setVisibility(GONE);
        tvInputReplace.setVisibility(GONE);
        cbPswSwitch.setVisibility(GONE);
        btClick.setVisibility(GONE);

        setupIcon();
        setupText();

        switch (inputType) {
            case INPUTTYPE_NONE:
            case INPUTTYPE_TEXT:
                viewClick.setVisibility(GONE);
                etInput.setVisibility(VISIBLE);
                if (inputType == INPUTTYPE_TEXT) {
                    etInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                }
                break;
            case INPUTTYPE_NUMBER:
                etInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                viewClick.setVisibility(GONE);
                etInput.setVisibility(VISIBLE);
                break;
            case INPUTTYPE_PHONE:
                etInput.setInputType(InputType.TYPE_CLASS_PHONE);
                if (etInput.getFilters() == null || etInput.getFilters().length == 0) {
                    etInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                }
                viewClick.setVisibility(GONE);
                etInput.setVisibility(VISIBLE);
                break;
            case INPUTTYPE_NUMBERPASSWORD:
            case INPUTTYPE_TEXTPASSWORD:
                viewClick.setVisibility(GONE);
                etInput.setVisibility(VISIBLE);
                cbPswSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            //如果是不能看到密码的情况下，
                            etInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        } else {
                            //如果是能看到密码的状态下
                            etInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        }
                    }
                });
                if (inputType == INPUTTYPE_NUMBERPASSWORD) {
                    etInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                } else {
                    etInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
            case INPUTTYPE_LISTSELECTE:
                cbPswSwitch.setVisibility(GONE);
                tvInputReplace.setVisibility(VISIBLE);
                btClick.setVisibility(VISIBLE);
                etInput.setVisibility(GONE);
                break;
        }

    }

    private void setupText() {
        tvInputReplace.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        etInput.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        if (!TextUtils.isEmpty(text)) {
            etInput.setText(text);
            tvInputReplace.setText(text);
        }
        if (!TextUtils.isEmpty(hint)) {
            etInput.setHint(hint);
            tvInputReplace.setHint(hint);
        }

        if (lines > 0) {
            etInput.setLines(lines);
        }
        if (maxLength > 0) {
            etInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        }
        if (maxEms > 0) {
            etInput.setMaxEms(maxEms);
        }
        if (maxLines > 0) {
            etInput.setMaxLines(maxLines);
        }

        etInput.setHighlightColor(textColorHighlight);
        etInput.setLinkTextColor(textColorLink);
        etInput.setHintTextColor(textColorHint);
        etInput.setTextColor(textColor);
    }


    private int switchPrimaryHeight = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setupPadding();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setupRadius();

        //子控件的宽度=父控件的宽度-2*边框笔触宽度
        //这样就不会踩线
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                (int) (MeasureSpec.getSize(widthMeasureSpec) - 2.2 * Math.max(borderWidth, radius)),
                MeasureSpec.EXACTLY
        );
        //给每个子控件设置上
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(
                    childWidthMeasureSpec,
                    heightMeasureSpec
            );
        }
        setupIconSize();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        Log.d("HXB", "left, top, right, bottom==" + left + "," + top + "," + right + "," + bottom);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            //左右将需要的空间空出来（宽度取边框笔触宽度和边框弧度的较大值）
            left = (int) (1.1 * Math.max(borderWidth, radius));
            right = (int) (getMeasuredWidth() - 1.1 * Math.max(borderWidth, radius));
            top = getPaddingTop();
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
        //设置空心，因为要画空心圆角矩形
        paint.setStyle(Paint.Style.STROKE);
        //设置笔画粗细度
        paint.setStrokeWidth(borderWidth);
        paint.setColor(borderColor);
        //开启抗锯齿
        paint.setAntiAlias(true);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));

        /**
         * 因为绘画的时候，设定的xy点是笔触的中点
         * 所以left right bottom都需要+-笔触宽度的一半(borderWidth / 2)，保证画出来的边框不会有一边在视线以外并且贴边
         * top是例外，因为有文字，所以要getPaddingTop()/2
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(borderWidth / 2, getPaddingTop() / 2, getMeasuredWidth() - borderWidth / 2, getMeasuredHeight() - borderWidth / 2, radius, radius, paint);
        } else {
            canvas.drawRoundRect(new RectF(borderWidth / 2, getPaddingTop() / 2, getMeasuredWidth() - borderWidth / 2, getMeasuredHeight() - borderWidth / 2), radius, radius, paint);
        }

        if (!TextUtils.isEmpty(title)) {
            titleRect = Utils.getTextSize(textSizeTitle, title);
            /**
             * 画一条线（颜色{@link #getBgColor()}），作为空白处用来画title
             * 线的起始点X值：笔触宽度+弧度值+用户自定义标题偏移量
             */
            int lineStartX = borderWidth + radius + titleOffset;
            //线的终点X值，则需要计算文字宽度来决定
            int lineEndX = lineStartX;
            //这里计算的是控件剩余可用于绘制的宽度，如果根本没地方了，直接结束绘制
            int availableWidth = getMeasuredWidth() - borderWidth - radius - lineStartX;
            if (availableWidth <= 0) {
                return;
            }
            int textWidth = titleRect.width();
            //如果标题过宽，则裁剪标题
            //-8dp是因为需要预留空白，防止标题和边框过于靠近
            if (textWidth > availableWidth - UnitConverter.dip2px(getContext(), 8)) {
                title = getSuitableText(title, textSizeTitle, availableWidth - UnitConverter.dip2px(getContext(), 6));
                titleRect = Utils.getTextSize(textSizeTitle, title);
                textWidth = titleRect.width();
            }
            if (textWidth * 0.2f > UnitConverter.dip2px(getContext(), 4)) {
                lineEndX += textWidth + UnitConverter.dip2px(getContext(), 4);
            } else {
                lineEndX += textWidth * 1.2f;
            }
            //标题起始点X值=空白线起始值X +( 空白线长度 - 标题长度 )/2
            //( 空白线长度 - 标题长度 )/2 就是留白的长度
            int textStartX = lineStartX + (lineEndX - lineStartX - textWidth) / 2;

            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(borderWidth);
            paint.setColor(getBgColor());
            canvas.drawLine(lineStartX, getPaddingTop() / 2, lineEndX, getPaddingTop() / 2, paint);

            paint.setTextSize(textSizeTitle);
            paint.setColor(textColorTitle);
            Paint.FontMetricsInt fm = paint.getFontMetricsInt();
            //文字的绘制，并不是以字高的中点为(x,y)，而是以baseLine为准，所以位置并不好算，这里也是瞎蒙
            canvas.drawText(title, textStartX, ((fm.bottom - fm.ascent) / 3 + getPaddingTop() / 2), paint);
        }
    }

    /**
     * 设置边框弧度
     */
    private void setupRadius() {
        //控制弧度半径不要超过一半的控件高度
        int max = (getMeasuredHeight() - getPaddingTop() - getPaddingBottom()) / 2 + borderWidth * 2;
        if (radius > max) {
            radius = max;
        }
    }

    private void setupIcon() {
        if (showLeftIcon) {
            ivLeftIcon.setVisibility(VISIBLE);
            if (leftIconRes != 0) {
                ivLeftIcon.setImageResource(leftIconRes);
            }
        } else {
            ivLeftIcon.setVisibility(GONE);
        }

        if (inputType == INPUTTYPE_NUMBERPASSWORD || inputType == INPUTTYPE_TEXTPASSWORD) {
            if (showPswSwitch) {
                if (pswSwitchIconRes != 0) {
                    cbPswSwitch.setBackgroundResource(pswSwitchIconRes);
                }
                cbPswSwitch.setVisibility(VISIBLE);
            } else {
                cbPswSwitch.setVisibility(GONE);
            }
        } else if (inputType == INPUTTYPE_LISTSELECTE) {
            if (showRightIcon) {
                if (rightIconRes != 0) {
                    ivMore.setImageResource(rightIconRes);
                }
                ivMore.setVisibility(VISIBLE);
            } else {
                ivMore.setVisibility(GONE);
            }
        }
    }

    /**
     * 设置每个图标的大小/位置
     */
    private void setupIconSize() {
        LinearLayout.LayoutParams lpParent = (LinearLayout.LayoutParams) viewContentParent.getLayoutParams();
        LinearLayout.LayoutParams lpSwitch = (LinearLayout.LayoutParams) cbPswSwitch.getLayoutParams();
        if (radius == 0) {
            //边框弧度为0，增加6dp的左右margin，防止贴边
            lpParent.leftMargin = UnitConverter.dip2px(getContext(), 6);
            lpSwitch.rightMargin = UnitConverter.dip2px(getContext(), 6);
        } else {
            lpParent.leftMargin = 0;
            lpSwitch.rightMargin = 0;
        }
        viewContentParent.setLayoutParams(lpParent);
        cbPswSwitch.setLayoutParams(lpSwitch);
        if (ivLeftIcon.getVisibility() == VISIBLE || ivMore.getVisibility() == VISIBLE || cbPswSwitch.getVisibility() == VISIBLE) {
            String text = "啦";
            Rect rect = Utils.getTextSize(textSize, text);
            ViewGroup.LayoutParams lp = null;
            /**
             * 在图标显示的时候，将图标宽高设置为字高的1.2倍（最大不大于{@link ICON_DEFAULT_WIDTH}），防止图标和文字大小悬殊
             */
            int rectHeight = (int) (rect.height() * 1.2f);
            if (rectHeight > ICON_DEFAULT_WIDTH) {
                rectHeight = ICON_DEFAULT_WIDTH;
            }
            lp = ivLeftIcon.getLayoutParams();
            lp.height = rectHeight;
            lp.width = rectHeight;
            ivLeftIcon.setLayoutParams(lp);

            lp = ivMore.getLayoutParams();
            lp.height = rectHeight;
            lp.width = rectHeight;
            ivMore.setLayoutParams(lp);
//            Log.d("HXB", "ivMore------lp.height==" + lp.height + "  lp.height==" + lp.height);


            if (switchPrimaryHeight == 0) {
                switchPrimaryHeight = cbPswSwitch.getMeasuredHeight();
            }
            lp = cbPswSwitch.getLayoutParams();
            if (rect.height() * 2 < switchPrimaryHeight) {
                lp.height = (int) (rect.height() * 2f);
                lp.width = (int) (rect.height() * 2f);
            } else {
                lp.height = (int) (rect.height() * 1.8f);
                lp.width = (int) (rect.height() * 1.8f);
            }
//            Log.d("HXB", "lp.height==" + lp.height + "  lp.height==" + lp.height);
            cbPswSwitch.setLayoutParams(lp);
        }
    }

    /**
     * 初始化上下padding，存在两种情况
     * 1.文字高度>边框笔触宽度
     * 2.文字高度<边框笔触宽度
     * 取较大值为topPadding
     * bottomPadding则固定是边框的笔触宽度即可
     */
    private void setupPadding() {
        if (!TextUtils.isEmpty(title)) {
            titleRect = Utils.getTextSize(textSizeTitle, title);
//            topPadding = Math.max(titleRect.height(), borderWidth);
            setPadding(0, Math.max(titleRect.height(), borderWidth), 0, borderWidth);
        } else {
            setPadding(0, borderWidth, 0, borderWidth);
//            topPadding = borderWidth;
        }
    }

    /**
     * 裁剪文字
     *
     * @param text           文字
     * @param textSize       字号
     * @param availableWidth 合适的宽度
     * @return
     */
    private String getSuitableText(String text, int textSize, int availableWidth) {
        while (Utils.getTextSize(textSize, text).width() > availableWidth) {
            text = text.substring(0, text.length() - 1);
        }
        text += "...";
        while (Utils.getTextSize(textSize, text).width() > availableWidth) {
            text = text.substring(0, text.length() - 1);
        }
        return text;
    }

    /**
     * 获取背景颜色，当前没有的话，获取父控件的背景颜色，如果也没有，返回白色
     *
     * @return
     */
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
        return Color.WHITE;
    }


    //////////////////////////////////get set///////////////////////////////////////////////////////
    public void setOnItemClickListener(OnClickListener onItemClickListener) {
        if (tvInputReplace != null) {
            btClick.setOnClickListener(onItemClickListener);
        }
    }

    public int getTitleOffset() {
        return titleOffset;
    }

    public void setTitleOffset(int titleOffset) {
        this.titleOffset = titleOffset;
//        setupPadding();
        invalidate();
    }

    public int getTextSizeTitle() {
        return textSizeTitle;
    }

    public void setTextSizeTitle(int textSizeTitle) {
        this.textSizeTitle = textSizeTitle;
        setupPadding();
//        invalidate();
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        tvInputReplace.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        etInput.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        setupIconSize();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        invalidate();
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        setupPadding();
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        setupRadius();
        setupIconSize();
    }

    public boolean isShowLeftIcon() {
        return showLeftIcon;
    }

    public void setShowLeftIcon(boolean showLeftIcon) {
        this.showLeftIcon = showLeftIcon;
        setupIcon();
    }

    public boolean isShowPswSwitch() {
        return showPswSwitch;
    }

    public void setShowPswSwitch(boolean showPswSwitch) {
        this.showPswSwitch = showPswSwitch;
        setupIcon();
    }

    public boolean isShowRightIcon() {
        return showRightIcon;
    }

    public void setShowRightIcon(boolean showRightIcon) {
        this.showRightIcon = showRightIcon;
        setupIcon();
    }

    public int getLeftIconRes() {
        return leftIconRes;
    }

    public void setLeftIconRes(int leftIconRes) {
        this.leftIconRes = leftIconRes;
        setupIcon();
    }

    public int getPswSwitchIconRes() {
        return pswSwitchIconRes;
    }

    public void setPswSwitchIconRes(int pswSwitchIconRes) {
        this.pswSwitchIconRes = pswSwitchIconRes;
        setupIcon();
    }

    public int getRightIconRes() {
        return rightIconRes;
    }

    public void setRightIconRes(int rightIconRes) {
        this.rightIconRes = rightIconRes;
        setupIcon();
    }

    public CharSequence getHint() {
        return hint;
    }

    public void setHint(CharSequence hint) {
        this.hint = hint;
        setupText();
    }

    public CharSequence getText() {
        return text;
    }

    public void setText(CharSequence text) {
        this.text = text;
        setupText();
    }

    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
        setupText();
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        setupText();
    }

    public int getMaxEms() {
        return maxEms;
    }

    public void setMaxEms(int maxEms) {
        this.maxEms = maxEms;
        setupText();
    }

    public int getMaxLines() {
        return maxLines;
    }

    public void setMaxLines(int maxLines) {
        this.maxLines = maxLines;
        setupText();
    }

    public int getTextColorTitle() {
        return textColorTitle;
    }

    public void setTextColorTitle(int textColorTitle) {
        this.textColorTitle = textColorTitle;
        setupText();
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        invalidate();
    }

    public int getTextColorHighlight() {
        return textColorHighlight;
    }

    public void setTextColorHighlight(int textColorHighlight) {
        this.textColorHighlight = textColorHighlight;
        setupText();
    }

    public int getTextColorLink() {
        return textColorLink;
    }

    public void setTextColorLink(int textColorLink) {
        this.textColorLink = textColorLink;
        setupText();
    }

    public int getTextColorHint() {
        return textColorHint;
    }

    public void setTextColorHint(int textColorHint) {
        this.textColorHint = textColorHint;
        setupText();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        setupText();
    }

    public ImageView getIvLeftIcon() {
        return ivLeftIcon;
    }

    public void setIvLeftIcon(ImageView ivLeftIcon) {
        this.ivLeftIcon = ivLeftIcon;
        setupIcon();
    }

    public EditText getEtInput() {
        return etInput;
    }

    public ImageView getIvMore() {
        return ivMore;
    }

    public View getViewClick() {
        return viewClick;
    }

    public TextView getTvInputReplace() {
        return tvInputReplace;
    }

    public CheckBox getCbPswSwitch() {
        return cbPswSwitch;
    }

    public View getBtClick() {
        return btClick;
    }

    public void setBtClick(View btClick) {
        this.btClick = btClick;
    }

    public int getInputType() {
        return inputType;
    }

    public void setInputType(@com.bingor.forminputview.InputType int inputType) {
        this.inputType = inputType;
        initView();
    }
    //////////////////////////////////get set///////////////////////////////////////////////////////
}
