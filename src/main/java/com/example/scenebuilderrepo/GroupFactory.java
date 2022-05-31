package com.example.scenebuilderrepo;

import javafx.scene.Group;
import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public
class GroupFactory
{
    Image Neutral = new Image(new File("hexagon.png").toURI()
                                      .toString());

    Image city          = new Image(new File("BIGCITY_HEX.png").toURI()
                                            .toString());
    Image village       = new Image(new File("VILLAGE_HEX.png").toURI()
                                            .toString());
    Image forest        = new Image(new File("FOREST_HEX.png").toURI()
                                            .toString());
    Image forest2       = new Image(new File("FOREST2_HEX.png").toURI()
                                            .toString());
    Image mountain      = new Image(new File("MOUNTAIN_HEX.png").toURI()
                                            .toString());
    Image hills         = new Image(new File("HILLS_HEX.png").toURI()
                                            .toString());
    Image desert        = new Image(new File("DESERT_HEX.png").toURI()
                                            .toString());
    Image desertPond    = new Image(new File("RADIOACTIVE_POND_HEX_WIATRACZEK.png").toURI()
                                            .toString());
    Image pondst        = new Image(new File("POND_RIVEREND_UP_HEX.png").toURI()
                                            .toString());
    Image ponden        = new Image(new File("POND_RIVEREND_DOWN_HEX.png").toURI()
                                            .toString());
    Image riverfl       = new Image(new File("RIVER_WITHFLOW_HEX.png").toURI()
                                            .toString());
    Image riverfl_right = new Image(new File("RIVER_WITHFLOW_HEX_LEFT_TO_RIGHT.png").toURI()
                                            .toString());

    Faction crystalmenFaction = null;
    Faction treemenFaction    = null;
    Faction skymenFaction     = null;

    Image im;

    HexImages rings = new HexImages("hexagon1.png", "hexagon2.png", "hexagon3.png");
    HexImages bases = new HexImages("hexagon_blue.png", "hexagon_brown.png", "hexagon_purple.png");

    Faction neutral = new Faction(FactionEnum.NO_FACTION, Neutral);
    Player  None    = new Player(neutral);

    public
    GroupFactory()
    {
    }

    public
    Group getGroup(GameController controller)
    {
        Group group = new Group();
        Board board = new Board();
        if ( GameInfo.gameLoadedFromXML )
        {
            SaveReader             reader              = new SaveReader();
            ArrayList<FactionEnum> loadedFactionList   = reader.getFactionList();
            ArrayList<HexStruct>   loadedHexStructList = reader.getHexList();

            Image Purple = new Image(new File("hexagon_purple.png").toURI()
                                             .toString());
            Image Brown  = new Image(new File("hexagon_brown.png").toURI()
                                             .toString());
            Image Blue   = new Image(new File("hexagon_blue.png").toURI()
                                             .toString());

            GameInfo.playerFactions.clear();
            for (int i = 0; i < loadedFactionList.size(); i++)
            {
                Faction faction;
                if ( loadedFactionList.get(i) == FactionEnum.SKYMEN )
                {
                    faction = new Faction(loadedFactionList.get(i), Blue);
                }
                else if ( loadedFactionList.get(i) == FactionEnum.CRYSTALMEN )
                {
                    faction = new Faction(loadedFactionList.get(i), Purple);
                }
                else
                {
                    faction = new Faction(loadedFactionList.get(i), Brown);
                }
                GameInfo.playerFactions.add(faction);
            }


            return group;
        }
        else
        {

            setPlayersGameInfo();

            getP1GameInfo();
            getP2GameInfo();
            if ( GameInfo.playerAmount == 3 )
            {
                getP3GameInfo();
            }

            for (int i = 0; i < MapConstants.MAP_LENGTH; i++)
            {
                board.addColumn();
                for (int j = 0; j < MapConstants.MAP_HEIGHT; j++)
                {
                    Hexagon hex;

                    hex = new Hexagon(i, j, None, controller);
                    im  = new Image(new File("hexagon.png").toURI()
                                            .toString());
                    MapTile container = new MapTile(rings, bases);

                    setHexAttributes(im, i, hex);

                    addHexToContainer(hex, container);

                    setTerrainBase(hex, i, j);
                    setTerrainRivers(hex, i, j);
                    setTerrainCities(hex, i, j);

                    setContainerLayoutAttributes(i, j, container);

                    board.addMapTile(container, i);
                    group.getChildren()
                            .add(container);
                }
            }

            Board.addHQ(crystalmenFaction, 0, 10);
            Board.addHQ(skymenFaction, MapConstants.MAP_LENGTH / 2, 0);
            Board.addHQ(treemenFaction, MapConstants.MAP_LENGTH - 1, MapConstants.MAP_HEIGHT - 1);
            Board.addUnit(skymenFaction, 0, 9);
            Board.addUnit(skymenFaction, 1, 9);
            Board.addUnit(skymenFaction, 1, 10);

            Board.addUnit(crystalmenFaction, MapConstants.MAP_LENGTH - 2, MapConstants.MAP_HEIGHT - 2);
            Board.addUnit(crystalmenFaction, MapConstants.MAP_LENGTH - 1, MapConstants.MAP_HEIGHT - 2);
            Board.addUnit(crystalmenFaction, MapConstants.MAP_LENGTH - 2, MapConstants.MAP_HEIGHT - 1);

            Board.addUnit(treemenFaction, (MapConstants.MAP_LENGTH / 2) + 1, 0);
            Board.addUnit(treemenFaction, (MapConstants.MAP_LENGTH / 2) - 1, 0);
            Board.addUnit(treemenFaction, (MapConstants.MAP_LENGTH / 2), 1);

            return group;
        }
    }

