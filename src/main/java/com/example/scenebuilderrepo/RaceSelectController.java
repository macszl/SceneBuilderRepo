package com.example.scenebuilderrepo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class RaceSelectController implements Initializable {

    Image Purple = new Image(new File("hexagon_purple.png").toURI().toString());
    Image Brown = new Image(new File("hexagon_brown.png").toURI().toString());
    Image Blue = new Image(new File("hexagon_blue.png").toURI().toString());


    Faction crystal=new Faction(FactionEnum.CRYSTALMEN,Purple);
    Faction tree=new Faction(FactionEnum.FORESTMEN,Brown);
    Faction sky=new Faction(FactionEnum.SKYMEN,Blue );
    @FXML
    private Button bl1;

    @FXML
    private Button bl2;

    @FXML
    private Button bl3;

    @FXML
    private Button br1;

    @FXML
    private Button br2;

    @FXML
    private Button br3;

    @FXML
    private ImageView imgp1;

    @FXML
    private ImageView imgp2;

    @FXML
    private ImageView imgp3;

    @FXML
    private ImageView rightArrowImage1;

    @FXML
    private ImageView rightArrowImage2;

    @FXML
    private ImageView rightArrowImage3;

    @FXML
    private ImageView leftArrowImage1;

    @FXML
    private ImageView leftArrowImage2;

    @FXML
    private ImageView leftArrowImage3;

    @FXML
    private ImageView backgroundImage;

    PlayerSelectController controller;

    Image arrowImage = new Image(new File("ARROW.png").toURI().toString());
    Image clickarrowImage = new Image(new File("ARROWCLICK.png").toURI().toString());
    Image bg = new Image(new File("raceSelectBG.png").toURI().toString());

    Vector<Image> imgs=new Vector<>();

    int im1=0;
    int im2=0;
    int im3=0;


    void setImg(int p)
    {
        if(p==1)
        {
            if(im1==3) im1=0;
            if(im1==-1) im1=2;
            imgp1.setImage(imgs.get(im1));
            if(im1==0) GameInfo.p1=crystal;
            if(im1==1) GameInfo.p1=tree;
            if(im1==2) GameInfo.p1=sky;
        }
        if(p==2)
        {
            if(im2==3) im2=0;
            if(im2==-1) im2=2;
            imgp2.setImage(imgs.get(im2));
            if(im2==0) GameInfo.p2=crystal;
            if(im2==1) GameInfo.p2=tree;
            if(im2==2) GameInfo.p2=sky;
        }
        if(p==3)
        {
            if(im3==3) im3=0;
            if(im3==-1) im3=2;
            imgp3.setImage(imgs.get(im3));
            if(im3==0) GameInfo.p3=crystal;
            if(im3==1) GameInfo.p3=tree;
            if(im3==2) GameInfo.p3=sky;
        }
        checkSelect();
    }

    void checkSelect()
    {
        if(im1!=im2&&im1!=im3&&im3!=im2) controller.enableCnt();
        else controller.disableCnt();
    }
    @FXML
    void bl1p(ActionEvent event) {
        im1++;
        setImg(1);
    }

    @FXML
    void bl2p(ActionEvent event) {
        im2++;
        setImg(2);

    }

    @FXML
    void bl3p(ActionEvent event) {
        im3++;
        setImg(3);
    }

    @FXML
    void br1p(ActionEvent event) {
        im1--;
        setImg(1);
    }

    @FXML
    void br2p(ActionEvent event) {
        im2--;
        setImg(2);
    }

    @FXML
    void br3p(ActionEvent event) {
        im3--;
        setImg(3);
    }
    @FXML void disableP3()
    {
        br3.setDisable(true);
        bl3.setDisable(true);
        imgp3.setImage(null);
        im3=4;
        checkSelect();
    }
    @FXML void enableP3()
    {
        br3.setDisable(false);
        bl3.setDisable(false);
        im3=0;
        checkSelect();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imgs.add(new Image(new File("CRYSTAL_HQ.png").toURI().toString()));
        imgs.add(new Image(new File("FOREST_HQ.png").toURI().toString()));
        imgs.add(new Image(new File("FLYING_HQ.png").toURI().toString()));
        imgp1.setImage(imgs.get(0));
        imgp2.setImage(imgs.get(1));
        imgp3.setImage(imgs.get(2));
        leftArrowImage1.setImage(arrowImage);
        leftArrowImage2.setImage(arrowImage);
        leftArrowImage3.setImage(arrowImage);
        rightArrowImage1.setImage(arrowImage);
        rightArrowImage2.setImage(arrowImage);
        rightArrowImage3.setImage(arrowImage);
        backgroundImage.setImage(bg);
        GameInfo.p1=crystal;
        GameInfo.p2=tree;
        GameInfo.p3=sky;
    }
}
