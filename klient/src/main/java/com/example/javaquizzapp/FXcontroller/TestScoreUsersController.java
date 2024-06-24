package com.example.javaquizzapp.FXcontroller;

import com.example.javaquizzapp.HttpService;
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

public class TestScoreUsersController {
    @FXML
    public Button Back;
    @FXML
    public TableColumn<Test,String> index;
    @FXML
    public TableColumn<Test,String> score;
    @FXML
    public TableColumn<Test,String> maxScore;
    @FXML
    public TableColumn<Test,String> grade;
    @Setter
    private HttpService httpService;

    @FXML
    private TableView<Test> testTable;
    @FXML
    public void initialize() throws IOException {
        try {
            httpService = new HttpService();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        index.setCellValueFactory(new PropertyValueFactory<>("studentIndex"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        maxScore.setCellValueFactory(new PropertyValueFactory<>("maxScore"));
        grade.setCellValueFactory(new PropertyValueFactory<>("grade"));

        List<Test> test = httpService.findAllTests();
        ObservableList<Test> testObservableList = FXCollections.observableArrayList(test);
        testTable.setItems(testObservableList);
    }
    @FXML
    public void BackButton(ActionEvent actionEvent) {
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