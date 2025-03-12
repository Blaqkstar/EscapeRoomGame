package game;

/**
 * Game item class
 */
public class Thermometer extends Item {

    private Transmorgrifier transmorgrifier;

    private Room room;

    public Thermometer(String name, String description, String inspection, Transmorgrifier transmorgrifier, Room room) {
        super(name, description, inspection);
        this.transmorgrifier=transmorgrifier;
        this.room=room;
    }

    @Override
    public void use() {
        if (transmorgrifier.isUsable() && transmorgrifier.isBeingUsed()) {
            transmorgrifier.getItemsToTransmorgrify().remove(this);
            System.out.println(ConsoleColors.RED+ "ACTION" +ConsoleColors.RESET+": The " +ConsoleColors.CYAN+ "thermometer" + ConsoleColors.RESET +" transforms into a ball of liquid mercury. After a few moments, the ball starts moving and crawls sluglike up the south wall and into a crack in the ceiling.");
            room.removeItem(Main.Direction.east, this);
        }
        else{
            System.out.println(ConsoleColors.RED+ "ACTION" +ConsoleColors.RESET+": You check pick up a bottle of liquid and check the temperature. The " +ConsoleColors.CYAN+ "thermometer" + ConsoleColors.RESET +" temperature goes up despite the bottle being cold to the touch.");
        }
    }
}
