package com.baoyachi.stepview.bean;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 状态
 */
public class StepState {
    /**
     * 未完成  undo step
     */
    public static final int STEP_UNDO = -1;
    /**
     * 正在进行 current step
     */
    public static final int STEP_CURRENT = 0;
    /**
     * 已完成 completed step
     */
    public static final int STEP_COMPLETED = 1;

    @IntDef({STEP_UNDO, STEP_CURRENT, STEP_COMPLETED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface StepStateType {
    }
}
