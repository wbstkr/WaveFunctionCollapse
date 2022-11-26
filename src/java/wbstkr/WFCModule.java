package wbstkr;

import processing.core.PApplet;
import wbstkr.util.SpriteSheet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WFCModule {
    private final SpriteSheet sheet;
    private final List<Integer> contents;

    public WFCModule(SpriteSheet sheet) {
        this.sheet = sheet;
        this.contents = new ArrayList<>();
        for (int i = 0; i < this.sheet.size(); i++) {
            this.contents.add(i);
        }
    }

    public int size() {
        return this.contents.size();
    }

    public void render(float x, float y, float tileSize) {
        int rowLength = (int) Math.ceil(Math.sqrt(this.contents.size()));
        float tileWidth = tileSize / rowLength;
        for (int i = 0; i < this.contents.size(); i++) {
            float xOffset = i % rowLength * tileWidth;
            float yOffset = (float) (i / rowLength * tileWidth);
            this.sheet.drawSprite(this.contents.get(i), x + xOffset, y + yOffset, tileWidth / this.sheet.sprite.width());
        }
    }

    public void setRand() {
        Collections.shuffle(this.contents);
        int target = this.contents.get(0);
        this.contents.clear();
        this.contents.add(target);
    }
}