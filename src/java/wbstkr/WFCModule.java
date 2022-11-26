package wbstkr;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import wbstkr.util.SpriteSheet;

public class WFCModule {
    private final PApplet parent;
    private final SpriteSheet sheet;
    private final List<Integer> contents;
    private final int index;

    public WFCModule(PApplet parent, SpriteSheet sheet) {
        this.parent = parent;
        this.sheet = sheet;
        this.contents = new ArrayList<>();
        this.index = -1;
        for (int i = 0; i < this.sheet.size(); i++) {
            this.contents.add(i);
        }
    }

    public void render() {

    }
}