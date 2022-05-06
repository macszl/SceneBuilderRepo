package com.example.scenebuilderrepo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Optionscontorl implements Initializable {

    private Menucontrol menucontrol;
    @FXML
    private ToggleGroup rez;
    @FXML
    private RadioButton x1600;

    @FXML
    private RadioButton x1920;

    @FXML
    void to1600(MouseEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setWidth(1600);
        stage.setHeight(900);
        stage.setFullScreen(false);
    }

    @FXML
    void to1920(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        //stage.setWidth(1920);
        // stage.setHeight(1080);
        stage.setFullScreen(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menu.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        menucontrol = fxmlLoader.getController();
    }
}
