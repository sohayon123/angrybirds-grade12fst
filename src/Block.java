//Program Name: Angry Chaims
//Date Edited: May 21st, 2017

import javafx.scene.image.ImageView;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import java.util.concurrent.Callable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.*;
import javafx.scene.paint.*;

import static java.lang.Math.*;

public class Block extends Projectile { //Class written by Sasha Ohayon and Kyle Singer, parent class written by Sasha Ohayon
	
	public static final Material ICE = new Material(100_000, 10); //Three materials declared with constant density and strength
	public static final Material WOOD = new Material(175_000, 25);
	public static final Material STONE = new Material(250_000, 100);
	public static final Material SUPERSTONE = new Material(1_000_000_000, 100);
	private final double MASS; //mass of block
	private double aX; //acceleration in x-direction
	private double aY; //acceleration in y-direction
	private DoubleProperty velocity; //velocity
	private DoubleProperty vX; //velocity in x-direction
	private DoubleProperty vY; //velocity in y-direction 
	private double time; //time 
	private final double DENSITY; //density of block
	private final double STRENGTH; //strength of the block
	private String [] path; //the file path
	private double angle; //the angle of travel
	private double x, y; //Inital position
	private int index; //Index of the block in terms of the level
	private double strength; //remaining HP
	private Level level; //Respective level
	private double angularV; //Angular velocity
	private boolean isTractor; 
	
	public Block(String [] path, double radius, double velocity, double angle, double aX, double aY,  double x, double y, Material material, int index, Level level, double angularV, boolean isTractor) { //Constructor given all properties
		     super(path, radius, velocity, angle, aX, aY, x, y, material, angularV);
			 this.level = level;
			 this.setFill(new ImagePattern(new Image(path[0])));
		     this.setRadius(radius);
			 this.aX = aX;
			 this.aY = aY;
			 this.isTractor = isTractor;
			 this.velocity = new SimpleDoubleProperty(this, "velocity", velocity);
		 	 this.vX = new SimpleDoubleProperty(this, "vX", velocity*cos(angle*(PI/180.0)));
			 this.vY = new SimpleDoubleProperty(this, "vY", velocity*sin(angle*(PI/180.0)));
			 this.DENSITY = material.getDensity();
			 this.STRENGTH = material.getStrength();
			 this.MASS = DENSITY*PI*getRadius()*getRadius();
			 this.path = path;
		 	 this.strength = STRENGTH;
			 this.angle = angle;
			 setCenterX(x);
			 setCenterY(y);
		     this.x = x;
			 this.y = y;
			 this.index = index;
			
			 this.velocity.bind(Bindings.createDoubleBinding(new Callable<Double>() { //Bindings between the x and y components of the velocity and the composed velocity
				 @Override
				 public Double call() throws Exception {
					 final double vX = getVelocityX();
					 final double vY = getVelocityY();
					 return sqrt(vX * vX + vY * vY);
				 }
			 }, this.vX, this.vY)); 
			 
		 }
		
		public Level getLevel() { //Get method for respective level
			return this.level;
		}
		
		public boolean isTractor() { //Gets isTractor property
			return isTractor;
		}
		
		public void hit(double damage){ //Subtracts specific amount of health from block
			strength-=damage;
			if (strength/STRENGTH <= 0.5 && strength/STRENGTH > 0) {
				this.setFill(new ImagePattern(new Image(path[1])));
			} else if (strength/STRENGTH <= 0) {
				this.setFill(new ImagePattern(new Image(path[2])));

			}
		}
		
		public double getRemainingHP(){ //Returns remaining health
			return strength;
		}
		
		public int getIndex() { //Returns index within level
			return this.index;
		}
		 
		public void setAngle(double angle) { //Changed launch angle
			this.angle = angle;	
		}
	
		public double getaX(){ //Returns X acceleration
		 	return this.aX;
		}
		 
		public double getaY(){ //Returns Y acceleration
		 	return this.aY;
		}
		 
	 	public double getDensity(){ //Returns density
	     	return this.DENSITY;
		}
		 
		public double getStrength(){ //Returns max HP
		 	return this.STRENGTH;
		}
	 
}//end of class