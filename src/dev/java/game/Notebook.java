package game;

public class Notebook extends Item {
    public Notebook(String name, String description, String inspection) {
        super(name, description, inspection);
    }
    @Override
    public void use() {
        this.setUsed(true); // used!
    }
}
