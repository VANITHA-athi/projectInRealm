package com.example.projectinrealm.events;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;


public class eventModel extends RealmObject {
    @PrimaryKey
    private long id;
    @Required
    private String eventName,eventDate,Description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this. id= id;
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

    public String getDescription() {
        return Description;
    }
    public void setDescription(String eventTime) {
        this.Description = eventTime;
    }
    public eventModel(){

    }


}
