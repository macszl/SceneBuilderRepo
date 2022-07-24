package com.example.scenebuilderrepo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import static javafx.application.Platform.exit;


//class responsible for the attack pane appearing when an attack event happens
//(unit attacking another unit, unit attacking HQ)
public
class AttackController
{

	//gif with the attack animation
	@FXML
	private ImageView animation;

	@FXML
	private AnchorPane attackPane;
	//defender image
	@FXML
	private ImageView attacked;
	//attacker image
	@FXML
	private ImageView attacker;

	//button that exits the game
	@FXML
	private Button quitButton;

	//faction to be used when there's a win event
	//(Only 1 HQ standing)
	@FXML
	private ImageView winnerFaction;
	//decorative image
	//"VICTORY" in Polish
	@FXML
	private ImageView winnerText;

	//getters
	public AnchorPane getattackPane ()
	{
		return attackPane;
	}

	public ImageView getAttacked () {return attacked;}

	public ImageView getAttacker () {return attacker;}

	public ImageView getAnimation () {return animation;}

	public ImageView getWinnerText () {return winnerText;}

	public ImageView getWinnerFaction () {
		return winnerFaction;
	}

	public Button getQuitButton() {
		return quitButton;
	}

	//function to be used when user presses the quitButton
	@FXML
	void quitGame(ActionEvent event) {
		exit();
	}
}

