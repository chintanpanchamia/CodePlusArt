import processing.core.*;
public class Wolf 
{

	  PVector position;
	  PVector velocity;
	  PVector acceleration;
	  float r;
	  float wandertheta;
	  float maxforce;    // Maximum steering force
	  float maxspeed;    // Maximum speed
	  int gender;
	  
	  PApplet pr;
	  
	  Wolf(float x, float y, PApplet parent) 
	  {
		acceleration = new PVector(0,0);
	    velocity = new PVector(0,0);
	    position = new PVector(x,y);
	    r = 20;
	    wandertheta = 0;
	    maxspeed = 1;
	    maxforce = 0.01f;
	    pr = parent;
	    
	    gender = pr.random(-1, 1) > 0 ? 1:0;
	  }
	
	  void run() 
	  {
	    update();
	    borders();
	    display();
	  }
	
	  // Method to update position
	  void update() 
	  {
	    // Update velocity
	    velocity.add(acceleration);
	    // Limit speed
	    velocity.limit(maxspeed);
	    position.add(velocity);
	    // Reset accelertion to 0 each cycle
	    acceleration.mult(0);
	  }
	
	  void wander() 
	  {
	    float wanderR = 100;         // Radius for our "wander circle"
	    float wanderD = 200;         // Distance for our "wander circle"
	    float change = 0.55f;
	    wandertheta += pr.random(-change,change);     // Randomly change wander theta
	
	    // Now we have to calculate the new position to steer towards on the wander circle
	    PVector circlepos = velocity.get();    // Start with velocity
	    circlepos.normalize();            // Normalize to get heading
	    circlepos.mult(wanderD);          // Multiply by distance
	    circlepos.add(position);               // Make it relative to boid's position
	
	    float h = velocity.heading2D();        // We need to know the heading to offset wandertheta
	
	    PVector circleOffSet = new PVector(wanderR*pr.cos(wandertheta+h),wanderR*pr.sin(wandertheta+h));
	    PVector target = PVector.add(circlepos,circleOffSet);
	    seek(target);
	
	  }
	
	  void applyForce(PVector force) 
	  {
	    // We could add mass here if we want A = F / M
	    acceleration.add(force);
	  }
	
	
	  // A method that calculates and applies a steering force towards a target
	  // STEER = DESIRED MINUS VELOCITY
	  void seek(PVector target) 
	  {
	    PVector desired = PVector.sub(target,position);  // A vector pointing from the position to the target
	
	    // Normalize desired and scale to maximum speed
	    desired.normalize();
	    desired.mult(maxspeed);
	    // Steering = Desired minus Velocity
	    PVector steer = PVector.sub(desired,velocity);
	    steer.limit(maxforce);  // Limit to maximum steering force
	
	    applyForce(steer);
	  }
	
	  void display() 
	  {
	    // Draw a triangle rotated in the direction of velocity
		  float theta = velocity.heading2D() + pr.radians(90);
		    pr.fill(255,0,0);
		    pr.stroke(0);
		    pr.pushMatrix();
		    pr.translate(position.x,position.y);
		    pr.rotate(theta);

		    
		    
		    //head
		    pr.beginShape();
		    pr.fill(255, 0, 0);
		    pr.strokeWeight(r/25);
		    pr.vertex(-0.8f*r,-3*r/2 + r/6); //1.5
		    pr.vertex(-r/3, -r);  //2
		    pr.vertex(r/3, -r);  //3
		    pr.vertex(0.8f*r,-3*r/2 + r/6); //3.5
		    pr.vertex(0.9f*r, -3*r/2 + r/3);  //4.5
		    pr.vertex(0.8f*r,-r/2);  //5
		    pr.vertex(r, r/3);  //6
		    pr.vertex(r/3, 2*r/3);  //7
		    pr.vertex(0, r);  //8
		    pr.vertex(-r/3, 2*r/3);  //9
		    pr.vertex(-r, r/3);  //10
		    pr.vertex(-0.8f*r, -r/2);  //11
		    pr.vertex(-0.9f*r, -3*r/2 + r/3);  //11.5
		    //vertex(-r, -3*r/2); //1
		    pr.vertex(-0.8f*r,-3*r/2 + r/6); //1.5
		    pr.endShape();
		    
		    //mane for females
		  //add mane for females
		    pr.beginShape();
		    pr.vertex(0.8f*r, r/2.33f);//6.5
		    pr.vertex(r, 2*r/3);//6.6
		    pr.vertex(r/3, r);//7.5
		    pr.vertex(0, 4*r/3);//8.5
		    pr.vertex(-r/3, r);//9.5
		    pr.vertex(-r, 2*r/3);//10.6
		    pr.vertex(-0.8f*r, r/2.33f);//10.5
		    pr.vertex(-r/3, 2*r/3);  //9
		    pr.vertex(0, r);  //8
		    pr.vertex(r/3, 2*r/3);  //7
		    pr.vertex(0.8f*r, r/2.33f);//6.5
		    pr.endShape();
		    
		    
		    pr.fill(0);
		    polygon(0, r/2.5f, 0.18f*r, 6); //nose
		    pr.fill(255);
		    pr.strokeWeight(r/16);
		    pr.line(-0.8f*r, -r/2,-r/3, -r/3);
		    pr.line(0.8f*r,-r/2,r/3, -r/3);
		    
		    pr.strokeWeight(r/50);
		    pr.line(-r/3, -r/3, -0.18f*r, r/2.5f);
		    pr.line(r/3, -r/3, 0.18f*r, r/2.5f);
		    pr.popMatrix();
	  }
	  
	  void polygon(float x, float y, float radius, int npoints) 
	  {
		  float angle = pr.TWO_PI / npoints;
		  pr.beginShape();
		  for (float a = 0; a < pr.TWO_PI; a += angle) {
		    float sx = x + pr.cos(a) * radius;
		    float sy = y + pr.sin(a) * radius;
		    pr.vertex(sx, sy);
		  }
		  pr.endShape(pr.CLOSE);
		}
	
	  // Wraparound
	  void borders() 
	  {
	    if (position.x < -r) position.x = pr.width+r;
	    if (position.y < -r) position.y = pr.height+r;
	    if (position.x > pr.width+r) position.x = -r;
	    if (position.y > pr.height+r) position.y = -r;
	  }
	
	
	
	// A method just to draw the circle associated with wandering
	void drawWanderStuff(PVector position, PVector circle, PVector target, float rad) 
	{
		pr.stroke(0);
		pr.noFill();
		pr.ellipseMode(pr.CENTER);
		pr.ellipse(circle.x,circle.y,rad*2,rad*2);
		pr.ellipse(target.x,target.y,4,4);
		pr.line(position.x,position.y,circle.x,circle.y);
		pr.line(circle.x,circle.y,target.x,target.y);
	}

}