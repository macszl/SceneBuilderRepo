package com.example.scenebuilderrepo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

//class responsible for selecting the races
//
public
class RaceSelectController implements Initializable
{


	@FXML
	public ImageView imgp1;
	@FXML
	public ImageView imgp2;
	@FXML
	public ImageView imgp3;

	//arrow elements and images
	@FXML
	public ImageView rightArrowImage1;
	@FXML
	public ImageView rightArrowImage2;
	@FXML
	public ImageView rightArrowImage3;
	@FXML
	public ImageView leftArrowImage1;
	@FXML
	public ImageView leftArrowImage2;
	@FXML
	public ImageView leftArrowImage3;
	public Image arrowImage = new Image(new File("ARROW.png")
												.toURI()
												.toString());
	public Image clickarrowImage = new Image(new File("ARROWCLICK.png")
													 .toURI()
													 .toString());
	public Image greyarrowImage = new Image(new File("ARROWGREY.png")
													.toURI()
													.toString());
	//background
	@FXML
	public ImageView backgroundImage;

	//faction images
	public Image bg = new Image(new File("raceSelectBG.png")
										.toURI()
										.toString());
	Image Purple = new Image(new File("hexagon_purple.png")
									 .toURI()
									 .toString());
	Image Brown = new Image(new File("hexagon_brown.png")
									.toURI()
									.toString());
	Image Blue = new Image(new File("hexagon_blue.png")
								   .toURI()
								   .toString());



	Faction crystal = new Faction(FactionEnum.CRYSTALMEN,
								  Purple);
	Faction tree = new Faction(FactionEnum.FORESTMEN,
							   Brown);
	Faction sky = new Faction(FactionEnum.SKYMEN,
							  Blue);


	PlayerSelectController controller;
	Vector<Image> imgs = new Vector<>();

	private final int  	MAX_BOUND =  3;
	private final int  SKY_FAC_NUM = 2;
	private final int TREE_FAC_NUM = 1;
	private final int CRYS_FAC_NUM = 0;
	private final int   MIN_BOUND = -1;

	private final int PLAYER_ONE = 1;
	private final int PLAYER_TWO = 2;
	private final int PLAYER_THREE = 3;

	private final int   PLAYER_ONE_PRIO = 0;
	private final int   PLAYER_TWO_PRIO = 1;
	private final int PLAYER_THREE_PRIO = 2;

	int im1 = CRYS_FAC_NUM;
	int im2 = TREE_FAC_NUM;
	int im3 = SKY_FAC_NUM;
	@FXML
	private Button bl1;
	@FXML
	private Button bl2;
	@FXML
	private Button bl3;
	@FXML
	private Button br1;
	@FXML
	private Button br2;
	@FXML
	private Button br3;

	void setImg (int p)
	{
		if ( p == PLAYER_ONE )
		{
			//flipping around from the max value
			if ( im1 == MAX_BOUND )
			{
				im1 = CRYS_FAC_NUM;
			}
			//flipping around from the min value
			if ( im1 == MIN_BOUND )
			{
				im1 = SKY_FAC_NUM;
			}

			imgp1.setImage(imgs.get(im1));
			if ( im1 == CRYS_FAC_NUM )
			{
				GameInfo.playerFactions.set(PLAYER_ONE_PRIO,
											crystal);
			}
			if ( im1 ==TREE_FAC_NUM )
			{
				GameInfo.playerFactions.set(PLAYER_ONE_PRIO,
											tree);
			}
			if ( im1 ==SKY_FAC_NUM )
			{
				GameInfo.playerFactions.set(PLAYER_ONE_PRIO,
											sky);
			}
		}
		if ( p == PLAYER_TWO )
		{
			//flipping around to the min value
			if ( im2 == MAX_BOUND )
			{
				im2 = CRYS_FAC_NUM;
			}
			//flipping around to the max value
			if ( im2 == MIN_BOUND )
			{
				im2 =SKY_FAC_NUM;
			}
			imgp2.setImage(imgs.get(im2));
			if ( im2 == CRYS_FAC_NUM )
			{
				GameInfo.playerFactions.set(PLAYER_TWO_PRIO,
											crystal);
			}
			if ( im2 ==TREE_FAC_NUM )
			{
				GameInfo.playerFactions.set(PLAYER_TWO_PRIO,
											tree);
			}
			if ( im2 ==SKY_FAC_NUM )
			{
				GameInfo.playerFactions.set(PLAYER_TWO_PRIO,
											sky);
			}
		}
		if ( p == PLAYER_THREE )
		{
			//flipping around to the min value
			if ( im3 == MAX_BOUND )
			{
				im3 = CRYS_FAC_NUM;
			}
			//flipping around to the max value
			if ( im3 == MIN_BOUND )
			{
				im3 =SKY_FAC_NUM;
			}
			imgp3.setImage(imgs.get(im3));
			if ( im3 == CRYS_FAC_NUM )
			{
				GameInfo.playerFactions.set(PLAYER_THREE_PRIO,
											crystal);
			}
			if ( im3 ==TREE_FAC_NUM )
			{
				GameInfo.playerFactions.set(PLAYER_THREE_PRIO,
											tree);
			}
			if ( im3 ==SKY_FAC_NUM )
			{
				GameInfo.playerFactions.set(PLAYER_THREE_PRIO,
											sky);
			}
		}
		checkSelect();
	}

