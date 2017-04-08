import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import processing.core.*;


public class Flocker extends PApplet {

	public static void main(String args[])
	{
		PApplet.main("Flocker");
	}
	
	
	Flock flock;

	public void settings() 
	{
		size(1000, 800);
	}
	
	public void setup() {
	  
	  flock = new Flock();
	  // Add an initial set of boids into the system
	  for (int i = 0; i < 200; i++) {
	    Boid b = new Boid(width/2,height/2, this);
	    flock.addBoid(b);
	  }
	}

	public void draw() 
	{
	  background(255);
	  int fontSize = 80;
	  
	  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	  Date date = new Date();
	  String d = dateFormat.format(date);
	  
	  PVector pos = new PVector(width/2,height/2);
	  // Instructions
	  fill(0);
	  //textSize(64);
	  float textWid = textWidth(d);

	  //lower y position including descent of text
	  float lower = pos.y + textDescent();
	  //upper calculated from lowewr point
	  float upper = lower - fontSize;
	  //textAlign(CENTER, CENTER);
	 
	  PFont myFont = createFont("JosefinSans-Regular.ttf", fontSize);
	  textFont(myFont);
	  text(d,width/2,height/2);
	  
	  stroke(150);
	  line(pos.x, lower, pos.x + textWid, lower);
	  
	  line(pos.x, upper, pos.x + textWid, upper );
	  
	  line(pos.x, upper, pos.x, lower );
	  
	  line(pos.x + textWid, upper, pos.x + textWid, lower );
	  
	  flock.run();
	}

	// Add a new boid into the System
	public void mouseDragged() 
	{
	  flock.addBoid(new Boid(mouseX,mouseY, this));
	}
}
