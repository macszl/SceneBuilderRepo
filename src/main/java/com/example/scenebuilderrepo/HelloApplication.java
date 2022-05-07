package com.example.scenebuilderrepo;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

class GameInfo
{
    static int hexsize=80;
    static int x=1600;
    static int y=900;
    static int players;
}
class MapConstants {
    public static final int MAP_HEIGHT = 11;
    public static final int MAP_LENGTH = 11;
}
class ImageSetPaths {
    public String unclicked;
    public String clicked;
    public String highlighted;

    public ImageSetPaths(String _u, String _c, String _h)
    {
        unclicked = _u;
        clicked = _c;
        highlighted = _h;
    }
}

enum Factions {
    CRYSTALGUYS,
    TREEGUYS,
    SKYGUYS
}


public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainmenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), GameInfo.x, GameInfo.y);
        stage.setTitle("Wojna meteorytów");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

class MapTile extends StackPane
{

    private Player owner;
    Hexagon hex;
    ImageView base = new ImageView();
    ImageView ring = new ImageView();;

    HexImages rings;
    MapObject obj=null;

    boolean isClicked = false;
    boolean isSelected = false;

    void clicked()
    {
        ring.setImage(rings.clicked);
    }
    void highlighted()
    {
        ring.setImage(rings.highlighted);
        isSelected=true;
    }
    void unclicked()
    {
        ring.setImage(rings.unclicked);
        isClicked=false;
        this.isSelected=false;
    }

    void setRing(Image _ring)
    {
        ring.setImage(_ring);
    }
    void setRings(HexImages _rings)
    {
        rings=_rings;
    }
    void setBase(Image _base)
    {
        base.setImage(_base);
    }
    void addMapObject(MapObject x)
    {
        obj=x;
        getChildren().add(x);
    }
    Player getOwner() {return owner;}
    void setOwner(Player _owner)
    {
        owner=_owner;
        setBase(owner.color);
    }
    MapTile(HexImages rings)
    {
        setRings(rings);
        setRing(rings.unclicked);
        this.getChildren().add(ring);
        this.getChildren().add(base);
        base.setFitHeight(GameInfo.hexsize);
        base.setFitWidth(GameInfo.hexsize);
        ring.setFitHeight(GameInfo.hexsize);
        ring.setFitWidth(GameInfo.hexsize);
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("mouse click detected! "+hex.x+" "+hex.y);
                if(isClicked)
                {
                    Board.unClickAll();
                    unclicked();
                    isClicked=false;
                }
                else
                {
                    if(isSelected)
                    {
                        move();
                    }
                    Board.unClickAll();
                    if(obj==null) return;
                    Board.selectNearby(hex.x,hex.y);
                    clicked();
                    isClicked=true;
                    if(obj.getClass()==Unit.class)
                    {
                        if(obj.faction==Factions.CRYSTALGUYS)
                        {
                            hex.controller.setUnitPortraitCrystal();
                        }
                        if(obj.faction==Factions.TREEGUYS)
                        {
                            hex.controller.setUnitPortraitForest();
                        }
                        if(obj.faction==Factions.SKYGUYS)
                        {
                            hex.controller.setUnitPortraitFlying();
                        }
                    }
                    if(obj.getClass()==HQ.class)
                    {
                        if(obj.faction==Factions.CRYSTALGUYS)
                        {
                            hex.controller.setHQPortraitCrystal();
                        }
                        if(obj.faction==Factions.TREEGUYS)
                        {
                            hex.controller.setHQPortraitForest();
                        }
                        if(obj.faction==Factions.SKYGUYS)
                        {
                            hex.controller.setHQPortraitFlying();
                        }
                    }
                }

            }
        });
    }

    void move()
    {
        Board.unitMove(this);
    }

}
class Hexagon extends ImageView
{
    //default images
    int x;
    int y;
    HelloController controller;



    Hexagon(int x,int y, Player _onwer,HelloController controller)
    {
        this.x=x;
        this.y=y;
        this.controller=controller;

    }

}

class MapObject extends ImageView
{
    Factions faction;
}
class Unit extends MapObject
{

