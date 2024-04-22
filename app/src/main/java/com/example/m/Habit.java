package com.example.m;

import java.util.Date;

public class Habit {
    private String habitName;
    private Date completionDate;

    public Habit(String habitName, Date completionDate) {
        this.habitName = habitName;
        this.completionDate = completionDate;
    }

    public String getHabitName() {
        return habitName;
    }

    public Date getCompletionDate() {
        return completionDate;
    }
}
