package game;

public class PottedPlant extends Item{

    public PottedPlant(String name, String description, String inspection) {
        super(name, description, inspection);
    }
    @Override
    public void use() {
        this.setUsed(true);
        System.out.println("You move the potted plant and even remove some of the dirt, but nothing of interest here.");
        setDescription("A plant in a pot.");
        setInspection("A vibrant plant sits in a polished ceramic pot, though some soil has been removed, exposing the rough, cracked surface beneath. \n" +
                "The leaves are still lush and green, but you can see the roots peeking through the disturbed soil.");
    }
}
