package com.example.scenebuilderrepo;

import javafx.scene.image.Image;

import java.io.File;

public class GameInfo {
    static int hexsize = 80;
    static int x = 1400;
    static int y = 900;
    static int players;
    static int turn = 0;

    static Faction p1;
    static Faction p2;
    static Faction p3;
}

enum FactionEnum {
    NO_FACTION,
    FORESTMEN,
    CRYSTALMEN,
    SKYMEN
}

class HexImages {
    public Image unclicked;
    public Image clicked;
    public Image highlighted;

    public HexImages(String _u, String _c, String _h) {
        unclicked = new Image(new File(_u).toURI().toString());
        clicked = new Image(new File(_c).toURI().toString());
        highlighted = new Image(new File(_h).toURI().toString());
    }
}

class MapConstants {
    public static final int MAP_HEIGHT = 11;
    public static final int MAP_LENGTH = 11;
}