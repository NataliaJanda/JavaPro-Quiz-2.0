package com.example.javaquizzapp.FXcontroller;

import com.example.javaquizzapp.HttpService;
import com.example.javaquizzapp.JavaQuizzAppApplication;
import com.example.javaquizzapp.entity.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import  javafx.scene.control.Button;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Optional;

public class startQuizController {
    @FXML
    public Button start;
    @FXML
    public Button Logout;
    @FXML
    public Button aboutQuiz;
    @FXML
    public Button YourScore;

    private Student currentStudent;
    @Setter
    private HttpService httpService;

    @FXML
    public void startQuiz(javafx.event.ActionEvent actionEvent){

        try {
            Stage stage = (Stage) start.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/test.fxml"));
            testController testController = fxmlLoader.getController();

            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();

            testController.resetQuiz();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void initialize() throws IOException {
        try {
            httpService = new HttpService();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        if (currentStudent != null) {
            System.out.println("Zalogowany student: " + currentStudent.getIndex());
            Optional<Student> studentOptional = Optional.ofNullable(httpService.findByIndex(currentStudent.getIndex()));
        }
    }
    @FXML
    public void LogoutButton(ActionEvent actionEvent) throws IOException {
        httpService.clearCurrentStudent();

        try {
            Stage stage = (Stage) Logout.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();

            LoginController loginController = fxmlLoader.getController();
            loginController.setHttpService(httpService);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void aboutQuizButton(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) aboutQuiz.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AboutQuiz.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();

            AboutQuizController aboutQuizController = fxmlLoader.getController();
            aboutQuizController.setHttpService(httpService);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void YourScoreButton(ActionEvent actionEvent) {

        try {
            Stage stage = (Stage) YourScore.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UserTestScore.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}