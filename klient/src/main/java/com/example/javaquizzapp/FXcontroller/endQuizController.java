package com.example.javaquizzapp.FXcontroller;

import com.example.javaquizzapp.HttpService;
import com.example.javaquizzapp.entity.Roles;
import com.example.javaquizzapp.entity.Student;
import com.example.javaquizzapp.entity.Test;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class endQuizController {
    public Button Back;
    public Label MaxScore;
    public Label YourScore;
    public Label Grade;

    public int currentQuestionIndex =8;
    public int countOfCorrectAnswers =10;
    public int shotNumber = 3;


    @Setter
    private HttpService httpService;

    @FXML
    private void initialize(){
        try {
            httpService = new HttpService();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        try {
            Grade.setText(String.valueOf(calculateGrade(countOfCorrectAnswers,currentQuestionIndex)));
            YourScore.setText(String.valueOf(countOfCorrectAnswers));
            MaxScore.setText(String.valueOf(currentQuestionIndex+1));

            String score = YourScore.getText();
            String max = MaxScore.getText();
            String grade = Grade.getText();
            int shot = shotNumber/2;

            Student currentStudent = httpService.getCurrentStudent();
            Test test = new Test(null,shot,score,max,grade,currentStudent);
            test.setStudent(currentStudent);
            httpService.saveTest(test);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void BackButton(ActionEvent actionEvent) throws IOException {
        Student currentStudent = httpService.getCurrentStudent();
        if (currentStudent.getRole() == Roles.STUDENT) {
            try {
                Stage stage = (Stage) Back.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/startQuiz.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
                stage.show();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                Stage stage = (Stage) Back.getScene().getWindow();
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
    }

    public double calculateGrade(int score, int maxScore){
        double percent = ((double) score /maxScore)*100;
        double grade;

        if(percent>=90) grade = 5;
        else if(percent>=85 ) grade =4.5;
        else if(percent>=75 ) grade =4;
        else if(percent>=65 ) grade=3.5;
        else if(percent>=60 ) grade=3;
        else grade=2;

        return grade;
    }
}