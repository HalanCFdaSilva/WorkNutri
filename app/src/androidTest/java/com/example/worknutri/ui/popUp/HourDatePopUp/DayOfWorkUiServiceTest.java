package com.example.worknutri.ui.popUp.HourDatePopUp;

import static org.junit.Assert.*;


import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.support.FakeDayOfWorkDao;
import com.example.worknutri.support.TestUtil;
import com.example.worknutri.ui.ActivityToTest;
import com.example.worknutri.ui.popUp.hourDatePopUp.DayOfWorkUiService;
import com.example.worknutri.ui.popUp.hourDatePopUp.datePicker.DatePickerPopUp;
import com.example.worknutri.ui.popUp.hourDatePopUp.daysOfWork.HourDateFragmentInserter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.lang.reflect.Field;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DayOfWorkUiServiceTest {
    private LinearLayout viewGroupToInsert;
    private DayOfWorkUiService service;

    @Before
    public void setUp() {
        Context context = TestUtil.getThemedContext();
        viewGroupToInsert = new LinearLayout(context);

        // usa construtor que cria DatePickerPopUp real via PopUpFactoryImpl
        service = new DayOfWorkUiService(new LinearLayout(context), viewGroupToInsert);
    }

    // helper para acessar campo privado datePickerPopUp
    private DatePickerPopUp getDatePickerPopUp() throws Exception {
        Field f = service.getClass().getDeclaredField("datePickerPopUp");
        f.setAccessible(true);
        return (DatePickerPopUp) f.get(service);
    }

    @Test
    public void onSaveButton_shouldAddNewDay_whenDateIsValid() throws Exception {
        // prepara um DayOfWork válido e injeta nos campos do DatePicker (via modifyDay)
        DayOfWork valid = new DayOfWork();
        valid.setDayOfWeek("Segunda");
        valid.setHoraInicio("08:00");
        valid.setHoraFim("17:00");
        valid.setId(0);

        DatePickerPopUp datePicker = getDatePickerPopUp();
        // preenche popup com os valores válidos
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> datePicker.modifyDay(valid));

        // registra o listener de salvar e executa clique no botão
        service.onPickerDayOfWorkClickInSaveButton();

        Button btnSave = datePicker.getButtonSave();
        assertNotNull("Botão salvar não encontrado", btnSave);

        InstrumentationRegistry.getInstrumentation().runOnMainSync(btnSave::performClick);

        // verifica que foi adicionado um inserter e que getAllDayOfWork retorna o dia
        List<DayOfWork> all = service.getAllDayOfWork();
        assertEquals(1, all.size());
        DayOfWork added = all.get(0);
        assertEquals("08:00", added.getHoraInicio());
        assertEquals("17:00", added.getHoraFim());
        assertEquals("SEGUNDA", added.getDayOfWeek());
    }

    @Test
    public void onSaveButton_shouldNotAddNewDay_whenDateIsInvalid() throws Exception {
        // DayOfWork inválido (sem dia da semana) deve ser rejeitado
        DayOfWork invalid = new DayOfWork();
        invalid.setDayOfWeek("");
        invalid.setHoraInicio("08:00");
        invalid.setHoraFim("17:00");
        invalid.setId(1);

        DatePickerPopUp datePicker = getDatePickerPopUp();
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> datePicker.modifyDay(invalid));

        service.onPickerDayOfWorkClickInSaveButton();

        Button btnSave = datePicker.getButtonSave();
        assertNotNull(btnSave);

        InstrumentationRegistry.getInstrumentation().runOnMainSync(btnSave::performClick);

        // não deve ter adicionado nenhum day
        List<DayOfWork> all = service.getAllDayOfWork();
        assertEquals(0, all.size());
    }

    @Test
    public void configureButtonDelete_shouldRemoveViewAndCallDaoDelete_whenDayHasId(){
        // cria day com id > 0 para ativar delete no DAO
        DayOfWork day = new DayOfWork();
        day.setId(42);
        day.setDayOfWeek("Terça");
        day.setHoraInicio("09:00");
        day.setHoraFim("18:00");

        FakeDayOfWorkDao fakeDao = new FakeDayOfWorkDao();


        // gera inserter (gera layout dentro de viewGroupToInsert)
        HourDateFragmentInserter inserter = service.generateHourDateFragmentInserter(day, fakeDao);

        // antes: deve ter adicionado uma view
        assertTrue(viewGroupToInsert.getChildCount() > 0);

        // obtém botão de lixeira e clica
        View trash = inserter.getHourDateFragment().getTrashButton();
        assertNotNull(trash);

        InstrumentationRegistry.getInstrumentation().runOnMainSync(trash::performClick);

        // view deve ter sido removida e DAO deve ter recebido delete
        assertEquals(0, viewGroupToInsert.getChildCount());
        assertTrue(fakeDao.deletedCalled);
        assertEquals(42, fakeDao.deletedDay.getId());
    }
    @Test
    public void configureButtonDelete_shouldNotCallDaoDelete_whenDayHasIdZero() {
        DayOfWork day = new DayOfWork();
        day.setId(0); // id zero não deve chamar delete no DAO
        day.setDayOfWeek("Sábado");
        day.setHoraInicio("11:00");
        day.setHoraFim("14:00");

        FakeDayOfWorkDao fakeDao = new FakeDayOfWorkDao();

        HourDateFragmentInserter inserter = service.generateHourDateFragmentInserter(day, fakeDao);

        assertTrue(viewGroupToInsert.getChildCount() > 0);

        View trash = inserter.getHourDateFragment().getTrashButton();
        assertNotNull(trash);

        InstrumentationRegistry.getInstrumentation().runOnMainSync(trash::performClick);

        // view removida mas DAO não chamado
        assertEquals(0, viewGroupToInsert.getChildCount());
        assertFalse(fakeDao.deletedCalled);
    }

    @Test
    public void onClickFragment_shouldModifyDayInPopup() throws Exception {
        DayOfWork day = new DayOfWork();
        day.setId(7);
        day.setDayOfWeek("Quarta");
        day.setHoraInicio("10:00");
        day.setHoraFim("19:00");

        FakeDayOfWorkDao fakeDao = new FakeDayOfWorkDao();

        ActivityScenario<ActivityToTest> activityScenario = ActivityScenario.launch(ActivityToTest.class);
        activityScenario.onActivity(activity -> {
            // substitui service por um que use o layout da activity
            service = new DayOfWorkUiService(
                    activity.findViewById(com.example.worknutri.R.id.layout_test),
                    viewGroupToInsert);

            HourDateFragmentInserter inserter = service.generateHourDateFragmentInserter(day, fakeDao);

            // configura listener que mostra popup ao clicar
            service.OnClickInHourDateFragment(inserter, day);
            View layout = inserter.getHourDateFragment().getLayout();
            layout.performClick();

        });


        // verificar que o DatePicker foi modificado com o mesmo day
        DatePickerPopUp datePicker = getDatePickerPopUp();
        DayOfWork current = datePicker.getDayOfWork();
        assertNotNull(current);
        assertEquals(7, current.getId());
        assertEquals("Quarta", current.getDayOfWeek());
    }

    @Test
    public void generatePopUpOfDatePickerToNewDayOfWork_shouldProvideEmptyDay() throws Exception {
        DatePickerPopUp datePicker = getDatePickerPopUp();

        // chama método que delega modifyDay(new DayOfWork())
        service.generatePopUpOfDatePickerToNewDayOfWork();

        DayOfWork d = datePicker.getDayOfWork();
        assertNotNull(d);
        assertEquals(0, d.getId());
        assertEquals("", d.getDayOfWeek());
        assertEquals("", d.getHoraInicio());
        assertEquals("", d.getHoraFim());
    }

    @Test
    public void generateHourDateFragmentInserter_shouldReuseInserter_whenSameId() {
        DayOfWork day = new DayOfWork();
        day.setId(5);
        day.setDayOfWeek("Sexta");
        day.setHoraInicio("07:00");
        day.setHoraFim("15:00");

        FakeDayOfWorkDao fakeDao = new FakeDayOfWorkDao();

        HourDateFragmentInserter inserter1 = service.generateHourDateFragmentInserter(day, fakeDao);
        // gerar de novo com o mesmo objeto/day deve retornar o mesmo inserter
        HourDateFragmentInserter inserter2 = service.generateHourDateFragmentInserter(day, fakeDao);

        assertSame("Inserter deveria ser o mesmo para mesmo id", inserter1, inserter2);
        // apenas uma view deve ter sido adicionada no container
        assertEquals(1, viewGroupToInsert.getChildCount());
    }



}