package com.example.javaquizzapp.FXcontroller;

import com.example.javaquizzapp.HttpService;
import com.example.javaquizzapp.JavaQuizzAppApplication;
import com.example.javaquizzapp.entity.Roles;
import com.example.javaquizzapp.entity.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;


public class LoginController {
    @FXML
    public Button RegisterF, LoginF, Login;
    @FXML
    public TextField indexLoginField;
    @FXML
    public PasswordField passwordLoginField;
    @FXML
    public Label errorMessage;
    @Setter
    private HttpService httpService;
    @FXML
    FXMLLoader fxmlLoader;

    @FXML
    public void LoginSubmit() throws IOException {
        String index = indexLoginField.getText();
        String password = passwordLoginField.getText();
        if (httpService.validateStudent(index, password)) {
            Student currentStudent = httpService.getCurrentStudent();
            if (currentStudent.getRole() == Roles.STUDENT) {
                try {
                    Stage stage = (Stage) Login.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/startQuiz.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setScene(scene);
                    stage.show();
                    startQuizController startQuizController = fxmlLoader.getController();
                    startQuizController.setHttpService(httpService);
                } catch (IOException e) {
                    e.printStackTrace();
                    errorMessage.setText("Wystąpił błąd podczas ładowania nowego widoku.");
                }

            }
            else {
                try {
                    Stage stage = (Stage) Login.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AdminGui.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setScene(scene);
                    stage.show();
                    AdminGuiController adminGuiController = fxmlLoader.getController();
                    adminGuiController.setHttpService(httpService);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } else {
            errorMessage.setText("Niepoprawny index lub hasło");
        }
    }

    @FXML
    public void RegisterButtonF () {
        try {
            Stage stage = (Stage) Login.getScene().getWindow();
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
    public void LoginButtonF () {
        try {
            Stage stage = (Stage) Login.getScene().getWindow();
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