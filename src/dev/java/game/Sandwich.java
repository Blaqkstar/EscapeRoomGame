package game;

public class Sandwich extends Item{

    private Item itemToSpitToUser;

    private Transmorgrifier transmorgrifier;

    private Room room;

    public Sandwich(String name, String description, String inspection, Transmorgrifier transmorgrifier, Room room) {
        super(name, description, inspection);
        this.transmorgrifier = transmorgrifier;
        this.room = room;
    }

    @Override
    public void use() {
        if (transmorgrifier.isUsable() && transmorgrifier.isBeingUsed()) {
            System.out.println(ConsoleColors.RED+ "ACTION" +ConsoleColors.RESET+": The " +ConsoleColors.CYAN+ "sandwich" + ConsoleColors.RESET +" explodes, sending its decomposing contents all across the room.");
            transmorgrifier.getItemsToTransmorgrify().remove(this);
            room.removeItem(Main.Direction.north, this);
        }
        else{
            System.out.println(ConsoleColors.RED+"ACTION" + ConsoleColors.RESET + ": You pick up the sandwich and it immediately starts crumbling as if made of gravel. You put it down.");
        }
    }
}
