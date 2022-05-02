package com.example.scenebuilderrepo;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController {

    @FXML
    private AnchorPane map_anchor;

    @FXML
    private ImageView unitport;

    public void set_portraitcrystal()
    {
        unitport.setImage(new Image(new File("crystalgirl.png").toURI().toString()));
    }
    public void set_HQportraitcrystal()
    {
        unitport.setImage(new Image(new File("crystalHQ.png").toURI().toString()));
    }

    public void set_board(Group board)
    {
        map_anchor.getChildren().add(board);
    }

}