//import image outterspace.png
//import image ship66.png
//import image blackhole1.png

//all variables that need to be declared prior
PImage space,ship,blackhole;
int lives=4;
int score=0; 
float g=-9.81;  // Gravity
float k=0.0;    // Friction const
float m=0.25;   // Mass kg
float dt=0.01;  // Time step 10ms
blackhole [] b;
int sx;
int sy;
int rx;
int ry;


void setup()
{
  size(600,400,P2D);
  space=loadImage("outterspace.png");
  ship=loadImage("ship66.png");
  blackhole=loadImage("blackhole17.png");
  textureMode(NORMAL);
  blendMode(BLEND);
  noStroke();
  b=new blackhole [17]; 
  
  for(int i=0;i<17;i++) 
  {
    b[i]=new blackhole ();
  }
 
}
void draw()
{
  //all shapes that have been drawn in
  background(space);
  fill(0,0,255);
  rect(525,frameCount%400,30,100);// right hand side rebound object
  fill(255,0,0);
  rect(50,frameCount%400,30,100); //left hand side rebound object
  image(ship,mouseX,mouseY); //ship loaded in
   //dispaly score
 textSize(32);
 fill(0,0,255);
 text(score, 10, 30); // Display score
 text(lives,150, 30); // Display lives
// part of the balls bouncing around
for(int i=0;i<17;i++) 
  {
   b[i].advance();
   
   int r=Collision(i,b); 
   if(r!=-1)            
   {
        float nvxj = (b[i].vx * (b[i].radius - b[r].radius) + (2 * b[r].radius * b[r].vx)) / (b[i].radius + b[r].radius);
      float nvyj = (b[i].vy * (b[i].radius - b[r].radius) + (2 * b[r].radius * b[r].vy)) / (b[i].radius + b[r].radius);
      float nvxr = (b[r].vx * (b[r].radius - b[i].radius) + (2 * b[i].radius * b[i].vx)) / (b[i].radius + b[r].radius);
      float nvyr = (b[r].vy * (b[r].radius - b[i].radius) + (2 * b[i].radius * b[i].vy)) / (b[i].radius + b[r].radius);   
  
      b[i].vx=nvxj;
      b[i].vy=nvyj;
      b[r].vx=nvxr;
      b[r].vy=nvyr;
      {
        
      }

   }


   b[i].draw_blackhole();
  }

  //paddle interaction
  if(mouseX>590)
  {
    mouseX=300;
    mouseY=200;
    lives--;
    if(lives==0) exit(); 
  }
  if(mouseX<10)
  {
    mouseX=300;
    mouseY=200;
    lives--;
    if(lives==0) exit(); 
  }
  if(mouseY>height-10)
  {
     mouseX=300;
    mouseY=200;
    lives--;
    if(lives==0) exit();
  }
    if(mouseY==frameCount%400)
  {
    score++;
  }
  if(lives==0) 
    {
      System.out.println("GAME OVER"); System.out.println("Your score was :" +score);
    }
}
int Collision(int j,blackhole b[])
{
  int res=-1; 
  for(int i=0;i<17;i++) // 
  {
   if (i!=j) 
   {
     float sep=sqrt(pow(b[i].x-b[j].x,2)+pow(b[i].y-b[j].y,2));
     float rads=b[i].radius+b[j].radius;
     if(sep<rads){ res=i;} 
   }
  }
  return(res);
}

class blackhole
{ 
  color c;  
  float radius; 
  float vy;
  float y;
  float x;
  float vx;
  float t;

 blackhole()
  {
   y=random(0.1,0.9); 
   vy=random(-1,1); 
   x=random(0.1,0.9);
   vx=random(-1,1);
   t=0;c=color(random(255),random(255),random(255));
   radius=0.01+random(0.04);

  }


  
  void advance()
  {
    vy=vy+(g-((k/m)*vy))*dt;
    y=y+(vy*dt); 
    vx=vx+(-((k/m)*vx))*dt;
    x=x+(vx*dt);   
    t=t+dt;
    if (y<=0)  {vy=-vy; y=y+(vy*dt);}
    if (x<=0)  {vx=-vx; x=x+(vx*dt);} 
    if (x>=1)  {vx=-vx; x=x+(vx*dt);} 
  }
  {
}
   void draw_blackhole()
  {
     float sx=map(x,0,1,0,width);
     float sy=map(y,0,1,height-1,0);
     float rx=map(radius,0,1,0,width);
     float ry=map(radius,0,1,0,height);
     fill(c);
     image(blackhole,sx,sy,2*rx,2*ry);   
  }
}