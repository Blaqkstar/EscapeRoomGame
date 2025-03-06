package game;

public class PottedPlant extends Item{

    public PottedPlant(String name, String description, String inspection) {
        super(name, description, inspection);
    }
    @Override
    public void use() {
        if (!isUsed()) {
            System.out.println(ConsoleColors.RED+"ACTION"+ConsoleColors.RESET+": You move the potted plant and even remove some of the dirt, but nothing of interest here.");
            setDescription("a plant in a pot");
            setInspection(ConsoleColors.GREEN+"PERCEPTION"+ConsoleColors.RESET+": A vibrant plant sits in a polished ceramic pot, though some soil has been removed, exposing the rough, cracked surface beneath. \n" +
                    "The leaves are still lush and green, but you can see the roots peeking through the disturbed soil.");
            setUsed(true);
        } else {
            System.out.println(ConsoleColors.RED+"ACTION"+ConsoleColors.RESET+": You've done enough damage here already. Let's poke around the room some more.");
        }

    }
}
