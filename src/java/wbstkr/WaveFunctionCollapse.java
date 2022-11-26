package wbstkr;

import processing.core.PApplet;
import wbstkr.util.SpriteSheet;

public class WaveFunctionCollapse extends PApplet {
    private Grid grid;

    public static void main(String[] args) {
        String[] processingArgs = {"WaveFunctionCollapse"};
        WaveFunctionCollapse mySketch = new WaveFunctionCollapse();
        PApplet.runSketch(processingArgs, mySketch);
    }

    @Override
    public void settings() {
        size(800, 800);
        noSmooth();
    }

    @Override
    public void setup() {
        SpriteSheet tiles = new SpriteSheet(this, "TileSet.png", 8, 8);
        this.grid = new Grid(this, tiles, 100);
    }

    @Override
    public void draw() {
        background(0);
        this.grid.update();
        if (this.grid.left <= 0) {
            this.grid.render();
        }
    }

    @Override
    public void keyPressed() {
        if (key == ' ') setup();
    }
}