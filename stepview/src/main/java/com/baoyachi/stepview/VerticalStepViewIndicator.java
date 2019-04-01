package com.baoyachi.stepview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 竖向的 StepsViewIndicator 指示器
 */
public class VerticalStepViewIndicator extends View {
    private final String TAG_NAME = this.getClass().getSimpleName();

    /**
     * 定义默认的高度
     */
    private int defaultStepIndicatorNum = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
    /**
     * 完成线的高度     definition completed line height
     */
    private float mCompletedLineHeight;
    /**
     * 圆的半径  definition circle radius
     */
    private float mCircleRadius;
    /**
     * 完成的默认图片    definition default completed icon
     */
    private Drawable mCompleteIcon;
    /**
     * 正在进行的默认图片     definition default underway icon
     */
    private Drawable mAttentionIcon;
    /**
     * 默认的背景图  definition default unCompleted icon
     */
    private Drawable mDefaultIcon;
    /**
     * 该View的X轴的中间位置
     */
    private float mCenterX;
    private float mLeftY;
    private float mRightY;
    /**
     * 当前有几部流程    there are currently few step
     */
    private int mStepNum = 0;
    /**
     * 两条连线之间的间距  definition the spacing between the two circles
     */
    private float mLinePadding;
    /**
     * 定义所有圆的圆心点位置的集合 definition all of circles center point list
     */
    private List<Float> mCircleCenterPointPositionList;
    /**
     * 未完成Paint  definition mUnCompletedPaint
     */
    private Paint mUnCompletedPaint;
    /**
     * 完成paint      definition mCompletedPaint
     */
    private Paint mCompletedPaint;
    /**
     * 定义默认未完成线的颜色  definition mUnCompletedLineColor
     */
    private int mUnCompletedLineColor = ContextCompat.getColor(getContext(), R.color.uncompleted_color);
    /**
     * 定义默认完成线的颜色      definition mCompletedLineColor
     */
    private int mCompletedLineColor = Color.WHITE;
    /**
     * 正在进行position   underway position
     */
    private int mCompletingPosition;
    /**
     * 画笔
     */
    private Path mPath;
    /**
     * View绘制的监听回调
     */
    private OnDrawIndicatorListener mOnDrawListener;

    private Rect mRect;
    /**
     * 这个控件的动态高度
     */
    private int mHeight;
    /**
     * 是否倒序画
     */
    private boolean mIsReverseDraw;

    public VerticalStepViewIndicator(Context context) {
        this(context, null);
    }

