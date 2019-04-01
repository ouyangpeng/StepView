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
import android.util.TypedValue;
import android.view.View;

import com.baoyachi.stepview.bean.StepBean;
import com.baoyachi.stepview.bean.StepState;

import java.util.ArrayList;
import java.util.List;

/**
 * 横向的 StepsViewIndicator 指示器
 */
public class HorizontalStepsViewIndicator extends View {
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
     * 该view的Y轴中间位置     definition view centerY position
     */
    private float mCenterY;
    /**
     * 左上方的Y位置  definition rectangle LeftY position
     */
    private float mLeftY;
    /**
     * 右下方的位置  definition rectangle RightY position
     */
    private float mRightY;
    /**
     * 当前有几部流程    there are currently few step
     */
    private List<StepBean> mStepBeanList;
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
     * 定义默认未完成线的颜色  definition
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
    /**
     * 屏幕宽度
     */
    private int screenWidth;

    public HorizontalStepsViewIndicator(Context context) {
        this(context, null);
    }

    public HorizontalStepsViewIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalStepsViewIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * init
     */
    private void init() {
        mStepBeanList = new ArrayList<>();
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
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = defaultStepIndicatorNum * 2;
        if (MeasureSpec.UNSPECIFIED != MeasureSpec.getMode(widthMeasureSpec)) {
            screenWidth = MeasureSpec.getSize(widthMeasureSpec);
        }
        int height = defaultStepIndicatorNum;
        if (MeasureSpec.UNSPECIFIED != MeasureSpec.getMode(heightMeasureSpec)) {
            height = Math.min(height, MeasureSpec.getSize(heightMeasureSpec));
        }
        width = (int) (mStepNum * mCircleRadius * 2 - (mStepNum - 1) * mLinePadding);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //获取中间的高度,目的是为了让该view绘制的线和圆在该view垂直居中   get view centerY，keep current stepview center vertical
        mCenterY = 0.5f * getHeight();
        //获取左上方Y的位置，获取该点的意义是为了方便画矩形左上的Y位置
        mLeftY = mCenterY - (mCompletedLineHeight / 2);
        //获取右下方Y的位置，获取该点的意义是为了方便画矩形右下的Y位置
        mRightY = mCenterY + mCompletedLineHeight / 2;

        mCircleCenterPointPositionList.clear();
        for (int i = 0; i < mStepNum; i++) {
            //先计算全部最左边的padding值（getWidth()-（圆形直径+两圆之间距离）*2）
            float paddingLeft = (screenWidth - mStepNum * mCircleRadius * 2 - (mStepNum - 1) * mLinePadding) / 2;
            //add to list
            mCircleCenterPointPositionList.add(paddingLeft + mCircleRadius + i * mCircleRadius * 2 + i * mLinePadding);
        }

        if (mOnDrawListener != null) {
            //指示器View绘制的监听回调  接着绘制文本
            mOnDrawListener.onDrawIndicator();
        }
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //指示器View绘制的监听回调  接着绘制文本
        if (mOnDrawListener != null) {
            mOnDrawListener.onDrawIndicator();
        }
        mUnCompletedPaint.setColor(mUnCompletedLineColor);
        mCompletedPaint.setColor(mCompletedLineColor);

        //-----------------------画线-------draw line-----------------------------------------------
        for (int i = 0; i < mCircleCenterPointPositionList.size() - 1; i++) {
            //前一个ComplectedXPosition
            final float preComplectedXPosition = mCircleCenterPointPositionList.get(i);
            //后一个ComplectedXPosition
            final float afterComplectedXPosition = mCircleCenterPointPositionList.get(i + 1);
            //判断在完成之前的所有点
            if (i <= mCompletingPosition && mStepBeanList.get(0).getState() != StepState.STEP_UNDO) {
                //判断在完成之前的所有点，画完成的线，这里是矩形,很细的矩形，类似线，为了做区分，好看些
                canvas.drawRect(preComplectedXPosition + mCircleRadius - 10,
                        mLeftY,
                        afterComplectedXPosition - mCircleRadius + 10,
                        mRightY,
                        mCompletedPaint);
            } else {
                //未完成的 画虚线
                // moveTo移动到上一个节点的右边缘：上一个节点的圆心x坐标 + 半径
                mPath.moveTo(preComplectedXPosition + mCircleRadius, mCenterY);
                // lineTo 连接到下一个节点的左边缘：下一个节点的圆心x坐标 - 半径
                mPath.lineTo(afterComplectedXPosition - mCircleRadius, mCenterY);
                canvas.drawPath(mPath, mUnCompletedPaint);
            }
        }
        //-----------------------画线-------draw line-----------------------------------------------


        //-----------------------画图标-----draw icon-----------------------------------------------
        for (int i = 0; i < mCircleCenterPointPositionList.size(); i++) {
            final float currentComplectedXPosition = mCircleCenterPointPositionList.get(i);

            Rect rect = new Rect((int) (currentComplectedXPosition - mCircleRadius),
                    (int) (mCenterY - mCircleRadius),
                    (int) (currentComplectedXPosition + mCircleRadius),
                    (int) (mCenterY + mCircleRadius));

            StepBean stepsBean = mStepBeanList.get(i);

            if (stepsBean.getState() == StepState.STEP_UNDO) {
                mDefaultIcon.setBounds(rect);
                mDefaultIcon.draw(canvas);
            } else if (stepsBean.getState() == StepState.STEP_CURRENT) {
                mCompletedPaint.setColor(Color.WHITE);
                canvas.drawCircle(currentComplectedXPosition, mCenterY, mCircleRadius * 1.1f, mCompletedPaint);
                mAttentionIcon.setBounds(rect);
                mAttentionIcon.draw(canvas);
            } else if (stepsBean.getState() == StepState.STEP_COMPLETED) {
                mCompleteIcon.setBounds(rect);
                mCompleteIcon.draw(canvas);
            }
        }
        //-----------------------画图标-----draw icon-----------------------------------------------
    }

    /**
     * 设置监听
     *
     * @param onDrawListener 监听器
     */
    public void setOnDrawListener(OnDrawIndicatorListener onDrawListener) {
        mOnDrawListener = onDrawListener;
    }

    /**
     * get圆的半径
     *
     * @return 圆的半径
     */
    public float getCircleRadius() {
        return mCircleRadius;
    }

    /**
     * 得到所有圆点所在的位置
     *
     * @return
     */
    public List<Float> getCircleCenterPointPositionList() {
        return mCircleCenterPointPositionList;
    }

    /**
     * 设置流程步数
     *
     * @param stepsBeanList 流程步数
     */
    public void setStepNum(List<StepBean> stepsBeanList) {
        this.mStepBeanList = stepsBeanList;
        mStepNum = mStepBeanList.size();

        if (mStepBeanList != null && mStepBeanList.size() > 0) {
            for (int i = 0; i < mStepNum; i++) {
                StepBean stepsBean = mStepBeanList.get(i);
                if (stepsBean.getState() == StepState.STEP_COMPLETED) {
                    mCompletingPosition = i;
                }
            }
        }
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
     * 指示器View绘制的监听回调
     */
    public interface OnDrawIndicatorListener {
        /**
         * 指示器View绘制的监听回调
         */
        void onDrawIndicator();
    }
}
