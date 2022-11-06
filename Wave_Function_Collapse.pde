public Module[][] grid;

public void setup() {
    size(600, 600);
    this.grid = new Module[10][10];
    for (int y = 0; y < this.grid.length; y++) {
        for (int x = 0; x < this.grid[y].length; x++) {
            this.grid[y][x] = new Module();
        }
    }
    
    noSmooth();
    
    noLoop();
}

public void draw() {
    background(0);
}

public <T> ArrayList<T> mergeArrays(ArrayList<T>...arrays) {
    ArrayList<T> merged = new ArrayList();
    for (ArrayList<T> array : arrays) {
        for (T element : array) {
            if (!merged.contains(element)) merged.add(element);
        }
    }
    return merged;
}