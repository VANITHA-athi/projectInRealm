package com.example.projectinrealm.events;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;


public class eventModel extends RealmObject {
    @PrimaryKey
    private long id;
    @Required
    private String eventName,eventDate,eventTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }
    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }
    public eventModel(){

    }


}
