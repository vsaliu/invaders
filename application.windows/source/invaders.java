import processing.core.*; 
import processing.data.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class invaders extends PApplet {


boolean left_pressed = false;
boolean right_pressed = false;
int missile_index = 0;
int missile_num = 6;
int alien_missile_num = 20;
int alien_x = 10;
int alien_y = 5;
int score = 0;
int last_time_am = 0;
int fire_freq = 3000;
boolean score_increment = true;
boolean you_lose = false;

PImage ship_img;
PImage alien_img;
Ship ship;
Missile missile[] = new Missile[missile_num];
Alien alien[][] = new Alien[alien_x][alien_y];
Alien_missile alien_missile[] = new Alien_missile[alien_missile_num];

public void setup() {
  size(700, 600);
  frameRate(30);
  ship_img = loadImage("ship.png");
  alien_img = loadImage("alien.png");
  ship = new Ship(width/2, height-ship_img.height-10, ship_img.width, ship_img.height, 12);
  for (int i=0; i<missile_num; i++) {
    missile[i] = new Missile(10, 10);
  }
  for(int i=0; i<alien_missile_num; i++){
   alien_missile[i] = new Alien_missile(); 
  }
  for (int i=0; i<alien_x; i++) {
    for (int j=0; j<alien_y; j++) {
      alien[i][j] = new Alien(i*width/alien_x, j*40, alien_img.width, alien_img.height);
    }
  }
}

public void draw() {
  if(you_lose == false){
  background(140);
  
  fill(0);
  textSize(20);
  text(score, width-50, height-30);
  if (score==50) {
    fill(0, 240, 0);
    textSize(40);
    text("YOU WIN!!!", width/2-60, height/2);
  }

  ship.display();  
  aliens();
  missiles();
  alien_missiles();
  }
  else{
    fill(240, 0, 0);
    textSize(40);
    text("YOU LOSE", width/2-60, height/2);
  }

}

public void explosion(int xpos, int ypos) {
  for (int i=0; i<1000; i++) {
    fill(250, 200, 0);
    int rand_x = PApplet.parseInt(random(-20, 20));
    int rand_y = PApplet.parseInt(random(-20, 20));
    point(xpos+rand_x, ypos+rand_y);
  }
}

public void keyPressed() {
  if (keyCode == LEFT) {
    left_pressed = true;
  }
  if (keyCode == RIGHT) {
    right_pressed = true;
  }
  if (key == ' ') {
    if (missile[missile_index].fired == false) {
      //missile[missile_index].fired = true;
      missile[missile_index].fire(ship.x+ship._width/2);
      missile_index++;
    }
    if (missile_index>missile_num-1) {
      missile_index=0;
    }
  }
}

public void keyReleased() {
  if (keyCode == LEFT) {
    left_pressed = false;
  } 
  if (keyCode == RIGHT) {
    right_pressed = false;
  }
}

class Ship {
  int x, y, _width, _height, speed;
  boolean hit;

  Ship(int x_ship, int y_ship, int width_ship, int height_ship, int ship_speed) {
    x = x_ship;
    y = y_ship;
    _width = width_ship;
    _height = height_ship;
    speed = ship_speed;
    hit = false;
  }

  public void display() {
    if (left_pressed) {
      x = x-speed;
    }
    if (right_pressed) {
      x = x+speed;
    }
    if ((x+_width)>width) {
      x = width-_width;
    }
    if (x<0) {
      x=0;
    }
//    fill(0, 120, 30);
//    rect(x, y, _width, _height);
    image(ship_img,x,y);
    if(hit == true){
     you_lose = true; 
     println("ship hit");
    }
  }
}

class Missile { 
  int _width, _height, x, y, index;
  boolean fired, hit;

  Missile(int width_missile, int height_missile) {
    _width = width_missile;
    _height = height_missile;
    fired = false;
    index=0;
    hit = false;
  } 

  public void fire(int xpos) {
    x = xpos;
    fired = true;
  }

  public void display() {
    if (fired && hit==false) {
      index = index+10;
      fill(0);
      rect(x, ship.y-index, _width, _height);
      y = ship.y-index;
    }
    if (y<=0) {
      fired = false; 
      index = 0;
    }
    if (hit) {
      index = 0;
      y = ship.y;
      hit = false;
      fired = false;
    }
  }
}

class Alien {
  int x, y, _width, _height, index;
  boolean hit, right_barrier, left_barrier;

  Alien(int xpos, int ypos, int alien_width, int alien_height) {
    x = xpos;
    y = ypos;
    _width = alien_width;
    _height = alien_height;
    hit = false;
    index = 5;
    right_barrier = false;
    left_barrier = false;
  }

  public void display() {
    x = x+index;

    if (x+_width>=width) {
      right_barrier = true;
    }
    else {
      right_barrier = false;
    }
    if (x<=0) {
      left_barrier = true;
    }
    else {
      left_barrier = false;
    }
    if (right_barrier) {
      index = -5;
    }
    if (left_barrier) {
      index = 5;
    }

    if (hit==false) {
//      fill(0, 250, 0);
//      rect(x, y, _width, _height);
        image(alien_img,x,y);
    }
    else {
      x = width+10;
    }
  }
}

class Alien_missile {
  int _width, _height, x, y, index, speed;
  boolean fired, hit;

  Alien_missile() {
    _width = 5;
    _height = 7;
    fired = false;
    hit = false;
    speed = 10;
  }  

  public void fire(int x_coor, int y_coor) {
    x = x_coor; 
    y = y_coor;
  }

  public void display() {
    if (fired && hit==false) {
      fill(0, 50, 100);
      rect(x, y, _width, _height);
      y = y+speed;
    }
    if (y>=height) {
      fired = false;
    }
    if (hit) {
      y = 0;
      hit = false;
      fired = false;
    }
  }
}



public void alien_missiles() {

  // random number generator for alien selection x
  // random y
  //

  if (millis()-last_time_am>fire_freq) {
    int num_missiles = PApplet.parseInt(random(alien_missile_num));
    //    int[] alien_select_x = new int[num_missiles];
    //    int[] alien_select_y = new int[num_missiles];

    for (int i = 0; i<num_missiles; i++) {
      if (alien_missile[i].fired==false) {
        int alien_select_x = PApplet.parseInt(random(alien_x));
        int alien_select_y = PApplet.parseInt(random(alien_y));
        alien_missile[i].fired = true;
        alien_missile[i].fire(alien[alien_select_x][alien_select_y].x, alien[alien_select_x][alien_select_y].y);
      }
    }
    last_time_am = millis();
  }
  
  if(millis()>5000 && millis()<=15000){
   fire_freq = 250; 
  }
  if(millis()>15000 && millis()<25000){
   fire_freq = 50; 
  }
  if(millis()>25000){
   fire_freq = 2; 
  }
  
  for (int i=0; i<alien_missile_num; i++) {
    alien_missile[i].display();
    if(alien_missile[i].x>=ship.x && alien_missile[i].x<=ship.x+ship._width && alien_missile[i].y>=ship.y && alien_missile[i].y<=ship.y+ship._height-30){
     ship.hit = true;
     println("missile hit"); 
    }
  }
  
  
  
}

public void aliens(){
 for (int i=0; i<alien_x; i++) {
    for (int j=0; j<alien_y; j++) {
      alien[i][j].display();
    }
  }
}
public void missiles(){

for (int i=0; i<missile_num; i++) {
  missile[i].display();

  for (int k=0; k<alien_x; k++) {
    for (int j=0; j<alien_y; j++) {
      if (missile[i].x>(alien[k][j].x-missile[i]._width) && missile[i].x<(alien[k][j].x+alien[k][j]._width) && missile[i].y<(alien[k][j].y+alien[k][j]._height) && missile[i].y>(alien[k][j].y)) {
        alien[k][j].hit = true; 
        missile[i].hit = true;
        explosion(alien[k][j].x, alien[k][j].y);
        score++;
      }
    }
  }
}

}

  public int sketchWidth() { return 700; }
  public int sketchHeight() { return 600; }
  static public void main(String args[]) {
    PApplet.main(new String[] { "invaders" });
  }
}
