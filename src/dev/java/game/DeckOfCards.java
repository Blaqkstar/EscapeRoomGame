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
        if (transmorgrifier.isUsable()) {
            TransmorgSupplier<Paper> paperSupplier = () -> new Paper("paper", "a stack of papers", "a stack of papers with jumbled, incoherent lettering", transmorgrifier, room);
            itemToSpitToUser = paperSupplier.get();
            itemToSpitToUser.setObserved(true);
            transmorgrifier.getItemsToTransmorgrify().add(itemToSpitToUser);
            transmorgrifier.getItemsToTransmorgrify().remove(this);
            System.out.println("The deck of cards transforms into a " + itemToSpitToUser.getDescription());
        }
        else{
            System.out.println("You play a game of solitaire. The smells of the room get stronger. You should probably get moving.");
        }
    }
}
