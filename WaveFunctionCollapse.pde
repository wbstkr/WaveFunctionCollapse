import wbstkr.util.*;
import java.util.Arrays;
import java.util.Collections;

public SpriteSheet tiles;
public ArrayList<ArrayList<Integer>> grid;
public ArrayList<ArrayList<Integer>> RULES;
public final int TILESIZE = 50;

public void setup() {
    size(800, 800);
    
    tiles = new SpriteSheet(this, "TileSet.png", 8, 8);
    grid = new ArrayList();
    for (int i = 0; i < (width / TILESIZE) * (height / TILESIZE); i++) {
        ArrayList module = new ArrayList();
        for (int n = 0; n < tiles.size(); n++) {
            module.add(n);
        }
        grid.add(module);
    }
    
    RULES = new ArrayList();
    for (int i = 0; i < tiles.size(); i++) {
        ArrayList<Integer> rule = new ArrayList();
        if (i > 0) {
            rule.add(i - 1);
        }
        rule.add(i);
        if (i < tiles.size() - 1) {
            rule.add(i + 1);
        }
        RULES.add(rule);
    }
    
    noSmooth();
}

public void draw() {
    background(0);
    update();
    render();
}

public int getGridOffset(int index, int offsetX, int offsetY) {
    int rowSize = width / TILESIZE;
    PVector gridPos = new PVector(index % rowSize, index / rowSize);
    gridPos.add(offsetX, offsetY);
    int newIndex = int(gridPos.x * gridPos.y);
    if (newIndex < 0 || newIndex >= grid.size()) {
        return - 1;
    } else {
        return newIndex;
    }
}

public void update() {
    // for(ArrayList<Integer>)
}

public void render() {
    for (int i = 0; i < grid.size(); i++) {
        int rowSize = width / TILESIZE;
        PVector gridPos = new PVector(i % rowSize, i / rowSize).mult(TILESIZE);
        int moduleRowSize = ceil(sqrt(grid.get(i).size()));
        float moduleWidth = float(TILESIZE) / float(moduleRowSize);
        for (int n = 0; n < grid.get(i).size(); n++) {
            PVector modulePos = new PVector(n % moduleRowSize, n / moduleRowSize).mult(moduleWidth).add(gridPos.copy());
            tiles.drawSprite(grid.get(i).get(n), modulePos.x, modulePos.y, moduleWidth / float(tiles.sprite.width()));
        }
    }
}