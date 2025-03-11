package game;

public class DeckOfCards extends Item {
    private Item itemToSpitToUser;

    public Item getItem() {
        return itemToSpitToUser;
    }

    private Transmorgrifier transmorgrifier;

    private Room room;

    public DeckOfCards(String name, String description, String inspection, Transmorgrifier transmorgrifier, Room room) {
        super(name, description, inspection);
        this.transmorgrifier=transmorgrifier;
        this.room=room;

    }

    @Override
    public void use() {
        if (transmorgrifier.isUsable() && transmorgrifier.isBeingUsed()) {
            TransmorgSupplier<Paper> paperSupplier = () -> new Paper("paper", "a stack of "+ConsoleColors.CYAN+"paper."+ConsoleColors.RESET, ConsoleColors.GREEN+ "PERCEPTION" +ConsoleColors.RESET+": A stack of "+ConsoleColors.CYAN+"paper"+ConsoleColors.RESET + " with jumbled, incoherent lettering", transmorgrifier, room);
            itemToSpitToUser = paperSupplier.get();
            itemToSpitToUser.setObserved(true);
            transmorgrifier.getItemsToTransmorgrify().add(itemToSpitToUser);
            transmorgrifier.getItemsToTransmorgrify().remove(this);
            room.setItem(Main.Direction.south, itemToSpitToUser);
            room.removeItem(Main.Direction.east, this);
            System.out.println(ConsoleColors.RED+ "ACTION" +ConsoleColors.RESET+": The deck of " + ConsoleColors.CYAN  +"cards" + ConsoleColors.RESET  +" transforms into a stack of " + ConsoleColors.CYAN  +"paper." + ConsoleColors.RESET);
        }
        else{
            System.out.println(ConsoleColors.RED+"ACTION" + ConsoleColors.RESET + ": You play a game of solitaire. The smells of the room get stronger. You should probably get moving.");
        }
    }
}
