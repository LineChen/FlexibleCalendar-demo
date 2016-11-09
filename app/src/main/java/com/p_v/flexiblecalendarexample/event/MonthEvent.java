package com.p_v.flexiblecalendarexample.event;

import com.p_v.flexiblecalendar.entity.Event;

/**
 * Created by chenliu on 2016/11/9 0009<br/>.
 * 描述：
 */
public class MonthEvent implements Event {

    /**
     * 底部小圆点的颜色
     */
    private int color;

    private boolean isToday;

    private boolean isSelect;

    public MonthEvent(int color){
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isToday() {
        return isToday;
    }

    public void setToday(boolean today) {
        isToday = today;
    }
}
