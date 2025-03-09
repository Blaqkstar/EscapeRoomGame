package game;

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
        TransmorgSupplier<Key> keySupplier = () -> new Key("key","A shiny new key","The key has a faint glow as if it is brimming with energy.", room);
        itemToSpitToUser = keySupplier.get();
        itemToSpitToUser.setObserved(true);
        room.setItem(Main.Direction.south, itemToSpitToUser); // adds key to the north
        transmorgrifier.getItemsToTransmorgrify().remove(this);
        ///  TODO: Add key to items to transmorgrify. If they transmorg it, they are locked in the room forever

        System.out.println("The ball of lead transforms into a shiny new key.");
    }
}
