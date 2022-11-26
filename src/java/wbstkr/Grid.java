package wbstkr;

import processing.core.PApplet;
import wbstkr.util.SpriteSheet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Grid {
    protected final ArrayList<WFCModule> contents;
    private final SpriteSheet sheet;
    private final int rowLength;
    private final float tileSize;
    private final HashMap<Integer, ArrayList<Integer>> rules;
    protected int left;

    public Grid(PApplet parent, SpriteSheet sheet, int rowLength) {
        this.sheet = sheet;
        this.rules = new HashMap<>();
        for (int i = 0; i < this.sheet.size(); i++) {
            ArrayList<Integer> rule = new ArrayList<>();
            for (int n = 0; n < this.sheet.size(); n++) {
                if (n < i - 2 || n > i + 2) rule.add(n);
            }
            this.rules.put(i, rule);
        }
        this.rowLength = rowLength;
        this.tileSize = parent.width / (float) rowLength;
        this.contents = new ArrayList<>();
        for (int i = 0; i < this.rowLength * parent.height / this.tileSize; i++) {
            this.contents.add(new WFCModule(this.sheet));
        }
        ArrayList<WFCModule> init = getSizeSubset(8);
        Collections.shuffle(init);
        init.get(0).set(this.sheet.size() / 2);
        updateNeighbors(this.contents.indexOf(init.get(0)));
        this.left = this.contents.size();
    }

    public void update() {
        if (this.left > 0) {
            int targetIndex = pickRand();
            if (targetIndex > -1) {
                updateNeighbors(targetIndex);
            }
            System.out.printf("\r%d", this.left);
            this.left--;
        }
    }

    private int pickRand() {
        ArrayList<WFCModule> targetGroup = null;
        for (int i = 2; i <= this.sheet.size(); i++) {
            if (!getSizeSubset(i).isEmpty()) {
                targetGroup = getSizeSubset(i);
                break;
            }
        }
        if (targetGroup == null) {
            return -1;
        } else {
            Collections.shuffle(targetGroup);
            targetGroup.get(0).setRand();
            return this.contents.indexOf(targetGroup.get(0));
        }
    }

    private void updateNeighbors(int index) {
        for (WFCModule neighbor : getNeighbors(index)) {
            int target = this.contents.get(index).getStates().get(0);
            for (int state : this.rules.get(target)) {
                neighbor.getStates().remove(Integer.valueOf(state));
            }
        }
    }

    private ArrayList<WFCModule> getNeighbors(int index) {
        ArrayList<WFCModule> output = new ArrayList<>();
        if (index % this.rowLength != 0) {
            output.add(this.contents.get(index - 1));
        }
        if (index % this.rowLength != 0 && index / this.rowLength != 0) {
            output.add(this.contents.get(index - this.rowLength - 1));
        }
        if (index / this.rowLength != 0) {
            output.add(this.contents.get(index - this.rowLength));
        }
        if (index / this.rowLength != 0 && index % this.rowLength != this.rowLength - 1) {
            output.add(this.contents.get(index - this.rowLength + 1));
        }
        if (index % this.rowLength != this.rowLength - 1) {
            output.add(this.contents.get(index + 1));
        }
        if (index % this.rowLength != this.rowLength - 1 && index < this.contents.size() - this.rowLength) {
            output.add(this.contents.get(index + this.rowLength + 1));
        }
        if (index < this.contents.size() - this.rowLength) {
            output.add(this.contents.get(index + this.rowLength));
        }
        if (index < this.contents.size() - this.rowLength && index % this.rowLength != 0) {
            output.add(this.contents.get(index + this.rowLength - 1));
        }
        return output;
    }

    protected ArrayList<WFCModule> getSizeSubset(int size) {
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
            float y = (float) (i / this.rowLength) * this.tileSize;
            this.contents.get(i).render(x, y, this.tileSize);
        }
    }
}
