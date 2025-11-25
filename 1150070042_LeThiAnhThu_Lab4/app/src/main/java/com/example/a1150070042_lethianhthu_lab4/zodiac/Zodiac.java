package com.example.a1150070042_lethianhthu_lab4.zodiac;

import java.io.Serializable;

public class Zodiac implements Serializable {
    private String name;
    private String date;
    private int iconId;
    private String summary;
    private String detailContent;

    public Zodiac(String name, String date, int iconId, String summary, String detailContent) {
        this.name = name;
        this.date = date;
        this.iconId = iconId;
        this.summary = summary;
        this.detailContent = detailContent;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public int getIconId() {
        return iconId;
    }

    public String getSummary() {
        return summary;
    }

    public String getDetailContent() {
        return detailContent;
    }
}