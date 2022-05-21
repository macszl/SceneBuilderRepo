package com.example.scenebuilderrepo;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

public class GameInfo {
    static int hexsize = 80;
    static int x = 1400;
    static int y = 900;
    static int playerAmount;
    static int turn = 0;
    static int currentPlayerCounter;
    static Faction[] playerFactions = new Faction[3];
    static ArrayList<ArrayList<Unit>> playerUnits = getPlayerUnits();
    static ArrayList<HQ> playerHQs = new ArrayList<>(3);


    public static int getPlayerId(FactionEnum _fac)
    {
        for (int i = 0; i < playerFactions.length; i++) {
            if(playerFactions[i].id == _fac)
                return i;
        }
        return -1;
    }

    public static void regenerateAP()
    {
        int size = playerUnits.get(currentPlayerCounter).size();
        for (int i = 0; i < size; i++)
        {
            if(playerUnits.get(currentPlayerCounter).get(i).action_points_cur == 0)
                playerUnits.get(currentPlayerCounter).get(i).action_points_cur = 1;
        }
    }

    public static void addUnit(FactionEnum _fac, Unit unit)
    {
        int i = getPlayerId(_fac);
        playerUnits.get(i).add(unit);
    }

    public static void addHQ(FactionEnum _fac, HQ hq)
    {
        int i = getPlayerId(_fac);
        playerHQs.add(hq);
    }

    public static ArrayList<ArrayList<Unit>> getPlayerUnits()
    {
        ArrayList<ArrayList<Unit>> unitList = new ArrayList<>();
        for(int i = 0; i < 3; i++)
        {
            unitList.add(new ArrayList<>());
        }
        return unitList;
    }
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