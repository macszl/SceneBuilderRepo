package com.example.scenebuilderrepo;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

class MapTile extends StackPane {

    Hexagon hex;
    ImageView hexColorBase = new ImageView();
    HexImages hexColorBases;
    ImageView hexStateRing = new ImageView();
    HexImages hexStateRings;
    MapObject obj = null;
    boolean isClicked = false;
    boolean isHighlighted = false;
    private Player owner;

    MapTile(HexImages rings, HexImages hexColorBases) {
        setHexStateRings(rings);
        setHexColorBases(hexColorBases);
        setHexStateRing(rings.unclicked);
        this.getChildren().add(hexStateRing);
        this.getChildren().add(hexColorBase);
        hexColorBase.setFitHeight(GameInfo.hexsize);
        hexColorBase.setFitWidth(GameInfo.hexsize);
        hexStateRing.setFitHeight(GameInfo.hexsize);
        hexStateRing.setFitWidth(GameInfo.hexsize);
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(owner != null)
                    System.out.println("Hex belongs to player " + GameInfo.getPlayerId(owner.faction.id));

                if (isClicked) {
                    Board.unClickAll();
                    setHexRingToUnclicked();
                    isClicked = false;
                } else {

                    moveObjIfDestSelected();
                    Board.unClickAll();
                    if (obj != null) {

                        if(GameInfo.getPlayerId(owner.faction.id) == GameInfo.currentPlayerCounter)
                            Board.highlightNearby(hex.x, hex.y);
                        setHexRingToClicked();
                        isClicked = true;

                        hex.controller.setUnitPortraitAndDesc(obj);
                    }

                }



            }
        });
    }

    void setHexRingToClicked() {
        hexStateRing.setImage(hexStateRings.clicked);
    }

    void setHexRingToHighlighted() {
        hexStateRing.setImage(hexStateRings.highlighted);
        isHighlighted = true;
    }

    void setHexRingToUnclicked() {
        hexStateRing.setImage(hexStateRings.unclicked);
        isClicked = false;
        this.isHighlighted = false;
    }

    void setHexColorBases(HexImages _bases) {
        hexColorBases = _bases;
    }

    void setHexStateRing(Image _ring) {
        hexStateRing.setImage(_ring);
    }

    void setHexStateRings(HexImages _rings) {
        hexStateRings = _rings;
    }

    void setHexColorBase(Image _base) {
        hexColorBase.setImage(_base);
    }

    void addMapObject(MapObject x) {
        obj = x;
        getChildren().add(x);
    }

    Player getOwner() {
        return owner;
    }

    void setOwner(Player _owner) {
        owner = _owner;
        setHexColorBase(owner.faction.color);
    }

    void moveObjIfDestSelected() {
        if (isHighlighted) {
            Board.unitMove(this);
        }
    }

}

class Hexagon extends ImageView {
    //default images
    int x;
    int y;
    GameController controller;


    Hexagon(int x, int y, Player _onwer, GameController controller) {
        this.x = x;
        this.y = y;
        this.controller = controller;

    }

}

class MapObject extends ImageView {
    Image portriat;
    Faction faction;

    int def;
    int atk;
    int hp_current;
    int hp_max;
}

class Unit extends MapObject {


    public Unit(Faction _faction, Image _portriat) {
        if (_faction.id == FactionEnum.CRYSTALMEN) setImage(new Image(new File("CRYSTAL_UNIT.png").toURI().toString()));
        if (_faction.id == FactionEnum.FORESTMEN) setImage(new Image(new File("FOREST_UNIT.png").toURI().toString()));
        if (_faction.id == FactionEnum.SKYMEN) setImage(new Image(new File("FLYING_UNIT.png").toURI().toString()));
        this.faction = _faction;
        this.portriat = _portriat;

        def = 3;
        atk = 8;
        hp_current = 20;
        hp_max = 20;
    }
}

class HQ extends MapObject {

