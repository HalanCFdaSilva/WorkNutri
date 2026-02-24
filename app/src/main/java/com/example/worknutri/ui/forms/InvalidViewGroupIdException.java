package com.example.worknutri.ui.forms;

public class InvalidViewGroupIdException extends IllegalArgumentException {
  public InvalidViewGroupIdException(int viewGroupId) {
    super("ViewGroup com id inválido: " + viewGroupId);
  }
}
