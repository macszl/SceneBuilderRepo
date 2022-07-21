package com.example.scenebuilderrepo;

import javafx.scene.Group;
import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

//Class responsible for creating the whole game, based on:
//Whether the user has pressed LOAD GAME or chose the players, the factions from the selection screen.
public
class GroupFactory
{
	//initializing the base image to be used in hexes
	Image Neutral = new Image(new File("hexagon.png").toURI().toString());
	//initializing terrain images to be used in hexes
	Image city = new Image(new File("BIGCITY_HEX.png").toURI().toString());
	Image village = new Image(new File("VILLAGE_HEX.png").toURI().toString());
	Image forest = new Image(new File("FOREST_HEX.png").toURI().toString());
	Image forest2 = new Image(new File("FOREST2_HEX.png").toURI().toString());
	Image mountain = new Image(new File("MOUNTAIN_HEX.png").toURI().toString());
	Image hills = new Image(new File("HILLS_HEX.png").toURI().toString());
	Image desert = new Image(new File("DESERT_HEX.png").toURI().toString());
	Image desertPond = new Image(new File("RADIOACTIVE_POND_HEX_WIATRACZEK.png").toURI().toString());
	Image pondst = new Image(new File("POND_RIVEREND_UP_HEX.png").toURI().toString());
	Image ponden = new Image(new File("POND_RIVEREND_DOWN_HEX.png").toURI().toString());
	Image riverfl = new Image(new File("RIVER_WITHFLOW_HEX.png").toURI().toString());
	Image riverfl_right = new Image(new File("RIVER_WITHFLOW_HEX_LEFT_TO_RIGHT.png").toURI().toString());

	HashMap<String, Image> imageHashMap;

	Faction crystalmenFaction = null;
	Faction treemenFaction = null;
	Faction skymenFaction = null;

	Image im;

	//initializing the image state to be used in hexes
	HexImages rings = new HexImages("hexagon1.png",
									"hexagon2.png",
									"hexagon3.png");
	//initializing the faction colors to be used in hexes
	Image Purple = new Image(new File("hexagon_purple.png").toURI().toString());
	Image Brown = new Image(new File("hexagon_brown.png").toURI().toString());
	Image Blue = new Image(new File("hexagon_blue.png").toURI().toString());

	Faction neutral = new Faction(FactionEnum.NO_FACTION, Neutral);
	Player None = new Player(neutral);

	public
	GroupFactory ()
	{
		//populating the hashMap with entries
		//filename and the corresponding image object.
		imageHashMap = new HashMap<>();
		imageHashMap.put("BIGCITY_HEX.png", city);
		imageHashMap.put("VILLAGE_HEX.png", village);
		imageHashMap.put("FOREST_HEX.png", forest);
		imageHashMap.put("FOREST2_HEX.png", forest2);
		imageHashMap.put("MOUNTAIN_HEX.png", mountain);
		imageHashMap.put("HILLS_HEX.png", hills);
		imageHashMap.put("DESERT_HEX.png", desert);
		imageHashMap.put("RADIOACTIVE_POND_HEX_WIATRACZEK.png", desertPond);
		imageHashMap.put("POND_RIVEREND_UP_HEX.png", pondst);
		imageHashMap.put("POND_RIVEREND_DOWN_HEX.png", ponden);
		imageHashMap.put("RIVER_WITHFLOW_HEX.png", riverfl);
		imageHashMap.put("RIVER_WITHFLOW_HEX_LEFT_TO_RIGHT.png", riverfl_right);
	}

