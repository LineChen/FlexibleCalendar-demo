package com.p_v.flexiblecalendar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by one_v on 2016/11/7 09:42.
 */

public class FlexibleGridView extends GridView {
    public FlexibleGridView(Context context) {
        super(context);
    }

    public FlexibleGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlexibleGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
