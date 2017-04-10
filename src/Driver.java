import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import processing.core.*;
public class Driver extends PApplet 
{
	Wolf wanderer;
	WolfPack myPack;
	boolean debug = true;

	public static void main(String args[])
	{
		PApplet.main("Driver");
	}

	public void settings()
	{
		size(1200, 700);
	}

	public void setup() 
	{
		myPack = new WolfPack(this);
	}

	public void draw() 
	{
		background(25);
		this.myPack.run();
		printTime();
	}

	void printTime()
	{
		//textMode(SHAPE);
		int fontSize = width * height / 18600;
		
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

		fill(255);
		stroke(150);

		PVector pos = new PVector(width/2,height/2);
		// float textWid = textWidth(d);
		// float lower = pos.y + textDescent();		//lower y position including descent of text
		// float upper = lower - fontSize;			//upper calculated from lower point

		// The multiplication factor is decided based on Max Possible TextWidth for a given fontSize
		// As we can't find maximum dynamically, we've scaled them based on observed values at fontSize 64 
		float maxTextWid = 1000 * fontSize / 64;
		float maxDateWid = 635 * fontSize / 64;
		float dateTimeWid = 900 * fontSize / 64;

		//textAlign(CENTER, CENTER);
		//text(d, pos.x - textWid/2, pos.y + textDescent());
		text(displayDate.toUpperCase(), pos.x - maxTextWid/2, pos.y + textDescent());
		text(displayTime, pos.x - maxTextWid/2 + maxDateWid,  pos.y + textDescent());
		text(timeMarker,  pos.x - maxTextWid/2 + dateTimeWid, pos.y + textDescent());
		
		//text("#GoWolfPack", , );

//		textMode(MODEL);
	}


	//CODE FOR CLOCK


	public void mousePressed() {
		debug = !debug;
	}
}