	//Constructor function for the Group object(that stores the entire game)
	//Used when user chooses to LOAD GAME.
	private
	Group getGroupFromXML(GameController controller)
	{
		Group group = new Group();
		Board board = new Board();
		//initializing a reader object which opens a file
		SaveReader reader = new SaveReader();

		//loading the information needed to reconstruct the factions
		ArrayList<FactionEnum> loadedFactionList = reader.getFactionList();
		//loading the information needed to reconstruct a list of hexes
		ArrayList<HexStruct> loadedHexStructList = reader.getHexList();
		//extended faction information
		ArrayList<FactionInfoStruct> loadedPlayerInfo = reader.getPlayerInformation();

		//assorted minor info
		GameInfo.playerAmount = reader.getPlayerAmount();
		GameInfo.currentPlayerCounter = reader.getCurrentPlayer();
		GameInfo.turn = reader.getTurn();

		//clearing any leftover faction information from the GameInfo class
		GameInfo.playerFactions.clear();

		//reconstructing the players
		for (int i = 0; i < GameInfo.playerAmount; i++)
		{
			Faction faction;
			//choosing the faction
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
			//adding the faction to the game
			GameInfo.playerFactions.add(faction);

			//adding assorted minor info to the player object
			Player player = new Player(faction);
			player.income = loadedPlayerInfo.get(i).income;
			player.gold = loadedPlayerInfo.get(i).gold;
			player.ownedHexes = loadedPlayerInfo.get(i).ownedHexes;
			//adding the player object to the game
			GameInfo.playerFactions.get(i).pl = player;
		}

		//reconstructing the board based on the info loaded from the xml file
		//loadedHexStructList.size() is board width multiplied board length
		for (int i = 0; i < loadedHexStructList.size(); i++)
		{
			//adding a new column when we're done with the hexes in the previous column
			if ( i % MapConstants.MAP_HEIGHT == 0 )
			{
				board.addColumn();
			}


			//getting the x and y from hexList
			int x = loadedHexStructList.get(i).x;
			int y = loadedHexStructList.get(i).y;

			//creating a new hexagon, initializing the hexagon image
			Hexagon hex = new Hexagon(x, y, None, controller);
			im = new Image(new File("hexagon.png").toURI().toString());
			//creating a new map tile
			MapTile tile = new MapTile(rings);
			//setting up the hex attributes such as the base image, location
			setHexAttributes(im, x, hex);
			//setting up the terrain image
			hex.setImage(imageHashMap.get(loadedHexStructList.get(i).imageFilename));

			addHexToMapTile(hex, tile);
			//setting up the layout of the map tile
			setMapTileLayoutAttributes(x, y, tile);
			//
			board.addMapTile(tile, x);
			group.getChildren().add(tile);
			//setting the hex faction
			FactionEnum fac = loadedHexStructList.get(i).faction;

			//creating an Unit since it has action points
			if ( loadedHexStructList.get(i).obj.actionPointMax == 1 )
			{
				int fac_idx = GameInfo.getPlayerId(fac);
				Board.addUnit(GameInfo.playerFactions.get(fac_idx), x, y);
				int unit_idx = GameInfo.playerUnits.get(fac_idx).size() - 1;
				int currentHP = loadedHexStructList.get(i).obj.hpCurrent;
				int maxHP = loadedHexStructList.get(i).obj.hpMax;

				int currentAP = loadedHexStructList.get(i).obj.actionPointCurr;
				int maxAP = loadedHexStructList.get(i).obj.actionPointMax;

				GameInfo.playerUnits.get(fac_idx).get(unit_idx).setAction_points_cur(currentAP);
				GameInfo.playerUnits.get(fac_idx).get(unit_idx).setAction_points_max(maxAP);

				GameInfo.playerUnits.get(fac_idx).get(unit_idx).setHp_current(currentHP);
				GameInfo.playerUnits.get(fac_idx).get(unit_idx).setHp_max(maxHP);
			}
			//creating an HQ since it has no action points but HP
			else if ( loadedHexStructList.get(i).obj.actionPointMax == 0 &&
					  loadedHexStructList.get(i).obj.hpMax != 0 )
			{
				int fac_idx = GameInfo.getPlayerId(fac);
				Board.addHQ(GameInfo.playerFactions.get(fac_idx), x, y);
				int hq_idx = GameInfo.playerHQs.size() - 1;

				int currentHP = loadedHexStructList.get(i).obj.hpCurrent;
				int maxHP = loadedHexStructList.get(i).obj.hpMax;

				GameInfo.playerHQs.get(hq_idx).setHp_current(currentHP);
				GameInfo.playerHQs.get(hq_idx).setHp_max(maxHP);
			}

			//setting up the owner of the tile
			if ( fac != null )
			{
				tile.setOwner(GameInfo.playerFactions
									  .get(GameInfo.getPlayerId(fac))
									  .pl);
			}

		}
		return group;
	}
	private
	Group getGroupFromMenu(GameController controller)
	{
		Group group = new Group();
		Board board = new Board();

		setPlayersGameInfo();

		//populating the skymenFaction, treemenFaction and crystalmenFaction variables
		getP1GameInfo();
		getP2GameInfo();
		if ( GameInfo.playerAmount == 3 )
		{
			getP3GameInfo();
		}

		for (int i = 0; i < MapConstants.MAP_LENGTH; i++)
		{
			//creating an empty column
			board.addColumn();
			for (int j = 0; j < MapConstants.MAP_HEIGHT; j++)
			{
				//creating a MapTile object and a hexagon object
				Hexagon hex = new Hexagon(i, j, None, controller);
				im = new Image(new File("hexagon.png")
									   .toURI()
									   .toString());
				MapTile mapTile = new MapTile(rings);
				//setting up the hex attributes
				setHexAttributes(im, i, hex);
				//adding hexagon to the map tile
				addHexToMapTile(hex, mapTile);

				//setting the terrain of the hex
				setTerrainBase(hex, i, j);
				setTerrainRivers(hex, i, j);
				setTerrainCities(hex, i, j);

				//setting the width, height and position of the map tile
				setMapTileLayoutAttributes(i, j, mapTile);

				//adding the map tile to the Board object map tile list.
				//the Board map tile list is used in many things throughout this application
				board.addMapTile(mapTile, i);
				//adding the map tile to the game object
				group.getChildren().add(mapTile);
			}
		}

		//portion of the code responsible for spawning units and buildings
		Board.addHQ(skymenFaction, 0, 10);
		Board.addHQ(crystalmenFaction, MapConstants.MAP_LENGTH / 2, 0);
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

	//getter for the "game" object that links everything
	//behavior of the function dependent on whether the user used LOAD GAME or generated the players and the factions
	public
	Group getGroup (GameController controller)
	{
		if ( GameInfo.gameLoadedFromXML )
		{
			return getGroupFromXML(controller);
		}
		else
		{
			return getGroupFromMenu(controller);
		}
	}

	//function sets treemenFaction or crystalmenFaction or skymenFaction
	//to the value of the third entry of the GameInfo faction list.
	private
	void getP3GameInfo ()
	{
		if ( GameInfo.playerFactions.get(2).id == FactionEnum.CRYSTALMEN )
		{
			crystalmenFaction = GameInfo.playerFactions.get(2);
		}
		else if ( GameInfo.playerFactions.get(2).id == FactionEnum.FORESTMEN )
		{
			treemenFaction = GameInfo.playerFactions.get(2);
		}
		else if ( GameInfo.playerFactions.get(2).id == FactionEnum.SKYMEN )
		{
			skymenFaction = GameInfo.playerFactions.get(2);
		}
	}

	//function sets treemenFaction or crystalmenFaction or skymenFaction
	//to the value of the second entry of the GameInfo faction list.
	private
	void getP2GameInfo ()
	{
		if ( GameInfo.playerFactions.get(1).id == FactionEnum.CRYSTALMEN )
		{
			crystalmenFaction = GameInfo.playerFactions.get(1);
		}
		else if ( GameInfo.playerFactions.get(1).id == FactionEnum.FORESTMEN )
		{
			treemenFaction = GameInfo.playerFactions.get(1);
		}
		else if ( GameInfo.playerFactions.get(1).id == FactionEnum.SKYMEN )
		{
			skymenFaction = GameInfo.playerFactions.get(1);
		}
	}

	//function sets treemenFaction or crystalmenFaction or skymenFaction
	//to the value of the first entry of the GameInfo faction list.
	private
	void getP1GameInfo ()
	{
		if ( GameInfo.playerFactions.get(0).id == FactionEnum.CRYSTALMEN )
		{
			crystalmenFaction = GameInfo.playerFactions.get(0);
		}
		else if ( GameInfo.playerFactions.get(0).id == FactionEnum.FORESTMEN )
		{
			treemenFaction = GameInfo.playerFactions.get(0);
		}
		else if ( GameInfo.playerFactions.get(0).id == FactionEnum.SKYMEN )
		{
			skymenFaction = GameInfo.playerFactions.get(0);
		}
	}

	//function responsible for setting up the layout attributes of each MapTile
	//logic changes on whether the column belongs to an even column, or an odd column
	private
	void setMapTileLayoutAttributes (int i, int j, MapTile tile)
	{
		if ( i % 2 == 0 )
		{
			tile.setLayoutY((GameInfo.hexsize - 5) * j);
		}
		else
		{
			tile.setLayoutY((GameInfo.hexsize - 5) * j + ((GameInfo.hexsize - 5) / 2));
		}

		tile.setLayoutX((GameInfo.hexsize - 10) * i);
	}

	//function responsible for making the hex object visible
	//uses StackPane's ObservableList property to make the hexagon visible
	private
	void addHexToMapTile (Hexagon hex, MapTile mapTile)
	{
		mapTile.getChildren().add(hex);
		mapTile.hex = hex;
	}

	//setter function responsible for setting the width, height and position of a hex.
	private
	void setHexAttributes (Image im, int i, Hexagon hex)
	{
		hex.setImage(im);
		hex.setFitHeight(GameInfo.hexsize);
		hex.setFitWidth(GameInfo.hexsize);
		hex.setX(GameInfo.hexsize * i);
	}

	//setting the internal hex terrain to hill, mountain, forest, desert if i and j fulfill certain conditions
	void setTerrainBase (Hexagon hex, int i, int j)
	{
		if ( ((j > 3) && (i > 5)) && !(j == 4 && i == 6) )
		{
			Random random = new Random();
			int randomNum = random.nextInt(2);
			if ( randomNum == 1 )
			{
				hex.setImage(forest);
			}
			else
			{
				hex.setImage(forest2);
			}
			hex.setTerrain(TerrainEnum.FOREST);
		}
		else if ( j > 4 )
		{
			Random random = new Random();
			int randomNum = random.nextInt(2);
			if ( randomNum == 1 )
			{
				hex.setImage(hills);
				hex.setTerrain(TerrainEnum.HILL);
			}
			else
			{
				hex.setImage(mountain);
				hex.setTerrain(TerrainEnum.MOUNTAIN);
			}
		}
		else
		{
			Random random = new Random();
			int randomNum = random.nextInt(7);
			if ( randomNum < 5 )
			{
				hex.setImage(desert);
			}
			else
			{
				hex.setImage(desertPond);
				hex.setTerrain(TerrainEnum.RADIOACTIVE);
			}
		}
	}

	//setting the internal hex terrain to city if i and j fulfill certain conditions
	void setTerrainCities (Hexagon hex, int i, int j)
	{
		if ( (j < 3 && (i > 2 && i < 8)) && !(j == 2 && i == 7) && !(j == 2 && i == 3) )
		{
			hex.setImage(city);
			hex.setTerrain(TerrainEnum.RUINS);
		}
	}

	//setting the internal hex terrain to river if i and j fulfill certain conditions
	void setTerrainRivers (Hexagon hex, int i, int j)
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
	}

	//function that populates the player variable inside the GameInfo class
	public
	void setPlayersGameInfo ()
	{
		GameInfo.playerFactions.get(0).pl = new Player(GameInfo.playerFactions.get(0));
		GameInfo.playerFactions.get(1).pl = new Player(GameInfo.playerFactions.get(1));
		GameInfo.playerFactions.get(2).pl = new Player(GameInfo.playerFactions.get(2));
	}

}
