package ru.smartinc.parkinsonapp.data;

/**
 * Created by AndreyBoy on 05.04.2018.
 */

public class Exercise {
    private int id;
    private String name;
    private String icon;
    private int time;

    public Exercise(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
