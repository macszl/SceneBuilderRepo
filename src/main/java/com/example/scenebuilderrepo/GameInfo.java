package com.example.scenebuilderrepo;

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

class MapConstants {
    public static final int MAP_HEIGHT = 11;
    public static final int MAP_LENGTH = 11;
}