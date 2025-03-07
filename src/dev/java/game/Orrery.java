package game;

public class Orrery extends Item{

    public Orrery(String name, String description, String inspection) {
        super(name, description, inspection);
    }
    @Override
    public void use() {
        // at least for now, this is decorative. Using should result in a println where the planets orbit a solar system with 14 planets
        // some other stuff maybe happens

    }
}
