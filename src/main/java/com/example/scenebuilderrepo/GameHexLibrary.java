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
                if(owner != null) {
                    System.out.println("Hex belongs to player " + GameInfo.getPlayerId(owner.faction.id));
                    if(obj != null)
                        System.out.println("Unit AP: " + obj.action_points_cur + "/" + obj.action_points_max);
                }

                if (isClicked) {
                    Board.unClickAll();
                    setHexRingToUnclicked();
                    isClicked = false;
                } else {

                    moveObjIfDestSelected();
                    Board.unClickAll();
                    if (obj != null) {

                        if(GameInfo.getPlayerId(owner.faction.id) == GameInfo.currentPlayerCounter && obj.action_points_cur != 0)
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
    int action_points_cur;
    int action_points_max;
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
        action_points_cur = 1;
        action_points_max = 1;
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
        action_points_cur = 0;
        action_points_max = 0;
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
    static void unitMove(MapTile destinationTile) {
        if (destinationTile.obj == null && selectedTile.obj.getClass().equals(Unit.class)) {
            //assigning the obj from the previous mapTile to the destination tile
            destinationTile.obj = selectedTile.obj;
            destinationTile.obj.action_points_cur -= 1;
            destinationTile.getChildren().add(destinationTile.obj);
            destinationTile.setOwner(selectedTile.getOwner());
            //removing the obj from the previous mapTile
            selectedTile.getChildren().remove(selectedTile.obj);
            selectedTile.obj = null;
        }
        else if(destinationTile.obj.getClass().equals(Unit.class) && destinationTile.getOwner() != selectedTile.getOwner() )
        {
            destinationTile.hex.controller.doAttack();
            battleCalc(destinationTile);
        }
    }

    static void battleCalc(MapTile destinationTile)
    {
        int attackerHPbefore = selectedTile.obj.hp_current;
        int attackerHPafter = attackerHPbefore - destinationTile.obj.def;
        int defenderHPbefore = destinationTile.obj.hp_current;
        int defenderHPafter = defenderHPbefore - selectedTile.obj.atk;

        if(attackerHPafter < 0)
        {
            selectedTile.obj.setImage(null);
            selectedTile.obj.portriat = null;
            selectedTile.obj = null;
            GameInfo.removeUnit(selectedTile);
            return;
        }

        selectedTile.obj.hp_current = attackerHPafter;
        if(defenderHPafter < 0)
        {
            destinationTile.obj.setImage(null);
            destinationTile.obj.portriat = null;
            destinationTile.obj = null;
            GameInfo.removeUnit(destinationTile);
            return;
        }

        destinationTile.obj.hp_current = defenderHPafter;
    }
    void addMapTile(MapTile tile, int x) {
        mapTiles.get(x).add(tile);
    }

    void addColumn() {
        Vector<MapTile> hexColumn = new Vector<>();
        mapTiles.add(hexColumn);
    }

    static void addUnit( Faction faction, int i, int j)
    {
        if(faction == null)
            return;

        mapTiles.get(i).get(j).setOwner(faction.pl);
        mapTiles.get(i).get(j).setHexColorBase(faction.color);
        Unit unit;
        if( faction.id == FactionEnum.SKYMEN)
        {
            unit = new Unit(faction,new Image(new File("FLYING_UNIT_PORTRAIT.png").toURI().toString()));
        }
        else if(faction.id == FactionEnum.CRYSTALMEN)
        {
            unit =  new Unit(faction,new Image(new File("CRYSTAL_UNIT_PORTRAIT.png").toURI().toString()));
        }
        else
        {
            unit = new Unit(faction ,new Image(new File("TREE_UNIT_PORTRAIT.png").toURI().toString()));
        }
        unit.setFitWidth(GameInfo.hexsize);
        unit.setFitHeight(GameInfo.hexsize);
        mapTiles.get(i).get(j).addMapObject(unit);
        GameInfo.addUnit(faction.id, unit);
    }

    static void addHQ( Faction faction, int i, int j)
    {
        if(faction == null)
            return;

        mapTiles.get(i).get(j).setOwner(faction.pl);
        mapTiles.get(i).get(j).setHexColorBase(faction.color);
        HQ hq;
        if( faction.id == FactionEnum.SKYMEN)
        {
            hq = new HQ(faction,new Image(new File("flying_meteor.png").toURI().toString()));
        }
        else if(faction.id == FactionEnum.CRYSTALMEN) {
            hq = new HQ(faction,new Image(new File("crystal_meteor.png").toURI().toString()));
        }
        else
        {
            hq = new HQ(faction,new Image(new File("forest_meteor.png").toURI().toString()));
        }
        hq.setFitWidth(GameInfo.hexsize);
        hq.setFitHeight(GameInfo.hexsize);
        mapTiles.get(i).get(j).addMapObject(hq);
        GameInfo.addHQ(faction.id, hq);
    }

    static void removeHQ(int i, int j)
    {
        //TODO
        //Do implementacji wyrzucenie gracza z gry i kolejki jezeli zostanie zniszczone jego HQ
    }

    static void removeUnit( int i, int j)
    {
        //Usuniecie jednostki z listy jednostek
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