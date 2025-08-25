package com.example.worknutri.ui.popUp.formsPopUpTest;

import com.example.worknutri.R;
import com.example.worknutri.ui.popUp.formsPopUp.PathologyType;

import org.junit.Test;

public class PathologyTypeTest {

    @Test
    public void testPathologyTypeNameAttribute() {
        // Test to ensure that all PathologyType enum values are correctly defined
        for (PathologyType type : PathologyType.values()) {
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
        for (PathologyType type : PathologyType.values()) {
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
        assert PathologyType.values().length == expectedCount : "Expected " + expectedCount + " PathologyType values, but found " + PathologyType.values().length;
    }

    @Test
    public void testPathologyTypeFromMethod() {
        // Test to ensure that the from method correctly maps strings to PathologyType enum values
        assert PathologyType.from("Patologia Atual") == PathologyType.ACTUAL_PATHOLOGY;
        assert PathologyType.from("Urina") == PathologyType.URINE;
        assert PathologyType.from("Fezes") == PathologyType.STOOL;
        assert PathologyType.from("Sono") == PathologyType.SLUMBER;
        assert PathologyType.from("Medicação") == PathologyType.MEDICATION;
        assert PathologyType.from("Suplementação") == PathologyType.SUPPLEMENT;
        assert PathologyType.from("Etilico") == PathologyType.ETHYLIC;
        assert PathologyType.from("Fumante") == PathologyType.SMOKER;
        assert PathologyType.from("Alergia") == PathologyType.ALLERGY;
        assert PathologyType.from("Água") == PathologyType.WATER;
        assert PathologyType.from("Açúcar") == PathologyType.SUGAR;
        assert PathologyType.from("Atividade Fisica") == PathologyType.ACTIVITY;
    }
    @Test
    public void testIfFromMethodThrowsIllegalArgumentExceptionWereReceiveInvalidParameter() {
        // Test to ensure that the from method throws an exception for invalid input
        try {
            PathologyType.from("InvalidType");
            assert false : "Expected IllegalArgumentException for invalid input";
        } catch (IllegalArgumentException e) {
            assert true; // Exception was thrown as expected
        }
    }

    @Test
    public void testIfFromMethodThrowsIllegalArgumentExceptionWereReceiveNullParameter() {
        // Test to ensure that the from method throws an exception for invalid input
        try {
            PathologyType.from(null);
            assert false : "Expected IllegalArgumentException for invalid input";
        } catch (IllegalArgumentException e) {
            assert true; // Exception was thrown as expected
        }
    }

    @Test
    public void testPathologyTypeHintAttribute() {
        for (PathologyType type : PathologyType.values()) {
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
