package com.akatsuki.bean;

public enum Circle {

    NONECIRCLE("不循环",0),
    EVERYDAY("每天",1),
    EVERYWEEK("每周",2);

    private String name;

    private int index;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private Circle(String name,int index){
        this.name = name;
        this.index = index;
    }
}
