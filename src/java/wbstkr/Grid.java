package wbstkr;

import processing.core.PApplet;
import wbstkr.util.SpriteSheet;

import java.util.ArrayList;
import java.util.Collections;

public class Grid {
    private final PApplet parent;
    private final SpriteSheet sheet;
    private final float tileSize;
    private final ArrayList<WFCModule> contents;
    private final int rowLength;

    public Grid(PApplet parent, SpriteSheet sheet, int rowLength) {
        this.parent = parent;
        this.sheet = sheet;
        this.rowLength = rowLength;
        this.tileSize = this.parent.width / (float) rowLength;
        this.contents = new ArrayList<>();
        for (int i = 0; i < this.rowLength * this.parent.height / this.tileSize; i++) {
            this.contents.add(new WFCModule(this.sheet));
        }
    }

    public void update() {
        pickRand();
    }

    private void pickRand() {
        ArrayList<WFCModule> targetGroup = null;
        for(int i = 2; i <= this.sheet.size(); i++) {
            if(getSizeSubset(i).size() > 0) {
                targetGroup = getSizeSubset(i);
                break;
            }
        }
        if (targetGroup != null) {
            Collections.shuffle(targetGroup);
            targetGroup.get(0).setRand();
        }
    }

    private ArrayList<WFCModule> getSizeSubset(int size) {
        ArrayList<WFCModule> output = new ArrayList<>();
        for (WFCModule content : this.contents) {
            if (content.size() == size) {
                output.add(content);
            }
        }
        return output;
    }

    public void render() {
        for (int i = 0; i < this.contents.size(); i++) {
            float x = i % this.rowLength * this.tileSize;
            float y = i / this.rowLength * this.tileSize;
            this.contents.get(i).render(x, y, this.tileSize);
        }
    }
}
