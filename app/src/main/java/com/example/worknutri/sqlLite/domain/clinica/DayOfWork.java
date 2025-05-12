package com.example.worknutri.sqlLite.domain.clinica;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.worknutri.util.StringsUtil;

@Entity(tableName = "day_of_work")
public class DayOfWork {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "id_clinica")
    private long idClinica;

    @ColumnInfo(name = "day_of_week")
    private String dayOfWeek;

    @ColumnInfo(name = "hora_inicio")
    private String horaInicio;

    @ColumnInfo(name = "hora_fim")
    private String horaFim;

    public DayOfWork() {
        id = 0;
        dayOfWeek = "";
        horaInicio = "";
        horaFim = "";

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdClinica() {
        return idClinica;
    }

    public void setIdClinica(long idClinica) {
        this.idClinica = idClinica;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    public boolean isDaysOfWorkColidde(DayOfWork dayOfWork){
        if (this.id != dayOfWork.id || this.id == 0){
            if (dayOfWork.getDayOfWeek().equals(this.getDayOfWeek())){
                int horaFimAnother = StringsUtil.convertHourStringInInt(dayOfWork.getHoraFim());
                if (this.horaEntreIntervalo(horaFimAnother)) return true;

                int horaInicioAnother = StringsUtil.convertHourStringInInt(dayOfWork.getHoraInicio());
                if ( this.horaEntreIntervalo(horaInicioAnother)) return true;
            }
        }
        return false;
    }

    private boolean horaEntreIntervalo(int hora) {
        int horaInicioThis = StringsUtil.convertHourStringInInt(this.getHoraInicio());
        int horaFimThis = StringsUtil.convertHourStringInInt(this.getHoraFim());
        return hora < horaFimThis && hora > horaInicioThis;



    }


}
