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

    private int x=1600;
    private int y=900;
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
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        y=900;
        x=1600;
        stage.setWidth(x);
        stage.setHeight(y);
    }

    @FXML
    void getResolutionTo1920(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        y=1080;
        x=1920;
        stage.setWidth(x);
        stage.setHeight(y);
    }

    @FXML
    void muteMusic(ActionEvent event) {

    }

    @FXML
    void muteSound(ActionEvent event) {

    }

    @FXML
    void switchToMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainmenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), x, y);
        stage.setScene(scene);
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
