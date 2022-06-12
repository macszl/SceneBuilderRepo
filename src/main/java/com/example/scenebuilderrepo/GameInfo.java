package com.example.scenebuilderrepo;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;

enum FactionEnum
{
	NO_FACTION,
	FORESTMEN,
	CRYSTALMEN,
	SKYMEN
}


enum TerrainEnum
{
	RADIOACTIVE(0),
	MOUNTAIN(1),
	HILL(2),
	FOREST(3),
	RUINS(4);
	
	private int id;

	TerrainEnum(int i) {
		this.id=i;
	}

	static public TerrainEnum getById(int i)
	{
		switch (i) {
			case 0: return TerrainEnum.RADIOACTIVE;
			case 1: return TerrainEnum.MOUNTAIN;
			case 2: return TerrainEnum.HILL;
			case 3: return TerrainEnum.FOREST;
			case 4: return TerrainEnum.RUINS;
		}
		return null;
	}

	public int getID() {
		return id;
	}
}
public
class GameInfo
{

	public static boolean gameLoadedFromXML = false;
	static boolean skipAtk = false;
	static int hexsize = 80;
	static int x = 1400;
	static int y = 900;
	static int playerAmount;
	static int turn = 0;
	static int currentPlayerCounter;
	static ArrayList<Faction> playerFactions = new ArrayList<>();
	static ArrayList<ArrayList<Unit>> playerUnits = getPlayerUnits();
	static ArrayList<HQ> playerHQs = new ArrayList<>(3);
	static GameController gameController;

	public static
	int getPlayerId (FactionEnum _fac)
	{
		for (int i = 0; i < playerFactions.size(); i++)
		{
			if ( playerFactions.get(i).id == _fac )
			{
				return i;
			}
		}
		return -1;
	}

	public static
	void regenerateAP ()
	{
		int size = playerUnits
				.get(currentPlayerCounter)
				.size();
		for (int i = 0; i < size; i++)
		{
			if ( playerUnits
						 .get(currentPlayerCounter)
						 .get(i).getAction_points_cur() == 0 )
			{
				playerUnits
						.get(currentPlayerCounter)
						.get(i).setAction_points_cur(1);
			}
		}
	}

	public static
	void addUnit (FactionEnum _fac, Unit unit)
	{
		int i = getPlayerId(_fac);
		playerUnits
				.get(i)
				.add(unit);
	}

	public static
	void addHQ (FactionEnum _fac, HQ hq)
	{
		playerHQs.add(hq);
	}

	public static
	ArrayList<ArrayList<Unit>> getPlayerUnits ()
	{
		ArrayList<ArrayList<Unit>> unitList = new ArrayList<>();
		for (int i = 0; i < 3; i++)
		{
			unitList.add(new ArrayList<>());
		}
		return unitList;
	}

	public static
	void removeUnit (MapTile tile)
	{
		int playerID = getPlayerId(tile.obj.faction.id);
		for (
				int i = 0;
				i < playerUnits
						.get(playerID)
						.size();
				i++)
		{
			if ( playerUnits
					.get(playerID)
					.get(i)
					.equals(tile.obj) )
			{
				//usuniecie jednostki
				playerUnits
						.get(playerID)
						.remove(i);
				break;
			}
		}
	}

	public static
	void removeHQ (MapTile tile)
	{
		for (int i = 0; i < playerHQs.size(); i++)
		{
			if ( playerHQs
					.get(i)
					.equals(tile.obj) )
			{
				int factionID = getPlayerId(tile.obj.faction.id);
				playerHQs.remove(i);
				playerUnits.remove(factionID);
				//
				playerFactions.remove(factionID);
				playerAmount--;
				if ( playerAmount == 1 )
				{
					gameController.endGame();

					//wygrana!
					//terminujemy gre i sie nie przejmujemy reszta.
				}

				if ( currentPlayerCounter == playerAmount )
				{
					currentPlayerCounter--;
				}
				break;
			}
		}
	}
}

class HexImages
{
	public Image unclicked;
	public Image clicked;
	public Image highlighted;

	public
	HexImages (String _u, String _c, String _h)
	{
		unclicked = new Image(new File(_u)
									  .toURI()
									  .toString());
		clicked = new Image(new File(_c)
									.toURI()
									.toString());
		highlighted = new Image(new File(_h)
										.toURI()
										.toString());
	}
}

class MapConstants
{
	public static final int MAP_HEIGHT = 11;
	public static final int MAP_LENGTH = 11;
}