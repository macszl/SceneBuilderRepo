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
import java.util.Vector;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("2.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 1600, 900);
        stage.setTitle("Hello!");
        Group board = new Group();
        Board b = new Board();
        Image im = new Image(new File("hexagon.png").toURI().toString());
        for(int i=0;i<11;i++)
        {
            for(int j=0;j<11;j++)
            {
                Hexagon img = new Hexagon(i,j);
            
                img.setImage(im);
                img.setFitHeight(70);
                img.setFitWidth(70);
                img.setX(60*i);

                b.addHex(img);
                if(i%2==0) img.setY(70*j);
                else img.setY(70*j+35);
                board.getChildren().add(img);

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
    static protected Image unclicked=new Image(new File("hexagon.png").toURI().toString());;
    static protected Image clicked=new Image(new File("hexagon2.png").toURI().toString());;
    static protected Image highlited=new Image(new File("hexagon3.png").toURI().toString());;
    int x;
    int y;

    boolean isClicked = false;
    Hexagon(int x,int y)
    {
        this.x=x;
        this.y=y;

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
                    Board.selectNerby(x,y);
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
    void highlited()
    {
        this.setImage(highlited);
    }
    void unclicked()
    {
        this.setImage(unclicked);
        isClicked=false;
    }

}

class Board
{
    static int width;
    static int height;
    static Vector<Hexagon> hexes = new Vector<>();

    void addHex(Hexagon hex)
    {
        hexes.add(hex);
    }
    static void unClickAll()
    {
        for(int i=0;i<hexes.size();i++)
        {
            hexes.get(i).unclicked();
        }
    }
    static void selectNerby(int x,int y)
    {
        for(int i=0;i<hexes.size();i++)
        {
            if(x%2==0) {
                if ((hexes.get(i).x == x + 1 || hexes.get(i).x == x - 1) && (hexes.get(i).y == y ||  hexes.get(i).y == y - 1)) {
                    hexes.get(i).highlited();
                }
                else if( (hexes.get(i).x == x) && (hexes.get(i).y == y-1 ||  hexes.get(i).y == y + 1) )
                {
                    hexes.get(i).highlited();
                }
            }
            else
            {
                if ((hexes.get(i).x == x + 1 || hexes.get(i).x == x - 1) && (hexes.get(i).y == y ||  hexes.get(i).y == y + 1)) {
                    hexes.get(i).highlited();
                }
                else if( (hexes.get(i).x == x) && (hexes.get(i).y == y-1 ||  hexes.get(i).y == y + 1) )
                {
                    hexes.get(i).highlited();
                }
            }

        }
    }
}
