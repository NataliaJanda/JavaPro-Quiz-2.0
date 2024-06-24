package com.example.javaquizzapp.entity;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum Roles {
    ADMIN,
    STUDENT;

    public static ObservableList<Roles> getRoles() {
        return FXCollections.observableArrayList(Roles.values());
    }
}

