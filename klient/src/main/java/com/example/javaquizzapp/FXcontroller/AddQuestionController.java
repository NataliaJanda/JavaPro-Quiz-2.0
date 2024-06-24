package com.example.javaquizzapp.FXcontroller;

import com.example.javaquizzapp.HttpService;
import com.example.javaquizzapp.entity.Answer;
import com.example.javaquizzapp.entity.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Setter;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;

public class AddQuestionController implements Initializable {
    public Button Back;
    public TextField answer1Field,answer2Field,answer3Field,answer4Field;
    public ChoiceBox<String> ChoiceBoxAns2,ChoiceBoxAns1,ChoiceBoxAns3,ChoiceBoxAns4;
    public Label InfoLabel;

    public Button selectImageButton;
    private FileChooser imageFileChooser;
    private File selectedImageFile;
    private final String[] PF = {"prawda","fałsz"};
    public TextArea questionField;
    public Label imagePathLabel;

    @Setter
    private HttpService httpService;

    public Button AddingQuestion;
    @FXML
    public void initialize(URL arg0, ResourceBundle arg1){
        try {
            httpService = new HttpService();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        ChoiceBoxAns1.getItems().addAll(PF);
        ChoiceBoxAns2.getItems().addAll(PF);
        ChoiceBoxAns3.getItems().addAll(PF);
        ChoiceBoxAns4.getItems().addAll(PF);
        imageFileChooser = new FileChooser();
    }
    public void selectImageFile(ActionEvent actionEvent) {
        selectedImageFile = imageFileChooser.showOpenDialog(null);
        if (selectedImageFile != null) {
            imagePathLabel.setText(selectedImageFile.getName());
        } else {
            imagePathLabel.setText("");
        }
    }
    @FXML
    private void AddingQuestionButton() {
        try {
            String questionText = questionField.getText();
            List<Answer> answers = new ArrayList<>();
            Question question = new Question(null, questionText);

            answers.add(new Answer(null, answer1Field.getText(), ChoiceBoxAns1.getValue().equals("prawda"), null));
            answers.add(new Answer(null, answer2Field.getText(), ChoiceBoxAns2.getValue().equals("prawda"), null));
            answers.add(new Answer(null, answer3Field.getText(), ChoiceBoxAns3.getValue().equals("prawda"), null));
            answers.add(new Answer(null, answer4Field.getText(), ChoiceBoxAns4.getValue().equals("prawda"), null));

            question.setAnswers(answers);

            if (selectedImageFile != null) {
                String base64Image = encodeFileToBase64(selectedImageFile);
                question.setImageData(base64Image);
            }

            httpService.addQuestion(question);
            InfoLabel.setText("Pytanie zostało dodane pomyślnie!");
        } catch (Exception e) {
            InfoLabel.setText("Błąd podczas dodawania pytania: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void selectImageFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        selectedImageFile = fileChooser.showOpenDialog(selectImageButton.getScene().getWindow());

        if (selectedImageFile != null) {
            imagePathLabel.setText(selectedImageFile.getAbsolutePath());
        }
    }

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
    private String encodeFileToBase64(File file) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] fileBytes = IOUtils.toByteArray(fileInputStream);
            return Base64.getEncoder().encodeToString(fileBytes);
        }
    }

}