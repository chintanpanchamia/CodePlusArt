import processing.core.*;

import java.util.*;
public class Boid{
	  PVector position;
	  PVector velocity;
	  PVector acceleration;
	  float r;
	  float maxforce;    // Maximum steering force
	  float maxspeed;    // Maximum speed
	  
	  PApplet pr;
	  
	  Boid(float x, float y, PApplet parent) {
		pr = parent;
	    acceleration = new PVector(0,0);
	    velocity = new PVector(pr.random(-1,1),pr.random(-1,1));
	    position = new PVector(x,y);
	    r = 8.0f;
	    maxspeed = 1f;
	    maxforce = 0.01f;
	   
	  }

	  void run(ArrayList<Boid> boids) {
	    flock(boids);
	    update();
	    borders();
	    render();
	  }

	  void applyForce(PVector force) {
	    // We could add mass here if we want A = F / M
	    acceleration.add(force);
	  }

	  // We accumulate a new acceleration each time based on three rules
	  void flock(ArrayList<Boid> boids) {
	    PVector sep = separate(boids);   // Separation
	    PVector ali = align(boids);      // Alignment
	    PVector coh = cohesion(boids);   // Cohesion
	    // Arbitrarily weight these forces
	    sep.mult(3.5f);
	    ali.mult(1.0f);
	    coh.mult(1.0f);
	    // Add the force vectors to acceleration
	    applyForce(sep);
	    applyForce(ali);
	    applyForce(coh);
	  }

	  // Method to update position
	  void update() {
	    // Update velocity
	    velocity.add(acceleration);
	    // Limit speed
	    velocity.limit(maxspeed);
	    position.add(velocity);
	    // Reset accelertion to 0 each cycle
	    acceleration.mult(0);
	  }

	  // A method that calculates and applies a steering force towards a target
	  // STEER = DESIRED MINUS VELOCITY
	  PVector seek(PVector target) {
	    PVector desired = PVector.sub(target,position);  // A vector pointing from the position to the target
	    // Normalize desired and scale to maximum speed
	    desired.normalize();
	    desired.mult(maxspeed);
	    // Steering = Desired minus Velocity
	    PVector steer = PVector.sub(desired,velocity);
	    steer.limit(maxforce);  // Limit to maximum steering force
	    return steer;
	  }
	  
	  void render() {
	    // Draw a triangle rotated in the direction of velocity
	    float theta = velocity.heading2D() + pr.radians(90);
	    pr.fill(255,0,0);
	    pr.stroke(0);
	    pr.pushMatrix();
	    pr.translate(position.x,position.y);
	    pr.rotate(theta);

	    int r = 10;
	    
	    
	    pr.beginShape();
	    pr.fill(255, 0, 0);
	    pr.strokeWeight(r/25);
	    
	    //vertex(-r, -3*r/2); //1
	    pr.vertex(-0.8f*r,-3*r/2 + r/6); //1.5
	    pr.vertex(-r/3, -r);  //2
	    pr.vertex(r/3, -r);  //3
	    pr.vertex(0.8f*r,-3*r/2 + r/6); //3.5
	    //vertex(r, -3*r/2);  //4
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
	  void borders() {
	    if (position.x < -r) position.x = pr.width+r;
	    if (position.y < -r) position.y = pr.height+r;
	    if (position.x > pr.width+r) position.x = -r;
	    if (position.y > pr.height+r) position.y = -r;
	  }

	  // Separation
	  // Method checks for nearby boids and steers away
	  PVector separate (ArrayList<Boid> boids) {
	    float desiredseparation = 25.0f;
	    PVector steer = new PVector(0,0,0);
	    int count = 0;
	    // For every boid in the system, check if it's too close
	    for (Boid other : boids) {
	      float d = PVector.dist(position,other.position);
	      // If the distance is greater than 0 and less than an arbitrary amount (0 when you are yourself)
	      if ((d > 0) && (d < desiredseparation)) {
	        // Calculate vector pointing away from neighbor
	        PVector diff = PVector.sub(position,other.position);
	        diff.normalize();
	        diff.div(d);        // Weight by distance
	        steer.add(diff);
	        count++;            // Keep track of how many
	      }
	    }
	    // Average -- divide by how many
	    if (count > 0) {
	      steer.div((float)count);
	    }

	    // As long as the vector is greater than 0
	    if (steer.mag() > 0) {
	      // Implement Reynolds: Steering = Desired - Velocity
	      steer.normalize();
	      steer.mult(maxspeed);
	      steer.sub(velocity);
	      steer.limit(maxforce);
	    }
	    return steer;
	  }

	  // Alignment
	  // For every nearby boid in the system, calculate the average velocity
	  PVector align (ArrayList<Boid> boids) {
	    float neighbordist = 50;
	    PVector sum = new PVector(0,0);
	    int count = 0;
	    for (Boid other : boids) {
	      float d = PVector.dist(position,other.position);
	      if ((d > 0) && (d < neighbordist)) {
	        sum.add(other.velocity);
	        count++;
	      }
	    }
	    if (count > 0) {
	      sum.div((float)count);
	      sum.normalize();
	      sum.mult(maxspeed);
	      PVector steer = PVector.sub(sum,velocity);
	      steer.limit(maxforce);
	      return steer;
	    } else {
	      return new PVector(0,0);
	    }
	  }

	  // Cohesion
	  // For the average position (i.e. center) of all nearby boids, calculate steering vector towards that position
	  PVector cohesion (ArrayList<Boid> boids) {
	    float neighbordist = 50;
	    PVector sum = new PVector(0,0);   // Start with empty vector to accumulate all positions
	    int count = 0;
	    for (Boid other : boids) {
	      float d = PVector.dist(position,other.position);
	      if ((d > 0) && (d < neighbordist)) {
	        sum.add(other.position); // Add position
	        count++;
	      }
	    }
	    if (count > 0) {
	      sum.div(count);
	      return seek(sum);  // Steer towards the position
	    } else {
	      return new PVector(0,0);
	    }
	  }
}
