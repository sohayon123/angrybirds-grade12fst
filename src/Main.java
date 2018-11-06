//Program Name: Angry Chaims
//Date Edited: May 21st, 2017

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.scene.paint.Stop;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.collections.ListChangeListener;
import javafx.stage.StageStyle;
import javafx.scene.text.TextAlignment;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.ArrayList;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.event.EventHandler;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.LongProperty;
import javafx.scene.Scene;
import javafx.scene.shape.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.Group;
import javafx.stage.WindowEvent;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.beans.binding.Bindings;
import javafx.scene.layout.Pane;
import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.lang.System.out;

public class Main extends Application { //Class written by Sasha Ohayon
	
	static Canvas canvas = new Canvas(600, 400); //Initalizing the canvas'
	static Canvas canvas2 = new Canvas(600, 400);
	static Canvas base = new Canvas(600, 400);
	static Group root = new Group(); //Main parent
	static Group group = new Group(root, base, canvas, canvas2); //Holds all canvas
	static Scene scene = new Scene(group);
	static GraphicsContext gc, gc2, gcb;
	static Projectile proj; //Projectile
	static Point2D mouse = new Point2D(150, 300), start, end; //Points for mouse and dragging
	static double angle; // Start angle
	static QuadCurve q; //Slingshot band
	static double controlX, controlY; //Control points for slingshot band
	static final double RESTITUTION = 0.7; //Energy lost coefficient
	static boolean FLYING = false, IN_GAME = true, DRAGGED = false; //booleans indicating projectile flying, in game, and mouse dragged
	static final ImageView sling = new ImageView(new Image("Resources/Slingshot.png", 40, 100, false, true));
	static final ImageView logo = new ImageView(new Image("Resources/logo.png", 500, 250, true, true));
	Levels levels = new Levels(); //Adds all levels 
	static int levelProgress = 0; //Current level program
	static Font font, smallFont, angrybirds; //Fonts
	static double par; //Score
	static Button scores, play, home, quit; //Buttons
	static HBox buttons; //Buttons parent
	static double currentLevelPrg = 0; //Shots per level
	static ArrayList<Record> data = new ArrayList<Record>(); //records
	static String name = System.getProperty("user.name");
	
