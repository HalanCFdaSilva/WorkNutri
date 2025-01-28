package com.example.nutricoop.sqlLite.clinica.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
}
