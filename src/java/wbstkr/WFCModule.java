package wbstkr;

import wbstkr.util.SpriteSheet;

import java.util.ArrayList;
import java.util.Collections;

public class WFCModule {
    private final SpriteSheet sheet;
    private final ArrayList<Integer> states;

    public WFCModule(SpriteSheet sheet) {
        this.sheet = sheet;
        this.states = new ArrayList<>();
        for (int i = 0; i < this.sheet.size(); i++) {
            this.states.add(i);
        }
    }

    public int size() {
        return this.states.size();
    }

    public ArrayList<Integer> getStates() {
        return this.states;
    }

    public void render(float x, float y, float tileSize) {
        int rowLength = (int) Math.ceil(Math.sqrt(this.states.size()));
        float tileWidth = tileSize / rowLength;
        for (int i = 0; i < this.states.size(); i++) {
            float xOffset = i % rowLength * tileWidth;
            float yOffset = (float) (i / rowLength) * tileWidth;
            this.sheet.drawSprite(this.states.get(i), x + xOffset, y + yOffset, tileWidth / this.sheet.sprite.width());
        }
    }

    public void setRand() {
        Collections.shuffle(this.states);
        int target = this.states.get(0);
        this.states.clear();
        this.states.add(target);
    }

    public void set(int i) {
        this.states.clear();
        this.states.add(i);
    }
}