	void checkSelect ()
	{
		if ( im1 != im2 && im1 != im3 && im3 != im2 )
		{
			controller.enableCnt();
		}
		else
		{
			controller.disableCnt();
		}
	}

	//functions used when user presses an arrow
	//for example bl1p is used when a right arrow belonging to the first player is pressed.
	@FXML
	void bl1p (ActionEvent event)
	{
		im1++;
		setImg(PLAYER_ONE);
	}

	@FXML
	void bl2p (ActionEvent event)
	{
		im2++;
		setImg(PLAYER_TWO);
	}

	@FXML
	void bl3p (ActionEvent event)
	{
		im3++;
		setImg(PLAYER_THREE);
	}

	@FXML
	void br1p (ActionEvent event)
	{
		im1--;
		setImg(PLAYER_ONE);
	}

	@FXML
	void br2p (ActionEvent event)
	{
		im2--;
		setImg(PLAYER_TWO);
	}

	@FXML
	void br3p (ActionEvent event)
	{
		im3--;
		setImg(PLAYER_THREE);
	}

	//enabling and disabling the third player
	@FXML
	void disableP3 ()
	{
		br3.setDisable(true);
		bl3.setDisable(true);
		br2.setDisable(false);
		bl2.setDisable(false);
		br1.setDisable(false);
		bl1.setDisable(false);
		imgp3.setImage(null);
		leftArrowImage3.setImage(greyarrowImage);
		rightArrowImage3.setImage(greyarrowImage);
		im3 = 4;
		checkSelect();
	}

	@FXML
	void enableP3 ()
	{
		br3.setDisable(false);
		bl3.setDisable(false);
		br2.setDisable(false);
		bl2.setDisable(false);
		br1.setDisable(false);
		bl1.setDisable(false);
		leftArrowImage3.setImage(arrowImage);
		rightArrowImage3.setImage(arrowImage);
		im3 = SKY_FAC_NUM;
		checkSelect();
	}

	//loading the elements and the images
	@Override
	public
	void initialize (URL url, ResourceBundle resourceBundle)
	{
		imgs.add(new Image(new File("CRYSTAL_HQ.png")
								   .toURI()
								   .toString()));
		imgs.add(new Image(new File("FOREST_HQ.png")
								   .toURI()
								   .toString()));
		imgs.add(new Image(new File("FLYING_HQ.png")
								   .toURI()
								   .toString()));
		GameInfo.playerFactions.clear();
		GameInfo.playerFactions.add(crystal);
		GameInfo.playerFactions.add(tree);
		GameInfo.playerFactions.add(sky);
		leftArrowImage1.setImage(greyarrowImage);
		leftArrowImage2.setImage(greyarrowImage);
		leftArrowImage3.setImage(greyarrowImage);
		rightArrowImage1.setImage(greyarrowImage);
		rightArrowImage2.setImage(greyarrowImage);
		rightArrowImage3.setImage(greyarrowImage);
		backgroundImage.setImage(bg);
		br3.setDisable(true);
		bl3.setDisable(true);
		br2.setDisable(true);
		bl2.setDisable(true);
		br1.setDisable(true);
		bl1.setDisable(true);

	}
}
