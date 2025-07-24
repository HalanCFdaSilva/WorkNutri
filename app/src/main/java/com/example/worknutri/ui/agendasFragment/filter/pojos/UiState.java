package com.example.worknutri.ui.agendasFragment.filter.pojos;

import java.io.Serializable;

public abstract class UiState implements Serializable {
    private boolean inOrder = true;
    private OrderFilterSelectedsBy OrderBy = OrderFilterSelectedsBy.NOME_ASC;


    public boolean isInOrder() {
        return inOrder;
    }

    public void setInOrder(boolean inOrder) {
        this.inOrder = inOrder;
    }

    public OrderFilterSelectedsBy getOrderBy() {
        return OrderBy;
    }

    public void setOrderBy(OrderFilterSelectedsBy orderBy) {
        OrderBy = orderBy;
    }


}
