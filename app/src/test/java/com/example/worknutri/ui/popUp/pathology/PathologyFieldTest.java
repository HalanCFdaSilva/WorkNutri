package com.example.worknutri.ui.popUp.pathology;

import com.example.worknutri.R;

import org.junit.Test;

public class PathologyFieldTest {

    @Test
    public void testPathologyTypeNameAttribute() {
        // Test to ensure that all PathologyType enum values are correctly defined
        for (PathologyField type : PathologyField.values()) {
            switch (type) {
                case ACTUAL_PATHOLOGY: {
                    assert "Patologia Atual".equals(type.getName());
                    break;
                }
                case URINE: {
                    assert "Urina".equals(type.getName());
                    break;
                }
                case STOOL:{
                    assert "Fezes".equals(type.getName());
                    break;
                }
                case SLUMBER: {
                    assert "Sono".equals(type.getName());
                    break;
                }
                case MEDICATION:{
                    assert "Medicação".equals(type.getName());
                    break;
                }
                case SUPPLEMENT:{
                    assert "Suplementação".equals(type.getName());
                    break;
                }
                case ETHYLIC: {
                    assert "Etilico".equals(type.getName());
                    break;
                }
                case SMOKER:{
                    assert "Fumante".equals(type.getName());
                    break;
                }
                case ALLERGY: {
                    assert "Alergia".equals(type.getName());
                    break;
                }
                case WATER: {
                    assert "Água".equals(type.getName());
                    break;
                }
                case SUGAR: {
                    assert "Açúcar".equals(type.getName());
                    break;
                }
                case ACTIVITY: {
                    assert "Atividade Fisica".equals(type.getName());
                    break;
                }
            }
        }
    }

    @Test
    public void testPathologyTypeGetUpperNameMethodIsCorrect() {
        // Test to ensure that the upper name of each PathologyType enum value is correctly defined
        for (PathologyField type : PathologyField.values()) {
            switch (type) {
                case ACTUAL_PATHOLOGY: {
                    assert "PATOLOGIA ATUAL".equals(type.getUpperName());
                    break;
                }
                case URINE: {
                    assert "URINA".equals(type.getUpperName());
                    break;
                }
                case STOOL:{
                    assert "FEZES".equals(type.getUpperName());
                    break;
                }
                case SLUMBER: {
                    assert "SONO".equals(type.getUpperName());
                    break;
                }
                case MEDICATION:{
                    assert "MEDICAÇÃO".equals(type.getUpperName());
                    break;
                }
                case SUPPLEMENT:{
                    assert "SUPLEMENTAÇÃO".equals(type.getUpperName());
                    break;
                }
                case ETHYLIC: {
                    assert "ETILICO".equals(type.getUpperName());
                    break;
                }
                case SMOKER:{
                    assert "FUMANTE".equals(type.getUpperName());
                    break;
                }
                case ALLERGY: {
                    assert "ALERGIA".equals(type.getUpperName());
                    break;
                }
                case WATER: {
                    assert "ÁGUA".equals(type.getUpperName());
                    break;
                }
                case SUGAR: {
                    assert "AÇÚCAR".equals(type.getUpperName());
                    break;
                }
                case ACTIVITY: {
                    assert "ATIVIDADE FISICA".equals(type.getUpperName());
                    break;
                }
            }
        }
    }

    @Test
    public void testPathologyTypeCount() {
        // Test to ensure that the number of PathologyType enum values is correct
        int expectedCount = 12; // Update this if the number of types changes
        assert PathologyField.values().length == expectedCount : "Expected " + expectedCount + " PathologyType values, but found " + PathologyField.values().length;
    }

    @Test
    public void testPathologyTypeFromMethod() {
        // Test to ensure that the from method correctly maps strings to PathologyType enum values
        assert PathologyField.from("Patologia Atual") == PathologyField.ACTUAL_PATHOLOGY;
        assert PathologyField.from("Urina") == PathologyField.URINE;
        assert PathologyField.from("Fezes") == PathologyField.STOOL;
        assert PathologyField.from("Sono") == PathologyField.SLUMBER;
        assert PathologyField.from("Medicação") == PathologyField.MEDICATION;
        assert PathologyField.from("Suplementação") == PathologyField.SUPPLEMENT;
        assert PathologyField.from("Etilico") == PathologyField.ETHYLIC;
        assert PathologyField.from("Fumante") == PathologyField.SMOKER;
        assert PathologyField.from("Alergia") == PathologyField.ALLERGY;
        assert PathologyField.from("Água") == PathologyField.WATER;
        assert PathologyField.from("Açúcar") == PathologyField.SUGAR;
        assert PathologyField.from("Atividade Fisica") == PathologyField.ACTIVITY;
    }
    @Test
    public void testIfFromMethodThrowsIllegalArgumentExceptionWereReceiveInvalidParameter() {
        // Test to ensure that the from method throws an exception for invalid input
        try {
            PathologyField.from("InvalidType");
            assert false : "Expected IllegalArgumentException for invalid input";
        } catch (IllegalArgumentException e) {
            assert true; // Exception was thrown as expected
        }
    }

    @Test
    public void testIfFromMethodThrowsIllegalArgumentExceptionWereReceiveNullParameter() {
        // Test to ensure that the from method throws an exception for invalid input
        try {
            PathologyField.from(null);
            assert false : "Expected IllegalArgumentException for invalid input";
        } catch (IllegalArgumentException e) {
            assert true; // Exception was thrown as expected
        }
    }

    @Test
    public void testPathologyTypeHintAttribute() {
        for (PathologyField type : PathologyField.values()) {
            switch (type) {
                case ACTUAL_PATHOLOGY: {
                    assert R.string.actual_pathology_hint== type.getHint();
                    break;
                }
                case URINE: {
                    assert R.string.urine_hint== type.getHint();
                    break;
                }
                case STOOL:{
                    assert R.string.stool_hint== type.getHint();
                    break;
                }
                case SLUMBER: {
                    assert R.string.slumber_hint== type.getHint();
                    break;
                }
                case MEDICATION:{
                    assert R.string.medication_hint== type.getHint();
                    break;
                }
                case SUPPLEMENT:{
                    assert R.string.supplement_hint== type.getHint();
                    break;
                }
                case ETHYLIC: {
                    assert R.string.ethylic_hint== type.getHint();
                    break;
                }
                case SMOKER:{
                    assert R.string.smoker_hint== type.getHint();
                    break;
                }
                case ALLERGY: {
                    assert R.string.allergy_hint== type.getHint();
                    break;
                }
                case WATER: {
                    assert R.string.water_hint== type.getHint();
                    break;
                }
                case SUGAR: {
                    assert R.string.sugar_hint== type.getHint();
                    break;
                }
                case ACTIVITY: {
                    assert R.string.activity_hint== type.getHint();
                    break;
                }
            }
        }
    }
}
