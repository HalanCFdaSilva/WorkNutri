package com.example.worknutri.ui.popUp.hourDatePopUp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.worknutri.sqlLite.dao.clinica.DayOfWorkDao;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.ui.popUp.factory.PopUpFactoryImpl;
import com.example.worknutri.ui.popUp.hourDatePopUp.datePicker.DatePickerPopUp;
import com.example.worknutri.ui.popUp.hourDatePopUp.daysOfWork.HourDateFragment;
import com.example.worknutri.ui.popUp.hourDatePopUp.daysOfWork.HourDateFragmentInserter;

import java.util.ArrayList;
import java.util.List;

public class DayOfWorkUiService {
    private DatePickerPopUp datePickerPopUp;
    private final List<HourDateFragmentInserter> hourDateFragmentInserterList;

    private final ViewGroup viewGroupToInsertHourDateFragment;



    public DayOfWorkUiService(ViewGroup viewGroupRoot, ViewGroup viewGroupToInsertHourDateFragment) {
        LayoutInflater layoutInflater = ((Activity)viewGroupRoot.getContext()).getLayoutInflater();
        this.datePickerPopUp = new PopUpFactoryImpl(layoutInflater).generateDatePickerPopUp(viewGroupRoot);
        this.viewGroupToInsertHourDateFragment = viewGroupToInsertHourDateFragment;
        this.hourDateFragmentInserterList = new ArrayList<>();
    }

    public DayOfWorkUiService(ViewGroup viewGroupToInsertHourDateFragment) {
        this.viewGroupToInsertHourDateFragment = viewGroupToInsertHourDateFragment;
        this.hourDateFragmentInserterList = new ArrayList<>();
    }

    public void onPickerDayOfWorkClickInSaveButton(){

        Button buttonSave = datePickerPopUp.getButtonSave();
        buttonSave.setOnClickListener(v -> {
            datePickerPopUp.convertToDayOfWork();
            if (datePickerPopUp.DatePickerIsCorrect(getAllDayOfWork())){
                saveinHourDateFragment(datePickerPopUp.getDayOfWork());
                datePickerPopUp.getPopUpWindow().dismiss();
            }

        });
    }

    private void saveinHourDateFragment(DayOfWork dayOfWork) {
        HourDateFragmentInserter hourDateFragmentInserter = getOrGenerateAndAddHourDateFragmentInserter(dayOfWork);
        hourDateFragmentInserter.generateAndInsertInLayoutOfHourDateFragment(dayOfWork);
    }

    public void insertAllDayOfWork(DayOfWorkDao dao,long clinicaId) {
        List<DayOfWork> dayOfWorkList = dao.getDaysforClinicaId(clinicaId);
        for (DayOfWork dayOfWork : dayOfWorkList){
            HourDateFragmentInserter hourDateFragmentInserter = generateHourDateFragmentInserter(dayOfWork,dao);
            OnClickInHourDateFragment(hourDateFragmentInserter,dayOfWork);


        }
    }
    public void insertAllDayOfWorkWithNotRemoveButton(DayOfWorkDao dao,long clinicaId) {
        List<DayOfWork> dayOfWorkList = dao.getDaysforClinicaId(clinicaId);
        for (DayOfWork dayOfWork : dayOfWorkList){
            HourDateFragmentInserter hourDateFragmentInserter = generateHourDateFragmentInserter(dayOfWork,dao);
            hourDateFragmentInserter.getHourDateFragment().removeButtonDelete();

        }
    }

    public HourDateFragmentInserter generateHourDateFragmentInserter(DayOfWork dayOfWork,DayOfWorkDao dao){
        HourDateFragmentInserter hourDateFragmentInserter = getOrGenerateAndAddHourDateFragmentInserter(dayOfWork);
        hourDateFragmentInserter.generateAndInsertInLayoutOfHourDateFragment(dayOfWork);
        configureButtonDelete(hourDateFragmentInserter,dao);
        return hourDateFragmentInserter;

    }

    private HourDateFragmentInserter getOrGenerateAndAddHourDateFragmentInserter(DayOfWork dayOfWork) {
        if (dayOfWork.getId() > 0){
            for (HourDateFragmentInserter inserter : hourDateFragmentInserterList)
                if (inserter.dayOfWorkEquals(dayOfWork)) return inserter;

        }
        HourDateFragmentInserter inserter = new HourDateFragmentInserter(viewGroupToInsertHourDateFragment);
        hourDateFragmentInserterList.add(inserter);

        return inserter;
    }
    public void configureButtonDelete(HourDateFragmentInserter hourDateFragmentInserter,DayOfWorkDao dao){
        ImageButton trashButton = hourDateFragmentInserter.getHourDateFragment().getTrashButton();
        DayOfWork dayOfWork = hourDateFragmentInserter.getDayOfWork();
        trashButton.setOnClickListener(v ->{
            viewGroupToInsertHourDateFragment.removeView(hourDateFragmentInserter.getHourDateFragment().getLayout());
            if (dayOfWork != null && dayOfWork.getId() != 0){
                dao.delete(dayOfWork);
            }
        });
    }

    public void OnClickInHourDateFragment(HourDateFragmentInserter hourDateFragmentInserter, DayOfWork dayOfWork){
        HourDateFragment hourDateFragment = hourDateFragmentInserter.getHourDateFragment();
        ViewGroup layout = hourDateFragment.getLayout();
        layout.setOnClickListener(v -> {
            datePickerPopUp.modifyDay(dayOfWork);
            onPickerDayOfWorkClickInSaveButton();
        });
    }

    public List<DayOfWork> getAllDayOfWork(){
        List<DayOfWork> dayOfWorkList = new ArrayList<>();
        for (HourDateFragmentInserter inserter: hourDateFragmentInserterList){
            dayOfWorkList.add(inserter.getDayOfWork());
        }
        return dayOfWorkList;
    }

    public void generatePopUpOfDatePickerToNewDayOfWork() {
        datePickerPopUp.modifyDay(new DayOfWork());
    }




}
