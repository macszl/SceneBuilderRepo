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
	private ImageView winnerFraction;

	@FXML
	private ImageView winnerText;

	public AnchorPane getattackPane ()
	{
		return attackPane;
	}

	public ImageView getAttacked () {return attacked;}

	public ImageView getAttacker () {return attacker;}

	public ImageView getAnimation () {return animation;}

	public ImageView getWinnerText () {return winnerText;}

	public ImageView getWinnerFraction() {
		return winnerFraction;
	}

	public Button getQuitButton() {
		return quitButton;
	}
	@FXML
	void quitGame(ActionEvent event) {
		exit();
	}
}

