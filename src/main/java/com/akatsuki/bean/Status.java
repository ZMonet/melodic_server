package com.akatsuki.bean;

public enum Status {

    INVAILD("已过期",0),
    UNFINISHED("未完成",1),
    FINISHED("已完成",2);



    private String name;

    private int index;

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    private Status(String name,int index){
        this.index = index;
        this.name = name;
    }

}
