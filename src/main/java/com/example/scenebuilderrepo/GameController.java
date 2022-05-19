package com.example.scenebuilderrepo;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class GameController implements Initializable {
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


    Image Neutral = new Image(new File("hexagon.png").toURI().toString());

    Image city = new Image(new File("BIGCITY_HEX.png").toURI().toString());
    Image village = new Image(new File("VILLAGE_HEX.png").toURI().toString());
    Image forest = new Image(new File("FOREST_HEX.png").toURI().toString());
    Image forest2 = new Image(new File("FOREST2_HEX.png").toURI().toString());
    Image mountain =  new Image(new File("MOUNTAIN_HEX.png").toURI().toString());
    Image hills =  new Image(new File("HILLS_HEX.png").toURI().toString());
    Image desert =  new Image(new File("DESERT_HEX.png").toURI().toString());
    Image desertPond =  new Image(new File("RADIOACTIVE_POND_HEX_WIATRACZEK.png").toURI().toString());
    Image pondst = new Image(new File("POND_RIVEREND_UP_HEX.png").toURI().toString());
    Image ponden = new Image(new File("POND_RIVEREND_DOWN_HEX.png").toURI().toString());
    Image riverfl = new Image(new File("RIVER_WITHFLOW_HEX.png").toURI().toString());
    Image riverfl_right = new Image(new File("RIVER_WITHFLOW_HEX_LEFT_TO_RIGHT.png").toURI().toString());

    Faction crystalmenFaction = null;
    Faction treemenFaction = null;
    Faction skymenFaction = null;

    AnchorPane atkPane;
    ImageView cover=new ImageView(new Image(new File("Cover.png").toURI().toString()));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("attackwindows.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AttackController atk = fxmlLoader.getController();
        atkPane = atk.getattackPane();

        Group group = new Group();
        Board board = new Board();
        Image im;

        HexImages rings = new HexImages("hexagon1.png" , "hexagon2.png", "hexagon3.png");
        HexImages bases = new HexImages("hexagon_blue.png" , "hexagon_brown.png", "hexagon_purple.png");

        unitPortrait.setFitWidth(unitPortrait.getFitWidth());
        unitPortrait.setFitHeight(unitPortrait.getFitHeight());

        Faction neutral=new Faction(FactionEnum.NO_FACTION,Neutral);
        Player None= new Player(neutral);

        setPlayersGameInfo();

        getP1GameInfo();
        getP2GameInfo();
        if(GameInfo.playerAmount ==3) {
            getP3GameInfo();
        }

        for(int i=0;i< MapConstants.MAP_LENGTH;i++)
        {
            board.addColumn();
            for(int j=0;j< MapConstants.MAP_HEIGHT;j++)
            {
                Hexagon hex;

                hex = new Hexagon(i, j, None,this);
                im =  new Image(new File("hexagon.png").toURI().toString());
                MapTile container = new MapTile(rings,bases);

                setHexAttributes(im, i, hex);

                addHexToContainer(hex, container);

                setTerrainBase(  hex, i, j);
                setTerrainRivers(hex, i, j);
                setTerrainCities(hex, i, j);
                setTerrainFactionCrystal(im, crystalmenFaction, i, j, hex, container);
                setTerrainFactionTree(im, treemenFaction, i, j, hex, container);
                setTerrainFactionSky(im, skymenFaction, i, j, hex, container);

                setContainerLayoutAttributes(i, j, container);

                board.addMapTile(container, i);
                group.getChildren().add(container);
            }
        }
        setBoard(group);
        factionGold.setText("Ilosc zlota "+5);
    }

    private void setContainerLayoutAttributes(int i, int j, MapTile container) {
        if(i %2==0)
            container.setLayoutY((GameInfo.hexsize-5)* j);
        else
            container.setLayoutY((GameInfo.hexsize-5)* j +((GameInfo.hexsize-5)/2));

        container.setLayoutX((GameInfo.hexsize-10)* i);
    }

    private void addHexToContainer(Hexagon hex, MapTile container) {
        container.getChildren().add(hex);
        container.hex= hex;
    }

    private void setHexAttributes(Image im, int i, Hexagon hex) {
        hex.setImage(im);
        hex.setFitHeight(GameInfo.hexsize);
        hex.setFitWidth(GameInfo.hexsize);
        hex.setX(GameInfo.hexsize* i);
    }

    private void getP3GameInfo() {
        if (GameInfo.playerFactions[2].id == FactionEnum.CRYSTALMEN) crystalmenFaction = GameInfo.playerFactions[2];
        else if (GameInfo.playerFactions[2].id == FactionEnum.FORESTMEN) treemenFaction = GameInfo.playerFactions[2];
        else if (GameInfo.playerFactions[2].id == FactionEnum.SKYMEN) skymenFaction = GameInfo.playerFactions[2];
    }

    private void getP2GameInfo() {
        if(GameInfo.playerFactions[1].id == FactionEnum.CRYSTALMEN) crystalmenFaction =GameInfo.playerFactions[1];
        else if(GameInfo.playerFactions[1].id == FactionEnum.FORESTMEN) treemenFaction =GameInfo.playerFactions[1];
        else if(GameInfo.playerFactions[1].id == FactionEnum.SKYMEN) skymenFaction =GameInfo.playerFactions[1];
    }

    private void getP1GameInfo() {
        if(GameInfo.playerFactions[0].id == FactionEnum.CRYSTALMEN) crystalmenFaction =GameInfo.playerFactions[0];
        else if(GameInfo.playerFactions[0].id == FactionEnum.FORESTMEN) treemenFaction =GameInfo.playerFactions[0];
        else if(GameInfo.playerFactions[0].id == FactionEnum.SKYMEN) skymenFaction =GameInfo.playerFactions[0];
    }

    private void setTerrainFactionSky(Image im, Faction tempsky, int i, int j, Hexagon hex, MapTile container) {

        if(tempsky == null)
            return;

        if (j == 0 && i == MapConstants.MAP_LENGTH / 2) {
            hex.setImage(im);
            container.setOwner(tempsky.pl);
            container.setHexColorBase(tempsky.color);
            HQ hq = new HQ(tempsky,new Image(new File("flying_meteor.png").toURI().toString()));
            hq.setFitHeight(GameInfo.hexsize);
            hq.setFitWidth(GameInfo.hexsize);
            container.addMapObject(hq);
        }
        if (j == 0 && i == (MapConstants.MAP_LENGTH / 2) + 1) {
            container.setOwner(tempsky.pl);
            container.setHexColorBase(tempsky.color);
            Unit unit = new Unit(tempsky,new Image(new File("FLYING_UNIT_PORTRAIT.png").toURI().toString()));
            unit.setFitHeight(GameInfo.hexsize);
            unit.setFitWidth(GameInfo.hexsize);
            container.addMapObject(unit);
        }
    }

    private void setTerrainFactionTree(Image im, Faction temptree, int i, int j, Hexagon hex, MapTile container) {
        if(temptree == null)
            return;

        if (j == MapConstants.MAP_HEIGHT - 2 && i == MapConstants.MAP_LENGTH - 1) {
            container.setOwner(temptree.pl);
            container.setHexColorBase(temptree.color);
            Unit unit = new Unit(temptree,new Image(new File("TREE_UNIT_PORTRAIT.png").toURI().toString()));
            unit.setFitHeight(GameInfo.hexsize);
            unit.setFitWidth(GameInfo.hexsize);
            container.addMapObject(unit);

        }
        if (j == MapConstants.MAP_HEIGHT - 1 && i == MapConstants.MAP_LENGTH - 1) {
            hex.setImage(im);
            container.setOwner(temptree.pl);
            container.setHexColorBase(temptree.color);
            HQ hq = new HQ(temptree,new Image(new File("tree_meteor.png").toURI().toString()));
            hq.setFitHeight(GameInfo.hexsize);
            hq.setFitWidth(GameInfo.hexsize);
            container.addMapObject(hq);

        }
    }

    private void setTerrainFactionCrystal(Image im, Faction tempcrystal, int i, int j, Hexagon hex, MapTile container) {
        if(tempcrystal == null)
            return;

        if (j == 9 && i == 0) {

            container.setOwner(tempcrystal.pl);
            container.setHexColorBase(tempcrystal.color);
            Unit unit = new Unit(tempcrystal,new Image(new File("CRYSTAL_UNIT_PORTRAIT.png").toURI().toString()));
            unit.setFitHeight(GameInfo.hexsize);
            unit.setFitWidth(GameInfo.hexsize);
            container.addMapObject(unit);


        }
        if (j == 10 && i == 0) {
            hex.setImage(im);
            container.setOwner(tempcrystal.pl);
            container.setHexColorBase(tempcrystal.color);
            HQ hq = new HQ(tempcrystal,new Image(new File("crystal_meteor.png").toURI().toString()));
            hq.setFitHeight(GameInfo.hexsize);
            hq.setFitWidth(GameInfo.hexsize);
            container.addMapObject(hq);

        }
    }

    void setTerrainBase(Hexagon hex, int i, int j)
    {
        //terrain
        if( ((j > 3) && (i > 5)) && !( j == 4 && i == 6) )
        {
            Random random = new Random();
            int randomNum = random.nextInt(2);
            if( randomNum == 1)
                hex.setImage(forest);
            else
                hex.setImage(forest2);
        }
        else if(  j > 4 )
        {
            Random random = new Random();
            int randomNum = random.nextInt(2);
            if( randomNum == 1)
                hex.setImage(hills);
            else
                hex.setImage(mountain);
        }
        else
        {
            Random random = new Random();
            int randomNum = random.nextInt(7);
            if(randomNum < 5)
                hex.setImage(desert);
            else
                hex.setImage(desertPond);
        }
    }

    void setTerrainCities(Hexagon hex, int i, int j)
    {
        if( (j < 3 && (i > 2 && i < 8) ) && !(j == 2 && i == 7) && !(j == 2 && i == 3))
        {
            hex.setImage(city);
        }
    }

    void setTerrainRivers(Hexagon hex, int i, int j)
    {
        if(j==8&&i==10)
        {
            hex.setImage(pondst);
        }
        if((i == 9 && j == 8)||(i==8&&j==9)||(i==7&&j==9))
        {
            hex.setImage(riverfl);
        }
        if(j==10&&i==6)
        {
            hex.setImage(ponden);
        }

        if(     (i == 5 && j == 5) || (i == 6 && j == 5) || (i == 7  && j == 4) ||
                (i == 8 && j == 4) || (i == 9 && j == 3) || (i == 10 && j == 3) )
        {
            hex.setImage(riverfl);
        }
        if(      (i == 0 && j == 6) || (i == 1 && j == 6) || (i == 2 && j == 7) ||
                (i == 3 && j == 7) || (i == 4 && j == 8))
        {
            hex.setImage(riverfl_right);
        }
        if( i == 4 && j == 6)
        {
            hex.setImage(ponden);
        }
    }

    @FXML
    public void setUnitPortraitAndDesc(MapObject unit)
    {
        unitPortrait.setImage(unit.portriat);
        unitStatsATK.setText("ATK "+unit.atk);
        unitStatsDEF.setText("DEF "+unit.def);
        unitStatsHP.setText("HP "+unit.hp_current+"/"+unit.hp_max);
    }
    public void setBoard(Group board)
    {
        makeDraggable(board);
        mapAnchor.getChildren().add(board);
    }

    public void setPlayersGameInfo()
    {
        GameInfo.playerFactions[0].pl= new Player(GameInfo.playerFactions[0]);
        GameInfo.playerFactions[1].pl= new Player(GameInfo.playerFactions[1]);
        GameInfo.playerFactions[2].pl= new Player(GameInfo.playerFactions[2]);
    }

    public void makeDraggable(Node node){

        node.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
        });

        node.setOnMouseDragged(mouseEvent -> {
            if(mouseEvent.getY()-mouseAnchorY>15||mouseEvent.getY()-mouseAnchorY<-15) {
                if (mouseEvent.getSceneY() - mouseAnchorY < 0 && GameInfo.y == 900) {
                    node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
                } else if (mouseEvent.getSceneY() - mouseAnchorY < 125 && GameInfo.y == 1080) {
                    node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
                }
            }
            if(mouseEvent.getX()-mouseAnchorX>15||mouseEvent.getX()-mouseAnchorX<-15) {
                if (mouseEvent.getSceneX() - mouseAnchorX > 0) {
                    node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
                }
            }
            
        });
    }


    public void endTurn() throws IOException, InterruptedException {

        System.out.println("Before click: Turn " + GameInfo.turn + " player: " + GameInfo.currentPlayerCounter);
        if( GameInfo.currentPlayerCounter != GameInfo.playerAmount - 1)
        {
            GameInfo.currentPlayerCounter += 1;
        }
        else {
            GameInfo.turn += 1;
            GameInfo.currentPlayerCounter = 0;
        }
        System.out.println("After click: Turn " + GameInfo.turn + " player: " + GameInfo.currentPlayerCounter);


    }

    public void doAttack()
    {
        showAttack();
        PauseTransition p = new PauseTransition(Duration.millis(1000));
        p.setOnFinished(e -> hideAttack());
        p.play();

    }
    @FXML
    public void hideAttack()
    {
        coverPane.getChildren().remove(cover);
        coverPane.getChildren().remove(atkPane);
    }
    @FXML
    public void showAttack()  {
        cover.setFitWidth(coverPane.getWidth());
        cover.setFitHeight(coverPane.getHeight());
        coverPane.getChildren().add(cover) ;
        coverPane.getChildren().add(atkPane);

    }
}