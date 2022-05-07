package com.example.scenebuilderrepo;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private AnchorPane mapAnchor;

    @FXML
    private ImageView unitPortrait;

    public void setUnitPortraitCrystal()
    {
        unitPortrait.setImage(new Image(new File("crystal_unit_portriat.png").toURI().toString()));

    }
    public void setHQPortraitCrystal()
    {
        unitPortrait.setImage(new Image(new File("crystal_meteor.png").toURI().toString()));
    }
    public void setUnitPortraitForest()
    {
        unitPortrait.setImage(new Image(new File("tree_unit_portriat.png").toURI().toString()));
    }
    public void setHQPortraitForest()
    {
        unitPortrait.setImage(new Image(new File("tree_meteor.png").toURI().toString()));
    }

    public void setUnitPortraitFlying()
    {
        unitPortrait.setImage(new Image(new File("flying_unit_portriat.png").toURI().toString()));
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
        //HexImages Purple = new HexImages("hexagon_purple.png" , "hexagon_purple2.png", "hexagon_purple3.png");
        //HexImages Blue = new HexImages("hexagon_blue.png" , "hexagon_blue2.png", "hexagon_blue3.png");
        //HexImages Brown = new HexImages("hexagon_brown.png" , "hexagon_brown2.png", "hexagon_brown3.png");

//        HexImages Neutral = new HexImages("hexagon.png" , "hexagon2.png", "hexagon3.png");

        Image Purple = new Image(new File("hexagon_purple.png").toURI().toString());
        Image Brown = new Image(new File("hexagon_brown.png").toURI().toString());
        Image Blue = new Image(new File("hexagon_blue.png").toURI().toString());
        Image Neutral = new Image(new File("hexagon.png").toURI().toString());

        Image city = new Image(new File("BIGCITY_HEX.png").toURI().toString());
        Image village = new Image(new File("VILLAGE_HEX.png").toURI().toString());
        Image forest = new Image(new File("FOREST_HEX.png").toURI().toString());
        Image pondst = new Image(new File("POND_RIVEREND_UP_HEX.png").toURI().toString());
        Image ponden = new Image(new File("POND_RIVEREND_DOWN_HEX.png").toURI().toString());
        Image riverfl = new Image(new File("RIVER_WITHFLOW_HEX.png").toURI().toString());


        HexImages rings =new HexImages("hexagon1.png" , "hexagon2.png", "hexagon3.png");

        unitPortrait.setFitWidth(unitPortrait.getFitWidth()*1.5);
        unitPortrait.setFitHeight(unitPortrait.getFitHeight()*1.5);

        Player player1= new Player(1,Factions.CRYSTALGUYS,Purple);
        Player player2= new Player(2,Factions.TREEGUYS,Brown);
        Player player3= new Player(3,Factions.SKYGUYS,Blue);
        Player None= new Player(0,null,Neutral);

        for(int i=0;i< MapConstants.MAP_LENGTH;i++)
        {
            b.addColumn();
            for(int j=0;j< MapConstants.MAP_HEIGHT;j++)
            {
                Hexagon hex;


                hex = new Hexagon(i, j, None,this);
                im =  new Image(new File("hexagon.png").toURI().toString());
                MapTile container = new MapTile(rings);




                hex.setImage(im);
                hex.setFitHeight(GameInfo.hexsize);
                hex.setFitWidth(GameInfo.hexsize);
                hex.setX(GameInfo.hexsize*i);

                container.getChildren().add(hex);
                container.hex=hex;

                if(j>8&&i>8)
                {
                    hex.setImage(forest);
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



                if(j<3&&i>3&&i<8)
                {
                    hex.setImage(village);
                }
                if(j<2&&i>3&&i<7)
                {
                    hex.setImage(city);
                }

                if(j==MapConstants.MAP_HEIGHT-2 &&i==0)
                {
                    container.setOwner(player1);
                    container.setBase(player1.color);
                    Unit unit = new Unit(player1.faction);
                    unit.setFitHeight(GameInfo.hexsize);
                    unit.setFitWidth(GameInfo.hexsize);
                    container.addMapObject(unit);


                }
                if(j==MapConstants.MAP_HEIGHT-1 &&i==0)
                {
                    hex.setImage(im);
                    container.setOwner(player1);
                    container.setBase(player1.color);
                    HQ hq = new HQ(player1.faction);
                    hq.setFitHeight(GameInfo.hexsize);
                    hq.setFitWidth(GameInfo.hexsize);
                    container.addMapObject(hq);

                }

                if(j==MapConstants.MAP_HEIGHT-2 &&i==MapConstants.MAP_LENGTH-1)
                {
                    container.setOwner(player2);
                    container.setBase(player2.color);
                    Unit unit = new Unit(player2.faction);
                    unit.setFitHeight(GameInfo.hexsize);
                    unit.setFitWidth(GameInfo.hexsize);
                    container.addMapObject(unit);

                }
                if(j==MapConstants.MAP_HEIGHT-1 &&i==MapConstants.MAP_LENGTH-1)
                {
                    hex.setImage(im);
                    container.setOwner(player2);
                    container.setBase(player2.color);
                    HQ hq = new HQ(player2.faction);
                    hq.setFitHeight(GameInfo.hexsize);
                    hq.setFitWidth(GameInfo.hexsize);
                    container.addMapObject(hq);

                }
                if(GameInfo.players==3) {
                    if (j == 0 && i == MapConstants.MAP_LENGTH / 2) {
                        hex.setImage(im);
                        container.setOwner(player3);
                        container.setBase(player3.color);
                        HQ hq = new HQ(player3.faction);
                        hq.setFitHeight(GameInfo.hexsize);
                        hq.setFitWidth(GameInfo.hexsize);
                        container.addMapObject(hq);
                    }
                    if (j == 0 && i == (MapConstants.MAP_LENGTH / 2) + 1) {
                        container.setOwner(player3);
                        container.setBase(player3.color);
                        Unit unit = new Unit(player3.faction);
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

    }
}