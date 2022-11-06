public class Module {
    public PImage tile;

    public Module() {
        this.tile = loadImage(Tiles.values()[int(random(Tiles.values().length))].filename);
    }
}