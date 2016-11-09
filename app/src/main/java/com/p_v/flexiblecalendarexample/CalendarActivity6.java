package com.p_v.flexiblecalendarexample;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.p_v.flexiblecalendar.FlexibleCalendarView;
import com.p_v.flexiblecalendar.entity.CalendarEvent;
import com.p_v.flexiblecalendar.view.BaseCellView;
import com.p_v.flexiblecalendar.view.SquareCellView;

import java.util.ArrayList;
import java.util.List;

public class CalendarActivity6 extends AppCompatActivity {

    FlexibleCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar6);

        calendarView = (FlexibleCalendarView) findViewById(R.id.calendar_view);
        calendarView.setWeekViewVisibility(false);

        calendarView.setCalendarView(new FlexibleCalendarView.CalendarView() {
            @Override
            public BaseCellView getCellView(int position, View convertView, ViewGroup parent, @BaseCellView.CellType int cellType) {
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(CalendarActivity6.this);
                    cellView = (BaseCellView) inflater.inflate(R.layout.calendar1_date_cell_view, null);
                }

                if(cellType ==BaseCellView.TODAY){
                    cellView.setTextColor(getResources().getColor(R.color.accent));
                } else {
                    cellView.setTextColor(Color.BLACK);
                }

                if(cellType == BaseCellView.OUTSIDE_MONTH){
                    //这个界面中其他月份的日期
                    cellView.setTextColor(getResources().getColor(R.color.accent));
                }
                return cellView;
            }

            @Override
            public BaseCellView getWeekdayCellView(int position, View convertView, ViewGroup parent) {
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(CalendarActivity6.this);
                    cellView = (SquareCellView) inflater.inflate(R.layout.calendar1_week_cell_view, null);
                }
                return cellView;
            }

            @Override
            public String getDayOfWeekDisplayValue(int dayOfWeek, String defaultValue) {
                return String.valueOf(defaultValue.charAt(0));
            }
        });


        calendarView.setEventDataProvider(new FlexibleCalendarView.EventDataProvider() {
            @Override
            public List<CalendarEvent> getEventsForTheDay(int year, int month, int day) {
                if (year == 2016 && month == 10 && day == 12) {
                    List<CalendarEvent> eventColors = new ArrayList<>(2);
                    eventColors.add(new CalendarEvent(android.R.color.holo_blue_light));
                    eventColors.add(new CalendarEvent(android.R.color.holo_purple));
                    return eventColors;
                }
                if (year == 2016 && month == 10 && day == 7 ||
                        year == 2016 && month == 10 && day == 29 ||
                        year == 2016 && month == 10 && day == 5 ||
                        year == 2016 && month == 10 && day == 9) {
                    List<CalendarEvent> eventColors = new ArrayList<>(1);
                    eventColors.add(new CalendarEvent(android.R.color.holo_blue_light));
                    return eventColors;
                }

                if (year == 2016 && month == 10 && day == 31 ||
                        year == 2016 && month == 10 && day == 22 ||
                        year == 2016 && month == 10 && day == 18 ||
                        year == 2016 && month == 10 && day == 11) {
                    List<CalendarEvent> eventColors = new ArrayList<>(3);
                    eventColors.add(new CalendarEvent(android.R.color.holo_red_dark));
                    eventColors.add(new CalendarEvent(android.R.color.holo_orange_light));
                    eventColors.add(new CalendarEvent(android.R.color.holo_purple));
                    return eventColors;
                }

                return null;
            }
        });


    }
}
