package com.example.scenebuilderrepo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import static javafx.application.Platform.exit;


public
class AttackController
{

	@FXML
	private ImageView animation;

	@FXML
	private AnchorPane attackPane;

	@FXML
	private ImageView attacked;

	@FXML
	private ImageView attacker;

	@FXML
	private Button quitButton;

	@FXML
	private ImageView winnerPane;

	@FXML
	private Text winnerText;

	AnchorPane getattackPane ()
	{
		return attackPane;
	}

	ImageView getAttacked () {return attacked;}

	ImageView getAttacker () {return attacker;}

	ImageView getAnimation () {return animation;}

	Text getWinnerText () {return winnerText;}


	public ImageView getWinnerPane() {
		return winnerPane;
	}

	public Button getQuitButton() {
		return quitButton;
	}
	@FXML
	void quitGame(ActionEvent event) {
		exit();
	}
}

