package com.example.worknutri.ui.forms;

import org.junit.Test;

import static org.junit.Assert.*;

public class InvalidViewGroupIdExceptionTest {

  @Test
  public void messageContainsIdAndText() {
    int id = 123;
    InvalidViewGroupIdException ex = new InvalidViewGroupIdException(id);
    String msg = ex.getMessage();

    assertNotNull("Exception message should not be null", msg);
    assertTrue("Message should contain the id", msg.contains(String.valueOf(id)));
    // message in the exception is Portuguese: "inválido"
    assertTrue("Message should indicate invalid id", msg.contains("inválido"));
  }

  @Test
  public void isInstanceOfIllegalArgumentException() {
    InvalidViewGroupIdException ex = new InvalidViewGroupIdException(-1);
    assertTrue("Should be an IllegalArgumentException", ex instanceof IllegalArgumentException);
  }
}

