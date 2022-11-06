public enum Tiles {
    PINES("big tree.png"),
    SHRUB("small tree.png"),
    GRASS("tall grass.png"),
    FIELD("grass.png"),
    BEACH("sand.png"),
    SHORE("shore.png"),
    WATER("water.png"),
    OCEAN("deep water.png"),
    ;
    public String filename;

    private Tiles(String filename) {
        this.filename = filename;
    }
}