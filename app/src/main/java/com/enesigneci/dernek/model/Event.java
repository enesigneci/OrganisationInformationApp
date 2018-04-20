package com.enesigneci.dernek.model;

import java.util.Date;

/**
 * Created by rdcmac on 18.04.2018.
 */

public class Event {
    int type;
    String who;
    String where;
    Date when;

    public Event() {
    }

    public Event(int type, String who, String where, Date when) {
        this.type = type;
        this.who = who;
        this.where = where;
        this.when = when;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public Date getWhen() {
        return when;
    }

    public void setWhen(Date when) {
        this.when = when;
    }
}
