package com.example.javaquizzapp.FXcontroller;

import com.example.javaquizzapp.HttpService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;

public class AboutQuizController {
    @Setter
    HttpService httpService;

    @FXML
    public Button Back;
    @FXML
    public void BackButton(ActionEvent actionEvent) {

        try {
            Stage stage = (Stage) Back.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/startQuiz.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();

            startQuizController startQuizController = fxmlLoader.getController();
            startQuizController.setHttpService(httpService);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
