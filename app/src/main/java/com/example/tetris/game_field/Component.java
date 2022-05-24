package com.example.tetris.game_field;



public class Component {
    public float left;
    public float top;
    public float right;
    public float bottom;
    public int type;

    public Component(float left, float top, float right, float bottom, int type) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        this.type = type;
    }
}