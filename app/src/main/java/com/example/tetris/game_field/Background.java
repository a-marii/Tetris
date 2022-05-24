package com.example.tetris.game_field;
import com.example.tetris.ui.Game;

public class Background {
    public int width_square;
    public int height_square;
    public Component[][] array;
    public int size;

    public Background() {
        width_square= Game.width;
        height_square=(int)(Game.height*0.65);
        size=Math.min(width_square/10, height_square/15);
        width_square/=size;
        height_square/=size;
        array = new Component[ width_square][ height_square];
        for(int i=0; i< array.length;i++){
            for(int j=0; j< array[i].length;j++) {
                array[i][j]=new Component(i*size, j*size+size, i*size+size, j*size+size*2,0);
            }
        }
    }
}