    public VerticalStepViewIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalStepViewIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * init
     */
    private void init() {
        mPath = new Path();
        mCircleCenterPointPositionList = new ArrayList<>();

        mUnCompletedPaint = new Paint();
        mCompletedPaint = new Paint();
        mUnCompletedPaint.setAntiAlias(true);
        mUnCompletedPaint.setColor(mUnCompletedLineColor);
        mUnCompletedPaint.setStyle(Paint.Style.STROKE);
        mUnCompletedPaint.setStrokeWidth(2);
        //DashPathEffect:DashPathEffect是PathEffect类的一个子类,可以使paint画出类似虚线的样子,并且可以任意指定虚实的排列方式.
        PathEffect mEffects = new DashPathEffect(new float[]{8, 8, 8, 8}, 1);
        mUnCompletedPaint.setPathEffect(mEffects);

        mCompletedPaint.setAntiAlias(true);
        mCompletedPaint.setColor(mCompletedLineColor);
        mCompletedPaint.setStyle(Paint.Style.STROKE);
        mCompletedPaint.setStrokeWidth(2);
        mCompletedPaint.setStyle(Paint.Style.FILL);

        //已经完成线的宽高 set mCompletedLineHeight
        mCompletedLineHeight = 0.05f * defaultStepIndicatorNum;
        //圆的半径  set mCircleRadius
        mCircleRadius = 0.28f * defaultStepIndicatorNum;
        //线与线之间的间距    set mLinePadding
        mLinePadding = 0.85f * defaultStepIndicatorNum;

        //已经完成的icon
        mCompleteIcon = ContextCompat.getDrawable(getContext(), R.drawable.complted);
        //正在进行的icon
        mAttentionIcon = ContextCompat.getDrawable(getContext(), R.drawable.attention);
        //未完成的icon
        mDefaultIcon = ContextCompat.getDrawable(getContext(), R.drawable.default_icon);
        //默认 反向绘制
        mIsReverseDraw = true;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG_NAME, "onMeasure");
        int width = defaultStepIndicatorNum;
        mHeight = 0;
        if (mStepNum > 0) {
            //dynamic measure VerticalStepViewIndicator height
            mHeight = (int) (getPaddingTop()
                    + getPaddingBottom()
                    + mCircleRadius * 2 * mStepNum
                    + (mStepNum - 1) * mLinePadding);
        }
        if (MeasureSpec.UNSPECIFIED != MeasureSpec.getMode(widthMeasureSpec)) {
            width = Math.min(width, MeasureSpec.getSize(widthMeasureSpec));
        }
        setMeasuredDimension(width, mHeight);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG_NAME, "onSizeChanged");
        mCenterX = getWidth() / 2;
        mLeftY = mCenterX - (mCompletedLineHeight / 2);
        mRightY = mCenterX + (mCompletedLineHeight / 2);

        for (int i = 0; i < mStepNum; i++) {
            //是否反向绘制 VerticalStepViewIndicator
            if (mIsReverseDraw) {
                mCircleCenterPointPositionList.add(mHeight - (mCircleRadius + i * mCircleRadius * 2 + i * mLinePadding));
            } else {
                mCircleCenterPointPositionList.add(mCircleRadius + i * mCircleRadius * 2 + i * mLinePadding);
            }
        }

        if (mOnDrawListener != null) {
            //指示器View绘制的监听回调  接着绘制文本
            mOnDrawListener.onDrawIndicator();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mOnDrawListener != null) {
            //指示器View绘制的监听回调  接着绘制文本
            mOnDrawListener.onDrawIndicator();
        }
        mUnCompletedPaint.setColor(mUnCompletedLineColor);
        mCompletedPaint.setColor(mCompletedLineColor);

        //-----------------------画线-------draw line-----------------------------------------------
        for (int i = 0; i < mCircleCenterPointPositionList.size() - 1; i++) {
            //前一个ComplectedXPosition
            final float preComplectedYPosition = mCircleCenterPointPositionList.get(i);
            //后一个ComplectedXPosition
            final float afterComplectedYPosition = mCircleCenterPointPositionList.get(i + 1);
            //判断在完成之前的所有点
            if (i < mCompletingPosition) {
                //判断在完成之前的所有点，画完成的线，这里是矩形,很细的矩形，类似线，为了做区分，好看些
                if (mIsReverseDraw) {
                    canvas.drawRect(mLeftY,
                            afterComplectedYPosition + mCircleRadius - 10,
                            mRightY,
                            preComplectedYPosition - mCircleRadius + 10,
                            mCompletedPaint);
                } else {
                    canvas.drawRect(mLeftY,
                            preComplectedYPosition + mCircleRadius - 10,
                            mRightY,
                            afterComplectedYPosition - mCircleRadius + 10,
                            mCompletedPaint);
                }
            } else {
                if (mIsReverseDraw) {
                    mPath.moveTo(mCenterX, afterComplectedYPosition + mCircleRadius);
                    mPath.lineTo(mCenterX, preComplectedYPosition - mCircleRadius);
                    canvas.drawPath(mPath, mUnCompletedPaint);
                } else {
                    mPath.moveTo(mCenterX, preComplectedYPosition + mCircleRadius);
                    mPath.lineTo(mCenterX, afterComplectedYPosition - mCircleRadius);
                    canvas.drawPath(mPath, mUnCompletedPaint);
                }

            }
        }
        //-----------------------画线-------draw line-----------------------------------------------

        //-----------------------画图标-----draw icon-----------------------------------------------
        for (int i = 0; i < mCircleCenterPointPositionList.size(); i++) {
            final float currentComplectedYPosition = mCircleCenterPointPositionList.get(i);
            mRect = new Rect((int) (mCenterX - mCircleRadius),
                    (int) (currentComplectedYPosition - mCircleRadius),
                    (int) (mCenterX + mCircleRadius),
                    (int) (currentComplectedYPosition + mCircleRadius));
            if (i < mCompletingPosition) {
                mCompleteIcon.setBounds(mRect);
                mCompleteIcon.draw(canvas);
            } else if (i == mCompletingPosition && mCircleCenterPointPositionList.size() != 1) {
                mCompletedPaint.setColor(Color.WHITE);
                canvas.drawCircle(mCenterX, currentComplectedYPosition, mCircleRadius * 1.1f, mCompletedPaint);
                mAttentionIcon.setBounds(mRect);
                mAttentionIcon.draw(canvas);
            } else {
                mDefaultIcon.setBounds(mRect);
                mDefaultIcon.draw(canvas);
            }
        }
        //-----------------------画图标-----draw icon-----------------------------------------------
    }

    /**
     * 设置View绘制的监听回调
     *
     * @param onDrawListener View绘制的监听回调
     */
    public void setOnDrawListener(OnDrawIndicatorListener onDrawListener) {
        mOnDrawListener = onDrawListener;
    }

    /**
     * 获取圆的半径
     *
     * @return 圆的半径
     */
    public float getCircleRadius() {
        return mCircleRadius;
    }

    /**
     * 得到所有圆点所在的位置
     *
     * @return 所有圆点所在的位置
     */
    public List<Float> getCircleCenterPointPositionList() {
        return mCircleCenterPointPositionList;
    }

    /**
     * 设置流程步数
     *
     * @param stepNum 流程步数
     */
    public void setStepNum(int stepNum) {
        this.mStepNum = stepNum;
        requestLayout();
    }

    /**
     * 设置线间距的比例系数
     *
     * @param linePaddingProportion 线间距的比例系数
     */
    public void setIndicatorLinePaddingProportion(float linePaddingProportion) {
        this.mLinePadding = linePaddingProportion * defaultStepIndicatorNum;
    }

    /**
     * 设置正在进行position
     *
     * @param completingPosition 正在进行position
     */
    public void setComplectingPosition(int completingPosition) {
        this.mCompletingPosition = completingPosition;
        requestLayout();
    }

    /**
     * 设置未完成线的颜色
     *
     * @param unCompletedLineColor 未完成线的颜色
     */
    public void setUnCompletedLineColor(int unCompletedLineColor) {
        this.mUnCompletedLineColor = unCompletedLineColor;
    }

    /**
     * 设置已完成线的颜色
     *
     * @param completedLineColor 已完成线的颜色
     */
    public void setCompletedLineColor(int completedLineColor) {
        this.mCompletedLineColor = completedLineColor;
    }

    /**
     * is reverse draw 是否倒序画
     */
    public void reverseDraw(boolean isReverseDraw) {
        this.mIsReverseDraw = isReverseDraw;
        invalidate();
    }

    /**
     * 设置默认图片
     *
     * @param defaultIcon 默认图片
     */
    public void setDefaultIcon(Drawable defaultIcon) {
        this.mDefaultIcon = defaultIcon;
    }

    /**
     * 设置已完成图片
     *
     * @param completeIcon 已完成图片
     */
    public void setCompleteIcon(Drawable completeIcon) {
        this.mCompleteIcon = completeIcon;
    }

    /**
     * 设置正在进行中的图片
     *
     * @param attentionIcon 正在进行中的图片
     */
    public void setAttentionIcon(Drawable attentionIcon) {
        this.mAttentionIcon = attentionIcon;
    }

    /**
     * OnDrawIndicatorListenerView绘制的监听回调
     */
    public interface OnDrawIndicatorListener {
        /**
         * 指示器View绘制的监听回调
         */
        void onDrawIndicator();
    }
}
