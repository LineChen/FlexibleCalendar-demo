package com.p_v.flexiblecalendar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;

import com.p_v.flexiblecalendar.entity.Event;
import com.p_v.fliexiblecalendar.R;

import java.util.List;

/**
 * Cell view with the event count
 *
 * @author p-v
 */
public class EventCountCellView extends BaseCellView {

    private static final int Y_OFFSET = -24;

    private Paint mPaint;
    private Paint mTextPaint;
    private int mEventCount;
    private int middleX;
    private int leftX;
    private int rightX;
    private int middleY;
//    private int eventCircleX;
//    private int eventCircleY;
//    private int mTextY;

    private int radius;
    private int eventTextColor;
    private int eventBackground;
    private int eventTextSize;

    public EventCountCellView(Context context) {
        super(context);
    }

    public EventCountCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public EventCountCellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EventCountCellView);
        try {
            radius = (int) a.getDimension(R.styleable.EventCountCellView_event_count_radius, 32);
            eventBackground = a.getColor(R.styleable.EventCountCellView_event_background,
                    getResources().getColor(android.R.color.black));
            eventTextColor = a.getColor(R.styleable.EventCountCellView_event_count_text_color,
                    getResources().getColor(android.R.color.white));
            eventTextSize = (int) a.getDimension(R.styleable.EventCountCellView_event_text_size, -1);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mEventCount > 0) {
            Paint p = new Paint();
            p.setTextSize(getTextSize());

            Rect rect = new Rect();
            p.getTextBounds("99+", 0, 1, rect); // measuring using fake text

            middleX = getWidth() / 2;
            leftX = middleX - rect.width() - 8;
            rightX = middleX + rect.width() + 8;

//            eventCircleX = (3 * getWidth() + rect.width()) / 4;
//            eventCircleY = (getHeight() - rect.height()) / 4;

            middleY = getHeight() - (getHeight() - rect.height()) / 4 - Y_OFFSET;

            Log.e("wwwwwwwwww", "getWidth()---->" + getWidth()
                            + "  rect.width--->" + rect.width()
//                    + "  eventCircleX--->" + eventCircleX
                            + "  getHeight()--->" + getHeight()
                            + "  rect.height-->" + rect.height()
//                    + "  eventCircleY--->" + eventCircleY
            );

            mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mTextPaint.setStyle(Paint.Style.FILL);
            mTextPaint.setTextSize(eventTextSize == -1 ? getTextSize() / 2 : eventTextSize);
            mTextPaint.setColor(eventTextColor);
            mTextPaint.setTextAlign(Paint.Align.CENTER);

//            mTextY = eventCircleY + radius / 2;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(0, Y_OFFSET);
        super.onDraw(canvas);
        if (mEventCount > 0 && mPaint != null && mTextPaint != null) {
//            canvas.drawCircle(eventCircleX, eventCircleY, radius, mPaint);
//            canvas.drawText("99+", eventCircleX, mTextY, mTextPaint);
            canvas.drawText("9", leftX, middleY, mTextPaint);
            canvas.drawText("22", rightX, middleY, mTextPaint);
            mPaint.setStrokeWidth(2);
            canvas.drawLine(middleX, middleY - 30, middleX, middleY + 10, mPaint);
        }
    }

    @Override
    public void setEvents(List<? extends Event> colorList) {
        if (colorList != null && !colorList.isEmpty()) {
            mEventCount = colorList.size();
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(eventBackground);
            invalidate();
            requestLayout();
        }
    }
}
