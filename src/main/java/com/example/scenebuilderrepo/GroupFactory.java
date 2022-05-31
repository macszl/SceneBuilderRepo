package com.example.scenebuilderrepo;

import javafx.scene.Group;
import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public
class GroupFactory
{
	Image Neutral = new Image(new File("hexagon.png")
									  .toURI()
									  .toString());

	Image city = new Image(new File("BIGCITY_HEX.png")
								   .toURI()
								   .toString());
	Image village = new Image(new File("VILLAGE_HEX.png")
									  .toURI()
									  .toString());
	Image forest = new Image(new File("FOREST_HEX.png")
									 .toURI()
									 .toString());
	Image forest2 = new Image(new File("FOREST2_HEX.png")
									  .toURI()
									  .toString());
	Image mountain = new Image(new File("MOUNTAIN_HEX.png")
									   .toURI()
									   .toString());
	Image hills = new Image(new File("HILLS_HEX.png")
									.toURI()
									.toString());
	Image desert = new Image(new File("DESERT_HEX.png")
									 .toURI()
									 .toString());
	Image desertPond = new Image(new File("RADIOACTIVE_POND_HEX_WIATRACZEK.png")
										 .toURI()
										 .toString());
	Image pondst = new Image(new File("POND_RIVEREND_UP_HEX.png")
									 .toURI()
									 .toString());
	Image ponden = new Image(new File("POND_RIVEREND_DOWN_HEX.png")
									 .toURI()
									 .toString());
	Image riverfl = new Image(new File("RIVER_WITHFLOW_HEX.png")
									  .toURI()
									  .toString());
	Image riverfl_right = new Image(new File("RIVER_WITHFLOW_HEX_LEFT_TO_RIGHT.png")
											.toURI()
											.toString());

	HashMap<String, Image> imageHashMap;
	Faction crystalmenFaction = null;
	Faction treemenFaction = null;
	Faction skymenFaction = null;

	Image im;

	HexImages rings = new HexImages("hexagon1.png",
									"hexagon2.png",
									"hexagon3.png");
	HexImages bases = new HexImages("hexagon_blue.png",
									"hexagon_brown.png",
									"hexagon_purple.png");

	Faction neutral = new Faction(FactionEnum.NO_FACTION,
								  Neutral);
	Player None = new Player(neutral);

	public
	GroupFactory ()
	{
		imageHashMap = new HashMap<>();
		imageHashMap.put("BIGCITY_HEX.png",
						 city);
		imageHashMap.put("VILLAGE_HEX.png",
						 village);
		imageHashMap.put("FOREST_HEX.png",
						 forest);
		imageHashMap.put("FOREST2_HEX.png",
						 forest2);
		imageHashMap.put("MOUNTAIN_HEX.png",
						 mountain);
		imageHashMap.put("HILLS_HEX.png",
						 hills);
		imageHashMap.put("DESERT_HEX.png",
						 desert);
		imageHashMap.put("RADIOACTIVE_POND_HEX_WIATRACZEK.png",
						 desertPond);
		imageHashMap.put("POND_RIVEREND_UP_HEX.png",
						 pondst);
		imageHashMap.put("POND_RIVEREND_DOWN_HEX.png",
						 ponden);
		imageHashMap.put("RIVER_WITHFLOW_HEX.png",
						 riverfl);
		imageHashMap.put("RIVER_WITHFLOW_HEX_LEFT_TO_RIGHT.png",
						 riverfl_right);
	}

