package com.example.scenebuilderrepo;

import javafx.animation.Animation;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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

	ImageView attacked;
	ImageView attacker;
	ImageView attackAnimation;
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
	@FXML
	private Text turnIndicator;
	@FXML
	private Text turnCounter;

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
		attacker=atk.getAttacker();
		attacked=atk.getAttacked();
		atkPane = atk.getattackPane();
		attackAnimation=atk.getAnimation();


		GroupFactory groupFactory = new GroupFactory();
		Group group = groupFactory.getGroup(this);

		unitPortrait.setFitWidth(unitPortrait.getFitWidth());
		unitPortrait.setFitHeight(unitPortrait.getFitHeight());


		setBoard(group);
		setTurnDisplay(GameInfo.playerFactions.get(GameInfo.currentPlayerCounter).pl);
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
	void setUnitPortraitAndDesc (MapTile unit)
	{
		unitPortrait.setImage(unit.obj.portriat);

		if(unit.getTerrainAtk() > 0)
			unitStatsATK.setText("ATK " + (unit.obj.atk-1)+"-"+(unit.obj.atk+1)+"(+"+unit.getTerrainAtk()+")");
		else
			unitStatsATK.setText("ATK " + (unit.obj.atk-1)+"-"+(unit.obj.atk+1));

		if(unit.getTerrainDef() > 0)
			unitStatsDEF.setText("DEF " +(unit.obj.def-1)+"-"+(unit.obj.def+1)+"(+"+unit.getTerrainDef()+")");
		else
			unitStatsDEF.setText("DEF " + (unit.obj.atk-1)+"-"+(unit.obj.atk+1));

		unitStatsHP.setText("HP " + unit.obj.getHp_current()+ "/" + unit.obj.getHp_max());
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
								   if( mouseEvent.getY() - mouseAnchorY > 15
									   || mouseEvent.getY() - mouseAnchorY < -15 )
								   {
									   if( mouseEvent.getSceneY() - mouseAnchorY < 0 && GameInfo.y == 900 )
									   {
										   node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
									   }
									   else if( mouseEvent.getSceneY() - mouseAnchorY < 125 && GameInfo.y == 1080 )
									   {
										   node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
									   }
								   }
								   if( mouseEvent.getX() - mouseAnchorX > 15
									   || mouseEvent.getX() - mouseAnchorX < -15 )
								   {
									   if( mouseEvent.getSceneX() - mouseAnchorX > 0 )
									   {
										   node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
									   }
								   }

							   });
	}


	private void setTurnDisplay(Player currentPlayer) {
		turnCounter.setText("Tura "+(GameInfo.turn+1)+": ");
		Color c;
		if(currentPlayer.faction.id==FactionEnum.SKYMEN) {
			c = Color.web("rgb(72,192,255)");
			turnIndicator.setText("  Ptakoludzie");
		}
		else if (currentPlayer.faction.id==FactionEnum.FORESTMEN)
		{
			c = Color.web("rgb(130,102,81)");
			turnIndicator.setText("  Drzewoludzie");
		}
		else
		{
			c = Color.web("rgb(161,112,192)");
			turnIndicator.setText("  Kryształoludzie");
		}
		turnIndicator.setFill(c);
	}

	public
	void endTurn () throws IOException, InterruptedException
	{

		System.out.println("Before click: Turn " + GameInfo.turn + " player: " + GameInfo.currentPlayerCounter);
		GameInfo.regenerateAP();
		Player currentPlayer = GameInfo.playerFactions.get(GameInfo.currentPlayerCounter).pl;
		currentPlayer.setIncome();
		currentPlayer.gold += currentPlayer.income;
		if( GameInfo.currentPlayerCounter < GameInfo.playerAmount - 1 )
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

		setTurnDisplay(GameInfo.playerFactions.get(GameInfo.currentPlayerCounter).pl);
		//make end turn write and save to disk
		SaveBuilder saveBuilder = new SaveBuilder("save.xml");
		saveBuilder.saveGameToXML();
	}

	public
	void recruitUnit ()
	{
		Player currentPlayer = GameInfo.playerFactions.get(GameInfo.currentPlayerCounter).pl;

		if( Board.selectedTile != null &&
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
	void doAttack (MapTile selectedTile, MapTile destinationTile)
	{
		if( !GameInfo.skipAtk )
		{
			setAttack(selectedTile,destinationTile);
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

	void setAttack (MapTile selectedTile, MapTile destinationTile)
	{
		attacker.setImage(selectedTile.obj.attacker);
		attacked.setImage(destinationTile.obj.portriat);
		atkPane.getChildren().remove(attackAnimation);
		atkPane.getChildren().add(attackAnimation);
		attackAnimation.setImage(selectedTile.obj.attackAnimation);

	}
}