    private
    void getP3GameInfo()
    {
        if ( GameInfo.playerFactions.get(2).id == FactionEnum.CRYSTALMEN )
        {
            crystalmenFaction =
                    GameInfo.playerFactions.get(2);
        }
        else if ( GameInfo.playerFactions.get(2).id == FactionEnum.FORESTMEN )
        {
            treemenFaction =
                    GameInfo.playerFactions.get(2);
        }
        else if ( GameInfo.playerFactions.get(2).id == FactionEnum.SKYMEN )
        {
            skymenFaction =
                    GameInfo.playerFactions.get(2);
        }
    }

    private
    void getP2GameInfo()
    {
        if ( GameInfo.playerFactions.get(1).id == FactionEnum.CRYSTALMEN )
        {
            crystalmenFaction =
                    GameInfo.playerFactions.get(1);
        }
        else if ( GameInfo.playerFactions.get(1).id == FactionEnum.FORESTMEN )
        {
            treemenFaction =
                    GameInfo.playerFactions.get(1);
        }
        else if ( GameInfo.playerFactions.get(1).id == FactionEnum.SKYMEN )
        {
            skymenFaction =
                    GameInfo.playerFactions.get(1);
        }
    }

    private
    void getP1GameInfo()
    {
        if ( GameInfo.playerFactions.get(0).id == FactionEnum.CRYSTALMEN )
        {
            crystalmenFaction =
                    GameInfo.playerFactions.get(0);
        }
        else if ( GameInfo.playerFactions.get(0).id == FactionEnum.FORESTMEN )
        {
            treemenFaction =
                    GameInfo.playerFactions.get(0);
        }
        else if ( GameInfo.playerFactions.get(0).id == FactionEnum.SKYMEN )
        {
            skymenFaction =
                    GameInfo.playerFactions.get(0);
        }
    }

    private
    void setContainerLayoutAttributes(int i, int j, MapTile container)
    {
        if ( i % 2 == 0 )
        {
            container.setLayoutY((GameInfo.hexsize - 5) * j);
        }
        else
        {
            container.setLayoutY((GameInfo.hexsize - 5) * j + ((GameInfo.hexsize - 5) / 2));
        }

        container.setLayoutX((GameInfo.hexsize - 10) * i);
    }

    private
    void addHexToContainer(Hexagon hex, MapTile container)
    {
        container.getChildren()
                .add(hex);
        container.hex = hex;
    }

    private
    void setHexAttributes(Image im, int i, Hexagon hex)
    {
        hex.setImage(im);
        hex.setFitHeight(GameInfo.hexsize);
        hex.setFitWidth(GameInfo.hexsize);
        hex.setX(GameInfo.hexsize * i);
    }

    void setTerrainBase(Hexagon hex, int i, int j)
    {
        //terrain
        if ( ((j > 3) && (i > 5)) && !(j == 4 && i == 6) )
        {
            Random random    = new Random();
            int    randomNum = random.nextInt(2);
            if ( randomNum == 1 )
            {
                hex.setImage(forest);
            }
            else
            {
                hex.setImage(forest2);
            }
        }
        else if ( j > 4 )
        {
            Random random    = new Random();
            int    randomNum = random.nextInt(2);
            if ( randomNum == 1 )
            {
                hex.setImage(hills);
            }
            else
            {
                hex.setImage(mountain);
            }
        }
        else
        {
            Random random    = new Random();
            int    randomNum = random.nextInt(7);
            if ( randomNum < 5 )
            {
                hex.setImage(desert);
            }
            else
            {
                hex.setImage(desertPond);
            }
        }
    }

    void setTerrainCities(Hexagon hex, int i, int j)
    {
        if ( (j < 3 && (i > 2 && i < 8)) && !(j == 2 && i == 7) && !(j == 2 && i == 3) )
        {
            hex.setImage(city);
        }
    }

    void setTerrainRivers(Hexagon hex, int i, int j)
    {
        if ( j == 8 && i == 10 )
        {
            hex.setImage(pondst);
        }
        if ( (i == 9 && j == 8) || (i == 8 && j == 9) || (i == 7 && j == 9) )
        {
            hex.setImage(riverfl);
        }
        if ( j == 10 && i == 6 )
        {
            hex.setImage(ponden);
        }

        if ( (i == 5 && j == 5) || (i == 6 && j == 5) || (i == 7 && j == 4) ||
             (i == 8 && j == 4) || (i == 9 && j == 3) || (i == 10 && j == 3) )
        {
            hex.setImage(riverfl);
        }
        if ( (i == 0 && j == 6) || (i == 1 && j == 6) || (i == 2 && j == 7) ||
             (i == 3 && j == 7) || (i == 4 && j == 8) )
        {
            hex.setImage(riverfl_right);
        }
        if ( i == 4 && j == 6 )
        {
            hex.setImage(ponden);
        }
        //setFactionGold();
    }

    public
    void setPlayersGameInfo()
    {
        GameInfo.playerFactions.get(0).pl = new Player(GameInfo.playerFactions.get(0));
        GameInfo.playerFactions.get(1).pl = new Player(GameInfo.playerFactions.get(1));
        GameInfo.playerFactions.get(2).pl = new Player(GameInfo.playerFactions.get(2));
    }

}