    public HQ(Faction _faction, Image _portriat) {
        if (_faction.id == FactionEnum.CRYSTALMEN) setImage(new Image(new File("CRYSTAL_HQ.png").toURI().toString()));
        if (_faction.id == FactionEnum.FORESTMEN) setImage(new Image(new File("FOREST_HQ.png").toURI().toString()));
        if (_faction.id == FactionEnum.SKYMEN) setImage(new Image(new File("FLYING_HQ.png").toURI().toString()));
        this.faction = _faction;
        this.portriat = _portriat;

        def = 4;
        atk = 0;
        hp_current = 95;
        hp_max = 100;
    }
}

class Player {
    int gold = 5;
    Faction faction;


    public Player(Faction _Faction) {
        faction = _Faction;
    }
}

class Board {
    static int width;
    static int height;
    static MapTile selectedTile = null;

    static ArrayList<Vector<MapTile>> mapTiles = new ArrayList<>();

    static void unClickAll() {
        for (int i = 0; i < mapTiles.size(); i++) {
            for (int j = 0; j < mapTiles.get(i).size(); j++) {
                mapTiles.get(i).get(j).setHexRingToUnclicked();
            }
        }
        selectedTile = null;
    }

    static void highlightNearby(int x, int y) {
        selectedTile = mapTiles.get(x).get(y);
        // The lookup tables are in a {x,y} format
        int[][] ODD_COLUMN_LOOKUP_TABLE = {{1, 1}, {1, 0}, {0, -1}, {-1, 0}, {-1, 1}, {0, 1}};
        int[][] EVEN_COLUMN_LOOKUP_TABLE = {{1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {0, 1}};
        if (!selectedTile.obj.getClass().equals(Unit.class))
            return;
        if (x % 2 == 0) { //Highlighting neighbouring mapTiles. Even column version.
            for (int i = 0; i < EVEN_COLUMN_LOOKUP_TABLE.length; i++) {
                int x_offset = EVEN_COLUMN_LOOKUP_TABLE[i][0];
                int y_offset = EVEN_COLUMN_LOOKUP_TABLE[i][1];
                if ((x + x_offset >= 0 && x + x_offset < MapConstants.MAP_LENGTH) &&
                        (y + y_offset >= 0 && y + y_offset < MapConstants.MAP_HEIGHT)) {
                    mapTiles.get(x + x_offset).get(y + y_offset).setHexRingToHighlighted();
                }
            }
        } else        //Highlighting neighbouring mapTiles. Odd column version
        {
            for (int i = 0; i < ODD_COLUMN_LOOKUP_TABLE.length; i++) {
                int x_offset = ODD_COLUMN_LOOKUP_TABLE[i][0];
                int y_offset = ODD_COLUMN_LOOKUP_TABLE[i][1];
                if ((x + x_offset >= 0 && x + x_offset < MapConstants.MAP_LENGTH) &&
                        (y + y_offset >= 0 && y + y_offset < MapConstants.MAP_HEIGHT)) {
                    mapTiles.get(x + x_offset).get(y + y_offset).setHexRingToHighlighted();
                }
            }
        }
    }

    @FXML
    static void unitMove(MapTile moveHere) {
        if (moveHere.obj == null && selectedTile != null && selectedTile.obj.getClass().equals(Unit.class)) {
            moveHere.obj = selectedTile.obj;
            moveHere.getChildren().add(moveHere.obj);
            moveHere.setOwner(selectedTile.getOwner());
            selectedTile.getChildren().remove(selectedTile.obj);
            selectedTile.obj = null;
        }
    }

    void addMapTile(MapTile tile, int x) {
        mapTiles.get(x).add(tile);
    }

    void addColumn() {
        Vector<MapTile> hexColumn = new Vector<>();
        mapTiles.add(hexColumn);
    }

}

class Faction {
    Image color;

    Player pl;

    FactionEnum id;

    public Faction(FactionEnum _id, Image _color) {
        id = _id;
        color = _color;
    }
}