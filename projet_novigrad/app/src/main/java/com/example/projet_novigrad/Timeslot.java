package com.example.projet_novigrad;

import android.os.Bundle;
import android.os.Parcel;

public class Timeslot{
    //public static final int MONDAY=1;
    //public static final int TUESDAY=2;
    //public static final int THURSDAY=3;
   // public static final int FRIDAY=5;
    //public static final int SATURDAY=6;
   // public static final int SUNDAY=7;

    public int year,month,weekDay, startHour, startMinute, endHour, endMinute;
    //public Calender calender;
    public Timeslot(int startHour,int startMinute,int endHour, int endMinute){
       // this.year=year;
       // this.month=month;
        //this.weekDay=weekDay;
        this.startHour=startHour;
        this.startMinute=startMinute;
        this.endHour=endHour;
        this.endMinute=endMinute;
        //this.calender=calender;
    }
    public Timeslot(Parcel p){
        Bundle data = p.readBundle(Timeslot.class.getClassLoader());
        data.setClassLoader(Timeslot.class.getClassLoader());
        this.weekDay= data.getInt("weekDay");
        this.startHour=data.getInt("startHour");
        this.startMinute=data.getInt("startMinute");
        this.endMinute=data.getInt("endMinute");
        this.endHour=data.getInt("endHour");

    }
    /*public Timeslot(int firstHour, int duration){
        startHour=7;
        startMinute = 30 + (firstHour - 1) * 45 + ((firstHour % 2 != 0) ? 15 : 0) * (firstHour / 2) + ((firstHour % 2 == 0) ? (firstHour / 4) * 15 : 0);
        while (startMinute >= 60) {
            startHour++;
            startMinute -= 60;
        }
        endHour = startHour;
        endMinute = startMinute + duration * 45;
        while (endMinute >= 60) {
            endHour++;
            endMinute -= 60;
        }

    }*/

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getDay(int day) {
        switch (day) {
            case 1:
                return ("Monday");
            case 2:
                return ("Tuesday");
            case 3:
                return ("Wednesday");
            case 4:
                return ("Thursday");
            case 5:
                return ("Friday");
        }
        return null;
    }



    public void setDay(int day){
        this.weekDay=day;
    }

}
