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
    public ImageView imgp1;

    @FXML
    public ImageView imgp2;

    @FXML
    public ImageView imgp3;

    @FXML
    public ImageView rightArrowImage1;

    @FXML
    public ImageView rightArrowImage2;

    @FXML
    public ImageView rightArrowImage3;

    @FXML
    public ImageView leftArrowImage1;

    @FXML
    public ImageView leftArrowImage2;

    @FXML
    public ImageView leftArrowImage3;

    @FXML
    public ImageView backgroundImage;

    PlayerSelectController controller;

    public Image arrowImage = new Image(new File("ARROW.png").toURI().toString());
    public Image clickarrowImage = new Image(new File("ARROWCLICK.png").toURI().toString());
    public Image greyarrowImage = new Image(new File("ARROWGREY.png").toURI().toString());
    public Image bg = new Image(new File("raceSelectBG.png").toURI().toString());

    Vector<Image> imgs=new Vector<>();

    int im1=0;
    int im2=1;
    int im3=2;


    void setImg(int p)
    {
        if(p==1)
        {
            if(im1==3) im1=0;
            if(im1==-1) im1=2;
            imgp1.setImage(imgs.get(im1));
            if(im1==0) GameInfo.playerFactions.set(0, crystal);
            if(im1==1) GameInfo.playerFactions.set(0, tree);
            if(im1==2) GameInfo.playerFactions.set(0, sky);
        }
        if(p==2)
        {
            if(im2==3) im2=0;
            if(im2==-1) im2=2;
            imgp2.setImage(imgs.get(im2));
            if(im2==0) GameInfo.playerFactions.set(1,crystal);
            if(im2==1) GameInfo.playerFactions.set(1,tree);
            if(im2==2) GameInfo.playerFactions.set(1,sky);
        }
        if(p==3)
        {
            if(im3==3) im3=0;
            if(im3==-1) im3=2;
            imgp3.setImage(imgs.get(im3));
            if(im3==0) GameInfo.playerFactions.set(2,crystal);
            if(im3==1) GameInfo.playerFactions.set(2,tree);
            if(im3==2) GameInfo.playerFactions.set(2,sky);
        }
        checkSelect();
    }

    void checkSelect()
    {
        if(im1!=im2&&im1!=im3&&im3!=im2) {
            controller.enableCnt();
        }
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
        leftArrowImage3.setImage(greyarrowImage);
        rightArrowImage3.setImage(greyarrowImage);
        im3=4;
        checkSelect();
    }
    @FXML void enableP3()
    {
        br3.setDisable(false);
        bl3.setDisable(false);
        leftArrowImage3.setImage(arrowImage);
        rightArrowImage3.setImage(arrowImage);
        im3=2;
        checkSelect();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imgs.add(new Image(new File("CRYSTAL_HQ.png").toURI().toString()));
        imgs.add(new Image(new File("FOREST_HQ.png").toURI().toString()));
        imgs.add(new Image(new File("FLYING_HQ.png").toURI().toString()));
        GameInfo.playerFactions.clear();
        GameInfo.playerFactions.add( crystal);
        GameInfo.playerFactions.add(tree);
        GameInfo.playerFactions.add(sky);
        leftArrowImage1.setImage(greyarrowImage);
        leftArrowImage2.setImage(greyarrowImage);
        leftArrowImage3.setImage(greyarrowImage);
        rightArrowImage1.setImage(greyarrowImage);
        rightArrowImage2.setImage(greyarrowImage);
        rightArrowImage3.setImage(greyarrowImage);
        backgroundImage.setImage(bg);
        br3.setDisable(true);
        bl3.setDisable(true);
        br2.setDisable(true);
        bl2.setDisable(true);
        br1.setDisable(true);
        bl1.setDisable(true);

    }
}
