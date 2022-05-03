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
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("game.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 900);
        HelloController controller = fxmlLoader.getController();
        stage.setTitle("Hello!");
        Group board = new Group();
        Board b = new Board();
        Image im;
        ImageSetPaths HQ_FilePaths = new ImageSetPaths("hexagon_hq.png" , "hexagon2_hq.png", "hexagon3_hq.png");
        ImageSetPaths Unit_FilePaths = new ImageSetPaths("hexagon_u.png" , "hexagon2_u.png", "hexagon3_u.png");
        ImageSetPaths Tile_FilePaths = new ImageSetPaths("hexagon.png" , "hexagon2.png", "hexagon3.png");
        for(int i=0;i< MapConstants.MAP_LENGTH;i++)
        {
            b.addColumn();
            for(int j=0;j< MapConstants.MAP_HEIGHT;j++)
            {
                Hexagon hex;


                hex = new Hexagon(i, j, Tile_FilePaths,controller);
                im =  new Image(new File("hexagon.png").toURI().toString());
                MapTile container = new MapTile();


                hex.setImage(im);
                hex.setFitHeight(70);
                hex.setFitWidth(70);
                hex.setX(60*i);


                container.getChildren().add(hex);
                if(i%2==0)
                    container.setLayoutY(70*j);
                else
                     container.setLayoutY(70*j+35);
                container.setLayoutX(60*i);
                container.hex=hex;
                if(j==10 &&i==0)
                {
                    Unit unit = new Unit(Factions.CRYSTALGUYS);
                    container.getChildren().add(unit);
                    unit.setFitHeight(50);
                    unit.setFitWidth(50);
                    container.obj=unit;

                }
                b.addHex(container, i);
                board.getChildren().add(container);

            }
        }
        controller.set_board(board);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

class MapTile extends StackPane
{
    Hexagon hex;
    MapObject obj=null;
    MapTile()
    {
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("mouse click detected! "+hex.x+" "+hex.y);
                if(hex.isClicked)
                {
                    Board.unClickAll();
                    hex.unclicked();
                    hex.isClicked=false;
                }
                else
                {
                    if(hex.isSelected)
                    {
                        move();
                    }
                    Board.unClickAll();
                    if(obj==null) return;
                    Board.selectNearby(hex.x,hex.y);
                    hex.clicked();
                    hex.isClicked=true;
                    if(obj.faction==Factions.CRYSTALGUYS)
                    {
                        hex.controller.set_portraitcrystal();
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
    protected Image unclicked;
    protected Image clicked;
    protected Image highlighted;
    int x;
    int y;
    HelloController controller;

    Factions faction;

    boolean isClicked = false;
    boolean isSelected = false;
    Hexagon(int x,int y, ImageSetPaths filePaths,HelloController controller)
    {
        this.x=x;
        this.y=y;
        assignImages(filePaths.unclicked, filePaths.clicked, filePaths.highlighted);
        this.controller=controller;

    }

    void assignImages(String path1, String path2, String path3)
    {
        unclicked= new Image(new File(path1).toURI().toString());
        clicked= new Image(new File(path2).toURI().toString());
        highlighted = new Image(new File(path3).toURI().toString());
    }
    void clicked()
    {
        this.setImage(clicked);
    }
    void highlighted()
    {
        this.setImage(highlighted);
        this.isSelected=true;
    }
    void unclicked()
    {
        this.setImage(unclicked);
        isClicked=false;
        this.isSelected=false;
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
        if(_faction==Factions.CRYSTALGUYS) setImage(new Image(new File("unit.png").toURI().toString()));
        this.faction=_faction;
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
                hexes.get(i).get(j).hex.unclicked();
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
        if(x%2==0) { //Selecting neighbouring hexes from an even column
            for(int i = 0; i < EVEN_COLUMN_LOOKUP_TABLE.length; i++)
            {
                int x_offset = EVEN_COLUMN_LOOKUP_TABLE[i][0];
                int y_offset = EVEN_COLUMN_LOOKUP_TABLE[i][1];
                if(     (x + x_offset >= 0 && x + x_offset < MapConstants.MAP_LENGTH) &&
                        (y + y_offset >= 0 && y + y_offset < MapConstants.MAP_HEIGHT ))
                {
                    hexes.get(x + x_offset).get(y + y_offset).hex.highlighted();
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
                    hexes.get(x + x_offset).get(y + y_offset).hex.highlighted();
                }
            }
        }
    }

    @FXML
    static void unitMove(MapTile moveHere)
    {
        if(moveHere.obj==null&&selected!=null)
        {
            moveHere.obj=selected.obj;
            moveHere.getChildren().add(moveHere.obj);
            selected.getChildren().remove(selected.obj);
            selected.obj=null;
        }
    }

}

