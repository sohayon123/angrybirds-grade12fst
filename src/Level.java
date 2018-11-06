//Name: Sasha Ohayon and Kyle Singer
//Program Name: Angry Chaims
//Date Edited: May 21st, 2017

import javafx.scene.Group;
import java.util.Arrays;
import java.util.ArrayList;

public class Level extends Group { //Class written by Sasha Ohayon
	Block [] blocks;
	int levelnum;
	int difficulty; //0 = Easy, 1 = Medium, 2 = Hard
	
	public Level(int blocknum, int levelnum, int difficulty) { //Constructor given block number, level number, and level difficulty
		this.blocks = new Block [blocknum];
		this.levelnum = levelnum;
		this.difficulty = difficulty;
	}
	
	public void layBrick(int brickindex, String path[], double radius, double velocity, double angle, double aX, double aY,  double x, double y, Material material, Level level, double angularV, boolean isTractor) { //Method initalizes new block and adds it to the level, which extends group, a parent node
		blocks[brickindex] = new Block(path, radius, velocity, angle, aX, aY, x, y, material, brickindex, level,  angularV, isTractor);
		blocks[brickindex].setCenterX(x);
		blocks[brickindex].setCenterY(y);
		blocks[brickindex].toFront();
	}
	
	public Block [] getBricks () { //Returns all bricks in a given level
		return blocks;
	}
	
	public void removeBrick(int index) { //Removes brick completely from a level
		ArrayList<Block> temp = new ArrayList<Block>(Arrays.asList(blocks));
		temp.remove(index);
		blocks = temp.toArray(new Block[temp.size()]);
	}
	
}