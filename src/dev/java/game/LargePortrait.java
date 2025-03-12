package game;

/**
 * Game item class
 */
public class LargePortrait extends Item {
    private Room room;

    public LargePortrait(String name, String description, String inspection, Room room) {
        super(name, description, inspection);
        this.room = room;
    }
    @Override
    public void use() {
        // this should provide a clue about the levers, linked to the materials in their handle grips
        // nvm the clue is in the inspection string
        System.out.println(ConsoleColors.GREEN+"PERCEPTION"+ConsoleColors.RESET+": Nothing happens.");
    }
}
