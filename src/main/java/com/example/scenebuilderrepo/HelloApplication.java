package com.example.scenebuilderrepo;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

                if(j == 10 && i == 0) {
                    hex = new HeadquartersHex(i, j, HQ_FilePaths, Factions.CRYSTALGUYS,controller);
                    im =  new Image(new File("hexagon_hq.png").toURI().toString());
                }
                else if ( (j == 9 || j == 8 || j == 7) && i == 0 ) {
                    hex = new UnitHex(i, j, Unit_FilePaths, Factions.CRYSTALGUYS,controller);
                    im =  new Image(new File("hexagon_u.png").toURI().toString());
                }
                else {
                    hex = new EmptyHex(i, j, Tile_FilePaths,controller);
                    im =  new Image(new File("hexagon.png").toURI().toString());
                }
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
        controller.set_board(board);
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
    protected Image unclicked;
    protected Image clicked;
    protected Image highlighted;
    int x;
    int y;

    HelloController controller;

    boolean isClicked = false;
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
    UnitHex(int x, int y, ImageSetPaths filePaths, Factions _faction, HelloController controller) {
        super(x, y, filePaths,controller);
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
                    if(faction==Factions.CRYSTALGUYS)
                    {
                        controller.set_portraitcrystal();
                    }
                }

            }
        });
        this.setPickOnBounds(true);
    }
}
class HeadquartersHex extends Hexagon
{
    Factions faction;
    HeadquartersHex(int x, int y, ImageSetPaths FilePaths, Factions _faction, HelloController controller) {
        super(x, y, FilePaths,controller);
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
                    controller.set_HQportraitcrystal();
                    isClicked=true;
                }

            }
        });
        this.setPickOnBounds(true);
    }

}

class EmptyHex extends Hexagon
{
    EmptyHex(int x, int y, ImageSetPaths FilePaths,HelloController controller) {
        super(x, y, FilePaths,controller);
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

