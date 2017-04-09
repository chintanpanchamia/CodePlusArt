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
		size(1000, 800);
	}
	
	public void setup() 
	{
	  //wanderer = new Wolf(width/2, height/2, this);
	  myPack = new WolfPack();
	}

	public void draw() 
	{
	  background(255);
	  
	  if(frameCount % 3 == 0)
		  wanderer.wander();
	  wanderer.run();
	  
	  //CODE FOR CLOCK
	  
	}

	public void mousePressed() {
	  debug = !debug;
	}
}
