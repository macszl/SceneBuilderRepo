package com.example.scenebuilderrepo;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class AttackController {

    @FXML
    private AnchorPane attackPane;

    @FXML
    private ImageView attacked;

    @FXML
    private ImageView attacker;
    @FXML
    private ImageView animation;

    AnchorPane getattackPane()
    {
        return attackPane;
    }

    ImageView getAttacked(){return attacked;}
    ImageView getAttacker(){return attacker;}

    ImageView getAnimation(){return animation;}

}

