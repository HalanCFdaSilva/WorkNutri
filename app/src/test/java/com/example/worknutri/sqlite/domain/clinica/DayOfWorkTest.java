package com.example.worknutri.sqlite.domain.clinica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;

import org.junit.Test;

public class DayOfWorkTest {

    @Test
    public void defaultConstructor_initializesDefaults() {
        DayOfWork d = new DayOfWork();
        assertEquals(0L, d.getId());
        assertEquals("", d.getDayOfWeek());
        assertEquals("", d.getHoraInicio());
        assertEquals("", d.getHoraFim());
    }

    private DayOfWork newDay(long clinicaId, String inicio, String fim, String day) {
        DayOfWork d = new DayOfWork();
        d.setIdClinica(clinicaId);
        d.setHoraInicio(inicio);
        d.setHoraFim(fim);
        d.setDayOfWeek(day);
        return d;
    }

    @Test
    public void nonOverlapping_adjacentIntervals_returnFalse() {
        DayOfWork a = newDay(1, "08:00", "10:00", "Segunda");
        DayOfWork b = newDay(1, "10:00", "12:00", "Segunda");
        assertFalse(a.isDaysOfWorkColidde(b));
        assertFalse(b.isDaysOfWorkColidde(a));
    }

    @Test
    public void overlapping_whenOtherStartsInside_returnTrue() {
        DayOfWork a = newDay(1, "08:00", "12:00", "Terça");
        DayOfWork b = newDay(1, "10:00", "13:00", "Terça");
        assertTrue(a.isDaysOfWorkColidde(b));
        assertTrue(b.isDaysOfWorkColidde(a));
    }

    @Test
    public void overlapping_whenOtherEndsInside_returnTrue() {
        DayOfWork a = newDay(1, "10:00", "14:00", "Quarta");
        DayOfWork b = newDay(1, "08:00", "12:00", "Quarta");
        assertTrue(a.isDaysOfWorkColidde(b));
        assertTrue(b.isDaysOfWorkColidde(a));
    }

    @Test
    public void sameNonZeroId_doesNotConsiderCollision() {
        DayOfWork a = newDay(1, "09:00", "11:00", "Quinta");
        DayOfWork b = newDay(1, "09:00", "11:00", "Quinta");
        a.setId(5);
        b.setId(5);
        assertFalse(a.isDaysOfWorkColidde(b));
        assertFalse(b.isDaysOfWorkColidde(a));
    }

    @Test
    public void id_getterSetter() {
        DayOfWork d = new DayOfWork();
        d.setId(7L);
        assertEquals(7L, d.getId());
    }

    @Test
    public void idClinica_getterSetter() {
        DayOfWork d = new DayOfWork();
        assertEquals(0, d.getId());
        d.setIdClinica(42L);
        assertEquals(42L, d.getIdClinica());
    }

    @Test
    public void dayOfWeek_getterSetter() {
        DayOfWork d = new DayOfWork();
        assertEquals("", d.getDayOfWeek());
        d.setDayOfWeek("Segunda");
        assertEquals("Segunda", d.getDayOfWeek());
    }

    @Test
    public void horaInicio_getterSetter() {
        DayOfWork d = new DayOfWork();
        assertEquals("", d.getHoraInicio());
        d.setHoraInicio("08:00");
        assertEquals("08:00", d.getHoraInicio());
    }

    @Test
    public void horaFim_getterSetter() {
        DayOfWork d = new DayOfWork();
        assertEquals("", d.getHoraFim());
        d.setHoraFim("12:00");
        assertEquals("12:00", d.getHoraFim());
    }

    @Test
    public void insertDate_copiesFields() {
        DayOfWork src = new DayOfWork();
        assertEquals(0L, src.getId());
        assertEquals("", src.getDayOfWeek());
        assertEquals("", src.getHoraInicio());
        assertEquals("", src.getHoraFim());
        src.setDayOfWeek("Terça");
        src.setHoraInicio("09:00");
        src.setHoraFim("11:00");

        DayOfWork dst = new DayOfWork();
        dst.insertDate(src);

        assertEquals("Terça", dst.getDayOfWeek());
        assertEquals("09:00", dst.getHoraInicio());
        assertEquals("11:00", dst.getHoraFim());
    }

}
