package com.example.worknutri.ui.formularios;

public class InvalidViewGroupIdException extends IllegalArgumentException {
  public InvalidViewGroupIdException(int viewGroupId) {
    super("ViewGroup com id inválido: " + viewGroupId);
  }
}
