package com.example.scenebuilderrepo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OptionsController implements Initializable {

    private MenuController menuController;
    @FXML
    private ToggleGroup resolutionSettings;
    @FXML
    private RadioButton getTo1600Button;

    @FXML
    private RadioButton getTo1920Button;

    @FXML
    private RadioButton muteSoundButton;

    @FXML
    private RadioButton muteMusicButton;

    @FXML
    private Button backToMenuButton;
    @FXML
    void getResolutionTo1600(ActionEvent event) {
        
    }

    @FXML
    void getResolutionTo1920(ActionEvent event) {

    }

    @FXML
    void muteMusic(ActionEvent event) {

    }

    @FXML
    void muteSound(ActionEvent event) {

    }

    @FXML
    void switchToMenu() {

    }
    //co to robi
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menu.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        menuController = fxmlLoader.getController();
    }
}
