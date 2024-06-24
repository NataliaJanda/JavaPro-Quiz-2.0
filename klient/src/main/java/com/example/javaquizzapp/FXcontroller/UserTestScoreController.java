package com.example.javaquizzapp.FXcontroller;

import com.example.javaquizzapp.HttpService;
import com.example.javaquizzapp.entity.Student;
import com.example.javaquizzapp.entity.Test;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public class UserTestScoreController {
    @FXML
    public Button Back;
    @FXML
    public TableColumn<Test,String> test_id;
    @FXML
    public TableColumn<Test,String> YourScore;
    @FXML
    public TableColumn<Test,String> MaxScore;
    @FXML
    public TableColumn<Test,String> Grade;

    @Setter
    private HttpService httpService;

    @FXML
    private TableView<Test> UserScoreTable;
    @FXML
    public void initialize() throws IOException {
        try {
            httpService = new HttpService();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        test_id.setCellValueFactory(new PropertyValueFactory<>("studentIndex"));
        YourScore.setCellValueFactory(new PropertyValueFactory<>("score"));
        MaxScore.setCellValueFactory(new PropertyValueFactory<>("maxScore"));
        Grade.setCellValueFactory(new PropertyValueFactory<>("grade"));

        Student currentStudent = httpService.getCurrentStudent();

        List<Test> test = httpService.findTestsByStudent(currentStudent.getId());
        ObservableList<Test> testObservableList = FXCollections.observableArrayList(test);
        UserScoreTable.setItems(testObservableList);
    }

    @FXML
    public void BackButton(ActionEvent actionEvent) {
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
}