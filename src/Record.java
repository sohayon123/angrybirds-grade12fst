//Name: Sasha Ohayon and Kyle Singer
//Name: Sasha Ohayon
//Program Name: Angry Chaims
//Date Edited: May 21st, 2017

import java.io.*;

public class Record implements Comparable<Record>, Serializable{ //Class written by Sasha Ohayon
	private String name;
	private int score;
	
	public Record () { //Empty constructor
		this("", 0);
	}
	
	public Record (String name, int score) { //Constructor given Name and Score
		this.name = name;
		this.score = score;
	}
	
	public String getName() { //Get method for name property
		return this.name;
	}
	
	public void setName(String name) {//Set method for name property
		this.name = name;
	}
	
	public int getScore() {//Get method for score property
		return this.score;
	}
	
	public void setScore(int score) { //Set method for score property
		this.score = score;
	}
	
	public String toString() { //To string method for record
		return this.name + " " + this.score;
	}
	
	public int compareTo(Record r) { //Method used for comparing records by store
		if (r.getScore() > this.getScore())
		return 1;
		else if (r.getScore() < this.getScore())
		return -1;
		else 
		return 0;
	}
}