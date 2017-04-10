import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


import processing.core.*;
public class Wolf 
{
	PVector position;
	PVector velocity;
	PVector acceleration;
	float r;
	float wandertheta;
	float maxforce;    	// Maximum steering force
	float maxspeed;    	// Maximum speed
	int gender;
	int type;			// 0-Generic, 1-Alpha, 2-Beta, 3-Puppies, 4-Oldies, 5-Omega
	PVector color, maneColor;
	PApplet pr;
	ArrayList<PVector> wolfPath = new ArrayList<>();
	PVector target = new PVector(-1, -1);
	int currentIndex = 0;

	Wolf(float x, float y, int type, PApplet parent) 
	{			
		pr = parent;
		this.type = type;
		gender = pr.random(-1, 1) > 0 ? 1:0; //1-female, 0-male
		position = new PVector(x,y);

		iniTypeKinematics(type);

	}

	void iniTypeKinematics(int type)
	{
		acceleration = new PVector(0,0);

		velocity = new PVector(0,0);
		r = 0.007f*pr.width; //* pr.height / 150680;
		maxspeed = r/10;

		wandertheta = 0;
		maxforce = 0.008f;
		switch(type)
		{
		case 1:	//Alpha
		{
			this.color = new PVector(204, 0, 0);
			this.maneColor = new PVector(150, 0, 0);
			break;
		}
		case 2: //beta
		{
			r *= 0.85f;
			//maxspeed *= 0.95f;
			this.color = new PVector(111, 102, 104);
			this.maneColor = new PVector(72, 65, 66);
			break;
		}
		case 3: //Puppies
		{
			r *= 0.65f;
			//maxspeed *= 0.9f;
			this.color = new PVector(227, 189, 197);
			this.maneColor = new PVector(172, 121, 132);
			break;
		}
		case 4: //oldies
		{
			r *= 0.8f;
			//maxspeed *= 0.85f;
			//				this.color = new PVector(195, 195, 195);
			//				this.maneColor = new PVector(150, 143, 145);
			this.maneColor = new PVector(195, 195, 195);
			this.color = new PVector(255, 255, 255);
			break;
		}
		case 5: //Omega
		{
			r *= 0.8f;
			//maxspeed *= 0.8f;
			this.color = new PVector(119, 42, 58);
			this.maneColor = new PVector(171, 36, 64);
			break;
		}

		default: //Loner
		{
			r *= 0.95f;
			//maxspeed *= 0.9f;
			this.color = new PVector(205, 175, 149);
			this.maneColor = new PVector(139, 119, 101);
			break;
		}
		}
	}

	void run(ArrayList<Wolf> pack) 
	{
		flock(pack);
		update();
		borders();
		display();
		if(this.type == 1)
			this.wander();
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
		//		System.out.println(position + " " + velocity);
	}

	void flock(ArrayList<Wolf> pack) {
		PVector sep = separate(pack);   // Separation
		PVector ali = align(pack);      // Alignment
		PVector coh = cohesion(pack);   // Cohesion
		// Arbitrarily weight these forces
		sep.mult(3.5f);
		ali.mult(1.0f);
		coh.mult(2.0f);
		// Add the force vectors to acceleration
		applyForce(sep);
		applyForce(ali);
		applyForce(coh);
	}



