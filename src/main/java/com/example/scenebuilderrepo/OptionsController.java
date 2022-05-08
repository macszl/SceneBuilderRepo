package com.example.scenebuilderrepo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
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
    private static RadioButton getTo1920Button;

    @FXML
    private RadioButton muteSoundButton;

    @FXML
    private RadioButton muteMusicButton;

    @FXML
    private Button backToMenuButton;

    private Scene menuScene;

    private Stage stage;
    @FXML
    void getResolutionTo1600(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        GameInfo.y=900;
        GameInfo.x=1600;
        stage.setWidth(GameInfo.x);
        stage.setHeight(GameInfo.y);
    }

    @FXML
    void getResolutionTo1920(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        GameInfo.y=1080;
        GameInfo.x=1920;
        stage.setWidth(GameInfo.x);
        stage.setHeight(GameInfo.y);
    }

    @FXML
    void muteMusic(ActionEvent event) {

    }

    @FXML
    void muteSound(ActionEvent event) {

    }

    @FXML
    void switchToMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        if(menuScene==null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainmenu.fxml"));
            menuScene = new Scene(fxmlLoader.load(), 1600, 900);
        }
        stage.setScene(menuScene);
        stage.show();
    }
    //co to robi
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainmenu.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        menuController = fxmlLoader.getController();
    }
}