	public
	Group getGroup (GameController controller)
	{
		Group group = new Group();
		Board board = new Board();
		if ( GameInfo.gameLoadedFromXML )
		{
			SaveReader reader = new SaveReader();
			ArrayList<FactionEnum> loadedFactionList = reader.getFactionList();
			ArrayList<HexStruct> loadedHexStructList = reader.getHexList();
			ArrayList<FactionInfoStruct> loadedPlayerInfo = reader.getPlayerInformation();
			int unitCounter = 0;


			GameInfo.playerAmount = loadedFactionList.size();
			GameInfo.currentPlayerCounter = reader.getCurrentPlayer();
			GameInfo.turn = reader.getTurn();

			Image Purple = new Image(new File("hexagon_purple.png")
											 .toURI()
											 .toString());
			Image Brown = new Image(new File("hexagon_brown.png")
											.toURI()
											.toString());
			Image Blue = new Image(new File("hexagon_blue.png")
										   .toURI()
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

				Player player = new Player(faction);
				player.income = loadedPlayerInfo.get(i).income;
				player.gold = loadedPlayerInfo.get(i).gold;
				player.ownedHexes = loadedPlayerInfo.get(i).ownedHexes;
				GameInfo.playerFactions.get(i).pl = player;
			}

			for (int i = 0; i < loadedHexStructList.size(); i++)
			{
				if ( i % 11 == 0 )
				{
					board.addColumn();
				}

				Hexagon hex;

				int x = loadedHexStructList.get(i).x;
				int y = loadedHexStructList.get(i).y;

				hex = new Hexagon(x, y, None, controller);
				im = new Image(new File("hexagon.png").toURI().toString());
				MapTile container = new MapTile(rings, bases);

				setHexAttributes(im, x, hex);

				hex.setImage(imageHashMap.get(loadedHexStructList.get(i).imageFilename));

				addHexToContainer(hex, container);

				setContainerLayoutAttributes(x, y, container);

				board.addMapTile(container, x);
				group.getChildren().add(container);

				FactionEnum fac = loadedHexStructList.get(i).faction;
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

				if(fac != null)
				{
					container.setOwner(GameInfo.playerFactions
											   .get(GameInfo.getPlayerId(fac))
											   .pl);
				}

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
					im = new Image(new File("hexagon.png")
										   .toURI()
										   .toString());
					MapTile container = new MapTile(rings,
													bases);

					setHexAttributes(im, i, hex);

					addHexToContainer(hex, container);

					setTerrainBase(hex, i, j);
					setTerrainRivers(hex, i, j);
					setTerrainCities(hex, i, j);

					setContainerLayoutAttributes(i, j, container);

					board.addMapTile(container, i);
					group.getChildren().add(container);
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
			Board.addUnit(treemenFaction,(MapConstants.MAP_LENGTH / 2) - 1, 0);
			Board.addUnit(treemenFaction, (MapConstants.MAP_LENGTH / 2), 1);

			return group;
		}
	}

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

	private
	void setContainerLayoutAttributes (int i, int j, MapTile container)
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
	void addHexToContainer (Hexagon hex, MapTile container)
	{
		container
				.getChildren()
				.add(hex);
		container.hex = hex;
	}

	private
	void setHexAttributes (Image im, int i, Hexagon hex)
	{
		hex.setImage(im);
		hex.setFitHeight(GameInfo.hexsize);
		hex.setFitWidth(GameInfo.hexsize);
		hex.setX(GameInfo.hexsize * i);
	}

	void setTerrainBase (Hexagon hex, int i, int j)
	{
		//terrain
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
		}
		else if ( j > 4 )
		{
			Random random = new Random();
			int randomNum = random.nextInt(2);
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
			Random random = new Random();
			int randomNum = random.nextInt(7);
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

	void setTerrainCities (Hexagon hex, int i, int j)
	{
		if ( (j < 3 && (i > 2 && i < 8)) && !(j == 2 && i == 7) && !(j == 2 && i == 3) )
		{
			hex.setImage(city);
		}
	}

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
		//setFactionGold();
	}

	public
	void setPlayersGameInfo ()
	{
		GameInfo.playerFactions.get(0).pl = new Player(GameInfo.playerFactions.get(0));
		GameInfo.playerFactions.get(1).pl = new Player(GameInfo.playerFactions.get(1));
		GameInfo.playerFactions.get(2).pl = new Player(GameInfo.playerFactions.get(2));
	}

}
