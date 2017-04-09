import processing.core.*;
public class Driver extends PApplet 
{
	Wolf wanderer;
	boolean debug = true;
	
	public static void main(String args[])
	{
		PApplet.main("Driver");
	}
	
	public void settings()
	{
		size(1000, 800);
	}
	
	public void setup() 
	{
	  wanderer = new Wolf(width/2, height/2, this);
	}

	public void draw() {
	  background(255);
	  if(frameCount % 5 == 0)
		  wanderer.wander();
	  wanderer.run();
	}

	public void mousePressed() {
	  debug = !debug;
	}
}