	PVector separate (ArrayList<Wolf> pack) 
	{
		float desiredseparation = 6.5f*this.r;
		PVector steer = new PVector(0,0,0);
		int count = 0;
		// For every boid in the system, check if it's too close
		for (Wolf other : pack) {
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

	PVector cohesion (ArrayList<Wolf> pack) 
	{
		float neighbordist = 5f*r;
		PVector sum = new PVector(0,0);   // Start with empty vector to accumulate all positions
		int count = 0;
		for (Wolf other : pack) 
		{
			float d = PVector.dist(position,other.position);
			if(this.type == 2 && other.type == 1)
			{
				PVector stickup = PVector.mult(other.velocity, -2).normalize();
				stickup.mult(0.25f).add(other.position);
				return seek1(stickup);
			}
			else if(this.type == 3 && other.type == 2)
			{
				PVector stickup = PVector.mult(other.velocity, -2.5f).normalize();
				stickup.mult(0.2f).add(other.position);
				return seek1(stickup);
			}
			else if(this.type == 4 && other.type == 3)
			{
				PVector stickup = PVector.mult(other.velocity, -3.0f).normalize();
				stickup.mult(0.15f).add(other.position);
				return seek1(stickup);
			}
			else if(this.type == 5 && other.type == 4)
			{
				PVector stickup = PVector.mult(other.velocity, -3.5f).normalize();
				stickup.mult(0.1f).add(other.position);
				return seek1(stickup);
			}
			if ((d > 0) && (d < neighbordist)) 
			{
				sum.add(other.position); // Add position
				count++;
			}
		}
		if (count > 0) {
			sum.div(count);
			return seek1(sum);  // Steer towards the position
		} else {
			return new PVector(0,0);
		}
	}


	
	PVector align (ArrayList<Wolf> pack) {
		float neighbordist = 5f*r;
		PVector sum = new PVector(0,0);
		int count = 0;
		for (Wolf other : pack) 
		{
			float d = PVector.dist(position, other.position);
			if(other.type == 1 && (d < neighbordist))
			{
				other.velocity.normalize().mult(other.maxspeed);
				return PVector.sub(other.velocity, this.velocity).limit(this.maxforce);
			}
			if ((d > 0) && (d < neighbordist)) 
			{
				sum.add(other.velocity);
				count++;
			}
		}
		if (count > 0) 
		{
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

	void follow(ArrayList<PVector> path)
	{
		this.target = path.get(this.currentIndex);


		if(PVector.dist(this.position, this.target) <= 120)
		{

			this.currentIndex = this.currentIndex + 1;
			if(this.currentIndex == path.size())
			{
				this.currentIndex = this.currentIndex - 1;
			}
		}

		seek(this.target);	
	}

	void wander() 
	{
		float wanderR = 100;         					// Radius for our "wander circle"
		float wanderD = 200;         					// Distance for our "wander circle"
		float change = 0.55f;
		wandertheta += pr.random(-change,change);     	// Randomly change wander theta

		// Now we have to calculate the new position to steer towards on the wander circle
		PVector circlepos = velocity.get();    			// Start with velocity
		circlepos.normalize();            				// Normalize to get heading
		circlepos.mult(wanderD);          				// Multiply by distance
		circlepos.add(position);               			// Make it relative to boid's position

		float h = velocity.heading();        			// We need to know the heading to offset wandertheta

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

	PVector seek1(PVector target) {
		PVector desired = PVector.sub(target,position);  // A vector pointing from the position to the target
		// Normalize desired and scale to maximum speed
		desired.normalize();
		desired.mult(maxspeed);
		// Steering = Desired minus Velocity
		PVector steer = PVector.sub(desired,velocity);
		steer.limit(maxforce);  // Limit to maximum steering force
		return steer;
	}

	void display() 
	{
		// Draw a triangle rotated in the direction of velocity
		float theta = velocity.heading() + pr.radians(90);
		pr.fill(255,0,0);
		pr.stroke(0);
		pr.pushMatrix();
		pr.translate(position.x,position.y);
		pr.rotate(theta);

		//head
		pr.beginShape();
		if(this.type == 1)
			pr.stroke(255); //for white outline
		pr.fill(color.x, color.y, color.z);
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

		if(this.gender == 1)
		{
			pr.fill(maneColor.x, maneColor.y, maneColor.z);
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
		}

		pr.fill(0);
		pr.stroke(0);
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
		if (position.x > pr.width+ r) position.x = -r;
		if (position.y > pr.height+ r) position.y = -r;
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