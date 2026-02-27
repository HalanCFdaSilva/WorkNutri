package com.example.worknutri.ui.forms.patientForm;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.worknutri.R;
import com.example.worknutri.ui.editTextKeysListener.CalendarioKeyListener;
import com.example.worknutri.ui.editTextKeysListener.PhoneKeyListener;
import com.example.worknutri.ui.editTextKeysListener.PesoKeyListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PatientFormActivity extends AppCompatActivity {

    private PatientFormAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_form_activity);
        adapter = new PatientFormAdapter(this);

        showAnthropometricCalc();

        adapter.setClinics(findViewById(R.id.patient_form_activity_personal_data_clinic_spinner));

        adapter.configuraBottomNav(findViewById(R.id.patient_form_activity_nav_view),getIntent());

        adapter.insertInFormulario(getIntent(), findViewById(R.id.patient_form_activity_constraint_layout));

        adapter.OpenActivityLevelPopUpOnClickIn(
                findViewById(R.id.patient_form_activity_anthropometry_calculations_activity_level_info_imageview),
                findViewById(R.id.patient_form_activity_constraint_layout));


        this.insertKeyListeners();
        FloatingActionButton saveButton = findViewById(R.id.patient_form_activity_save_fab);
        saveButton.setOnClickListener(v -> saveLayout());
    }


    @Override
    protected void onStart() {
        super.onStart();
        Button button = findViewById(R.id.patient_form_activity_pathological_add_button);
        button.setOnClickListener(onClick->
                adapter.generateAddPatologyPopup(findViewById(R.id.patient_form_activity_constraint_layout),
                        findViewById(R.id.patient_form_activity_pathological_layout_content)));

    }

    private void insertKeyListeners() {
        EditText editText = findViewById(R.id.patient_form_activity_personal_data_phone);
        editText.setOnKeyListener(new PhoneKeyListener());

        editText = findViewById(R.id.patient_form_activity_personal_data_birthday);
        editText.setOnKeyListener(new CalendarioKeyListener());

        PesoKeyListener keyListener = new PesoKeyListener();
        keyListener.setSpinnerAltura(findViewById(R.id.patient_form_activity_anthropometry_height_spinner));
        keyListener.setPesoIdeal(findViewById(R.id.patient_form_activity_anthropometry_weight_ideal_spinner),
                findViewById(R.id.patient_form_activity_anthropometry_weight_ideal));
        editText = findViewById(R.id.patient_form_activity_anthropometry_height);
        editText.setOnKeyListener(keyListener);


    }


    private void showAnthropometricCalc() {
        Button button = findViewById(R.id.patient_form_activity_anthropometry_calculations_button);
        button.setOnClickListener(v -> adapter
                .generateAnthropometricDataPopUp(findViewById(R.id.patient_form_activity_constraint_layout)));
    }

    private void saveLayout() {
        ViewGroup viewGroup = findViewById(R.id.patient_form_activity_constraint_layout);

        if (adapter.validateForm(viewGroup, findViewById(R.id.patient_form_activity_error))) {
            adapter.savePatientInDb(viewGroup);
            finish();
        }

    }


}
