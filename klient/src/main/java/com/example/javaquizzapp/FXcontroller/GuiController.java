package com.example.javaquizzapp.FXcontroller;

import com.example.javaquizzapp.HttpService;
import com.example.javaquizzapp.entity.Roles;
import com.example.javaquizzapp.entity.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;
import java.net.MalformedURLException;

import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class GuiController {
    @FXML
    public PasswordField passwordField;
    @FXML
    public PasswordField repeatPasswordField;
    @FXML
    public TextField lastNameField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField indexField;
    @FXML
    public Button Register, RegisterPanel, LoginPanel;

    public testController testController;

    @Setter
    private HttpService httpService;

    @FXML
    public void RegisterSubmit(javafx.event.ActionEvent actionEvent) throws IOException {
        String index = indexField.getText();
        String password = passwordField.getText();
        String repeatPassword = repeatPasswordField.getText();
        String name = nameField.getText();
        String lastName = lastNameField.getText();
        Roles roles = Roles.STUDENT;

        httpService = new HttpService();

        if (!password.equals(repeatPassword)) {
            System.out.println("Passwords do not match!");
            return;
        }

        Student student = new Student(null, index, name, lastName, password, roles);
        try {
            httpService.registerStudent(student);
            System.out.println("Student registered successfully!");

            Stage stage = (Stage) Register.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/startQuiz.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            System.out.println("Failed to register student: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    public void RegisterButtonPanel(javafx.event.ActionEvent actionEvent){
        try {
            httpService = new HttpService();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        try {
            Stage stage = (Stage) Register.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();

            GuiController guiController = fxmlLoader.getController();
            guiController.setHttpService(httpService);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void LoginButtonPanel(javafx.event.ActionEvent actionEvent){
        try {
            httpService = new HttpService();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        try {
            Stage stage = (Stage) Register.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();

            LoginController loginController = fxmlLoader.getController();
            loginController.setHttpService(httpService);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}