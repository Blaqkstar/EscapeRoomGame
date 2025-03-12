package game;

/**
 * Game item class
 */
public class Flask extends Item{
    private Item itemToSpitToUser;

    public Item getItem() {
        return itemToSpitToUser;
    }

    private Transmorgrifier transmorgrifier;

    private Room room;

    public Flask(String name, String description, String inspection, Transmorgrifier transmorgrifier, Room room) {
        super(name, description, inspection);
        this.transmorgrifier=transmorgrifier;
        this.room=room;

    }

    @Override
    public void use() {
        if (transmorgrifier.isUsable() && transmorgrifier.isBeingUsed()) {
            TransmorgSupplier<BallOfLead> ballOfLeadSupplier = () -> new BallOfLead("lead", "a misshapen hunk of "+ConsoleColors.CYAN+"lead"+ConsoleColors.RESET, ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": Nothing interesting here. It's just a ball of "+ConsoleColors.CYAN+"lead."+ConsoleColors.RESET, transmorgrifier, room);
            itemToSpitToUser = ballOfLeadSupplier.get();
            itemToSpitToUser.setObserved(true);
            transmorgrifier.getItemsToTransmorgrify().add(itemToSpitToUser);
            transmorgrifier.getItemsToTransmorgrify().remove(this);
            room.setItem(Main.Direction.south, itemToSpitToUser);
            room.removeItem(Main.Direction.north, this);
            System.out.println(ConsoleColors.RED+ "ACTION" +ConsoleColors.RESET+": The " + ConsoleColors.CYAN  +"flask" + ConsoleColors.RESET  +" transforms into a ball of " + ConsoleColors.CYAN  +"lead." + ConsoleColors.RESET);
        }
        else{
            System.out.println(ConsoleColors.RED+"ACTION" + ConsoleColors.RESET + ": You pour some liquid into the "+ConsoleColors.CYAN+"flask"+ConsoleColors.RESET + ". Nothing happens.");
        }
    }
}
