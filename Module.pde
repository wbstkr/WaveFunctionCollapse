public class Module {
    public ArrayList<Integer> contents;

    public Module(SpriteSheet sheet) {
        this.contents = new ArrayList();
        for(int i = 0; i < sheet.size(); i++) {
            this.contents.add(i);
        }
    }
}