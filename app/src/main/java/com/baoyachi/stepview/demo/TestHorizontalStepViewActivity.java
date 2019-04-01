package com.baoyachi.stepview.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.baoyachi.stepview.bean.StepState;

import java.util.ArrayList;
import java.util.List;

/**
 * 日期：16/7/26 15:06
 * <p>
 * 描述：
 */
public class TestHorizontalStepViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_horizontal);
        findViewById(R.id.btn_status).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HorizontalStepView stepView = findViewById(R.id.stepview);
                //-----------------------------this data is example and you can also get data from server-----------------------------
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
                //-----------------------------this data is example and you can also get data from server-----------------------------
                stepView.setStepViewTexts(stepsBeanList)
                        .setTextSize(16)//set textSize
                        //设置StepsViewIndicator完成线的颜色
                        .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(getBaseContext(), android.R.color.white))
                        //设置StepsViewIndicator未完成线的颜色
                        .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(getBaseContext(), R.color.uncompleted_text_color))
                        //设置StepsView text完成线的颜色
                        .setStepViewComplectedTextColor(ContextCompat.getColor(getBaseContext(), android.R.color.white))
                        //设置StepsView text未完成线的颜色
                        .setStepViewUnComplectedTextColor(ContextCompat.getColor(getBaseContext(), R.color.uncompleted_text_color))
                        //设置StepsViewIndicator CompleteIcon
                        .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.complted))
                        //设置StepsViewIndicator DefaultIcon
                        .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.default_icon))
                        //设置StepsViewIndicator AttentionIcon
                        .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.attention));
            }
        });


    }
}
