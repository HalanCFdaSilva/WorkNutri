package com.example.nutricoop.ui.addPaciente;

import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.nutricoop.R;
import com.example.nutricoop.calcular.Conversor;
import com.example.nutricoop.sqlLite.domain.paciente.Antropometria;
import com.example.nutricoop.sqlLite.domain.paciente.Paciente;
import com.example.nutricoop.sqlLite.domain.paciente.Patologia;
import com.example.nutricoop.ui.InsertSelectViewSupport;

public class GenerateEntitieOfAddPaciente {
    private final ViewGroup viewOfQuestionary;

    public GenerateEntitieOfAddPaciente(ViewGroup viewGroup) {
        this.viewOfQuestionary = viewGroup;
    }

    public Patologia generatePatologia() {

        Patologia patologia = new Patologia();
        patologia.setPatologiaAtual(getStringOfEditText(R.id.paciente_descrition_patologia_patologia_atual));
        patologia.setUrina(getStringOfEditText(R.id.add_paciente_patologia_edit_text_urina));
        patologia.setFezes(getStringOfEditText(R.id.add_paciente_patologia_edit_text_fezes));
        patologia.setHoraSono(getStringOfEditText(R.id.add_paciente_patologia_edit_text_sono));
        patologia.setMedicacao(getStringOfEditText(R.id.add_paciente_patologia_edit_text_medicacao));
        patologia.setSuplemento(getStringOfEditText(R.id.add_paciente_patologia_edit_text_suplemento));
        patologia.setFumante(getStringOfEditText(R.id.add_paciente_patologia_edit_text_fumante));
        patologia.setEtilico(getStringOfEditText(R.id.add_paciente_patologia_edit_text_etilico));
        patologia.setAlergiaAlimentar(getStringOfEditText(R.id.add_paciente_patologia_edit_text_alergia));
        patologia.setConsumoAgua(getStringOfEditText(R.id.add_paciente_patologia_edit_text_agua));
        patologia.setAcucar(getStringOfEditText(R.id.add_paciente_patologia_edit_text_acucar));
        patologia.setAtividadeFisica(getStringOfEditText(R.id.add_paciente_patologia_edit_text_atividade));


        return patologia;
    }

    public Antropometria generateAntropometria(Paciente paciente) {

        double atual = Double.parseDouble(getStringOfEditText(R.id.add_paciente_antropometria_edit_text_peso_atual));
        int position = getPositionOfSpinner(R.id.add_paciente_antropometria_spinner_peso_atual);
        String pesoAtualConvertido = Conversor.convertToGram(position,atual);


        atual = Double.parseDouble(getStringOfEditText(R.id.paciente_descrition_antropometria_altura));
        position = getPositionOfSpinner(R.id.add_paciente_antropometria_spinner_altura);
        String alturaConvertido = Conversor.convertToGram(position,atual);

        Antropometria antropometria = new Antropometria(pesoAtualConvertido,alturaConvertido,paciente);

        atual = Double.parseDouble(getStringOfEditText(R.id.add_paciente_antropometria_edit_text_peso_ideal));
        position = getPositionOfSpinner(R.id.add_paciente_antropometria_spinner_peso_ideal);
        String pesoIdealConvertido = Conversor.convertToGram(position,atual);
        antropometria.setPesoDesejado(pesoIdealConvertido);

        antropometria.setCircumferenciaBracoDir(this.getStringOfEditText(R.id.add_paciente_antropometria_edit_text_circum_braco));
        antropometria.setCircumferenciaCoxaDir(this.getStringOfEditText(R.id.add_paciente_antropometria_edit_text_circum_coxa));
        antropometria.setCircumferenciaAbdomen(this.getStringOfEditText(R.id.add_paciente_antropometria_edit_text_circum_abdomen));
        antropometria.setCircumferenciaCintura(this.getStringOfEditText(R.id.add_paciente_antropometria_edit_text_circum_cintura));
        antropometria.setCircumferenciaQuadril(this.getStringOfEditText(R.id.add_paciente_antropometria_edit_text_circum_quadril));
        return antropometria;
    }

    public Paciente generatePaciente() {
        Paciente paciente = new Paciente();

        paciente.setNomePaciente(this.getStringOfEditText(R.id.add_paciente_dados_pessoais_name));


        paciente.setCpf(this.getStringOfEditText(R.id.add_paciente_dados_pessoais_cpf));


        int i = getPositionOfSpinner(R.id.add_paciente_dados_pessoais_genero_spinner);
        paciente.setGenero(i == 0 ? 'M' : 'F');

        paciente.setIdade(InsertSelectViewSupport.getIntOfEditText(
                viewOfQuestionary.findViewById(R.id.add_paciente_dados_pessoais_idade)));

        paciente.setNascimento(getStringOfEditText(R.id.add_paciente_dados_pessoais_nascimento));

        paciente.setEmail(getStringOfEditText(R.id.add_paciente_dados_pessoais_email));

        paciente.setTelefone(getStringOfEditText(R.id.add_paciente_dados_pessoais_fone));
        paciente.setObservacoes(getStringOfEditText(R.id.add_paciente_Multiline_edit_observation));

        return paciente;
    }
    private String getStringOfEditText(int id){
        EditText viewById = viewOfQuestionary.findViewById(id);
        return InsertSelectViewSupport.getStringOfEditText(viewById);
    }
    private int getPositionOfSpinner(int id){
        Spinner spinner = viewOfQuestionary.findViewById(R.id.add_paciente_antropometria_spinner_peso_ideal);
        return spinner.getSelectedItemPosition();
    }
}
