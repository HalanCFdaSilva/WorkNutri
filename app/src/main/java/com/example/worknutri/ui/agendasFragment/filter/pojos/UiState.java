package com.example.worknutri.ui.agendasFragment.filter.pojos;

import java.io.Serializable;

public abstract class UiState implements Serializable {
    private boolean inOrder = true;


    public boolean isInOrder() {
        return inOrder;
    }

    public void setInOrder(boolean inOrder) {
        this.inOrder = inOrder;
    }


}
