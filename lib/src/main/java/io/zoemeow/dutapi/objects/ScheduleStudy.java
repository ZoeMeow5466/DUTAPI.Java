package io.zoemeow.dutapi.objects;

import java.util.ArrayList;

public class ScheduleStudy {
    private ArrayList<ScheduleItem> scheduleList = new ArrayList<>();
    private ArrayList<WeekItem> weekList = new ArrayList<>();
    
    public ScheduleStudy() {

    }

    public ScheduleStudy(ArrayList<ScheduleItem> scheduleList, ArrayList<WeekItem> weekList) {
        this.scheduleList = scheduleList;
        this.weekList = weekList;
    }

    public ArrayList<ScheduleItem> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(ArrayList<ScheduleItem> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public ArrayList<WeekItem> getWeekList() {
        return weekList;
    }

    public void setWeekList(ArrayList<WeekItem> weekList) {
        this.weekList = weekList;
    }
}