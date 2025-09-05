package com.example.worknutri.ui.popUp.pathology;

import com.example.worknutri.sqlLite.domain.paciente.Patologia;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PathologyTypeToPathologyMapperTest {
    private Patologia patologia;


    @Before
    public void generatePatologia(){
        patologia = new Patologia();

    }

    @Test
    public void mapperAActualPathologyTypeToInsertAValueInCorrectPathologyVariavel(){

        String expected = "Patologia Atual";
        insertValue(PathologyField.ACTUAL_PATHOLOGY,expected);
        Assert.assertEquals(expected, patologia.getPatologiaAtual());
    }

    @Test
    public void mapperAUrineTypeToInsertAValueInCorrectPathologyVariavel(){

        String expected = "Urina";
        insertValue(PathologyField.URINE,expected);
        Assert.assertEquals(expected, patologia.getUrina());
    }

    @Test
    public void mapperAStoolTypeToInsertAValueInCorrectPathologyVariavel(){

        String expected = "Fezes";
        insertValue(PathologyField.STOOL,expected);
        Assert.assertEquals(expected, patologia.getFezes());
    }

    @Test
    public void mapperASlumberTypeToInsertAValueInCorrectPathologyVariavel(){

        String expected = "Sono";
        insertValue(PathologyField.SLUMBER,expected);
        Assert.assertEquals(expected, patologia.getHoraSono());
    }

    @Test
    public void mapperAMedicationTypeToInsertAValueInCorrectPathologyVariavel(){

        String expected = "Medicacao";
        insertValue(PathologyField.MEDICATION,expected);
        Assert.assertEquals(expected, patologia.getMedicacao());
    }

    @Test
    public void mapperASupplementTypeToInsertAValueInCorrectPathologyVariavel(){

        String expected = "Suplemento";
        insertValue(PathologyField.SUPPLEMENT,expected);
        Assert.assertEquals(expected, patologia.getSuplemento());
    }

    @Test
    public void mapperAEthylicTypeToInsertAValueInCorrectPathologyVariavel(){

        String expected = "Etilico";
        insertValue(PathologyField.ETHYLIC,expected);
        Assert.assertEquals(expected, patologia.getEtilico());
    }
    @Test
    public void mapperASmokerTypeToInsertAValueInCorrectPathologyVariavel(){

        String expected = "Fumante";
        insertValue(PathologyField.SMOKER,expected);
        Assert.assertEquals(expected, patologia.getFumante());
    }
    @Test
    public void mapperAAllergyTypeToInsertAValueInCorrectPathologyVariavel(){

        String expected = "Alergia Alimentar";
        insertValue(PathologyField.ALLERGY,expected);
        Assert.assertEquals(expected, patologia.getAlergiaAlimentar());
    }
    @Test
    public void mapperAWaterTypeToInsertAValueInCorrectPathologyVariavel(){

        String expected = "Agua";
        insertValue(PathologyField.WATER,expected);
        Assert.assertEquals(expected, patologia.getConsumoAgua());
    }
    @Test
    public void mapperASugarTypeToInsertAValueInCorrectPathologyVariavel(){

        String expected = "Acucar";
        insertValue(PathologyField.SUGAR,expected);
        Assert.assertEquals(expected, patologia.getAcucar());
    }
    @Test
    public void mapperAActivityTypeToInsertAValueInCorrectPathologyVariavel(){

        String expected = "Bebida";
        insertValue(PathologyField.ACTIVITY,expected);
        Assert.assertEquals(expected, patologia.getAtividadeFisica());
    }


    @Test
    public void mapperAGivenNullPathologyTypeWhenInsertValueThenNoActionIsPerformed(){
        String expected = "Some Value";
        patologia = new Patologia();
        insertValue(null, expected);
        Assert.assertNull(patologia.getPatologiaAtual());
        Assert.assertNull(patologia.getUrina());
        Assert.assertNull(patologia.getFezes());
        Assert.assertNull(patologia.getHoraSono());
        Assert.assertNull(patologia.getMedicacao());
        Assert.assertNull(patologia.getSuplemento());
        Assert.assertNull(patologia.getEtilico());
        Assert.assertNull(patologia.getFumante());
        Assert.assertNull(patologia.getAlergiaAlimentar());
        Assert.assertNull(patologia.getConsumoAgua());
        Assert.assertNull(patologia.getAcucar());
        Assert.assertNull(patologia.getAtividadeFisica());
    }



    public void insertValue(PathologyField pathologyField, String value) {
        PathologyFieldMapper mapper = new PathologyFieldMapper(pathologyField);
        mapper.setValue(patologia, value);
    }



    @Test
    public void mapperAActualPathologyTypeToGetAValueOfCorrectPathologyVariavel(){

        String valueReturn = getValue(PathologyField.ACTUAL_PATHOLOGY);
        Assert.assertEquals(patologia.getPatologiaAtual(),valueReturn);
    }

    @Test
    public void mapperAUrineTypeToGetAValueOfCorrectPathologyVariavel(){

        String valueReturn = getValue(PathologyField.URINE);
        Assert.assertEquals(patologia.getUrina(),valueReturn);

    }

    @Test
    public void mapperAStoolTypeToGetAValueOfCorrectPathologyVariavel(){
        String valueReturn = getValue(PathologyField.STOOL);
        Assert.assertEquals(patologia.getFezes(),valueReturn);

    }

    @Test
    public void mapperASlumberTypeToGetAValueOfCorrectPathologyVariavel(){
        String valueReturn = getValue(PathologyField.SLUMBER);
        Assert.assertEquals(patologia.getHoraSono(),valueReturn);
    }

    @Test
    public void mapperAMedicationTypeToGetAValueOfCorrectPathologyVariavel(){
        String valueReturn = getValue(PathologyField.MEDICATION);
        Assert.assertEquals(patologia.getMedicacao(),valueReturn);
    }

    @Test
    public void mapperASupplementTypeToGetAValueOfCorrectPathologyVariavel(){
        String valueReturn = getValue(PathologyField.SUPPLEMENT);
        Assert.assertEquals(patologia.getSuplemento(),valueReturn);
    }

    @Test
    public void mapperAEthylicTypeToGetAValueOfCorrectPathologyVariavel(){
        String valueReturn = getValue(PathologyField.ETHYLIC);
        Assert.assertEquals(patologia.getEtilico(),valueReturn);
    }
    @Test
    public void mapperASmokerTypeToGetAValueOfCorrectPathologyVariavel(){
        String valueReturn = getValue(PathologyField.SMOKER);
        Assert.assertEquals(patologia.getFumante(),valueReturn);
    }
    @Test
    public void mapperAAllergyTypeToGetAValueOfCorrectPathologyVariavel(){
        String valueReturn = getValue(PathologyField.ALLERGY);
        Assert.assertEquals(patologia.getAlergiaAlimentar(),valueReturn);
    }
    @Test
    public void mapperAWaterTypeToGetAValueOfCorrectPathologyVariavel(){
        String valueReturn = getValue(PathologyField.WATER);
        Assert.assertEquals(patologia.getConsumoAgua(),valueReturn);
    }
    @Test
    public void mapperASugarTypeToGetAValueOfCorrectPathologyVariavel(){

        String valueReturn = getValue(PathologyField.SUGAR);
        Assert.assertEquals(patologia.getAcucar(),valueReturn);
    }
    @Test
    public void mapperAActivityTypeToGetAValueOfCorrectPathologyVariavel(){

        String valueReturn = getValue(PathologyField.ACTIVITY);
        Assert.assertEquals(patologia.getAtividadeFisica(),valueReturn);
    }
    public String getValue(PathologyField pathologyField) {
        resetPatologia();
        PathologyFieldMapper mapper = new PathologyFieldMapper(pathologyField);
        return mapper.getValue(patologia);
    }
    public void resetPatologia() {
        patologia = new Patologia();
        patologia.setSuplemento("Suplemento a1");
        patologia.setMedicacao("Medicacao a1");
        patologia.setHoraSono("Sono a1");
        patologia.setFezes("Fezes a1");
        patologia.setUrina("Urina a1");
        patologia.setPatologiaAtual("Patologia Atual a1");
        patologia.setEtilico("Etilico a1");
        patologia.setFumante("Fumante a1");
        patologia.setAlergiaAlimentar("Alergia Alimentar a1");
        patologia.setConsumoAgua("Agua a1");
        patologia.setAcucar("Acucar a1");
        patologia.setAtividadeFisica("Bebida a1");
    }

    @Test
    public void mapperAGivenPathologyTypeWhenGetValueThenReturnNullIfPathologyIsNull() {
        PathologyFieldMapper mapper = new PathologyFieldMapper(PathologyField.ACTUAL_PATHOLOGY);
        String value = mapper.getValue(null);
        Assert.assertNull(value);
    }

}
