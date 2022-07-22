package com.example.scenebuilderrepo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//class responsible for the faction select menu
public
class PlayerSelectController implements Initializable
{
	//this class includes a race select pane
	RaceSelectController controller;
	//background
	@FXML
	private ImageView backgroundImage;
	Image bg = new Image(new File("playerSelectBG.png")
								 .toURI()
								 .toString());

	boolean loaded = false;
	private Stage stage;

	//buttons
	@FXML
	private Button three;
	@FXML
	private Button two;
	@FXML
	private Button cont;
	//button images
	@FXML
	private ImageView buttonImage1;
	@FXML
	private ImageView buttonImage2;
	@FXML
	private ImageView buttonImage3;
	//button state images
	Image buttonImage = new Image(new File("BUTTON.png")
										  .toURI()
										  .toString());
	Image clickbuttonImage = new Image(new File("BUTTONCLICK.png")
											   .toURI()
											   .toString());
	Image greybuttonImage = new Image(new File("BUTTONGREY.png")
											  .toURI()
											  .toString());
	//greying out and disabling the continue button
	void enableCnt ()
	{
		buttonImage3.setImage(buttonImage);
		cont.setDisable(false);
	}

	void disableCnt ()
	{
		buttonImage3.setImage(greybuttonImage);
		cont.setDisable(true);
	}

	@FXML
	private AnchorPane raceSelect;

	//loading the 3 player version of the race select pane
	@FXML
	void setThreePlayer (ActionEvent event) throws IOException
	{

		GameInfo.playerAmount = 3;
		if ( !loaded )
		{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("raceselect.fxml"));
			AnchorPane pane = fxmlLoader.load();
			raceSelect
					.getChildren()
					.clear();
			raceSelect
					.getChildren()
					.add(pane);
			controller = fxmlLoader.getController();
			controller.controller = this;
		}
		controller.imgp1.setImage(controller.imgs.get(0));
		controller.imgp2.setImage(controller.imgs.get(1));
		controller.imgp3.setImage(controller.imgs.get(2));
		controller.rightArrowImage1.setImage(controller.arrowImage);
		controller.rightArrowImage2.setImage(controller.arrowImage);
		controller.leftArrowImage1.setImage(controller.arrowImage);
		controller.leftArrowImage2.setImage(controller.arrowImage);
		controller.enableP3();
	}
	//loading the 2 player version of the race select pane
	@FXML
	void setTwoPlayer (ActionEvent event) throws IOException
	{
		GameInfo.playerAmount = 2;
		if ( !loaded )
		{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("raceselect.fxml"));
			AnchorPane pane = fxmlLoader.load();
			raceSelect
					.getChildren()
					.clear();
			raceSelect
					.getChildren()
					.add(pane);
			controller = fxmlLoader.getController();
			controller.controller = this;
		}
		controller.imgp1.setImage(controller.imgs.get(0));
		controller.imgp2.setImage(controller.imgs.get(1));
		controller.rightArrowImage1.setImage(controller.arrowImage);
		controller.rightArrowImage2.setImage(controller.arrowImage);
		controller.rightArrowImage3.setImage(controller.arrowImage);
		controller.leftArrowImage1.setImage(controller.arrowImage);
		controller.leftArrowImage2.setImage(controller.arrowImage);
		controller.leftArrowImage3.setImage(controller.arrowImage);
		controller.disableP3();
	}

	//function that initializes and launches game.fxml
	@FXML
	void startGame (ActionEvent event) throws IOException
	{
		stage = (Stage) ((Node) event.getSource())
				.getScene()
				.getWindow();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("game.fxml"));
		Scene scene = new Scene(fxmlLoader.load(),
								GameInfo.x,
								GameInfo.y);
		stage.setScene(scene);
		stage.show();
	}

	//initialization function, responsible for loading all the images, buttons and backgrounds
	@Override
	public
	void initialize (URL url, ResourceBundle resourceBundle)
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("raceselect.fxml"));
		AnchorPane pane = null;
		try
		{
			pane = fxmlLoader.load();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		raceSelect
				.getChildren()
				.clear();
		raceSelect
				.getChildren()
				.add(pane);
		controller = fxmlLoader.getController();
		controller.controller = this;
		cont.setDisable(true);
		backgroundImage.setImage(bg);
		buttonImage1.setImage(buttonImage);
		buttonImage2.setImage(buttonImage);
		buttonImage3.setImage(greybuttonImage);
	}
}
