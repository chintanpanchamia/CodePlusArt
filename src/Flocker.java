import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import processing.core.*;


public class Flocker extends PApplet {

	public static void main(String args[])
	{
		PApplet.main("Flocker");
	}
	
	
	Flock flock;

	public void settings() 
	{
		//size(2000, 1600);
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
	  int fontSize = 96;
	  
	  PFont myFont = createFont("JosefinSans-Regular.ttf", fontSize);
	  textFont(myFont);
	  textSize(fontSize);
	  
	  // Source: http://stackoverflow.com/questions/4216745/java-string-to-date-conversion/
	  DateFormat dateFormat = new SimpleDateFormat("E MMMM dd, yyyy    hh:mm:ss a", Locale.ENGLISH);
	  DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("E  MMMM dd, yyyy");
	  DateTimeFormatter timeformat = DateTimeFormatter.ofPattern("hh:mm:ss a");
	  
	  Date date = new Date();
	  LocalDateTime localDate = LocalDateTime.now();
	  
	  String d = dateFormat.format(date);
	  String displayDate = dateformat.format(localDate);
	  String displayTime = timeformat.format(localDate);
	  displayTime = displayTime.substring(0, displayTime.length()-2);
	  String timeMarker = d.substring(d.length()-2);
	  
	  fill(0);
	  stroke(150);
	  
	  PVector pos = new PVector(width/2,height/2);
	  // float textWid = textWidth(d);
	  // float lower = pos.y + textDescent();		//lower y position including descent of text
	  // float upper = lower - fontSize;			//upper calculated from lower point
	  
	  // The multiplication factor is decided based on Max POssible TextWidth for a given fontSize
	  // As we can't find maximum dynamically, we've scaled them based on observed values at fontSize 64 
	  float maxTextWid = 1000 * fontSize / 64;
	  float maxDateWid = 635 * fontSize / 64;
	  float dateTimeWid = 900 * fontSize / 64;

	  //textAlign(CENTER, CENTER);
	  //text(d, pos.x - textWid/2, pos.y + textDescent());
	  text(displayDate, pos.x - maxTextWid/2, 				pos.y + textDescent());
	  text(displayTime, pos.x - maxTextWid/2 + maxDateWid,  pos.y + textDescent());
	  text(timeMarker,  pos.x - maxTextWid/2 + dateTimeWid, pos.y + textDescent());
	  
	  //System.out.format("%s, %s, %s, %s, %s", width/2, height/2, textWid, upper, lower);
	  //System.out.println();
	  /*line(pos.x-maxTextWid/2, lower, pos.x + maxTextWid/2, lower);
	  line(pos.x-maxTextWid/2, upper, pos.x + maxTextWid/2, upper);
	  line(pos.x - maxTextWid/2, upper, pos.x - maxTextWid/2, lower );
	  line(pos.x + maxTextWid/2 , upper, pos.x + maxTextWid/2, lower );*/
	  

	  // fill(255,0,0);
	  // ellipse(width/2, height/2,10,10);

	  
	  flock.run();
	}

	// Add a new boid into the System
	public void mouseDragged() 
	{
	  flock.addBoid(new Boid(mouseX,mouseY, this));
	}
}
