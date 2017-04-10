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
		size(5760/2, 2304/2);
		//size(2880, 2440);
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
		//int fontSize = width * height / 51840;
		int fontSize = 128;
		
		PFont myFont = createFont("JosefinSans-Regular.ttf", fontSize);
		textFont(myFont);
		textSize(fontSize);

		// Source: http://stackoverflow.com/questions/4216745/java-string-to-date-conversion/
		DateFormat dateFormat = new SimpleDateFormat("E MMMM dd, yyyy     hh:mm:ss a", Locale.ENGLISH);
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
		float maxTextWid = 1050 * fontSize / 64;
		float maxDateWid = 685 * fontSize / 64;
		float dateTimeWid = 935 * fontSize / 64;

		//textAlign(CENTER, CENTER);
		//text(d, pos.x - textWid/2, pos.y + textDescent());
		text(displayDate.toUpperCase(), pos.x - maxTextWid/2, pos.y + textDescent());
		text(displayTime, pos.x - maxTextWid/2 + maxDateWid,  pos.y + textDescent());
		text(timeMarker,  pos.x - maxTextWid/2 + dateTimeWid, pos.y + textDescent());
		
		fontSize /= 1.5;
		textSize(fontSize);
		fill(204,0,0);
		
		myFont = createFont("JosefinSans-BoldItalic.ttf", fontSize);
		textFont(myFont);
		String displayMessage1 = "#BecomeTheAlpha";
		text(displayMessage1, pos.x - maxTextWid/2 + textWidth(displayDate)/2.5f,
				pos.y + fontSize*1.5f + textDescent());
		
		myFont = createFont("JosefinSans-Italic.ttf", fontSize);
		textFont(myFont);
		String displayMessage2 = "#GoWolfPack";
		text(displayMessage2, pos.x + maxDateWid/2 - textWidth(displayMessage2)/1.8f,
				pos.y + fontSize*1.5f + textDescent());

//		textMode(MODEL);
	}


	//CODE FOR CLOCK


	public void mousePressed() {
		debug = !debug;
	}
}
