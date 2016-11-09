package com.p_v.flexiblecalendarexample.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.p_v.flexiblecalendar.view.CircularEventCellView;

/**
 * Created by chenliu on 2016/11/9.<br/>
 * 描述：自定义日历显示
 * </br>
 */
public class MonthCellView extends DotEventCellView {

    public MonthCellView(Context context) {
        super(context);
    }

    public MonthCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MonthCellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = (10*width)/8;
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }
}
