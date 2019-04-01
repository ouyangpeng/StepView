package com.baoyachi.stepview;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * 竖向的StepView
 */
public class VerticalStepView extends LinearLayout implements VerticalStepViewIndicator.OnDrawIndicatorListener {
    private RelativeLayout mTextContainer;
    private VerticalStepViewIndicator mStepsViewIndicator;
    private List<String> mTexts;
    private int mCompletingPosition;
    /**
     * 定义默认未完成文字的颜色;
     */
    private int mUnComplectedTextColor = ContextCompat.getColor(getContext(), R.color.uncompleted_text_color);
    /**
     * 定义默认完成文字的颜色;
     */
    private int mComplectedTextColor = ContextCompat.getColor(getContext(), android.R.color.white);
    /**
     * 定义默认文字大小
     */
    private int mTextSize = 14;

    private TextView mTextView;


    public VerticalStepView(Context context) {
        this(context, null);
    }

    public VerticalStepView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalStepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.widget_vertical_stepsview, this);
        mStepsViewIndicator = rootView.findViewById(R.id.steps_indicator);
        mStepsViewIndicator.setOnDrawListener(this);
        mTextContainer = rootView.findViewById(R.id.rl_text_container);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 设置显示的文字
     *
     * @param texts 显示的文字
     */
    public VerticalStepView setStepViewTexts(List<String> texts) {
        mTexts = texts;
        if (texts != null) {
            mStepsViewIndicator.setStepNum(mTexts.size());
        } else {
            mStepsViewIndicator.setStepNum(0);
        }
        return this;
    }

    /**
     * 设置正在进行的position
     *
     * @param completingPosition 正在进行的position
     */
    public VerticalStepView setStepsViewIndicatorCompletingPosition(int completingPosition) {
        mCompletingPosition = completingPosition;
        mStepsViewIndicator.setComplectingPosition(completingPosition);
        return this;
    }

    /**
     * 设置未完成文字的颜色
     *
     * @param unComplectedTextColor 未完成文字的颜色
     */
    public VerticalStepView setStepViewUnComplectedTextColor(int unComplectedTextColor) {
        mUnComplectedTextColor = unComplectedTextColor;
        return this;
    }

    /**
     * 设置完成文字的颜色
     *
     * @param complectedTextColor 完成文字的颜色
     */
    public VerticalStepView setStepViewComplectedTextColor(int complectedTextColor) {
        this.mComplectedTextColor = complectedTextColor;
        return this;
    }

    /**
     * 设置StepsViewIndicator未完成线的颜色
     *
     * @param unCompletedLineColor StepsViewIndicator未完成线的颜色
     */
    public VerticalStepView setStepsViewIndicatorUnCompletedLineColor(int unCompletedLineColor) {
        mStepsViewIndicator.setUnCompletedLineColor(unCompletedLineColor);
        return this;
    }

    /**
     * 设置StepsViewIndicator完成线的颜色
     *
     * @param completedLineColor StepsViewIndicator完成线的颜色
     */
    public VerticalStepView setStepsViewIndicatorCompletedLineColor(int completedLineColor) {
        mStepsViewIndicator.setCompletedLineColor(completedLineColor);
        return this;
    }

    /**
     * 设置StepsViewIndicator默认图片
     *
     * @param defaultIcon StepsViewIndicator默认图片
     */
    public VerticalStepView setStepsViewIndicatorDefaultIcon(Drawable defaultIcon) {
        mStepsViewIndicator.setDefaultIcon(defaultIcon);
        return this;
    }

    /**
     * 设置StepsViewIndicator已完成图片
     *
     * @param completeIcon StepsViewIndicator已完成图片
     */
    public VerticalStepView setStepsViewIndicatorCompleteIcon(Drawable completeIcon) {
        mStepsViewIndicator.setCompleteIcon(completeIcon);
        return this;
    }

    /**
     * 设置StepsViewIndicator正在进行中的图片
     *
     * @param attentionIcon StepsViewIndicator正在进行中的图片
     */
    public VerticalStepView setStepsViewIndicatorAttentionIcon(Drawable attentionIcon) {
        mStepsViewIndicator.setAttentionIcon(attentionIcon);
        return this;
    }

    /**
     * is reverse draw 是否倒序画
     *
     * @param isReverseDraw 是否倒序画
     */
    public VerticalStepView reverseDraw(boolean isReverseDraw) {
        this.mStepsViewIndicator.reverseDraw(isReverseDraw);
        return this;
    }

    /**
     * 设置线间距的比例系数
     *
     * @param linePaddingProportion 线间距的比例系数
     */
    public VerticalStepView setLinePaddingProportion(float linePaddingProportion) {
        this.mStepsViewIndicator.setIndicatorLinePaddingProportion(linePaddingProportion);
        return this;
    }


    /**
     * 设置文本大小
     *
     * @param textSize 文本大小
     */
    public VerticalStepView setTextSize(int textSize) {
        if (textSize > 0) {
            mTextSize = textSize;
        }
        return this;
    }

    @Override
    public void onDrawIndicator() {
        if (mTextContainer != null) {
            //clear ViewGroup
            mTextContainer.removeAllViews();
            List<Float> complectedXPosition = mStepsViewIndicator.getCircleCenterPointPositionList();
            if (mTexts != null && complectedXPosition != null && complectedXPosition.size() > 0) {
                for (int i = 0; i < mTexts.size(); i++) {
                    mTextView = new TextView(getContext());
                    mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
                    mTextView.setText(mTexts.get(i));
                    mTextView.setY(complectedXPosition.get(i) - mStepsViewIndicator.getCircleRadius() / 2);
                    mTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                    if (i <= mCompletingPosition) {
                        mTextView.setTypeface(null, Typeface.BOLD);
                        mTextView.setTextColor(mComplectedTextColor);
                    } else {
                        mTextView.setTextColor(mUnComplectedTextColor);
                    }
                    mTextContainer.addView(mTextView);
                }
            }
        }
    }
}
