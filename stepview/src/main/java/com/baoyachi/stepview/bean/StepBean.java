package com.baoyachi.stepview.bean;

public class StepBean {
    private String name;
    private int state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(@StepState.StepStateType int state) {
        this.state = state;
    }

    public StepBean() {
    }

    public StepBean(String name, @StepState.StepStateType int state) {
        this.name = name;
        this.state = state;
    }
}
