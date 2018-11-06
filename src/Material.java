//Name: Sasha Ohayon and Kyle Singer
//Name: Sasha Ohayon
//Program Name: Angry Chaims
//Date Edited: May 21st, 2017

public class Material { //Class written by Sasha Ohayon
	double STRENGTH;
	double DENSITY;
	
	public Material (double STRENGTH, double DENSITY) { //Constructor using block strength and density
		this.STRENGTH = STRENGTH;
		this.DENSITY = DENSITY;
	}
	
	public double getDensity() { //Get method for density property
		return DENSITY;
	}
	
	public double getStrength() {//Get method for strength property
		return STRENGTH;
	}
}