package com.p_v.flexiblecalendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.p_v.flexiblecalendar.entity.Event;
import com.p_v.flexiblecalendar.entity.SelectedDateItem;
import com.p_v.flexiblecalendar.view.BaseCellView;
import com.p_v.flexiblecalendar.view.impl.DateCellViewImpl;
import com.p_v.fliexiblecalendar.R;

import java.util.Calendar;
import java.util.List;

/**
 * Created by chenliu on 2016/11/9.<br/>
 * 描述：
 * </br>
 */
public class MonthCalendarView extends LinearLayout implements FlexibleCalendarGridAdapter.MonthEventFetcher, FlexibleCalendarGridAdapter.OnDateCellItemClickListener {

    FlexibleGridView monthGridView;

    FlexibleCalendarGridAdapter adapter;

    private FlexibleCalendarView.CalendarView calendarView;

    /**
     * Currently selected date item
     */
    private SelectedDateItem selectedDateItem;

    private SelectedDateItem userSelectedItem;

    private int displayYear;
    private int displayMonth;
    private int startDisplayDay;

    private int gridViewHorizontalSpacing;
    private int gridViewVerticalSpacing;
    private boolean showDatesOutsideMonth;
    private int startDayOfTheWeek;
    private boolean decorateDatesOutsideMonth;
    private boolean disableAutoDateSelection;

    private FlexibleCalendarView.EventDataProvider eventDataProvider;
    private FlexibleCalendarView.OnDateClickListener onDateClickListener;

    /**
     * Default calendar view for internal usage
     */
    private class DefaultCalendarView implements FlexibleCalendarView.CalendarView {

        @Override
        public BaseCellView getCellView(int position, View convertView, ViewGroup parent,
                                        int cellType) {
            BaseCellView cellView = (BaseCellView) convertView;
            if(cellView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                cellView = (BaseCellView)inflater.inflate(R.layout.square_cell_layout,null);
            }
            return cellView;
        }

        @Override
        public BaseCellView getWeekdayCellView(int position, View convertView, ViewGroup parent) {
            BaseCellView cellView = (BaseCellView) convertView;
            if(cellView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                cellView = (BaseCellView)inflater.inflate(R.layout.square_cell_layout,null);
            }
            return cellView;
        }

        @Override
        public String getDayOfWeekDisplayValue(int dayOfWeek, String defaultValue) {
            return null;
        }
    }

    public MonthCalendarView(Context context) {
        this(context, null);
    }

