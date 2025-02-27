package game;

public class PottedPlant extends Item{

    public PottedPlant(String name, String description, String inspection) {
        super(name, description, inspection);
    }
    @Override
    public void use() {
        this.setUsed(true);
        System.out.println("You move the potted plant and even dump the dirt out, but nothing of interest here.");
    }
}
