package com.p_v.flexiblecalendarexample;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.p_v.flexiblecalendar.FlexibleCalendarView;
import com.p_v.flexiblecalendar.MonthCalendarView;
import com.p_v.flexiblecalendar.entity.CalendarEvent;
import com.p_v.flexiblecalendar.view.BaseCellView;
import com.p_v.flexiblecalendarexample.event.MonthEvent;

import java.util.ArrayList;
import java.util.List;

public class MonthCalendarActivity extends AppCompatActivity {
    MonthCalendarView monthCalendar;

    int yearSelected;
    int monthSelected;
    int daySelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_calendar);
        monthCalendar = (MonthCalendarView) findViewById(R.id.month_calendar);

        monthCalendar.setCalendarView(new FlexibleCalendarView.CalendarView() {
            @Override
            public BaseCellView getCellView(int position, View convertView, ViewGroup parent, @BaseCellView.CellType int cellType) {
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(MonthCalendarActivity.this);
                    cellView = (BaseCellView) inflater.inflate(R.layout.item_month_cell_view, null);
                }

                switch (cellType){
                    case BaseCellView.TODAY:
                        cellView.setTextColor(Color.BLACK);
                        break;

                    case BaseCellView.SELECTED:
                        cellView.setTextColor(Color.WHITE);
                        break;

                    case BaseCellView.SELECTED_TODAY:
                        cellView.setTextColor(Color.WHITE);
                        break;

                    default:
                        cellView.setTextColor(Color.BLACK);
                        break;
                }
                return cellView;
            }

            @Override
            public BaseCellView getWeekdayCellView(int position, View convertView, ViewGroup parent) {
                return null;
            }

            @Override
            public String getDayOfWeekDisplayValue(int dayOfWeek, String defaultValue) {
                return null;
            }
        });


        monthCalendar.setOnDateClickListener(new FlexibleCalendarView.OnDateClickListener() {
            @Override
            public void onDateClick(int year, int month, int day) {
//                Toast.makeText(ScheduleActivity.this, year +"-" + (month + 1) + "-" + day, Toast.LENGTH_SHORT).show();
                yearSelected = year;
                monthSelected = month;
                daySelected = day;
            }
        });

        monthCalendar.setEventDataProvider(new FlexibleCalendarView.EventDataProvider() {
            @Override
            public List<MonthEvent> getEventsForTheDay(int year, int month, int day) {
                Log.e("====",year + "-" + month  +"-" + day);
                if (year == yearSelected && month == monthSelected && day == daySelected) {
                    List<MonthEvent> monthEvents = new ArrayList<>(1);
                    MonthEvent event = new MonthEvent(R.color.transeparent);
                    event.setSelect(true);
                    monthEvents.add(event);
                    return monthEvents;
                }

                if (year == 2016 && month == 10 && day == 9) {
                    List<MonthEvent> monthEvents = new ArrayList<>(1);
                    MonthEvent event = new MonthEvent(android.R.color.holo_blue_light);
                    event.setToday(true);
                    monthEvents.add(event);
                    return monthEvents;
                }


//                if (year == 2016 && month == 10 && day == 7 ||
//                        year == 2016 && month == 10 && day == 29 ||
//                        year == 2016 && month == 10 && day == 5 ||
//                        year == 2016 && month == 10 && day == 9) {
//                    List<CalendarEvent> eventColors = new ArrayList<>(1);
//                    eventColors.add(new CalendarEvent(android.R.color.holo_blue_light));
//                    return eventColors;
//                }

//                if (year == 2016 && month == 10 && day == 31 ||
//                        year == 2016 && month == 10 && day == 22 ||
//                        year == 2016 && month == 10 && day == 18 ||
//                        year == 2016 && month == 10 && day == 11) {
//                    List<CalendarEvent> eventColors = new ArrayList<>(3);
//                    eventColors.add(new CalendarEvent(android.R.color.holo_red_dark));
//                    eventColors.add(new CalendarEvent(android.R.color.holo_orange_light));
//                    eventColors.add(new CalendarEvent(android.R.color.holo_purple));
//                    return eventColors;
//                }
                return null;
            }
        });
    }
}
