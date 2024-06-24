package com.example.javaquizzapp.FXcontroller;

import com.example.javaquizzapp.HttpService;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;

public class AdminGuiController {
    @FXML
    public Button start, TestScoreUsers, AddQuestion, Users;
    @FXML
    public Button Logout;
    @Setter
    private HttpService httpService;

    @FXML
    public void TestScoreUsersButton(){
        try {
            Stage stage = (Stage) TestScoreUsers.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TestScoreUsers.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void AddQuestionButton(){
        try {
            Stage stage = (Stage) AddQuestion.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AddQuestion.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void UsersButton(){
        try {
            Stage stage = (Stage) Users.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Users.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void startQuiz(javafx.event.ActionEvent actionEvent){
        try {
            Stage stage = (Stage) start.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/test.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();

            testController testController = fxmlLoader.getController();
            testController.resetQuiz();

        } catch (IOException e) {
            e.printStackTrace();
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
}