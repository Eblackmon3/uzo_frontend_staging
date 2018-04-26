package Model.DataObjects;

import java.sql.Date;
import java.sql.Time;

public class Event {
    private int event_id;
    private String date;
    private String dress_code;
    private double duration;
    private boolean open;
    private String event_title;
    private int company_id;
    private int time;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isOpen() {
        return open;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getDuration() {
        return duration;
    }

    public int getEvent_id() {
        return event_id;
    }

    public int getTime() {
        return time;
    }

    public String getDress_code() {
        return dress_code;
    }

    public String getEvent_title() {
        return event_title;
    }

    public String getDate() {
        return date;
    }

    public void setDress_code(String dress_code) {
        this.dress_code = dress_code;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public boolean equals(Object obj) {
        Event event= (Event) obj;

        if(getEvent_id()==event_id){
            return true;
        }
        return false;
    }

}
