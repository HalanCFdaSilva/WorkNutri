package com.example.nutricoop.sqlLite.domain.clinica;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "clinicas")
public class Clinica {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nome;
    private String telefone1;
    private String telefone2;

}
