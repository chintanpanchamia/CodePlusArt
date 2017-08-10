void settings()
{
  size(500, 500);
}

void setup()
{
  fill(255);
  rect(0,0,width, height);
  stroke(0);
  line(width/2, 0, width/2, height);
  line(0, height/2, width, height/2);
  
  stroke(0);
  int r = 100;
  pushMatrix();
  translate(width/2, height/2);
  //beginShape();
  //fill(255);
  //strokeWeight(r/25);
  
  ////vertex(-r, -3*r/2); //1
  //vertex(-0.8*r,-3*r/2 + r/6); //1.5
  //vertex(-r/3, -r);  //2
  //vertex(r/3, -r);  //3
  //vertex(0.8*r,-3*r/2 + r/6); //3.5
  ////vertex(r, -3*r/2);  //4
  //vertex(0.9*r, -3*r/2 + r/3);  //4.5
  //vertex(0.8*r,-r/2);  //5
  //vertex(r, r/3);  //6
  //vertex(r/3, 2*r/3);  //7
  //vertex(0, r);  //8
  //vertex(-r/3, 2*r/3);  //9
  //vertex(-r, r/3);  //10
  //vertex(-0.8*r, -r/2);  //11
  //vertex(-0.9*r, -3*r/2 + r/3);  //11.5
  ////vertex(-r, -3*r/2); //1
  //vertex(-0.8*r,-3*r/2 + r/6); //1.5
  //endShape();
  
  ////add mane for females
  //beginShape();
  //vertex(0.9f*r, r/2.34f);//6.5
  //vertex(r, 2*r/3);//6.6
  //vertex(r/3, r);//7.5
  //vertex(0, 4*r/3);//8.5
  //vertex(-r/3, r);//9.5
  //vertex(-r, 2*r/3);//10.6
  //vertex(-0.9f*r, r/2.34f);//10.5
  //vertex(-r/3, 2*r/3);  //9
  //vertex(0, r);  //8
  //vertex(r/3, 2*r/3);  //7
  //vertex(0.9f*r, r/2.34f);//6.5
  //endShape();
  
  //fill(0);
  //polygon(0, r/2.5, 0.18*r, 6); //nose
  //fill(255);
  //strokeWeight(6);
  //line(-0.8*r, -r/2,-r/3, -r/3);
  //line(0.8*r,-r/2,r/3, -r/3);
  
  //strokeWeight(2);
  //line(-r/3, -r/3, -0.18*r, r/2.5);
  //line(r/3, -r/3, 0.18*r, r/2.5);
  fill(0);
  ellipse(0, 0, r, r);
  beginShape();
  fill(0);
  vertex(0, 0);
  vertex(-r*1.732f/2, -r/2);
  vertex(0,-2*r);
  vertex(r*1.732f/2, -r/2);
  endShape();
  
  popMatrix();
}
//- wolf.width/2     - wolf.height/2

void polygon(float x, float y, float radius, int npoints) {
  float angle = TWO_PI / npoints;
  beginShape();
  for (float a = 0; a < TWO_PI; a += angle) {
    float sx = x + cos(a) * radius;
    float sy = y + sin(a) * radius;
    vertex(sx, sy);
  }
  endShape(CLOSE);
}