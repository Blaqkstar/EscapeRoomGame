package game;

public class LargePortrait extends Item {
    private Room room;

    public LargePortrait(String name, String description, String inspection, Room room) {
        super(name, description, inspection);
        this.room = room;
    }
    @Override
    public void use() {
        // this should provide a clue about the levers, linked to the materials in their handle grips

    }
}
