package com.example.javaquizzapp;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaQuizzAppApplication extends Application {

    public static void main(String[] args) {
		launch(args);
	}

	@Override
	@FXML
	public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui.fxml"));
		Parent root = fxmlLoader.load();


		stage.setTitle("Quizz");
		Scene scene = new Scene(root, 900, 600);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	@FXML
	public void init() throws Exception {
	}

	@Override
	@FXML
	public void stop() throws Exception {
	}
}