package com.home.assignment.userapi.util;

public enum Operation {
    CREATE("Create"),
    DELETE("Delete"),
    UPDATE("Update");

    private final String typeOfOperation;

    Operation(String typeOfOperation) {
        this.typeOfOperation = typeOfOperation;
    }

    public String getTypeOfOperation() {
        return typeOfOperation;
    }
}
