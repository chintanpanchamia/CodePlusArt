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
		size(2000, 1600);
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
	  int fontSize = 96;
	  
	  PFont myFont = createFont("JosefinSans-Regular.ttf", fontSize);
	  textFont(myFont);
	  textSize(fontSize);
	  
	  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	  Date date = new Date();
	  String d = dateFormat.format(date);
	  
	  fill(0);
	  stroke(150);
	  
	  PVector pos = new PVector(width/2,height/2);
	  float textWid = textWidth(d);
	  float lower = pos.y + textDescent();		//lower y position including descent of text
	  float upper = lower - fontSize;			//upper calculated from lower point
	  float maxTextWid = 550 * fontSize / 64;

	  //textAlign(CENTER, CENTER);
	  //text(d,pos.x-textWid/2,pos.y+textDescent());
	  text(d, pos.x-maxTextWid/2, pos.y+textDescent());
	  
	  //System.out.format("%s, %s, %s, %s, %s", width/2, height/2, textWid, upper, lower);
	  //System.out.println();
	  /*line(pos.x-maxTextWid/2, lower, pos.x + maxTextWid/2, lower);
	  line(pos.x-maxTextWid/2, upper, pos.x + maxTextWid/2, upper);
	  line(pos.x - maxTextWid/2, upper, pos.x - maxTextWid/2, lower );
	  line(pos.x + maxTextWid/2 , upper, pos.x + maxTextWid/2, lower );*/
	  
	  fill(255,0,0);
	  ellipse(width/2, height/2,10,10);
	  
	  flock.run();
	}

	// Add a new boid into the System
	public void mouseDragged() 
	{
	  flock.addBoid(new Boid(mouseX,mouseY, this));
	}
}
