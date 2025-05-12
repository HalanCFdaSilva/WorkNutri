package com.example.worknutri.ui.popUp.hourDatePopUp.daysOfWork;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;

public class HourDateFragmentInserter {


    private ViewGroup layoutToInsertHourDate;
    private HourDateFragment hourDateFragment;
    private DayOfWork dayOfWork;

    public HourDateFragmentInserter(ViewGroup layoutToInsertFragment) {
        this.layoutToInsertHourDate = layoutToInsertFragment;
        this.hourDateFragment = new HourDateFragment();
        
        Context context = layoutToInsertFragment.getContext();
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        this.hourDateFragment.generateNewLayout(inflater);
    }

    public void generateAndInsertInLayoutOfHourDateFragment( DayOfWork dayOfWork) {
        setDayOfWork(dayOfWork);
        hourDateFragment.setFromDayOfWork(dayOfWork);
        if (layoutToInsertHourDate.indexOfChild(hourDateFragment.getLayout()) < 0){
            hourDateFragment.addLayout(layoutToInsertHourDate);
        }
    }


    public HourDateFragment getHourDateFragment() {
        return hourDateFragment;
    }

    public DayOfWork getDayOfWork(){
        return dayOfWork;
    }

    public void setDayOfWork(DayOfWork dayOfWork) {
        this.dayOfWork = dayOfWork;
    }

    public boolean dayOfWorkEquals(DayOfWork dayOfWork) {
        return this.dayOfWork.getId() == dayOfWork.getId();
    }
}
