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
    int newIndex = int(gridPos.x + (gridPos.y * rowSize));
    if (newIndex < 0 || newIndex >= grid.size()) {
        return - 1;
    } else {
        return newIndex;
    }
}

public void update() {
    int least = 8;
    for (ArrayList < Integer > module : grid) {
        if (module.size() < least && module.size() > 1) {
            least = module.size();
        }
    }
    ArrayList<Integer> choose = new ArrayList();
    for (int i = 0; i < grid.size(); i++) {
        if (grid.get(i).size() == least) {
            choose.add(i);
        }
    }
    if (choose.size() > 0) {
        Collections.shuffle(choose);
        int index = choose.get(0);
        Collections.shuffle(grid.get(index));
        int temp = grid.get(index).get(0);
        grid.get(index).clear();
        grid.get(index).add(temp);
        int[][] offsets = {{ - 1, 0} , {0, -1} , {1, 0} , {0, 1} };
        for (int[] offset : offsets) {
            int newIndex = getGridOffset(index, offset[0], offset[1]);
            if (newIndex == -1) {
                continue;
            } else {
                if (grid.get(newIndex).size() > 1) {
                    grid.get(newIndex).clear();
                    for (int n : RULES.get(temp)) {
                        grid.get(newIndex).add(n);
                    }
                }
            }
        }
    }
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

public void keyPressed() {
    if (key == ' ') setup();
}