    public Unit(Factions _faction)
    {
        if(_faction==Factions.CRYSTALGUYS) setImage(new Image(new File("CRYSTAL_UNIT.png").toURI().toString()));
        if(_faction==Factions.TREEGUYS) setImage(new Image(new File("FOREST_UNIT.png").toURI().toString()));
        if(_faction==Factions.SKYGUYS) setImage(new Image(new File("FLYING_UNIT.png").toURI().toString()));
        this.faction=_faction;
    }
}

class HQ extends MapObject
{
    public HQ(Factions _faction)
    {
        if(_faction==Factions.CRYSTALGUYS) setImage(new Image(new File("CRYSTAL_HQ.png").toURI().toString()));
        if(_faction==Factions.TREEGUYS) setImage(new Image(new File("FOREST_HQ.png").toURI().toString()));
        if(_faction==Factions.SKYGUYS) setImage(new Image(new File("FLYING_HQ.png").toURI().toString()));
        this.faction=_faction;
    }
}

class Player
{
    int number;
    Factions faction;

    //HexImages imgs;

    Image color;

    public Player(int x,Factions _Faction,Image img)
    {
        number=x;
        faction = _Faction;
        color=img;
        //imgs=_imgs;
    }
}

class HexImages
{
    public Image unclicked;
    public Image clicked;
    public Image highlighted;

    public HexImages(String _u, String _c, String _h)
    {
        unclicked = new Image(new File(_u).toURI().toString());
        clicked =new Image(new File(_c).toURI().toString());
        highlighted =new Image(new File(_h).toURI().toString());
    }


}
class Board
{
    static int width;
    static int height;
    static MapTile selected = null;

    static ArrayList<Vector<MapTile>> hexes = new ArrayList<>();

    void addHex(MapTile hex, int x)
    {
        hexes.get(x).add(hex);
    }

    void addColumn()
    {
        Vector<MapTile> hexColumn = new Vector<>();
        hexes.add(hexColumn);
    }
    static void unClickAll()
    {
        for(int i = 0;i < hexes.size();i++)
        {
            for(int j = 0; j < hexes.get(i).size(); j++)
            {
                hexes.get(i).get(j).unclicked();
            }
        }
        selected = null;
    }
    static void selectNearby(int x,int y)
    {
        selected=hexes.get(x).get(y);
        // The lookup tables are in a {x,y} format
        int[][]  ODD_COLUMN_LOOKUP_TABLE = { {1,  1}, {1,  0}, { 0, -1}, {-1,  0}, {-1,  1}, { 0, 1}};
        int[][] EVEN_COLUMN_LOOKUP_TABLE = { {1,  0}, {1, -1}, { 0, -1}, {-1, -1}, {-1,  0}, { 0, 1}};
        if(!selected.obj.getClass().equals(Unit.class)) return;
        if(x%2==0) { //Selecting neighbouring hexes from an even column
            for(int i = 0; i < EVEN_COLUMN_LOOKUP_TABLE.length; i++)
            {
                int x_offset = EVEN_COLUMN_LOOKUP_TABLE[i][0];
                int y_offset = EVEN_COLUMN_LOOKUP_TABLE[i][1];
                if(     (x + x_offset >= 0 && x + x_offset < MapConstants.MAP_LENGTH) &&
                        (y + y_offset >= 0 && y + y_offset < MapConstants.MAP_HEIGHT ))
                {
                    hexes.get(x + x_offset).get(y + y_offset).highlighted();
                }
            }
        }
        else        //Selecting neighbouring hexes from an odd column
        {
            for(int i = 0; i < ODD_COLUMN_LOOKUP_TABLE.length; i++)
            {
                int x_offset = ODD_COLUMN_LOOKUP_TABLE[i][0];
                int y_offset = ODD_COLUMN_LOOKUP_TABLE[i][1];
                if(     (x + x_offset >= 0 && x + x_offset < MapConstants.MAP_LENGTH) &&
                        (y + y_offset >= 0 && y + y_offset < MapConstants.MAP_HEIGHT ))
                {
                    hexes.get(x + x_offset).get(y + y_offset).highlighted();
                }
            }
        }
    }

    @FXML
    static void unitMove(MapTile moveHere)
    {
        if(moveHere.obj==null&&selected!=null&&selected.obj.getClass().equals(Unit.class))
        {
            moveHere.obj=selected.obj;
            moveHere.getChildren().add(moveHere.obj);
            moveHere.setOwner(selected.getOwner());
            selected.getChildren().remove(selected.obj);
            selected.obj=null;
        }
    }

}

