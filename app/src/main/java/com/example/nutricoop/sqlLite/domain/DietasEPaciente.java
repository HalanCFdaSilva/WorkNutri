package com.example.nutricoop.sqlLite.domain;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.nutricoop.sqlLite.domain.paciente.Paciente;

import java.util.List;

public class DietasEPaciente {
    @Embedded public Paciente paciente;
    @Relation(parentColumn = "cpf",entityColumn = "cpf_paciente")
    public List<Dietas> dietas;
}
