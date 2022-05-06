package com.example.scenebuilderrepo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Menucontrol implements Initializable {

    @FXML
    private AnchorPane menuAnchor;

    @FXML
    private VBox menuScene;

    @FXML
    private Button opitonsButton;

    @FXML
    private Button MMb;

    @FXML
    void toMenu(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainmenu.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        try {
            menuAnchor.getChildren().clear();
            menuAnchor.getChildren().add(registerPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void toOptions(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("options.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        try {
            menuAnchor.getChildren().clear();
            menuAnchor.getChildren().add(registerPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainmenu.fxml"));
        Pane registerPane = null;
        try {
            registerPane = (Pane) fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            menuAnchor.getChildren().clear();
            menuAnchor.getChildren().add(registerPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
