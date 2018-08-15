package com.akatsuki.bean;

/**
 * Created by think on 2018/4/21.
 */
public enum Priority {
    HIGH("高",3),
    MID("中",2),
    LOW("低",1);

    private String name;

    private int index;

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    private Priority(String name,int index){
        this.index = index;
        this.name = name;
    }

}
