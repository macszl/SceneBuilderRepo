package com.example.scenebuilderrepo;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public
class GameController implements Initializable
{


	AnchorPane atkPane;
	ImageView cover = new ImageView(new Image(new File("Cover.png")
													  .toURI()
													  .toString()));
	@FXML
	private CheckMenuItem skipAtkButton;
	@FXML
	private StackPane coverPane;
	@FXML
	private Label factionGold;
	@FXML
	private Button recruitmentButton;
	@FXML
	private Button turnEndButton;
	@FXML
	private Label unitStatsATK;
	@FXML
	private Label unitStatsDEF;
	@FXML
	private Label unitStatsDesc;
	@FXML
	private Label unitStatsHP;
	@FXML
	private AnchorPane mapAnchor;
	@FXML
	private ImageView unitPortrait;
	private double mouseAnchorX;
	private double mouseAnchorY;

	@Override
	public
	void initialize (URL url, ResourceBundle resourceBundle)
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("attackwindows.fxml"));
		try
		{
			fxmlLoader.load();
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		AttackController atk = fxmlLoader.getController();
		atkPane = atk.getattackPane();


		GroupFactory groupFactory = new GroupFactory();
		Group group = groupFactory.getGroup(this);

		unitPortrait.setFitWidth(unitPortrait.getFitWidth());
		unitPortrait.setFitHeight(unitPortrait.getFitHeight());


		setBoard(group);
	}

	@FXML
	public
	void setFactionGold (Player x)
	{
		factionGold.setText("Ilosc zlota " + Math.round(x.gold) + "(+" + x.income + ")");
	}

	@FXML
	void animationToggle (ActionEvent event)
	{
		GameInfo.skipAtk = skipAtkButton.isSelected();
	}

	@FXML
	public
	void setUnitPortraitAndDesc (MapObject unit)
	{
		unitPortrait.setImage(unit.portriat);
		unitStatsATK.setText("ATK " + unit.atk);
		unitStatsDEF.setText("DEF " + unit.def);
		unitStatsHP.setText("HP " + unit.getHp_current() + "/" + unit.getHp_max());
	}

	public
	void setBoard (Group board)
	{
		makeDraggable(board);
		mapAnchor
				.getChildren()
				.add(board);
	}

	public
	void makeDraggable (Node node)
	{

		node.setOnMousePressed(mouseEvent ->
							   {
								   mouseAnchorX = mouseEvent.getX();
								   mouseAnchorY = mouseEvent.getY();
							   });

		node.setOnMouseDragged(mouseEvent ->
							   {
								   if ( mouseEvent.getY() - mouseAnchorY > 15
										|| mouseEvent.getY() - mouseAnchorY < -15 )
								   {
									   if ( mouseEvent.getSceneY() - mouseAnchorY < 0 && GameInfo.y == 900 )
									   {
										   node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
									   }
									   else if ( mouseEvent.getSceneY() - mouseAnchorY < 125 && GameInfo.y == 1080 )
									   {
										   node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
									   }
								   }
								   if ( mouseEvent.getX() - mouseAnchorX > 15
										|| mouseEvent.getX() - mouseAnchorX < -15 )
								   {
									   if ( mouseEvent.getSceneX() - mouseAnchorX > 0 )
									   {
										   node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
									   }
								   }

							   });
	}


	public
	void endTurn () throws IOException, InterruptedException
	{

		System.out.println("Before click: Turn " + GameInfo.turn + " player: " + GameInfo.currentPlayerCounter);
		GameInfo.regenerateAP();
		Player currentPlayer = GameInfo.playerFactions.get(GameInfo.currentPlayerCounter).pl;
		currentPlayer.setIncome();
		currentPlayer.gold += currentPlayer.income;

		if ( GameInfo.currentPlayerCounter < GameInfo.playerAmount - 1 )
		{
			GameInfo.currentPlayerCounter += 1;
		}
		else
		{
			GameInfo.turn += 1;
			GameInfo.currentPlayerCounter = 0;
		}
		currentPlayer = GameInfo.playerFactions.get(GameInfo.currentPlayerCounter).pl;
		setFactionGold(currentPlayer);
		Board.unClickAll();
		System.out.println("After click: Turn " + GameInfo.turn + " player: " + GameInfo.currentPlayerCounter);

		//make end turn write and save to disk
		SaveBuilder saveBuilder = new SaveBuilder("save.xml");
		saveBuilder.saveGameToXML();
	}

	public
	void recruitUnit ()
	{
		Player currentPlayer = GameInfo.playerFactions.get(GameInfo.currentPlayerCounter).pl;

		if ( Board.selectedTile != null &&
			 Board.selectedTile.obj == null &&
			 currentPlayer.gold >= 5 )

		{
			int x = Board.selectedTile.hex.x;
			int y = Board.selectedTile.hex.y;

			int idx = GameInfo.currentPlayerCounter;
			Faction currentFaction = GameInfo.playerFactions.get(idx);
			Board.addUnit(currentFaction,
						  x,
						  y);
			currentPlayer.gold -= 5;
			setFactionGold(currentPlayer);
		}
	}

	public
	void doAttack ()
	{
		if ( !GameInfo.skipAtk )
		{
			showAttack();
			PauseTransition p = new PauseTransition(Duration.millis(1000));
			p.setOnFinished(e -> hideAttack());
			p.play();
		}

	}

	@FXML
	public
	void hideAttack ()
	{
		coverPane
				.getChildren()
				.remove(cover);
		coverPane
				.getChildren()
				.remove(atkPane);
	}

	@FXML
	public
	void showAttack ()
	{
		cover.setFitWidth(coverPane.getWidth());
		cover.setFitHeight(coverPane.getHeight());
		coverPane
				.getChildren()
				.add(cover);
		coverPane
				.getChildren()
				.add(atkPane);

	}
}