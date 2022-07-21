package com.example.scenebuilderrepo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public
class MainMenuApplication extends Application
{

	public static
	void main (String[] args)
	{
		launch();
	}

	//Function that launches the application in the main menu screen.
	@Override
	public
	void start (Stage stage) throws IOException
	{
		FXMLLoader fxmlLoader = new FXMLLoader(MainMenuApplication.class.getResource("mainmenu.fxml"));
		Scene scene = new Scene(fxmlLoader.load(),
								GameInfo.x,
								GameInfo.y);
		stage.setTitle("Wojny meteorytów");

		stage.setScene(scene);
		stage.show();
	}
}
