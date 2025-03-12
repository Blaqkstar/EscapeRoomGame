package game;

/**
 * Game item class
 */
public class BallOfLead extends Item{

    private Room room;

    private Item itemToSpitToUser;

    public Item getItem() {
        return itemToSpitToUser;
    }

    private Transmorgrifier transmorgrifier;

    public BallOfLead(String name, String description, String inspection, Transmorgrifier transmorgrifier, Room room) {
        super(name, description, inspection);
        this.transmorgrifier = transmorgrifier;
        this.room = room;
    }

    @Override
    public void use() {
        if (transmorgrifier.isUsable() && transmorgrifier.isBeingUsed()) {
            TransmorgSupplier<Key> keySupplier = () -> new Key("key", "a shiny new "+ConsoleColors.CYAN+"key"+ConsoleColors.RESET, ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": The "+ConsoleColors.CYAN+"key"+ConsoleColors.RESET + " has a faint glow as if it is brimming with energy.", room);
            itemToSpitToUser = keySupplier.get();
            itemToSpitToUser.setObserved(true);
            room.setItem(Main.Direction.south, itemToSpitToUser); // adds key to the north
            room.removeItem(Main.Direction.south, this);
            transmorgrifier.getItemsToTransmorgrify().remove(this);
            ///  TODO: Add key to items to transmorgrify. If they transmorg it, they are locked in the room forever

            System.out.println(ConsoleColors.RED + "ACTION" + ConsoleColors.RESET + ": The ball of " + ConsoleColors.CYAN + "lead" + ConsoleColors.RESET + " transforms into a " + ConsoleColors.CYAN + "shiny new key." + ConsoleColors.RESET);
        }
        else{
            System.out.println(ConsoleColors.RED + "ACTION" + ConsoleColors.RESET + ": You look around the room but don't see anything this would be used for.");
        }
    }
}