    public MonthCalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);

        initAttrs(context, attrs);

        initAdapter(context);
        initMonthGridView(context);

    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MonthCalendarView);
        try {
            Calendar cal = Calendar.getInstance(FlexibleCalendarHelper.getLocale(context));
            displayMonth = a.getInteger(R.styleable.MonthCalendarView_startDisplayMonth2,cal.get(Calendar.MONTH));
            displayYear = a.getInteger(R.styleable.MonthCalendarView_startDisplayYear2, cal.get(Calendar.YEAR));
            startDisplayDay = cal.get(Calendar.DAY_OF_MONTH);

            gridViewHorizontalSpacing = (int)a.getDimension(R.styleable.MonthCalendarView_monthDayHorizontalSpacing2, 0);
            gridViewVerticalSpacing = (int)a.getDimension(R.styleable.MonthCalendarView_monthDayVerticalSpacing2,0);
            showDatesOutsideMonth = a.getBoolean(R.styleable.MonthCalendarView_showDatesOutsideMonth2, false);
            decorateDatesOutsideMonth = a.getBoolean(R.styleable.MonthCalendarView_decorateDatesOutsideMonth2, false);
            disableAutoDateSelection = a.getBoolean(R.styleable.MonthCalendarView_disableAutoDateSelection2, false);

            startDayOfTheWeek = a.getInt(R.styleable.MonthCalendarView_startDayOfTheWeek2, Calendar.SUNDAY);
            if(startDayOfTheWeek<1 || startDayOfTheWeek>7){
                // setting the start day to sunday in case of invalid input
                startDayOfTheWeek = Calendar.SUNDAY;
            }

        } finally {
            a.recycle();
        }
    }

    private void initAdapter(Context context) {
        adapter = new FlexibleCalendarGridAdapter(context, displayYear, displayMonth, showDatesOutsideMonth, decorateDatesOutsideMonth, startDayOfTheWeek, disableAutoDateSelection);
        adapter.setOnDateClickListener(this);
        adapter.setMonthEventFetcher(this);

        //initialize the default calendar view
        calendarView = new DefaultCalendarView();

        //set the default cell view
        adapter.setCellViewDrawer(new DateCellViewImpl(calendarView));

        //initialize with the current selected item
        selectedDateItem = new SelectedDateItem(displayYear, displayMonth,startDisplayDay);
        adapter.setSelectedItem(selectedDateItem, true, false);
    }

    private void initMonthGridView(Context context) {
        monthGridView = (FlexibleGridView) LayoutInflater.from(context).inflate(R.layout.month_grid_layout, null);
        monthGridView.setVerticalSpacing(gridViewVerticalSpacing);
        monthGridView.setHorizontalSpacing(gridViewHorizontalSpacing);
        monthGridView.setAdapter(adapter);

        addView(monthGridView);
    }

    public void setSpacing(int horizontalSpacing, int verticalSpacing) {
        this.gridViewHorizontalSpacing = horizontalSpacing;
        this.gridViewVerticalSpacing = verticalSpacing;
    }

    public void setStartDayOfTheWeek(int startDayOfTheWeek) {
        this.startDayOfTheWeek = startDayOfTheWeek;
        adapter.setFirstDayOfTheWeek(startDayOfTheWeek);
    }

    public void setShowDatesOutsideMonth(boolean showDatesOutsideMonth) {
        this.showDatesOutsideMonth = showDatesOutsideMonth;
        adapter.setShowDatesOutsideMonth(showDatesOutsideMonth);
    }

    public void setDecorateDatesOutsideMonth(boolean decorateDatesOutsideMonth) {
        this.decorateDatesOutsideMonth = decorateDatesOutsideMonth;
        adapter.setDecorateDatesOutsideMonth(decorateDatesOutsideMonth);
    }

    public void setDisableAutoDateSelection(boolean disableAutoDateSelection) {
        this.disableAutoDateSelection = disableAutoDateSelection;
        adapter.setDisableAutoDateSelection(disableAutoDateSelection);
    }

    public void setEventDataProvider(FlexibleCalendarView.EventDataProvider eventDataProvider){
        this.eventDataProvider = eventDataProvider;
    }

    public void setOnDateClickListener(FlexibleCalendarView.OnDateClickListener onDateClickListener){
        this.onDateClickListener = onDateClickListener;
    }

    /**
     * Set the customized calendar view for the calendar for customizing cells
     * and layout
     * @param calendar
     */
    public void setCalendarView(FlexibleCalendarView.CalendarView calendar){
        this.calendarView = calendar;
        adapter.getCellViewDrawer().setCalendarView(calendarView);
        adapter.getCellViewDrawer().setCalendarView(calendarView);
    }


    @Override
    public List<? extends Event> getEventsForTheDay(int year, int month, int day) {
        return eventDataProvider == null?
                null : eventDataProvider.getEventsForTheDay(year, month, day);
    }

    @Override
    public void onDateClick(SelectedDateItem selectedItem) {

        this.selectedDateItem = selectedItem;

        // set user selected date item
        if(disableAutoDateSelection){
            this.userSelectedItem = selectedItem.clone();
        }

        if(onDateClickListener!=null) {
            onDateClickListener.onDateClick(selectedItem.getYear(), selectedItem.getMonth(), selectedItem.getDay());
        }
    }

}
