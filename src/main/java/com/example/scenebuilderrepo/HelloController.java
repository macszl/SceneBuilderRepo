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
        int imagesize=80;
        int hexsize=80;
        HexImages Purple = new HexImages("hexagon_purple.png" , "hexagon_purple2.png", "hexagon_purple3.png");
        HexImages Blue = new HexImages("hexagon_blue.png" , "hexagon_blue2.png", "hexagon_blue3.png");
        HexImages Brown = new HexImages("hexagon_brown.png" , "hexagon_brown2.png", "hexagon_brown3.png");

        HexImages Neutral = new HexImages("hexagon.png" , "hexagon2.png", "hexagon3.png");

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
                MapTile container = new MapTile();


                hex.setImage(im);
                hex.setFitHeight(hexsize);
                hex.setFitWidth(hexsize);
                hex.setX(hexsize*i);



                if(j==MapConstants.MAP_HEIGHT-2 &&i==0)
                {
                    hex.setOwner(player1);
                    hex.setImage(player1.imgs.unclicked);
                    Unit unit = new Unit(player1.faction);
                    container.getChildren().add(unit);
                    unit.setFitHeight(imagesize);
                    unit.setFitWidth(imagesize);
                    container.obj=unit;

                }
                if(j==MapConstants.MAP_HEIGHT-1 &&i==0)
                {
                    hex.setOwner(player1);
                    hex.setImage(player1.imgs.unclicked);
                    HQ hq = new HQ(player1.faction);
                    container.getChildren().add(hq);
                    hq.setFitHeight(imagesize);
                    hq.setFitWidth(imagesize);
                    container.obj=hq;

                }

                if(j==MapConstants.MAP_HEIGHT-2 &&i==MapConstants.MAP_LENGTH-1)
                {
                    hex.setOwner(player2);
                    hex.setImage(player2.imgs.unclicked);
                    Unit unit = new Unit(player2.faction);
                    container.getChildren().add(unit);
                    unit.setFitHeight(imagesize);
                    unit.setFitWidth(imagesize);
                    container.obj=unit;

                }
                if(j==MapConstants.MAP_HEIGHT-1 &&i==MapConstants.MAP_LENGTH-1)
                {
                    hex.setOwner(player2);
                    hex.setImage(player2.imgs.unclicked);
                    HQ hq = new HQ(player2.faction);
                    container.getChildren().add(hq);
                    hq.setFitHeight(imagesize);
                    hq.setFitWidth(imagesize);
                    container.obj=hq;

                }
                if(GameInfo.players==3) {
                    if (j == 0 && i == MapConstants.MAP_LENGTH / 2) {
                        hex.setOwner(player3);
                        hex.setImage(player3.imgs.unclicked);
                        HQ hq = new HQ(player3.faction);
                        container.getChildren().add(hq);
                        hq.setFitHeight(imagesize);
                        hq.setFitWidth(imagesize);
                        container.obj=hq;
                    }
                    if (j == 0 && i == (MapConstants.MAP_LENGTH / 2) + 1) {
                        hex.setOwner(player3);
                        hex.setImage(player3.imgs.unclicked);
                        Unit unit = new Unit(player3.faction);
                        container.getChildren().add(unit);
                        unit.setFitHeight(imagesize);
                        unit.setFitWidth(imagesize);
                        container.obj=unit;
                    }
                }
                container.getChildren().add(hex);
                if(i%2==0)
                    container.setLayoutY((hexsize-5)*j);
                else
                    container.setLayoutY((hexsize-5)*j+((hexsize-5)/2));
                container.setLayoutX((hexsize-10)*i);
                container.hex=hex;

                b.addHex(container, i);
                board.getChildren().add(container);

            }
        }
        setBoard(board);

    }
}