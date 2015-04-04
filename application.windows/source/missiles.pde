void missiles(){

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

