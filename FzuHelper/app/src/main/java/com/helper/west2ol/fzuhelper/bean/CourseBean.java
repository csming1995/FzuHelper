package com.helper.west2ol.fzuhelper.bean;

/**
 * Created by Administrator on 2016/10/30.
 */

public class CourseBean {
    private String kcName;
    private String kcLocation;
    private int kcStartTime;
    private int kcEndTime;
    private int kcStartWeek;
    private int kcEndWeek;
    private boolean kcIsDouble;
    private int kcWeekend;
    private boolean kcIsSingle;
    private int kcYear;
    private int kcXuenian;
    public void setKcName(String kcName){
        this.kcName = kcName;
    }
    public String getKcName(){
        return kcName;
    }

    public void setKcLocation(String kcLocation){
        this.kcLocation = kcLocation;
    }
    public String getKcLocation(){
        return kcLocation;
    }

    public int getKcStartTime() {
        return kcStartTime;
    }

    public void setKcStartTime(int kcStartTime) {
        this.kcStartTime = kcStartTime;
    }

    public int getKcEndTime() {
        return kcEndTime;
    }

    public void setKcEndTime(int kcEndTime) {
        this.kcEndTime = kcEndTime;
    }

    public int getKcStartWeek() {
        return kcStartWeek;
    }

    public void setKcStartWeek(int kcStartWeek) {
        this.kcStartWeek = kcStartWeek;
    }

    public int getKcEndWeek() {
        return kcEndWeek;
    }

    public void setKcEndWeek(int kcEndWeek) {
        this.kcEndWeek = kcEndWeek;
    }

    public boolean isKcIsDouble() {
        return kcIsDouble;
    }

    public void setKcIsDouble(boolean kcIsDouble) {
        this.kcIsDouble = kcIsDouble;
    }

    public int getKcWeekend() {
        return kcWeekend;
    }

    public void setKcWeekend(int kcWeekend) {
        this.kcWeekend = kcWeekend;
    }

    public boolean isKcIsSingle() {
        return kcIsSingle;
    }

    public void setKcIsSingle(boolean kcIsSingle) {
        this.kcIsSingle = kcIsSingle;
    }

    public int getKcYear() {
        return kcYear;
    }

    public void setKcYear(int kcYear) {
        this.kcYear = kcYear;
    }

    public int getKcXuenian() {
        return kcXuenian;
    }

    public void setKcXuenian(int kcXuenian) {
        this.kcXuenian = kcXuenian;
    }
}