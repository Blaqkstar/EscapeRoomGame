package game;

public class Paper extends Item {

    private Transmorgrifier transmorgrifier;

    private Room room;

    public Paper(String name, String description, String inspection, Transmorgrifier transmorgrifier, Room room) {
        super(name, description, inspection);
        this.transmorgrifier=transmorgrifier;
        this.room=room;

    }

    @Override
    public void use() {
        if (transmorgrifier.isBeingUsed()) {
            TransmorgSupplier<BallOfLead> ballOfLeadSupplier = () -> new BallOfLead("lead", "A misshapen hunk of lead", "Nothing interesting here. It's just a ball of lead.", transmorgrifier, room);
            transmorgrifier.getItemsToTransmorgrify().remove(this);
            room.removeItem(Main.Direction.south, this);
            System.out.println(ConsoleColors.RED + "ACTION" + ConsoleColors.RESET + ": The stack of papers erupt in a ball of flame");
        }
        else {
            System.out.println("You write down some of your thoughts, but the corners of your mind are filled with terror, and you stop.");
        }

    }
}
