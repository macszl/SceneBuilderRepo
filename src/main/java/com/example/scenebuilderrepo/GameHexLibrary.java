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
    ImageView base = new ImageView();
    HexImages bases;
    ImageView ring = new ImageView();
    HexImages rings;
    MapObject obj = null;
    boolean isClicked = false;
    boolean isSelected = false;
    private Player owner;

    MapTile(HexImages rings, HexImages bases) {
        setRings(rings);
        setBases(bases);
        setRing(rings.unclicked);
        this.getChildren().add(ring);
        this.getChildren().add(base);
        base.setFitHeight(GameInfo.hexsize);
        base.setFitWidth(GameInfo.hexsize);
        ring.setFitHeight(GameInfo.hexsize);
        ring.setFitWidth(GameInfo.hexsize);
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("mouse click detected! " + hex.x + " " + hex.y);
                if (isClicked) {
                    Board.unClickAll();
                    unclicked();
                    isClicked = false;
                } else {
                    if (isSelected) {
                        move();
                    }
                    Board.unClickAll();
                    if (obj == null) return;
                    Board.selectNearby(hex.x, hex.y);
                    clicked();
                    isClicked = true;

                    hex.controller.setUnitPortraitAndDesc(obj);

                }

            }
        });
    }

    void clicked() {
        ring.setImage(rings.clicked);
    }

    void highlighted() {
        ring.setImage(rings.highlighted);
        isSelected = true;
    }

    void unclicked() {
        ring.setImage(rings.unclicked);
        isClicked = false;
        this.isSelected = false;
    }

    void setBases(HexImages _bases) {
        bases = _bases;
    }

    void setRing(Image _ring) {
        ring.setImage(_ring);
    }

    void setRings(HexImages _rings) {
        rings = _rings;
    }

    void setBase(Image _base) {
        base.setImage(_base);
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
        setBase(owner.faction.color);
    }

    void move() {
        Board.unitMove(this);
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
    static MapTile selected = null;

    static ArrayList<Vector<MapTile>> mapTiles = new ArrayList<>();

    static void unClickAll() {
        for (int i = 0; i < mapTiles.size(); i++) {
            for (int j = 0; j < mapTiles.get(i).size(); j++) {
                mapTiles.get(i).get(j).unclicked();
            }
        }
        selected = null;
    }

    static void selectNearby(int x, int y) {
        selected = mapTiles.get(x).get(y);
        // The lookup tables are in a {x,y} format
        int[][] ODD_COLUMN_LOOKUP_TABLE = {{1, 1}, {1, 0}, {0, -1}, {-1, 0}, {-1, 1}, {0, 1}};
        int[][] EVEN_COLUMN_LOOKUP_TABLE = {{1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {0, 1}};
        if (!selected.obj.getClass().equals(Unit.class)) return;
        if (x % 2 == 0) { //Selecting neighbouring mapTiles from an even column
            for (int i = 0; i < EVEN_COLUMN_LOOKUP_TABLE.length; i++) {
                int x_offset = EVEN_COLUMN_LOOKUP_TABLE[i][0];
                int y_offset = EVEN_COLUMN_LOOKUP_TABLE[i][1];
                if ((x + x_offset >= 0 && x + x_offset < MapConstants.MAP_LENGTH) &&
                        (y + y_offset >= 0 && y + y_offset < MapConstants.MAP_HEIGHT)) {
                    mapTiles.get(x + x_offset).get(y + y_offset).highlighted();
                }
            }
        } else        //Selecting neighbouring mapTiles from an odd column
        {
            for (int i = 0; i < ODD_COLUMN_LOOKUP_TABLE.length; i++) {
                int x_offset = ODD_COLUMN_LOOKUP_TABLE[i][0];
                int y_offset = ODD_COLUMN_LOOKUP_TABLE[i][1];
                if ((x + x_offset >= 0 && x + x_offset < MapConstants.MAP_LENGTH) &&
                        (y + y_offset >= 0 && y + y_offset < MapConstants.MAP_HEIGHT)) {
                    mapTiles.get(x + x_offset).get(y + y_offset).highlighted();
                }
            }
        }
    }

    @FXML
    static void unitMove(MapTile moveHere) {
        if (moveHere.obj == null && selected != null && selected.obj.getClass().equals(Unit.class)) {
            moveHere.obj = selected.obj;
            moveHere.getChildren().add(moveHere.obj);
            moveHere.setOwner(selected.getOwner());
            selected.getChildren().remove(selected.obj);
            selected.obj = null;
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