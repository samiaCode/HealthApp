package com.example.m;

import java.util.Date;

public class Habit {
    private String habitName;

    public Habit(String habitName) {
        this.habitName = habitName;
    }

    public String getHabitName() {
        return habitName;
    }

    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }
}