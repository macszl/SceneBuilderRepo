package com.example.scenebuilderrepo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayerSelect {

    private Stage stage;
    @FXML
    private Button three;

    @FXML
    private Button two;

    @FXML
    private Button cont;

    @FXML
    void setThreePlayer(ActionEvent event) {
        GameInfo.players=3;
    }

    @FXML
    void setTwoPlayer(ActionEvent event) {
        GameInfo.players=2;
    }

    @FXML
    void startGame(ActionEvent event) throws IOException {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("game.fxml"));
        Scene scene= new Scene(fxmlLoader.load(), 1600, 900);
        stage.setScene(scene);
        stage.show();
    }

}
