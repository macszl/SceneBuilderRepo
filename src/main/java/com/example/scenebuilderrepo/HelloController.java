package com.example.scenebuilderrepo;


import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;

public class HelloController {

    @FXML
    private AnchorPane mapAnchor;

    @FXML
    private ImageView unitPortrait;

    public void setPortraitCrystal()
    {
        unitPortrait.setImage(new Image(new File("crystalgirl.png").toURI().toString()));
    }
    public void setHQPortraitCrystal()
    {
        unitPortrait.setImage(new Image(new File("crystalHQ.png").toURI().toString()));
    }

    public void setBoard(Group board)
    {
        mapAnchor.getChildren().add(board);
    }
    public void clearPortrait()
    {
        unitPortrait.setImage(null);
    }
}