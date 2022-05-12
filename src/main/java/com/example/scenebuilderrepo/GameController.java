package com.example.scenebuilderrepo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class GameController implements Initializable {
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
    public void setUnitPortrait(MapObject unit)
    {
        unitPortrait.setImage(unit.portriat);
        unitStatsATK.setText("ATK "+unit.atk);
        unitStatsDEF.setText("DEF "+unit.def);
        unitStatsHP.setText("HP "+unit.hp_current+"/"+unit.hp_max);

    }
    public void setUnitPortraitForest()
    {
        unitPortrait.setImage(new Image(new File("TREE_UNIT_PORTRAIT.png").toURI().toString()));
    }
    public void setHQPortraitForest()
    {
        unitPortrait.setImage(new Image(new File("tree_meteor.png").toURI().toString()));
    }

    public void setUnitPortraitFlying()
    {
        unitPortrait.setImage(new Image(new File("FLYING_UNIT_PORTRAIT.png").toURI().toString()));
    }
    public void setHQPortraitFlying()
    {
        unitPortrait.setImage(new Image(new File("flying_meteor.png").toURI().toString()));
    }
    public void setBoard(Group board)
    {
        mapAnchor.getChildren().add(board);
    }
    public void clearPortrait()
    {
        unitPortrait.setImage(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Group board = new Group();
        Board b = new Board();
        Image im;

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

        HexImages rings =new HexImages("hexagon1.png" , "hexagon2.png", "hexagon3.png");
        HexImages bases =new HexImages("hexagon_blue.png" , "hexagon_brown.png", "hexagon_purple.png");

        unitPortrait.setFitWidth(unitPortrait.getFitWidth());
        unitPortrait.setFitHeight(unitPortrait.getFitHeight());


        Player player1= new Player(1,GameInfo.p1);
        GameInfo.p1.pl=player1;
        Player player2= new Player(2,GameInfo.p2);
        GameInfo.p2.pl=player2;
        Player player3= new Player(3,GameInfo.p3);
        GameInfo.p3.pl=player3;

        Faction neutral=new Faction(0,Neutral);
        Player None= new Player(0,neutral);

        Faction tempcrystal = null;
        Faction temptree = null;
        Faction tempsky = null;
        if(GameInfo.p1.id==1) tempcrystal=GameInfo.p1;
        if(GameInfo.p1.id==2) temptree=GameInfo.p1;
        if(GameInfo.p1.id==3) tempsky=GameInfo.p1;

        if(GameInfo.p2.id==1) tempcrystal=GameInfo.p2;
        if(GameInfo.p2.id==2) temptree=GameInfo.p2;
        if(GameInfo.p2.id==3) tempsky=GameInfo.p2;
        if(GameInfo.players==3) {
            if (GameInfo.p3.id == 1) tempcrystal = GameInfo.p3;
            if (GameInfo.p3.id == 2) temptree = GameInfo.p3;
            if (GameInfo.p3.id == 3) tempsky = GameInfo.p3;
        }

        for(int i=0;i< MapConstants.MAP_LENGTH;i++)
        {
            b.addColumn();
            for(int j=0;j< MapConstants.MAP_HEIGHT;j++)
            {
                Hexagon hex;


                hex = new Hexagon(i, j, None,this);
                im =  new Image(new File("hexagon.png").toURI().toString());
                MapTile container = new MapTile(rings,bases);




                hex.setImage(im);
                hex.setFitHeight(GameInfo.hexsize);
                hex.setFitWidth(GameInfo.hexsize);
                hex.setX(GameInfo.hexsize*i);

                container.getChildren().add(hex);
                container.hex=hex;

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

                if( (j < 3 && (i > 2 && i < 8) ) && !(j == 2 && i == 7) && !(j == 2 && i == 3))
                {
                    hex.setImage(city);
                }


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
                if(tempcrystal!=null) {
                    if (j == 9 && i == 0) {

                        container.setOwner(tempcrystal.pl);
                        container.setBase(tempcrystal.color);
                        Unit unit = new Unit(tempcrystal,new Image(new File("CRYSTAL_UNIT_PORTRAIT.png").toURI().toString()));
                        unit.setFitHeight(GameInfo.hexsize);
                        unit.setFitWidth(GameInfo.hexsize);
                        container.addMapObject(unit);


                    }
                    if (j == 10 && i == 0) {
                        hex.setImage(im);
                        container.setOwner(tempcrystal.pl);
                        container.setBase(tempcrystal.color);
                        HQ hq = new HQ(tempcrystal,new Image(new File("crystal_meteor.png").toURI().toString()));
                        hq.setFitHeight(GameInfo.hexsize);
                        hq.setFitWidth(GameInfo.hexsize);
                        container.addMapObject(hq);

                    }
                }

                if(temptree!=null) {
                    if (j == MapConstants.MAP_HEIGHT - 2 && i == MapConstants.MAP_LENGTH - 1) {
                        container.setOwner(temptree.pl);
                        container.setBase(temptree.color);
                        Unit unit = new Unit(temptree,new Image(new File("TREE_UNIT_PORTRAIT.png").toURI().toString()));
                        unit.setFitHeight(GameInfo.hexsize);
                        unit.setFitWidth(GameInfo.hexsize);
                        container.addMapObject(unit);

                    }
                    if (j == MapConstants.MAP_HEIGHT - 1 && i == MapConstants.MAP_LENGTH - 1) {
                        hex.setImage(im);
                        container.setOwner(temptree.pl);
                        container.setBase(temptree.color);
                        HQ hq = new HQ(temptree,new Image(new File("tree_meteor.png").toURI().toString()));
                        hq.setFitHeight(GameInfo.hexsize);
                        hq.setFitWidth(GameInfo.hexsize);
                        container.addMapObject(hq);

                    }
                }
                if(tempsky!=null) {
                    if (j == 0 && i == MapConstants.MAP_LENGTH / 2) {
                        hex.setImage(im);
                        container.setOwner(tempsky.pl);
                        container.setBase(tempsky.color);
                        HQ hq = new HQ(tempsky,new Image(new File("flying_meteor.png").toURI().toString()));
                        hq.setFitHeight(GameInfo.hexsize);
                        hq.setFitWidth(GameInfo.hexsize);
                        container.addMapObject(hq);
                    }
                    if (j == 0 && i == (MapConstants.MAP_LENGTH / 2) + 1) {
                        container.setOwner(tempsky.pl);
                        container.setBase(tempsky.color);
                        Unit unit = new Unit(tempsky,new Image(new File("FLYING_UNIT_PORTRAIT.png").toURI().toString()));
                        unit.setFitHeight(GameInfo.hexsize);
                        unit.setFitWidth(GameInfo.hexsize);
                        container.addMapObject(unit);
                    }
                }
                if(i%2==0)
                    container.setLayoutY((GameInfo.hexsize-5)*j);
                else
                    container.setLayoutY((GameInfo.hexsize-5)*j+((GameInfo.hexsize-5)/2));
                container.setLayoutX((GameInfo.hexsize-10)*i);


                b.addHex(container, i);
                board.getChildren().add(container);

            }
        }
        setBoard(board);
        factionGold.setText("Ilosc zlota "+5);
    }
}