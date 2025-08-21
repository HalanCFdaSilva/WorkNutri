package com.example.worknutri.ui.popUp.formsPopUp;

import com.example.worknutri.sqlLite.domain.paciente.Patologia;

public class PathologyTypeToPathologyMapper {
    private final PathologyType category;

    public PathologyTypeToPathologyMapper(PathologyType category) {
        this.category = category;
    }

    public void insertValueInCorrectAtribute(Patologia pathology, String value) {

        if (category != null && pathology != null) {
            switch (category){
                case ACTUAL_PATHOLOGY: {
                    pathology.setPatologiaAtual(value);
                    break;
                }
                case STOOL:{
                    pathology.setFezes(value);
                    break;
                }
                case SUGAR:{
                    pathology.setAcucar(value);
                    break;
                }
                case URINE:{
                    pathology.setUrina(value);
                    break;
                }
                case WATER:{
                    pathology.setConsumoAgua(value);
                    break;
                }
                case SMOKER:{
                    pathology.setFumante(value);
                    break;
                }
                case ALLERGY:{
                    pathology.setAlergiaAlimentar(value);
                    break;
                }
                case ETHYLIC:{
                    pathology.setEtilico(value);
                    break;
                }
                case SLUMBER:{
                    pathology.setHoraSono(value);
                    break;
                }
                case ACTIVITY:{
                    pathology.setAtividadeFisica(value);
                    break;
                }
                case MEDICATION:{
                    pathology.setMedicacao(value);
                    break;
                }
                case SUPPLEMENT:pathology.setSuplemento(value);
            }
        }
    }

    public String getValueOfPathology(Patologia pathology) {
        if (category != null && pathology != null) {
            switch (category){
                case SUPPLEMENT: return pathology.getSuplemento();
                case ACTIVITY: return pathology.getAtividadeFisica();
                case MEDICATION: return pathology.getMedicacao();
                case SLUMBER: return pathology.getHoraSono();
                case ETHYLIC: return pathology.getEtilico();
                case ALLERGY: return pathology.getAlergiaAlimentar();
                case SMOKER: return pathology.getFumante();
                case WATER: return pathology.getConsumoAgua();
                case URINE: return pathology.getUrina();
                case SUGAR: return pathology.getAcucar();
                case STOOL: return pathology.getFezes();
                case ACTUAL_PATHOLOGY: return pathology.getPatologiaAtual();
            }
        }
        return null;
    }
}
