//Name: Sasha Ohayon and Kyle Singer
//Program Name: Angry Chaims
//Date Edited: May 21st, 2017

public class Levels { //Class written by Sasha Ohayon, with individual levels designed by Kyle Singer
	
	static Level levelOne;
	static Level levelTwo;
	static Level levelThree;
	static Level levelFour;
	static Level levelFive;
	static Level levelSix;
	static Level levelSeven;
	static Level levelEight;
	static Level levelNine;
	static Level levelTen;
	
	public static void initialize () { //Each level is individually designed, specifically so that each block looks as intended and is located where intended
		//Floor = 345 pixels
		//Radius = 10,15,20 
		//#layBrick(index, filepath[], radius, velocity, angle, aX, aY,  x, y, material, level, angularV, isTractor)
	
		levelOne = new Level(4, 1, 0);
		levelOne.layBrick(0, new String []{"Resources/Blocks/Wood/circle.png", "Resources/Blocks/Wood/brokencircle.png", "Resources/transparent.png"}, 10, 1, 0, 0, 9.8 , 430, 335, Block.WOOD, levelOne, 0, false);
		levelOne.layBrick(1, new String []{"Resources/Blocks/Stone/circle.png", "Resources/Blocks/Stone/brokencircle.png", "Resources/transparent.png"}, 10, 1, 0, 0, 9.8 , 450, 295, Block.STONE, levelOne, 0, false);
		levelOne.layBrick(2, new String []{"Resources/Blocks/Stone/circle.png", "Resources/Blocks/Stone/brokencircle.png", "Resources/transparent.png"}, 10, 1, 0, 0, 9.8 , 470, 335, Block.STONE, levelOne, 0, false);
		levelOne.layBrick(3, new String []{"Resources/tractor.png", "Resources/tractor.png", "Resources/transparent.png"}, 15, 1, 0, 0, 9.8 , 450, 320, Block.ICE, levelOne, 0, true);

		levelTwo = new Level (10, 2, 0);
		levelTwo.layBrick(0, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 420, 335, Block.STONE, levelTwo,0,false);
		levelTwo.layBrick(1, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 440, 335, Block.STONE, levelTwo,0,false);
		levelTwo.layBrick(2, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 460, 335, Block.STONE, levelTwo,0,false);
		levelTwo.layBrick(3, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 480, 335, Block.STONE, levelTwo,0,false);
		levelTwo.layBrick(4, new String []{"Resources/tractor.png","Resources/tractor.png","Resources/transparent.png"}, 15, 1, 0, 0, 9.8, 420, 310, Block.ICE, levelTwo,0,true);
		levelTwo.layBrick(5, new String []{"Resources/tractor.png","Resources/tractor.png","Resources/transparent.png"}, 15, 1, 0, 0, 9.8, 480, 310, Block.ICE, levelTwo,0,true);
		levelTwo.layBrick(6, new String []{"Resources/Blocks/Wood/circle.png","Resources/Blocks/Wood/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 420, 285, Block.WOOD, levelTwo,0,false);
		levelTwo.layBrick(7, new String []{"Resources/Blocks/Wood/circle.png","Resources/Blocks/Wood/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 440, 285, Block.WOOD, levelTwo,0,false);
		levelTwo.layBrick(8, new String []{"Resources/Blocks/Wood/circle.png","Resources/Blocks/Wood/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 460, 285, Block.WOOD, levelTwo,0,false);
		levelTwo.layBrick(9, new String []{"Resources/Blocks/Wood/circle.png","Resources/Blocks/Wood/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 480, 285, Block.WOOD, levelTwo,0,false);

		levelThree = new Level(9, 3, 0);
		levelThree.layBrick(0, new String []{"Resources/tractor.png","Resources/tractor.png","Resources/transparent.png"}, 15, 1, 0, 0, 9.8, 420, 330, Block.ICE, levelThree,0,true);
		levelThree.layBrick(1, new String []{"Resources/Blocks/Wood/circle.png","Resources/Blocks/Wood/brokencircle.png","Resources/transparent.png"}, 15, 1, 0, 0, 9.8, 420, 300, Block.WOOD, levelThree,0,false);
		levelThree.layBrick(2, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 15, 1, 0, 0, 9.8, 420, 270, Block.STONE, levelThree,0,false);
		levelThree.layBrick(3, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 15, 1, 0, 0, 9.8, 420, 240, Block.STONE, levelThree,0,false);
		levelThree.layBrick(4, new String []{"Resources/tractor.png","Resources/tractor.png","Resources/transparent.png"}, 15, 1, 0, 0, 9.8, 420, 210, Block.ICE, levelThree,0,true);
		levelThree.layBrick(5, new String []{"Resources/Blocks/Wood/circle.png","Resources/Blocks/Wood/brokencircle.png","Resources/transparent.png"}, 15, 1, 0, 0, 9.8, 420, 180, Block.WOOD, levelThree,0,false);
		levelThree.layBrick(6, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 15, 1, 0, 0, 9.8, 420, 150, Block.STONE, levelThree,0,false);
		levelThree.layBrick(7, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 15, 1, 0, 0, 9.8, 420, 120, Block.STONE, levelThree,0,false);
		levelThree.layBrick(8, new String []{"Resources/tractor.png","Resources/tractor.png","Resources/transparent.png"}, 15, 1, 0, 0, 9.8, 420, 90, Block.ICE, levelThree,0,true);

		levelFour = new Level(16, 4, 0);
		levelFour.layBrick(0, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 420, 335, Block.STONE, levelFour,0,false);
		levelFour.layBrick(1, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 440, 335, Block.STONE, levelFour,0,false);
		levelFour.layBrick(2, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 460, 335, Block.STONE, levelFour,0,false);
		levelFour.layBrick(3, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 480, 335, Block.STONE, levelFour,0,false);
		levelFour.layBrick(4, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 500, 335, Block.STONE, levelFour,0,false);
		levelFour.layBrick(5, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 440, 315, Block.STONE, levelFour,0,false);
		levelFour.layBrick(6, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 460, 315, Block.STONE, levelFour,0,false);
		levelFour.layBrick(7, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 480, 315, Block.STONE, levelFour,0,false);
		levelFour.layBrick(8, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 500, 315, Block.STONE, levelFour,0,false);
		levelFour.layBrick(9, new String []{"Resources/tractor.png","Resources/tractor.png","Resources/transparent.png"}, 15, 1, 0, 0, 9.8, 565, 250, Block.ICE, levelFour,0,true);
		levelFour.layBrick(10, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 460, 295, Block.STONE, levelFour,0,false);
		levelFour.layBrick(11, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 480, 295, Block.STONE, levelFour,0,false);
		levelFour.layBrick(12, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 500, 295, Block.STONE, levelFour,0,false);
		levelFour.layBrick(13, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 480, 275, Block.STONE, levelFour,0,false);
		levelFour.layBrick(14, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 500, 275, Block.STONE, levelFour,0,false);
		levelFour.layBrick(15, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 500, 255, Block.STONE, levelFour,0,false);
		
		levelFive = new Level(15, 5, 0);
		levelFive.layBrick(0, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 400, 335, Block.STONE, levelFive,0,false);
		levelFive.layBrick(1, new String []{"Resources/Blocks/Wood/circle.png","Resources/Blocks/Wood/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 420, 335, Block.WOOD, levelFive,0,false);
		levelFive.layBrick(2, new String []{"Resources/Blocks/Wood/circle.png","Resources/Blocks/Wood/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 440, 335, Block.WOOD, levelFive,0,false);
		levelFive.layBrick(3, new String []{"Resources/tractor.png","Resources/tractor.png","Resources/transparent.png"}, 20, 1, 0, 0, 9.8, 470, 325, Block.ICE, levelFive,0,true); 
		levelFive.layBrick(4, new String []{"Resources/Blocks/Wood/circle.png","Resources/Blocks/Wood/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 500, 335, Block.WOOD, levelFive,0,false);
		levelFive.layBrick(5, new String []{"Resources/Blocks/Wood/circle.png","Resources/Blocks/Wood/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 520, 335, Block.WOOD, levelFive,0,false);
		levelFive.layBrick(6, new String []{"Resources/Blocks/Wood/circle.png","Resources/Blocks/Wood/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 540, 335, Block.WOOD, levelFive,0,false);
		levelFive.layBrick(7, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 400, 315, Block.STONE, levelFive,0,false);
		levelFive.layBrick(8, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 420, 315, Block.STONE, levelFive,0,false);
		levelFive.layBrick(9, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 440, 315, Block.STONE, levelFive,0,false);
		levelFive.layBrick(10, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 500, 315, Block.STONE, levelFive,0,false);
		levelFive.layBrick(11, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 520, 315, Block.STONE, levelFive,0,false); 
		levelFive.layBrick(12, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 540, 315, Block.STONE, levelFive,0,false);
		levelFive.layBrick(13, new String []{"Resources/Blocks/Wood/circle.png","Resources/Blocks/Wood/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 560, 335, Block.WOOD, levelFive,0,false);
		levelFive.layBrick(14, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 10, 1, 0, 0, 9.8, 560, 315, Block.STONE, levelFive,0,false);

		levelSix = new Level(5, 6, 0);
		levelSix.layBrick(0, new String []{"Resources/tractor.png","Resources/tractor.png","Resources/transparent.png"}, 10, 1, 0, 0, 5, 530, 295, Block.WOOD, levelSix,0,true);
		levelSix.layBrick(1, new String []{"Resources/tractor.png","Resources/tractor.png","Resources/transparent.png"}, 20, 1, 0, 0, 5, 530, 325, Block.WOOD, levelSix,0,true);
		levelSix.layBrick(2, new String []{"Resources/tractor.png","Resources/tractor.png","Resources/transparent.png"}, 15, 1, 0, 0, 5, 530, 270, Block.WOOD, levelSix,0,true);
		levelSix.layBrick(3, new String []{"Resources/tractor.png","Resources/tractor.png","Resources/transparent.png"}, 20, 1, 0, 0, 5, 530, 235, Block.WOOD, levelSix,0,true);
		levelSix.layBrick(4, new String []{"Resources/tractor.png","Resources/tractor.png","Resources/transparent.png"}, 10, 1, 0, 0, 5, 530, 205, Block.WOOD, levelSix,0,true);
		
		levelSeven = new Level(3, 7, 0);
		levelSeven.layBrick(0, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 50, 1, 0, 0, 9.8, 400, 295, Block.SUPERSTONE, levelSeven,0,false);
		levelSeven.layBrick(1, new String []{"Resources/Blocks/Stone/circle.png","Resources/Blocks/Stone/brokencircle.png","Resources/transparent.png"}, 50, 1, 0, 0, 9.8, 550, 295, Block.STONE, levelSeven,0,false);
		levelSeven.layBrick(2, new String []{"Resources/tractor.png","Resources/tractor.png","Resources/transparent.png"}, 10, 1, 0, 0, 5, 475, 235, Block.SUPERSTONE, levelSeven,0,true);
		
	}


	public static Level [] get() { //Adds each level to an array 
		return new Level [] {
			levelOne, 
			levelTwo, 
			levelThree,
			levelFour,
			levelFive,
			levelSix,
			levelSeven
		};
	}
}