	public void start(Stage stage) {
	
		stage.initStyle(StageStyle.DECORATED); //Decorating window
		stage.setWidth(600);
		stage.setHeight(400);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
		stage.setTitle("Angry Chaims");
		
		read(); //Reads scores from scores.txt
		startUp(); //Initializes all fonts, components, etc
		home(); //Loads home screen
		animate(); //Begins animation timer controlling all animation,collisions, and movement, 
		
		play.setOnAction(new EventHandler<ActionEvent>() { //Button listeners
			@Override
			public void handle(ActionEvent event) {
				play();
			}
		});
		
		scores.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				scoreMenu();
			}
		});
		
		quit.setOnAction(new EventHandler<ActionEvent>() { //Writes updated highscores to file upon quit button clicked
			@Override
			public void handle(ActionEvent event) {
				write();
				System.exit(0);
			}
		});
		
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {//Writes updated highscores to file upon window closed
			@Override
			public void handle(WindowEvent event) {
				write();
			}
		});

		home.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				home();
			}
		});
			
			
		scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() { //Mouse pressed
			@Override
			public void handle(MouseEvent event) {
				root.getChildren().remove(proj); //Removes projectiles already on screen						
				start = new Point2D(event.getX(), event.getY()); //Saves point
				gc.clearRect(0, 0, 600, 400); //Clears canvas 1
				FLYING = false;
				if (end == null)
				end = new Point2D(event.getX(), event.getY());
			}
		});
		
		scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				if (IN_GAME && !FLYING) {
					root.getChildren().remove(q); //Removes quadratic curve in order to prevent duplicates
					end = new Point2D(event.getX(), event.getY());	
								
					q = new QuadCurve(61, 260, end.getX(), end.getY(),  81, 263); //Initializes new curve
					q.setFill(Color.TRANSPARENT); //Decorates quadratic curve
					q.setStroke(Color.BLACK);
					q.setStrokeWidth(5);
					root.getChildren().add(q);
					q.toBack();
					DRAGGED = true; 
				}
			}
		});
		
		scene.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				if (IN_GAME && DRAGGED) {
					q.setControlX(61);// Changes stretch of band
					q.setControlY(260);
					par++; //removes from score
					currentLevelPrg++; //Adds to current shots per level count
					gc2.setFont(smallFont); 
					gc2.clearRect(0, 0, 600, 400);
					gc2.fillText("Your Score: " + Integer.toString(1000-(int)par), 20, 40);
						
					angle = atan2(end.getY()-260, end.getX() - 61);//Uses trig to find launch angle
					double energy = 1_100_000*sqrt((start.getX()-end.getX())*(start.getX()-end.getX()) + (start.getY()-end.getY())*((start.getY()-end.getY()))); //Energy using 1/2mv^2
					double velocity = 0.5*sqrt(2*energy/(400*50)); //Finds velocity and angular velocity, each from 50% of the total energy
					double angularV = 0.5*sqrt(2*energy/(400*50));
					proj = new Projectile(new String []{"/Resources/Chaim.png"}, 10, toDegrees(angle) > 180 ? velocity : -velocity, toDegrees(angle), 0, 9.8, 61, 260, Projectile.CHAIM, angularV);		//initializes projectile
								
					root.getChildren().add(proj);
					DRAGGED = false;
				}

 				}
			});
		}
	
		public void animate() {
			final LongProperty lastUpdateTime = new SimpleLongProperty(0);
			final AnimationTimer timer = new AnimationTimer() {
				@Override
				public void handle(long timestamp) {
					if (lastUpdateTime.get() > 0) {
						double elapsedTime = (timestamp - lastUpdateTime.get()); //Elapsed seconds
						updateWorld(); //Updates world
				}
				lastUpdateTime.set(timestamp);
			}

		};
		timer.start();
		}
		
		public void updateWorld() {			
			if (IN_GAME && proj != null && levels.get()[levelProgress] != null) {
				
			Point2D p = proj.getNewPoint();
			proj.setCenterX(p.getX()); //Relocates projectile
			proj.setCenterY(p.getY());
			
			if (round(proj.getVelocity()) == 0) { //Stops object from looking like it is rolling when stationary
				proj.setVelocityX(0);
				proj.setVelocityY(0);
			} else {
			
			if (proj.getVelocityX() > 0)
			proj.setRotate(floor(proj.getAngularVelocity()*proj.getTime())); //Rotates object based on angular vel
			else 
			proj.setRotate(floor(proj.getAngularVelocity()*proj.getTime()));
			}

			if (proj.getCenterX()+proj.getRadius() > 600 && proj.getVelocityX() > 0) { //Wall and floor collisions
				p = new Point2D(585, p.getY());
				proj.flipVelocityX();
				proj.flipAngularVelocityY();
				proj.setVelocityX(proj.getVelocityX()*RESTITUTION);
				proj.setVelocityY(proj.getVelocityY()*RESTITUTION);
			} else if (proj.getCenterX()-proj.getRadius() < 0 && proj.getVelocityX() < 0) {
				p = new Point2D(0, p.getY());
				proj.flipVelocityX();
				proj.setVelocityX(proj.getVelocityX()*RESTITUTION);
				proj.setVelocityY(proj.getVelocityY()*RESTITUTION);
			} else if (proj.getCenterY() + proj.getRadius() >= 345 && proj.getVelocityY() > 0) {
				p = new Point2D(p.getX(), 360);
				proj.flipVelocityY();
				proj.setVelocityX(proj.getVelocityX()*RESTITUTION);
				proj.setVelocityY(proj.getVelocityY()*RESTITUTION);
			} 
			
			Level currentLevel = levels.get()[levelProgress]; 
			
			for (int i = 0; i < currentLevel.getBricks().length; i++) {
				for (int x = 0; x < currentLevel.getBricks().length; x++) {
					blockMovement(currentLevel.getBricks()[i], currentLevel.getBricks()[x], currentLevel); //Moves each block from the level
				}	
					
				
				boolean tractors = false;
				for (int j = 0; j < currentLevel.getBricks().length; j++) { //Checks for remaining tractors, checks collisions
					if (currentLevel.getBricks()[j].isTractor()) {
						tractors = true;
						
						double deltaX = proj.getCenterX()-currentLevel.getBricks()[i].getCenterX();
						double deltaY = proj.getCenterY()-currentLevel.getBricks()[i].getCenterY();

						if (currentLevel.getBricks().length == 1 && projCollided(proj, currentLevel.getBricks()[j], deltaX, deltaY))
						tractors = false;
					}					
				}
				

				try {
					if (!tractors && IN_GAME) { //ended a level
						levels.get()[levelProgress].getChildren().removeAll(levels.get()[levelProgress].getBricks());
						levelProgress++;
						currentLevelPrg = 0;
						
						if (levelProgress >= levels.get().length) {
							levelProgress = levels.get().length;
							gc2.clearRect(0, 0, 600, 400);
							gc2.setFont(font);
						}
						

						
						StackPane stack = new StackPane(); //Level transition
						stack.relocate(0,0);
						
						Rectangle trans = new Rectangle (-1000, 0, 1000, 400);
						trans.setFill(Color.BLACK);
						Text text;
						if (levelProgress >= levels.get().length)
						text = new Text("");
						else
						text = new Text("Level " + (levelProgress+1));
						
						text.setFill(Color.WHITE);
						text.setTextAlignment(TextAlignment.LEFT);
						text.setFont(font);
						stack.toFront();
						stack.getChildren().addAll(trans,text);
						
						root.getChildren().remove(stack);
						root.getChildren().add(stack);

						TranslateTransition tt = new TranslateTransition(Duration.millis(2000), stack);
						tt.setFromX(-1000);
						tt.setToX(600);
						tt.play();
						
						
						root.getChildren().remove(proj);
						
						try {
					    levels.get()[levelProgress].getChildren().addAll(levels.get()[levelProgress].getBricks());
						} catch (ArrayIndexOutOfBoundsException e) { //Checks that the levelcount has surpassed the number of prewritten levels
							tractors = true;
							IN_GAME = false;
							buttons.setVisible(false);
							home();
							data.add(new Record(name, 1000-(int)par));
							Collections.sort(data);
						}
						
						tt.setOnFinished(new EventHandler<ActionEvent>(){ 
							@Override
							public void handle(ActionEvent event) {
								buttons.setVisible(true);
								if (levelProgress >= levels.get().length) 
								gc2.fillText("Your Score: " + Integer.toString(1000-(int)par), 40, 160);

							}
						});
					
					}
				} catch (Exception e) {
					levelProgress--;
					e.printStackTrace();
				}
				
				if (IN_GAME) { //Checks for more collisions and inflicts damage
				currentLevel = levels.get()[levelProgress]; 
				
				try {
				double deltaX = proj.getCenterX()-currentLevel.getBricks()[i].getCenterX();
				double deltaY = proj.getCenterY()-currentLevel.getBricks()[i].getCenterY();
				
				double damage = abs(currentLevel.getBricks()[i].getVelocity()*currentLevel.getBricks()[i].getVelocity()*currentLevel.getBricks()[i].getMass()*0.5);
				
				if (damage >= 50000)
				damage = 50000;
				
								
				if (projCollided(proj, currentLevel.getBricks()[i], deltaX, deltaY) && root.getChildren().contains(proj)) {
					if (damage < currentLevel.getBricks()[i].getRemainingHP())
					bounce(proj, currentLevel.getBricks()[i], deltaX, deltaY);
					
					currentLevel.getBricks()[i].hit(damage);
					
					if (currentLevel.getBricks()[i].getRemainingHP() <= 0) {
						root.getChildren().remove(currentLevel.getBricks()[i]);
						currentLevel.removeBrick(i);
					}
										
					}
				} catch (Exception e) {}
				}
			}
			}
		}	
		
		public boolean projCollided(Projectile p1, Projectile p2, double deltaX, double deltaY) { //Checks for collisions between any two objects 
			double sumRadii = p1.getRadius() + p2.getRadius();
			double distance = sqrt(deltaX * deltaX + deltaY * deltaY);

			if (distance <= sumRadii) {
				if (deltaX * (p2.getVelocityX() - p1.getVelocityX()) + deltaY * (p2.getVelocityY() - p1.getVelocityY()) > 0) {
					return true;
				}
			}
			return false;
		}
		
		public void blockMovement(Block b, Block b2, Level level) { //Responsible for block movement
			if (currentLevelPrg > 0) {
				boolean ROOM_COLLISION = false;
				Point2D p = b.getModifiedNewPoint(b.getRate()/b.getLevel().getBricks().length);
				b.setCenterX(p.getX());
				b.setCenterY(p.getY());
				
				if (b.getCenterX()+b.getRadius() > 600 && b.getVelocityX() > 0) { //Wall and floor collisions 
					ROOM_COLLISION = true;
					p = new Point2D(585, p.getY());
					b.flipVelocityX();
					b.setVelocityX(b.getVelocityX()*RESTITUTION);
					b.setVelocityY(b.getVelocityY()*RESTITUTION);
				} else if (b.getCenterX()-b.getRadius() < 0 && b.getVelocityX() < 0) {
					ROOM_COLLISION = true;
					p = new Point2D(0, p.getY());
					b.flipVelocityX();
					b.setVelocityX(b.getVelocityX()*RESTITUTION);
					b.setVelocityY(b.getVelocityY()*RESTITUTION);
				} else if (b.getCenterY() + b.getRadius() >= 345 && b.getVelocityY() > 0) {
					ROOM_COLLISION = true;
					p = new Point2D(p.getX(), 360);
					b.flipVelocityY();
					b.setVelocityX(b.getVelocityX()*RESTITUTION);
					b.setVelocityY(b.getVelocityY()*RESTITUTION);
				} 
				
				double deltaX = b.getCenterX()-b2.getCenterX();
				double deltaY = b.getCenterY()-b2.getCenterY();
										
				if (projCollided(b, b2, deltaX, deltaY) && root.getChildren().contains(proj)) { //Checks for inter-block collisions
					bounce(b, b2, deltaX, deltaY);
				}
			}
		
		}
				
		public void bounce (Projectile p1, Projectile p2, double deltaX, double deltaY) { //Bounces balls using convseration of momentum, energy, and vectors
						
			//Conservation of momentum
			double sumRadii = p1.getRadius() + p2.getRadius();
			double distance = sqrt(deltaX * deltaX + deltaY * deltaY);
			double cosD = deltaX / distance ;
			double sinD = deltaY / distance ;
			
			double v1Parallel = p1.getVelocityX() * cosD + p1.getVelocityY() * sinD; //Decomposes initial velocities parallel to contact vector
			double v2Parallel = p2.getVelocityX() * cosD + p2.getVelocityY() * sinD; 
					
			double massSum = p1.getMass() + p2.getMass();
			double massDiff = p1.getMass() - p2.getMass();

			double v1PerpX = p1.getVelocityX() - v1Parallel * cosD; //Decomposes initial velocities orthogonal to contact vector
			double v1PerpY = p1.getVelocityY() - v1Parallel * sinD; 
			double v2PerpX = p2.getVelocityX() - v2Parallel * cosD; 
			double v2PerpY = p2.getVelocityY() - v2Parallel * sinD; 
			
			double cv1 = ((2*p2.getMass()*v2Parallel) + (v1Parallel * massDiff))/massSum; //composed, momentum conserved, new velocity
			double cv2 = ((2*p1.getMass()*v1Parallel) - (v2Parallel * massDiff))/massSum;
						
			if (p1 instanceof Block && p2 instanceof Block) { //Only add damping when collision is not block-block
				p1.setVelocityX(cv1 * cosD + v1PerpX);
				p1.setVelocityY(cv1 * sinD + v1PerpY);
				p2.setVelocityX(cv2 * cosD + v2PerpX);
				p2.setVelocityY(cv2 * sinD + v2PerpY);
			} else {									
				p1.setVelocityX(RESTITUTION*(cv1 * cosD + v1PerpX));
				p1.setVelocityY(RESTITUTION*(cv1 * sinD + v1PerpY));
				p2.setVelocityX(RESTITUTION*(cv2 * cosD + v2PerpX));
				p2.setVelocityY(RESTITUTION*(cv2 * sinD + v2PerpY));
			}
			
			//Conservation of angular momentum
			
			v1Parallel = p1.getAngularVelocityX() * cosD + p1.getAngularVelocityY() * sinD;
			v2Parallel = p2.getAngularVelocityX() * cosD + p2.getAngularVelocityY() * sinD; 
					
			v1PerpX = p1.getAngularVelocityX() - v1Parallel * cosD; 
			v1PerpY = p1.getAngularVelocityY() - v1Parallel * sinD; 
			v2PerpX = p2.getAngularVelocityX() - v2Parallel * cosD; 
			v2PerpY = p2.getAngularVelocityY() - v2Parallel * sinD; 
			
			cv1 = ((2*p2.getMass()*v2Parallel) + (v1Parallel * massDiff))/massSum;
			cv2 = ((2*p1.getMass()*v1Parallel) - (v2Parallel * massDiff))/massSum;
			
			if (p1 instanceof Block && p2 instanceof Block) {
				p1.setAngularVelocityX(cv1 * cosD + v1PerpX);
				p1.setAngularVelocityY(cv1 * sinD + v1PerpY);
				p2.setAngularVelocityX(cv2 * cosD + v2PerpX);
				p2.setAngularVelocityY(cv2 * sinD + v2PerpY);
			} else {									
				p1.setAngularVelocityX(RESTITUTION*(cv1 * cosD + v1PerpX));
				p1.setAngularVelocityY(RESTITUTION*(cv1 * sinD + v1PerpY));
				p2.setAngularVelocityX(RESTITUTION*(cv2 * cosD + v2PerpX));
				p2.setAngularVelocityY(RESTITUTION*(cv2 * sinD + v2PerpY));
			}
						
		}
		 
	public void home() { //Loads home screen
		IN_GAME = false;
		root.getChildren().removeAll(levels.get()); //removes level
		root.getChildren().removeAll(proj, home); //removes proj and home button to ensure no duplicates of home button
		root.getChildren().add(buttons); //Adds buttons
		if (levelProgress < levels.get().length) {
			root.getChildren().add(logo);
			logo.relocate(50, 25);
			gc2.clearRect(0, 0, 600, 400);
			levels.get()[levelProgress].getChildren().removeAll(levels.get()[levelProgress].getBricks());
		} else {
			buttons.getChildren().remove(play);
			buttons.setLayoutX(170);
		}


				
	}
	
	public void startUp() { //Initalizes various variables
		par = 0;
		levelProgress = 0;
		IN_GAME = false;
		
		ImageView[] clouds = {new ImageView(new Image("/Resources/clouds.png")), new ImageView(new Image("/Resources/clouds.png"))};
		
		root.getChildren().add(scrolling(clouds, 600));
		
		InputStream input = getClass().getResourceAsStream("/Resources/AvantGarde.ttf"); //Fonts

		try {
			font = Font.loadFont(input, 60);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		input = getClass().getResourceAsStream("/Resources/AvantGarde.ttf");
		
		try {
			smallFont = Font.loadFont(input, 20);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		input = getClass().getResourceAsStream("/Resources/angrybirds.ttf");
		
		try {
			angrybirds = Font.loadFont(input, 35);
		} catch (Exception e) {
			e.printStackTrace();
		}

		gc = canvas.getGraphicsContext2D(); //Initalizes graphics contexts for each canvas
		gc2 = canvas2.getGraphicsContext2D();
		gcb = base.getGraphicsContext2D();
	
		gc.setStroke(Color.BLACK);
		gc2.setFont(smallFont);
		
		root.getChildren().remove(sling);
		root.getChildren().add(sling);	//Adds slingshot
		sling.relocate(50, 243.5);
		
		gcb.clearRect(0, 0, 600, 400);
		gcb.drawImage(new Image("Resources/bg.png"), 0, 0, 600, 435);//Adds background

		root.toFront();
		sling.toFront();
		
		levels = new Levels();
		levels.initialize();
		root.getChildren().removeAll(levels.get());
		root.getChildren().addAll(levels.get());
		
		q = new QuadCurve(61, 260, 61, 260,  81, 263);//adds quadcurve, initaially unstretched
		q.setFill(Color.TRANSPARENT);
		q.setStroke(Color.BLACK);
		q.setStrokeWidth(5);
		root.getChildren().add(q);
		q.toBack();
		
		gc2.clearRect(0, 0, 600, 400);
		
		scores = new Button("Scores");//initalizes buttons
		play = new Button("Play!");
		home = new Button("Home");
		quit = new Button("Quit");
		buttons = new HBox(play, scores, quit);
		
		play.setStyle("-fx-base: #FFBA34;"); //button color
		scores.setStyle("-fx-base: #FFBA34;");
		quit.setStyle("-fx-base: #FFBA34;");
		home.setStyle("-fx-base: #FFBA34;");
		
		play.setPrefWidth(150);
		play.setPrefHeight(75); //button sizes
		scores.setPrefWidth(150);
		scores.setPrefHeight(75);
		
		quit.setPrefWidth(150);
		quit.setPrefHeight(75);
		home.setPrefWidth(150);
		home.setPrefHeight(75);
		
		play.setFont(angrybirds); //button font
		scores.setFont(angrybirds);
		quit.setFont(angrybirds);
		home.setFont(angrybirds);
				
		buttons.setSpacing(10); //button locations
		buttons.setLayoutX(110);
		buttons.setLayoutY(260);
					
	}
	
	public void play () {
		
		root.getChildren().remove(logo); //exits home screen and continues game
		root.getChildren().add(home);
		home.setLayoutX(440);
		home.setLayoutY(10);
		
		root.getChildren().removeAll(levels.get());
		root.getChildren().addAll(levels.get());
		levels.get()[levelProgress].getChildren().removeAll(levels.get()[levelProgress].getBricks());
		levels.get()[levelProgress].getChildren().addAll(levels.get()[levelProgress].getBricks());
		gc2.fillText("Your Score: " + (int)(1000-par), 20, 40);
		root.getChildren().remove(buttons);
		IN_GAME = true;
	}
	
	public void read() {
		try { //Reads records as arraylist from file
			File file = new File("scores.txt");
			if (file.length() != 0) {
				
				if (file.length() != 0) {
					ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
					data = (ArrayList<Record>)ois.readObject();
				}
				
				Collections.sort(data);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void write() {
		try { //Writes data to scores.txt

			File file = new File("scores.txt");
			
			Collections.sort(data);
		
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("scores.txt")));
			oos.writeObject(data);
			oos.flush();
			oos.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void scoreMenu() {
		
		double size;
		Pane scoreMenu = new Pane();
		Pane scoreDisplays = new Pane();
		Pane namePane = new Pane();
		Pane scorePane = new Pane();
		Text[] names = new Text[10];
		Text[] scoreText = new Text[10];
		Button closeButton = new Button();
		ImageView scoreFG = new ImageView(new Image("Resources/ScoreFG.png"));
		ImageView scoreBG = new ImageView(new Image("Resources/ScoreBG.png"));
		ImageView[] scoreDisplay = new ImageView[10];
		
		closeButton.relocate(14, 14);
		closeButton.graphicProperty().bind(Bindings.when(closeButton.hoverProperty())
			.then(new ImageView(new Image("/Resources/CloseButton Hover.png")))
			.otherwise(new ImageView(new Image("/Resources/CloseButton.png"))));
		closeButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-background-radius: 5em;	-fx-min-width: 3px; -fx-min-height: 3px; -fx-max-width: 3px; -fx-max-height: 3px;");
		
		closeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				root.getChildren().remove(scoreMenu);
			}
		});
		
		scoreMenu.getChildren().add(scoreBG);
		root.getChildren().add(scoreMenu);
		root.requestFocus();

		if (data.size() < 10)
		size = data.size();
		else size = 10;
		
		for (int i = 0; i < size; i++) {
			
			double height = 80 + (i * 210);
			double nameHeight = 80 + (i * 210);
			double scoreHeight = 190 + (i * 210);

			scoreDisplay[i] =  new ImageView(new Image("Resources/Score Display.png"));
			scoreDisplays.getChildren().add(scoreDisplay[i]);
			scoreDisplay[i].relocate(110, height);
			
			scoreText[i] = new Text(String.valueOf(data.get(i).getScore()));
			scoreText[i].setStyle("-fx-stroke: black; -fx-stroke-width: 2px;");
			scoreText[i].setFill(Color.WHITE);
			scoreText[i].setFont(Font.loadFont("file:Resources/angrybirds.ttf", 40));
			
			scorePane.getChildren().add(scoreText[i]);
			scoreText[i].relocate((600 - scoreText[i].getBoundsInParent().getWidth()) / 2, scoreHeight);
			
			names[i] = new Text(String.valueOf("(" + (i + 1) + ") " + data.get(i).getName()));
			names[i].setStyle("-fx-stroke: black; -fx-stroke-width: 2px;");
			names[i].setFill(Color.WHITE);
			names[i].setFont(Font.loadFont("file:Resources/angrybirds.ttf", 40));
			
			namePane.getChildren().add(names[i]);
			names[i].relocate((600 - names[i].getBoundsInParent().getWidth()) / 2, nameHeight);
		}	
		
		scoreMenu.getChildren().addAll(scoreDisplays, namePane, scorePane, scoreFG, closeButton);

		scoreMenu.setOnScroll(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {

				double newY = scoreDisplays.getTranslateY() + event.getDeltaY();

				if (newY < 0 && newY > -1 * (scoreDisplays.getBoundsInParent().getHeight()- 350)) {
					scoreDisplays.setTranslateY(newY);
					namePane.setTranslateY(newY);
					scorePane.setTranslateY(newY);
				}
			}
		});
	}
	
	 public Group scrolling(final ImageView[] content, final int width) { //scrolling for clouds
		
		Group group = new Group(); //Creates new parent with content for scrolling
		Timeline bgAnimate = new Timeline(); //Timeline for animating the clouds
		
		group.getChildren().addAll(content);
		bgAnimate.setCycleCount(Timeline.INDEFINITE);
		content[1].setTranslateX(600);//Takes second copy of picture that needs to be scrolled and places it at the edge of the window
		
		EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
		 public void handle(ActionEvent event) {
			for (ImageView node: content) {

			 node.setTranslateX(node.getTranslateX() - 1.0);

			 if (node.getLayoutX() + node.getTranslateX() + node.getBoundsInLocal().getWidth() <= 0)
				node.setTranslateX(width - node.getLayoutX()); //Moves clouds back to opposite side when they reach the end

			}
		 }
		};
		KeyFrame keyFrame = new KeyFrame(new Duration(40), onFinished);//Creates new 15 second keyframe with the eventhandler above
		bgAnimate.getKeyFrames().add(keyFrame); //Adds the keyframe with the eventhandler to a timeline that will run over and over
		bgAnimate.play(); //Plays timeline
		
		return group;
	 }

	public static void main(String[]args) { //Runs program, essentially the main of a JavaFX program
		launch(args);
	}
	
}