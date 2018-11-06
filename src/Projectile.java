//Name: Sasha Ohayon and Kyle Singer
//Name: Sasha Ohayon
//Program Name: Angry Chaims
//Date Edited: May 21st, 2017

import static java.lang.Math.*;

import javafx.scene.image.ImageView;
import javafx.geometry.Point2D;
import javafx.scene.paint.*;
import javafx.scene.image.Image;
import javafx.scene.shape.*;
import java.util.concurrent.Callable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.SimpleDoubleProperty;

public class Projectile extends Circle { //Class written by Sasha Ohayon
	
		public static final Material CHAIM = new Material(1_000_000_000, 50); //Constant material with essentially no breaking point and same density as ICE
		private final DoubleProperty vX; //velocity in the X direction
		private final DoubleProperty vY; //velocity in the Y direction
		private final DoubleProperty velocity; //velocity
		private final DoubleProperty aVx;//angular velocity in the X direction
		private final DoubleProperty aVy;//angular velocity in the Y direction
		private final DoubleProperty angularV;//angular velocity 
		private final double mass, frameRate = 0.1, angle, aX, aY; //mass, delta time, launch angle, acceleration x and y
		private double x, y, time = 0; //Initial position, total time
		private String[] path; //filapath(s)
							
		public Projectile(String [] path, double radius, double velocity, double angle, double aX, double aY, double x, double y, Material material, double angularV) { //Standard Constructor 
			
			this.setFill(new ImagePattern(new Image(path[0])));
			this.vX = new SimpleDoubleProperty(this, "vX", velocity*cos(angle*(PI/180.0)));
			this.vY = new SimpleDoubleProperty(this, "vY", velocity*sin(angle*(PI/180.0)));
			this.velocity = new SimpleDoubleProperty(this, "velocity", velocity);
			this.aVx = new SimpleDoubleProperty(this, "vX", angularV*cos(angle*(PI/180.0)));
			this.aVy = new SimpleDoubleProperty(this, "vY", angularV*sin(angle*(PI/180.0)));
			this.angularV = new SimpleDoubleProperty(this, "angularV", angularV);
			this.angle = angle;
			setRadius(radius);
			this.mass = material.getDensity()*PI*getRadius()*getRadius();
			setCenterX(x);
			setCenterY(y);
			this.path = path;
			this.aX = aX;
			this.aY = aY;
			this.x = x;
			this.y = y;
			
			this.angularV.bind(Bindings.createDoubleBinding(new Callable<Double>() { //Binding X/Y angular velocity to the composed angular velocity
				
				@Override
				public Double call() throws Exception {
					final double angVX = getAngularVelocityX();
					final double angVY = getAngularVelocityY();
					return sqrt(angVX * angVX + angVY * angVY);
				}
			}, this.aVy, this.aVy));
								
			this.velocity.bind(Bindings.createDoubleBinding(new Callable<Double>() { //Binding X/Y velocity to the composed velocity

				
				@Override
				public Double call() throws Exception {
					final double vX = getVelocityX();
					final double vY = getVelocityY();
					return sqrt(vX * vX + vY * vY);
				}
			}, this.vX, this.vY));
		}
				
		public final Point2D getNewPoint() { //Retrieves new point on the quadratic curve by adding the framerate to the passed time
				this.time += this.frameRate;
				x += this.getVelocityX()*this.frameRate;
				y += this.getVelocityY()*this.frameRate;
				setCenterX(x);
				setCenterY(y);

				this.vX.set(this.getVelocityX()+this.aX*this.frameRate);
				this.vY.set(this.getVelocityY()+this.aY*this.frameRate);
			

			 return new Point2D(x, y);
		}
		
		public final Point2D getModifiedNewPoint(double frameRate) { //Specifically used for blocks, new framerate will allow compensate for whenever blocks are having collisions checked
			this.time += frameRate;
			x += this.getVelocityX()*frameRate;
			y += this.getVelocityY()*frameRate;
		 	setCenterX(x);
			setCenterY(y);

		 	this.vX.set(this.getVelocityX()+this.aX*frameRate);
			this.vY.set(this.getVelocityY()+this.aY*frameRate);
			
			return new Point2D(x, y);

		}
		
		public final void flipAngularVelocityX() { //Changes direction of spin in X
			this.aVx.set(this.getAngularVelocityX()*-1);
		}
		
		public final void flipAngularVelocityY() {//Changes direction of spin in Y
			this.aVy.set(this.getAngularVelocityY()*-1);
		}
		
		public final double getAngularVelocityX() {//returns angular velocity X
			return this.aVx.get();
		}
		
		public final double getAngularVelocityY() {//returns angular velocity Y
			return this.aVy.get();
		}
		
		public final double getAngularVelocity() {//returns composed angular velocity
			return this.angularV.get();
		}
		
		public final void setAngularVelocityX(double vel) {//sets angular velocity X
			this.aVx.set(vel);
		}
		
		public final void setAngularVelocityY(double vel) {//sets angular velocity Y
			this.aVy.set(vel);
		}
	
		public final void flipVelocityX() { //flips X velocity
			this.vX.set(this.getVelocityX()*-1);
		}
		
		public final void flipVelocityY() { //flips Y velocity
			this.vY.set(this.getVelocityY()*-1);
		}
		
		public final double getMass() {//returns mass
			return mass;
		}

		public final double getVelocityX() {//returns X velocity
			return this.vX.get();
		}

		public final void setVelocityX(double vX) { //sets X velocity
			this.vX.set(vX);
		}
	
		public final DoubleProperty velocityXProperty() { //returns the double property for X velocity
			return this.vX;
		}

		public final double getVelocityY() { //returns Y velocity
			return this.vY.get();
		}

		public final void setVelocityY(double vY) {//sets Y velocity
			this.vY.set(vY);
		}

		public final DoubleProperty velocityYProperty() {  //returns the double property for Y velocity
			return this.vY;
		}
		
		public final double getVelocity() { //returns velocity
			return this.velocity.get();
		}
		
		public final DoubleProperty speedProperty() {//returns the double property for velocity
			return this.velocity;
		}
		
		public final double getTime() { //returns passed time
			return this.time;
		}
		
		public final double getRate() { //returns framerate
			return this.frameRate;
		}
		
	}
	
