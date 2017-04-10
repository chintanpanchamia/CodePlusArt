import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import processing.core.*;
public class WolfPack 
{
	ArrayList<Wolf> pack;
	int MAX_PACK_SIZE;

	PApplet pr;

	WolfPack(PApplet parent)
	{
		pack = new ArrayList<Wolf>();

		this.pr = parent;
		this.MAX_PACK_SIZE = 80;
		this.fillPack();
	}

	void fillPack()
	{	
		int alphaNumber = (int)pr.random(1, this.MAX_PACK_SIZE/10);
		PVector alphaPos = new PVector(0.5f*pr.width, 0.5f*pr.height);
		//Alpha
		this.pack.add(new Wolf(alphaPos.x, alphaPos.y, 1, pr));
		float tempX = 0f, tempY = 0f;
		for(int i = 0; i < alphaNumber - 1; i++)
		{
		//this.packPath.add(new PVector(alphaPos.x, alphaPos.y));
			tempX = pr.random(0, alphaPos.x);
			tempY = pr.random(alphaPos.y, pr.height);
			this.pack.add(new Wolf(tempX, tempY, 1, pr));
		}
		//Beta
		int betaNumber = (int)pr.random(this.MAX_PACK_SIZE/6, this.MAX_PACK_SIZE/3);
		for(int i = 0; i < betaNumber; i++)
		{
			tempX = pr.random(0, alphaPos.x);
			tempY = pr.random(alphaPos.y, pr.height);
			this.pack.add(new Wolf(tempX, tempY, 2, pr));
			//this.packPath.add(new PVector(tempX, tempY));
		}
		//System.out.println(betaNumber);

		//Omega
		int omegaNumber = (int)pr.random(1, this.MAX_PACK_SIZE/6);

		//Puppies
		int puppiesNumber = (this.MAX_PACK_SIZE - 1 - betaNumber - omegaNumber)/2;
		for(int i = 0; i < puppiesNumber; i++)
		{
			tempX = pr.random(0, alphaPos.x);
			tempY = pr.random(alphaPos.y, pr.height);
			this.pack.add(new Wolf(tempX, tempY, 3, pr));
			//this.packPath.add(new PVector(tempX, tempY));
		}
		//System.out.println(puppiesNumber);

		//Oldies
		int oldiesNumber = this.MAX_PACK_SIZE - 1 - betaNumber - omegaNumber - puppiesNumber;
		for(int i = 0; i < oldiesNumber; i++)
		{
			tempX = pr.random(0, alphaPos.x);
			tempY = pr.random(alphaPos.y, pr.height);
			this.pack.add(new Wolf(tempX, tempY, 4, pr));
			//this.packPath.add(new PVector(tempX, tempY));
		}
		//System.out.println(oldiesNumber);
		//Adding Omega
		for(int i = 0; i < omegaNumber; i++)
		{
			tempX = pr.random(0, alphaPos.x);
			tempY = pr.random(alphaPos.y, pr.height);
			this.pack.add(new Wolf(tempX, tempY, 5, pr));
			//this.packPath.add(new PVector(tempX, tempY));
		}
		//System.out.println(omegaNumber);

	}

	void run()
	{
		for(Wolf x: pack)
		{
			x.run(pack);
		}
	}

//	void updatePaths()
//	{
//		//this.packPath = new ArrayList<>();
//		for(int i = 0; i < this.pack.size(); i++)
//		{
//			Wolf w = this.pack.get(i);
//			PVector t = new PVector(w.position.x, w.position.y);
//			if(w.wolfPath.size() > 200)
//				w.wolfPath.remove(0);
//			w.wolfPath.add(t);
//			w.currentIndex = 0;
//		}
//	}
//
//	void makeMovement()
//	{
//		this.pack.get(0).wander();
//		for(int i = 1; i < this.pack.size(); i++)
//		{
//			//this.pack.get(i).seek(this.pack.get(i - 1).position);
//			this.pack.get(i).follow(this.pack.get(i - 1).wolfPath);
//
//		}
//	}

}
