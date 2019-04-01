package com.baoyachi.stepview.demo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.baoyachi.stepview.bean.StepState;
import com.baoyachi.stepview.demo.R;

import java.util.ArrayList;
import java.util.List;

public class HorizontalStepViewFragment extends Fragment {
    View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = View.inflate(container.getContext(), R.layout.fragment_horizontal_stepview, null);
        showStepView0();
        showStepView1();
        showStepView2();
        showStepView3();
        showStepView4();
        showStepView5();
        showStepView6();
        return mView;
    }

    private void showStepView0() {
        HorizontalStepView stepView = mView.findViewById(R.id.step_view0);
        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("接单", StepState.STEP_COMPLETED);
        StepBean stepBean1 = new StepBean("打包", StepState.STEP_COMPLETED);
        StepBean stepBean2 = new StepBean("出发", StepState.STEP_CURRENT);
        StepBean stepBean3 = new StepBean("送单", StepState.STEP_UNDO);
        StepBean stepBean4 = new StepBean("完成", StepState.STEP_UNDO);
        StepBean stepBean5 = new StepBean("支付", StepState.STEP_UNDO);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);
        stepsBeanList.add(stepBean3);
        stepsBeanList.add(stepBean4);
        stepsBeanList.add(stepBean5);
        setStepViewAttribute(stepView, stepsBeanList, 18);
    }


    private void showStepView1() {
        HorizontalStepView stepView = mView.findViewById(R.id.step_view1);
        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("接单", StepState.STEP_UNDO);
        stepsBeanList.add(stepBean0);
        setStepViewAttribute(stepView, stepsBeanList, 8);
    }

    private void showStepView2() {
        HorizontalStepView stepView = mView.findViewById(R.id.step_view2);
        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("接单", StepState.STEP_COMPLETED);
        StepBean stepBean1 = new StepBean("打包", StepState.STEP_CURRENT);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        setStepViewAttribute(stepView, stepsBeanList, 9);
    }

    private void showStepView3() {
        HorizontalStepView stepView = mView.findViewById(R.id.step_view3);
        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("接单", StepState.STEP_COMPLETED);
        StepBean stepBean1 = new StepBean("打包", StepState.STEP_CURRENT);
        StepBean stepBean2 = new StepBean("出发", StepState.STEP_UNDO);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);
        setStepViewAttribute(stepView, stepsBeanList, 10);
    }

    private void showStepView4() {
        HorizontalStepView stepView = mView.findViewById(R.id.step_view4);
        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("接单", StepState.STEP_COMPLETED);
        StepBean stepBean1 = new StepBean("打包", StepState.STEP_COMPLETED);
        StepBean stepBean2 = new StepBean("出发", StepState.STEP_CURRENT);
        StepBean stepBean3 = new StepBean("送单", StepState.STEP_UNDO);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);
        stepsBeanList.add(stepBean3);
        setStepViewAttribute(stepView, stepsBeanList, 11);


    }

    private void showStepView5() {
        HorizontalStepView stepView = mView.findViewById(R.id.step_view5);
        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("接单", StepState.STEP_COMPLETED);
        StepBean stepBean1 = new StepBean("打包", StepState.STEP_COMPLETED);
        StepBean stepBean2 = new StepBean("出发", StepState.STEP_COMPLETED);
        StepBean stepBean3 = new StepBean("送单", StepState.STEP_CURRENT);
        StepBean stepBean4 = new StepBean("完成", StepState.STEP_UNDO);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);
        stepsBeanList.add(stepBean3);
        stepsBeanList.add(stepBean4);
        setStepViewAttribute(stepView, stepsBeanList, 12);
    }

    private void showStepView6() {
        HorizontalStepView stepView = mView.findViewById(R.id.step_view6);
        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("接单", StepState.STEP_COMPLETED);
        StepBean stepBean1 = new StepBean("打包", StepState.STEP_COMPLETED);
        StepBean stepBean2 = new StepBean("出发", StepState.STEP_COMPLETED);
        StepBean stepBean3 = new StepBean("送单", StepState.STEP_COMPLETED);
        StepBean stepBean4 = new StepBean("完成", StepState.STEP_COMPLETED);
        StepBean stepBean5 = new StepBean("支付", StepState.STEP_COMPLETED);
        StepBean stepBean6 = new StepBean("支付2", StepState.STEP_COMPLETED);

        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);
        stepsBeanList.add(stepBean3);
        stepsBeanList.add(stepBean4);
        stepsBeanList.add(stepBean5);
        stepsBeanList.add(stepBean6);
        setStepViewAttribute(stepView, stepsBeanList, 13);
    }

    private HorizontalStepView setStepViewAttribute(HorizontalStepView stepView, List<StepBean> stepsBeanList, int textSize) {
        return stepView.setStepViewTexts(stepsBeanList)
                //setStepViewAttribute textSize
                .setTextSize(textSize)
                //设置StepsViewIndicator完成线的颜色
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(getActivity(), android.R.color.white))
                //设置StepsViewIndicator未完成线的颜色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(getActivity(), R.color.uncompleted_text_color))
                //设置StepsView text完成线的颜色
                .setStepViewComplectedTextColor(ContextCompat.getColor(getActivity(), android.R.color.white))
                //设置StepsView text未完成线的颜色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(getActivity(), R.color.uncompleted_text_color))
                //设置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getActivity(), R.drawable.complted))
                //设置StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getActivity(), R.drawable.default_icon))
                //设置StepsViewIndicator AttentionIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getActivity(), R.drawable.attention));
    }
}
