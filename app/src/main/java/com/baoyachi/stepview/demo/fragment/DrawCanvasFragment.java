package com.baoyachi.stepview.demo.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyachi.stepview.demo.R;

public class DrawCanvasFragment extends Fragment {
    Paint paint;
    Paint pathPaint;
    Path path;
    DashPathEffect mEffects;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return new RectView(container.getContext());
    }

    public class RectView extends View {

        public RectView(Context context) {
            super(context);
            init();
        }

        private void init() {
            // 定义画笔
            paint = new Paint();
            //设置实心
            paint.setStyle(Paint.Style.FILL);
            // 消除锯齿
            paint.setAntiAlias(true);
            //设置画笔颜色
            paint.setColor(Color.WHITE);
            // 设置paint的外框宽度
            paint.setStrokeWidth(40);

            pathPaint = new Paint();
            pathPaint.setAntiAlias(true);
            pathPaint.setColor(Color.WHITE);
            pathPaint.setStyle(Paint.Style.STROKE);
            pathPaint.setStrokeWidth(2);

            path = new Path();
            //DashPathEffect:DashPathEffect是PathEffect类的一个子类,可以使paint画出类似虚线的样子,并且可以任意指定虚实的排列方式.
            mEffects = new DashPathEffect(new float[]{8, 8, 8, 8}, 1);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            //设置背景色
            setBackgroundResource(R.drawable.default_bg);

            //--------------------------绘制矩形-----------------------------------------------------
            //绘制矩形
            canvas.drawRect(100, 200, 600, 220, paint);
            //--------------------------绘制矩形-----------------------------------------------------

            //--------------------------绘制圆-----------------------------------------------------
            canvas.drawCircle(350, 400, 100, paint);
            //--------------------------绘制圆-----------------------------------------------------

            //--------------------------绘制虚线-----------------------------------------------------
            path.moveTo(100, 600);
            path.lineTo(600, 600);
            pathPaint.setPathEffect(mEffects);
            canvas.drawPath(path, pathPaint);
            //--------------------------绘制虚线-----------------------------------------------------
        }
    }
}
