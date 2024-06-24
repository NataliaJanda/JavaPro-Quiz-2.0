package com.example.javaquizzapp.FXcontroller;

import com.example.javaquizzapp.HttpService;
import com.example.javaquizzapp.entity.Student;
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

public class UsersController {

    @FXML
    public Button Back;
    @FXML
    public TableColumn<Student, String> name;
    @FXML
    public TableColumn<Student, String> lastName;
    @FXML
    public TableColumn<Student, String> index;
    @FXML
    private TableView<Student> studentTable;
    @Setter
    private HttpService httpService;


    @FXML
    public void initialize() throws IOException {
        try {
            httpService = new HttpService();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        index.setCellValueFactory(new PropertyValueFactory<>("index"));


        List<Student> students = httpService.findAllStudents();
        ObservableList<Student> studentObservableList = FXCollections.observableArrayList(students);
        studentTable.setItems(studentObservableList);
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