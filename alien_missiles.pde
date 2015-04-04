

void alien_missiles() {

  // random number generator for alien selection x
  // random y
  //

  if (millis()-last_time_am>fire_freq) {
    int num_missiles = int(random(alien_missile_num));
    //    int[] alien_select_x = new int[num_missiles];
    //    int[] alien_select_y = new int[num_missiles];

    for (int i = 0; i<num_missiles; i++) {
      if (alien_missile[i].fired==false) {
        int alien_select_x = int(random(alien_x));
        int alien_select_y = int(random(alien_y));
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

