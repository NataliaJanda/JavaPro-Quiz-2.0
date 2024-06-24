package com.example.javaquizzapp.FXcontroller;

import com.example.javaquizzapp.HttpService;
import com.example.javaquizzapp.entity.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Setter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;

public class testController implements Initializable {
    @FXML
    public Label LabelQuestionNumber, MaxScore, YourScore, Grade,Question, Time;
    @FXML
    public CheckBox Answer1, Answer2, Answer3, Answer4;
    @FXML
    public Button Back, Next;
    @FXML
    private List<Question> questions;
    @FXML
    private int currentQuestionIndex = 0;
    @FXML
    public int countOfCorrectAnswers = 0;
    public double grade=0;
    private Timeline timer;
    private long remainingTime;
    public int shotNumber = 0;
    private Student currentStudent;
    public int index;
    @FXML
    public ImageView questionImageView;
    @Setter
    HttpService httpService;

    @FXML
    public void resetQuiz() throws IOException {
        currentQuestionIndex = 0;
        countOfCorrectAnswers = 0;
        grade=0;

        questions = httpService.getRandomQuestions(10);
        if (!questions.isEmpty()) {
            displayQuestion(questions.get(currentQuestionIndex));
        }
        startTimer();
    }

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        try {
            httpService = new HttpService();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        shotNumber ++;
        System.out.println(shotNumber/2);
        try {
            questions = httpService.getRandomQuestions(10);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!questions.isEmpty()) {
            displayQuestion(questions.get(currentQuestionIndex));
        }
        startTimer();
        try {
            currentStudent = httpService.getCurrentStudent();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (currentStudent != null) {
            System.out.println("Zalogowany student w testController: " + index);
        } else {
            System.out.println("Brak zalogowanego studenta w testController");
        }
    }

    @FXML
    private void startTimer() {
        if (timer != null) {
            timer.stop();
        }
        remainingTime = 10 * 60;
        timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (remainingTime <= 0) {
                timer.stop();
                //TODO: rezultaty
            } else {
                remainingTime--;
                long m = remainingTime / 60;
                long s = remainingTime % 60;
                Time.setText(String.format("%02d:%02d", m, s));
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.playFromStart();
    }

    @FXML
    public void BackButton(javafx.event.ActionEvent actionEvent) throws IOException {
        Student currentStudent = httpService.getCurrentStudent();
        if (currentStudent.getRole() == Roles.STUDENT) {
            try {
                if (timer != null) {
                    timer.stop();
                }
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
                if (timer != null) {
                    timer.stop();
                }
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


    @FXML
    public void NextButton(javafx.event.ActionEvent actionEvent) throws IOException {
        checkAnswers(questions.get(currentQuestionIndex));
        if (currentQuestionIndex < questions.size() - 1) {
            currentQuestionIndex++;
            displayQuestion(questions.get(currentQuestionIndex));
        }
        else{
            calculateGrade(countOfCorrectAnswers,currentQuestionIndex);

            Stage stage = (Stage) Next.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/endQuiz.fxml"));

            endQuizController endQuizController = new endQuizController();
            endQuizController.currentQuestionIndex = currentQuestionIndex;
            endQuizController.countOfCorrectAnswers = countOfCorrectAnswers;
            endQuizController.shotNumber = shotNumber;
            fxmlLoader.setController(endQuizController);

            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();}

    }

    @FXML
    private void displayQuestion(Question question) {
        LabelQuestionNumber.setText(String.valueOf(currentQuestionIndex + 1));
        Question.setText(question.getQuestion());

        List<Answer> answers = question.getAnswers();
        if (!answers.isEmpty()) {
            Answer1.setText(answers.get(0).getAnswer());
            if (answers.size() > 1) Answer2.setText(answers.get(1).getAnswer());
            if (answers.size() > 2) Answer3.setText(answers.get(2).getAnswer());
            if (answers.size() > 3) Answer4.setText(answers.get(3).getAnswer());
        } else {
            Answer1.setText("");
            Answer2.setText("");
            Answer3.setText("");
            Answer4.setText("");
        }

        if (question.getImageData() != null && !question.getImageData().isEmpty()) {
            byte[] imageBytes = Base64.getDecoder().decode(question.getImageData());
            Image image = new Image(new ByteArrayInputStream(imageBytes));
            questionImageView.setImage(image);
            questionImageView.setVisible(true);
        } else {
            questionImageView.setImage(null);
            questionImageView.setVisible(false);
        }
        Answer1.setSelected(false);
        Answer2.setSelected(false);
        Answer3.setSelected(false);
        Answer4.setSelected(false);
    }
    @FXML
    public void checkAnswers(Question question){
        List<Answer> answers = question.getAnswers();

        boolean correct=true;
        if (!answers.isEmpty()) correct &= (answers.get(0).isCorrect() == Answer1.isSelected());
        if (answers.size() > 1) correct &= (answers.get(1).isCorrect() == Answer2.isSelected());
        if (answers.size() > 2) correct &= (answers.get(2).isCorrect() == Answer3.isSelected());
        if (answers.size() > 3) correct &= (answers.get(3).isCorrect() == Answer4.isSelected());

        if(correct){
            countOfCorrectAnswers++;
        }
    }

    @FXML
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