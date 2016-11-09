package com.p_v.flexiblecalendarexample.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;

import com.p_v.flexiblecalendar.entity.CalendarEvent;
import com.p_v.flexiblecalendar.entity.Event;
import com.p_v.flexiblecalendar.view.BaseCellView;
import com.p_v.flexiblecalendarexample.R;
import com.p_v.flexiblecalendarexample.event.MonthEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenliu on 2016/11/9.<br/>
 * 描述：
 * </br>
 */
public class DotEventCellView extends BaseCellView {

    private float eventCircleY;
    private int radius;
    private int padding;
    private int leftMostPosition = Integer.MIN_VALUE;

    private MonthEvent event;

    /**
     * 文字区域
     */
    Rect rectText;
    Rect outRect= new Rect();

    Paint dotPaint;

    Paint bgPaint;

    public DotEventCellView(Context context) {
        super(context);
    }

    public DotEventCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public DotEventCellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DotEventCellView);
        try{
            radius = (int)a.getDimension(R.styleable.DotEventCellView_dot_event_radius, 10);
            padding = (int)a.getDimension(R.styleable.DotEventCellView_event_dot_padding, 1);
        }finally {
            a.recycle();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Paint p = new Paint();
        p.setTextSize(getTextSize());
        rectText = new Rect();
        p.getTextBounds("31", 0, 1, rectText); // measuring using fake text

        getDrawingRect(outRect);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        float cx = outRect.centerX();
        float cy = getBaseline() - rectText.height() / 2;
        float br = rectText.width() * 2;
        //先画背景
        if (bgPaint != null) {
            canvas.drawCircle(cx, cy, br, bgPaint);
        }

        super.onDraw(canvas);

        // draw only if there is no state or just one state i.e. the regular day state
        if(dotPaint != null) {
            canvas.drawCircle(outRect.centerX(), cy + br + 20, radius, dotPaint);
        }

        //不设置为null,选择其他的日期时背景色还在
        if(event!= null && event.isSelect()){
            bgPaint = null;
        }

    }

    private int calculateStartPoint(int offset){
        return leftMostPosition + offset *(2*(radius+padding)) ;
    }

    @Override
    public void setEvents(List<? extends Event> colorList){
        if(colorList != null && !colorList.isEmpty()){
            MonthEvent event = (MonthEvent) colorList.get(0);
            this.event = event;
            dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            dotPaint.setStyle(Paint.Style.FILL);
            dotPaint.setColor(getContext().getResources().getColor(event.getColor()));

            if(event.isToday()){
                bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                bgPaint.setStyle(Paint.Style.STROKE);
                bgPaint.setStrokeWidth(5);
                bgPaint.setColor(getContext().getResources().getColor(R.color.md_pink_400));
            }

            if(event.isSelect()){
                bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                bgPaint.setStyle(Paint.Style.FILL);
                bgPaint.setColor(getContext().getResources().getColor(R.color.md_pink_400));
            }

            invalidate();
            requestLayout();
        }
    }

}
