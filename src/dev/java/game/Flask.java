package game;

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
        if (transmorgrifier.isUsable()) {
            TransmorgSupplier<BallOfLead> ballOfLeadSupplier = () -> new BallOfLead("lead", "A misshapen hunk of lead", "Nothing interesting here. It's just a ball of lead.", transmorgrifier, room);
            itemToSpitToUser = ballOfLeadSupplier.get();
            itemToSpitToUser.setObserved(true);
            transmorgrifier.getItemsToTransmorgrify().add(itemToSpitToUser);
            transmorgrifier.getItemsToTransmorgrify().remove(this);
            System.out.println("The flask transforms into a ball of lead.");
        }
        else{
            System.out.println("You pour some liquid into the flask. Nothing happens.");
        }
    }
}
