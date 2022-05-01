package com.example.scenebuilderrepo;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;


class MapConstants {
    public static final int MAP_HEIGHT = 11;
    public static final int MAP_LENGTH = 11;
}


enum Factions {
    CRYSTALGUYS,
    TREEGUYS,
    SKYGUYS
}


public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("2.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 1600, 900);
        stage.setTitle("Hello!");
        Group board = new Group();
        Board b = new Board();
        Image im = new Image(new File("hexagon.png").toURI().toString());
        for(int i=0;i< MapConstants.MAP_LENGTH;i++)
        {
            b.addColumn();
            for(int j=0;j< MapConstants.MAP_HEIGHT;j++)
            {
                Hexagon hex;

                if(j == 10 && i == 0)
                    hex = new HeadquartersHex(i, j, Factions.TREEGUYS);
                else if ( (j == 9 || j == 8 || j == 7) && i == 0 )
                    hex = new UnitHex(i, j, Factions.TREEGUYS);
                else
                    hex = new EmptyHex(i, j);

                hex.setImage(im);
                hex.setFitHeight(70);
                hex.setFitWidth(70);
                hex.setX(60*i);

                b.addHex(hex, i);
                if(i%2==0)
                    hex.setY(70*j);
                else
                    hex.setY(70*j+35);
                board.getChildren().add(hex);

            }
        }
        Scene scene = new Scene(board,1600,900);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
class Hexagon extends ImageView
{
    //default images
    static protected Image unclicked=new Image(new File("hexagon.png").toURI().toString());
    static protected Image clicked=new Image(new File("hexagon2.png").toURI().toString());
    static protected Image highlighted=new Image(new File("hexagon3.png").toURI().toString());
    int x;
    int y;

    boolean isClicked = false;
    Hexagon(int x,int y)
    {
        this.x=x;
        this.y=y;
    }

    void clicked()
    {
        this.setImage(clicked);
    }
    void highlighted()
    {
        this.setImage(highlighted);
    }
    void unclicked()
    {
        this.setImage(unclicked);
        isClicked=false;
    }

}
class UnitHex extends Hexagon
{
    Factions faction;
    UnitHex(int x, int y, Factions _faction) {
        super(x, y);
        faction = _faction;
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("mouse click detected! "+x+" "+y);
                if(isClicked)
                {
                    Board.unClickAll();
                    unclicked();
                    isClicked=false;
                }
                else
                {
                    Board.unClickAll();
                    Board.selectNearby(x,y);
                    clicked();
                    isClicked=true;
                }

            }
        });
        this.setPickOnBounds(true);
    }

    void clicked()
    {
        this.setImage(clicked);
    }
    void highlighted()
    {
        this.setImage(highlighted);
    }
    void unclicked()
    {
        this.setImage(unclicked);
        isClicked=false;
    }
}
class HeadquartersHex extends Hexagon
{
    Factions faction;
    HeadquartersHex(int x, int y, Factions _faction) {
        super(x, y);
        faction = _faction;
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("mouse click detected! "+x+" "+y);
                if(isClicked)
                {
                    Board.unClickAll();
                    unclicked();
                    isClicked=false;
                }
                else
                {
                    Board.unClickAll();
                    clicked();
                    isClicked=true;
                }

            }
        });
        this.setPickOnBounds(true);
    }

    void clicked()
    {
        this.setImage(clicked);
    }
    void highlighted()
    {
        this.setImage(highlighted);
    }
    void unclicked()
    {
        this.setImage(unclicked);
        isClicked=false;
    }
}

class EmptyHex extends Hexagon
{
    EmptyHex(int x, int y) {
        super(x, y);
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("mouse click detected! "+x+" "+y);
                if(isClicked)
                {
                    unclicked();
                    isClicked=false;
                }
                else
                {
                    Board.unClickAll();
                    clicked();
                    isClicked=true;
                }

            }
        });
        this.setPickOnBounds(true);
    }
}
class Board
{
    static int width;
    static int height;

    static ArrayList<Vector<Hexagon>> hexes = new ArrayList<>();

    void addHex(Hexagon hex, int x)
    {
        hexes.get(x).add(hex);
    }

    void addColumn()
    {
        Vector<Hexagon> hexColumn = new Vector<>();
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
    }
    static void selectNearby(int x,int y)
    {
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
}

